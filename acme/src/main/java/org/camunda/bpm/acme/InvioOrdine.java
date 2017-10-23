package org.camunda.bpm.acme;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.util.json.JSONObject;
import java.util.logging.Logger;
public class InvioOrdine implements JavaDelegate {
		private final static Logger LOGGER = Logger.getLogger("INVIO ORDINE");
		public void execute(DelegateExecution execution) throws Exception {
			String rivendita=(String) execution.getVariable("rivendita");
			String colore=(String) execution.getVariable("colorazione");
			String modellobase=(String) execution.getVariable("modellobase");
			String freno=(String) execution.getVariable("freno");
			String sterzo=(String) execution.getVariable("sterzo");
			String ammortizzatori=(String) execution.getVariable("ammortizzatori");
			String trasmissione=(String) execution.getVariable("trasmissione");
			String citta=(String) execution.getVariable("citta");
			boolean accessorio_1=(boolean) execution.getVariable("accessorio1");
			boolean accessorio_2=(boolean) execution.getVariable("accessorio2");
			boolean accessorio_3=(boolean) execution.getVariable("accessorio3");
			String accessorio1 = null;
			String accessorio2=null;
			String accessorio3= null;
			if (accessorio_1){
				accessorio1="campanello";
				}
			else{
				accessorio1="";
			}
			if (accessorio_2){
				accessorio2="cestino";
				}
			else{
				accessorio2="";
				}
			if (accessorio_3){
				accessorio3="cavalletto";
				}
			else{
				accessorio3="";
				}
			JSONObject componenti= new JSONObject();
			JSONObject ordine= new JSONObject();
			JSONObject accessori=new JSONObject();
			ordine.put("rivendita", rivendita);
			ordine.put("citta", citta);
			accessori.put("accessorio1", accessorio1);
			accessori.put("accessorio2", accessorio2);
			accessori.put("accessorio3", accessorio3);
			componenti.put("modellobase", modellobase);
			componenti.put("colorazione", colore);
			componenti.put("freno", freno);
			componenti.put("sterzo", sterzo);
			componenti.put("ammortizzatori", ammortizzatori);
			componenti.put("trasmissione", trasmissione);
			ordine.put("componenti", componenti);
			ordine.put("accessori", accessori);
			LOGGER.info("ORDINE: \n" + ordine.toString(3));
			String id_rivendita= execution.getId();
			execution.getProcessEngineServices().getRuntimeService().createMessageCorrelation("MsgOrdine")
			.setVariable("id_rivendita", id_rivendita)
			.setVariable("ordine", ordine.toString())
			.correlate();
			LOGGER.info("L'ordine della rivendita " + rivendita + " e' composto dal ciclo " + modellobase + " di colore "+ colore +
					" con le seguenti customizzazioni: \n" + freno + ", " + sterzo + ", "+ ammortizzatori + ", "+ trasmissione +". \n"
					+ "Inoltre l'ordine prevede anche i seguenti accessori: \n" + accessorio1 + ", "+accessorio2 + ", "+ accessorio3 + ".");
		};
}
