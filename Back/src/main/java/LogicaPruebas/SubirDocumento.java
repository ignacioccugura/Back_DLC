package LogicaPruebas;

import AccesoDatos.AccesoDatos;
import AccesoDatos.Entities.Termino;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

@Path("/subir_documento/{nombreDoc}")
public class SubirDocumento {
    @POST
    public Response subirDoc(@PathParam("nombreDoc") String nombreDoc,File file) throws IOException {
        File arch = new File("D:\\Descargas\\DocumentosTP1\\" + nombreDoc);
        file.renameTo(arch);

        AccesoDatos ad = new AccesoDatos();
        EntityManager em = ad.getEntityManager();
        String palabra;
        int cPosteos=0;
        Scanner sc = new Scanner(new BufferedReader(new FileReader(file)));

        HashMap<String,Termino> tablaHash = new HashMap<String, Termino>(40000);

        while(sc.hasNext()){
            palabra = sc.next().replaceAll("[^a-zA-Z]", "").trim().toLowerCase();
            if(palabra.length() < 3){
                continue;
            }
            Termino termino;

            if(tablaHash.containsKey(palabra)){


                termino = tablaHash.get(palabra);
                termino.incrementarFrecuenciaRelativa();
                tablaHash.put(palabra, termino);

            }
            else {
                cPosteos++;
                termino = new Termino(palabra, nombreDoc);
                tablaHash.put(palabra,termino);
                System.out.println("cantidad de posteos: " + cPosteos);


            }
        }
        Iterator<Map.Entry<String,Termino>> it = tablaHash.entrySet().iterator();
        em.getTransaction().begin();


        while(it.hasNext())
        {


            Termino t = it.next().getValue();
            em.persist(t);


        }


        em.getTransaction().commit();
        return Response.ok(cPosteos).header("Access-Control-Allow-Origin","*").build();
    }
}