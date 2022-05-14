package AccesoDatos.Entities;

import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posteos")
public class Termino implements Serializable
{

    @Id
    @Column(name = "termino")
    private String termino;
    @Id
    @Column(name = "documento")
    private String documento;

    @Column(name = "frecuencia")
    private int frecuenciaRelativa = 1;

    //@Column(name = "frecuencia_max")
    //private int frecuenciaMax = 0;

    //@OneToMany(mappedBy = "termino",cascade = {CascadeType.MERGE})
    //private List<Posteo> posteos = new ArrayList<>();

    public Termino(String termino, String documento) {

        this.termino = termino;
        this.documento = documento;
    }

    public Termino() {

    }

    //public void agregarPosteo(Posteo posteo){
    //    this.posteos.add(posteo);
    //}

    public void incrementarFrecuenciaRelativa() {
        this.frecuenciaRelativa++;
    }

    public int getFrecuenciaRelativa(){return this.frecuenciaRelativa;}

    //public void setFrecuenciaMax(int frecuenciaMax){
    //    this.frecuenciaMax = frecuenciaMax;
    //}

    //public int getFrecuenciaMax(){
    //    return this.frecuenciaMax;
    //}
    public void setFrecuenciaRelativa(){this.frecuenciaRelativa = 1;}
    public String getTermino(){
        return this.termino;
    }

    public String getDocumento(){return this.documento;}

    public void setDocumento(String documento){this.documento = documento;}
    public double CalcularPeso(double cantidadAparicion, double cantDocs){
        return this.getFrecuenciaRelativa() * Math.log(cantDocs/cantidadAparicion);
    }
    //public void limpiarPosteos(){
    //    this.posteos.clear();
    //}
    //public int size(){
    //    return this.posteos.size();
    //}

}
