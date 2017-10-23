package org.camunda.bpm.acme;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import java.util.logging.Logger;
public class Notifica implements JavaDelegate {
	private final static Logger LOGGER = Logger.getLogger("NOTIFICA CUSTOMIZZAZIONI");
	public void execute(DelegateExecution execution) throws Exception {
		boolean accettato = (Boolean)execution.getVariable("esito");
		execution.setVariable("accettato", accettato);
		String id_rivendita=(String)execution.getVariable("id_rivendita");
		String id_acme= execution.getId();
		if (accettato == false)
			LOGGER.info("L'ordine e' stato rifiutato");
		else
			LOGGER.info("L'ordine e' stato accettato");
		execution.getProcessEngineServices().getRuntimeService().createMessageCorrelation("MsgNotifica")
		.processInstanceId(id_rivendita)
		.setVariable("id_acme", id_acme)
		.setVariable("accettato", accettato)
		.correlate(); 
	}
}
