package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ConexionBD {
    private static final String URL = "jdbc:sqlserver://localhost\\SQLEXPRESS;"
            + "databaseName=GestorProyectosDB;"
            + "integratedSecurity=true;"
            + "encrypt=true;"
            + "trustServerCertificate=true;";
    
    private static Connection conexion = null;
    
    public static Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                conexion = DriverManager.getConnection(URL);
                System.out.println("✅ Conexión exitosa a GestorProyectosDB");
            }
        } catch (ClassNotFoundException e) {
            registrarError("Driver JDBC no encontrado", "getConexion", e.toString());
        } catch (SQLException e) {
            registrarError("Error al conectar con la base de datos", "getConexion", e.toString());
        }
        return conexion;
    }
    
    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("🔌 Conexión cerrada");
            }
        } catch (SQLException e) {
            registrarError("Error al cerrar la conexión", "cerrarConexion", e.toString());
        }
    }
    
    public static void registrarError(String mensajeError, String nombreMetodo, String detallesError) {
        String sql = "INSERT INTO RegistroErrores (mensaje_error, nombre_metodo, detalles_error) VALUES (?, ?, ?)";
        try (Connection conn = getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, mensajeError);
            stmt.setString(2, nombreMetodo);
            stmt.setString(3, detallesError);
            stmt.executeUpdate();
            System.err.println("⚠️ Error registrado en BD: " + mensajeError);
        } catch (SQLException e) {
            System.err.println("❌ Error al registrar error en BD: " + e.getMessage());
        }
    }
    
    // Método para convertir LocalDateTime a Timestamp
    public static Timestamp localDateTimeToTimestamp(LocalDateTime localDateTime) {
        return localDateTime != null ? Timestamp.valueOf(localDateTime) : null;
    }
    
    // Método para convertir Timestamp a LocalDateTime
    public static LocalDateTime timestampToLocalDateTime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }
}