package model;

import java.io.Serializable;

public class Usuario implements Serializable {

    private int id;
    private String nombre;
    private String apellido;
    private String username;
    private String password;
    private String correo;
    private String rol;
    private boolean activo;

    // ==========================
    // CONSTRUCTOR VACÍO
    // ==========================
    public Usuario() {
    }

    // ==========================
    // CONSTRUCTOR COMPLETO
    // ==========================
    public Usuario(
            int id,
            String nombre,
            String apellido,
            String username,
            String password,
            String correo,
            String rol,
            boolean activo) {

        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.password = password;
        this.correo = correo;
        this.rol = rol;
        this.activo = activo;
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


    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }


    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
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

    public boolean esAdministrador() {
        return "ADMINISTRADOR".equalsIgnoreCase(rol);
    }

    public boolean esCliente() {
        return "CLIENTE".equalsIgnoreCase(rol);
    }

    public boolean esAsesor() {
        return "ASESOR".equalsIgnoreCase(rol);
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    @Override
    public String toString() {
        return getNombreCompleto()
                + " - "
                + rol;
    }
}