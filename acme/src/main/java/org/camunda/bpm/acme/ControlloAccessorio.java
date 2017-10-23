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
public class ControlloAccessorio implements JavaDelegate {
	private final static Logger LOGGER = Logger.getLogger("CONTROLLO ACCESSORI");
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		String[] accessori = new String[3];
		String app= (String) execution.getVariable("ordine");
		JSONObject ordine= new JSONObject(app);
		JSONObject acc= ordine.getJSONObject("accessori");
		accessori[0]=(String) acc.get("accessorio1");
		accessori[1]=(String) acc.get("accessorio2");
		accessori[2]=(String) acc.get("accessorio3");
		URL url = new URL("http://localhost:8004/retrieveA");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		String appoggio=br.readLine();
		br.close();
		/*
		 * JSON constructor
		 */
		JSONObject ordineAcc= new JSONObject(appoggio);
		JSONArray ordineAccArr= ordineAcc.getJSONArray("values");
		JSONObject ordineAccFinale= new JSONObject();
		JSONArray ordineAccArrFinale= new JSONArray();
		JSONObject ordineAccForn=new JSONObject();
		JSONArray ordineAccArrForn=new JSONArray();
		/*
		 * We check the presence of accessories ordered from the resale in our warehouses.
		 + If they are present, we are going to create a JSON containing the information about
		 + the accessories and warehouses that they own. In the event that the warehouses
		 + do not have such accessories, we are going to create the JSON of the order to be made
		 + to the supplier
		 */
		for(int i=0; i<3; i++){
			int trovato=0;
			if(!accessori[i].equals("")){
				for(int j=0; j<ordineAccArr.length(); j++){
					JSONObject riga = ordineAccArr.getJSONObject(j);
					if(accessori[i].equals(riga.get("Nome_Accessorio"))){
						if( riga.get("Presente").equals(true) ){
							JSONObject appoggio1= new JSONObject();
							appoggio1.put("nome_accessorio", accessori[i]);
							appoggio1.put("id_magazzino", riga.get("Id_Magazzino"));
							appoggio1.put("lat", riga.get("Latitudine"));
							appoggio1.put("lon", riga.get("Longitudine"));
							appoggio1.put("citta", riga.get("Citta"));
							ordineAccArrFinale.put(appoggio1);
						}
						else{
							trovato++;
						}
						if(trovato==3){
							JSONObject appoggio2=new JSONObject();
							appoggio2.put("nome_accessorio", accessori[i]);
							ordineAccArrForn.put(appoggio2);
						}
					}
				}
			}
		}
		boolean checkAccessoriF;
		boolean checkAccessori;
		if(ordineAccArrForn.length()!=0){
			checkAccessoriF=true;
			ordineAccForn.put("valore", ordineAccArrForn);
			execution.setVariable("ordineAccForn", ordineAccForn.toString());
			LOGGER.info("Ordine degli accessori da richiedere al fornitore: \n" + ordineAccForn.toString(3));
		}
		else{
			checkAccessoriF=false;
		}
		execution.setVariable("checkAccF", checkAccessoriF);
		if(ordineAccArrFinale.length()!=0){
			checkAccessori=true;
			ordineAccFinale.put("valore", ordineAccArrFinale);
			execution.setVariable("ordineAcc", ordineAccFinale.toString());
			LOGGER.info("Ordine degli accessori presenti nei magazzini: \n" + ordineAccFinale.toString(3));
		}
		else{
			checkAccessori=false;
		}
		execution.setVariable("checkAcc", checkAccessori);
	}
}
