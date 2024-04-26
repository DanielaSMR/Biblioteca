import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class Libro{
    private String ISBN;
    private String titulo;
    private String autor = "anonimo";
    private String editorial;
    private boolean estadoPrestamo;
    private Integer ubicacionLibro;
    private Double precio;
    private String nomBiblotecario;
    private String nomUsuario;
    public static Scanner sc = new Scanner(System.in);


    public Libro(String ISBN,String titulo,String autor,String editorial,boolean estadoPrestamo,
    Integer ubicacionLibro,Double precio,String nomBiblotecario,String nomUsuario){
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.estadoPrestamo = estadoPrestamo;
        this.ubicacionLibro = ubicacionLibro;
        this.ISBN = ISBN;
        this.precio = precio;
        this.nomBiblotecario = nomBiblotecario;
        this.nomUsuario = nomUsuario;
    }

    public Libro(){
        
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
                    break;
                case "2":
                    buscarAutor(eleccion,st);
                    break;
                case "3":
                    buscarEditorial(eleccion,st);
                    break;
                case "4":
                    buscarUbicacion(eleccion,st);
                    break;
                case "5":
                    buscarISBN(eleccion,st);
                    break;
                case "6":
                    buscarEmpleado(eleccion,st);
                    break;
                case "7":
                    buscarPrestado(eleccion,st);
                    break;
                case "8":
                    buscarLibroUsuario(eleccion,st);
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
                            st.getConnection().commit();
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
        if(contador == 0){
            System.out.println("No se encontro ninguna coincidencia");
        }
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
                            st.getConnection().commit();
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
        if(contador == 0){
            System.out.println("No se encontro ninguna coincidencia");
        }
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
                            st.getConnection().commit();
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
        if(contador == 0){
            System.out.println("No se encontro ninguna coincidencia");
        }
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
                            st.getConnection().commit();
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
        if(contador == 0){
            System.out.println("No se encontro ninguna coincidencia");
        }
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
                            st.getConnection().commit();
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
        if(contador == 0){
            System.out.println("No se encontro ninguna coincidencia");
        }
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
                            st.getConnection().commit();
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
        if(contador == 0){
            System.out.println("No se encontro ninguna coincidencia");
        }
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
                            st.getConnection().commit();
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
        if(contador == 0){
            System.out.println("No se encontro ninguna coincidencia");
        }
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
                            st.getConnection().commit();
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

    public static void alquilarLibro(Statement st) {
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
                    st.getConnection().commit();
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

    public static void devolucionLibro(Statement st) {
        System.out.println("Estos son los libros alquilados:");
        int contadorEstado = 1;
        try{
            ResultSet rs = st.executeQuery("SELECT isbn,titulo,estadoprestamo FROM public.libros WHERE estadoprestamo = true ;" );
            while(rs.next()){
                    System.out.println(contadorEstado + "- ISBN: " + rs.getString(1) + " Titulo: " + rs.getString(2) + " Estado Prestamo: " + rs.getBoolean(4));
                    contadorEstado++;
            }
            rs.close();
        }catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        System.out.println("Que libro quiere devolver?");
        String nombreLibro = sc.nextLine();

        try{
            ResultSet rs = st.executeQuery("SELECT isbn,titulo,estadoprestamo FROM public.libros WHERE titulo = " + nombreLibro + ";");
            while(rs.next()){
                if(rs.getBoolean(4)){
                    String sentenciaSql = "UPDATE public.libros SET estadoprestamo = false WHERE titulo = ?";
                    PreparedStatement ps = st.getConnection().prepareStatement(sentenciaSql);
                    ps.setString(1, nombreLibro);
                    int filasAfectadas = ps.executeUpdate();
                    if (filasAfectadas > 0) {
                        System.out.println("Se actualizo con exito");
                        st.getConnection().commit();
                    } else {
                        System.out.println("No se pudo actualizar");
                    }
                    ps.close();
                }else{
                    System.out.println("El libro que buscas no esta alquilado");
                }
                rs.close();
            }
        }catch (SQLException sqle) {
            sqle.printStackTrace();
        }

    }
    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public boolean isEstadoPrestamo() {
        return estadoPrestamo;
    }

    public void setEstadoPrestamo(boolean estadoPrestamo) {
        this.estadoPrestamo = estadoPrestamo;
    }

    public Integer getUbicacionLibro() {
        return ubicacionLibro;
    }

    public void setUbicacionLibro(Integer ubicacionLibro) {
        this.ubicacionLibro = ubicacionLibro;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String iSBN) {
        ISBN = iSBN;
    }

    public String getNomBiblotecario() {
        return nomBiblotecario;
    }

    public void setNomBiblotecario(String nomBiblotecario) {
        this.nomBiblotecario = nomBiblotecario;
    }

    public String getNomUsuario() {
        return nomUsuario;
    }

    public void setNomUsuario(String nomUsuario) {
        this.nomUsuario = nomUsuario;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    

}