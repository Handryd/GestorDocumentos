package service;

import dao.RequisitoDocumentoDAO;
import model.RequisitoDocumento;

import java.util.List;

public class RequisitoDocumentoService {

    private final RequisitoDocumentoDAO requisitoDAO;

    public RequisitoDocumentoService() {
        this.requisitoDAO = new RequisitoDocumentoDAO();
    }

    // ==========================
    // CREAR REQUISITO
    // ==========================
    public boolean crearRequisito(RequisitoDocumento r) {

        if (r == null) {
            return false;
        }

        if (r.getNombre() == null ||
                r.getNombre().trim().isEmpty()) {

            System.out.println(
                    "El nombre del requisito es obligatorio."
            );

            return false;
        }

        // Por defecto activo si no viene definido
        r.setActivo(true);

        return requisitoDAO.insertar(r);
    }

    // ==========================
    // OBTENER TODOS
    // ==========================
    public List<RequisitoDocumento> obtenerTodos() {

        return requisitoDAO.listarTodos();
    }

    // ==========================
    // OBTENER ACTIVOS
    // ==========================
    public List<RequisitoDocumento> obtenerActivos() {

        return requisitoDAO.listarActivos();
    }

    // ==========================
    // BUSCAR POR ID
    // ==========================
    public RequisitoDocumento buscarPorId(int id) {

        if (id <= 0) {
            return null;
        }

        return requisitoDAO.buscarPorId(id);
    }

    // ==========================
    // ACTUALIZAR
    // ==========================
    public boolean actualizar(RequisitoDocumento r) {

        if (r == null || r.getId() <= 0) {
            return false;
        }

        if (r.getNombre() == null ||
                r.getNombre().trim().isEmpty()) {

            return false;
        }

        return requisitoDAO.actualizar(r);
    }

    // ==========================
    // ELIMINAR
    // ==========================
    public boolean eliminar(int id) {

        if (id <= 0) {
            return false;
        }

        return requisitoDAO.eliminar(id);
    }

    // ==========================
    // CAMBIAR ESTADO
    // ==========================
    public boolean cambiarEstado(int id, boolean activo) {

        if (id <= 0) {
            return false;
        }

        return requisitoDAO.cambiarEstado(id, activo);
    }

    // ==========================
    // VALIDAR SI ES VÁLIDO
    // ==========================
    public boolean esValido(RequisitoDocumento r) {

        return r != null
                && r.getNombre() != null
                && !r.getNombre().trim().isEmpty();
    }

    // ==========================
    // CONTAR REQUISITOS
    // ==========================
    public int contar() {

        return obtenerTodos().size();
    }

    // ==========================
    // CONTAR ACTIVOS
    // ==========================
    public int contarActivos() {

        return obtenerActivos().size();
    }
}