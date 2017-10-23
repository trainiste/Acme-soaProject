package com.serviziREST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;
@Path("/fornitore")
public class FornitoreEsterno {
	@GET
	@Produces("application/json")
	public Response contattaFornitore() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("risposta", "true");
		return Response.status(200).entity(jsonObject.toString()).build();
}
}
