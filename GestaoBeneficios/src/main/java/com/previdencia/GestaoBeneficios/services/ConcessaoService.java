package com.previdencia.GestaoBeneficios.services;

import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.previdencia.GestaoBeneficios.models.Concessao;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import com.previdencia.GestaoBeneficios.models.Beneficio;
import com.previdencia.GestaoBeneficios.repository.BeneficioRepository;
import com.previdencia.GestaoBeneficios.repository.ConcessaoRepository;

/**
 * Servico da classe Concessao
 *
 * @version 1.1
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
    public ResponseEntity<String> somar(Long cpf){
        List<Concessao> lista = concessaoRepository.findAllByRequisitante(cpf);
        double soma = 0;
        for(Concessao concessao : lista) {
            if(concessao.isStatus()){
                soma = soma + concessao.getValor();
            }
        }
        return ResponseEntity.ok().body(String.valueOf(soma));
    }

    /**
     * Desativa uma concessao ativa
     * @param uuid Id de identificacao da concessao
     * @return ResponseEntity com a confirmacao da desativacao
     * @since 1.1
     */
    public ResponseEntity<String> desativar(UUID uuid){
        if(!concessaoRepository.existsById(uuid)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("UUID nao localizado no banco de dados\n");
        }

        Concessao concessao = concessaoRepository.getReferenceById(uuid);
        concessao.setStatus(false);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("Concessao desativada com sucesso!");
    }

    /**
     * Remove uma concessao
     * @param uuid Id de identificacao da concessao
     * @return ResponseEntity confirmando o removimento
     * @since 1.1
     */
    public ResponseEntity<String> remover(UUID uuid){
        if(!concessaoRepository.existsById(uuid)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("UUID nao localizado no banco de dados\n");
        }

        concessaoRepository.deleteById(uuid);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("Concessao removida com sucesso!");
    }

    /**
     * Metodo que recebe um cpf requerente de um beneficio e analisa e calculo se deve ou nao conceder o beneficio
     * @param cpfBeneficiado
     * @param cpfRequisitante
     * @param id
     * @return
     * 		Http status 202 -> Pedido autorizado
     *  	Http status 405 -> Id nao aceito
     */
    public ResponseEntity<String> conceder(Long cpfRequisitante, Long id,
                                           Long cpfBeneficiado, int op) {
        long tempo = 0;
        double valor;
        double contribuicao = 0;
        Beneficio beneficio = beneficioRepository.getReferenceById(id);

        if(!beneficioRepository.existsById(id) ||
                (beneficio.isIndividual() && op == 2) ||
                (!beneficio.isIndividual() && op == 1)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("O beneficio "+beneficio.getNome()+
                            " não é apropriado para a chamada\n");
        }

        String url="${IP}/contribuintes/consultar/{cpf}";
        RestTemplate restTemplate = new RestTemplate();


        String string = restTemplate.getForObject(url, String.class, cpfRequisitante);
        JsonObject json = new Gson().fromJson(string, JsonObject.class);


        try {
            tempo = json.get("tempoContribuicaoMeses").getAsLong();
            contribuicao = json.get("totalContribuidoAjustado").getAsDouble();
        }
        catch (Exception e) {
            System.out.println("\n\n\n\nJSON NAO GERADO:" +
                    "tempo:"+tempo+"\n" +
                    "contribuicao:"+contribuicao+"\n");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body("Falha na conexao com API externas\n");
        }

        if(beneficio.getTempoMinimo() > tempo){
            System.out.println("\n\n\n\nTEMPO MINIMO NAO CUMPRIDO\n\n\n\n");
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Tempo para "+beneficio.getNome()+" insuficiente\n" +
                            "tempo minimo: "+ beneficio.getTempoMinimo()+"\n" +
                            "tempo de contribuicao:"+tempo+"\n");
        }


        valor = (contribuicao * beneficio.getValorPercentual())/100;
        Concessao concessaoAutorizada = new Concessao(UUID.randomUUID(),
                cpfRequisitante, cpfBeneficiado, LocalDate.now(), valor,
                true, beneficio);
        concessaoRepository.save(concessaoAutorizada);

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("Concessao Autorizada,\nUUID da operacao="
                        +concessaoAutorizada.getId()+"\n");
    }

}
