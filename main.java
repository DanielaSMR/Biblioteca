import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);




        try {
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
        connection.close();
    }
}