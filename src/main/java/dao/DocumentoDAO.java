package dao;

import model.Documento;
import util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentoDAO {

    // ==========================
    // INSERTAR DOCUMENTO
    // ==========================
    public boolean insertar(Documento d) {

        String sql = """
            INSERT INTO documentos
            (
                expediente_id,
                requisito_id,
                nombre_documento,
                estado,
                ruta_archivo,
                observaciones,
                fecha_registro
            )
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, d.getExpedienteId());
            ps.setInt(2, d.getRequisitoId());
            ps.setString(3, d.getNombreDocumento());
            ps.setString(4, d.getEstado());
            ps.setString(5, d.getRutaArchivo());
            ps.setString(6, d.getObservaciones());
            ps.setDate(7, Date.valueOf(d.getFechaRegistro()));

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // ==========================
    // LISTAR POR EXPEDIENTE
    // ==========================
    public List<Documento> listarPorExpediente(int expedienteId) {

        List<Documento> lista = new ArrayList<>();

        String sql = """
            SELECT *
            FROM documentos
            WHERE expediente_id = ?
            ORDER BY id DESC
        """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, expedienteId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Documento d = new Documento();

                d.setId(rs.getInt("id"));
                d.setExpedienteId(rs.getInt("expediente_id"));
                d.setRequisitoId(rs.getInt("requisito_id"));
                d.setNombreDocumento(rs.getString("nombre_documento"));
                d.setEstado(rs.getString("estado"));
                d.setRutaArchivo(rs.getString("ruta_archivo"));
                d.setObservaciones(rs.getString("observaciones"));

                Date fecha = rs.getDate("fecha_registro");
                if (fecha != null) {
                    d.setFechaRegistro(fecha.toLocalDate());
                }

                lista.add(d);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ==========================
    // ACTUALIZAR ESTADO
    // ==========================
    public boolean actualizarEstado(int id, String estado) {

        String sql = """
            UPDATE documentos
            SET estado = ?
            WHERE id = ?
        """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, estado);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // ==========================
    // ACTUALIZAR ARCHIVO (RUTA PDF)
    // ==========================
    public boolean actualizarArchivo(int id, String rutaArchivo) {

        String sql = """
            UPDATE documentos
            SET ruta_archivo = ?
            WHERE id = ?
        """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, rutaArchivo);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // ==========================
    // ELIMINAR DOCUMENTO
    // ==========================
    public boolean eliminar(int id) {

        String sql = """
            DELETE FROM documentos
            WHERE id = ?
        """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // ==========================
    // CONTAR POR EXPEDIENTE
    // ==========================
    public int contarPorExpediente(int expedienteId) {

        String sql = """
            SELECT COUNT(*) total
            FROM documentos
            WHERE expediente_id = ?
        """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, expedienteId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    // ==========================
    // CONTAR ENTREGADOS
    // ==========================
    public int contarEntregados(int expedienteId) {

        String sql = """
            SELECT COUNT(*) total
            FROM documentos
            WHERE expediente_id = ?
            AND estado = 'ENTREGADO'
        """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, expedienteId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}