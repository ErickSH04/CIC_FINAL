/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Erick
 */
public class medico {
    private String idMedico, nombreMed, apellido1, apellido2, fechaNac, especialidad, numCedula, numTelefonico, domicilio, correo; 

    public medico(String idMedico, String nombreMed, String apellido1, String apellido2, String fechaNac, String especialidad,String  numCedula, String numTelefonico, String domicilio, String correo){
        this.idMedico = idMedico;
        this.nombreMed = nombreMed;
        this.apellido1 = apellido1; 
        this.apellido2 = apellido2;
        this.fechaNac = fechaNac; 
        this.especialidad = especialidad;
        this.numCedula = numCedula;
        this.numTelefonico = numTelefonico;
        this.domicilio = domicilio;
        this.correo = correo;
    }
    
    public medico(){
        
    }

    public String getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(String idMedico) {
        this.idMedico = idMedico;
    }

    public String getNombreMed() {
        return nombreMed;
    }

    public void setNombreMed(String nombreMed) {
        this.nombreMed = nombreMed;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getNumCedula() {
        return numCedula;
    }

    public void setNumCedula(String numCedula) {
        this.numCedula = numCedula;
    }

    public String getNumTelefonico() {
        return numTelefonico;
    }

    public void setNumTelefonico(String numTelefonico) {
        this.numTelefonico = numTelefonico;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public static void mostrarMed(medico med){
        System.out.println("\tDatos del medico");
        System.out.println("nombre:"+med.getNombreMed());
        System.out.println("apellido 1:"+med.getApellido1());
        System.out.println("apellido 2:"+med.getApellido2());
        System.out.println("fecha de nacimiento: "+med.getFechaNac());
        System.out.println("especialidad: "+med.getEspecialidad());
        System.out.println("numero de cedula: "+med.getNumCedula());
        System.out.println("domicilio: "+med.getDomicilio());
        System.out.println("correo: "+med.getCorreo());
        System.out.println("Id del medico: "+Integer.parseInt(med.getIdMedico()));
    }
}
