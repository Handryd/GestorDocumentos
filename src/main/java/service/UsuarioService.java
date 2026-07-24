package service;

import dao.UsuarioDAO;
import model.Usuario;

import java.util.List;
import java.util.Set;

public class UsuarioService {

    private static final Set<String> ROLES_PERMITIDOS = Set.of(
            "ADMINISTRADOR",
            "CLIENTE",
            "USUARIO"
    );

    private final UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public List<Usuario> listarUsuarios() {
        return usuarioDAO.listarUsuarios();
    }

    public String registrarUsuario(Usuario usuario) {
        if (usuario == null) {
            return "Datos de usuario inválidos.";
        }

        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            return "El nombre es obligatorio.";
        }

        if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()) {
            return "El nombre de usuario es obligatorio.";
        }

        if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
            return "La contraseña es obligatoria.";
        }

        if (usuario.getRol() == null || !ROLES_PERMITIDOS.contains(usuario.getRol().toUpperCase())) {
            return "Seleccione un rol válido.";
        }

        if (usuarioDAO.buscarPorUsername(usuario.getUsername().trim()) != null) {
            return "Ya existe un usuario con ese nombre de acceso.";
        }

        usuario.setNombre(usuario.getNombre().trim());
        usuario.setApellido(
                usuario.getApellido() == null
                        ? ""
                        : usuario.getApellido().trim()
        );
        usuario.setUsername(usuario.getUsername().trim());
        usuario.setCorreo(
                usuario.getCorreo() == null
                        ? null
                        : usuario.getCorreo().trim()
        );
        usuario.setRol(usuario.getRol().toUpperCase());
        usuario.setActivo(true);

        if (!usuarioDAO.registrarUsuario(usuario)) {
            return "No se pudo registrar el usuario.";
        }

        return null;
    }
}
