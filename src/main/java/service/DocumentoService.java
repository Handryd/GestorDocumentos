package service;

import dao.DocumentoDAO;
import dao.RequisitoDocumentoDAO;
import model.Documento;
import model.RequisitoDocumento;

import java.time.LocalDate;
import java.util.List;

public class DocumentoService {

    private final DocumentoDAO documentoDAO;
    private final RequisitoDocumentoDAO requisitoDAO;

    public DocumentoService() {
        this.documentoDAO = new DocumentoDAO();
        this.requisitoDAO = new RequisitoDocumentoDAO();
    }

    // ==========================
    // GENERAR DOCUMENTOS AUTOMÁTICOS
    // ==========================
    public boolean generarDocumentosParaExpediente(int expedienteId) {

        List<RequisitoDocumento> requisitos =
                requisitoDAO.listarActivos();

        if (requisitos == null || requisitos.isEmpty()) {
            return false;
        }

        for (RequisitoDocumento r : requisitos) {

            Documento d = new Documento();

            d.setExpedienteId(expedienteId);
            d.setRequisitoId(r.getId());
            d.setNombreDocumento(r.getNombre());
            d.setEstado("PENDIENTE");
            d.setFechaRegistro(LocalDate.now());

            documentoDAO.insertar(d);
        }

        return true;
    }

    // ==========================
    // LISTAR POR EXPEDIENTE
    // ==========================
    public List<Documento> obtenerPorExpediente(int expedienteId) {

        if (expedienteId <= 0) {
            return null;
        }

        return documentoDAO.listarPorExpediente(expedienteId);
    }

    // ==========================
    // MARCAR COMO ENTREGADO
    // ==========================
    public boolean marcarEntregado(int documentoId) {

        return documentoDAO.actualizarEstado(
                documentoId,
                "ENTREGADO"
        );
    }

    // ==========================
    // MARCAR COMO PENDIENTE
    // ==========================
    public boolean marcarPendiente(int documentoId) {

        return documentoDAO.actualizarEstado(
                documentoId,
                "PENDIENTE"
        );
    }

    // ==========================
    // SUBIR ARCHIVO
    // ==========================
    public boolean subirArchivo(int documentoId, String archivo) {

        if (archivo == null || archivo.trim().isEmpty()) {
            return false;
        }

        return documentoDAO.actualizarArchivo(
                documentoId,
                archivo
        );
    }

    // ==========================
    // PORCENTAJE DE AVANCE
    // ==========================
    public double calcularProgreso(int expedienteId) {

        int total = documentoDAO.contarPorExpediente(expedienteId);

        int entregados = documentoDAO.contarEntregados(expedienteId);

        if (total == 0) {
            return 0.0;
        }

        return (entregados * 100.0) / total;
    }

    // ==========================
    // DOCUMENTOS ENTREGADOS
    // ==========================
    public int contarEntregados(int expedienteId) {

        return documentoDAO.contarEntregados(expedienteId);
    }

    // ==========================
    // TOTAL DOCUMENTOS
    // ==========================
    public int contarTotal(int expedienteId) {

        return documentoDAO.contarPorExpediente(expedienteId);
    }

    // ==========================
    // REGENERAR DOCUMENTOS
    // ==========================
    public boolean regenerarDocumentos(int expedienteId) {

        // Nota: aquí podrías limpiar y volver a generar

        List<Documento> existentes =
                documentoDAO.listarPorExpediente(expedienteId);

        if (existentes == null || existentes.isEmpty()) {
            return generarDocumentosParaExpediente(expedienteId);
        }

        return false;
    }
}