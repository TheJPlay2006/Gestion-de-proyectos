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
    private JButton btnCompletar; // Botón adicional para tareas

    public TablaTareas() {
        tareaServicio = new TareaServicio();
        initComponents();
        setupLayout();
        cargarDatos();
    }

    private void initComponents() {
        String[] columnas = {"ID", "Título", "Proyecto ID", "Descripción", "Asignado a (ID)", "Estado", "Prioridad", "Inicio", "Vencimiento"};
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
        btnCompletar = new JButton("Marcar Completada");

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
        modeloTabla.setRowCount(0); // Limpiar tabla
        try {
            List<Tarea> tareas = tareaServicio.obtenerTodasLasTareas();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            for (Tarea tarea : tareas) {
                Object[] fila = {
                    tarea.getId(),
                    tarea.getTitulo(),
                    tarea.getProyectoId(),
                    tarea.getDescripcion() != null ? tarea.getDescripcion().substring(0, Math.min(30, tarea.getDescripcion().length())) : "", // Truncar descripción
                    tarea.getAsignadoA(),
                    tarea.getEstado(),
                    tarea.getPrioridad(),
                    tarea.getFechaInicio() != null ? tarea.getFechaInicio().format(formatter) : "",
                    tarea.getFechaVencimiento() != null ? tarea.getFechaVencimiento().format(formatter) : ""
                };
                modeloTabla.addRow(fila);
            }
        } catch (Exception e) {
             JOptionPane.showMessageDialog(this, "Error al cargar tareas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
             e.printStackTrace(); // Para diagnóstico en consola
        }
    }

    private void mostrarFormularioTarea(Tarea tarea) {
        // Asegúrate de pasar la ventana padre correcta
        FormularioTarea dialog = new FormularioTarea(SwingUtilities.getWindowAncestor(this), tarea);
        dialog.setVisible(true);

        if (dialog.isAceptado()) {
            cargarDatos(); // Refrescar la tabla
        }
    }

    private void editarTareaSeleccionada() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            try {
                int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
                Tarea tarea = tareaServicio.obtenerTareaPorId(id);
                if (tarea != null) {
                    mostrarFormularioTarea(tarea);
                } else {
                     JOptionPane.showMessageDialog(this, "No se encontró la tarea seleccionada.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                 JOptionPane.showMessageDialog(this, "Error al obtener datos de la tarea: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                 e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una tarea para editar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminarTareaSeleccionada() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            try {
                int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
                String titulo = (String) modeloTabla.getValueAt(filaSeleccionada, 1);

                int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de eliminar la tarea '" + titulo + "'?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

                if (opcion == JOptionPane.YES_OPTION) {
                    if (tareaServicio.eliminarTarea(id)) {
                        JOptionPane.showMessageDialog(this, "Tarea eliminada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        cargarDatos(); // Refrescar la tabla
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al eliminar la tarea.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception e) {
                 JOptionPane.showMessageDialog(this, "Error al eliminar la tarea: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                 e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una tarea para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void completarTareaSeleccionada() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            try {
                int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
                String titulo = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
                String estadoActual = (String) modeloTabla.getValueAt(filaSeleccionada, 5);
                
                if ("Hecho".equals(estadoActual)) {
                    JOptionPane.showMessageDialog(this, "La tarea ya está marcada como completada.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Marcar la tarea '" + titulo + "' como completada?",
                    "Confirmar Completado",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

                if (opcion == JOptionPane.YES_OPTION) {
                    // Suponiendo que tienes un método en TareaServicio para esto
                    if (tareaServicio.marcarTareaComoCompletada(id)) {
                        JOptionPane.showMessageDialog(this, "Tarea marcada como completada.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        cargarDatos(); // Refrescar la tabla
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al marcar la tarea como completada.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception e) {
                 JOptionPane.showMessageDialog(this, "Error al completar la tarea: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                 e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una tarea para completar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
}