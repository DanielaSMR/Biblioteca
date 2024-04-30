import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Sincronizar{


    public static void añadirEmple(Statement st)throws SQLException{
        ResultSet rs = st.executeQuery("SELECT * FROM empleados");
        while (rs.next()) {
            Empleado empleados = new Empleado(null,null);
            empleados.setDNI(rs.getString("DNI"));
            empleados.setNombre(rs.getString("nombre"));
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
        ResultSet rs = st.executeQuery("SELECT * FROM Libro");
        while (rs.next()) {
            Usuario usuarios = new Usuario(0,null);
            usuarios.setId(rs.getInt("id"));
            usuarios.setNombre(rs.getString("nombre"));
        }
        rs.close();

    }
    

}