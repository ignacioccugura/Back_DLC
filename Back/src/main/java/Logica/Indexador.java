package Logica;

import AccesoDatos.AccesoDatos;
import LogicaPruebas.Indexacion;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;

@Path("/indexar")
public class Indexador {

    @Inject
    AccesoDatos ad;

    @Produces("application/json")
    @GET
    public Response Indexar() throws SQLException {

        File carpeta = new File("D:\\Descargas\\DocumentosTP1");
        for(File file : carpeta.listFiles()){
            IteradorArchivos.FiltrarDocumento(file,ad);
        }
        ad.Desconectar();
        return Response.ok().build();

    }
}