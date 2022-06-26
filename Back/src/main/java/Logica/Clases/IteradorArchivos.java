package Logica.Clases;

import AccesoDatos.AccesoDatos;
import AccesoDatos.Entities.Termino;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class IteradorArchivos {

    public static void FiltrarDocumento(File file, AccesoDatos ad){

        String palabra;
        HashMap<String, Termino> tablaHash = new HashMap<String, Termino>(40000);

        try(Scanner sc = new Scanner(new BufferedReader(new FileReader(file))))
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
                    termino = new Termino(palabra, file.getName());
                    tablaHash.put(palabra,termino);
                }

            }

        }

        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();}

        CrearPosteos(tablaHash,ad);
    }

    private static void CrearPosteos(HashMap<String,Termino> tablahash, AccesoDatos ad){
        Iterator<Map.Entry<String,Termino>> it = tablahash.entrySet().iterator();
        ad.IniciarTransaccion();

        while(it.hasNext())
        {
            Termino t = it.next().getValue();
            ad.Guardar(t);
        }

        ad.FinalizarTransaccion();
    }
}
