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

        Connection conexion = null;
        // Database connect
        // Conectamos con la base de datos
        conexion = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mati", "mati", "mati");
        Statement st = conexion.createStatement();
        conexion.setAutoCommit(false);



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
                    nuevoLibro();
                    break;
                case "2":
                    buscarLibro("2");
                    break;
                case "3":
                    buscarLibro("3");
                    break;
                case "4":
                    
                    break;
                case "5":
                    
                    break; 
                case "6":
                    do{
                    System.out.println("Que quieres hacer?\n"+
                    "1-Listar empleados\n"+
                    "2-Dar de alta a un nuevo empleado\n"+
                    "3-Dar de baja a un empleado" +
                    "4-Salir");
                    eleccionEmpleados = sc.nextLine();
                    switch (eleccionEmpleados) {
                        case "1":
                            listarEmpleados();
                            break;
                        case "2":
                            nuevoEmpleado(st);
                            conexion.commit();
                            break;
                        case "3":
                            eliminarEmpleado();
                            break;
                        case "4":
                            break;
                        default:
                            break;
                    }
                }while (!eleccionEmpleados.equals("4"));
                case "7":
                do{
                    System.out.println("Que quieres hacer?\n"+
                    "1-Listar usuarios\n"+
                    "2-Dar de alta a un nuevo usaurio\n"+
                    "3-Dar de baja a un usuario" +
                    "4-Salir");
                    eleccionUsuarios = sc.nextLine();
                    switch (eleccionUsuarios) {
                        case "1":
                            listarUsuario();
                            break;
                        case "2":
                            nuevoUsuario();
                            conexion.commit();
                            break;
                        case "3":
                            eliminarUsuario();
                            break;
                        case "4":
                            break;
                        default:
                            break;
                    }
                }while (!eleccionUsuarios.equals("4"));
                case "8":
                    
                    break;        
                default:
                    break;
            }
        }while(!eleccion.equals("10"));
        conexion.commit();
        conexion.close();

    }

    public static void buscarEmpleado(){

    }

    public static void listarEmpleados(){
        System.out.println("Estos son los empleados");
        for(int i = 0; i < empleados.size();i++){
            System.out.println(empleados.get(i).getNombre());
        }
    }

    public static void nuevoEmpleado(Statement st){
        
        System.out.println("Como se llama el nuevo empleado?");
        String nombre = sc.nextLine();
        System.out.println("Cual es el DNI?");
        String DNI = sc.nextLine();

        String sentenciaSql = "INSERT INTO empleados (dni,nombre)" + "VALUES ('" + DNI + "','" + nombre + "');" ;
        
        try {
            ResultSet rs = st.executeQuery("SELECT dni FROM empleados");
            int n=0;
            while (rs.next()) {
                System.out.print("Column "+ n +" returned ");
                System.out.println(rs.getString(1));
                
                if(!rs.getString(1).equals(DNI)){
                    st.execute(sentenciaSql);
                    System.out.println("Se añadio con exito");
                }else
                n++;
            }
            rs.close();
            st.close();
          } catch (SQLException sqle) {
            sqle.printStackTrace();
          }
    }

    public static void eliminarEmpleado(){
        System.out.println("Como se llama el empleado que quieres borrar");
        String nombre = sc.nextLine();
        for(int i = 0; i < empleados.size();i++){
            if(nombre.equals(empleados.get(i).getNombre())){
                System.out.println("Has eliminado al empleado:" + nombre);
                empleados.remove(i);
            }
        }
    }


    public static void listarUsuario(){
        System.out.println("Estos son los empleados");
        for(int i = 0; i < usuarios.size();i++){
            System.out.println(usuarios.get(i).getNombre());
        }
    }

    public static void nuevoUsuario(){
        System.out.println("Como se llama el nuevo empleado?");
        String nombre = sc.nextLine();
        for(int i = 0; i < usuarios.size();i++){
            if(nombre.equals(usuarios.get(i).getNombre())){
                System.out.println("Escribe el apellido");
                String apellido = sc.nextLine();
                nombre = nombre + apellido;
            }
        }
        //empleados.add(new Empleado(nombre));
    }

    public static void eliminarUsuario(){
        System.out.println("Como se llama el empleado que quieres borrar");
        String nombre = sc.nextLine();
        for(int i = 0; i < usuarios.size();i++){
            if(nombre.equals(usuarios.get(i).getNombre())){
                System.out.println("Has eliminado al usuarios: " + nombre);
                usuarios.remove(i);
            }
        }
       
    }

    public static void nuevoLibro(){
        System.out.println("Añade el titulo");
        String titulo = sc.nextLine();
        System.out.println("Escribe el autor");
        String autor = sc.nextLine();
        System.out.println("Escribe la editorial");
        String editorial = sc.nextLine();
        System.out.println("Escribe la ubicacion en biblioteca(pasillo)");
        int ubicacion = sc.nextInt();
        System.out.println("Escribe el ISBN");
        String ISBN2 = sc.nextLine();
        String ISBN = sc.nextLine();
        System.out.println("Escribe el precio");
        Double precio = sc.nextDouble();

        libros.add(new Libro(ISBN,titulo,autor,editorial,false,ubicacion,precio," "," "));

    }

    

    public static void buscarLibro(String eleccion){
        String eleccionLibros;
        do{
            System.out.println("Como quieres buscar el libro?"+
            "\n 1-Titulo"+
            "\n 2-Autor"+
            "\n 3-Editorial"+
            "\n 4-Ubicacion"+
            "\n 5-ISBN"+
            "\n 6-Nombre empleado de biblioteca"+
            "\n 7-Estado de prestamos"+
            "\n 8-Nombre del usuario que lo alquilo"+
            "\n 9-Salir");
            eleccionLibros = sc.nextLine();
            switch (eleccionLibros) {
                case "1":
                    buscarTitulo(eleccion);
                    break;
                case "2":
                    buscarAutor(eleccion);
                    break;
                case "3":
                    buscarEditorial(eleccion);
                    break;
                case "4":
                    buscarUbicacion(eleccion);
                    break;
                case "5":
                    buscarISBN(eleccion);
                    break;
                case "6":
                    buscarEmpleado(eleccion);
                    break;
                case "7":
                    buscarPrestado(eleccion);
                    break;
                case "8":
                    buscarUsuario(eleccion);
                    break;
                case "9":
                    System.out.println("Volviendo al menu principal...");
                    break;
                default:
                    break;
            }
        }while(!eleccionLibros.equals("9"));
    }



    public static void buscarTitulo(String eleccion){
        System.out.println("Escribe el nombre del libro que buscas");
        String titulo = sc.nextLine();
        int contador = 0;
        System.out.println("Los libros con este titulo:");
        for(int i = 0;i < libros.size();i++){
            if(titulo.equals(libros.get(i).getTitulo())){
                contador++;
                System.out.println(contador + " - " + "posicion " + i + " - " + libros.get(i).getTitulo());
            }
        }
        System.out.println("Se han encontrado: " + contador + " resultados");
        if(eleccion.equals("3")){
            System.out.println("Que libro quieres borrar?");
            int posicion = sc.nextInt();
            libros.remove(posicion);
        }
    }

    public static void buscarAutor(String eleccion){
        System.out.println("Escribe el nombre del autor que buscas");
        String autor = sc.nextLine();
        int contador = 0;
        System.out.println("Los libros con este autor:");
        for(int i = 0;i < libros.size();i++){
            if(autor.equals(libros.get(i).getAutor())){
                contador++;
                System.out.println(contador + "-" + libros.get(i).getTitulo());
            }
        }
        System.out.println("Se han encontrado: " + contador + " resultados");
    }

    public static void buscarEditorial(String eleccion){
        System.out.println("Escribe el nombre de la editorial que buscas");
        String editorial = sc.nextLine();
        int contador = 0;
        System.out.println("Los libros con esta editorial:");
        for(int i = 0;i < libros.size();i++){
            if(editorial.equals(libros.get(i).getEditorial())){
                contador++;
                System.out.println(contador + "-" + libros.get(i).getTitulo());
            }
        }
        System.out.println("Se han encontrado: " + contador + " resultados");
    }

    public static void buscarUbicacion(String eleccion){
        System.out.println("Escribe el nombre de la ubicacion que buscas");
        int ubicacion = sc.nextInt();
        int contador = 0;
        System.out.println("Los libros con esta ubicacion:");
        for(int i = 0;i < libros.size();i++){
            if(ubicacion == libros.get(i).getUbicacionLibro()){
                contador++;
                System.out.println(contador + "-" + libros.get(i).getTitulo());
            }
        }
        System.out.println("Se han encontrado: " + contador + " resultados");
    }

    public static void buscarISBN(String eleccion){
        System.out.println("Escribe el nombre de la ubicacion que buscas");
        int ubicacion = sc.nextInt();
        int contador = 0;
        System.out.println("Los libros con esta ubicacion:");
        for(int i = 0;i < libros.size();i++){
            if(ubicacion == libros.get(i).getUbicacionLibro()){
                contador++;
                System.out.println(contador + "-" + libros.get(i).getTitulo());
            }
        }
        System.out.println("Se han encontrado: " + contador + " resultados");
    }


    public static void buscarEmpleado(String eleccion){
        System.out.println("Escribe el nombre del empleado que buscas");
        String empleado = sc.nextLine();
        int contador = 0; 
        System.out.println("Los libros que el empleado presto:");
        for(int i = 0;i < libros.size();i++){
            if(empleado.equals(libros.get(i).getNomBiblotecario())){
                contador++;
                System.out.println(contador + "-" + libros.get(i).getTitulo());
            }
        }
        System.out.println("Se han encontrado: " + contador + " resultados");
    }


    public static void buscarPrestado(String eleccion){
        System.out.println("Esta prestado el libro? 1-SI 2-NO");
        String prestamo = sc.nextLine();
        int contador = 0 ;
        if(prestamo.equals("SI")){
            System.out.println("Estos son los libros prestados:");
            for(int i = 0;i < libros.size();i++){
                if(libros.get(i).isEstadoPrestamo() == true){
                    contador++;
                    System.out.println(contador + "-" + libros.get(i).getTitulo());
                }
            }
        }else if(prestamo.equals("NO")){
            System.out.println("Estos son los libros no prestados:");
            for(int i = 0;i < libros.size();i++){
                if(libros.get(i).isEstadoPrestamo() == false){
                    contador++;
                    System.out.println(contador + "-" + libros.get(i).getTitulo());
                }
            }
        }
    
        System.out.println("Se han encontrado: " + contador + " resultados");
    }


    public static void buscarUsuario(String eleccion){
        System.out.println("Escribe el nombre del usuario que buscas");
        String usuario = sc.nextLine();
        int contador = 0 ;
        System.out.println("Los libros que el usuario alquilo:");
        for(int i = 0;i < libros.size();i++){
            if(usuario.equals(libros.get(i).getNomUsuario())){
                contador++;
                System.out.println(contador + "-" + libros.get(i).getTitulo());
            }
        }
        System.out.println("Se han encontrado: " + contador + " resultados");
    }
    


}