package Logica.EndPoints;

import AccesoDatos.AccesoDatos;
import Logica.Clases.IteradorArchivos;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.*;

@Path("/subir_documento/{nombreDoc}")
public class SubirDocumento {
    @Inject
    AccesoDatos ad;

    @POST
    public Response subirDoc(@PathParam("nombreDoc") String nombreDoc,File file) throws IOException {
        File arch = new File("D:\\Descargas\\DocumentosTP1\\" + nombreDoc);
        if(arch.exists()){
            return Response.status(418,"El archivo ya existe en la base de datos").build();
        }
        file.renameTo(arch);

        IteradorArchivos.FiltrarDocumento(arch,ad);
        return Response.ok().build();
    }
}