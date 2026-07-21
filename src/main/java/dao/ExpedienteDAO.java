package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Expediente;
import util.ConexionBD;

public class ExpedienteDAO {

    // ==========================
    // GENERAR CÓDIGO AUTOMÁTICO
    // ==========================
    public String generarCodigoBase() {

        String sql = """
                SELECT COUNT(*) total
                FROM expedientes
                """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {

                int total = rs.getInt("total") + 1;

                return String.format("EXP-%03d", total);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "EXP-001";
    }

    // ==========================
    // CREAR EXPEDIENTE
    // ==========================
    public boolean insertar(Expediente e) {

        String sql = """
                INSERT INTO expedientes
                (
                    codigo,
                    cliente_id,
                    nombre_cliente,
                    estado,
                    progreso,
                    fecha_creacion,
                    observaciones
                )
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getCodigo());
            ps.setInt(2, e.getClienteId());
            ps.setString(3, e.getNombreCliente());
            ps.setString(4, e.getEstado());
            ps.setDouble(5, e.getProgreso());
            ps.setDate(6, Date.valueOf(e.getFechaCreacion()));
            ps.setString(7, e.getObservaciones());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // ==========================
    // LISTAR TODOS
    // ==========================
    public List<Expediente> listarTodos() {

        List<Expediente> lista = new ArrayList<>();

        String sql = """
                SELECT *
                FROM expedientes
                ORDER BY id DESC
                """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Expediente e = new Expediente();

                e.setId(rs.getInt("id"));
                e.setCodigo(rs.getString("codigo"));
                e.setClienteId(rs.getInt("cliente_id"));
                e.setNombreCliente(rs.getString("nombre_cliente"));
                e.setEstado(rs.getString("estado"));
                e.setProgreso(rs.getDouble("progreso"));
                e.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());
                e.setObservaciones(rs.getString("observaciones"));

                lista.add(e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ==========================
    // BUSCAR POR ID
    // ==========================
    public Expediente buscarPorId(int id) {

        String sql = """
                SELECT *
                FROM expedientes
                WHERE id = ?
                """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Expediente e = new Expediente();

                    e.setId(rs.getInt("id"));
                    e.setCodigo(rs.getString("codigo"));
                    e.setClienteId(rs.getInt("cliente_id"));
                    e.setNombreCliente(rs.getString("nombre_cliente"));
                    e.setEstado(rs.getString("estado"));
                    e.setProgreso(rs.getDouble("progreso"));
                    e.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());
                    e.setObservaciones(rs.getString("observaciones"));

                    return e;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // ==========================
    // LISTAR POR CLIENTE
    // ==========================
    public List<Expediente> listarPorCliente(int clienteId) {

        List<Expediente> lista = new ArrayList<>();

        String sql = """
                SELECT *
                FROM expedientes
                WHERE cliente_id = ?
                ORDER BY id DESC
                """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, clienteId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Expediente e = new Expediente();

                    e.setId(rs.getInt("id"));
                    e.setCodigo(rs.getString("codigo"));
                    e.setClienteId(rs.getInt("cliente_id"));
                    e.setNombreCliente(rs.getString("nombre_cliente"));
                    e.setEstado(rs.getString("estado"));
                    e.setProgreso(rs.getDouble("progreso"));
                    e.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());
                    e.setObservaciones(rs.getString("observaciones"));

                    lista.add(e);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ==========================
    // ACTUALIZAR PROGRESO
    // ==========================
    public boolean actualizarProgreso(int id, double progreso) {

        String estado = (progreso >= 100) ? "COMPLETADO" : "EN PROCESO";

        String sql = """
                UPDATE expedientes
                SET progreso = ?, estado = ?
                WHERE id = ?
                """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, progreso);
            ps.setString(2, estado);
            ps.setInt(3, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ==========================
    // CREAR SUB-EXPEDIENTE (VERSION 01-02)
    // ==========================
    public String generarSubCodigo(String codigoBase) {

        String sql = """
                SELECT COUNT(*) total
                FROM expedientes
                WHERE codigo LIKE ?
                """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigoBase + "%");

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    int total = rs.getInt("total");

                    return codigoBase + "-" + String.format("%02d", total);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return codigoBase + "-01";
    }


    public boolean registrarExpediente(Expediente e) {
        return insertar(e);
    }


    public List<Expediente> listarExpedientes() {
        return listarTodos();
    }


    public boolean actualizarExpediente(Expediente e) {

        String sql = """
            UPDATE expedientes
            SET codigo=?, cliente_id=?, nombre_cliente=?,
                estado=?, progreso=?, fecha_creacion=?, observaciones=?
            WHERE id=?
            """;

        try(Connection con = ConexionBD.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getCodigo());
            ps.setInt(2, e.getClienteId());
            ps.setString(3, e.getNombreCliente());
            ps.setString(4, e.getEstado());
            ps.setDouble(5, e.getProgreso());
            ps.setDate(6, Date.valueOf(e.getFechaCreacion()));
            ps.setString(7, e.getObservaciones());
            ps.setInt(8, e.getId());

            return ps.executeUpdate() > 0;

        } catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }      


    public boolean eliminarExpediente(int id){

        String sql="DELETE FROM expedientes WHERE id=?";

        try(Connection con=ConexionBD.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){

            ps.setInt(1,id);

            return ps.executeUpdate()>0;

        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }


    public Expediente buscarPorCodigoExpediente(String codigo){

        String sql="SELECT * FROM expedientes WHERE codigo=?";

        try(Connection con=ConexionBD.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)){

            ps.setString(1,codigo);

            ResultSet rs=ps.executeQuery();

            if(rs.next()){
                return buscarPorId(rs.getInt("id"));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }


    public int contarEntregados(){

        String sql="SELECT COUNT(*) FROM expedientes WHERE estado='COMPLETADO'";

        try(Connection con=ConexionBD.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery()){

            if(rs.next()) return rs.getInt(1);

        }catch(SQLException e){
            e.printStackTrace();
        }

        return 0;
    }


    public int contarPendientes(){

        String sql="SELECT COUNT(*) FROM expedientes WHERE estado!='COMPLETADO'";

        try(Connection con=ConexionBD.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery()){

            if(rs.next()) return rs.getInt(1);

        }catch(SQLException e){
            e.printStackTrace();
        }

        return 0;
    }
}