package com.previdencia.GestaoBeneficios.services;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class ContribuicaoConnection implements Connection{

    @Override
    public String createGetRequest(String requisitante) {
        RestTemplate restTemplate = new RestTemplate();
        Dotenv dotenv = Dotenv.load();
        String apiConnection=dotenv.get("HOST");
        String url= "http://"+apiConnection+"/contribuintes/consultar/"+requisitante;
        try {
            return restTemplate.getForObject(url, String.class);
        }
        catch (RestClientException e) {
            return null;
        }
    }

    public String giveHost(){
        Dotenv dotenv = Dotenv.load();
        String apiConnection=dotenv.get("HOST");
        return apiConnection;
    }
}
