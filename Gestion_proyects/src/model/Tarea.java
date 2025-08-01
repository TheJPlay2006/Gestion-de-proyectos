/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Tarea {
    private int id;
    private int proyectoId;
    private String titulo;
    private String descripcion;
    private Integer asignadoA; // ID del usuario asignado (puede ser null)
    private String estado; // Por Hacer, En Progreso, Revisión, Hecho
    private String prioridad; // Baja, Media, Alta
    private LocalDate fechaInicio;
    private LocalDate fechaVencimiento;
    private LocalDate fechaCompletado;
    private LocalDateTime fechaCreacion;
    
    // Constructor vacío
    public Tarea() {}
    
    // Constructor con parámetros
    public Tarea(int proyectoId, String titulo, String descripcion, Integer asignadoA, 
                 String estado, String prioridad, LocalDate fechaInicio, LocalDate fechaVencimiento) {
        this.proyectoId = proyectoId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.asignadoA = asignadoA;
        this.estado = estado;
        this.prioridad = prioridad;
        this.fechaInicio = fechaInicio;
        this.fechaVencimiento = fechaVencimiento;
    }
    
    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getProyectoId() { return proyectoId; }
    public void setProyectoId(int proyectoId) { this.proyectoId = proyectoId; }
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public Integer getAsignadoA() { return asignadoA; }
    public void setAsignadoA(Integer asignadoA) { this.asignadoA = asignadoA; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }
    
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
    
    public LocalDate getFechaCompletado() { return fechaCompletado; }
    public void setFechaCompletado(LocalDate fechaCompletado) { this.fechaCompletado = fechaCompletado; }
    
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    
    @Override
    public String toString() {
        return "Tarea{" + "id=" + id + ", proyectoId=" + proyectoId + ", titulo=" + titulo + 
               ", estado=" + estado + ", prioridad=" + prioridad + ", fechaVencimiento=" + fechaVencimiento + '}';
    }
}