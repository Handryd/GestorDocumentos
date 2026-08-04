package service;

import dao.UsuarioDAO;
import model.Usuario;

public class AuthService {

    private final UsuarioDAO usuarioDAO;

    public AuthService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    // ==========================
    // INICIAR SESIÓN
    // ==========================
    public Usuario login(
            String username,
            String password) {

        if (username == null ||
                username.trim().isEmpty()) {

            System.out.println(
                    "El usuario es obligatorio."
            );

            return null;
        }

        if (password == null ||
                password.trim().isEmpty()) {

            System.out.println(
                    "La contraseña es obligatoria."
            );

            return null;
        }

        Usuario usuario =
                usuarioDAO.validarLogin(
                        username,
                        password
                );

        if (usuario == null) {

            System.out.println(
                    "Credenciales incorrectas."
            );

            return null;
        }

        if (!usuario.isActivo()) {

            System.out.println(
                    "Usuario inactivo."
            );

            return null;
        }

        return usuario;
    }

    // ==========================
    // VALIDAR ADMINISTRADOR
    // ==========================
    public boolean esAdministrador(
            Usuario usuario) {

        return usuario != null
                && usuario.isActivo()
                && "ADMINISTRADOR"
                .equalsIgnoreCase(
                        usuario.getRol()
                );
    }

    // ==========================
    // VALIDAR CLIENTE
    // ==========================
    public boolean esCliente(
            Usuario usuario) {

        return usuario != null
                && usuario.isActivo()
                && "CLIENTE"
                .equalsIgnoreCase(
                        usuario.getRol()
                );
    }

    // ==========================
    // VALIDAR USUARIO
    // ==========================
    public boolean esUsuario(
            Usuario usuario) {

        return usuario != null
                && usuario.isActivo()
                && "USUARIO"
                .equalsIgnoreCase(
                        usuario.getRol()
                );
    }

    // ==========================
    // VALIDAR ACCESO GENERAL
    // ==========================
    public boolean tieneAcceso(
            Usuario usuario) {

        return usuario != null
                && usuario.isActivo();
    }

    // ==========================
    // BUSCAR USUARIO
    // ==========================
    public Usuario buscarUsuario(
            String username) {

        if (username == null ||
                username.trim().isEmpty()) {

            return null;
        }

        return usuarioDAO
                .buscarPorUsername(
                        username
                );
    }

    // ==========================
    // VALIDAR EXISTENCIA
    // ==========================
    public boolean existeUsuario(
            String username) {

        return buscarUsuario(
                username
        ) != null;
    }

    // ==========================
    // VALIDAR REGISTRO
    // ==========================
    public boolean validarNuevoUsuario(
            Usuario usuario) {

        if (usuario == null) {
            return false;
        }

        if (usuario.getNombre() == null ||
                usuario.getNombre()
                        .trim()
                        .isEmpty()) {

            return false;
        }

        if (usuario.getUsername() == null ||
                usuario.getUsername()
                        .trim()
                        .isEmpty()) {

            return false;
        }

        if (usuario.getPassword() == null ||
                usuario.getPassword()
                        .trim()
                        .isEmpty()) {

            return false;
        }

        if (existeUsuario(
                usuario.getUsername())) {

            System.out.println(
                    "El usuario ya existe."
            );

            return false;
        }

        return true;
    }

    // ==========================
    // REGISTRO PÚBLICO (SIEMPRE USUARIO)
    // ==========================
    public String registrarPublico(Usuario usuario) {

        if (usuario == null) {
            return "Datos inválidos.";
        }

        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            return "El nombre es obligatorio.";
        }

        if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()) {
            return "El usuario de acceso es obligatorio.";
        }

        if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
            return "La contraseña es obligatoria.";
        }

        if (existeUsuario(usuario.getUsername().trim())) {
            return "Ese nombre de usuario ya está registrado.";
        }

        usuario.setNombre(usuario.getNombre().trim());
        usuario.setApellido(
                usuario.getApellido() == null
                        ? ""
                        : usuario.getApellido().trim()
        );
        usuario.setUsername(usuario.getUsername().trim());
        usuario.setCorreo(
                usuario.getCorreo() == null || usuario.getCorreo().isBlank()
                        ? null
                        : usuario.getCorreo().trim()
        );
        usuario.setRol("USUARIO");
        usuario.setActivo(true);

        if (!usuarioDAO.registrarUsuario(usuario)) {
            return "No se pudo completar el registro.";
        }

        return null;
    }

    // ==========================
    // OBTENER ROL
    // ==========================
    public String obtenerRol(
            Usuario usuario) {

        if (usuario == null) {
            return "";
        }

        return usuario.getRol();
    }

    // ==========================
    // VALIDAR SESIÓN
    // ==========================
    public boolean sesionValida(
            Usuario usuario) {

        return usuario != null
                && usuario.isActivo();
    }
}