package dao;

import model.RequisitoDocumento;
import util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequisitoDocumentoDAO {

    // ==========================
    // INSERTAR REQUISITO
    // ==========================
    public boolean insertar(RequisitoDocumento r) {

        String sql = """
                INSERT INTO requisitos_documento
                (nombre, descripcion, activo)
                VALUES (?, ?, ?)
                """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, r.getNombre());
            ps.setString(2, r.getDescripcion());
            ps.setBoolean(3, r.isActivo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }

    // ==========================
    // LISTAR TODOS
    // ==========================
    public List<RequisitoDocumento> listarTodos() {

        List<RequisitoDocumento> lista = new ArrayList<>();

        String sql = """
                SELECT *
                FROM requisitos_documento
                ORDER BY nombre
                """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                RequisitoDocumento r = new RequisitoDocumento();

                r.setId(rs.getInt("id"));
                r.setNombre(rs.getString("nombre"));
                r.setDescripcion(rs.getString("descripcion"));
                r.setActivo(rs.getBoolean("activo"));

                lista.add(r);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return lista;
    }

    // ==========================
    // LISTAR SOLO ACTIVOS
    // ==========================
    public List<RequisitoDocumento> listarActivos() {

        List<RequisitoDocumento> lista = new ArrayList<>();

        String sql = """
                SELECT *
                FROM requisitos_documento
                WHERE activo = TRUE
                ORDER BY nombre
                """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                RequisitoDocumento r = new RequisitoDocumento();

                r.setId(rs.getInt("id"));
                r.setNombre(rs.getString("nombre"));
                r.setDescripcion(rs.getString("descripcion"));
                r.setActivo(rs.getBoolean("activo"));

                lista.add(r);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return lista;
    }

    // ==========================
    // BUSCAR POR ID
    // ==========================
    public RequisitoDocumento buscarPorId(int id) {

        String sql = """
                SELECT *
                FROM requisitos_documento
                WHERE id = ?
                """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    RequisitoDocumento r = new RequisitoDocumento();

                    r.setId(rs.getInt("id"));
                    r.setNombre(rs.getString("nombre"));
                    r.setDescripcion(rs.getString("descripcion"));
                    r.setActivo(rs.getBoolean("activo"));

                    return r;
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }

    // ==========================
    // ACTUALIZAR
    // ==========================
    public boolean actualizar(RequisitoDocumento r) {

        String sql = """
                UPDATE requisitos_documento
                SET nombre = ?,
                    descripcion = ?,
                    activo = ?
                WHERE id = ?
                """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, r.getNombre());
            ps.setString(2, r.getDescripcion());
            ps.setBoolean(3, r.isActivo());
            ps.setInt(4, r.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }

    // ==========================
    // ELIMINAR
    // ==========================
    public boolean eliminar(int id) {

        String sql = """
                DELETE FROM requisitos_documento
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
    // ACTIVAR / DESACTIVAR
    // ==========================
    public boolean cambiarEstado(int id, boolean activo) {

        String sql = """
                UPDATE requisitos_documento
                SET activo = ?
                WHERE id = ?
                """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBoolean(1, activo);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }
}