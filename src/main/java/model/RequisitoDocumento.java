package model;

import java.io.Serializable;

public class RequisitoDocumento implements Serializable {

    private int id;

    private String nombre;

    private String descripcion;

    private boolean activo;

    // ==========================
    // CONSTRUCTOR VACÍO
    // ==========================
    public RequisitoDocumento() {
    }

    // ==========================
    // GETTERS Y SETTERS
    // ==========================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    // ==========================
    // MÉTODOS AUXILIARES
    // ==========================

    public boolean estaActivo() {
        return activo;
    }

    @Override
    public String toString() {
        return nombre;
    }
}