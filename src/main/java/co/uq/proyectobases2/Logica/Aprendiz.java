package co.uq.proyectobases2.Logica;

import java.sql.Date;
import java.util.ArrayList;

public class Aprendiz {
    private int id_aprendiz;
    private int id_rango;
    private int id_equipo;
    private int id_monitor;
    private Date fecha_afiliacion;
    private String estado;
    private String nombre;
    private String email;
    private String telefono;
    private ArrayList<Aprendiz> afiliados;

    public Aprendiz(
            int id_aprendiz,
            int id_rango,
            int id_equipo,
            int id_monitor,
            Date fecha_afiliacion,
            String estado,
            String nombre,
            String email,
            String telefono
    ){
        this.id_aprendiz = id_aprendiz;
        this.id_rango = id_rango;
        this.id_equipo = id_equipo;
        this.id_monitor = id_monitor;
        this.fecha_afiliacion = fecha_afiliacion;
        this.nombre = nombre;
        this.estado = estado;
        this.email = email;
        this.telefono = telefono;
        this.afiliados = new ArrayList<>();
    }

    public int getId_aprendiz() {
        return id_aprendiz;
    }

    public void setId_aprendiz(int id_aprendiz) {
        this.id_aprendiz = id_aprendiz;
    }

    public int getId_rango() {
        return id_rango;
    }

    public void setId_rango(int id_rango) {
        this.id_rango = id_rango;
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

    public Date getFecha_afiliacion() {
        return fecha_afiliacion;
    }

    public void setFecha_afiliacion(Date fecha_afiliacion) {
        this.fecha_afiliacion = fecha_afiliacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public ArrayList<Aprendiz> getAfiliados() {
        return afiliados;
    }

    public void setAfiliados(ArrayList<Aprendiz> afiliados) {
        this.afiliados = afiliados;
    }
}
