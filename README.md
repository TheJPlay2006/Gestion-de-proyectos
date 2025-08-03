# Gestor de Proyectos en Java

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)
[![Swing](https://img.shields.io/badge/Swing-GUI-blue?style=for-the-badge)](https://docs.oracle.com/javase/tutorial/uiswing/)
[![SQL Server](https://img.shields.io/badge/SQL%20Server-CC2927?style=for-the-badge&logo=microsoft%20sql%20server&logoColor=white)](https://www.microsoft.com/sql-server)
[![Apache Ant](https://img.shields.io/badge/Apache%20Ant-A81C7D?style=for-the-badge&logo=apache%20ant&logoColor=white)](https://ant.apache.org/)
[![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/TheJPlay2006/Gestion-de-proyectos)

## 📋 Descripción

Aplicación de escritorio desarrollada en **Java Swing** para la gestión integral de proyectos. Permite organizar proyectos, asignar y hacer seguimiento de tareas, gestionar usuarios y recursos asociados, conectándose a una base de datos **Microsoft SQL Server** mediante **JDBC**.

Este proyecto fue creado como práctica para la asignatura **Programación I** de la carrera **Técnico Universitario en Informática** (UTN ITI-221).

<img width="1480" height="991" alt="image" src="https://github.com/user-attachments/assets/e1977cdd-bd08-4981-917b-d3c47b91a603" />

<!---
<div align="center">
  <img src="screenshots/principal.png" alt="Captura de pantalla principal" width="800"/>
  <p><em>Vista principal de la aplicación</em></p>
</div>
-->

## 🎯 Características

* **Gestión de Proyectos:** Alta, baja, modificación y consulta de proyectos (CRUD).
* **Gestión de Tareas:** Creación, edición, eliminación y asignación de tareas a usuarios.
* **Gestión de Usuarios:** Administración de los miembros del equipo.
* **Gestión de Recursos:** Adjuntar documentos, enlaces y otros recursos a tareas.
* **Seguimiento de Progreso:** Visualización del estado de tareas y proyectos.
* **Interfaz Gráfica Intuitiva:** Basada en Swing con menús, barras de herramientas y pestañas.
* **Persistencia de Datos:** Conexión y almacenamiento en SQL Server.
* **Arquitectura MVC:** Código organizado siguiendo el patrón Modelo-Vista-Controlador.

## 🛠️ Tecnologías Utilizadas

* **Lenguaje:** Java 11+ (Compatible con JDK 23+)
* **IDE:** NetBeans 24+ (Compatible con Apache NetBeans)
* **Interfaz Gráfica:** Java Swing
* **Base de Datos:** Microsoft SQL Server 2022 Express (Compatible con versiones superiores)
* **Conectividad BD:** JDBC (Microsoft JDBC Driver 12.10+)
* **Sistema de Construcción:** Apache Ant
* **Control de Versiones:** Git
* **Repositorio:** GitHub

## 📦 Estructura del Proyecto

El proyecto sigue una arquitectura MVC clara con sistema de construcción Ant:

```
Gestion-de-proyectos/
├── src/
│   └── Main/                    # Punto de entrada principal
│       ├── dao/                 # Acceso a datos (UsuarioDAO, ProyectoDAO, etc.)
│       ├── database/            # Utilidades de base de datos (ConexionBD)
│       ├── model/               # POJOs (Usuario, Proyecto, Tarea, Recurso)
│       ├── service/             # Lógica de negocio (UsuarioServicio, ProyectoServicio, etc.)
│       └── ui/                  # Interfaz de usuario (MainFrame, componentes)
│           └── componentes/     # Componentes específicos (TablaProyectos, FormularioProyecto, etc.)
├── lib/                         # Librerías JAR externas (JDBC Driver)
├── build/                       # Archivos compilados (.class)
├── dist/                        # JAR ejecutable generado
├── nbproject/                   # Configuración de NetBeans
├── build.xml                    # Script de construcción Ant
├── README.md                    # Este archivo
└── ...                          # Otros archivos del proyecto
```

## ▶️ Instrucciones de Ejecución

### Prerrequisitos

1. **JDK 11 o superior** instalado y configurado en tu `PATH` y `JAVA_HOME`.
2. **Apache Ant** instalado y configurado en tu `PATH`.
3. **NetBeans 24+** (u otro IDE compatible con Ant) *(Opcional, para desarrollo)*.
4. **Microsoft SQL Server 2022 Express** (o superior) instalado y en ejecución.
5. **SQL Server Management Studio (SSMS)** *(Opcional, para administrar la base de datos)*.
6. **Git** instalado (para clonar el repositorio).

### 1. Clonar el Repositorio

```bash
git clone https://github.com/TheJPlay2006/Gestion-de-proyectos.git
cd Gestion-de-proyectos
```

### 2. Configuración de la Base de Datos

**Crear la Base de Datos:**

1. Abre SQL Server Management Studio (SSMS).
2. Conéctate a tu instancia local (por ejemplo, `JPLAYLAPTOP\SQLEXPRESS`).
3. Crea una nueva consulta y ejecuta el script SQL que contiene la estructura de las tablas.

Ejemplo básico de creación de BD:

```sql
USE [master]
GO
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'GestorProyectosDB')
BEGIN
    CREATE DATABASE [GestorProyectosDB]
END
GO
USE [GestorProyectosDB]
GO

-- Aquí irían las sentencias CREATE TABLE para:
-- Usuarios, Proyectos, Tareas, Recursos, RegistroErrores
```

Asegúrate de que las tablas (`Usuarios`, `Proyectos`, `Tareas`, `Recursos`, `RegistroErrores`) se creen correctamente.

**Configurar Autenticación:**

* **Autenticación Integrada de Windows** (recomendado para desarrollo):
  * Asegúrate de que tu usuario de Windows tenga acceso a la instancia `JPLAYLAPTOP\SQLEXPRESS` y a la base de datos `GestorProyectosDB`.
  * El archivo `mssql-jdbc_auth-<version>.jar` o `sqljdbc_auth.dll` debe estar disponible.
  * **Configurar VM Options:** Agrega `--enable-native-access=ALL-UNNAMED` para evitar advertencias de seguridad.

* **Autenticación de SQL Server** (alternativa):
  * Habilita la autenticación mixta en SQL Server.
  * Crea un usuario de SQL Server (ejemplo: `gestor_user`).
  * Modifica la cadena de conexión en `database.ConexionBD` para usar `user=gestor_user;password=tu_contraseña;` en lugar de `integratedSecurity=true;`.

### 3. Compilar y Ejecutar con Apache Ant

**Opción A: Desde línea de comandos**

```bash
# Limpiar proyecto
ant clean

# Compilar el proyecto
ant compile

# Crear JAR ejecutable
ant jar

# Ejecutar la aplicación
ant run
```

**Opción B: Ejecutar JAR directamente**

```bash
# Después de compilar con 'ant jar'
java --enable-native-access=ALL-UNNAMED -jar dist/Gestion-de-proyectos.jar
```

### 4. Ejecutar desde NetBeans (Recomendado para desarrollo)

1. Abre NetBeans.
2. Ve a **File -> Open Project...** y selecciona la carpeta `Gestion-de-proyectos`.
3. Una vez cargado, haz clic derecho en el proyecto y selecciona **Properties**.
4. Ve a la categoría **Run**.
5. En el campo **Main Class**, asegúrate de que esté seleccionada `Main.Main`.
6. En el campo **VM Options**, si estás usando autenticación integrada, escribe:
   ```
   --enable-native-access=ALL-UNNAMED
   ```
7. Haz clic en **OK**.
8. Haz clic derecho en el proyecto y selecciona **Run** o presiona **F6**.

## 📁 Entregables

Este repositorio contiene:

* ✅ Código fuente completo del proyecto estructurado en paquetes.
* ✅ Archivo `build.xml` de configuración Apache Ant.
* ✅ Clase principal `Main.Main`.
* ✅ Estructura de paquetes `dao`, `database`, `model`, `service`, `ui`.
* ✅ Este archivo `README.md`.
* ✅ (Próximamente) Archivo JAR ejecutable en `dist/` tras construir con Ant.
* ✅ (Próximamente) Scripts SQL (`schema.sql`).
* ✅ (Próximamente) Backup de la base de datos.
* ✅ (Próximamente) Modelo UML de las entidades.
* ✅ (Próximamente) Video de demostración.

## 🔧 Comandos Ant Disponibles

```bash
ant clean          # Limpia los archivos compilados
ant compile        # Compila el código fuente
ant jar            # Crea el archivo JAR ejecutable
ant run            # Ejecuta la aplicación
ant javadoc        # Genera documentación JavaDoc (si está configurado)
ant test           # Ejecuta pruebas unitarias (si están configuradas)
```

## 👨‍💻 Autor

* **Jairo Herrera** ([TheJPlay2006](https://github.com/TheJPlay2006))

## 📚 Recursos y Referencias

* [Documentación Oficial de Java - Swing](https://docs.oracle.com/javase/tutorial/uiswing/)
* [Documentación de Microsoft SQL Server](https://docs.microsoft.com/sql/sql-server/)
* [Documentación de Apache Ant](https://ant.apache.org/manual/)
* [Guía de NetBeans](https://netbeans.apache.org/tutorial/)
* [Microsoft JDBC Driver para SQL Server](https://docs.microsoft.com/sql/connect/jdbc/)

## 📝 Licencia

Este proyecto fue creado como trabajo práctico para un curso académico. No se especifica una licencia de software libre abierta. Todos los derechos pertenecen al autor, Jairo Herrera, salvo que se indique lo contrario.

---

**💡 Nota:** Este proyecto utiliza **Apache Ant** como sistema de construcción en lugar de Maven, por lo que todas las instrucciones de compilación y ejecución están adaptadas para trabajar con Ant y NetBeans.
