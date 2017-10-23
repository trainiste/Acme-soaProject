package org.camunda.bpm.acme;
import java.util.logging.Logger;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.util.json.JSONObject;
public class InvioRisposta implements JavaDelegate {
    private final static Logger LOGGER = Logger.getLogger("ACCETTAZIONE PREVENTIVO");
	public void execute(DelegateExecution execution) throws Exception {
		String appoggio= (String) execution.getVariable("ordine");
		String id_acme= (String) execution.getVariable("id_acme");
		JSONObject ordine = new JSONObject(appoggio);
		boolean esitoprev=(boolean)execution.getVariable("esitoprev");
		LOGGER.info("Il prezzo da versare e' : "+ ordine.get("prezzo"));
		execution.setVariable("esitoprev", esitoprev);
		execution.getProcessEngineServices().getRuntimeService().createMessageCorrelation("MsgInvioRisp")
			.processInstanceId(id_acme)
			.setVariable("esitoprev", esitoprev)
			.correlate(); 
		if(esitoprev)
		{
			LOGGER.info("Il preventivo e' stato accettato dalla rivendita");
		}
		else
		{
			LOGGER.info("Il preventivo e' stato rifiutato dalla rivendita");
		}
	}
}
