package LogicaPruebas;

import AccesoDatos.AccesoDatos;
import AccesoDatos.Entities.Documento;
import AccesoDatos.Entities.Termino;

import javax.persistence.EntityManager;
import java.util.*;

public class Buscador {

    double[] vectorConsulta;
    List<Documento> documentosRankeados;


    public void BuscarDocumentos() {

        AccesoDatos ad = new AccesoDatos();
        EntityManager em = ad.getEntityManager();

        //int cantDocs = (Integer) em.createNativeQuery("select count(distinct documento) from dlc.posteos").getSingleResult();
        //System.out.println(cantDocs);
        String consulta = "tobeornottobe";
        consulta = consulta.replaceAll("[^a-zA-Z0-9]", " ").toLowerCase();
        String[] vPalabras = consulta.split(" ");

        String consultaSQL;

        vectorConsulta = new double[vPalabras.length];
        documentosRankeados = new ArrayList<>();
        documentosRankeados.add(new Documento(0,"x"));
        System.out.println(documentosRankeados.size());

        HashMap<String,double[]> documentos = new HashMap<>();

        for (int i = 0; i < vPalabras.length; i++) {

            consultaSQL = "Select * from posteos where termino like '" + vPalabras[i] + "' order by frecuencia desc";
            List<Termino> terminos = em.createNativeQuery(consultaSQL,Termino.class).getResultList();


            for (int j = 0; j < terminos.size(); j++) {
                double peso = terminos.get(j).CalcularPeso(terminos.size(), 593);

                if(peso == 0){
                    continue;
                }

                if(documentos.containsKey(terminos.get(j).getDocumento())){
                    documentos.get(terminos.get(j).getDocumento())[i] = peso;
                }
                else{
                    double[] documentoVector = new double[vPalabras.length];
                    documentoVector[i] = peso;
                    documentos.put(terminos.get(j).getDocumento(),documentoVector);
                }
            }

            vectorConsulta[i] = this.CalcularPeso_x_doc(1,1,593);



        }
        Iterator<Map.Entry<String,double[]>> it = documentos.entrySet().iterator();
        System.out.println("tama;o hashtable: " + documentos.size());
        while(it.hasNext()){

            Map.Entry entry = it.next();
            CalculoDeSimilitud((double[]) entry.getValue(),(String) entry.getKey());

        }
        this.documentosRankeados.remove(documentosRankeados.size()-1);
        this.RecorrerMasRelevantes(10);
    }

    public void CalculoDeSimilitud(double[] vDoc, String nomDoc) {

        double acTotal;
        double acDenominador = 0;
        double acConsulta = 0;
        double acDocumento = 0;

        for(int i = 0; i < vDoc.length; i++){

            acDenominador += (vDoc[i] * vectorConsulta[i]);
            acConsulta += Math.pow(vectorConsulta[i],2);
            acDocumento += Math.pow(vDoc[i],2);
        }

        acTotal = acDenominador / ((Math.sqrt(acConsulta)) + (Math.sqrt(acDocumento)));

        Documento doc = new Documento(acTotal,nomDoc);

        System.out.println(acTotal);

        int lugar = 0;

        for(int i = documentosRankeados.size() - 1 ; i >= 0; i--){
            if(acTotal > documentosRankeados.get(i).getRelevancia()){
                lugar = i;
            }
            else{
                break;
            }
        }
        System.out.println("cant docs rankeados: " + documentosRankeados.size());
        documentosRankeados.add(lugar,doc);
    }

    public double CalcularPeso_x_doc(int frecuencia, double cantidadAparicion, int qDocs){
        System.out.println(cantidadAparicion);
        return frecuencia * Math.log(qDocs/cantidadAparicion);
    }

    public void RecorrerMasRelevantes(int n){
        System.out.println("cant docs rankeados: " + documentosRankeados.size());
        for(int i = 0 ; i < documentosRankeados.size() || i == n - 1; i++){
            System.out.println(documentosRankeados.get(i).getNombre());
        }
    }

}
