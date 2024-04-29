import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Usuario {
    private String nombre;
    private int id;
    public static Scanner sc = new Scanner(System.in);

    public Usuario(int id,String nombre){
        this.id = id;
        this.nombre = nombre;
    }

    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



    public int getId() {
        return id;
    }



    public void setId(int id) {
        this.id = id;
    }
}
