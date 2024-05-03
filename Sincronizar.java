import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Sincronizar{


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
        ResultSet rs = st.executeQuery("SELECT * FROM usuario");
        while (rs.next()) {
            Usuario usuarios = new Usuario(0,null);
            usuarios.setId(rs.getInt("id"));
            usuarios.setNombre(rs.getString("nombre"));
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