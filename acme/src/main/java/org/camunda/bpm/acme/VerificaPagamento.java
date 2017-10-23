package org.camunda.bpm.acme;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.util.json.JSONObject;
public class VerificaPagamento  implements JavaDelegate{
	private final static Logger LOGGER = Logger.getLogger("VERIFICA PAGAMENTO");
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		URL url = new URL("http://localhost:8000/ServiziAcmeREST/servizi/banca");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		String prova=br.readLine();
		br.close();
		JSONObject jsonObj = new JSONObject(prova);
	    boolean pagamento= (boolean) jsonObj.get("controllo_pagamento");
	    execution.setVariable("pagamento", pagamento );
	    if(pagamento){
			LOGGER.info("Il pagamento e' stato effettuato");
	    }
	    else
	    {
			LOGGER.info("Il pagamento non e' stato effettuato");
	    }
	}
}
