package com.previdencia.GestaoBeneficios.TestAPI;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe que imita uma chamada API e retorna um JSON
 * @author Leonardo Fachinello Bonetti
 * @version 1.0
 *
 */
public class TestAPI {
	
	public static JSONObject apiContribuicoes() {
		
		JSONObject json = new JSONObject();
		
		try {
			json.put("cpf", "12345678910");
			json.put("categoria", "Empregado");
			json.put("tempoContribuicaoMeses", 50);
			json.put("totalContribuidoAjustado", 4754.97);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return json;
		
	}

}
