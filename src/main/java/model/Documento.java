package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Documento implements Serializable {

    private int id;

    // Relación con expediente
    private int expedienteId;

    // Relación con requisito
    private int requisitoId;

    private String nombreDocumento;

    // PENDIENTE | ENTREGADO
    private String estado;

    // Ruta del archivo en servidor
    private String rutaArchivo;

    private String observaciones;

    private LocalDate fechaRegistro;

    // ==========================
    // CONSTRUCTOR
    // ==========================
    public Documento() {
        this.estado = "PENDIENTE";
        this.fechaRegistro = LocalDate.now();
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

    public int getExpedienteId() {
        return expedienteId;
    }

    public void setExpedienteId(int expedienteId) {
        this.expedienteId = expedienteId;
    }

    public int getRequisitoId() {
        return requisitoId;
    }

    public void setRequisitoId(int requisitoId) {
        this.requisitoId = requisitoId;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    // ==========================
    // MÉTODOS DE NEGOCIO
    // ==========================

    public boolean estaEntregado() {
        return "ENTREGADO".equalsIgnoreCase(estado);
    }

    public boolean estaPendiente() {
        return "PENDIENTE".equalsIgnoreCase(estado);
    }

    public void marcarEntregado() {
        this.estado = "ENTREGADO";
    }

    public void marcarPendiente() {
        this.estado = "PENDIENTE";
    }

    public boolean tieneArchivo() {
        return rutaArchivo != null && !rutaArchivo.trim().isEmpty();
    }

    @Override
    public String toString() {
        return nombreDocumento + " (" + estado + ")";
    }
}