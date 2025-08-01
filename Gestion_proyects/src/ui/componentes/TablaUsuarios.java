/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.componentes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import model.Usuario;
import service.UsuarioServicio;
import java.time.format.DateTimeFormatter;

public class TablaUsuarios extends JPanel {
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private UsuarioServicio usuarioServicio;
    private JButton btnNuevo;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnActualizar;
    
    public TablaUsuarios() {
        usuarioServicio = new UsuarioServicio();
        initComponents();
        setupLayout();
        cargarDatos();
    }
    
    private void initComponents() {
        String[] columnas = {"ID", "Nombre de Usuario", "Nombre Completo", "Correo Electrónico", "Fecha Creación"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        btnNuevo = new JButton("Nuevo Usuario");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnActualizar = new JButton("Actualizar");
        
        btnNuevo.addActionListener(e -> mostrarFormularioUsuario(null));
        btnEditar.addActionListener(e -> editarUsuarioSeleccionado());
        btnEliminar.addActionListener(e -> eliminarUsuarioSeleccionado());
        btnActualizar.addActionListener(e -> cargarDatos());
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBotones.add(btnNuevo);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);
        
        add(panelBotones, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }
    
    public void cargarDatos() {
        modeloTabla.setRowCount(0);
        
        List<Usuario> usuarios = usuarioServicio.obtenerTodosLosUsuarios();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        for (Usuario usuario : usuarios) {
            Object[] fila = {
                usuario.getId(),
                usuario.getNombreUsuario(),
                usuario.getNombreCompleto(),
                usuario.getCorreoElectronico(),
                usuario.getFechaCreacion() != null ? usuario.getFechaCreacion().format(formatter) : ""
            };
            modeloTabla.addRow(fila);
        }
    }
    
    private void mostrarFormularioUsuario(Usuario usuario) {
        FormularioUsuario dialog = new FormularioUsuario(SwingUtilities.getWindowAncestor(this), usuario);
        dialog.setVisible(true);
        
        if (dialog.isAceptado()) {
            cargarDatos();
        }
    }
    
    private void editarUsuarioSeleccionado() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            Usuario usuario = usuarioServicio.obtenerUsuarioPorId(id);
            if (usuario != null) {
                mostrarFormularioUsuario(usuario);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor seleccione un usuario para editar", 
                                        "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void eliminarUsuarioSeleccionado() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 2);
            
            int opcion = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de eliminar el usuario '" + nombre + "'?", 
                "Confirmar Eliminación", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE);
                
            if (opcion == JOptionPane.YES_OPTION) {
                if (usuarioServicio.eliminarUsuario(id)) {
                    JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente", 
                                                "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el usuario", 
                                                "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor seleccione un usuario para eliminar", 
                                        "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
}