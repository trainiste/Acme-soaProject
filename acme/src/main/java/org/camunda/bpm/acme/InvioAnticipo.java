package org.camunda.bpm.acme;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.util.json.JSONObject;
import java.util.logging.Logger;
public class InvioAnticipo implements JavaDelegate{
	private final static Logger LOGGER = Logger.getLogger("INVIO ANTICIPO");
	public void execute(DelegateExecution execution) throws Exception {
		String appoggio= (String) execution.getVariable("ordine");
		String id_acme= (String) execution.getVariable("id_acme");
		JSONObject ordine= new JSONObject(appoggio);
		double qta_anticipo = (Double)execution.getVariable("qta_anticipo");
		int costo = (int) ordine.get("prezzo");
		double prezzo= (double) costo;
		double anticipo= (prezzo/100)*qta_anticipo;
		ordine.put("anticipo_ricevuto", anticipo);
		LOGGER.info("ORDINE " + ordine.toString(3));
		execution.setVariable("ordine", ordine.toString());
		execution.getProcessEngineServices().getRuntimeService().createMessageCorrelation("MsgInvioAnticipo")
			.processInstanceId(id_acme)
			.setVariable("ordine", ordine.toString())
			.correlate(); 
		LOGGER.info("Anticipo versato: "+ ordine.get("anticipo_ricevuto"));
	};
}
