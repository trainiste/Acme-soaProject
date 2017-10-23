package org.camunda.bpm.acme;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.util.json.JSONObject;
import java.util.logging.Logger;
public class InvioPreventivo implements JavaDelegate{
	private final static Logger LOGGER = Logger.getLogger("INVIO PREVENTIVO");
	public void execute(DelegateExecution execution) throws Exception {
		double prezzo = (Double)execution.getVariable("prezzo");
		double sconto = (Double)execution.getVariable("sconto");
		double prezzoscontato = prezzo -((prezzo*sconto)/100);
		String appoggio= (String)execution.getVariable("ordine");
		JSONObject ordine= new JSONObject(appoggio);
		ordine.put("prezzo", prezzoscontato);
		String id_rivendita= (String)execution.getVariable("id_rivendita");
		execution.setVariable("ordine", ordine);
		LOGGER.info("ORDINE \n" + ordine.toString(3));
		execution.getProcessEngineServices().getRuntimeService().createMessageCorrelation("MsgInvioPrev")
			.processInstanceId(id_rivendita)
			.setVariable("ordine", ordine.toString())
			.correlate(); 
		if (sconto>0)
		{
			LOGGER.info("Ordine accettato, la somma da versare e' "+ prezzoscontato + " al netto dello sconto del " + sconto + " %.");
		}
		else
		{
			LOGGER.info("Ordine accettato, non sono previsti sconti per questo ordine quindi la somma da versare e' "+ prezzoscontato);
		}
	}
}
