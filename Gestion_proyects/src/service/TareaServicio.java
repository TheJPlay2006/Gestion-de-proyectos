package service;

import dao.TareaDAO;
import model.Tarea;
import util.ConexionBD; // Importación actualizada
import java.util.List;

public class TareaServicio {
    private TareaDAO tareaDAO;
    
    public TareaServicio() {
        this.tareaDAO = new TareaDAO();
    }
    
    // Crear una nueva tarea
    public boolean crearTarea(Tarea tarea) {
        try {
            // Validaciones
            if (tarea.getTitulo() == null || tarea.getTitulo().trim().isEmpty()) {
                System.err.println("El título de la tarea es obligatorio");
                return false;
            }
            if (tarea.getProyectoId() <= 0) {
                System.err.println("El ID del proyecto es obligatorio");
                return false;
            }
            if (tarea.getFechaInicio() != null && tarea.getFechaVencimiento() != null) {
                if (tarea.getFechaInicio().isAfter(tarea.getFechaVencimiento())) {
                    System.err.println("La fecha de inicio no puede ser posterior a la fecha de vencimiento");
                    return false;
                }
            }
            
            return tareaDAO.crearTarea(tarea);
        } catch (Exception e) {
            ConexionBD.registrarError("Error en servicio al crear tarea", "crearTarea", e.toString());
            return false;
        }
    }
    
    // Obtener todas las tareas
    public List<Tarea> obtenerTodasLasTareas() {
        try {
            return tareaDAO.obtenerTodasLasTareas();
        } catch (Exception e) {
            ConexionBD.registrarError("Error en servicio al obtener tareas", "obtenerTodasLasTareas", e.toString());
            return null;
        }
    }
    
    // Obtener tareas por proyecto ID
    public List<Tarea> obtenerTareasPorProyectoId(int proyectoId) {
        try {
            if (proyectoId <= 0) {
                System.err.println("ID de proyecto inválido");
                return null;
            }
            return tareaDAO.obtenerTareasPorProyectoId(proyectoId);
        } catch (Exception e) {
            ConexionBD.registrarError("Error en servicio al obtener tareas por proyecto", "obtenerTareasPorProyectoId", e.toString());
            return null;
        }
    }
    
    // Obtener tarea por ID
    public Tarea obtenerTareaPorId(int id) {
        try {
            return tareaDAO.obtenerTareaPorId(id);
        } catch (Exception e) {
            ConexionBD.registrarError("Error en servicio al obtener tarea por ID", "obtenerTareaPorId", e.toString());
            return null;
        }
    }
    
    // Actualizar tarea
    public boolean actualizarTarea(Tarea tarea) {
        try {
            if (tarea.getId() <= 0) {
                System.err.println("ID de tarea inválido");
                return false;
            }
            if (tarea.getTitulo() == null || tarea.getTitulo().trim().isEmpty()) {
                System.err.println("El título de la tarea es obligatorio");
                return false;
            }
            if (tarea.getProyectoId() <= 0) {
                System.err.println("El ID del proyecto es obligatorio");
                return false;
            }
            if (tarea.getFechaInicio() != null && tarea.getFechaVencimiento() != null) {
                if (tarea.getFechaInicio().isAfter(tarea.getFechaVencimiento())) {
                    System.err.println("La fecha de inicio no puede ser posterior a la fecha de vencimiento");
                    return false;
                }
            }
            
            return tareaDAO.actualizarTarea(tarea);
        } catch (Exception e) {
            ConexionBD.registrarError("Error en servicio al actualizar tarea", "actualizarTarea", e.toString());
            return false;
        }
    }
    
    // Eliminar tarea
    public boolean eliminarTarea(int id) {
        try {
            if (id <= 0) {
                System.err.println("ID de tarea inválido");
                return false;
            }
            return tareaDAO.eliminarTarea(id);
        } catch (Exception e) {
            ConexionBD.registrarError("Error en servicio al eliminar tarea", "eliminarTarea", e.toString());
            return false;
        }
    }
    
    // Marcar tarea como completada
    public boolean marcarTareaComoCompletada(int id) {
        try {
            if (id <= 0) {
                System.err.println("ID de tarea inválido");
                return false;
            }
            return tareaDAO.marcarTareaComoCompletada(id);
        } catch (Exception e) {
            ConexionBD.registrarError("Error en servicio al marcar tarea como completada", "marcarTareaComoCompletada", e.toString());
            return false;
        }
    }
    
    // Buscar tareas por título
    public List<Tarea> buscarTareasPorTitulo(String titulo) {
        try {
            if (titulo == null || titulo.trim().isEmpty()) {
                return obtenerTodasLasTareas();
            }
            return tareaDAO.buscarTareasPorTitulo(titulo);
        } catch (Exception e) {
            ConexionBD.registrarError("Error en servicio al buscar tareas", "buscarTareasPorTitulo", e.toString());
            return null;
        }
    }
    
    // Cambiar estado de tarea
    public boolean cambiarEstadoTarea(int id, String nuevoEstado) {
        try {
            if (id <= 0) {
                System.err.println("ID de tarea inválido");
                return false;
            }
            if (nuevoEstado == null || nuevoEstado.trim().isEmpty()) {
                System.err.println("El estado es obligatorio");
                return false;
            }
            
            Tarea tarea = obtenerTareaPorId(id);
            if (tarea == null) {
                System.err.println("Tarea no encontrada");
                return false;
            }
            
            tarea.setEstado(nuevoEstado);
            if ("Hecho".equals(nuevoEstado)) {
                tarea.setFechaCompletado(java.time.LocalDate.now());
            }
            
            return actualizarTarea(tarea);
        } catch (Exception e) {
            ConexionBD.registrarError("Error en servicio al cambiar estado de tarea", "cambiarEstadoTarea", e.toString());
            return false;
        }
    }
}