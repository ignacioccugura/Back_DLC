package Logica.EndPoints;

import AccesoDatos.AccesoDatos;
import AccesoDatos.Entities.Documento;
import AccesoDatos.Entities.Termino;
import Logica.Clases.Buscador;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.swing.text.Document;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.*;

@Path("/buscar")
public class Buscar {

    @Inject
    Buscador buscador;

    @GET
    @Path("/{cadena}")
    @Produces("text/json")
    public Response BuscarDocumentos_Consulta(@PathParam("cadena") String cadena) throws SQLException {
        return Response.ok(buscador.BuscarDocumentos(cadena)).build();
    }
}