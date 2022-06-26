package Logica.EndPoints;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.File;

@Path("/descargar_documento")
public class DescargarDoc {
    @GET
    @Produces("text/plain")
    @Path("/{archivo}")
    public Response descargaDocumento(@PathParam("archivo") String archivo) {
        File arch = new File("D:\\Descargas\\DocumentosTP1\\" + archivo);
        return Response.ok(arch).header("content-disposition", "attachment; filename=" + archivo).build();
    }
}