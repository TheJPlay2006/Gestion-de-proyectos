    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.componentes;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import model.Proyecto;

public class FormularioProyecto extends JDialog {
    private JTextField txtNombre;
    private JTextArea txtDescripcion;
    private JTextField txtFechaInicio;
    private JTextField txtFechaFin;
    private JComboBox<String> cmbEstado;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private Proyecto proyecto;
    private boolean aceptado = false;
    private service.ProyectoServicio proyectoServicio;
    
    public FormularioProyecto(Window parent, Proyecto proyecto) {
        super(parent, proyecto == null ? "Nuevo Proyecto" : "Editar Proyecto", 
              ModalityType.APPLICATION_MODAL);
        this.proyecto = proyecto;
        this.proyectoServicio = new service.ProyectoServicio();
        initComponents();
        cargarDatos();
        setupLayout();
        setupEventHandlers();
        setSize(400, 350);
        setLocationRelativeTo(parent);
    }
    
    private void initComponents() {
        txtNombre = new JTextField(20);
        txtDescripcion = new JTextArea(4, 20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtFechaInicio = new JTextField(10);
        txtFechaFin = new JTextField(10);
        
        cmbEstado = new JComboBox<>(new String[]{
            "No Iniciado", "En Progreso", "Completado", "En Espera"
        });
        
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Panel de formulario
        JPanel panelForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        panelForm.add(txtNombre, gbc);
        
        // Descripción
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panelForm.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panelForm.add(new JScrollPane(txtDescripcion), gbc);
        
        // Fecha Inicio
        gbc.gridx = 0; gbc.gridy = 2;
        panelForm.add(new JLabel("Fecha Inicio (dd/MM/yyyy):"), gbc);
        gbc.gridx = 1;
        panelForm.add(txtFechaInicio, gbc);
        
        // Fecha Fin
        gbc.gridx = 0; gbc.gridy = 3;
        panelForm.add(new JLabel("Fecha Fin (dd/MM/yyyy):"), gbc);
        gbc.gridx = 1;
        panelForm.add(txtFechaFin, gbc);
        
        // Estado
        gbc.gridx = 0; gbc.gridy = 4;
        panelForm.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1;
        panelForm.add(cmbEstado, gbc);
        
        add(panelForm, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        btnGuardar.addActionListener(e -> guardarProyecto());
        btnCancelar.addActionListener(e -> dispose());
        
        // Cerrar con ESC
        getRootPane().registerKeyboardAction(e -> dispose(),
            KeyStroke.getKeyStroke("ESCAPE"), JComponent.WHEN_IN_FOCUSED_WINDOW);
    }
    
    private void cargarDatos() {
        if (proyecto != null) {
            txtNombre.setText(proyecto.getNombre());
            txtDescripcion.setText(proyecto.getDescripcion());
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            if (proyecto.getFechaInicio() != null) {
                txtFechaInicio.setText(proyecto.getFechaInicio().format(formatter));
            }
            if (proyecto.getFechaFin() != null) {
                txtFechaFin.setText(proyecto.getFechaFin().format(formatter));
            }
            
            cmbEstado.setSelectedItem(proyecto.getEstado());
        }
    }
    
    private void guardarProyecto() {
        try {
            // Validar datos
            String nombre = txtNombre.getText().trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre del proyecto es obligatorio", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
                txtNombre.requestFocus();
                return;
            }
            
            // Parsear fechas
            LocalDate fechaInicio = null;
            LocalDate fechaFin = null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            if (!txtFechaInicio.getText().trim().isEmpty()) {
                try {
                    fechaInicio = LocalDate.parse(txtFechaInicio.getText().trim(), formatter);
                } catch (DateTimeParseException e) {
                    JOptionPane.showMessageDialog(this, "Formato de fecha de inicio inválido. Use dd/MM/yyyy", 
                                                "Error", JOptionPane.ERROR_MESSAGE);
                    txtFechaInicio.requestFocus();
                    return;
                }
            }
            
            if (!txtFechaFin.getText().trim().isEmpty()) {
                try {
                    fechaFin = LocalDate.parse(txtFechaFin.getText().trim(), formatter);
                } catch (DateTimeParseException e) {
                    JOptionPane.showMessageDialog(this, "Formato de fecha de fin inválido. Use dd/MM/yyyy", 
                                                "Error", JOptionPane.ERROR_MESSAGE);
                    txtFechaFin.requestFocus();
                    return;
                }
            }
            
            // Validar que fecha inicio no sea posterior a fecha fin
            if (fechaInicio != null && fechaFin != null && fechaInicio.isAfter(fechaFin)) {
                JOptionPane.showMessageDialog(this, "La fecha de inicio no puede ser posterior a la fecha de fin", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Crear o actualizar proyecto
            if (proyecto == null) {
                proyecto = new Proyecto();
            }
            
            proyecto.setNombre(nombre);
            proyecto.setDescripcion(txtDescripcion.getText().trim());
            proyecto.setFechaInicio(fechaInicio);
            proyecto.setFechaFin(fechaFin);
            proyecto.setEstado((String) cmbEstado.getSelectedItem());
            
            boolean exito;
            if (proyecto.getId() == 0) {
                exito = proyectoServicio.crearProyecto(proyecto);
            } else {
                exito = proyectoServicio.actualizarProyecto(proyecto);
            }
            
            if (exito) {
                aceptado = true;
                JOptionPane.showMessageDialog(this, "Proyecto guardado correctamente", 
                                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el proyecto", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar el proyecto: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean isAceptado() {
        return aceptado;
    }
}