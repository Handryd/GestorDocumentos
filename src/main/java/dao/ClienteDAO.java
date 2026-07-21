package dao;

import model.Cliente;
import util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // ==========================
    // REGISTRAR CLIENTE
    // ==========================
    public boolean registrarCliente(Cliente cliente) {

        String sql = """
                INSERT INTO clientes
                (
                    codigo_expediente,
                    expediente_padre,
                    nombre,
                    correo,
                    telefono
                )
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cliente.getCodigoExpediente());

            if (cliente.getExpedientePadre() == null ||
                cliente.getExpedientePadre().trim().isEmpty()) {

                ps.setNull(2, Types.VARCHAR);

            } else {

                ps.setString(2, cliente.getExpedientePadre());
            }

            ps.setString(3, cliente.getNombre());
            ps.setString(4, cliente.getCorreo());
            ps.setString(5, cliente.getTelefono());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error al registrar cliente:");
            e.printStackTrace();
            return false;
        }
    }

    // ==========================
    // LISTAR CLIENTES
    // ==========================
    public List<Cliente> listarClientes() {

        List<Cliente> lista = new ArrayList<>();

        String sql = """
                SELECT *
                FROM clientes
                ORDER BY codigo_expediente
                """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Cliente cliente = new Cliente();

                cliente.setId(rs.getInt("id"));
                cliente.setCodigoExpediente(
                        rs.getString("codigo_expediente"));
                cliente.setExpedientePadre(
                        rs.getString("expediente_padre"));
                cliente.setNombre(
                        rs.getString("nombre"));
                cliente.setCorreo(
                        rs.getString("correo"));
                cliente.setTelefono(
                        rs.getString("telefono"));

                lista.add(cliente);
            }

        } catch (SQLException e) {

            System.out.println("Error al listar clientes:");
            e.printStackTrace();
        }

        return lista;
    }

    // ==========================
    // BUSCAR POR ID
    // ==========================
    public Cliente buscarPorId(int id) {

        Cliente cliente = null;

        String sql = """
                SELECT *
                FROM clientes
                WHERE id = ?
                """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    cliente = new Cliente();

                    cliente.setId(rs.getInt("id"));
                    cliente.setCodigoExpediente(
                            rs.getString("codigo_expediente"));
                    cliente.setExpedientePadre(
                            rs.getString("expediente_padre"));
                    cliente.setNombre(
                            rs.getString("nombre"));
                    cliente.setCorreo(
                            rs.getString("correo"));
                    cliente.setTelefono(
                            rs.getString("telefono"));
                }
            }

        } catch (SQLException e) {

            System.out.println("Error al buscar cliente:");
            e.printStackTrace();
        }

        return cliente;
    }

    // ==========================
    // ACTUALIZAR CLIENTE
    // ==========================
    public boolean actualizarCliente(Cliente cliente) {

        String sql = """
                UPDATE clientes
                SET
                    codigo_expediente = ?,
                    expediente_padre = ?,
                    nombre = ?,
                    correo = ?,
                    telefono = ?
                WHERE id = ?
                """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cliente.getCodigoExpediente());

            if (cliente.getExpedientePadre() == null ||
                cliente.getExpedientePadre().trim().isEmpty()) {

                ps.setNull(2, Types.VARCHAR);

            } else {

                ps.setString(2, cliente.getExpedientePadre());
            }

            ps.setString(3, cliente.getNombre());
            ps.setString(4, cliente.getCorreo());
            ps.setString(5, cliente.getTelefono());

            ps.setInt(6, cliente.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error al actualizar cliente:");
            e.printStackTrace();
            return false;
        }
    }

    // ==========================
    // ELIMINAR CLIENTE
    // ==========================
    public boolean eliminarCliente(int id) {

        String sql = """
                DELETE FROM clientes
                WHERE id = ?
                """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error al eliminar cliente:");
            e.printStackTrace();
            return false;
        }
    }

    // ==========================
    // BUSCAR POR CÓDIGO
    // ==========================
    public Cliente buscarPorCodigoExpediente(String codigoExpediente) {

        Cliente cliente = null;

        String sql = """
                SELECT *
                FROM clientes
                WHERE codigo_expediente = ?
                """;

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigoExpediente);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    cliente = new Cliente();

                    cliente.setId(rs.getInt("id"));
                    cliente.setCodigoExpediente(
                            rs.getString("codigo_expediente"));
                    cliente.setExpedientePadre(
                            rs.getString("expediente_padre"));
                    cliente.setNombre(
                            rs.getString("nombre"));
                    cliente.setCorreo(
                            rs.getString("correo"));
                    cliente.setTelefono(
                            rs.getString("telefono"));
                }
            }

        } catch (SQLException e) {

            System.out.println("Error al buscar por código:");
            e.printStackTrace();
        }

        return cliente;
    }
}