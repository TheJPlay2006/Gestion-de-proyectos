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
        System.out.println("🚀 Iniciando aplicación Gestor de Proyectos...");
        
        // Verificar conexión a la base de datos
        System.out.println("🔌 Verificando conexión a la base de datos...");
        if (verificarConexionBaseDatos()) {
            System.out.println("✅ Conexión a base de datos OK");
            
            try {
            } catch (Exception e) {
            }
            
            System.out.println("📱 Creando y mostrando interfaz gráfica...");
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    try {
                        System.out.println("✨ Mostrando ventana principal...");
                        MainFrame frame = new MainFrame();
                        frame.setVisible(true);
                        System.out.println("✅ Ventana principal mostrada correctamente");
                    } catch (Exception e) {
                        System.err.println("❌ Error al iniciar la interfaz gráfica: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            });
        } else {
            System.err.println("❌ No se pudo conectar a la base de datos. La aplicación no se iniciará.");
            // Mostrar mensaje de error al usuario
            SwingUtilities.invokeLater(() -> {
                javax.swing.JOptionPane.showMessageDialog(null, 
                    "No se pudo conectar a la base de datos.\nVerifique la configuración de conexión.", 
                    "Error de Conexión", 
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            });
        }
    }
    
    private static boolean verificarConexionBaseDatos() {
        try {
            System.out.println("🔄 Intentando conectar a la base de datos...");
            Connection conn = ConexionBD.getConexion();
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Conexión a la base de datos verificada correctamente");
                // Probar una consulta simple
                try {
                    java.sql.Statement stmt = conn.createStatement();
                    java.sql.ResultSet rs = stmt.executeQuery("SELECT 1");
                    if (rs.next()) {
                        System.out.println("🔍 Conexión verificada con consulta de prueba");
                    }
                    rs.close();
                    stmt.close();
                } catch (Exception e) {
                    System.err.println("⚠️  Error en consulta de prueba: " + e.getMessage());
                }
                return true;
            } else {
                System.err.println("❌ No se pudo establecer conexión con la base de datos");
                return false;
            }
        } catch (Exception e) {
            System.err.println("❌ Error al conectar con la base de datos: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}