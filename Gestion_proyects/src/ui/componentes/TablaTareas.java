/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.componentes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import model.Tarea;
import service.TareaServicio;
import java.time.format.DateTimeFormatter;

public class TablaTareas extends JPanel {
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private TareaServicio tareaServicio;
    private JButton btnNuevo;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnActualizar;
    private JButton btnCompletar;
    
    public TablaTareas() {
        tareaServicio = new TareaServicio();
        initComponents();
        setupLayout();
        cargarDatos();
    }
    
    private void initComponents() {
        String[] columnas = {"ID", "Título", "Proyecto ID", "Descripción", "Asignado a", 
                           "Estado", "Prioridad", "Fecha Inicio", "Fecha Vencimiento"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        btnNuevo = new JButton("Nueva Tarea");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnActualizar = new JButton("Actualizar");
        btnCompletar = new JButton("Marcar como Completada");
        
        btnNuevo.addActionListener(e -> mostrarFormularioTarea(null));
        btnEditar.addActionListener(e -> editarTareaSeleccionada());
        btnEliminar.addActionListener(e -> eliminarTareaSeleccionada());
        btnActualizar.addActionListener(e -> cargarDatos());
        btnCompletar.addActionListener(e -> completarTareaSeleccionada());
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBotones.add(btnNuevo);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCompletar);
        panelBotones.add(btnActualizar);
        
        add(panelBotones, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }
    
    public void cargarDatos() {
        modeloTabla.setRowCount(0);
        
        List<Tarea> tareas = tareaServicio.obtenerTodasLasTareas();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        for (Tarea tarea : tareas) {
            Object[] fila = {
                tarea.getId(),
                tarea.getTitulo(),
                tarea.getProyectoId(),
                tarea.getDescripcion(),
                tarea.getAsignadoA(),
                tarea.getEstado(),
                tarea.getPrioridad(),
                tarea.getFechaInicio() != null ? tarea.getFechaInicio().format(formatter) : "",
                tarea.getFechaVencimiento() != null ? tarea.getFechaVencimiento().format(formatter) : ""
            };
            modeloTabla.addRow(fila);
        }
    }
    
    private void mostrarFormularioTarea(Tarea tarea) {
        FormularioTarea dialog = new FormularioTarea(SwingUtilities.getWindowAncestor(this), tarea);
        dialog.setVisible(true);
        
        if (dialog.isAceptado()) {
            cargarDatos();
        }
    }
    
    private void editarTareaSeleccionada() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            Tarea tarea = tareaServicio.obtenerTareaPorId(id);
            if (tarea != null) {
                mostrarFormularioTarea(tarea);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor seleccione una tarea para editar", 
                                        "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void eliminarTareaSeleccionada() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            String titulo = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
            
            int opcion = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de eliminar la tarea '" + titulo + "'?", 
                "Confirmar Eliminación", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE);
                
            if (opcion == JOptionPane.YES_OPTION) {
                if (tareaServicio.eliminarTarea(id)) {
                    JOptionPane.showMessageDialog(this, "Tarea eliminada correctamente", 
                                                "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar la tarea", 
                                                "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor seleccione una tarea para eliminar", 
                                        "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void completarTareaSeleccionada() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            String titulo = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
            
            int opcion = JOptionPane.showConfirmDialog(this, 
                "¿Marcar la tarea '" + titulo + "' como completada?", 
                "Confirmar Completado", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE);
                
            if (opcion == JOptionPane.YES_OPTION) {
                if (tareaServicio.marcarTareaComoCompletada(id)) {
                    JOptionPane.showMessageDialog(this, "Tarea marcada como completada", 
                                                "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarDatos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al marcar la tarea como completada", 
                                                "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor seleccione una tarea para completar", 
                                        "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
}