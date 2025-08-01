package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Recurso;
import util.ConexionBD;

public class RecursoDAO {
    
    // Crear un nuevo recurso
    public boolean crearRecurso(Recurso recurso) {
        String sql = "INSERT INTO Recursos (tarea_id, nombre_recurso, tipo_recurso, ruta_recurso) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, recurso.getTareaId());
            stmt.setString(2, recurso.getNombreRecurso());
            stmt.setString(3, recurso.getTipoRecurso());
            stmt.setString(4, recurso.getRutaRecurso());
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            ConexionBD.registrarError("Error al crear recurso", "crearRecurso", e.toString());
            return false;
        }
    }
    
    // Obtener todos los recursos
    public List<Recurso> obtenerTodosLosRecursos() {
        List<Recurso> recursos = new ArrayList<>();
        String sql = "SELECT * FROM Recursos ORDER BY fecha_subida DESC";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Recurso recurso = new Recurso();
                recurso.setId(rs.getInt("id"));
                recurso.setTareaId(rs.getInt("tarea_id"));
                recurso.setNombreRecurso(rs.getString("nombre_recurso"));
                recurso.setTipoRecurso(rs.getString("tipo_recurso"));
                recurso.setRutaRecurso(rs.getString("ruta_recurso"));
                
                // Conversión de Timestamp a LocalDateTime
                Timestamp timestamp = rs.getTimestamp("fecha_subida");
                if (timestamp != null) {
                    recurso.setFechaSubida(timestamp.toLocalDateTime());
                }
                
                recursos.add(recurso);
            }
            
        } catch (SQLException e) {
            ConexionBD.registrarError("Error al obtener recursos", "obtenerTodosLosRecursos", e.toString());
        }
        return recursos;
    }
    
    // Obtener recursos por tarea ID
    public List<Recurso> obtenerRecursosPorTareaId(int tareaId) {
        List<Recurso> recursos = new ArrayList<>();
        String sql = "SELECT * FROM Recursos WHERE tarea_id = ? ORDER BY fecha_subida DESC";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, tareaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Recurso recurso = new Recurso();
                    recurso.setId(rs.getInt("id"));
                    recurso.setTareaId(rs.getInt("tarea_id"));
                    recurso.setNombreRecurso(rs.getString("nombre_recurso"));
                    recurso.setTipoRecurso(rs.getString("tipo_recurso"));
                    recurso.setRutaRecurso(rs.getString("ruta_recurso"));
                    
                    // Conversión de Timestamp a LocalDateTime
                    Timestamp timestamp = rs.getTimestamp("fecha_subida");
                    if (timestamp != null) {
                        recurso.setFechaSubida(timestamp.toLocalDateTime());
                    }
                    
                    recursos.add(recurso);
                }
            }
            
        } catch (SQLException e) {
            ConexionBD.registrarError("Error al obtener recursos por tarea", "obtenerRecursosPorTareaId", e.toString());
        }
        return recursos;
    }
    
    // Obtener recurso por ID
    public Recurso obtenerRecursoPorId(int id) {
        String sql = "SELECT * FROM Recursos WHERE id = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Recurso recurso = new Recurso();
                    recurso.setId(rs.getInt("id"));
                    recurso.setTareaId(rs.getInt("tarea_id"));
                    recurso.setNombreRecurso(rs.getString("nombre_recurso"));
                    recurso.setTipoRecurso(rs.getString("tipo_recurso"));
                    recurso.setRutaRecurso(rs.getString("ruta_recurso"));
                    
                    // Conversión de Timestamp a LocalDateTime
                    Timestamp timestamp = rs.getTimestamp("fecha_subida");
                    if (timestamp != null) {
                        recurso.setFechaSubida(timestamp.toLocalDateTime());
                    }
                    
                    return recurso;
                }
            }
            
        } catch (SQLException e) {
            ConexionBD.registrarError("Error al obtener recurso por ID", "obtenerRecursoPorId", e.toString());
        }
        return null;
    }
    
    // Actualizar recurso
    public boolean actualizarRecurso(Recurso recurso) {
        String sql = "UPDATE Recursos SET tarea_id = ?, nombre_recurso = ?, tipo_recurso = ?, ruta_recurso = ? WHERE id = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, recurso.getTareaId());
            stmt.setString(2, recurso.getNombreRecurso());
            stmt.setString(3, recurso.getTipoRecurso());
            stmt.setString(4, recurso.getRutaRecurso());
            stmt.setInt(5, recurso.getId());
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            ConexionBD.registrarError("Error al actualizar recurso", "actualizarRecurso", e.toString());
            return false;
        }
    }
    
    // Eliminar recurso
    public boolean eliminarRecurso(int id) {
        String sql = "DELETE FROM Recursos WHERE id = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            ConexionBD.registrarError("Error al eliminar recurso", "eliminarRecurso", e.toString());
            return false;
        }
    }
    
    // Buscar recursos por nombre
    public List<Recurso> buscarRecursosPorNombre(String nombre) {
        List<Recurso> recursos = new ArrayList<>();
        String sql = "SELECT * FROM Recursos WHERE nombre_recurso LIKE ? ORDER BY nombre_recurso";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + nombre + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Recurso recurso = new Recurso();
                    recurso.setId(rs.getInt("id"));
                    recurso.setTareaId(rs.getInt("tarea_id"));
                    recurso.setNombreRecurso(rs.getString("nombre_recurso"));
                    recurso.setTipoRecurso(rs.getString("tipo_recurso"));
                    recurso.setRutaRecurso(rs.getString("ruta_recurso"));
                    
                    // Conversión de Timestamp a LocalDateTime
                    Timestamp timestamp = rs.getTimestamp("fecha_subida");
                    if (timestamp != null) {
                        recurso.setFechaSubida(timestamp.toLocalDateTime());
                    }
                    
                    recursos.add(recurso);
                }
            }
            
        } catch (SQLException e) {
            ConexionBD.registrarError("Error al buscar recursos", "buscarRecursosPorNombre", e.toString());
        }
        return recursos;
    }
}