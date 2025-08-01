package service;

import dao.UsuarioDAO;
import model.Usuario;
import util.ConexionBD; // Importación actualizada
import java.util.List;

public class UsuarioServicio {
    private UsuarioDAO usuarioDAO;
    
    public UsuarioServicio() {
        this.usuarioDAO = new UsuarioDAO();
    }
    
    // Crear un nuevo usuario
    public boolean crearUsuario(Usuario usuario) {
        try {
            // Validaciones
            if (usuario.getNombreUsuario() == null || usuario.getNombreUsuario().trim().isEmpty()) {
                System.err.println("El nombre de usuario es obligatorio");
                return false;
            }
            if (usuario.getNombreCompleto() == null || usuario.getNombreCompleto().trim().isEmpty()) {
                System.err.println("El nombre completo es obligatorio");
                return false;
            }
            
            return usuarioDAO.crearUsuario(usuario);
        } catch (Exception e) {
            ConexionBD.registrarError("Error en servicio al crear usuario", "crearUsuario", e.toString());
            return false;
        }
    }
    
    // Obtener todos los usuarios
    public List<Usuario> obtenerTodosLosUsuarios() {
        try {
            return usuarioDAO.obtenerTodosLosUsuarios();
        } catch (Exception e) {
            ConexionBD.registrarError("Error en servicio al obtener usuarios", "obtenerTodosLosUsuarios", e.toString());
            return null;
        }
    }
    
    // Obtener usuario por ID
    public Usuario obtenerUsuarioPorId(int id) {
        try {
            return usuarioDAO.obtenerUsuarioPorId(id);
        } catch (Exception e) {
            ConexionBD.registrarError("Error en servicio al obtener usuario por ID", "obtenerUsuarioPorId", e.toString());
            return null;
        }
    }
    
    // Actualizar usuario
    public boolean actualizarUsuario(Usuario usuario) {
        try {
            if (usuario.getId() <= 0) {
                System.err.println("ID de usuario inválido");
                return false;
            }
            if (usuario.getNombreUsuario() == null || usuario.getNombreUsuario().trim().isEmpty()) {
                System.err.println("El nombre de usuario es obligatorio");
                return false;
            }
            if (usuario.getNombreCompleto() == null || usuario.getNombreCompleto().trim().isEmpty()) {
                System.err.println("El nombre completo es obligatorio");
                return false;
            }
            
            return usuarioDAO.actualizarUsuario(usuario);
        } catch (Exception e) {
            ConexionBD.registrarError("Error en servicio al actualizar usuario", "actualizarUsuario", e.toString());
            return false;
        }
    }
    
    // Eliminar usuario
    public boolean eliminarUsuario(int id) {
        try {
            if (id <= 0) {
                System.err.println("ID de usuario inválido");
                return false;
            }
            return usuarioDAO.eliminarUsuario(id);
        } catch (Exception e) {
            ConexionBD.registrarError("Error en servicio al eliminar usuario", "eliminarUsuario", e.toString());
            return false;
        }
    }
    
    // Buscar usuarios por nombre
    public List<Usuario> buscarUsuariosPorNombre(String nombre) {
        try {
            if (nombre == null || nombre.trim().isEmpty()) {
                return obtenerTodosLosUsuarios();
            }
            return usuarioDAO.buscarUsuariosPorNombre(nombre);
        } catch (Exception e) {
            ConexionBD.registrarError("Error en servicio al buscar usuarios", "buscarUsuariosPorNombre", e.toString());
            return null;
        }
    }
}