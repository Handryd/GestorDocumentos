package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Expediente implements Serializable {

    private int id;

    // ID visible tipo: EXP-001, EXP-001-01
    private String codigo;

    private int clienteId;

    private String nombreCliente;

    private String estado;

    private double progreso;

    private LocalDate fechaCreacion;

    private String observaciones;

    // ==========================
    // CONSTRUCTOR
    // ==========================
    public Expediente() {
        this.estado = "ACTIVO";
        this.progreso = 0.0;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoExpediente() {
        return codigo;
    }

    public void setCodigoExpediente(String codigoExpediente) {
        this.codigo = codigoExpediente;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    // Compatibilidad con el resto del proyecto

    public String getNombreDocumento() {
        return observaciones;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.observaciones = nombreDocumento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getProgreso() {
        return progreso;
    }

    public void setProgreso(double progreso) {
        this.progreso = progreso;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaRegistro() {
        return fechaCreacion;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaCreacion = fechaRegistro;
    }
    
    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    // ==========================
    // MÉTODOS DE NEGOCIO
    // ==========================

    public void actualizarProgreso(double progreso) {
        this.progreso = progreso;

        if (progreso >= 100.0) {
            this.estado = "COMPLETADO";
        }
    }

    public boolean estaCompleto() {
        return "COMPLETADO".equalsIgnoreCase(estado);
    }

    public boolean estaActivo() {
        return "ACTIVO".equalsIgnoreCase(estado);
    }

    public String obtenerEstadoVisual() {

        if (progreso >= 100) {
            return "🟢 COMPLETADO";
        } else if (progreso >= 50) {
            return "🟡 EN PROCESO";
        } else {
            return "🔴 INICIAL";
        }
    }

    @Override
    public String toString() {
        return codigo + " - " + nombreCliente;
    }
}