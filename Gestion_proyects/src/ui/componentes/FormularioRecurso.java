package ui.componentes;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import model.Recurso;
import service.TareaServicio; // Para validar el ID de tarea
import model.Tarea; // Para el combo box de tareas (opcional)
import java.util.List; // Para cargar lista de tareas (opcional)

public class FormularioRecurso extends JDialog {
    private JTextField txtNombreRecurso;
    private JComboBox<String> cmbTipoRecurso;
    private JTextField txtRutaRecurso;
    private JTextField txtTareaId;
    private JButton btnExaminar; // Botón para seleccionar archivo
    private JButton btnGuardar;
    private JButton btnCancelar;
    private Recurso recurso;
    private boolean aceptado = false;
    private service.RecursoServicio recursoServicio;
    // Opcional: Para un combo de selección de tareas en lugar de ID numérico
    // private JComboBox<Tarea> cmbTarea; 
    // private TareaServicio tareaServicio;

    public FormularioRecurso(Window parent, Recurso recurso) {
        super(parent, recurso == null ? "Nuevo Recurso" : "Editar Recurso", ModalityType.APPLICATION_MODAL);
        this.recurso = recurso;
        this.recursoServicio = new service.RecursoServicio();
        // this.tareaServicio = new TareaServicio(); // Si usas combo de tareas
        initComponents();
        // cargarTareas(); // Si usas combo de tareas
        cargarDatos();
        setupLayout();
        setupEventHandlers();
        setSize(500, 300);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        txtNombreRecurso = new JTextField(30);
        // Tipos de recursos comunes
        cmbTipoRecurso = new JComboBox<>(new String[]{"Documento", "Enlace", "Imagen", "Hoja de Cálculo", "Presentación", "Archivo Comprimido", "Otro"});
        txtRutaRecurso = new JTextField(30);
        txtTareaId = new JTextField(10);
        // txtTareaId.setInputVerifier(new IntegerInputVerifier()); // Opcional: Verificador de enteros

        btnExaminar = new JButton("Examinar..."); // Botón para archivos locales
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
    }
    
    // Opcional: Cargar tareas en un JComboBox en lugar de usar ID numérico
    /*
    private void cargarTareas() {
        cmbTarea.removeAllItems();
        cmbTarea.addItem(null); // Opción para "sin asignar"
        try {
            List<Tarea> tareas = tareaServicio.obtenerTodasLasTareas();
            for (Tarea tarea : tareas) {
                cmbTarea.addItem(tarea);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar tareas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    */

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Panel del formulario
        JPanel panelForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Nombre del Recurso
        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(new JLabel("Nombre del Recurso (*):"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        panelForm.add(txtNombreRecurso, gbc);

        // Tipo de Recurso
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        panelForm.add(new JLabel("Tipo (*):"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        panelForm.add(cmbTipoRecurso, gbc);

        // Ruta/URL del Recurso
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        panelForm.add(new JLabel("Ruta/URL (*):"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 1;
        panelForm.add(txtRutaRecurso, gbc);
        gbc.gridx = 2; gbc.gridwidth = 1;
        panelForm.add(btnExaminar, gbc); // Botón Examinar al lado del campo de ruta

        // ID de la Tarea (o JComboBox de tareas)
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1;
        panelForm.add(new JLabel("ID Tarea:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        panelForm.add(txtTareaId, gbc);
        // Si usaras el combo: panelForm.add(cmbTarea, gbc);

        add(panelForm, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() {
        btnGuardar.addActionListener(e -> guardarRecurso());
        btnCancelar.addActionListener(e -> dispose());
        btnExaminar.addActionListener(e -> seleccionarArchivo()); // Acción del botón Examinar

        // Cerrar con ESC
        getRootPane().registerKeyboardAction(e -> dispose(),
            KeyStroke.getKeyStroke("ESCAPE"), JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    private void cargarDatos() {
        if (recurso != null) {
            txtNombreRecurso.setText(recurso.getNombreRecurso());
            cmbTipoRecurso.setSelectedItem(recurso.getTipoRecurso());
            txtRutaRecurso.setText(recurso.getRutaRecurso());
            if (recurso.getTareaId() > 0) {
                txtTareaId.setText(String.valueOf(recurso.getTareaId()));
            }
            // Si usaras el combo:
            // if (recurso.getTareaId() > 0) {
            //     // Buscar la tarea en el combo y seleccionarla
            //     for (int i = 0; i < cmbTarea.getItemCount(); i++) {
            //         Tarea t = cmbTarea.getItemAt(i);
            //         if (t != null && t.getId() == recurso.getTareaId()) {
            //             cmbTarea.setSelectedItem(t);
            //             break;
            //         }
            //     }
            // }
        }
    }

    private void seleccionarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        // Sugerir la ruta actual si ya hay algo en el campo
        String rutaActual = txtRutaRecurso.getText().trim();
        if (!rutaActual.isEmpty()) {
            File archivoActual = new File(rutaActual);
            if (archivoActual.exists() && archivoActual.isFile()) {
                fileChooser.setSelectedFile(archivoActual);
            } else if (archivoActual.exists() && archivoActual.isDirectory()) {
                fileChooser.setCurrentDirectory(archivoActual);
            }
        }
        
        int userSelection = fileChooser.showOpenDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            txtRutaRecurso.setText(archivoSeleccionado.getAbsolutePath());
            
            // Sugerir un nombre si el campo está vacío
            if (txtNombreRecurso.getText().trim().isEmpty()) {
                txtNombreRecurso.setText(archivoSeleccionado.getName());
            }
            
            // Sugerir un tipo basado en la extensión si no se ha seleccionado uno
            if (cmbTipoRecurso.getSelectedIndex() == 0 && cmbTipoRecurso.getItemAt(0).equals("Documento")) { // Asumiendo que "Documento" es el primero por defecto
                 String nombre = archivoSeleccionado.getName().toLowerCase();
                 if (nombre.endsWith(".pdf")) {
                     cmbTipoRecurso.setSelectedItem("Documento");
                 } else if (nombre.endsWith(".jpg") || nombre.endsWith(".jpeg") || nombre.endsWith(".png") || nombre.endsWith(".gif")) {
                     cmbTipoRecurso.setSelectedItem("Imagen");
                 } else if (nombre.endsWith(".xls") || nombre.endsWith(".xlsx")) {
                     cmbTipoRecurso.setSelectedItem("Hoja de Cálculo");
                 } else if (nombre.endsWith(".ppt") || nombre.endsWith(".pptx")) {
                     cmbTipoRecurso.setSelectedItem("Presentación");
                 } else if (nombre.endsWith(".zip") || nombre.endsWith(".rar")) {
                     cmbTipoRecurso.setSelectedItem("Archivo Comprimido");
                 }
                 // Puedes agregar más tipos según tus necesidades
            }
        }
    }

    private void guardarRecurso() {
        try {
            // --- Validaciones ---
            String nombre = txtNombreRecurso.getText().trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre del recurso es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
                txtNombreRecurso.requestFocusInWindow();
                return;
            }

            String tipo = (String) cmbTipoRecurso.getSelectedItem();
            if (tipo == null || tipo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El tipo de recurso es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
                cmbTipoRecurso.requestFocusInWindow();
                return;
            }

            String ruta = txtRutaRecurso.getText().trim();
            if (ruta.isEmpty()) {
                JOptionPane.showMessageDialog(this, "La ruta o URL del recurso es obligatoria.", "Error", JOptionPane.ERROR_MESSAGE);
                txtRutaRecurso.requestFocusInWindow();
                return;
            }

            // Validar ID de Tarea (si se proporciona)
            Integer tareaId = null;
            String tareaIdStr = txtTareaId.getText().trim();
            if (!tareaIdStr.isEmpty()) {
                try {
                    tareaId = Integer.parseInt(tareaIdStr);
                    if (tareaId <= 0) {
                        JOptionPane.showMessageDialog(this, "El ID de la tarea debe ser un número positivo.", "Error", JOptionPane.ERROR_MESSAGE);
                        txtTareaId.requestFocusInWindow();
                        return;
                    }
                    // Opcional: Verificar si la tarea existe
                    // TareaServicio ts = new TareaServicio();
                    // if (ts.obtenerTareaPorId(tareaId) == null) {
                    //     JOptionPane.showMessageDialog(this, "No se encontró una tarea con el ID " + tareaId + ".", "Error", JOptionPane.ERROR_MESSAGE);
                    //     txtTareaId.requestFocusInWindow();
                    //     return;
                    // }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "El ID de la tarea debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    txtTareaId.requestFocusInWindow();
                    return;
                }
            }

            // --- Crear o actualizar el objeto Recurso ---
            if (recurso == null) {
                recurso = new model.Recurso(); // Crear nueva instancia
                // La fecha de subida se puede establecer en el constructor del modelo o en la BD
            }

            recurso.setNombreRecurso(nombre);
            recurso.setTipoRecurso(tipo);
            recurso.setRutaRecurso(ruta);
            recurso.setTareaId(tareaId); // Puede ser null

            // --- Llamar al servicio para guardar ---
            boolean exito;
            if (recurso.getId() == 0) { // Nuevo recurso
                exito = recursoServicio.crearRecurso(recurso);
            } else { // Editar recurso existente
                exito = recursoServicio.actualizarRecurso(recurso);
            }

            if (exito) {
                aceptado = true;
                JOptionPane.showMessageDialog(this, "Recurso guardado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Cerrar el diálogo
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el recurso. Es posible que ya exista un recurso con ese nombre o que los datos sean incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error inesperado al guardar el recurso: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Para diagnóstico en consola
        }
    }

    public boolean isAceptado() {
        return aceptado;
    }
    
}