package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Clase para gestionar la conexión a la base de datos SQL Server
 * utilizando Autenticación Integrada de Windows.
 */
public class ConexionBD {
    // Cadena de conexión usando Autenticación Integrada de Windows
    // Asegúrate de que el nombre del servidor y la base de datos sean correctos
    private static final String URL = "jdbc:sqlserver://JPLAYLAPTOP\\SQLEXPRESS;"
            + "databaseName=GestorProyectosDB;"
            + "integratedSecurity=true;"
            + "encrypt=true;"
            + "trustServerCertificate=true;";

    private static Connection conexion = null;

    /**
     * Obtiene una conexión a la base de datos.
     * Implementa un patrón Singleton para reutilizar la conexión.
     * @return Un objeto Connection, o null si falla la conexión.
     */
    public static Connection getConexion() {
        System.out.println("🔄 ConexionBD.getConexion() llamado");
        try {
            // Verificar si la conexión ya existe y sigue abierta
            if (conexion == null || conexion.isClosed()) {
                System.out.println("🔌 Creando nueva conexión a la base de datos...");
                
                // Cargar explícitamente el driver JDBC (buena práctica)
                System.out.println("🚗 Cargando driver JDBC de SQL Server...");
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                System.out.println("✅ Driver JDBC cargado correctamente.");
                
                // Intentar establecer la conexión
                System.out.println("🔗 Intentando conectar a: " + URL);
                conexion = DriverManager.getConnection(URL);
                System.out.println("🎉 ¡Conexión exitosa a GestorProyectosDB en JPLAYLAPTOP\\SQLEXPRESS!");
            } else {
                System.out.println("🔁 Reutilizando conexión existente.");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Error crítico: No se pudo encontrar el driver JDBC de SQL Server.");
            System.err.println("   Asegúrate de que el archivo mssql-jdbc-*.jar esté en tu classpath.");
            System.err.println("   Detalles: " + e.getMessage());
            e.printStackTrace();
            registrarError("Driver JDBC no encontrado", "getConexion", e.toString());
        } catch (SQLException e) {
            System.err.println("❌ Error de SQL al intentar conectar a la base de datos:");
            System.err.println("   Mensaje: " + e.getMessage());
            System.err.println("   SQL State: " + e.getSQLState());
            System.err.println("   Error Code: " + e.getErrorCode());
            System.err.println("   Posible causa: Problemas con sqljdbc_auth.dll o credenciales de Windows.");
            System.err.println("   Verifica que:");
            System.err.println("   1. El parámetro JVM '--enable-native-access=ALL-UNNAMED' esté configurado.");
            System.err.println("   2. El archivo 'sqljdbc_auth.dll' esté en la ruta correcta o en System32.");
            System.err.println("   3. Tu usuario de Windows tenga acceso a JPLAYLAPTOP\\SQLEXPRESS.");
            System.err.println("   4. La base de datos 'GestorProyectosDB' exista.");
            e.printStackTrace();
            registrarError("Error de SQL al conectar", "getConexion", e.toString());
        } catch (Exception e) {
            System.err.println("❌ Error inesperado al obtener la conexión:");
            System.err.println("   Mensaje: " + e.getMessage());
            e.printStackTrace();
            registrarError("Error inesperado en getConexion", "getConexion", e.toString());
        }
        return conexion;
    }

    /**
     * Cierra la conexión a la base de datos si está abierta.
     */
    public static void cerrarConexion() {
        System.out.println("🚪 Intentando cerrar la conexión a la base de datos...");
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("✅ Conexión cerrada exitosamente.");
            } else {
                System.out.println("ℹ️  No había conexión activa que cerrar.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al intentar cerrar la conexión: " + e.getMessage());
            registrarError("Error al cerrar la conexión", "cerrarConexion", e.toString());
        }
    }

    /**
     * Registra un error en la consola (y en una tabla de la BD en el futuro).
     * @param mensajeError El mensaje principal del error.
     * @param nombreMetodo El nombre del método donde ocurrió el error.
     * @param detallesError Detalles adicionales del error.
     */
    public static void registrarError(String mensajeError, String nombreMetodo, String detallesError) {
        System.err.println("⚠️  [LOG DE ERROR] ----------------------------------------");
        System.err.println("   Mensaje: " + mensajeError);
        System.err.println("   Método: " + nombreMetodo);
        System.err.println("   Detalles: " + detallesError);
        System.err.println("----------------------------------------------------------");
        // En una implementación completa, aquí insertarías el error en la tabla RegistroErrores
    }

    // --- Métodos de utilidad para conversiones de fecha/hora ---

    /**
     * Convierte un LocalDateTime de Java a un Timestamp de SQL.
     * @param localDateTime El objeto LocalDateTime.
     * @return El objeto Timestamp correspondiente, o null si la entrada es null.
     */
    public static Timestamp localDateTimeToTimestamp(LocalDateTime localDateTime) {
        return localDateTime != null ? Timestamp.valueOf(localDateTime) : null;
    }

    /**
     * Convierte un Timestamp de SQL a un LocalDateTime de Java.
     * @param timestamp El objeto Timestamp.
     * @return El objeto LocalDateTime correspondiente, o null si la entrada es null.
     */
    public static LocalDateTime timestampToLocalDateTime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }
    
    // --- Método main para pruebas ---
    
    /**
     * Método de prueba para verificar la conexión de forma aislada.
     * @param args Argumentos de línea de comandos (no se usan).
     */
    public static void main(String[] args) {
        System.out.println("🧪 === PRUEBA DE CONEXIÓN A LA BASE DE DATOS ===");
        Connection conn = null;
        try {
            conn = getConexion();
            if (conn != null && !conn.isClosed()) {
                System.out.println("🎉 ¡Prueba de conexión EXITOSA!");
                System.out.println("   URL de conexión: " + URL);
                System.out.println("   Catálogo (Base de datos): " + conn.getCatalog());
                System.out.println("   Esquema por defecto: " + conn.getSchema());
            } else {
                System.out.println("💥 Prueba de conexión FALLIDA.");
            }
        } catch (Exception e) {
            System.err.println("💥 Excepción durante la prueba de conexión:");
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("✅ Conexión de prueba cerrada.");
                } catch (SQLException e) {
                    System.err.println("❌ Error al cerrar la conexión de prueba: " + e.getMessage());
                }
            }
            System.out.println("🏁 === FIN DE LA PRUEBA ===");
        }
    }
}