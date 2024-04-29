import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Empleado {
    private String nombre;
    private String DNI;
    public static Scanner sc = new Scanner(System.in);


    public Empleado(String DNI,String nombre){
        this.nombre = nombre;
        this.DNI = DNI;
    }


    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getDNI() {
        return DNI;
    }


    public void setDNI(String dNI) {
        DNI = dNI;
    }

    

}
