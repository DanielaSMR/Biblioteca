public class Libro{
    private String titulo;
    private String autor;
    private String editorial;
    private boolean estadoPrestamo;
    private Integer ubicacionLibro;
    private String ISBN;
    private Double precio;
    private String nomBiblotecario;
    private String nomUsuario;

    public Libro(String titulo,String autor,String editorial,boolean estadoPrestamo,
    Integer ubicacionLibro,String ISBN,Double precio,String nomBiblotecario,String nomUsuario){
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