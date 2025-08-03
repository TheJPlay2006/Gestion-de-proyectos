# 📋 Gestor de Proyectos en Java

![Java](https://img.shields.io/badge/Java-17+-orange?style=flat-square&logo=java)
![Swing](https://img.shields.io/badge/Swing-GUI-blue?style=flat-square&logo=java)
![SQL Server](https://img.shields.io/badge/SQL%20Server-Express-red?style=flat-square&logo=microsoft-sql-server)
![NetBeans](https://img.shields.io/badge/NetBeans-24+-green?style=flat-square&logo=apache-netbeans-ide)
![Apache Ant](https://img.shields.io/badge/Apache%20Ant-Build-A81C7D?style=flat-square&logo=apache-ant)

> Aplicación de escritorio desarrollada en Java Swing para la gestión integral de proyectos de software, con arquitectura MVC y base de datos SQL Server.

---

## 📝 Descripción

Aplicación de escritorio desarrollada en **Java Swing** para la gestión integral de proyectos. Permite organizar proyectos, asignar y hacer seguimiento de tareas, gestionar usuarios y recursos asociados, conectándose a una base de datos **Microsoft SQL Server** mediante **JDBC**.

Este proyecto fue creado como práctica para la asignatura **Programación I** de la carrera Técnico Universitario en Informática (**UTN ITI-221**).

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
│       │   └── TareaDAO.java
│       ├── database/            # Utilidades de base de datos
│       │   └── ConexionBD.java
│       ├── model/               # Clases POJO
│       │   ├── Usuario.java
│       │   ├── Proyecto.java
│       │   ├── Tarea.java
│       │   └── Recurso.java
│       ├── service/             # Lógica de negocio
│       │   ├── UsuarioServicio.java
│       │   └── ProyectoServicio.java
│       └── ui/                  # Interfaz de usuario
│           ├── MainFrame.java
│           └── componentes/     # Componentes específicos
│               ├── TablaProyectos.java
│               └── FormularioProyecto.java
├── lib/                         # Librerías JAR externas
├── build/                       # Archivos compilados
├── dist/                        # JAR ejecutable generado
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
3. **Crea la base de datos**:

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

-- Crear tablas: Usuarios, Proyectos, Tareas, Recursos, RegistroErrores
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
**Jairo Steven Herrera Romero**  
Estudiante de Técnico Universitario en Informática  
Universidad Técnica Nacional (UTN)

### 📚 Contexto Académico
- **Asignatura**: Programación I
- **Código**: UTN ITI-221
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
