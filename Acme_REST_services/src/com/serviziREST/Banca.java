package com.serviziREST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;
@Path("/banca")
	public class Banca {
		public static boolean getRandom()
		{
			return Math.random() < 0.90;
		}
		@GET
		@Produces("application/json")
		public Response paytobank() throws JSONException {
			JSONObject jsonObject = new JSONObject();
			if(getRandom())
			{
				jsonObject.put("controllo_pagamento", true);
			}
			else
			{
				jsonObject.put("controllo_pagamento", false);
			}
		return Response.status(200).entity(jsonObject.toString()).build();
	}
	}
