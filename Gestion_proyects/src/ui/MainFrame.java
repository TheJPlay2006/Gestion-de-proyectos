package ui;

import javax.swing.*;
import java.awt.*;
import ui.componentes.TablaProyectos;
import ui.componentes.TablaTareas; // Asegúrate de tener esta clase
import ui.componentes.TablaUsuarios; // Asegúrate de tener esta clase
import ui.componentes.TablaRecursos; // Asegúrate de tener esta clase

public class MainFrame extends JFrame {
    private JMenuBar menuBar;
    private JToolBar toolBar;
    private JPanel navigationPanel;
    private JTabbedPane mainTabbedPane;
    private JPanel dashboardPanel;
    private JPanel proyectosPanel;
    private JPanel tareasPanel;
    private JPanel usuariosPanel;
    private JPanel recursosPanel;

    public MainFrame() {
        // Establecer el look and feel del sistema
        try {
        } catch (Exception e) {
            System.err.println("No se pudo establecer el look and feel del sistema: " + e.getMessage());
        }
        
        initComponents();
        setupLayout();
        setupEventHandlers();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Gestor de Proyectos - Jairo Herrera");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        // setExtendedState(JFrame.MAXIMIZED_BOTH); // Opcional
    }

    private void initComponents() {
        createMenuBar();
        createToolBar();
        createNavigationPanel();
        createMainTabs();
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();

        // Menú Archivo
        JMenu archivoMenu = new JMenu("Archivo");
        JMenuItem nuevoProyectoItem = new JMenuItem("Nuevo Proyecto");
        JMenuItem nuevaTareaItem = new JMenuItem("Nueva Tarea");
        JMenuItem guardarItem = new JMenuItem("Guardar");
        JMenuItem salirItem = new JMenuItem("Salir");
        salirItem.addActionListener(e -> System.exit(0));

        archivoMenu.add(nuevoProyectoItem);
        archivoMenu.add(nuevaTareaItem);
        archivoMenu.addSeparator();
        archivoMenu.add(guardarItem);
        archivoMenu.addSeparator();
        archivoMenu.add(salirItem);

        // Menú Editar
        JMenu editarMenu = new JMenu("Editar");
        JMenuItem cortarItem = new JMenuItem("Cortar");
        JMenuItem copiarItem = new JMenuItem("Copiar");
        JMenuItem pegarItem = new JMenuItem("Pegar");

        editarMenu.add(cortarItem);
        editarMenu.add(copiarItem);
        editarMenu.add(pegarItem);

        // Menú Ver
        JMenu verMenu = new JMenu("Ver");
        JMenuItem actualizarItem = new JMenuItem("Actualizar");
        JCheckBoxMenuItem barraHerramientasItem = new JCheckBoxMenuItem("Barra de Herramientas", true);
        JCheckBoxMenuItem panelNavegacionItem = new JCheckBoxMenuItem("Panel de Navegación", true);

        verMenu.add(actualizarItem);
        verMenu.addSeparator();
        verMenu.add(barraHerramientasItem);
        verMenu.add(panelNavegacionItem);

        // Menú Proyecto
        JMenu proyectoMenu = new JMenu("Proyecto");
        JMenuItem propiedadesItem = new JMenuItem("Propiedades");
        JMenuItem tareasItem = new JMenuItem("Tareas");
        JMenuItem recursosItem = new JMenuItem("Recursos");

        proyectoMenu.add(propiedadesItem);
        proyectoMenu.add(tareasItem);
        proyectoMenu.add(recursosItem);

        // Menú Tareas
        JMenu tareasMenu = new JMenu("Tareas");
        JMenuItem asignarItem = new JMenuItem("Asignar");
        JMenuItem estadoItem = new JMenuItem("Cambiar Estado");

        tareasMenu.add(asignarItem);
        tareasMenu.add(estadoItem);

        // Menú Informes
        JMenu informesMenu = new JMenu("Informes");
        JMenuItem resumenItem = new JMenuItem("Resumen de Proyecto");
        JMenuItem tareasPendientesItem = new JMenuItem("Tareas Pendientes");
        JMenuItem progresoItem = new JMenuItem("Informe de Progreso");

        informesMenu.add(resumenItem);
        informesMenu.add(tareasPendientesItem);
        informesMenu.add(progresoItem);

        // Menú Ayuda
        JMenu ayudaMenu = new JMenu("Ayuda");
        JMenuItem contenidoItem = new JMenuItem("Contenido");
        JMenuItem acercaDeItem = new JMenuItem("Acerca de");

        ayudaMenu.add(contenidoItem);
        ayudaMenu.add(acercaDeItem);

        menuBar.add(archivoMenu);
        menuBar.add(editarMenu);
        menuBar.add(verMenu);
        menuBar.add(proyectoMenu);
        menuBar.add(tareasMenu);
        menuBar.add(informesMenu);
        menuBar.add(ayudaMenu);

        setJMenuBar(menuBar);
    }

    private void createToolBar() {
        toolBar = new JToolBar("Barra de Herramientas");
        toolBar.setFloatable(false);

        JButton nuevoProyectoBtn = new JButton("Nuevo Proyecto");
        JButton nuevaTareaBtn = new JButton("Nueva Tarea");
        JButton guardarBtn = new JButton("Guardar");
        JButton actualizarBtn = new JButton("Actualizar");
        JButton eliminarBtn = new JButton("Eliminar");

        nuevoProyectoBtn.setToolTipText("Crear nuevo proyecto");
        nuevaTareaBtn.setToolTipText("Crear nueva tarea");
        guardarBtn.setToolTipText("Guardar cambios");
        actualizarBtn.setToolTipText("Actualizar vista");
        eliminarBtn.setToolTipText("Eliminar selección");

        // Agregar ActionListeners
        nuevoProyectoBtn.addActionListener(e -> crearNuevoProyecto());
        actualizarBtn.addActionListener(e -> actualizarVistaActual());

        toolBar.add(nuevoProyectoBtn);
        toolBar.add(nuevaTareaBtn);
        toolBar.addSeparator();
        toolBar.add(guardarBtn);
        toolBar.add(actualizarBtn);
        toolBar.add(eliminarBtn);
        toolBar.addSeparator();

        toolBar.add(Box.createHorizontalGlue());

        // Cambiar el nombre de usuario
        JLabel usuarioLabel = new JLabel("Usuario: Jairo Herrera");
        toolBar.add(usuarioLabel);
    }

    private void createNavigationPanel() {
        navigationPanel = new JPanel();
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.Y_AXIS));
        navigationPanel.setBorder(BorderFactory.createTitledBorder("Panel Principal"));
        navigationPanel.setPreferredSize(new Dimension(200, 0));

        String[] opciones = {
            "Dashboard", "Proyectos", "Tareas", "Usuarios",
            "Recursos", "Calendario", "Informes", "Configuración"
        };

        ButtonGroup group = new ButtonGroup();

        for (String opcion : opciones) {
            JToggleButton button = new JToggleButton(opcion);
            button.setAlignmentX(Component.LEFT_ALIGNMENT);
            button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
            button.setFocusPainted(false);

            if (opcion.equals("Dashboard")) {
                button.setSelected(true);
            }

            // Solución para el problema de variable efectivamente final
            final String opcionFinal = opcion;
            button.addActionListener(e -> cambiarPestana(opcionFinal));

            group.add(button);
            navigationPanel.add(button);
            navigationPanel.add(Box.createVerticalStrut(2));
        }
    }

    private void createMainTabs() {
        mainTabbedPane = new JTabbedPane();

        // Crear paneles para cada pestaña usando componentes reales conectados a BD
        dashboardPanel = createDashboardPanel();
        proyectosPanel = createProyectosPanel(); // Panel real
        tareasPanel = createTareasPanel();       // Panel real
        usuariosPanel = createUsuariosPanel();   // Panel real
        recursosPanel = createRecursosPanel();   // Panel real

        mainTabbedPane.addTab("Dashboard", dashboardPanel);
        mainTabbedPane.addTab("Proyectos", proyectosPanel);
        mainTabbedPane.addTab("Tareas", tareasPanel);
        mainTabbedPane.addTab("Usuarios", usuariosPanel);
        mainTabbedPane.addTab("Recursos", recursosPanel);
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Obtener datos reales para el resumen
        service.ProyectoServicio proyectoServicio = new service.ProyectoServicio();
        service.TareaServicio tareaServicio = new service.TareaServicio();
        
        int totalProyectos = 0;
        int proyectosActivos = 0;
        int tareasPendientes = 0;
        int tareasCompletadas = 0;
        
        try {
            totalProyectos = proyectoServicio.obtenerTodosLosProyectos().size();
            // Contar proyectos activos (no completados)
            proyectosActivos = (int) proyectoServicio.obtenerTodosLosProyectos().stream()
                                 .filter(p -> !"Completado".equals(p.getEstado()) && !"Hecho".equals(p.getEstado()))
                                 .count();
                                 
            tareasPendientes = (int) tareaServicio.obtenerTodasLasTareas().stream()
                                 .filter(t -> !"Hecho".equals(t.getEstado()))
                                 .count();
            tareasCompletadas = (int) tareaServicio.obtenerTodasLasTareas().stream()
                                 .filter(t -> "Hecho".equals(t.getEstado()))
                                 .count();
        } catch (Exception e) {
            System.err.println("Error calculando resumen del dashboard: " + e.getMessage());
            // Valores por defecto en caso de error
        }

        JPanel summaryPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Usar datos reales
        summaryPanel.add(createSummaryCard(String.valueOf(totalProyectos), "Proyectos Totales"));
        summaryPanel.add(createSummaryCard(String.valueOf(proyectosActivos), "Proyectos Activos"));
        summaryPanel.add(createSummaryCard(String.valueOf(tareasPendientes), "Tareas Pendientes"));
        summaryPanel.add(createSummaryCard(String.valueOf(tareasCompletadas), "Tareas Completadas"));

        panel.add(summaryPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 0, 10));

        // Para tablas reales de proyectos recientes y tareas pendientes,
        // necesitarías crear componentes personalizados similares a TablaProyectos
        // Por ahora, dejamos las tablas estáticas o puedes crear componentes específicos
        
        JPanel proyectosRecientesPanel = new JPanel(new BorderLayout());
        proyectosRecientesPanel.setBorder(BorderFactory.createTitledBorder("Proyectos Recientes"));
        proyectosRecientesPanel.add(new JScrollPane(createProyectosTable()), BorderLayout.CENTER);
        centerPanel.add(proyectosRecientesPanel);

        JPanel tareasPendientesPanel = new JPanel(new BorderLayout());
        tareasPendientesPanel.setBorder(BorderFactory.createTitledBorder("Tareas Pendientes"));
        tareasPendientesPanel.add(new JScrollPane(createTareasTable()), BorderLayout.CENTER);
        centerPanel.add(tareasPendientesPanel);

        panel.add(centerPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createSummaryCard(String number, String label) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setBackground(Color.WHITE);

        JLabel numberLabel = new JLabel(number, SwingConstants.CENTER);
        numberLabel.setFont(new Font("Arial", Font.BOLD, 24));
        numberLabel.setForeground(new Color(0, 122, 204));

        JLabel textLabel = new JLabel(label, SwingConstants.CENTER);
        textLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        textLabel.setForeground(Color.GRAY);

        card.add(numberLabel, BorderLayout.CENTER);
        card.add(textLabel, BorderLayout.SOUTH);

        return card;
    }

    // Estas tablas aún son estáticas, pero puedes reemplazarlas con componentes reales
    // que carguen datos desde la BD si lo deseas para el dashboard también.
    private JTable createProyectosTable() {
        // En una implementación completa, esta tabla también se conectaría a la BD
        String[] columnNames = {"Nombre", "Estado", "Fecha Inicio", "Fecha Fin", "Progreso"};
        Object[][] data = {
            {"Sistema de Inventario", "En Progreso", "01/06/2024", "31/08/2024", "65%"},
            {"Campaña Marketing", "Completado", "01/07/2024", "30/09/2024", "100%"},
            {"Rediseño Web", "No Iniciado", "15/10/2024", "30/12/2024", "0%"},
            {"Migración Base Datos", "En Espera", "01/09/2024", "15/11/2024", "30%"}
        };

        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);
        return table;
    }

    private JTable createTareasTable() {
        // En una implementación completa, esta tabla también se conectaría a la BD
        String[] columnNames = {"Título", "Proyecto", "Asignado a", "Prioridad", "Fecha Vencimiento"};
        Object[][] data = {
            {"Diseño de Base de Datos", "Sistema de Inventario", "Jairo Herrera", "Alta", "15/06/2024"},
            {"Crear Banners", "Campaña Marketing", "Ana Gómez", "Media", "10/07/2024"},
            {"Implementar API REST", "Sistema de Inventario", "Jairo Herrera", "Alta", "15/07/2024"}
        };

        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);
        return table;
    }

    // --- Métodos para crear paneles reales conectados a la BD ---
    private JPanel createProyectosPanel() {
        return new TablaProyectos(); // Panel real con tabla conectada a BD
    }

    private JPanel createTareasPanel() {
        return new TablaTareas(); // Panel real con tabla conectada a BD
    }

    private JPanel createUsuariosPanel() {
        return new TablaUsuarios(); // Panel real con tabla conectada a BD
    }

    private JPanel createRecursosPanel() {
        return new TablaRecursos(); // Panel real con tabla conectada a BD
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        if (toolBar != null) {
            add(toolBar, BorderLayout.NORTH);
        }

        JPanel mainPanel = new JPanel(new BorderLayout());

        if (navigationPanel != null) {
            mainPanel.add(navigationPanel, BorderLayout.WEST);
        }

        if (mainTabbedPane != null) {
            mainPanel.add(mainTabbedPane, BorderLayout.CENTER);
        }

        add(mainPanel, BorderLayout.CENTER);
    }

    private void setupEventHandlers() {
        // Manejadores de eventos para menús y botones
    }

    // --- Métodos para manejar eventos ---
    private void crearNuevoProyecto() {
        try {
            ui.componentes.FormularioProyecto dialog = new ui.componentes.FormularioProyecto(SwingUtilities.getWindowAncestor(this), null);
            dialog.setVisible(true);
            
            if (dialog.isAceptado()) {
                // Refrescar la tabla de proyectos si es la pestaña activa
                if (mainTabbedPane.getSelectedIndex() == 1) { // Índice de la pestaña Proyectos
                    if (proyectosPanel instanceof ui.componentes.TablaProyectos) {
                        ((ui.componentes.TablaProyectos) proyectosPanel).cargarDatos();
                    }
                }
            }
        } catch (Exception e) {
             JOptionPane.showMessageDialog(this, "Error al abrir el formulario de proyecto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
             e.printStackTrace();
        }
    }

    private void actualizarVistaActual() {
        int selectedIndex = mainTabbedPane.getSelectedIndex();
        try {
            switch (selectedIndex) {
                case 0: // Dashboard
                    // Opcional: Recalcular y actualizar datos del dashboard
                    // dashboardPanel = createDashboardPanel(); // Re-crear el panel
                    // mainTabbedPane.setComponentAt(0, dashboardPanel);
                    JOptionPane.showMessageDialog(this, "Datos del dashboard actualizados (simulado).", "Actualizar", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 1: // Proyectos
                    if (proyectosPanel instanceof ui.componentes.TablaProyectos) {
                        ((ui.componentes.TablaProyectos) proyectosPanel).cargarDatos();
                        JOptionPane.showMessageDialog(this, "Lista de proyectos actualizada.", "Actualizar", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                case 2: // Tareas
                    if (tareasPanel instanceof ui.componentes.TablaTareas) {
                        ((ui.componentes.TablaTareas) tareasPanel).cargarDatos();
                        JOptionPane.showMessageDialog(this, "Lista de tareas actualizada.", "Actualizar", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                case 3: // Usuarios
                    if (usuariosPanel instanceof ui.componentes.TablaUsuarios) {
                        ((ui.componentes.TablaUsuarios) usuariosPanel).cargarDatos();
                        JOptionPane.showMessageDialog(this, "Lista de usuarios actualizada.", "Actualizar", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                case 4: // Recursos
                    if (recursosPanel instanceof ui.componentes.TablaRecursos) {
                        ((ui.componentes.TablaRecursos) recursosPanel).cargarDatos();
                        JOptionPane.showMessageDialog(this, "Lista de recursos actualizada.", "Actualizar", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Actualización para esta vista no implementada.", "Actualizar", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
             JOptionPane.showMessageDialog(this, "Error al actualizar la vista: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
             e.printStackTrace();
        }
    }

    private void cambiarPestana(String opcion) {
        switch (opcion) {
            case "Dashboard":
                mainTabbedPane.setSelectedIndex(0);
                break;
            case "Proyectos":
                mainTabbedPane.setSelectedIndex(1);
                break;
            case "Tareas":
                mainTabbedPane.setSelectedIndex(2);
                break;
            case "Usuarios":
                mainTabbedPane.setSelectedIndex(3);
                break;
            case "Recursos":
                mainTabbedPane.setSelectedIndex(4);
                break;
            case "Calendario":
            case "Informes":
            case "Configuración":
                JOptionPane.showMessageDialog(this, "Funcionalidad de '" + opcion + "' aún no implementada.", "Información", JOptionPane.INFORMATION_MESSAGE);
                break;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new MainFrame().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}