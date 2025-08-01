package ui.componentes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import model.Recurso;
import service.RecursoServicio;
import java.time.format.DateTimeFormatter;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TablaRecursos extends JPanel {
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private RecursoServicio recursoServicio;
    private JButton btnNuevo;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnActualizar;
    private JButton btnAbrir; // Nuevo botón para abrir el recurso
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JButton btnLimpiarBusqueda;

    public TablaRecursos() {
        recursoServicio = new RecursoServicio();
        initComponents();
        setupLayout();
        cargarDatos();
    }

    private void initComponents() {
        // Definir columnas de la tabla
        String[] columnas = {"ID", "Nombre del Recurso", "Tipo", "Ruta/URL", "ID Tarea", "Fecha Subida"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer celdas no editables
            }
        };

        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // Ajustar el ancho de las columnas
        tabla.getColumnModel().getColumn(0).setPreferredWidth(40);  // ID
        tabla.getColumnModel().getColumn(1).setPreferredWidth(150); // Nombre
        tabla.getColumnModel().getColumn(2).setPreferredWidth(80);  // Tipo
        tabla.getColumnModel().getColumn(3).setPreferredWidth(200); // Ruta/URL
        tabla.getColumnModel().getColumn(4).setPreferredWidth(60);  // ID Tarea
        tabla.getColumnModel().getColumn(5).setPreferredWidth(120); // Fecha

        // Crear botones
        btnNuevo = new JButton("Nuevo Recurso");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnActualizar = new JButton("Actualizar");
        btnAbrir = new JButton("Abrir/Ver"); // Nuevo botón

        // Crear componentes de búsqueda
        txtBuscar = new JTextField(15);
        btnBuscar = new JButton("Buscar");
        btnLimpiarBusqueda = new JButton("Limpiar");

        // Asociar listeners a los botones
        btnNuevo.addActionListener(e -> mostrarFormularioRecurso(null));
        btnEditar.addActionListener(e -> editarRecursoSeleccionado());
        btnEliminar.addActionListener(e -> eliminarRecursoSeleccionado());
        btnActualizar.addActionListener(e -> cargarDatos());
        btnAbrir.addActionListener(e -> abrirRecursoSeleccionado());
        
        btnBuscar.addActionListener(e -> buscarRecursos());
        btnLimpiarBusqueda.addActionListener(e -> {
            txtBuscar.setText("");
            cargarDatos(); // Recargar todos
        });
        
        // Atajo de teclado para búsqueda (Enter en el campo de texto)
        txtBuscar.addActionListener(e -> buscarRecursos());
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Panel superior: Búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("Buscar Recursos"));
        panelBusqueda.add(new JLabel("Nombre:"));
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);
        panelBusqueda.add(btnLimpiarBusqueda);

        // Panel central: Botones de acción
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBotones.setBorder(BorderFactory.createTitledBorder("Acciones"));
        panelBotones.add(btnNuevo);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnAbrir); // Agregar botón nuevo
        panelBotones.add(btnActualizar);

        // Panel para los botones superiores
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelBusqueda, BorderLayout.NORTH);
        panelSuperior.add(panelBotones, BorderLayout.CENTER);

        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    /**
     * Carga todos los recursos desde la base de datos en la tabla.
     */
    public void cargarDatos() {
        cargarDatosDesdeLista(recursoServicio.obtenerTodosLosRecursos());
    }
    
    /**
     * Carga una lista específica de recursos en la tabla.
     * @param recursos La lista de recursos a mostrar.
     */
    private void cargarDatosDesdeLista(List<Recurso> recursos) {
        modeloTabla.setRowCount(0); // Limpiar tabla
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        try {
            for (Recurso recurso : recursos) {
                Object[] fila = {
                    recurso.getId(),
                    recurso.getNombreRecurso(),
                    recurso.getTipoRecurso(),
                    recurso.getRutaRecurso(),
                    recurso.getTareaId(),
                    recurso.getFechaSubida() != null ? recurso.getFechaSubida().format(formatter) : ""
                };
                modeloTabla.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar recursos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Muestra el formulario para crear o editar un recurso.
     * @param recurso El recurso a editar, o null para crear uno nuevo.
     */
    private void mostrarFormularioRecurso(Recurso recurso) {
        FormularioRecurso dialog = new FormularioRecurso(SwingUtilities.getWindowAncestor(this), recurso);
        dialog.setVisible(true);

        if (dialog.isAceptado()) {
            cargarDatos(); // Refrescar la tabla
            JOptionPane.showMessageDialog(this, "Recurso " + (recurso == null ? "creado" : "actualizado") + " correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Edita el recurso seleccionado en la tabla.
     */
    private void editarRecursoSeleccionado() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            try {
                int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
                Recurso recurso = recursoServicio.obtenerRecursoPorId(id);
                if (recurso != null) {
                    mostrarFormularioRecurso(recurso);
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el recurso seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al obtener datos del recurso: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un recurso para editar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Elimina el recurso seleccionado en la tabla.
     */
    private void eliminarRecursoSeleccionado() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            try {
                int id = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
                String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 1);

                int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de eliminar el recurso '" + nombre + "'?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

                if (opcion == JOptionPane.YES_OPTION) {
                    if (recursoServicio.eliminarRecurso(id)) {
                        cargarDatos(); // Refrescar la tabla
                        JOptionPane.showMessageDialog(this, "Recurso eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al eliminar el recurso.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar el recurso: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un recurso para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * Intenta abrir o ver el recurso seleccionado (si es un archivo local o una URL).
     */
    private void abrirRecursoSeleccionado() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada >= 0) {
            try {
                String ruta = (String) modeloTabla.getValueAt(filaSeleccionada, 3); // Columna Ruta/URL
                String tipo = (String) modeloTabla.getValueAt(filaSeleccionada, 2); // Columna Tipo

                if (ruta == null || ruta.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No hay ruta o URL definida para este recurso.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                if ("Enlace".equalsIgnoreCase(tipo) || ruta.startsWith("http://") || ruta.startsWith("https://")) {
                    // Intentar abrir en el navegador
                    try {
                        java.awt.Desktop.getDesktop().browse(new java.net.URI(ruta));
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "No se pudo abrir el enlace automáticamente. Copie y pegue la siguiente URL en su navegador:\n" + ruta, "Información", JOptionPane.INFORMATION_MESSAGE);
                        // Opcional: Copiar al portapapeles
                        // java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new java.awt.datatransfer.StringSelection(ruta), null);
                    }
                } else {
                    // Asumir que es un archivo local
                    File archivo = new File(ruta);
                    if (archivo.exists()) {
                        try {
                            java.awt.Desktop.getDesktop().open(archivo);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(this, "No se pudo abrir el archivo automáticamente. Verifique que la aplicación asociada esté instalada.\nRuta: " + ruta, "Advertencia", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        // Pedir al usuario que busque el archivo si la ruta guardada no existe
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setSelectedFile(new File(ruta)); // Sugerir la ruta guardada
                         FileNameExtensionFilter filter = new FileNameExtensionFilter(
                            "Archivos comunes (PDF, DOC, XLS, TXT, JPG, PNG)", 
                            "pdf", "doc", "docx", "xls", "xlsx", "txt", "jpg", "jpeg", "png");
                        fileChooser.setFileFilter(filter);
                        
                        int userSelection = fileChooser.showOpenDialog(this);
                        if (userSelection == JFileChooser.APPROVE_OPTION) {
                            File archivoSeleccionado = fileChooser.getSelectedFile();
                            try {
                                java.awt.Desktop.getDesktop().open(archivoSeleccionado);
                                // Opcional: Preguntar si desea actualizar la ruta en la BD
                                // int actualizar = JOptionPane.showConfirmDialog(this, "¿Desea actualizar la ruta del recurso en la base de datos?", "Actualizar Ruta", JOptionPane.YES_NO_OPTION);
                                // if (actualizar == JOptionPane.YES_OPTION) {
                                //     // Lógica para actualizar la ruta en la BD (requiere modificar RecursoServicio)
                                // }
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(this, "No se pudo abrir el archivo seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al intentar abrir el recurso: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un recurso para abrir.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    
    /**
     * Busca recursos cuyo nombre contenga el texto ingresado.
     */
    private void buscarRecursos() {
        String criterio = txtBuscar.getText().trim();
        if (criterio.isEmpty()) {
            cargarDatos(); // Si no hay criterio, cargar todos
        } else {
            try {
                List<Recurso> resultados = recursoServicio.buscarRecursosPorNombre(criterio);
                cargarDatosDesdeLista(resultados);
                if (resultados.isEmpty()) {
                     JOptionPane.showMessageDialog(this, "No se encontraron recursos con el nombre '" + criterio + "'.", "Búsqueda", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al buscar recursos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                cargarDatos(); // En caso de error, recargar todos
            }
        }
    }
}