package co.uq.proyectobases2.Logica;

public class Equipo {
    private int id_equipo;
    private int id_monitor;
    private String nombre;

    public Equipo(int id_equipo, int id_mentor, String nombre){
        this.id_equipo = id_equipo;
        this.id_monitor = id_mentor;
        this.nombre = nombre;
    }

    public int getId_equipo() {
        return id_equipo;
    }

    public void setId_equipo(int id_equipo) {
        this.id_equipo = id_equipo;
    }

    public int getId_monitor() {
        return id_monitor;
    }

    public void setId_monitor(int id_monitor) {
        this.id_monitor = id_monitor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
