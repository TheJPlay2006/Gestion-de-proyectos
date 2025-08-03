# 📋 Gestor de Proyectos en Java

![Java](https://img.shields.io/badge/Java-17+-orange?style=flat-square&logo=java)
![Swing](https://img.shields.io/badge/Swing-GUI-blue?style=flat-square&logo=java)
![SQL Server](https://img.shields.io/badge/SQL%20Server-Express-red?style=flat-square&logo=microsoft-sql-server)
![NetBeans](https://img.shields.io/badge/NetBeans-24+-green?style=flat-square&logo=apache-netbeans-ide)
![Apache Ant](https://img.shields.io/badge/Apache%20Ant-Build-A81C7D?style=flat-square&logo=apache-ant)

> Aplicación de escritorio desarrollada en Java Swing para la gestión integral de proyectos de software, con arquitectura MVC y base de datos SQL Server.

## 💾 Esquema de Base de Datos

### 📊 Tablas Principales

| Tabla | Descripción | Campos Clave |
|-------|-------------|--------------|
| **Usuarios** | Gestión de usuarios del sistema | `id`, `nombre_usuario`, `nombre_completo`, `correo_electronico` |
| **Proyectos** | Información de proyectos | `id`, `nombre`, `descripcion`, `estado`, `fecha_inicio`, `fecha_fin` |
| **Tareas** | Tareas asociadas a proyectos | `id`, `proyecto_id`, `titulo`, `asignado_a`, `estado`, `prioridad` |
| **Recursos** | Recursos adjuntos a tareas | `id`, `tarea_id`, `nombre_recurso`, `tipo_recurso`, `ruta_recurso` |
| **RegistroErrores** | Log de errores del sistema | `id`, `mensaje_error`, `nombre_metodo`, `fecha_registro` |

### 🔗 Relaciones
- `Tareas.proyecto_id` → `Proyectos.id` (CASCADE DELETE)
- `Tareas.asignado_a` → `Usuarios.id`
- `Recursos.tarea_id` → `Tareas.id` (CASCADE DELETE)

### 📈 Estados del Sistema
- **Proyectos**: `No Iniciado`, `En Progreso`, `Completado`, `En Espera`
- **Tareas**: `Por Hacer`, `En Progreso`, `Revisión`, `Hecho`
- **Prioridades**: `Baja`, `Media`, `Alta`

---

## 📝 Descripción

Aplicación de escritorio desarrollada en **Java Swing** para la gestión integral de proyectos. Permite organizar proyectos, asignar y hacer seguimiento de tareas, gestionar usuarios y recursos asociados, conectándose a una base de datos **Microsoft SQL Server** mediante **JDBC**.

Este proyecto fue creado como práctica para la asignatura **Programación I** de la carrera **Ingeniería en Tecnologías de la Información** de la **Universidad Técnica Nacional (UTN)**.

---

## 🎯 Funcionalidades Principales

### ✅ Gestión Completa
- **Proyectos**: Alta, baja, modificación y consulta (CRUD completo)
- **Tareas**: Creación, edición, eliminación y asignación a usuarios
- **Usuarios**: Administración completa de miembros del equipo
- **Recursos**: Adjuntar documentos, enlaces y otros recursos a tareas

### ✅ Seguimiento y Control
- **Progreso**: Visualización del estado de tareas y proyectos
- **Asignaciones**: Control de responsables por tarea
- **Estados**: Seguimiento de avance en tiempo real

### ✅ Interfaz y Arquitectura
- **GUI Intuitiva**: Interfaz gráfica basada en Swing
- **Arquitectura MVC**: Código organizado y mantenible
- **Persistencia**: Almacenamiento seguro en SQL Server

---

## 🛠️ Tecnologías Utilizadas

| Tecnología | Uso | Versión |
|------------|-----|---------|
| **Java** | Lenguaje principal | 11+ (Compatible JDK 23+) |
| **NetBeans IDE** | Entorno de desarrollo | 24+ |
| **Java Swing** | Interfaz gráfica (GUI) | - |
| **SQL Server** | Base de datos relacional | 2022 Express |
| **JDBC** | Conectividad base de datos | Microsoft Driver 12.10+ |
| **Apache Ant** | Sistema de construcción | - |
| **Git** | Control de versiones | - |

---

## 🧩 Estructura del Proyecto

```
Gestion-de-proyectos/
├── src/
│   └── Main/                    # Punto de entrada principal
│       ├── dao/                 # Data Access Objects
│       │   ├── UsuarioDAO.java
│       │   ├── ProyectoDAO.java
│       │   ├── TareaDAO.java
│       │   └── RecursoDAO.java
│       ├── database/            # Utilidades de base de datos
│       │   └── ConexionBD.java
│       ├── model/               # Clases POJO
│       │   ├── Usuario.java
│       │   ├── Proyecto.java
│       │   ├── Tarea.java
│       │   └── Recurso.java
│       ├── service/             # Lógica de negocio
│       │   ├── UsuarioServicio.java
│       │   ├── ProyectoServicio.java
│       │   └── TareaServicio.java
│       └── ui/                  # Interfaz de usuario
│           ├── MainFrame.java
│           └── componentes/     # Componentes específicos
│               ├── TablaProyectos.java
│               └── FormularioProyecto.java
├── lib/                         # Librerías JAR externas (JDBC Driver)
├── build/                       # Archivos compilados
├── dist/                        # JAR ejecutable generado
├── bd/                          # Scripts de base de datos
│   └── script_bd.sql            # Script completo de creación
├── nbproject/                   # Configuración NetBeans
├── build.xml                    # Script construcción Ant
└── README.md                    # Este archivo
```

---

## 🚀 Cómo Ejecutar el Proyecto

### 1. Prerrequisitos

☑️ **Java JDK 11** o superior  
☑️ **Apache Ant** instalado y configurado  
☑️ **NetBeans 24+** (recomendado para desarrollo)  
☑️ **Microsoft SQL Server 2022 Express** o superior  
☑️ **Git** para clonar el repositorio  

### 2. Instalación

#### 📁 Clona el repositorio
```bash
git clone https://github.com/TheJPlay2006/Gestion-de-proyectos.git
cd Gestion-de-proyectos
```

#### 🗄️ Configura la base de datos

1. **Abre SQL Server Management Studio (SSMS)**
2. **Conéctate a tu instancia local** (ejemplo: `JPLAYLAPTOP\SQLEXPRESS`)
3. **Ejecuta el siguiente script SQL completo**:

```sql
-- Crear la base de datos si no existe
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'GestorProyectosDB')
BEGIN
    CREATE DATABASE GestorProyectosDB;
    PRINT '✅ Base de datos GestorProyectosDB creada.';
END
ELSE
BEGIN
    PRINT 'ℹ️  La base de datos GestorProyectosDB ya existe.';
END
GO

-- Usar la base de datos
USE GestorProyectosDB;
GO

-- Tabla de Usuarios
CREATE TABLE Usuarios (
    id INT IDENTITY(1,1) PRIMARY KEY,
    nombre_usuario NVARCHAR(50) NOT NULL UNIQUE,
    nombre_completo NVARCHAR(100) NOT NULL,
    correo_electronico NVARCHAR(100),
    fecha_creacion DATETIME DEFAULT GETDATE()
);

-- Tabla de Proyectos
CREATE TABLE Proyectos (
    id INT IDENTITY(1,1) PRIMARY KEY,
    nombre NVARCHAR(100) NOT NULL,
    descripcion NVARCHAR(MAX),
    fecha_inicio DATE,
    fecha_fin DATE,
    estado NVARCHAR(20) DEFAULT 'No Iniciado' CHECK (estado IN ('No Iniciado', 'En Progreso', 'Completado', 'En Espera')),
    fecha_creacion DATETIME DEFAULT GETDATE()
);

-- Tabla de Tareas
CREATE TABLE Tareas (
    id INT IDENTITY(1,1) PRIMARY KEY,
    proyecto_id INT NOT NULL,
    titulo NVARCHAR(100) NOT NULL,
    descripcion NVARCHAR(MAX),
    asignado_a INT, -- FK a Usuarios
    estado NVARCHAR(20) DEFAULT 'Por Hacer' CHECK (estado IN ('Por Hacer', 'En Progreso', 'Revisión', 'Hecho')),
    prioridad NVARCHAR(10) DEFAULT 'Media' CHECK (prioridad IN ('Baja', 'Media', 'Alta')),
    fecha_inicio DATE,
    fecha_vencimiento DATE,
    fecha_completado DATE,
    fecha_creacion DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (proyecto_id) REFERENCES Proyectos(id) ON DELETE CASCADE,
    FOREIGN KEY (asignado_a) REFERENCES Usuarios(id)
);

-- Tabla de Recursos
CREATE TABLE Recursos (
    id INT IDENTITY(1,1) PRIMARY KEY,
    tarea_id INT NOT NULL,
    nombre_recurso NVARCHAR(100) NOT NULL,
    tipo_recurso NVARCHAR(50), -- Documento, Enlace, Nota, etc.
    ruta_recurso NVARCHAR(255), -- Ruta o URL del recurso
    fecha_subida DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (tarea_id) REFERENCES Tareas(id) ON DELETE CASCADE
);

-- Tabla de Logs de Errores
CREATE TABLE RegistroErrores (
    id INT IDENTITY(1,1) PRIMARY KEY,
    mensaje_error NVARCHAR(MAX),
    nombre_metodo NVARCHAR(100),
    detalles_error NVARCHAR(MAX),
    fecha_registro DATETIME DEFAULT GETDATE()
);

-- Insertar datos de ejemplo
INSERT INTO Usuarios (nombre_usuario, nombre_completo, correo_electronico) VALUES
('juan_dev', 'Juan Pérez', 'juan.perez@example.com'),
('ana_mgr', 'Ana Gómez', 'ana.gomez@example.com');

INSERT INTO Proyectos (nombre, descripcion, fecha_inicio, fecha_fin) VALUES
('Sistema de Inventario', 'Desarrollo de un sistema para control de inventario', '2024-06-01', '2024-08-31'),
('Campaña Marketing', 'Campaña digital para lanzamiento de producto', '2024-07-01', '2024-09-30');

INSERT INTO Tareas (proyecto_id, titulo, descripcion, asignado_a, estado, prioridad, fecha_inicio, fecha_vencimiento) VALUES
(1, 'Diseño de Base de Datos', 'Crear modelo ER y scripts SQL', 1, 'En Progreso', 'Alta', '2024-06-01', '2024-06-15'),
(1, 'Implementar API REST', 'Desarrollar endpoints para CRUD', 1, 'Por Hacer', 'Alta', '2024-06-16', '2024-07-15'),
(2, 'Crear Banners', 'Diseñar creatividades para redes sociales', 2, 'Hecho', 'Media', '2024-07-01', '2024-07-10');

INSERT INTO Recursos (tarea_id, nombre_recurso, tipo_recurso, ruta_recurso) VALUES
(1, 'Modelo_ER.pdf', 'Documento', '/recursos/Modelo_ER.pdf'),
(3, 'Banners.zip', 'Documento', '/recursos/Banners.zip');

PRINT '🏁 Script de creación de base de datos ejecutado correctamente.';
```

4. **Configura la autenticación** (Windows Authentication recomendada)

#### ⚙️ Compilar y ejecutar

**Opción A: Línea de comandos (Apache Ant)**
```bash
# Limpiar proyecto
ant clean

# Compilar
ant compile

# Crear JAR ejecutable  
ant jar

# Ejecutar aplicación
ant run
```

**Opción B: JAR directo**
```bash
java --enable-native-access=ALL-UNNAMED -jar dist/Gestion-de-proyectos.jar
```

**Opción C: NetBeans (Recomendado)**
1. Abre NetBeans
2. `File → Open Project` → Selecciona la carpeta del proyecto
3. Click derecho → `Properties → Run`
4. Main Class: `Main.Main`
5. VM Options: `--enable-native-access=ALL-UNNAMED`
6. `F6` para ejecutar

---

## 🔧 Comandos Ant Disponibles

```bash
ant clean          # Limpia archivos compilados
ant compile        # Compila el código fuente
ant jar            # Crea el archivo JAR ejecutable
ant run            # Ejecuta la aplicación
ant javadoc        # Genera documentación JavaDoc
ant test           # Ejecuta pruebas unitarias
```

---

## 🏗️ Arquitectura del Sistema

### Patrón MVC Implementado

- **📊 Modelo (Model)**: POJOs que representan entidades del dominio
- **👁️ Vista (View)**: Interfaces gráficas desarrolladas en Swing
- **🎮 Controlador (Controller)**: Servicios que manejan la lógica de negocio

### Capas de la Aplicación

1. **Presentación**: Interfaz gráfica (Swing)
2. **Negocio**: Servicios y lógica de aplicación
3. **Acceso a Datos**: DAOs para interacción con BD
4. **Persistencia**: Base de datos SQL Server

---

## 🎓 Información Académica

### 👨‍🎓 Autor
**Jairo Herrera Romero**  
Estudiante de Ingeniería en Tecnologías de la Información  
Universidad Técnica Nacional (UTN)

### 📚 Contexto Académico
- **Asignatura**: Programación I
- **Carrera**: Ingeniería en Tecnologías de la Información
- **Universidad**: Universidad Técnica Nacional (UTN)
- **Propósito**: Proyecto práctico de gestión de software

---

## 🔗 Enlaces y Recursos

- **🔗 Repositorio**: [GitHub - Gestion-de-proyectos](https://github.com/TheJPlay2006/Gestion-de-proyectos)
- **📖 Documentación Java Swing**: [Oracle Docs](https://docs.oracle.com/javase/tutorial/uiswing/)
- **🗄️ SQL Server**: [Microsoft Docs](https://docs.microsoft.com/sql/sql-server/)
- **🔨 Apache Ant**: [Apache Ant Manual](https://ant.apache.org/manual/)

---

## 🤝 Contribuciones

Este es un proyecto académico desarrollado con fines educativos. Si encuentras errores o tienes sugerencias:

- 🐛 **Crear un issue** en el repositorio
- 🔄 **Enviar un pull request** con mejoras
- 📧 **Contactar al desarrollador**

---

## 📄 Licencia

Este proyecto fue creado como trabajo práctico para un curso académico. Todos los derechos pertenecen al autor **Jairo Herrera**, salvo que se indique lo contrario.

---

## 📦 Clonar el Repositorio

### HTTPS
```bash
git clone https://github.com/TheJPlay2006/Gestion-de-proyectos.git
```

### SSH
```bash
git clone git@github.com:TheJPlay2006/Gestion-de-proyectos.git
```

### Pasos después de clonar:
1. **Navega al directorio**:
   ```bash
   cd Gestion-de-proyectos
   ```

2. **Abre en NetBeans**:
   - `File → Open Project`
   - Selecciona la carpeta del proyecto

3. **Sigue los pasos de instalación** mencionados anteriormente

---

## 🎯 Objetivos Académicos Cumplidos

✅ **Desarrollo de aplicaciones desktop** en Java  
✅ **Integración con bases de datos** relacionales  
✅ **Implementación de patrones** de diseño (MVC, DAO)  
✅ **Interfaz de usuario** intuitiva y funcional  
✅ **Arquitectura en capas** bien definida  
✅ **Gestión de proyectos** de software  
✅ **Uso de herramientas** profesionales (Ant, NetBeans)  

---

## 💡 Notas de Desarrollo

- **🔐 Autenticación**: Sistema integrado de Windows por defecto
- **🔄 CRUD Completo**: Todas las entidades soportan operaciones básicas
- **🏗️ Escalabilidad**: Arquitectura preparada para futuras expansiones
- **🛡️ Manejo de Errores**: Registro de errores en base de datos

---

<div align="center">

### 🎓 Desarrollado con ❤️ para fines educativos

**Proyecto de Programación I - Universidad Técnica Nacional**

⭐ **¡No olvides darle una estrella al repositorio si te fue útil!** ⭐

</div>
