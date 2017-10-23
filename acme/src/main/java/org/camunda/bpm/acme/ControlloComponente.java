package org.camunda.bpm.acme;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.util.json.JSONArray;
import org.camunda.bpm.engine.impl.util.json.JSONObject;
public class ControlloComponente implements JavaDelegate {
	private final static Logger LOGGER = Logger.getLogger("CONTROLLO COMPONENTI");
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		String[] componenti = new String[6];
		String app= (String) execution.getVariable("ordine");
		JSONObject ordine= new JSONObject(app);
		JSONObject comp = ordine.getJSONObject("componenti");
		LOGGER.info("COMP \n" + ordine.toString(3));
		componenti[0]= (String) comp.get("modellobase");
		componenti[1]=(String) comp.get("colorazione");
		componenti[2]=(String) comp.get("freno");
		componenti[3]=(String) comp.get("sterzo");
		componenti[4]=(String) comp.get("ammortizzatori");
		componenti[5]=(String) comp.get("trasmissione");
		//Jolie call
		URL url = new URL("http://localhost:8004/retrieveCompMP");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		String appoggio=br.readLine();
		br.close();
		JSONObject compMagP= new JSONObject(appoggio);
		JSONArray rispostaMP= compMagP.getJSONArray("values");
		JSONArray ordineComp= new JSONArray();
		JSONObject ordineCompT=new JSONObject();
		JSONArray ordineForn= new JSONArray();
		JSONObject ordineFornT=new JSONObject();
		for (int i = 0; i < 6; i++) {
			for(int j=0; j< rispostaMP.length(); j++){
				JSONObject riga = rispostaMP.getJSONObject(j);
				if(componenti[i].equals(riga.get("Valore_Tipo"))){
					if(riga.get("Presente").equals(true)){
						JSONObject appoggio1=new JSONObject();
						appoggio1.put("tipo", riga.get("Tipo_componente"));
						appoggio1.put("componente", riga.get("Valore_Tipo"));
						appoggio1.put("id_magazzino", riga.get("id_magazzino"));
						ordineComp.put(appoggio1);
				}
				else
				{
					/*
					 * In the event that a component is not present within the princiaple
					 * warehouse, we will check the presence of the component within
					 * the secondary warehouses
					*/
					URL url2 = new URL("http://localhost:8004/retrieveCompMS?valore="+ riga.get("Valore_Tipo"));
					HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
					conn2.setRequestMethod("GET");
					conn2.setRequestProperty("Accept", "application/json");
					BufferedReader br2 = new BufferedReader(new InputStreamReader((conn2.getInputStream())));
					String appoggiox =br2.readLine();
					br2.close();
					JSONObject riga11= new JSONObject(appoggiox);
					JSONArray riga12= riga11.getJSONArray("values");
					JSONObject riga13= riga12.getJSONObject(1);
					if(componenti[i].equals(riga13.get("Valore_Tipo"))){
						if(riga13.get("Presente").equals(true)){
							JSONObject appoggio2=new JSONObject();
							appoggio2.put("tipo", riga.get("Tipo_componente"));
							appoggio2.put("componente", componenti[i]);
							appoggio2.put("id_magazzino", riga.get("id_magazzino"));
							ordineComp.put(appoggio2);
						}
						else{
							/*
							 * If the component is not present in the main warehouse
							 * or in the secondary warehouse then we add it in the
							 * order to be delivered to the supplier
							 */
							JSONObject appoggio3=new JSONObject();
							appoggio3.put("componente",componenti[i]);
							appoggio3.put("tipo", riga.get("Tipo_componente"));
							ordineForn.put(appoggio3);

						}
					}
					}
				}
			}
		}
		boolean checkOrdine;
		if(ordineComp.length()!=0){
			ordineCompT.put("valore", ordineComp);
			execution.setVariable("ordine_magazzini", ordineCompT.toString());
			checkOrdine=true;
			LOGGER.info("Ordine dei componenti presenti nei magazzini: \n" + ordineCompT.toString(3));
		}
		else
		{
			checkOrdine=false;
			execution.setVariable("ordine_magazzini", "");
		}
		execution.setVariable("checkOrdine", checkOrdine);
		boolean checkFornitore;
		if(ordineForn.length()!=0){
			ordineFornT.put("valore", ordineForn);
			execution.setVariable("ordine_fornitore", ordineFornT.toString());
			LOGGER.info("Ordine dei componenti da richiedere al fornitore: \n" + ordineFornT.toString(3));
			checkFornitore=true;
		}
		else{
			checkFornitore=false;
			execution.setVariable("ordine_fornitore", "");
		}
		execution.setVariable("checkFornitore", checkFornitore);
	}
}
