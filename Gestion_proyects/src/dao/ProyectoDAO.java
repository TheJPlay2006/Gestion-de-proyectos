package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Proyecto;
import util.ConexionBD;

public class ProyectoDAO {
    
    // Crear un nuevo proyecto
    public boolean crearProyecto(Proyecto proyecto) {
        String sql = "INSERT INTO Proyectos (nombre, descripcion, fecha_inicio, fecha_fin, estado) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, proyecto.getNombre());
            stmt.setString(2, proyecto.getDescripcion());
            stmt.setDate(3, proyecto.getFechaInicio() != null ? Date.valueOf(proyecto.getFechaInicio()) : null);
            stmt.setDate(4, proyecto.getFechaFin() != null ? Date.valueOf(proyecto.getFechaFin()) : null);
            stmt.setString(5, proyecto.getEstado());
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            ConexionBD.registrarError("Error al crear proyecto", "crearProyecto", e.toString());
            return false;
        }
    }
    
    // Obtener todos los proyectos
    public List<Proyecto> obtenerTodosLosProyectos() {
        List<Proyecto> proyectos = new ArrayList<>();
        String sql = "SELECT * FROM Proyectos ORDER BY fecha_creacion DESC";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Proyecto proyecto = new Proyecto();
                proyecto.setId(rs.getInt("id"));
                proyecto.setNombre(rs.getString("nombre"));
                proyecto.setDescripcion(rs.getString("descripcion"));
                proyecto.setFechaInicio(rs.getDate("fecha_inicio") != null ? rs.getDate("fecha_inicio").toLocalDate() : null);
                proyecto.setFechaFin(rs.getDate("fecha_fin") != null ? rs.getDate("fecha_fin").toLocalDate() : null);
                proyecto.setEstado(rs.getString("estado"));
                
                // Conversión de Timestamp a LocalDateTime
                Timestamp timestamp = rs.getTimestamp("fecha_creacion");
                if (timestamp != null) {
                    proyecto.setFechaCreacion(timestamp.toLocalDateTime());
                }
                
                proyectos.add(proyecto);
            }
            
        } catch (SQLException e) {
            ConexionBD.registrarError("Error al obtener proyectos", "obtenerTodosLosProyectos", e.toString());
        }
        return proyectos;
    }
    
    // Obtener proyecto por ID
    public Proyecto obtenerProyectoPorId(int id) {
        String sql = "SELECT * FROM Proyectos WHERE id = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Proyecto proyecto = new Proyecto();
                    proyecto.setId(rs.getInt("id"));
                    proyecto.setNombre(rs.getString("nombre"));
                    proyecto.setDescripcion(rs.getString("descripcion"));
                    proyecto.setFechaInicio(rs.getDate("fecha_inicio") != null ? rs.getDate("fecha_inicio").toLocalDate() : null);
                    proyecto.setFechaFin(rs.getDate("fecha_fin") != null ? rs.getDate("fecha_fin").toLocalDate() : null);
                    proyecto.setEstado(rs.getString("estado"));
                    
                    // Conversión de Timestamp a LocalDateTime
                    Timestamp timestamp = rs.getTimestamp("fecha_creacion");
                    if (timestamp != null) {
                        proyecto.setFechaCreacion(timestamp.toLocalDateTime());
                    }
                    
                    return proyecto;
                }
            }
            
        } catch (SQLException e) {
            ConexionBD.registrarError("Error al obtener proyecto por ID", "obtenerProyectoPorId", e.toString());
        }
        return null;
    }
    
    // Actualizar proyecto
    public boolean actualizarProyecto(Proyecto proyecto) {
        String sql = "UPDATE Proyectos SET nombre = ?, descripcion = ?, fecha_inicio = ?, fecha_fin = ?, estado = ? WHERE id = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, proyecto.getNombre());
            stmt.setString(2, proyecto.getDescripcion());
            stmt.setDate(3, proyecto.getFechaInicio() != null ? Date.valueOf(proyecto.getFechaInicio()) : null);
            stmt.setDate(4, proyecto.getFechaFin() != null ? Date.valueOf(proyecto.getFechaFin()) : null);
            stmt.setString(5, proyecto.getEstado());
            stmt.setInt(6, proyecto.getId());
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            ConexionBD.registrarError("Error al actualizar proyecto", "actualizarProyecto", e.toString());
            return false;
        }
    }
    
    // Eliminar proyecto
    public boolean eliminarProyecto(int id) {
        String sql = "DELETE FROM Proyectos WHERE id = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            ConexionBD.registrarError("Error al eliminar proyecto", "eliminarProyecto", e.toString());
            return false;
        }
    }
    
    // Buscar proyectos por nombre
    public List<Proyecto> buscarProyectosPorNombre(String nombre) {
        List<Proyecto> proyectos = new ArrayList<>();
        String sql = "SELECT * FROM Proyectos WHERE nombre LIKE ? ORDER BY nombre";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + nombre + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Proyecto proyecto = new Proyecto();
                    proyecto.setId(rs.getInt("id"));
                    proyecto.setNombre(rs.getString("nombre"));
                    proyecto.setDescripcion(rs.getString("descripcion"));
                    proyecto.setFechaInicio(rs.getDate("fecha_inicio") != null ? rs.getDate("fecha_inicio").toLocalDate() : null);
                    proyecto.setFechaFin(rs.getDate("fecha_fin") != null ? rs.getDate("fecha_fin").toLocalDate() : null);
                    proyecto.setEstado(rs.getString("estado"));
                    
                    // Conversión de Timestamp a LocalDateTime
                    Timestamp timestamp = rs.getTimestamp("fecha_creacion");
                    if (timestamp != null) {
                        proyecto.setFechaCreacion(timestamp.toLocalDateTime());
                    }
                    
                    proyectos.add(proyecto);
                }
            }
            
        } catch (SQLException e) {
            ConexionBD.registrarError("Error al buscar proyectos", "buscarProyectosPorNombre", e.toString());
        }
        return proyectos;
    }
}