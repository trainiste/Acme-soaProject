package com.serviziREST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;
/*
 * https://en.wikipedia.org/wiki/Great-circle_distance
 * http://www.hwupgrade.it/forum/showthread.php?t=1788774
 */
@Path("/calcolodistanzegeo")
public class CalcoloDistanzeGeografiche {
	@GET
	@Produces("application/json")
	public Response getUsers(
			@QueryParam("lat1") double lat1,
			@QueryParam("lon1") double lon1,
			@QueryParam("lat2") double lat2,
			@QueryParam("lon2") double lon2) throws JSONException{
			JSONObject distanza= new JSONObject();
		        lat1 = lat1*Math.PI / 180;
		        lon1 = lon1*Math.PI / 180;
		        lat2 = lat2*Math.PI / 180;
		        lon2 = lon2*Math.PI / 180;
		        double dist_long = lon2 - lon1;
		        double dist_lat = lat2 - lat1;
		        double pezzo1 = Math.cos(lat2)*Math.sin(dist_long);
		        double pezzo11 = pezzo1*pezzo1;
		        double pezzo2 = Math.cos(lat1)*Math.sin(lat2)-Math.sin(lat1)*Math.cos(lat2)*Math.cos(dist_long);
		        double pezzo22 = pezzo2*pezzo2;
		        double pezzo3 = Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(dist_long);
		        double pezzo4 = Math.atan((Math.sqrt(pezzo11+pezzo22))/pezzo3);
		       distanza.put("distanza", pezzo4*6372);
			return Response
			   .status(200)
			   .entity(distanza.toString()).build();
		}
}
