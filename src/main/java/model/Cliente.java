package model;

import java.io.Serializable;

public class Cliente implements Serializable {

    private int id;

    // Código visible del expediente
    // Ejemplo: EXP-001, EXP-001-02
    private String codigoExpediente;

    // Expediente principal al que pertenece
    // Ejemplo: EXP-001
    private String expedientePadre;

    private String nombre;
    private String correo;
    private String telefono;

    // ==========================
    // CONSTRUCTOR VACÍO
    // ==========================
    public Cliente() {
    }

    // ==========================
    // CONSTRUCTOR COMPLETO
    // ==========================
    public Cliente(int id,
                   String codigoExpediente,
                   String expedientePadre,
                   String nombre,
                   String correo,
                   String telefono) {

        this.id = id;
        this.codigoExpediente = codigoExpediente;
        this.expedientePadre = expedientePadre;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
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


    public String getCodigoExpediente() {
        return codigoExpediente;
    }

    public void setCodigoExpediente(String codigoExpediente) {
        this.codigoExpediente = codigoExpediente;
    }


    public String getExpedientePadre() {
        return expedientePadre;
    }

    public void setExpedientePadre(String expedientePadre) {
        this.expedientePadre = expedientePadre;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }


    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // ==========================
    // MÉTODOS AUXILIARES
    // ==========================

    /**
     * Indica si el expediente es principal.
     */
    public boolean esExpedientePrincipal() {

        return expedientePadre == null
                || expedientePadre.trim().isEmpty();
    }

    /**
     * Devuelve el expediente padre o "Principal".
     */
    public String getDescripcionPadre() {

        return esExpedientePrincipal()
                ? "Principal"
                : expedientePadre;
    }

    @Override
    public String toString() {

        return codigoExpediente
                + " - "
                + nombre;
    }
}