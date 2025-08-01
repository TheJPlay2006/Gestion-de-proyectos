/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Tarea;
import util.ConexionBD;

public class TareaDAO {
    
    // Crear una nueva tarea
    public boolean crearTarea(Tarea tarea) {
        String sql = "INSERT INTO Tareas (proyecto_id, titulo, descripcion, asignado_a, estado, prioridad, fecha_inicio, fecha_vencimiento) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, tarea.getProyectoId());
            stmt.setString(2, tarea.getTitulo());
            stmt.setString(3, tarea.getDescripcion());
            stmt.setObject(4, tarea.getAsignadoA(), Types.INTEGER);
            stmt.setString(5, tarea.getEstado());
            stmt.setString(6, tarea.getPrioridad());
            stmt.setDate(7, tarea.getFechaInicio() != null ? Date.valueOf(tarea.getFechaInicio()) : null);
            stmt.setDate(8, tarea.getFechaVencimiento() != null ? Date.valueOf(tarea.getFechaVencimiento()) : null);
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            ConexionBD.registrarError("Error al crear tarea", "crearTarea", e.toString());
            return false;
        }
    }
    
    // Obtener todas las tareas
    public List<Tarea> obtenerTodasLasTareas() {
        List<Tarea> tareas = new ArrayList<>();
        String sql = "SELECT * FROM Tareas ORDER BY fecha_creacion DESC";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Tarea tarea = new Tarea();
                tarea.setId(rs.getInt("id"));
                tarea.setProyectoId(rs.getInt("proyecto_id"));
                tarea.setTitulo(rs.getString("titulo"));
                tarea.setDescripcion(rs.getString("descripcion"));
                tarea.setAsignadoA(rs.getObject("asignado_a", Integer.class));
                tarea.setEstado(rs.getString("estado"));
                tarea.setPrioridad(rs.getString("prioridad"));
                tarea.setFechaInicio(rs.getDate("fecha_inicio") != null ? rs.getDate("fecha_inicio").toLocalDate() : null);
                tarea.setFechaVencimiento(rs.getDate("fecha_vencimiento") != null ? rs.getDate("fecha_vencimiento").toLocalDate() : null);
                tarea.setFechaCompletado(rs.getDate("fecha_completado") != null ? rs.getDate("fecha_completado").toLocalDate() : null);
                
                // Conversión de Timestamp a LocalDateTime
                Timestamp timestamp = rs.getTimestamp("fecha_creacion");
                if (timestamp != null) {
                    tarea.setFechaCreacion(timestamp.toLocalDateTime());
                }
                
                tareas.add(tarea);
            }
            
        } catch (SQLException e) {
            ConexionBD.registrarError("Error al obtener tareas", "obtenerTodasLasTareas", e.toString());
        }
        return tareas;
    }
    
    // Obtener tareas por proyecto ID
    public List<Tarea> obtenerTareasPorProyectoId(int proyectoId) {
        List<Tarea> tareas = new ArrayList<>();
        String sql = "SELECT * FROM Tareas WHERE proyecto_id = ? ORDER BY fecha_vencimiento";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, proyectoId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Tarea tarea = new Tarea();
                    tarea.setId(rs.getInt("id"));
                    tarea.setProyectoId(rs.getInt("proyecto_id"));
                    tarea.setTitulo(rs.getString("titulo"));
                    tarea.setDescripcion(rs.getString("descripcion"));
                    tarea.setAsignadoA(rs.getObject("asignado_a", Integer.class));
                    tarea.setEstado(rs.getString("estado"));
                    tarea.setPrioridad(rs.getString("prioridad"));
                    tarea.setFechaInicio(rs.getDate("fecha_inicio") != null ? rs.getDate("fecha_inicio").toLocalDate() : null);
                    tarea.setFechaVencimiento(rs.getDate("fecha_vencimiento") != null ? rs.getDate("fecha_vencimiento").toLocalDate() : null);
                    tarea.setFechaCompletado(rs.getDate("fecha_completado") != null ? rs.getDate("fecha_completado").toLocalDate() : null);
                    
                    // Conversión de Timestamp a LocalDateTime
                    Timestamp timestamp = rs.getTimestamp("fecha_creacion");
                    if (timestamp != null) {
                        tarea.setFechaCreacion(timestamp.toLocalDateTime());
                    }
                    
                    tareas.add(tarea);
                }
            }
            
        } catch (SQLException e) {
            ConexionBD.registrarError("Error al obtener tareas por proyecto", "obtenerTareasPorProyectoId", e.toString());
        }
        return tareas;
    }
    
    // Obtener tarea por ID
    public Tarea obtenerTareaPorId(int id) {
        String sql = "SELECT * FROM Tareas WHERE id = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Tarea tarea = new Tarea();
                    tarea.setId(rs.getInt("id"));
                    tarea.setProyectoId(rs.getInt("proyecto_id"));
                    tarea.setTitulo(rs.getString("titulo"));
                    tarea.setDescripcion(rs.getString("descripcion"));
                    tarea.setAsignadoA(rs.getObject("asignado_a", Integer.class));
                    tarea.setEstado(rs.getString("estado"));
                    tarea.setPrioridad(rs.getString("prioridad"));
                    tarea.setFechaInicio(rs.getDate("fecha_inicio") != null ? rs.getDate("fecha_inicio").toLocalDate() : null);
                    tarea.setFechaVencimiento(rs.getDate("fecha_vencimiento") != null ? rs.getDate("fecha_vencimiento").toLocalDate() : null);
                    tarea.setFechaCompletado(rs.getDate("fecha_completado") != null ? rs.getDate("fecha_completado").toLocalDate() : null);
                    
                    // Conversión de Timestamp a LocalDateTime
                    Timestamp timestamp = rs.getTimestamp("fecha_creacion");
                    if (timestamp != null) {
                        tarea.setFechaCreacion(timestamp.toLocalDateTime());
                    }
                    
                    return tarea;
                }
            }
            
        } catch (SQLException e) {
            ConexionBD.registrarError("Error al obtener tarea por ID", "obtenerTareaPorId", e.toString());
        }
        return null;
    }
    
    // Actualizar tarea
    public boolean actualizarTarea(Tarea tarea) {
        String sql = "UPDATE Tareas SET proyecto_id = ?, titulo = ?, descripcion = ?, asignado_a = ?, estado = ?, prioridad = ?, fecha_inicio = ?, fecha_vencimiento = ?, fecha_completado = ? WHERE id = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, tarea.getProyectoId());
            stmt.setString(2, tarea.getTitulo());
            stmt.setString(3, tarea.getDescripcion());
            stmt.setObject(4, tarea.getAsignadoA(), Types.INTEGER);
            stmt.setString(5, tarea.getEstado());
            stmt.setString(6, tarea.getPrioridad());
            stmt.setDate(7, tarea.getFechaInicio() != null ? Date.valueOf(tarea.getFechaInicio()) : null);
            stmt.setDate(8, tarea.getFechaVencimiento() != null ? Date.valueOf(tarea.getFechaVencimiento()) : null);
            stmt.setDate(9, tarea.getFechaCompletado() != null ? Date.valueOf(tarea.getFechaCompletado()) : null);
            stmt.setInt(10, tarea.getId());
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            ConexionBD.registrarError("Error al actualizar tarea", "actualizarTarea", e.toString());
            return false;
        }
    }
    
    // Eliminar tarea
    public boolean eliminarTarea(int id) {
        String sql = "DELETE FROM Tareas WHERE id = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            ConexionBD.registrarError("Error al eliminar tarea", "eliminarTarea", e.toString());
            return false;
        }
    }
    
    // Marcar tarea como completada
    public boolean marcarTareaComoCompletada(int id) {
        String sql = "UPDATE Tareas SET estado = 'Hecho', fecha_completado = ? WHERE id = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, Date.valueOf(java.time.LocalDate.now()));
            stmt.setInt(2, id);
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            ConexionBD.registrarError("Error al marcar tarea como completada", "marcarTareaComoCompletada", e.toString());
            return false;
        }
    }
    
    // Buscar tareas por título
    public List<Tarea> buscarTareasPorTitulo(String titulo) {
        List<Tarea> tareas = new ArrayList<>();
        String sql = "SELECT * FROM Tareas WHERE titulo LIKE ? ORDER BY titulo";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + titulo + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Tarea tarea = new Tarea();
                    tarea.setId(rs.getInt("id"));
                    tarea.setProyectoId(rs.getInt("proyecto_id"));
                    tarea.setTitulo(rs.getString("titulo"));
                    tarea.setDescripcion(rs.getString("descripcion"));
                    tarea.setAsignadoA(rs.getObject("asignado_a", Integer.class));
                    tarea.setEstado(rs.getString("estado"));
                    tarea.setPrioridad(rs.getString("prioridad"));
                    tarea.setFechaInicio(rs.getDate("fecha_inicio") != null ? rs.getDate("fecha_inicio").toLocalDate() : null);
                    tarea.setFechaVencimiento(rs.getDate("fecha_vencimiento") != null ? rs.getDate("fecha_vencimiento").toLocalDate() : null);
                    tarea.setFechaCompletado(rs.getDate("fecha_completado") != null ? rs.getDate("fecha_completado").toLocalDate() : null);
                    
                    // Conversión de Timestamp a LocalDateTime
                    Timestamp timestamp = rs.getTimestamp("fecha_creacion");
                    if (timestamp != null) {
                        tarea.setFechaCreacion(timestamp.toLocalDateTime());
                    }
                    
                    tareas.add(tarea);
                }
            }
            
        } catch (SQLException e) {
            ConexionBD.registrarError("Error al buscar tareas", "buscarTareasPorTitulo", e.toString());
        }
        return tareas;
    }
}