package ui.componentes;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import model.Tarea;
import service.UsuarioServicio; // Para cargar usuarios
import model.Usuario; // Para el combo box
import java.util.List;

public class FormularioTarea extends JDialog {
    private JTextField txtTitulo;
    private JTextArea txtDescripcion;
    private JTextField txtProyectoId;
    private JComboBox<Usuario> cmbAsignadoA; // Usamos Usuario para mostrar nombre
    private JComboBox<String> cmbEstado;
    private JComboBox<String> cmbPrioridad;
    private JTextField txtFechaInicio;
    private JTextField txtFechaVencimiento;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private Tarea tarea;
    private boolean aceptado = false;
    private service.TareaServicio tareaServicio;
    private UsuarioServicio usuarioServicio;

    public FormularioTarea(Window parent, Tarea tarea) {
        super(parent, tarea == null ? "Nueva Tarea" : "Editar Tarea", ModalityType.APPLICATION_MODAL);
        this.tarea = tarea;
        this.tareaServicio = new service.TareaServicio();
        this.usuarioServicio = new UsuarioServicio(); // Inicializar servicio de usuarios
        initComponents();
        cargarUsuarios(); // Cargar usuarios en el combo
        cargarDatos();
        setupLayout();
        setupEventHandlers();
        setSize(450, 500); // Ajustar tamaño
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        txtTitulo = new JTextField(25);
        txtDescripcion = new JTextArea(4, 25); // Más filas para descripción
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtProyectoId = new JTextField(10);
        cmbAsignadoA = new JComboBox<>();
        cmbEstado = new JComboBox<>(new String[]{"Por Hacer", "En Progreso", "Revisión", "Hecho"});
        cmbPrioridad = new JComboBox<>(new String[]{"Baja", "Media", "Alta"});
        txtFechaInicio = new JTextField(10);
        txtFechaVencimiento = new JTextField(10);

        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
    }
    
    private void cargarUsuarios() {
        cmbAsignadoA.removeAllItems();
        cmbAsignadoA.addItem(null); // Opción para "no asignado"
        try {
            List<Usuario> usuarios = usuarioServicio.obtenerTodosLosUsuarios();
            for (Usuario usuario : usuarios) {
                cmbAsignadoA.addItem(usuario); // El toString de Usuario debe mostrar el nombre
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar usuarios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Título
        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(new JLabel("Título (*):"), gbc); // (*) indica obligatorio
        gbc.gridx = 1; gbc.gridwidth = 2;
        panelForm.add(txtTitulo, gbc);

        // Descripción
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panelForm.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panelForm.add(new JScrollPane(txtDescripcion), gbc);

        // Proyecto ID
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        panelForm.add(new JLabel("ID Proyecto (*):"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        panelForm.add(txtProyectoId, gbc);

        // Asignado a
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1;
        panelForm.add(new JLabel("Asignado a:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        panelForm.add(cmbAsignadoA, gbc);

        // Estado
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 1;
        panelForm.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        panelForm.add(cmbEstado, gbc);

        // Prioridad
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 1;
        panelForm.add(new JLabel("Prioridad:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        panelForm.add(cmbPrioridad, gbc);

        // Fecha Inicio
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 1;
        panelForm.add(new JLabel("Fecha Inicio (dd/MM/yyyy):"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        panelForm.add(txtFechaInicio, gbc);

        // Fecha Vencimiento
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 1;
        panelForm.add(new JLabel("Fecha Vencimiento (dd/MM/yyyy):"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        panelForm.add(txtFechaVencimiento, gbc);

        add(panelForm, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() {
        btnGuardar.addActionListener(e -> guardarTarea());
        btnCancelar.addActionListener(e -> dispose());

        getRootPane().registerKeyboardAction(e -> dispose(),
            KeyStroke.getKeyStroke("ESCAPE"), JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    private void cargarDatos() {
        if (tarea != null) {
            txtTitulo.setText(tarea.getTitulo());
            txtDescripcion.setText(tarea.getDescripcion());
            txtProyectoId.setText(String.valueOf(tarea.getProyectoId()));

            // Seleccionar usuario asignado
            if (tarea.getAsignadoA() != null) {
                for (int i = 0; i < cmbAsignadoA.getItemCount(); i++) {
                    Usuario usuario = cmbAsignadoA.getItemAt(i);
                    if (usuario != null && usuario.getId() == tarea.getAsignadoA()) {
                        cmbAsignadoA.setSelectedItem(usuario);
                        break;
                    }
                }
            }

            cmbEstado.setSelectedItem(tarea.getEstado());
            cmbPrioridad.setSelectedItem(tarea.getPrioridad());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            if (tarea.getFechaInicio() != null) {
                txtFechaInicio.setText(tarea.getFechaInicio().format(formatter));
            }
            if (tarea.getFechaVencimiento() != null) {
                txtFechaVencimiento.setText(tarea.getFechaVencimiento().format(formatter));
            }
        }
    }

    private void guardarTarea() {
        try {
            // Validaciones
            String titulo = txtTitulo.getText().trim();
            if (titulo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El título de la tarea es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
                txtTitulo.requestFocus();
                return;
            }

            String proyectoIdStr = txtProyectoId.getText().trim();
            if (proyectoIdStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El ID del proyecto es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
                txtProyectoId.requestFocus();
                return;
            }

            int proyectoId;
            try {
                proyectoId = Integer.parseInt(proyectoIdStr);
                if (proyectoId <= 0) {
                    JOptionPane.showMessageDialog(this, "El ID del proyecto debe ser un número positivo.", "Error", JOptionPane.ERROR_MESSAGE);
                    txtProyectoId.requestFocus();
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El ID del proyecto debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                txtProyectoId.requestFocus();
                return;
            }

            // Parsear fechas
            LocalDate fechaInicio = null;
            LocalDate fechaVencimiento = null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            if (!txtFechaInicio.getText().trim().isEmpty()) {
                try {
                    fechaInicio = LocalDate.parse(txtFechaInicio.getText().trim(), formatter);
                } catch (DateTimeParseException e) {
                    JOptionPane.showMessageDialog(this, "Formato de fecha de inicio inválido. Use dd/MM/yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
                    txtFechaInicio.requestFocus();
                    return;
                }
            }

            if (!txtFechaVencimiento.getText().trim().isEmpty()) {
                try {
                    fechaVencimiento = LocalDate.parse(txtFechaVencimiento.getText().trim(), formatter);
                } catch (DateTimeParseException e) {
                    JOptionPane.showMessageDialog(this, "Formato de fecha de vencimiento inválido. Use dd/MM/yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
                    txtFechaVencimiento.requestFocus();
                    return;
                }
            }

            // Validar fechas
            if (fechaInicio != null && fechaVencimiento != null && fechaInicio.isAfter(fechaVencimiento)) {
                JOptionPane.showMessageDialog(this, "La fecha de inicio no puede ser posterior a la fecha de vencimiento.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear o actualizar tarea
            if (tarea == null) {
                tarea = new Tarea();
            }

            tarea.setTitulo(titulo);
            tarea.setDescripcion(txtDescripcion.getText().trim());
            tarea.setProyectoId(proyectoId);

            // Obtener ID del usuario seleccionado
            Usuario usuarioSeleccionado = (Usuario) cmbAsignadoA.getSelectedItem();
            tarea.setAsignadoA(usuarioSeleccionado != null ? usuarioSeleccionado.getId() : null);

            tarea.setEstado((String) cmbEstado.getSelectedItem());
            tarea.setPrioridad((String) cmbPrioridad.getSelectedItem());
            tarea.setFechaInicio(fechaInicio);
            tarea.setFechaVencimiento(fechaVencimiento);

            boolean exito;
            if (tarea.getId() == 0) { // Nueva tarea
                exito = tareaServicio.crearTarea(tarea);
            } else { // Editar tarea
                exito = tareaServicio.actualizarTarea(tarea);
            }

            if (exito) {
                aceptado = true;
                JOptionPane.showMessageDialog(this, "Tarea guardada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar la tarea.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar la tarea: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Para diagnóstico
        }
    }

    public boolean isAceptado() {
        return aceptado;
    }
}
