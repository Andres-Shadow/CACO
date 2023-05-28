package co.uq.proyectobases2.Logica;

public class Rango {
    private int id_rango;
    private String etiqueta;
    private int minAprendices;
    private int maxAprendices;
    private float comision;

    public Rango(int id_rango, String etiqueta, int minAprendices, int maxAprendices, float comision){
        this.id_rango = id_rango;
        this.etiqueta = etiqueta;
        this.minAprendices = minAprendices;
        this.maxAprendices = maxAprendices;
        this.comision = comision;
    }

    public int getId_rango() {
        return id_rango;
    }

    public void setId_rango(int id_rango) {
        this.id_rango = id_rango;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public int getMinAprendices() {
        return minAprendices;
    }

    public void setMinAprendices(int minAprendices) {
        this.minAprendices = minAprendices;
    }

    public int getMaxAprendices() {
        return maxAprendices;
    }

    public void setMaxAprendices(int maxAprendices) {
        this.maxAprendices = maxAprendices;
    }

    public float getComision() {
        return comision;
    }

    public void setComision(float comision) {
        this.comision = comision;
    }
}
