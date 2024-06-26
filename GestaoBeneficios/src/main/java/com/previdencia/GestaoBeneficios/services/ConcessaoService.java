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
    public double somar(Long cpf){
        List<Concessao> lista = concessaoRepository.findAllByRequisitante(cpf);
        double soma = 0;
        for(Concessao concessao : lista) {
            if(concessao.isStatus()){
                soma = soma + concessao.getValor();
            }
        }
        return soma;
    }

    /**
     * Desativa uma concessao ativa
     * @param uuid Id de identificacao da concessao
     * @return ResponseEntity com a confirmacao da desativacao
     * @since 1.1
     */
    public ResponseEntity<String> desativar(UUID uuid){
        try {
            Concessao concessao = concessaoRepository.getReferenceById(uuid);
            concessao.setStatus(false);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body("Concessao desativada com sucesso!");
        }
        catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("UUID nao localizado no banco de dados\n");
        }
    }

    /**
     * Metodo que recebe um cpf requerente de um beneficio e analisa e calculo se deve ou nao conceder o beneficio
     * @param cpf
     * @param id
     * @return
     * 		Http status 202 -> Pedido autorizado
     *  	Http status 405 -> Id nao aceito
     */
    public ResponseEntity<String> concederIndividual(Long cpf, Long id) {
        long tempo = 0;
        double valor;
        double contribuicao = 0;
        Beneficio beneficio = beneficioRepository.getReferenceById(id);
        List<Concessao> concessaoList = concessaoRepository.findAllByRequisitante(cpf);

        Boolean status= verificaBeneficio(1, id, beneficio);
        if(!status) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("O beneficio "+beneficio.getNome()+" não " +
                            "é apropriado para a chamada\n");
        }
        for (Concessao c : concessaoList){
            if(c.getBeneficio().getId() == id && c.isStatus()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("O beneficio "+beneficio.getNome()+" ja esta em " +
                                "uso pelo cpf "+cpf+"\n");
            }
        }

        String url="http://192.168.37.10:8080/contribuintes/consultar/" + cpf;
        RestTemplate restTemplate = new RestTemplate();


        String string = restTemplate.getForObject(url, String.class);
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Falha na conexao com API externas\n");
        }

        if(beneficio.getRequisitos() > tempo){
            System.out.println("\n\n\n\nTEMPO MINIMO NAO CUMPRIDO\n\n\n\n");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("Tempo para "+beneficio.getNome()+" insuficiente\n" +
                            "tempo minimo: "+ beneficio.getRequisitos()+"\n" +
                            "tempo de contribuicao:"+tempo+"\n");
        }

        valor = (contribuicao * beneficio.getValor())/100;
        Concessao concessaoAutorizada = new Concessao(UUID.randomUUID(), cpf, cpf,
                LocalDate.now(), valor, true, beneficio);
        concessaoRepository.save(concessaoAutorizada);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("Concessao Autorizada, UUID da operacao="
                        +concessaoAutorizada.getId()+"\n");
    }

    /**
     * Metodo para conceder beneficios para outra pessoa que nao seja o requisitante
     *
     * @param cpfRequisitante
     * @param id
     * @param cpfBeneficiado
     * @return Http status 202 -> Pedido autorizado,
     * Http status 405 -> Id nao aceito
     */
    public ResponseEntity<String> conceder(Long cpfRequisitante, Long id, Long cpfBeneficiado) {
        long tempo = 0;
        double valor = 0;
        double contribuicao = 0;
        Beneficio beneficio = beneficioRepository.getReferenceById(id);
        List<Concessao> concessaoList = concessaoRepository.findAllByRequisitante(cpfRequisitante);

        Boolean status= verificaBeneficio(2, id, beneficio);
        if(!status) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("O beneficio "+beneficio.getNome()+" não é apropriado para a chamada\n");
        }

        for (Concessao c : concessaoList){
            if(c.getBeneficio().getId() == id && c.isStatus()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("O beneficio "+beneficio.getNome()+" ja esta em " +
                                "uso pelo cpf "+cpfRequisitante+"\n");
            }
        }


        String url="http://192.168.37.10:8080/contribuintes/consultar/"+ cpfRequisitante;
        RestTemplate restTemplate = new RestTemplate();

        String string = restTemplate.getForObject(url, String.class);
        JsonObject json = new Gson().fromJson(string, JsonObject.class);

        try {
            tempo = json.get("tempoContribuicaoMeses").getAsLong();
            contribuicao = json.get("totalContribuidoAjustado").getAsDouble();
        } catch (Exception e) {
            System.out.println("\n\n\n\nJSON NAO GERADO:" +
                    "tempo:"+tempo+"\n" +
                    "contribuicao:"+contribuicao+"\n");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Falha na conexao com API externas\n");
        }

        if(beneficio.getRequisitos() > tempo){
            System.out.println("\n\n\n\nTEMPO MINIMO NAO CUMPRIDO\n\n\n\n");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("Tempo para "+beneficio.getNome()+" insuficiente\n" +
                            "tempo minimo: "+ beneficio.getRequisitos()+"\n" +
                            "tempo de contribuicao:"+tempo+"\n");
        }

        valor = ((contribuicao * beneficio.getValor())/100);
        Concessao concessaoAutorizada = new Concessao(UUID.randomUUID(),
                cpfRequisitante, cpfBeneficiado,
                LocalDate.now(), valor, true, beneficio);
        concessaoRepository.save(concessaoAutorizada);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("Concessao Autorizada, UUID da operacao="
                        +concessaoAutorizada.getId()+
                        "\n");
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
