import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Sincronizar{
    static ArrayList<Usuario> usus = new ArrayList<Usuario>();


    public static void añadirEmple(Statement st)throws SQLException{
        ResultSet rs = st.executeQuery("SELECT * FROM empleados");
        while (rs.next()) {
            Empleado empleTemp = new Empleado(null,null);
            empleTemp.setDNI(rs.getString("DNI"));
            empleTemp.setNombre(rs.getString("nombre"));
        }
        rs.close();

    }


    public static void añadirUsu(Statement st)throws SQLException{
        ResultSet rs = st.executeQuery("SELECT * FROM usuarios");
        while (rs.next()) {
            Usuario usuarios = new Usuario(0,null);
            usuarios.setId(rs.getInt("id"));
            usuarios.setNombre(rs.getString("nombre"));

            try{
                usus.add(usuarios);
            }catch(Exception ex){
                ex.getMessage();
            }
        }
        rs.close();


    }

    public static void añadirLibro(Statement st)throws SQLException{
        ResultSet rs = st.executeQuery("SELECT * FROM libro");
        while (rs.next()) {
            Usuario usuarios = new Usuario(0,null);
            usuarios.setId(rs.getInt("id"));
            usuarios.setNombre(rs.getString("nombre"));
        }
        rs.close();

    }
    

}