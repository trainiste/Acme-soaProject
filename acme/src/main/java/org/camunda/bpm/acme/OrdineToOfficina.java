package org.camunda.bpm.acme;
import java.util.logging.Logger;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.util.json.JSONObject;
public class OrdineToOfficina implements JavaDelegate {
	private final static Logger LOGGER = Logger.getLogger("INVIO ORDINE ASSEMBLAGGIO");
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		String ordineF= (String) execution.getVariable("ordine_fornitore");
		String ordineMag= (String) execution.getVariable("ordine_magazzini");
		String id_acme= (String) execution.getVariable("id_acme");
		JSONObject ordine= new JSONObject();
		if(!ordineF.equals("")){
			JSONObject ordine_fornitore= new JSONObject(ordineF);
			ordine.put("ordine_fornitore",ordine_fornitore);
		}
		else{
			ordine.put("ordine_fornitore","Nessun componente da ordinare al fornitore");
		}
		if(!ordineMag.equals("")){
			JSONObject ordine_magazzini= new JSONObject(ordineMag);
			ordine.put("ordine_magazzini", ordine_magazzini);
		}
		else{
			ordine.put("ordine_magazzini", "Componenti da ordinare");
		}
		LOGGER.info("L'ordine assemblaggio inviato all'officina: \n" + ordine.toString(3));
		RuntimeService runtimeService=execution.getProcessEngineServices().getRuntimeService();
		runtimeService.createMessageCorrelation("MessageFlow_1q9yvfx")
		.setVariable("id_acme", id_acme)
		.setVariable("ordine_assemblaggio", ordine.toString())
		.correlate();
	}
}
