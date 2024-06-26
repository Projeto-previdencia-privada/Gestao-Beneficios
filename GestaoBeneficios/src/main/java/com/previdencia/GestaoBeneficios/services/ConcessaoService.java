package com.previdencia.GestaoBeneficios.services;

import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.previdencia.GestaoBeneficios.dto.ConcessaoPedidoDTO;
import com.previdencia.GestaoBeneficios.models.Concessao;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
public class ConcessaoService{

    private final ConcessaoRepository concessaoRepository;

    private final BeneficioRepository beneficioRepository;

    private final ContribuicaoConnection connection;

    @Autowired
    public ConcessaoService(ConcessaoRepository concessaoRepository,
                             BeneficioRepository beneficioRepository,
                            ContribuicaoConnection connection) {
        this.concessaoRepository = concessaoRepository;
        this.beneficioRepository = beneficioRepository;
        this.connection = connection;
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
    public ResponseEntity<String> somar(String cpf){
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
        if(!concessaoRepository.existsByUuid(uuid)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("UUID nao localizado no banco de dados\n");
        }
        Concessao concessao = concessaoRepository.findByUuid(uuid);
        concessao.setStatus(false);
        concessaoRepository.save(concessao);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .build();
    }

    /**
     * Metodo que recebe um cpf requerente de um beneficio e analisa e calculo se deve ou nao conceder o beneficio
     * @param concessao
     * @return
     * 		Http status 202 -> Pedido autorizado
     *  	Http status 405 -> Id nao aceito
     */
    public ResponseEntity<String> conceder(@NotNull ConcessaoPedidoDTO concessao) {
        long tempo = 0;
        double valor;
        String cpfRequisitante= concessao.getRequisitante();
        String cpfBeneficiado= concessao.getBeneficiado();
        double contribuicao = 0;
        Beneficio beneficio = procuraBeneficio(concessao.getBeneficioNome());

        if(beneficio == null){
            return ResponseEntity.notFound().build();
        }

        String json_connection = connection.createGetRequest(cpfRequisitante);
        System.out.println("JSON CONNECTION ======= "+json_connection);
        JsonObject json = new Gson().fromJson(json_connection, JsonObject.class);
        if(json == null){
            return ResponseEntity.internalServerError().build();
        }
        try {
            tempo = json.get("tempoContribuicaoMeses").getAsLong();
            contribuicao = json.get("totalContribuidoAjustado").getAsDouble();
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body("Falha na obtencao do JSON\n");
        }
        if(!verificaBeneficio(beneficio, tempo)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Tempo para "+beneficio.getNome()+" insuficiente\n" +
                            "tempo minimo: "+ beneficio.getTempoMinimo()+"\n" +
                            "tempo de contribuicao:"+tempo+"\n");
        }

        valor = calculaValor(contribuicao, beneficio.getValorPercentual());
        adicionar(cpfRequisitante,cpfBeneficiado,valor,beneficio);
        return ResponseEntity.accepted().build();
    }


    public boolean adicionar(String cpfRequisitante,
                                            String cpfBeneficiado, double valor,
                                            Beneficio beneficio){

        Concessao concessaoAutorizada = new Concessao(UUID.randomUUID(),
                cpfRequisitante, cpfBeneficiado, LocalDate.now(), valor,
                true, beneficio);

        concessaoRepository.save(concessaoAutorizada);
        return true;
    }


    public Beneficio procuraBeneficio(String nome){
        if(!beneficioRepository.existsByNome(nome)) {
            return null;
        }
        return beneficioRepository.findByNome(nome);
    }

    public Double calculaValor(double contribuicao, double valorPercentual){
        return ((contribuicao * valorPercentual)/100);
    }

    public boolean verificaBeneficio(Beneficio beneficio, Long tempo){
        if(beneficio.getTempoMinimo() > tempo || beneficio.isStatus()== false){
            return false;
        }
        return true;
    }
}
