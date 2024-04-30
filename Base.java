import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

//Meter array list(al menos 2 funciones)
public class Base {
    public static Scanner sc = new Scanner(System.in);
    public static ArrayList<Empleado> empleados;
    public static ArrayList<Usuario> usuarios;
    public static ArrayList<Libro> libros;
    
    public static void main(String[] args) throws Exception {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
        }

        Connection connection = null;
        // Database connect
        // Conectamos con la base de datos
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mati", "mati", "mati");
        //connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "a");
        Statement st = connection.createStatement();
        connection.setAutoCommit(false);

        Sincronizar.añadirEmple(st);
        Sincronizar.añadirUsu(st);

        int eleccion;
        do{
            System.out.println("Elige una opcion\n"+
            "1- Dar de alta un libro en el sistema:\n"+
            "2- Búsqueda de libros dentro del sistema\n"+
            "3- Dar de baja un libro en el sistema.\n"+
            "4- Alquiler de un libro por un usuario.\n"+
           "5- Devolución de un libro por un usuario.\n"+
            "6- Gestión de empleados/as de la biblioteca.\n"+
            "7- Gestión de usuarios/as de la biblioteca.\n"+
            "8- Salir del sistema.");
            eleccion = (int)Integer.parseInt(sc.nextLine());
            switch (eleccion) {
                case 1:
                    GestionLibro.nuevoLibro(st);
                    break;
                case 2:
                    GestionLibro.buscarLibro("2",st);
                    break;
                case 3:
                    GestionLibro.buscarLibro("3",st);
                    break;
                case 4:
                    GestionLibro.alquilarLibro(st);
                    break;
                case 5:
                    GestionLibro.devolucionLibro(st);
                    break; 
                case 6:
                    GestionEmple.buscarEmpleado(st);
                    break;
                case 7:
                    GestionUsu.buscarUsuario(st);
                    break;
                case 8:
                    System.out.println("Saliendo...");
                    break;        
                default:
                    System.out.println("No existe esa opcion");
                    break;
            }
        }while(eleccion != 8);
        connection.commit();
        connection.close();

    }





}