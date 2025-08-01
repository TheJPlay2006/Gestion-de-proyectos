package service;

import dao.ProyectoDAO;
import model.Proyecto;
import util.ConexionBD; // Importación actualizada
import java.util.List;

public class ProyectoServicio {
    private ProyectoDAO proyectoDAO;
    
    public ProyectoServicio() {
        this.proyectoDAO = new ProyectoDAO();
    }
    
    // Crear un nuevo proyecto
    public boolean crearProyecto(Proyecto proyecto) {
        try {
            // Validaciones
            if (proyecto.getNombre() == null || proyecto.getNombre().trim().isEmpty()) {
                System.err.println("El nombre del proyecto es obligatorio");
                return false;
            }
            if (proyecto.getFechaInicio() != null && proyecto.getFechaFin() != null) {
                if (proyecto.getFechaInicio().isAfter(proyecto.getFechaFin())) {
                    System.err.println("La fecha de inicio no puede ser posterior a la fecha de fin");
                    return false;
                }
            }
            
            return proyectoDAO.crearProyecto(proyecto);
        } catch (Exception e) {
            ConexionBD.registrarError("Error en servicio al crear proyecto", "crearProyecto", e.toString());
            return false;
        }
    }
    
    // Obtener todos los proyectos
    public List<Proyecto> obtenerTodosLosProyectos() {
        try {
            return proyectoDAO.obtenerTodosLosProyectos();
        } catch (Exception e) {
            ConexionBD.registrarError("Error en servicio al obtener proyectos", "obtenerTodosLosProyectos", e.toString());
            return null;
        }
    }
    
    // Obtener proyecto por ID
    public Proyecto obtenerProyectoPorId(int id) {
        try {
            return proyectoDAO.obtenerProyectoPorId(id);
        } catch (Exception e) {
            ConexionBD.registrarError("Error en servicio al obtener proyecto por ID", "obtenerProyectoPorId", e.toString());
            return null;
        }
    }
    
    // Actualizar proyecto
    public boolean actualizarProyecto(Proyecto proyecto) {
        try {
            if (proyecto.getId() <= 0) {
                System.err.println("ID de proyecto inválido");
                return false;
            }
            if (proyecto.getNombre() == null || proyecto.getNombre().trim().isEmpty()) {
                System.err.println("El nombre del proyecto es obligatorio");
                return false;
            }
            if (proyecto.getFechaInicio() != null && proyecto.getFechaFin() != null) {
                if (proyecto.getFechaInicio().isAfter(proyecto.getFechaFin())) {
                    System.err.println("La fecha de inicio no puede ser posterior a la fecha de fin");
                    return false;
                }
            }
            
            return proyectoDAO.actualizarProyecto(proyecto);
        } catch (Exception e) {
            ConexionBD.registrarError("Error en servicio al actualizar proyecto", "actualizarProyecto", e.toString());
            return false;
        }
    }
    
    // Eliminar proyecto
    public boolean eliminarProyecto(int id) {
        try {
            if (id <= 0) {
                System.err.println("ID de proyecto inválido");
                return false;
            }
            return proyectoDAO.eliminarProyecto(id);
        } catch (Exception e) {
            ConexionBD.registrarError("Error en servicio al eliminar proyecto", "eliminarProyecto", e.toString());
            return false;
        }
    }
    
    // Buscar proyectos por nombre
    public List<Proyecto> buscarProyectosPorNombre(String nombre) {
        try {
            if (nombre == null || nombre.trim().isEmpty()) {
                return obtenerTodosLosProyectos();
            }
            return proyectoDAO.buscarProyectosPorNombre(nombre);
        } catch (Exception e) {
            ConexionBD.registrarError("Error en servicio al buscar proyectos", "buscarProyectosPorNombre", e.toString());
            return null;
        }
    }
    
    // Calcular progreso del proyecto (método adicional)
    public double calcularProgresoProyecto(int proyectoId) {
        try {
            // Aquí iría la lógica para calcular el progreso basado en tareas
            // Por ahora retornamos un valor de ejemplo
            return 0.0;
        } catch (Exception e) {
            ConexionBD.registrarError("Error en servicio al calcular progreso", "calcularProgresoProyecto", e.toString());
            return 0.0;
        }
    }
}