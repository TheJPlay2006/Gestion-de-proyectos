/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.componentes;

import javax.swing.*;
import java.awt.*;
import model.Usuario;

public class FormularioUsuario extends JDialog {
    private JTextField txtNombreUsuario;
    private JTextField txtNombreCompleto;
    private JTextField txtCorreoElectronico;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private Usuario usuario;
    private boolean aceptado = false;
    private service.UsuarioServicio usuarioServicio;
    
    public FormularioUsuario(Window parent, Usuario usuario) {
        super(parent, usuario == null ? "Nuevo Usuario" : "Editar Usuario", 
              ModalityType.APPLICATION_MODAL);
        this.usuario = usuario;
        this.usuarioServicio = new service.UsuarioServicio();
        initComponents();
        cargarDatos();
        setupLayout();
        setupEventHandlers();
        setSize(400, 250);
        setLocationRelativeTo(parent);
    }
    
    private void initComponents() {
        txtNombreUsuario = new JTextField(20);
        txtNombreCompleto = new JTextField(20);
        txtCorreoElectronico = new JTextField(20);
        
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel panelForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Nombre de Usuario
        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(new JLabel("Nombre de Usuario:"), gbc);
        gbc.gridx = 1;
        panelForm.add(txtNombreUsuario, gbc);
        
        // Nombre Completo
        gbc.gridx = 0; gbc.gridy = 1;
        panelForm.add(new JLabel("Nombre Completo:"), gbc);
        gbc.gridx = 1;
        panelForm.add(txtNombreCompleto, gbc);
        
        // Correo Electrónico
        gbc.gridx = 0; gbc.gridy = 2;
        panelForm.add(new JLabel("Correo Electrónico:"), gbc);
        gbc.gridx = 1;
        panelForm.add(txtCorreoElectronico, gbc);
        
        add(panelForm, BorderLayout.CENTER);
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        btnGuardar.addActionListener(e -> guardarUsuario());
        btnCancelar.addActionListener(e -> dispose());
        
        getRootPane().registerKeyboardAction(e -> dispose(),
            KeyStroke.getKeyStroke("ESCAPE"), JComponent.WHEN_IN_FOCUSED_WINDOW);
    }
    
    private void cargarDatos() {
        if (usuario != null) {
            txtNombreUsuario.setText(usuario.getNombreUsuario());
            txtNombreCompleto.setText(usuario.getNombreCompleto());
            txtCorreoElectronico.setText(usuario.getCorreoElectronico());
        }
    }
    
    private void guardarUsuario() {
        try {
            // Validar datos
            String nombreUsuario = txtNombreUsuario.getText().trim();
            if (nombreUsuario.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre de usuario es obligatorio", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
                txtNombreUsuario.requestFocus();
                return;
            }
            
            String nombreCompleto = txtNombreCompleto.getText().trim();
            if (nombreCompleto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre completo es obligatorio", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
                txtNombreCompleto.requestFocus();
                return;
            }
            
            // Crear o actualizar usuario
            if (usuario == null) {
                usuario = new Usuario();
            }
            
            usuario.setNombreUsuario(nombreUsuario);
            usuario.setNombreCompleto(nombreCompleto);
            usuario.setCorreoElectronico(txtCorreoElectronico.getText().trim());
            
            boolean exito;
            if (usuario.getId() == 0) {
                exito = usuarioServicio.crearUsuario(usuario);
            } else {
                exito = usuarioServicio.actualizarUsuario(usuario);
            }
            
            if (exito) {
                aceptado = true;
                JOptionPane.showMessageDialog(this, "Usuario guardado correctamente", 
                                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el usuario", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar el usuario: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean isAceptado() {
        return aceptado;
    }
}