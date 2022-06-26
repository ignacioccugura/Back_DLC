package AccesoDatos;

import AccesoDatos.Entities.Termino;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.transaction.Transaction;
import java.io.Serializable;
import java.sql.*;

@RequestScoped
public class AccesoDatos {

    private Connection con;
    private Statement st;
    private EntityManager em;
    private EntityTransaction transaction;


    public AccesoDatos() {
        this.Conectar();
    }

    public String Conectar()
    {
        try {
            this.con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dlc","root","root");
            con.setAutoCommit(true);
            this.st = con.createStatement();
            this.PrepararPersistencia();
            con.close();
            return "Base de datos conectada...";
        } catch (SQLException e) {
            e.printStackTrace();
            return (e.getMessage());
        }
    }

    public Connection getCon(){
        return this.con;
    }

    public void Desconectar() throws SQLException
    {
        this.st.close();
        this.con.close();
    }

    public ResultSet Select(String query) throws SQLException
    {
        ResultSet rs = this.st.executeQuery(query);
        return rs;
    }

    public void Insert(String insert) throws SQLException{
        this.InUpDe(insert);
    }

    public void Update(String update) throws  SQLException{
        this.InUpDe(update);
    }

    public void Delete(String delete) throws SQLException{
        this.InUpDe(delete);
    }

    private void InUpDe (String inupde) throws SQLException{
        st.executeUpdate(inupde);
    }

    public void PrepararPersistencia(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        this.em = emf.createEntityManager();
    }

    public EntityManager getEntityManager(){
        return this.em;
    }

    public void Guardar(Serializable obj){
        this.em.persist(obj);
    }

    public void IniciarTransaccion(){
        this.transaction = this.em.getTransaction();
        this.transaction.begin();
    }

    public void FinalizarTransaccion(){
        this.transaction.commit();
    }
}
