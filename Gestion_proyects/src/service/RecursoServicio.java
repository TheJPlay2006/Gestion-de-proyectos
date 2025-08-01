package service;

import dao.RecursoDAO;
import model.Recurso;
import util.ConexionBD; // Importación actualizada
import java.util.List;

public class RecursoServicio {
    private RecursoDAO recursoDAO;
    
    public RecursoServicio() {
        this.recursoDAO = new RecursoDAO();
    }
    
    // Crear un nuevo recurso
    public boolean crearRecurso(Recurso recurso) {
        try {
            // Validaciones
            if (recurso.getNombreRecurso() == null || recurso.getNombreRecurso().trim().isEmpty()) {
                System.err.println("El nombre del recurso es obligatorio");
                return false;
            }
            if (recurso.getTareaId() <= 0) {
                System.err.println("El ID de la tarea es obligatorio");
                return false;
            }
            if (recurso.getTipoRecurso() == null || recurso.getTipoRecurso().trim().isEmpty()) {
                System.err.println("El tipo de recurso es obligatorio");
                return false;
            }
            
            return recursoDAO.crearRecurso(recurso);
        } catch (Exception e) {
            ConexionBD.registrarError("Error en servicio al crear recurso", "crearRecurso", e.toString());
            return false;
        }
    }
    
    // Obtener todos los recursos
    public List<Recurso> obtenerTodosLosRecursos() {
        try {
            return recursoDAO.obtenerTodosLosRecursos();
        } catch (Exception e) {
            ConexionBD.registrarError("Error en servicio al obtener recursos", "obtenerTodosLosRecursos", e.toString());
            return null;
        }
    }
    
    // Obtener recursos por tarea ID
    public List<Recurso> obtenerRecursosPorTareaId(int tareaId) {
        try {
            if (tareaId <= 0) {
                System.err.println("ID de tarea inválido");
                return null;
            }
            return recursoDAO.obtenerRecursosPorTareaId(tareaId);
        } catch (Exception e) {
            ConexionBD.registrarError("Error en servicio al obtener recursos por tarea", "obtenerRecursosPorTareaId", e.toString());
            return null;
        }
    }
    
    // Obtener recurso por ID
    public Recurso obtenerRecursoPorId(int id) {
        try {
            return recursoDAO.obtenerRecursoPorId(id);
        } catch (Exception e) {
            ConexionBD.registrarError("Error en servicio al obtener recurso por ID", "obtenerRecursoPorId", e.toString());
            return null;
        }
    }
    
    // Actualizar recurso
    public boolean actualizarRecurso(Recurso recurso) {
        try {
            if (recurso.getId() <= 0) {
                System.err.println("ID de recurso inválido");
                return false;
            }
            if (recurso.getNombreRecurso() == null || recurso.getNombreRecurso().trim().isEmpty()) {
                System.err.println("El nombre del recurso es obligatorio");
                return false;
            }
            if (recurso.getTareaId() <= 0) {
                System.err.println("El ID de la tarea es obligatorio");
                return false;
            }
            if (recurso.getTipoRecurso() == null || recurso.getTipoRecurso().trim().isEmpty()) {
                System.err.println("El tipo de recurso es obligatorio");
                return false;
            }
            
            return recursoDAO.actualizarRecurso(recurso);
        } catch (Exception e) {
            ConexionBD.registrarError("Error en servicio al actualizar recurso", "actualizarRecurso", e.toString());
            return false;
        }
    }
    
    // Eliminar recurso
    public boolean eliminarRecurso(int id) {
        try {
            if (id <= 0) {
                System.err.println("ID de recurso inválido");
                return false;
            }
            return recursoDAO.eliminarRecurso(id);
        } catch (Exception e) {
            ConexionBD.registrarError("Error en servicio al eliminar recurso", "eliminarRecurso", e.toString());
            return false;
        }
    }
    
    // Buscar recursos por nombre
    public List<Recurso> buscarRecursosPorNombre(String nombre) {
        try {
            if (nombre == null || nombre.trim().isEmpty()) {
                return obtenerTodosLosRecursos();
            }
            return recursoDAO.buscarRecursosPorNombre(nombre);
        } catch (Exception e) {
            ConexionBD.registrarError("Error en servicio al buscar recursos", "buscarRecursosPorNombre", e.toString());
            return null;
        }
    }
}