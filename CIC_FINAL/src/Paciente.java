/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Erick
 */
public class Paciente {
   private String nss, nombrePac, apellido1, apellido2, fechaNac, metodoPago, correo;
   
   public Paciente(String nss, String nombrePac, String apellido1, String apellido2, String fechaNac, String metodoPago, String correo){
       this.nss=nss;
       this.nombrePac = nombrePac;
       this.apellido1 = apellido1;
       this.apellido2 = apellido2;
       this.fechaNac = fechaNac;
       this.metodoPago = metodoPago;
       this.correo = correo;
   }
   
      public Paciente(String nss, String nombrePac, String apellido1, String apellido2, String fechaNac){
       this.nss=nss;
       this.nombrePac = nombrePac;
       this.apellido1 = apellido1;
       this.apellido2 = apellido2;
       this.fechaNac = fechaNac;
       this.metodoPago = "";
       this.correo = "";
   }
   
   public Paciente(){
       
   }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getNombrePac() {
        return nombrePac;
    }

    public void setNombrePac(String nombrePac) {
        this.nombrePac = nombrePac;
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

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
   
    public static void mostrarPac(Paciente pac){
        System.out.println("\tDatos del paciente");
        System.out.println("Numero de seguridad social: "+pac.getNss());
        System.out.println("nombre:"+pac.getNombrePac());
        System.out.println("apellido 1:"+pac.getApellido1());
        System.out.println("apellido 2:"+pac.getApellido2());
        System.out.println("fecha de nacimiento: "+pac.getFechaNac());
        System.out.println("metodo de pago: "+pac.getMetodoPago());
        System.out.println("correo: "+pac.getCorreo());
    }
}
