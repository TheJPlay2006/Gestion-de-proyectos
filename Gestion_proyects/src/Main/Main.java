package Main;

import ui.MainFrame;
import util.ConexionBD;
import java.sql.Connection;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Clase principal para ejecutar la aplicación de Gestión de Proyectos
 * @author jh599
 */
public class Main {
    
    public static void main(String[] args) {
        // Verificar conexión a la base de datos
        if (verificarConexionBaseDatos()) {
            try {
            } catch (Exception e) {
            }
            
            SwingUtilities.invokeLater(() -> {
                try {
                    new MainFrame().setVisible(true);
                } catch (Exception e) {
                    System.err.println("Error al iniciar la aplicación: " + e.getMessage());
                    e.printStackTrace();
                }
            });
        } else {
            System.err.println("No se pudo conectar a la base de datos. La aplicación no se iniciará.");
            // Mostrar mensaje de error al usuario
            javax.swing.JOptionPane.showMessageDialog(null, 
                "No se pudo conectar a la base de datos.\nVerifique la configuración de conexión.", 
                "Error de Conexión", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private static boolean verificarConexionBaseDatos() {
        try {
            Connection conn = ConexionBD.getConexion();
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Conexión a la base de datos verificada correctamente");
                return true;
            } else {
                System.err.println("❌ No se pudo establecer conexión con la base de datos");
                return false;
            }
        } catch (Exception e) {
            System.err.println("❌ Error al conectar con la base de datos: " + e.getMessage());
            return false;
        }
    }
}