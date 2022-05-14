package LogicaPruebas;

import AccesoDatos.AccesoDatos;
import AccesoDatos.Entities.Termino;

import javax.persistence.EntityManager;
import java.io.*;

import java.sql.SQLException;
import java.util.*;

public class Indexacion {
    public static void main() throws FileNotFoundException, SQLException {
        //Corroborar que la cantidad de archivos en base sean la misma cantidad que en el documento.
        AccesoDatos ad = new AccesoDatos();
        EntityManager em = ad.getEntityManager();

        int c = 0;
        String palabra;

        int cPosteos=0;

        File carpeta = new File("D:\\Descargas\\DocumentosTP1");



        //em.getTransaction().begin();
        for (File archivoLeer : carpeta.listFiles()) {
            System.out.println(archivoLeer.getName() + ":" + c);
            c++;
            HashMap<String,Termino> tablaHash = new HashMap<String, Termino>(40000);


            try(Scanner sc = new Scanner(new BufferedReader(new FileReader(archivoLeer))))
            {

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
                        termino = new Termino(palabra, archivoLeer.getName());
                        tablaHash.put(palabra,termino);
                        System.out.println("cantidad de posteos: " + cPosteos);


                    }

                }


            }

            catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();}

            Iterator<Map.Entry<String,Termino>> it = tablaHash.entrySet().iterator();
            em.getTransaction().begin();


            while(it.hasNext())
            {


                Termino t = it.next().getValue();
                em.persist(t);


            }


            em.getTransaction().commit();
        }

        System.out.println("cantidad de posteos: " + cPosteos);

    }
}
