package dao; 

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Usuario;
import util.ConexionBD;

public class UsuarioDAO {
    
    // Crear un nuevo usuario
    public boolean crearUsuario(Usuario usuario) {
        String sql = "INSERT INTO Usuarios (nombre_usuario, nombre_completo, correo_electronico) VALUES (?, ?, ?)";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getNombreUsuario());
            stmt.setString(2, usuario.getNombreCompleto());
            stmt.setString(3, usuario.getCorreoElectronico());
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            ConexionBD.registrarError("Error al crear usuario", "crearUsuario", e.toString());
            return false;
        }
    }
    
    // Obtener todos los usuarios
    public List<Usuario> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuarios ORDER BY nombre_completo";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombreUsuario(rs.getString("nombre_usuario"));
                usuario.setNombreCompleto(rs.getString("nombre_completo"));
                usuario.setCorreoElectronico(rs.getString("correo_electronico"));
                
                // Conversión de Timestamp a LocalDateTime
                Timestamp timestamp = rs.getTimestamp("fecha_creacion");
                if (timestamp != null) {
                    usuario.setFechaCreacion(timestamp.toLocalDateTime());
                }
                
                usuarios.add(usuario);
            }
            
        } catch (SQLException e) {
            ConexionBD.registrarError("Error al obtener usuarios", "obtenerTodosLosUsuarios", e.toString());
        }
        return usuarios;
    }
    
    // Obtener usuario por ID
    public Usuario obtenerUsuarioPorId(int id) {
        String sql = "SELECT * FROM Usuarios WHERE id = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNombreUsuario(rs.getString("nombre_usuario"));
                    usuario.setNombreCompleto(rs.getString("nombre_completo"));
                    usuario.setCorreoElectronico(rs.getString("correo_electronico"));
                    
                    // Conversión de Timestamp a LocalDateTime
                    Timestamp timestamp = rs.getTimestamp("fecha_creacion");
                    if (timestamp != null) {
                        usuario.setFechaCreacion(timestamp.toLocalDateTime());
                    }
                    
                    return usuario;
                }
            }
            
        } catch (SQLException e) {
            ConexionBD.registrarError("Error al obtener usuario por ID", "obtenerUsuarioPorId", e.toString());
        }
        return null;
    }
    
    // Actualizar usuario
    public boolean actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE Usuarios SET nombre_usuario = ?, nombre_completo = ?, correo_electronico = ? WHERE id = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getNombreUsuario());
            stmt.setString(2, usuario.getNombreCompleto());
            stmt.setString(3, usuario.getCorreoElectronico());
            stmt.setInt(4, usuario.getId());
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            ConexionBD.registrarError("Error al actualizar usuario", "actualizarUsuario", e.toString());
            return false;
        }
    }
    
    // Eliminar usuario
    public boolean eliminarUsuario(int id) {
        String sql = "DELETE FROM Usuarios WHERE id = ?";
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            ConexionBD.registrarError("Error al eliminar usuario", "eliminarUsuario", e.toString());
            return false;
        }
    }
    
    // Buscar usuarios por nombre
    public List<Usuario> buscarUsuariosPorNombre(String nombre) {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuarios WHERE nombre_completo LIKE ? ORDER BY nombre_completo";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + nombre + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNombreUsuario(rs.getString("nombre_usuario"));
                    usuario.setNombreCompleto(rs.getString("nombre_completo"));
                    usuario.setCorreoElectronico(rs.getString("correo_electronico"));
                    
                    // Conversión de Timestamp a LocalDateTime
                    Timestamp timestamp = rs.getTimestamp("fecha_creacion");
                    if (timestamp != null) {
                        usuario.setFechaCreacion(timestamp.toLocalDateTime());
                    }
                    
                    usuarios.add(usuario);
                }
            }
            
        } catch (SQLException e) {
            ConexionBD.registrarError("Error al buscar usuarios", "buscarUsuariosPorNombre", e.toString());
        }
        return usuarios;
    }
}