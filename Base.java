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
// -->Pasar las funciones a otro archivo
// -->Cerrar y abrir la base correctamente
// -->Solucionar error "Libro no encontrado"

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
                    nuevoLibro(st);
                    break;
                case 2:
                    buscarLibro("2",st);
                    break;
                case 3:
                    buscarLibro("3",st);
                    break;
                case 4:
                    alquilarLibro(st);
                    break;
                case 5:
                    
                    break; 
                case 6:
                    buscarEmpleado(st);//Terminao
                    break;
                case 7:
                    buscarUsuario(st);//Terminao
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


    private static void alquilarLibro(Statement st) {
        System.out.println("Estos son los libros NO alquilados:");
        int contadorEstado = 1;
        try{
            ResultSet rs = st.executeQuery("SELECT isbn,titulo,estadoprestamo FROM public.libros WHERE estadoprestamo = false ;" );
            while(rs.next()){
                    System.out.println(contadorEstado + "- ISBN: " + rs.getString(1) + " Titulo: " + rs.getString(2) + " Estado Prestamo: " + rs.getString(4));
                    contadorEstado++;
            }
            rs.close();
        }catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        System.out.println("Que libro quiere cambiar?");
        String nombreLibro = sc.nextLine();

        String id = "";
        try{
            int contador1 = 1;
            ResultSet rs = st.executeQuery("SELECT nombre FROM public.empleados;" );
            while(rs.next()){
                    System.out.println(contador1 + "- Empleado Nombre: " + rs.getString(2));
                    contador1++;
            }

            System.out.println("Que empleado quiere registrar?");
            int eleccionEmpleado = sc.nextInt();

            for(int i = 1;i < eleccionEmpleado;i++){
                if(i == eleccionEmpleado){
                    id = rs.getString(1);
                }
            }
            rs.close();
        }catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        String id2 = "";
        try{
            int contador1 = 1;
            ResultSet rs = st.executeQuery("SELECT nombre FROM public.usuarios;" );
            while(rs.next()){
                    System.out.println(contador1 + "- Usuario Nombre: " + rs.getString(2));
                    contador1++;
            }
            
            System.out.println("Que usuario quiere registrar?");
            int eleccionUsuario = sc.nextInt();

            for(int i = 1;i < eleccionUsuario;i++){
                if(i == eleccionUsuario){
                    id2 = rs.getString(1);
                }
            }
            rs.close();
        }catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        try{
            ResultSet rs = st.executeQuery("SELECT * FROM empleados");
            while(rs.next()){
                String sentenciaSql = "UPDATE public.libros SET nomemple=?, nomusu=? WHERE id=? ";
                PreparedStatement ps = st.getConnection().prepareStatement(sentenciaSql);
                ps.setString(1, id) ;
                ps.setString(2, id2);
                ps.setString(3, nombreLibro);
                int filasAfectadas = ps.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Se actualizo con exito");
                } else {
                    System.out.println("No se pudo actualizar");
                }
                ps.close();
            }
            rs.close();
        }catch (SQLException sqle) {
            sqle.printStackTrace();
        }


    }


    public static void buscarEmpleado(Statement st) throws SQLException{
        int eleccionEmpleados;
        do{
            System.out.println("Que quieres hacer?\n"+
            "1-Listar empleados\n"+
            "2-Dar de alta a un nuevo empleado\n"+
            "3-Dar de baja a un empleado" +
            "4-Salir");
            eleccionEmpleados = sc.nextInt();
            switch (eleccionEmpleados) {
                case 1:
                    listarEmpleados(st);
                    st.getConnection().commit();
                    break;
                case 2:
                    nuevoEmpleado(st);
                    st.getConnection().commit();
                    break;
                case 3:
                    eliminarEmpleado(st);
                    st.getConnection().commit();
                    break;
                case 4:
                System.out.println("Volviendo al menu...");
                    break;
                default:
                    break;
            }
        }while (eleccionEmpleados != 4);
    }

    public static void listarEmpleados(Statement st)throws SQLException{
        System.out.println("Estos son los empleados");
        ResultSet rs = st.executeQuery("SELECT * FROM empleados");
        int n=0;
        while (rs.next()) {
            System.out.println("Columna DNI: " + rs.getString(1) + "Columna Nombre: " + rs.getString(2));
            n++;
        }
        rs.close();
        
    }

    public static void nuevoEmpleado(Statement st){
        
        System.out.println("Como se llama el nuevo empleado?");
        String nombre2 = sc.nextLine();
        String nombre = sc.nextLine();
        System.out.println("Cual es el DNI?");
        String DNI = sc.nextLine();
        
        try {
            //Comprobar si existe el empleado
            ResultSet rs = st.executeQuery("SELECT dni FROM empleados WHERE dni = '" + DNI + "'");
            if (rs.next()) {
                System.out.println("El empleado ya existe en la base de datos.");
            }else {
                // Insertar nuevo empleado si el DNI no existe
                String sentenciaSql = "INSERT INTO public.empleados (dni, nombre) VALUES (?, ?)";
                PreparedStatement ps = st.getConnection().prepareStatement(sentenciaSql);
                ps.setString(1, DNI) ;//parametro 1 del Insert
                ps.setString(2, nombre);//paranetro 2 del Insert
                int filasAfectadas = ps.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Se añadió el empleado con éxito.");
                } else {
                    System.out.println("No se pudo añadir el empleado.");
                }
                ps.close();
            }
            rs.close();
          } catch (SQLException sqle) {
            sqle.printStackTrace();
          }
    }

    public static void eliminarEmpleado(Statement st)throws SQLException{
        System.out.println("Cual es el DNI del empleado a borrar");
        String DNI2 = sc.nextLine();
        String DNI = sc.nextLine();
        String sentenciaSql = "DELETE FROM public.empleados WHERE dni = ? ;";
        try{
            ResultSet rs = st.executeQuery("SELECT * FROM empleados");
            while(rs.next()){
                //comprueba si el empleado existe
                if(rs.getString(1).equals(DNI)){
                    System.out.println("Eliminando al empleado: \n DNI: " + rs.getString(1) + " Nombre: " + rs.getString(2));
                    PreparedStatement ps = st.getConnection().prepareStatement(sentenciaSql);
                    ps.setString(1, DNI) ;//parametro del delete
                    int filasAfectadas = ps.executeUpdate();
                    if (filasAfectadas > 0) {
                        System.out.println("Se elimino el empleado con éxito.");
                    } else {
                        System.out.println("No se pudo eliminar el empleado.");
                    }
                    ps.close();
                }else{
                    System.out.println("No se encontro el empleado");
                }
            }
            rs.close();
        }catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        

    }

    public static void buscarUsuario(Statement st) throws SQLException{
        int eleccionUsuarios;
        do{
            System.out.println("Que quieres hacer?\n"+
            "1-Listar usuarios\n"+
            "2-Dar de alta a un nuevo usaurio\n"+
            "3-Dar de baja a un usuario" +
            "4-Salir");
            eleccionUsuarios = sc.nextInt();
            switch (eleccionUsuarios) {
                case 1:
                    listarUsuario(st);
                    st.getConnection().commit();
                    break;
                case 2:
                    nuevoUsuario(st);
                    st.getConnection().commit();
                    break;
                case 3:
                    eliminarUsuario(st);
                    st.getConnection().commit();
                    break;
                case 4:
                System.out.println("Volviendo al menu...");
                     break;
                default:
                    break;
            }
        }while (eleccionUsuarios != 4);
    }


    public static void listarUsuario(Statement st) throws SQLException {
        System.out.println("Estos son los usuarios");
        ResultSet rs = st.executeQuery("SELECT * FROM usuarios");
        int n=0;
        while (rs.next()) {
            System.out.println("Columna ID: " + rs.getInt(1) + " Columna Nombre: " + rs.getString(2));
            n++;
        }
        rs.close();
    }

    public static void nuevoUsuario(Statement st) throws SQLException{
        System.out.println("Como se llama el nuevo usuario?");
        String nombre2 = sc.nextLine();
        String nombre = sc.nextLine();
        System.out.println("Cual es el ID?");
        int id = sc.nextInt();
        
        try {
            ResultSet rs = st.executeQuery("SELECT id FROM usuarios WHERE id = '" + id + "'");
            if (rs.next()) {
                System.out.println("El empleado ya existe en la base de datos.");
            }else {
                String sentenciaSql = "INSERT INTO public.usuarios (id, nombre) VALUES (?, ?)";
                PreparedStatement ps = st.getConnection().prepareStatement(sentenciaSql);
                ps.setInt(1, id);//parametro 1 del Insert
                ps.setString(2, nombre);//paranetro 2 del Insert
                int filasAfectadas = ps.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Se añadió el usuarios con éxito.");
                } else {
                    System.out.println("No se pudo añadir el usuario.");
                }
                ps.close();
            }
            rs.close();
          } catch (SQLException sqle) {
            sqle.printStackTrace();
          }
    }

    public static void eliminarUsuario(Statement st) throws SQLException{
        System.out.println("Cual es el Id del usuario a borrar");
        String DNI2 = sc.nextLine();
        int id = sc.nextInt();
        String sentenciaSql = "DELETE FROM public.usuarios WHERE id = ? ;";
        try{
            ResultSet rs = st.executeQuery("SELECT * FROM usuarios");
            while(rs.next()){
                //comprueba si el empleado existe
                if(rs.getInt(1) == (id)){
                    System.out.println("Eliminando al empleado: \n DNI: " + rs.getInt(1) + " Nombre: " + rs.getString(2));
                    PreparedStatement ps = st.getConnection().prepareStatement(sentenciaSql);
                    ps.setInt(id, id);//parametro del delete
                    int filasAfectadas = ps.executeUpdate();
                    if (filasAfectadas > 0) {
                        System.out.println("Se elimino el empleado con éxito.");
                    } else {
                        System.out.println("No se pudo eliminar el empleado.");
                    }
                    ps.close();
                }else{
                    System.out.println("No se encontro el empleado");
                }
            }
            rs.close();
        }catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        
       
    }

    public static void nuevoLibro(Statement st){
        System.out.println("Añade el titulo");
        String titulo2 = sc.nextLine();
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

        try {
            ResultSet rs = st.executeQuery("SELECT isbn FROM libros WHERE isbn = '" + ISBN + "'");
            if (rs.next()) {
                System.out.println("El empleado ya existe en la base de datos.");
            }else {
                String sentenciaSql = "INSERT INTO public.libros (isbn, titulo, autor, editorial, estadoprestamo, ubicacionlibro, precio, nomemple, nomusu) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
                PreparedStatement ps = st.getConnection().prepareStatement(sentenciaSql);
                ps.setString(1, ISBN);
                ps.setString(2, titulo);
                ps.setString(3, autor);
                ps.setString(4, editorial);
                ps.setBoolean(5, false);
                ps.setInt(6, ubicacion);
                ps.setDouble(7, precio);
                ps.setString(8, null);
                ps.setString(9, null);
                int filasAfectadas = ps.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Se añadió el libro con éxito.");
                    st.getConnection().commit();
                } else {
                    System.out.println("No se pudo añadir el libro.");
                }
                ps.close();
            }
            rs.close();
          } catch (SQLException sqle) {
            sqle.printStackTrace();
          }
    }

    

    public static void buscarLibro(String eleccion,Statement st) throws SQLException{
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
                    buscarTitulo(eleccion,st);
                    st.getConnection().commit();
                    break;
                case "2":
                    buscarAutor(eleccion,st);
                    st.getConnection().commit();
                    break;
                case "3":
                    buscarEditorial(eleccion,st);
                    st.getConnection().commit();
                    break;
                case "4":
                    buscarUbicacion(eleccion,st);
                    st.getConnection().commit();
                    break;
                case "5":
                    buscarISBN(eleccion,st);
                    st.getConnection().commit();
                    break;
                case "6":
                    buscarEmpleado(eleccion,st);
                    st.getConnection().commit();
                    break;
                case "7":
                    buscarPrestado(eleccion,st);
                    st.getConnection().commit();
                    break;
                case "8":
                    buscarLibroUsuario(eleccion,st);
                    st.getConnection().commit();
                    break;
                case "9":
                    System.out.println("Volviendo al menu principal...");
                    break;
                default:
                    break;
            }
        }while(!eleccionLibros.equals("9"));
    }



    public static void buscarTitulo(String eleccion,Statement st){
        System.out.println("Escribe el nombre del libro que buscas");
        String titulo = sc.nextLine();
        int contador = 0;
        System.out.println("Los libros con este titulo:");
        try{
            ResultSet rs = st.executeQuery("SELECT isbn,titulo FROM public.libros WHERE titulo LIKE '%" + titulo + "%';");
            while(rs.next()){
                contador++;
                System.out.println(contador + "- ISBN: " + rs.getString(1) + " Titulo: " + rs.getString(2));
                
            }
            rs.close();
        }catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        if(contador == 0){
            System.out.println("No se encontro ninguna coincidencia");
        }
        System.out.println("Se han encontrado: " + contador + " resultados");

        if(eleccion.equals("3")){
            System.out.println("Cual es el titulo del libro quieres borrar?");
            titulo = sc.nextLine();
            String sentenciaSql = "DELETE FROM public.libros WHERE titulo = ? ;";
            try{
                ResultSet rs = st.executeQuery("SELECT * FROM libros");
                while(rs.next()){
                    if(rs.getString(2).equals(titulo)){
                        System.out.println("Eliminando el libro: \n ISBN: " + rs.getString(1) + " Titulo: " + rs.getString(2));
                        PreparedStatement ps = st.getConnection().prepareStatement(sentenciaSql);
                        ps.setString(1, titulo);//parametro del delete
                        int filasAfectadas = ps.executeUpdate();
                        if (filasAfectadas > 0) {
                            System.out.println("Se elimino el libro con éxito.");
                        } else {
                            System.out.println("No se pudo eliminar el libro.");
                        }
                        ps.close();
                    }else{
                        System.out.println("No se encontro el libro");
                    }
                }
                rs.close();
            }catch (SQLException sqle) {
                sqle.printStackTrace();
            }
            
        }
       
        
    }

    public static void buscarAutor(String eleccion,Statement st){
        System.out.println("Escribe el nombre del autor que buscas");
        String autor = sc.nextLine();
        int contador = 0;
        System.out.println("Los libros con este autor:");
        try{
            ResultSet rs = st.executeQuery("SELECT isbn,titulo,autor FROM libros WHERE autor LIKE %" + autor + "%;");
            while(rs.next()){
               
                    System.out.println(contador + "- ISBN: " + rs.getString(1) + " Titulo: " + rs.getString(2) + " Autor: " + rs.getString(3));
                    contador++;
            }
            rs.close();
        }catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        System.out.println("No se encontro ninguna coincidencia");
        System.out.println("Se han encontrado: " + contador + " resultados");

        if(eleccion.equals("3")){
            System.out.println("Cual es el titulo del libro quieres borrar?");
            String titulo = sc.nextLine();
            String sentenciaSql = "DELETE FROM public.libros WHERE titulo = ? ;";
            try{
                ResultSet rs = st.executeQuery("SELECT * FROM libros");
                while(rs.next()){
                    if(rs.getString(2).equals(titulo)){
                        System.out.println("Eliminando el libro: \n ISBN: " + rs.getString(1) + " Titulo: " + rs.getString(2));
                        PreparedStatement ps = st.getConnection().prepareStatement(sentenciaSql);
                        ps.setString(1, titulo);//parametro del delete
                        int filasAfectadas = ps.executeUpdate();
                        if (filasAfectadas > 0) {
                            System.out.println("Se elimino el libro con éxito.");
                        } else {
                            System.out.println("No se pudo eliminar el libro.");
                        }
                        ps.close();
                    }else{
                        System.out.println("No se encontro el libro");
                    }
                }
                rs.close();
            }catch (SQLException sqle) {
                sqle.printStackTrace();
            }
            
        }
    }

    public static void buscarEditorial(String eleccion,Statement st){
        System.out.println("Escribe el nombre de la editorial que buscas");
        String editorial = sc.nextLine();
        int contador = 0;
        System.out.println("Los libros con esta editorial:");
        try{
            ResultSet rs = st.executeQuery("SELECT isbn,titulo,editorial FROM public.libros WHERE editorial LIKE %" + editorial + "%;");
            while(rs.next()){
                    System.out.println(contador + "- ISBN: " + rs.getString(1) + " Titulo: " + rs.getString(2) + " Editorial: " + rs.getString(4));
                    contador++;
            }
            rs.close();
        }catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        System.out.println("No se encontro ninguna coincidencia");
        System.out.println("Se han encontrado: " + contador + " resultados");

        if(eleccion.equals("3")){
            System.out.println("Cual es el titulo del libro quieres borrar?");
            String titulo = sc.nextLine();
            String sentenciaSql = "DELETE FROM public.libros WHERE titulo = ? ;";
            try{
                ResultSet rs = st.executeQuery("SELECT * FROM libros");
                while(rs.next()){
                    if(rs.getString(2).equals(titulo)){
                        System.out.println("Eliminando el libro: \n ISBN: " + rs.getString(1) + " Titulo: " + rs.getString(2));
                        PreparedStatement ps = st.getConnection().prepareStatement(sentenciaSql);
                        ps.setString(1, titulo);//parametro del delete
                        int filasAfectadas = ps.executeUpdate();
                        if (filasAfectadas > 0) {
                            System.out.println("Se elimino el libro con éxito.");
                        } else {
                            System.out.println("No se pudo eliminar el libro.");
                        }
                        ps.close();
                    }else{
                        System.out.println("No se encontro el libro");
                    }
                }
                rs.close();
            }catch (SQLException sqle) {
                sqle.printStackTrace();
            }
            
        }
    }

    public static void buscarUbicacion(String eleccion,Statement st){
        System.out.println("Escribe el nombre de la ubicacion que buscas");
        int ubicacion = sc.nextInt();
        int contador = 0;
        System.out.println("Los libros con esta editorial:");
        try{
            ResultSet rs = st.executeQuery("SELECT isbn,titulo,ubicacion FROM public.libros WHERE ubicacion = " + ubicacion + ";");
            while(rs.next()){
                    System.out.println(contador + "- ISBN: " + rs.getString(1) + " Titulo: " + rs.getString(2) + " Ubicacion: " + rs.getString(6));
                    contador++;
            }
            rs.close();
        }catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        System.out.println("No se encontro ninguna coincidencia");
        System.out.println("Se han encontrado: " + contador + " resultados");

        if(eleccion.equals("3")){
            System.out.println("Cual es el titulo del libro quieres borrar?");
            String titulo = sc.nextLine();
            String sentenciaSql = "DELETE FROM public.libros WHERE titulo = ? ;";
            try{
                ResultSet rs = st.executeQuery("SELECT * FROM libros");
                while(rs.next()){
                    if(rs.getString(2).equals(titulo)){
                        System.out.println("Eliminando el libro: \n ISBN: " + rs.getString(1) + " Titulo: " + rs.getString(2));
                        PreparedStatement ps = st.getConnection().prepareStatement(sentenciaSql);
                        ps.setString(1, titulo);//parametro del delete
                        int filasAfectadas = ps.executeUpdate();
                        if (filasAfectadas > 0) {
                            System.out.println("Se elimino el libro con éxito.");
                        } else {
                            System.out.println("No se pudo eliminar el libro.");
                        }
                        ps.close();
                    }else{
                        System.out.println("No se encontro el libro");
                    }
                }
                rs.close();
            }catch (SQLException sqle) {
                sqle.printStackTrace();
            }
            
        }
    }

    public static void buscarISBN(String eleccion,Statement st){
        System.out.println("Escribe el nombre del isbn que buscas");
        String isbn = sc.nextLine();
        int contador = 0;
        System.out.println("Los libros con este isbn:");
        try{
            ResultSet rs = st.executeQuery("SELECT isbn,titulo FROM public.libros WHERE isbn = '" + isbn + "';");
            while(rs.next()){
                    System.out.println(contador + "- ISBN: " + rs.getString(1) + " Titulo: " + rs.getString(2));
                    contador++;
            }
            rs.close();
        }catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        System.out.println("No se encontro ninguna coincidencia");
        System.out.println("Se han encontrado: " + contador + " resultados");

        if(eleccion.equals("3")){
            System.out.println("Cual es el titulo del libro quieres borrar?");
            String titulo = sc.nextLine();
            String sentenciaSql = "DELETE FROM public.libros WHERE titulo = ? ;";
            try{
                ResultSet rs = st.executeQuery("SELECT * FROM libros");
                while(rs.next()){
                    if(rs.getString(2).equals(titulo)){
                        System.out.println("Eliminando el libro: \n ISBN: " + rs.getString(1) + " Titulo: " + rs.getString(2));
                        PreparedStatement ps = st.getConnection().prepareStatement(sentenciaSql);
                        ps.setString(1, titulo);//parametro del delete
                        int filasAfectadas = ps.executeUpdate();
                        if (filasAfectadas > 0) {
                            System.out.println("Se elimino el libro con éxito.");
                        } else {
                            System.out.println("No se pudo eliminar el libro.");
                        }
                        ps.close();
                    }else{
                        System.out.println("No se encontro el libro");
                    }
                }
                rs.close();
            }catch (SQLException sqle) {
                sqle.printStackTrace();
            }
            
        }
    }

    public static void buscarEmpleado(String eleccion,Statement st){
        System.out.println("Escribe el nombre del empleado que buscas");
        String emple = sc.nextLine();
        int contador = 0;
        System.out.println("Los libros con esta empleado:");
        try{
            ResultSet rs = st.executeQuery("SELECT isbn,titulo,nomemple FROM public.libros WHERE nomemple = '" + emple + "';");
            while(rs.next()){
                    System.out.println(contador + "- ISBN: " + rs.getString(1) + " Titulo: " + rs.getString(2) + " Empleado: " + rs.getString(8));
                    contador++;
            }
            rs.close();
        }catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        System.out.println("No se encontro ninguna coincidencia");//CORREGIR
        System.out.println("Se han encontrado: " + contador + " resultados");

        if(eleccion.equals("3")){
            System.out.println("Cual es el titulo del libro quieres borrar?");
            String titulo = sc.nextLine();
            String sentenciaSql = "DELETE FROM public.libros WHERE titulo = ? ;";
            try{
                ResultSet rs = st.executeQuery("SELECT * FROM libros");
                while(rs.next()){
                    if(rs.getString(2).equals(titulo)){
                        System.out.println("Eliminando el libro: \n ISBN: " + rs.getString(1) + " Titulo: " + rs.getString(2));
                        PreparedStatement ps = st.getConnection().prepareStatement(sentenciaSql);
                        ps.setString(1, titulo);//parametro del delete
                        int filasAfectadas = ps.executeUpdate();
                        if (filasAfectadas > 0) {
                            System.out.println("Se elimino el libro con éxito.");
                        } else {
                            System.out.println("No se pudo eliminar el libro.");
                        }
                        ps.close();
                    }else{
                        System.out.println("No se encontro el libro");
                    }
                }
                rs.close();
            }catch (SQLException sqle) {
                sqle.printStackTrace();
            }
            
        }
    }


    public static void buscarPrestado(String eleccion,Statement st){
        System.out.println("Esta prestado el libro? 1-SI 2-NO");
        String prestado = sc.nextLine();
        Boolean prestamo;
        if(prestado.equals("SI")){
            prestamo = true;
        }else{
            prestamo = false;
        }

        int contador = 0;
        System.out.println("Estos son los libros:");
        try{
            ResultSet rs = st.executeQuery("SELECT isbn,titulo,estadoprestamo FROM public.libros WHERE estadoprestamo = " + prestamo + ";" );
            while(rs.next()){
                    System.out.println(contador + "- ISBN: " + rs.getString(1) + " Titulo: " + rs.getString(2) + " Estado Prestamo: " + rs.getString(4));
                    contador++;
            }
            rs.close();
        }catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        System.out.println("No se encontro ninguna coincidencia");//CORREGIR
        System.out.println("Se han encontrado: " + contador + " resultados");

        if(eleccion.equals("3")){
            System.out.println("Cual es el titulo del libro quieres borrar?");
            String titulo = sc.nextLine();
            String sentenciaSql = "DELETE FROM public.libros WHERE titulo = ? ;";
            try{
                ResultSet rs = st.executeQuery("SELECT * FROM libros");
                while(rs.next()){
                    if(rs.getString(2).equals(titulo)){
                        System.out.println("Eliminando el libro: \n ISBN: " + rs.getString(1) + " Titulo: " + rs.getString(2));
                        PreparedStatement ps = st.getConnection().prepareStatement(sentenciaSql);
                        ps.setString(1, titulo);//parametro del delete
                        int filasAfectadas = ps.executeUpdate();
                        if (filasAfectadas > 0) {
                            System.out.println("Se elimino el libro con éxito.");
                        } else {
                            System.out.println("No se pudo eliminar el libro.");
                        }
                        ps.close();
                    }else{
                        System.out.println("No se encontro el libro");
                    }
                }
                rs.close();
            }catch (SQLException sqle) {
                sqle.printStackTrace();
            }
            
        }
    }


    public static void buscarLibroUsuario(String eleccion,Statement st){
        System.out.println("Escribe el nombre del usuario que buscas");
        String user = sc.nextLine();
        int contador = 0;
        System.out.println("Los libros que han alquilado estos usuarios:");
        try{
            ResultSet rs = st.executeQuery("SELECT isbn,titulo,nomusu FROM public.libros WHERE nomusu = '" + user + "';");
            while(rs.next()){
                    System.out.println(contador + "- ISBN: " + rs.getString(1) + " Titulo: " + rs.getString(2) + " Usuario: " + rs.getString(9));
                    contador++;
            }
            rs.close();
        }catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        System.out.println("No se encontro ninguna coincidencia");//CORREGIR
        System.out.println("Se han encontrado: " + contador + " resultados");

        if(eleccion.equals("3")){
            System.out.println("Cual es el titulo del libro quieres borrar?");
            String titulo = sc.nextLine();
            String sentenciaSql = "DELETE FROM public.libros WHERE titulo = ? ;";
            try{
                ResultSet rs = st.executeQuery("SELECT * FROM libros");
                while(rs.next()){
                    if(rs.getString(2).equals(titulo)){
                        System.out.println("Eliminando el libro: \n ISBN: " + rs.getString(1) + " Titulo: " + rs.getString(2));
                        PreparedStatement ps = st.getConnection().prepareStatement(sentenciaSql);
                        ps.setString(1, titulo);//parametro del delete
                        int filasAfectadas = ps.executeUpdate();
                        if (filasAfectadas > 0) {
                            System.out.println("Se elimino el libro con éxito.");
                        } else {
                            System.out.println("No se pudo eliminar el libro.");
                        }
                        ps.close();
                    }else{
                        System.out.println("No se encontro el libro");
                    }
                }
                rs.close();
            }catch (SQLException sqle) {
                sqle.printStackTrace();
            }
            
        }
    }
    



}