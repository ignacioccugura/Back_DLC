package Logica.EndPoints;

import AccesoDatos.AccesoDatos;
import Logica.Clases.IteradorArchivos;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.File;
import java.sql.SQLException;

@Path("/indexar")
public class Indexador {

    @Inject
    AccesoDatos ad;
    @GET
    public Response Indexar() throws SQLException {

        File carpeta = new File("D:\\Descargas\\DocumentosTP1");
        for(File file : carpeta.listFiles()){
            IteradorArchivos.FiltrarDocumento(file,ad);
        }
        ad.Desconectar();

        String[] respuesta = new String[1];
        respuesta[0] = "Se cargaron correctamente los datos!";
        return Response.ok(respuesta).build();
    }
}