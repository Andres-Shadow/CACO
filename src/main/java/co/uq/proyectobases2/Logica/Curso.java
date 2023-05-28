package co.uq.proyectobases2.Logica;

public class Curso {

    private int curso_id;
    private int precio;
    private int cupos;
    private String nombre;

    public Curso(
            int curso_id,
            int precio,
            int cupos,
            String nombre
    ){
        this.curso_id = curso_id;
        this.precio = precio;
        this.cupos = cupos;
        this.nombre = nombre;
    }

    public int getCurso_id() {
        return curso_id;
    }

    public void setCurso_id(int curso_id) {
        this.curso_id = curso_id;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getCupos() {
        return cupos;
    }

    public void setCupos(int cupos) {
        this.cupos = cupos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
