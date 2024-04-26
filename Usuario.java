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
                    break;
                case 2:
                    nuevoUsuario(st);
                    break;
                case 3:
                    eliminarUsuario(st);
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
                    st.getConnection().commit();
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
                        st.getConnection().commit();
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
