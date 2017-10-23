package org.camunda.bpm.acme;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
public class ContattaFornitore implements JavaDelegate {
	private final static Logger LOGGER = Logger.getLogger("CONTATTA FORNITORE");
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		/*
		 * Supplier API
		 */
		URL url = new URL("http://localhost:8000/ServiziAcmeREST/servizi/fornitore");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		LOGGER.info("I prodotti richiesti sono stati spediti dal fornitore");
	}
}
