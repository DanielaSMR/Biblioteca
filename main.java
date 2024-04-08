import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class main {
    public static Scanner sc = new Scanner(System.in);

    public static List<Empleado> empleados;
    public static List<Usuario> usuarios;
    public static List<Libro> libros;
    public static void main(String[] args) throws Exception {

        empleados = new ArrayList<>();
        empleados.add(new Empleado("Alberto"));
        empleados.add(new Empleado("Encarna"));
        empleados.add(new Empleado("Estela"));
        empleados.add(new Empleado("Manolo"));
        empleados.add(new Empleado("Agustín"));

        usuarios = new ArrayList<>();
        usuarios.add(new Usuario("usu1"));
        usuarios.add(new Usuario("usu2"));
        usuarios.add(new Usuario("usu3"));
        usuarios.add(new Usuario("usu4"));
        usuarios.add(new Usuario("usu5"));


        String eleccion;
        String eleccionEmpleados;
        String eleccionUsuarios;
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
            eleccion = sc.nextLine();
            switch (eleccion) {
                case "1":
                    
                    break;
                case "2":
                    
                    break;
                case "3":
                    
                    break;
                case "4":
                    
                    break;
                case "5":
                    
                    break; 
                case "6":
                    System.out.println("Que quieres hacer?\n"+
                    "1-Listar empleados\n"+
                    "2-Dar de alta a un nuevo empleado\n"+
                    "3-Dar de baja a un empleado");
                    eleccionEmpleados = sc.nextLine();
                    if(eleccionEmpleados.equals("1")){
                        listarEmpleados();
                    }else if(eleccionEmpleados.equals("2")){
                        nuevoEmpleado();
                    }else if(eleccionEmpleados.equals("3")){
                        eliminarEmpleado();
                    }else{
                    break;
                    }
                case "7":
                    System.out.println("Que quieres hacer?\n"+
                    "1-Listar usuarios\n"+
                    "2-Dar de alta a un nuevo usuario\n"+
                    "3-Dar de baja a un usuario");
                    eleccionUsuarios = sc.nextLine();
                    if(eleccionUsuarios.equals("1")){

                    }else if(eleccionUsuarios.equals("2")){

                    }else if(eleccionUsuarios.equals("3")){
                        
                    }else{
                    
                    }
                    break; 
                case "8":
                    
                    break;        
                default:
                    break;
            }
        }while(!eleccion.equals("10"));


        /*try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error al registrar el driver de PostgreSQL: " + ex);
        }

        Connection connection = null;
        // Database connect
        // Conectamos con la base de datos
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mati", "mati", "mati");
        Statement st = connection.createStatement();
        connection.setAutoCommit(false);

        connection.commit();
        ResultSet rs = st.executeQuery("SELECT * FROM ciclista");
        int n = 0;
        while (rs.next()) {
            System.out.print("Column " + n + " returned ");
            System.out.println(rs.getInt(1) + " name " + rs.getString(2));
            n++;
        }
        rs.close();
        st.close();
        connection.close();*/
    }

    public static void listarEmpleados(){
        System.out.println("Estos son los empleados");
        for(int i = 0; i < empleados.size();i++){
            System.out.println(empleados.get(i));
        }
    }

    public static void nuevoEmpleado(){
        System.out.println("Como se llama el nuevo empleado?");
        String nombre = sc.nextLine();
        for(int i = 0; i < empleados.size();i++){
            if(nombre.equals(empleados.get(i))){
                System.out.println("Escribe el apellido");
                String apellido = sc.nextLine();
                nombre = nombre + apellido;
            }
        }
        empleados.add(new Empleado(nombre));
    }

    public static void eliminarEmpleado(){
        System.out.println("Como se llama el empleado que quieres borrar");
        String nombre = sc.nextLine();
        for(int i = 0; i < empleados.size();i++){
            if(nombre.equals(empleados.get(i))){
                empleados.remove(i);
            }
        }
    }
    
}