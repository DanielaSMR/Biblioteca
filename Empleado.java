import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Empleado {
    private String nombre;
    private String DNI;
    public static Scanner sc = new Scanner(System.in);


    public Empleado(String DNI,String nombre){
        this.nombre = nombre;
        this.DNI = DNI;
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
                    break;
                case 2:
                    nuevoEmpleado(st);
                    break;
                case 3:
                    eliminarEmpleado(st);
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
                    st.getConnection().commit();
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


    public String getDNI() {
        return DNI;
    }


    public void setDNI(String dNI) {
        DNI = dNI;
    }

    

}
