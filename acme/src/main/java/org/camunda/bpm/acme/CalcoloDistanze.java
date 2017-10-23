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
public class CalcoloDistanze implements JavaDelegate{
	private final static Logger LOGGER = Logger.getLogger("CALCOLO DISTANZE GEOGRAFICHE");
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		String accessoriS= (String) execution.getVariable("ordineAcc");
		String app=(String) execution.getVariable("ordine");
		JSONObject ordine= new JSONObject(app);
		String cittaR= (String) ordine.get("citta");
		/*
		 * Google Maps API call for calculate distance
		 */
		URL url = new URL("http://maps.googleapis.com/maps/api/geocode/json?address=" + cittaR);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		StringBuilder everything = new StringBuilder();
		String line;
		while((line = br.readLine()) != null) {
		       everything.append(line);
		}
		String appoggio=everything.toString();
		br.close();
		/*
		 * Parsing response
		 */
		JSONObject jsonCitta= new JSONObject(appoggio);
		JSONArray results= jsonCitta.getJSONArray("results");
		JSONObject primo_elemento=results.getJSONObject(0);
		JSONObject geometry= primo_elemento.getJSONObject("geometry");
		JSONObject location=geometry.getJSONObject("location");
		double latR=(double) location.get("lat");
		double lonR=(double) location.get("lng");
		LOGGER.info("La citta' della rivendita e' " + cittaR + ", le cui coordinate sono : \n" + "Latitudine: " + latR + "\n" + "Longitudine: "+ lonR);
		/*
		 * JSON costructor
		 */
		JSONObject jsonAcc= new JSONObject(accessoriS);
		JSONArray arrAcc= jsonAcc.getJSONArray("valore");
		JSONArray distanze= new JSONArray();
		for(int i=0; i< arrAcc.length(); i++){
			JSONObject elemento= arrAcc.getJSONObject(i);
			double latA=(double) elemento.get("lat");
			double lonA=(double) elemento.get("lon");
			URL url1 = new URL("http://localhost:8000/ServiziAcmeREST/servizi/calcolodistanzegeo?lat1="+ latA +"&lon1="+ lonA + "&lat2="+ latR +"&lon2="+ lonR);
			HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
			conn1.setRequestMethod("GET");
			conn1.setRequestProperty("Accept", "application/json");
			BufferedReader br1 = new BufferedReader(new InputStreamReader((conn1.getInputStream())));
			String appoggio1=br1.readLine();
			br1.close();
			JSONObject rispostaCalcGeo= new JSONObject(appoggio1);
			rispostaCalcGeo.put("id_magazzino", elemento.get("id_magazzino"));
			rispostaCalcGeo.put("nome_accessorio", elemento.get("nome_accessorio"));
			rispostaCalcGeo.put("citta", elemento.get("citta"));
			distanze.put(rispostaCalcGeo);
		}
		JSONObject risultato= new JSONObject();
		String[] accessori = new String[3];
		JSONObject acc= ordine.getJSONObject("accessori");
		accessori[0]=(String) acc.get("accessorio1");
		accessori[1]=(String) acc.get("accessorio2");
		accessori[2]=(String) acc.get("accessorio3");
		/*
		 * Min distance
		 */
		for(int i=0; i<3; i++){
			double dist_min=-1;
			int id_mag=0;
			String citta="";
			for(int j=0; j< distanze.length(); j++){
				JSONObject elemDistanze= distanze.getJSONObject(j);
				if(elemDistanze.get("nome_accessorio").equals(accessori[i])){
					double distanza= (double) elemDistanze.get("distanza");
					if(dist_min==-1 || dist_min > distanza){
						dist_min=distanza;
						id_mag=(int) elemDistanze.get("id_magazzino");
						citta= (String) elemDistanze.get("citta");
					}
				}
			}
			if (dist_min!= -1){
				JSONObject accessorio = new JSONObject();
				accessorio.put("distanza_min", dist_min);
				accessorio.put("citta", citta);
				accessorio.put("id_magazzino", id_mag);
				risultato.put(accessori[i], accessorio);
			}
		}
		LOGGER.info("Gli accessori verranno spediti dai seguenti magazzini: \n" + risultato.toString(3));
		execution.setVariable("ordineAccMagVicini", risultato.toString());
	}
}
