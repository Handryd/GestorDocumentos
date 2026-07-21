package service;

import dao.ExpedienteDAO;
import model.Expediente;

import java.time.LocalDate;
import java.util.List;

public class ExpedienteService {

    private final ExpedienteDAO expedienteDAO;

    public ExpedienteService() {
        expedienteDAO = new ExpedienteDAO();
    }

    // ==========================================
    // REGISTRAR EXPEDIENTE
    // ==========================================
    public boolean registrarExpediente(Expediente expediente) {

        if (!validarExpediente(expediente)) {
            return false;
        }

        if (expediente.getCodigo() == null
                || expediente.getCodigo().isBlank()) {

            expediente.setCodigo(
                    expedienteDAO.generarCodigoBase()
            );
        }

        if (expediente.getFechaCreacion() == null) {
            expediente.setFechaCreacion(LocalDate.now());
        }

        return expedienteDAO.insertar(expediente);
    }

    // ==========================================
    // ACTUALIZAR
    // ==========================================
    public boolean actualizarExpediente(Expediente expediente) {

        if (!validarExpediente(expediente)) {
            return false;
        }

        return expedienteDAO.actualizarExpediente(expediente);
    }

    // ==========================================
    // ELIMINAR
    // ==========================================
    public boolean eliminarExpediente(int id) {
        return expedienteDAO.eliminarExpediente(id);
    }

    // ==========================================
    // LISTAR
    // ==========================================
    public List<Expediente> listarExpedientes() {
        return expedienteDAO.listarTodos();
    }

    // ==========================================
    // BUSCAR POR ID
    // ==========================================
    public Expediente buscarPorId(int id) {
        return expedienteDAO.buscarPorId(id);
    }

    // ==========================================
    // LISTAR POR CLIENTE
    // ==========================================
    public List<Expediente> listarPorCliente(int clienteId) {
        return expedienteDAO.listarPorCliente(clienteId);
    }

    // ==========================================
    // ACTUALIZAR PROGRESO
    // ==========================================
    public boolean actualizarProgreso(int id, double progreso) {

        if (progreso < 0) {
            progreso = 0;
        }

        if (progreso > 100) {
            progreso = 100;
        }

        return expedienteDAO.actualizarProgreso(id, progreso);
    }

    // ==========================================
    // VALIDACIONES
    // ==========================================
    private boolean validarExpediente(Expediente expediente) {

        if (expediente == null) {
            System.out.println("Expediente nulo.");
            return false;
        }

        if (expediente.getClienteId() <= 0) {
            System.out.println("Debe seleccionar un cliente.");
            return false;
        }

        if (expediente.getNombreCliente() == null
                || expediente.getNombreCliente().isBlank()) {

            System.out.println("Nombre del cliente obligatorio.");
            return false;
        }

        if (expediente.getEstado() == null
                || expediente.getEstado().isBlank()) {

            expediente.setEstado("ACTIVO");
        }

        if (expediente.getFechaCreacion() == null) {
            expediente.setFechaCreacion(LocalDate.now());
        }

        return true;
    }

    // ==========================================
    // DASHBOARD
    // ==========================================
    public int totalExpedientes() {
        return listarExpedientes().size();
    }

    public int totalCompletados() {

        int total = 0;

        for (Expediente e : listarExpedientes()) {

            if ("COMPLETADO".equalsIgnoreCase(e.getEstado())) {
                total++;
            }
        }

        return total;
    }

    public int totalActivos() {

        int total = 0;

        for (Expediente e : listarExpedientes()) {

            if (!"COMPLETADO".equalsIgnoreCase(e.getEstado())) {
                total++;
            }
        }

        return total;
    }

    // ==========================================
    // PORCENTAJE GENERAL
    // ==========================================
    public double calcularPorcentajeGeneral() {

        List<Expediente> lista = listarExpedientes();

        if (lista.isEmpty()) {
            return 0;
        }

        double suma = 0;

        for (Expediente e : lista) {
            suma += e.getProgreso();
        }

        return suma / lista.size();
    }
    
}