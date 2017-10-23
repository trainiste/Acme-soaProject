include "console.iol"
include "database.iol"
include "string_utils.iol"
include "StorageInterface.iol"

execution { concurrent }

inputPort Server {
	Location: "socket://localhost:8004/"
	Protocol: http { .format = "json" }
	Interfaces: StorageInterface
}

init
{
	with (connectionInfo) {
		.username = "root";
		.password = "";
		.host = "127.0.0.1";
		.port = 3306;
		.database = "AcmeDB"; 
		.driver = "mysql"
	};

	connect@Database(connectionInfo)();
	println@Console("connected")()

}

main
{
	[ retrieveCompMP(request)(response) {
		query@Database(
			"select magazzini.Id as id_magazzino,
					componenti.Id as Id_Componente,
					componenti.Tipo as Tipo_componente,
					componenti.Valore as Valore_Tipo,
					magazzini.Nome as Nome_Magazzino,
					cm.Presente
			 from magazzini, cm, componenti
			 where magazzini.Id = cm.Magazzini_Id and
			 	   cm.Componenti_id = componenti.Id and
			 	   magazzini.Tipo ='P'"
		)(sqlResponse);
		response.values -> sqlResponse.row
	} ]

	[ retrieveCompMS(request)(response) {
		query@Database(
			"select magazzini.Id as id_magazzino,
					componenti.Id as Id_Componente,
					componenti.Tipo as Tipo_componente,
					componenti.Valore as Valore_Tipo,
					cm.Presente
			 from magazzini, cm, componenti
			 where magazzini.Id = cm.Magazzini_Id and
			 	   cm.Componenti_id = componenti.Id and
			 	   magazzini.Tipo ='S' and
			 	   componenti.Valore =:valore"{
				.valore = request.valore
			}
		)(sqlResponse);
		response.values -> sqlResponse.row
	} ]

	[ retrieveA(request)(response) {
		query@Database(
			"select magazzini.Id as Id_Magazzino,
					accessori.Id as Id_Accessori,
					accessori.Nome as Nome_Accessorio,
					am.Presente, 
					magazzini.Latitudine, 
					magazzini.Longitudine,
					magazzini.Nome as Citta
			 from magazzini, am, accessori
			 where magazzini.Id = am.Magazzini_Id and am.Accessori_id = accessori.Id"
		)(sqlResponse);
		response.values -> sqlResponse.row
	} ]
}
