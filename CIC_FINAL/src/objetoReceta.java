/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Erick
 */
public class objetoReceta {
    private int idRec, idMed;
    private String medicamentos="",indicaciones="", fechaEmision="", numeroSeguro="";
    
    public objetoReceta(String medicamentos,String indicaciones, String fechaEmision, String numeroSeguro, int idRec, int idMed){
        this.idRec = idRec;
        this.idMed = idMed;
        this.medicamentos = medicamentos;
        this.indicaciones = indicaciones;
        this.fechaEmision = fechaEmision;
        this.numeroSeguro = numeroSeguro;
    }
    
    public objetoReceta(){
        
    }

    public int getIdRec() {
        return idRec;
    }

    public void setIdRec(int idRec) {
        this.idRec = idRec;
    }

    public int getIdMed() {
        return idMed;
    }

    public void setIdMed(int idMed) {
        this.idMed = idMed;
    }

    public String getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(String medicamentos) {
        this.medicamentos = medicamentos;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getNumeroSeguro() {
        return numeroSeguro;
    }

    public void setNumeroSeguro(String numeroSeguro) {
        this.numeroSeguro = numeroSeguro;
    }
    
    
}
