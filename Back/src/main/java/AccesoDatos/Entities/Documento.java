package AccesoDatos.Entities;

public class Documento {
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
}
