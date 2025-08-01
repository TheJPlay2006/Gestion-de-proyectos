/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.componentes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import model.Proyecto;
import service.ProyectoServicio;
import java.time.format.DateTimeFormatter;

public class TablaProyectos extends JPanel {
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private ProyectoServicio proyectoServicio;
    private JButton btnNuevo;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnActualizar;
    
    public TablaProyectos() {
        proyectoServicio = new ProyectoServicio();
        initComponents();
        setupLayout();
        cargarDatos();
    }
    
    private void initComponents() {
        // Configurar tabla
        String[] columnas = {"ID", "Nombre", "Descripción", "Fecha Inicio", "Fecha Fin", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer las celdas no editables
            }
        };
        
        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Configurar botones
        btnNuevo = new JButton("Nuevo");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnActualizar = new JButton("Actualizar");
        
        // Agregar listeners
        btnNuevo.addActionListener(e -> mostrarFormularioProyecto(null));
        btnEditar.addActionListener(e -> editarProyectoSeleccionado());
        btnEliminar.addActionListener(e -> eliminarProyectoSeleccionado());
        btnActualizar.addActionListener(e -> cargarDatos());
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBotones.add(btnNuevo);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);
        
        add(panelBotones, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }
    
    public void cargarDatos() {
        // Limpiar tabla
        modeloTabla.setRowCount(0);
        
        // Cargar datos desde la base de datos
        List<Proyecto> proyectos = proyectoServicio.obtenerTodosLosProyectos();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        for (Proyecto proyecto : proyectos) {
            Object[] fila = {
                proyecto.getId(),
                proyecto.getNombre(),
                proyecto.getDescripcion(),
                proyecto.getFechaInicio() != null ? proyecto.getFechaInicio().format(formatter) : "",
                proyecto.getFechaFin() != null ? proyecto.getFechaFin().format(formatter) : "",
                proyecto.getEstado()
            };
            modeloTabla.addRow(fila);
        }
    }
    
    private void mostrarFormularioProyecto(Proyecto proyecto) {
        FormularioProyecto dialog = new FormularioProyecto(SwingUtilities.getWindowAncestor(this), proyecto);
        dialog.setVisible(true);
        
        if (dialog.isAceptado()) {
            cargarDatos();
        }
    }
    
    private void editarProyectoSeleccionado() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            Proyecto proyecto = proyectoServicio.obtenerProyectoPorId(id);
            if (proyecto != null) {
                mostrarFormularioProyecto(proyecto);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor seleccione un proyecto para editar", 
                                        "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void eliminarProyectoSeleccionado() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
            
            int opcion = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de eliminar el proyecto '" + nombre + "'?", 
                "Confirmar Eliminación", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE);
                
            if (opcion == JOptionPane.YES_OPTION) {
                if (proyectoServicio.eliminarProyecto(id)) {
                    JOptionPane.showMessageDialog(this, "Proyecto eliminado correctamente", 
                                                "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el proyecto", 
                                                "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor seleccione un proyecto para eliminar", 
                                        "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
}