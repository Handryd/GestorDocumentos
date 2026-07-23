package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Usuario;
import util.ConexionBD;

public class UsuarioDAO {

    // ==========================
    // REGISTRAR USUARIO
    // ==========================
    public boolean registrarUsuario(Usuario usuario) {

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

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getUsername());
            ps.setString(4, usuario.getPassword());
            ps.setString(5, usuario.getCorreo());
            ps.setString(6, "CLIENTE");
            ps.setBoolean(7, usuario.isActivo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }

    // ==========================
    // LOGIN
    // ==========================
    public Usuario validarLogin(
            String username,
            String password) {

        String sql = """
                SELECT *
                FROM usuarios
                WHERE username = ?
                AND password = ?
                AND activo = TRUE
                """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

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

    // ==========================
    // LISTAR USUARIOS
    // ==========================
    public List<Usuario> listarUsuarios() {

        List<Usuario> lista = new ArrayList<>();

        String sql = """
                SELECT *
                FROM usuarios
                ORDER BY nombre
                """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                lista.add(
                        mapearUsuario(rs)
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return lista;
    }

    // ==========================
    // BUSCAR POR ID
    // ==========================
    public Usuario buscarPorId(int id) {

        String sql = """
                SELECT *
                FROM usuarios
                WHERE id = ?
                """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

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

    // ==========================
    // BUSCAR POR USERNAME
    // ==========================
    public Usuario buscarPorUsername(
            String username) {

        String sql = """
                SELECT *
                FROM usuarios
                WHERE username = ?
                """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);

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

    // ==========================
    // ACTUALIZAR USUARIO
    // ==========================
    public boolean actualizarUsuario(
            Usuario usuario) {

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

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getUsername());
            ps.setString(4, usuario.getPassword());
            ps.setString(5, usuario.getCorreo());
            ps.setString(6, usuario.getRol());
            ps.setBoolean(7, usuario.isActivo());
            ps.setInt(8, usuario.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }

    // ==========================
    // ELIMINAR USUARIO
    // ==========================
    public boolean eliminarUsuario(int id) {

        String sql = """
                DELETE FROM usuarios
                WHERE id = ?
                """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }

    // ==========================
    // TOTAL USUARIOS
    // ==========================
    public int contarUsuarios() {

        String sql = """
                SELECT COUNT(*) total
                FROM usuarios
                """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {

                return rs.getInt("total");
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return 0;
    }

    // ==========================
    // MAPEAR RESULTSET
    // ==========================
    private Usuario mapearUsuario(
            ResultSet rs)
            throws SQLException {

        Usuario usuario = new Usuario();

        usuario.setId(
                rs.getInt("id")
        );

        usuario.setNombre(
                rs.getString("nombre")
        );

        usuario.setApellido(
                rs.getString("apellido")
        );

        usuario.setUsername(
                rs.getString("username")
        );

        usuario.setPassword(
                rs.getString("password")
        );

        usuario.setCorreo(
                rs.getString("correo")
        );

        usuario.setRol(
                rs.getString("rol")
        );

        usuario.setActivo(
                rs.getBoolean("activo")
        );

        return usuario;
    }
}