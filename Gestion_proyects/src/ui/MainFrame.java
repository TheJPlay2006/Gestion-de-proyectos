package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ui.componentes.TablaProyectos; // Importamos la nueva clase

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
        initComponents();
        setupLayout();
        setupEventHandlers();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Gestor de Proyectos - Sistema de Gestión de Proyectos");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
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
        
        JLabel usuarioLabel = new JLabel("Usuario: Juan Pérez");
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
            
            // Agregar ActionListener para cambiar de pestaña
            button.addActionListener(e -> cambiarPestana(opcion));
            
            group.add(button);
            navigationPanel.add(button);
            navigationPanel.add(Box.createVerticalStrut(2));
        }
    }
    
    private void createMainTabs() {
        mainTabbedPane = new JTabbedPane();
        
        // Crear paneles para cada pestaña
        dashboardPanel = createDashboardPanel();
        proyectosPanel = createProyectosPanel(); // Usamos el panel real
        tareasPanel = createTareasPanel();
        usuariosPanel = createUsuariosPanel();
        recursosPanel = createRecursosPanel();
        
        mainTabbedPane.addTab("Dashboard", dashboardPanel);
        mainTabbedPane.addTab("Proyectos", proyectosPanel);
        mainTabbedPane.addTab("Tareas", tareasPanel);
        mainTabbedPane.addTab("Usuarios", usuariosPanel);
        mainTabbedPane.addTab("Recursos", recursosPanel);
    }
    
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel summaryPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        summaryPanel.add(createSummaryCard("12", "Proyectos Totales"));
        summaryPanel.add(createSummaryCard("8", "Proyectos Activos"));
        summaryPanel.add(createSummaryCard("45", "Tareas Pendientes"));
        summaryPanel.add(createSummaryCard("23", "Tareas Completadas"));
        
        panel.add(summaryPanel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        
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
    
    private JTable createProyectosTable() {
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
        String[] columnNames = {"Título", "Proyecto", "Asignado a", "Prioridad", "Fecha Vencimiento"};
        Object[][] data = {
            {"Diseño de Base de Datos", "Sistema de Inventario", "Juan Pérez", "Alta", "15/06/2024"},
            {"Crear Banners", "Campaña Marketing", "Ana Gómez", "Media", "10/07/2024"},
            {"Implementar API REST", "Sistema de Inventario", "Juan Pérez", "Alta", "15/07/2024"}
        };
        
        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);
        return table;
    }
    
    private JPanel createProyectosPanel() {
        // Usamos el panel real con tabla conectada a BD
        return new TablaProyectos();
    }
    
    private JPanel createTareasPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Gestión de Tareas");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        JTextArea placeholder = new JTextArea("Panel de gestión de tareas\n\n" +
            "Aquí se mostrarán:\n" +
            "- Lista de tareas\n" +
            "- Formulario para crear/editar tareas\n" +
            "- Asignación de tareas a usuarios\n" +
            "- Seguimiento de progreso\n" +
            "- Opciones de filtrado por proyecto/usuario/estado");
        placeholder.setEditable(false);
        placeholder.setBackground(panel.getBackground());
        panel.add(new JScrollPane(placeholder), BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createUsuariosPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Gestión de Usuarios");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        JTextArea placeholder = new JTextArea("Panel de gestión de usuarios\n\n" +
            "Aquí se mostrarán:\n" +
            "- Lista de usuarios\n" +
            "- Formulario para crear/editar usuarios\n" +
            "- Asignación de roles\n" +
            "- Información de contacto");
        placeholder.setEditable(false);
        placeholder.setBackground(panel.getBackground());
        panel.add(new JScrollPane(placeholder), BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createRecursosPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Gestión de Recursos");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        JTextArea placeholder = new JTextArea("Panel de gestión de recursos\n\n" +
            "Aquí se mostrarán:\n" +
            "- Lista de recursos adjuntos\n" +
            "- Formulario para subir recursos\n" +
            "- Tipos de recursos: documentos, enlaces, imágenes\n" +
            "- Asociación con tareas/proyectos");
        placeholder.setEditable(false);
        placeholder.setBackground(panel.getBackground());
        panel.add(new JScrollPane(placeholder), BorderLayout.CENTER);
        
        return panel;
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
    
    // Métodos para manejar eventos
    private void crearNuevoProyecto() {
        ui.componentes.FormularioProyecto dialog = new ui.componentes.FormularioProyecto(this, null);
        dialog.setVisible(true);
        
        if (dialog.isAceptado()) {
            // Actualizar la vista de proyectos si estamos en esa pestaña
            if (mainTabbedPane.getSelectedIndex() == 1) { // Pestaña de Proyectos
                ((TablaProyectos) proyectosPanel).cargarDatos();
            }
        }
    }
    
    private void actualizarVistaActual() {
        int selectedIndex = mainTabbedPane.getSelectedIndex();
        switch (selectedIndex) {
            case 1: // Proyectos
                if (proyectosPanel instanceof TablaProyectos) {
                    ((TablaProyectos) proyectosPanel).cargarDatos();
                }
                break;
            // Agregar casos para otras pestañas cuando las implementemos
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
        }
    }
    
    public static void main(String[] args) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}