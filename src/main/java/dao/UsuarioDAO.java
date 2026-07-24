package dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Usuario;
import util.ConexionBD;

public class UsuarioDAO {

    private boolean esquemaCompleto(Connection con) throws SQLException {
        return tieneColumna(con, "username")
                && tieneColumna(con, "nombre")
                && tieneColumna(con, "activo");
    }

    private boolean tieneColumna(Connection con, String columna)
            throws SQLException {

        DatabaseMetaData meta = con.getMetaData();
        String catalogo = con.getCatalog();

        try (ResultSet rs = meta.getColumns(catalogo, null, "usuarios", columna)) {
            if (rs.next()) {
                return true;
            }
        }

        try (ResultSet rs = meta.getColumns(catalogo, null, "USUARIOS", columna)) {
            return rs.next();
        }
    }

    private String columnaAcceso(Connection con) throws SQLException {
        if (tieneColumna(con, "username")) {
            return "username";
        }
        return "usuario";
    }

    public boolean registrarUsuario(Usuario usuario) {

        try (Connection con = ConexionBD.getConnection()) {

            if (con == null) {
                return false;
            }

            if (esquemaCompleto(con)) {
                return registrarUsuarioCompleto(con, usuario);
            }

            return registrarUsuarioBasico(con, usuario);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean registrarUsuarioCompleto(
            Connection con,
            Usuario usuario) throws SQLException {

        String sql = """
                INSERT INTO usuarios
                (
                    nombre,
                    apellido,
                    username,
                    password,
                    correo,
                    rol,
                    activo
                )
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getUsername());
            ps.setString(4, usuario.getPassword());
            ps.setString(5, usuario.getCorreo());
            ps.setString(6, usuario.getRol());
            ps.setBoolean(7, usuario.isActivo());
            return ps.executeUpdate() > 0;
        }
    }

    private boolean registrarUsuarioBasico(
            Connection con,
            Usuario usuario) throws SQLException {

        String sql = """
                INSERT INTO usuarios
                (usuario, password, rol)
                VALUES (?, ?, ?)
                """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getPassword());
            ps.setString(3, usuario.getRol());
            return ps.executeUpdate() > 0;
        }
    }

    public Usuario validarLogin(String username, String password) {

        try (Connection con = ConexionBD.getConnection()) {

            if (con == null) {
                return null;
            }

            String colAcceso = columnaAcceso(con);
            boolean completo = esquemaCompleto(con);

            String sql = completo
                    ? """
                    SELECT *
                    FROM usuarios
                    WHERE %s = ?
                    AND password = ?
                    AND activo = TRUE
                    """.formatted(colAcceso)
                    : """
                    SELECT *
                    FROM usuarios
                    WHERE %s = ?
                    AND password = ?
                    """.formatted(colAcceso);

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.setString(2, password);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapearUsuario(rs);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Usuario> listarUsuarios() {

        List<Usuario> lista = new ArrayList<>();

        try (Connection con = ConexionBD.getConnection()) {

            if (con == null) {
                return lista;
            }

            String orden = esquemaCompleto(con) ? "nombre" : "id";

            String sql = """
                    SELECT *
                    FROM usuarios
                    ORDER BY %s
                    """.formatted(orden);

            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    lista.add(mapearUsuario(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Usuario buscarPorId(int id) {

        String sql = """
                SELECT *
                FROM usuarios
                WHERE id = ?
                """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (con == null) {
                return null;
            }

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Usuario buscarPorUsername(String username) {

        try (Connection con = ConexionBD.getConnection()) {

            if (con == null) {
                return null;
            }

            String colAcceso = columnaAcceso(con);
            String sql = """
                    SELECT *
                    FROM usuarios
                    WHERE %s = ?
                    """.formatted(colAcceso);

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, username);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return mapearUsuario(rs);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean actualizarUsuario(Usuario usuario) {

        try (Connection con = ConexionBD.getConnection()) {

            if (con == null) {
                return false;
            }

            if (esquemaCompleto(con)) {
                return actualizarUsuarioCompleto(con, usuario);
            }

            return actualizarUsuarioBasico(con, usuario);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean actualizarUsuarioCompleto(
            Connection con,
            Usuario usuario) throws SQLException {

        String sql = """
                UPDATE usuarios
                SET
                    nombre = ?,
                    apellido = ?,
                    username = ?,
                    password = ?,
                    correo = ?,
                    rol = ?,
                    activo = ?
                WHERE id = ?
                """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getUsername());
            ps.setString(4, usuario.getPassword());
            ps.setString(5, usuario.getCorreo());
            ps.setString(6, usuario.getRol());
            ps.setBoolean(7, usuario.isActivo());
            ps.setInt(8, usuario.getId());
            return ps.executeUpdate() > 0;
        }
    }

    private boolean actualizarUsuarioBasico(
            Connection con,
            Usuario usuario) throws SQLException {

        String sql = """
                UPDATE usuarios
                SET usuario = ?, password = ?, rol = ?
                WHERE id = ?
                """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getPassword());
            ps.setString(3, usuario.getRol());
            ps.setInt(4, usuario.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminarUsuario(int id) {

        String sql = """
                DELETE FROM usuarios
                WHERE id = ?
                """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (con == null) {
                return false;
            }

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int contarUsuarios() {

        String sql = """
                SELECT COUNT(*) total
                FROM usuarios
                """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (con == null) {
                return 0;
            }

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {

        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id"));

        String username = leerColumna(rs, "username");
        if (username == null) {
            username = leerColumna(rs, "usuario");
        }
        usuario.setUsername(username);

        String nombre = leerColumna(rs, "nombre");
        if (nombre == null || nombre.isBlank()) {
            nombre = username;
        }
        usuario.setNombre(nombre);

        String apellido = leerColumna(rs, "apellido");
        usuario.setApellido(apellido == null ? "" : apellido);

        usuario.setPassword(rs.getString("password"));
        usuario.setCorreo(leerColumna(rs, "correo"));
        usuario.setRol(rs.getString("rol"));

        String activo = leerColumna(rs, "activo");
        usuario.setActivo(
                activo == null
                        || "1".equals(activo)
                        || "true".equalsIgnoreCase(activo)
        );

        return usuario;
    }

    private String leerColumna(ResultSet rs, String columna) throws SQLException {
        try {
            return rs.getString(columna);
        } catch (SQLException e) {
            return null;
        }
    }
}
