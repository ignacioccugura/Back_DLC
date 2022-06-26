package AccesoDatos.Entities;

public class Documento implements Comparable{
    double relevancia;
    String nombre;

    public Documento(double relevancia, String nombre) {
        this.relevancia = relevancia;
        this.nombre = nombre;
    }

    public double getRelevancia() {
        return relevancia;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public int compareTo(Object o) {
        Documento doc = (Documento) o;
        if(doc.getRelevancia() > this.getRelevancia()){
            return -1;
        }
        else{
            if(doc.getRelevancia() < this.getRelevancia()){
                return 1;
            }
            else{
                return 0;
            }
        }
    }
}
