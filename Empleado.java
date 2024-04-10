public class Empleado {
    private String nombre;
    private String DNI;

    public Empleado(String nombre,String DNI){
        this.nombre = nombre;
        this.DNI = DNI;
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
