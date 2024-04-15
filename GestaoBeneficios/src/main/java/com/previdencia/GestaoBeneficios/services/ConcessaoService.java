package com.previdencia.GestaoBeneficios.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.previdencia.GestaoBeneficios.models.Concessao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.net.*;
import java.time.LocalDate;

import org.json.JSONException;
import org.json.JSONObject;

import com.previdencia.GestaoBeneficios.models.Beneficio;
import com.previdencia.GestaoBeneficios.models.Concessao;
import com.previdencia.GestaoBeneficios.repository.BeneficioRepository;
import com.previdencia.GestaoBeneficios.repository.ConcessaoRepository;

/**
 * Servico da classe Concessao
 *
 * @Version 1.0
 * @since 1.0
 */
@Service
public class ConcessaoService {
    @Autowired
    private final ConcessaoRepository concessaoRepository;

    @Autowired
    private final BeneficioRepository beneficioRepository;

    public ConcessaoService(ConcessaoRepository concessaoRepository,
                             BeneficioRepository beneficioRepository) {
        this.concessaoRepository = concessaoRepository;
        this.beneficioRepository = beneficioRepository;
    }

    /**
     * Retorna a soma das concessoes do cpf requisitado.
     * O parametro cpf deve ser um numero de 11 digitos por padronizacao
     * <p>
     *     O metodo sempre retornara um double da soma de todas as requisicoes ativas
     * </p>
     * @param cpf Um long contendo o cpf para a pesquisa no banco de dados
     * @return Soma das concessoes
     * @since 1.0
     */
    public double somar(Long cpf){
        List<Concessao> lista = concessaoRepository.findAllByRequisitante(cpf);
        double soma = 0;
        for(Concessao concessao : lista) {
            soma = soma + concessao.getValor();
        }
        return soma;
    }

    /**
     * Metodo que recebe um cpf requerente de um beneficio e analisa e calculo se deve ou nao conceder o beneficio
     * @param cpf
     * @param id
     * @return
     * 		Http status 202 -> Pedido autorizado
     *  	Http status 405 -> Id nao aceito
     */
    public ResponseEntity<Object> concederIndividual(Long cpf, Long id) {
        long tempo = 0;
        double valor;
        BigDecimal contribuicao = BigDecimal.valueOf(0);
        Beneficio beneficio = beneficioRepository.getReferenceById(id);

        Boolean status= verificaBeneficio(1, id, beneficio);
        if(!status) {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }

        String url="http://192.168.37.10:8080/contribuintes/consultar/" + cpf;
        RestTemplate restTemplate = new RestTemplate();

        JSONObject json = restTemplate.getForObject(url, JSONObject.class);

        try {
            tempo = json.getLong("tempoContribuicaoMeses");
            contribuicao = json.getBigDecimal("totalContribuidoAjustado");
        }
        catch (JSONException e) {
            System.out.println("\n\n\n\nJSON NAO GERADO:" +
                    "tempo:"+tempo+"\n" +
                    "contribuicao:"+contribuicao+"\n");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        if(beneficio.getRequisitos() > tempo){
            System.out.println("\n\n\n\nTEMPO MINIMO NAO CUMPRIDO\n\n\n\n");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }


        valor = (contribuicao.multiply(BigDecimal.valueOf(beneficio.getValor())).divide(BigDecimal.valueOf(100))).doubleValue();
        Concessao concessaoAutorizada = new Concessao(UUID.randomUUID(), cpf, cpf,
                LocalDate.now(), valor, true, beneficio);
        concessaoRepository.save(concessaoAutorizada);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

    /**
     * Metodo para conceder beneficios para outra pessoa que nao seja o requisitante
     * @param cpfRequisitante
     * @param id
     * @param cpfBeneficiado
     * @return Http status 202 -> Pedido autorizado,
     * Http status 405 -> Id nao aceito
     *
     */
    public ResponseEntity<Object> conceder(Long cpfRequisitante, Long id, Long cpfBeneficiado) {
        long tempo = 0;
        double valor = 0;
        double contribuicao = 0;
        Beneficio beneficio = beneficioRepository.getReferenceById(id);

        Boolean status= verificaBeneficio(2, id, beneficio);
        if(!status) {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }

        String url="http://192.168.37.10:8080/contribuintes/consultar"+ cpfRequisitante;
        RestTemplate restTemplate = new RestTemplate();
        JSONObject json = restTemplate.getForObject(url, JSONObject.class);

        try {
            tempo = json.getLong("tempoContribuicaoMeses");
            contribuicao = json.getInt("totalContribuidoAjustado");
        } catch (JSONException e) {
            System.out.println("\n\n\n\nJSON NAO GERADO:" +
                    "tempo:"+tempo+"\n" +
                    "contribuicao:"+contribuicao+"\n");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        if(beneficio.getRequisitos() > tempo){
            System.out.println("\n\n\n\nTEMPO MINIMO NAO CUMPRIDO\n\n\n\n");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        valor = ((contribuicao * beneficio.getValor())/100);
        Concessao concessaoAutorizada = new Concessao(UUID.randomUUID(),
                cpfRequisitante, cpfBeneficiado,
                LocalDate.now(), valor, true, beneficio);
        concessaoRepository.save(concessaoAutorizada);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    public boolean verificaBeneficio(int options, Long id, Beneficio beneficio) {
        if (!beneficioRepository.existsById(id)) {
            System.out.println("\n\n\n\nBENEFICIO NAO LOCALIZADO\n\n\n\n");
            return false;
        }
        if ((beneficio.isIndividual() && options == 2) || !beneficio.isIndividual() && options == 1) {
            System.out.println("\n\n\n\nBENEFICIO INCORRETO PARA A CHAMADA\n\n\n\n");
            return false;
        }
        return true;
    }
}
