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
//mejoras:
// -->Cerrar y abrir la base correctamente
// -->Solucionar error "Libro no encontrado"
// -->Crear funcion Abrir y Cerrar

public class Base {
    public static Scanner sc = new Scanner(System.in);
    public static List<Empleado> empleados;
    public static List<Usuario> usuarios;
    public static List<Libro> libros;
    
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
                    Libro.nuevoLibro(st);
                    break;
                case 2:
                    Libro.buscarLibro("2",st);
                    break;
                case 3:
                    Libro.buscarLibro("3",st);
                    break;
                case 4:
                    Libro.alquilarLibro(st);
                    break;
                case 5:
                    Libro.devolucionLibro(st);
                    break; 
                case 6:
                    Empleado.buscarEmpleado(st);
                    break;
                case 7:
                    Usuario.buscarUsuario(st);
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