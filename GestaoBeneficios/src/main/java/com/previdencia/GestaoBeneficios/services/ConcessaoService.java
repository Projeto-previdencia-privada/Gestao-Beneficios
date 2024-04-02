package com.previdencia.GestaoBeneficios.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.previdencia.GestaoBeneficios.models.Concessao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.net.*;
import java.time.LocalDate;

import org.json.JSONException;
import org.json.JSONObject;

import com.previdencia.GestaoBeneficios.TestAPI.TestAPI;
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
        int tempo = 0;
        double valor = 0;
        double contribuicao = 0;
        if(beneficioRepository.existsById(id) &&
                beneficioRepository.getReferenceById(id).isIndividual()==true){
	        /*String url="https://localhost:8080/contribuintes/consultar/"+id+"";
	        RestTemplate restTemplate = new RestTemplate();
	        JSONObject json = restTemplate.getForObject(url, JSONObject.class);
	        */
            JSONObject json = TestAPI.apiContribuicoes();
            try {
                tempo = json.getInt("tempoContribuicaoMeses");
                contribuicao = json.getInt("totalContribuidoAjustado");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Beneficio beneficio = beneficioRepository.getReferenceById(id);
            if(beneficio.getRequisitos() <= tempo) {
                valor = ((contribuicao * beneficio.getValor())/100);
                Concessao concessaoAutorizada = new Concessao(UUID.randomUUID(), cpf, cpf,
                        LocalDate.now(), valor, true, beneficio);
                concessaoRepository.save(concessaoAutorizada);
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }
            else {
                return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
            }

        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
        int tempo = 0;
        double valor = 0;
        double contribuicao = 0;
        if(beneficioRepository.existsById(id) &&
                beneficioRepository.getReferenceById(id).isIndividual()==false){
	        /*String url="https://localhost:8080/contribuintes/consultar/"+id+"";
	        RestTemplate restTemplate = new RestTemplate();
	        JSONObject json = restTemplate.getForObject(url, JSONObject.class);
	        */
            JSONObject json = TestAPI.apiContribuicoes();
            try {
                tempo = json.getInt("tempoContribuicaoMeses");
                contribuicao = json.getInt("totalContribuidoAjustado");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Beneficio beneficio = beneficioRepository.getReferenceById(id);
            if(beneficio.getRequisitos() <= tempo) {
                valor = ((contribuicao * beneficio.getValor())/100);
                Concessao concessaoAutorizada = new Concessao(UUID.randomUUID(),
                        cpfRequisitante, cpfBeneficiado,
                        LocalDate.now(), valor, true, beneficio);
                concessaoRepository.save(concessaoAutorizada);
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }
            else {
                return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
            }
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}