/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

public class Recurso {
    private int id;
    private int tareaId;
    private String nombreRecurso;
    private String tipoRecurso; // Documento, Enlace, Nota, etc.
    private String rutaRecurso; // Ruta o URL del recurso
    private LocalDateTime fechaSubida;
    
    // Constructor vacío
    public Recurso() {}
    
    // Constructor con parámetros
    public Recurso(int tareaId, String nombreRecurso, String tipoRecurso, String rutaRecurso) {
        this.tareaId = tareaId;
        this.nombreRecurso = nombreRecurso;
        this.tipoRecurso = tipoRecurso;
        this.rutaRecurso = rutaRecurso;
    }
    
    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getTareaId() { return tareaId; }
    public void setTareaId(int tareaId) { this.tareaId = tareaId; }
    
    public String getNombreRecurso() { return nombreRecurso; }
    public void setNombreRecurso(String nombreRecurso) { this.nombreRecurso = nombreRecurso; }
    
    public String getTipoRecurso() { return tipoRecurso; }
    public void setTipoRecurso(String tipoRecurso) { this.tipoRecurso = tipoRecurso; }
    
    public String getRutaRecurso() { return rutaRecurso; }
    public void setRutaRecurso(String rutaRecurso) { this.rutaRecurso = rutaRecurso; }
    
    public LocalDateTime getFechaSubida() { return fechaSubida; }
    public void setFechaSubida(LocalDateTime fechaSubida) { this.fechaSubida = fechaSubida; }
    
    @Override
    public String toString() {
        return "Recurso{" + "id=" + id + ", tareaId=" + tareaId + ", nombreRecurso=" + nombreRecurso + 
               ", tipoRecurso=" + tipoRecurso + ", rutaRecurso=" + rutaRecurso + '}';
    }
}