/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Erick
 */
public class ObjetoExpediente {
    private String idExp="",idMed="", ns = "", temp="", presionArt="", fec = "", motivo = "";
    private int frecResp = 0, frecCard = 0;
    
    
    
    public ObjetoExpediente(String idExp, String idMed, String ns, String temp, String presionArt, String motivo, int fecResp, int fecCard){
        this.idExp = idExp;
        this.idMed = idMed;
        this.ns = ns;
        this.temp = temp;
        this.presionArt = presionArt;
        this.fec = fec;
        this.motivo = motivo;
        this.frecResp = frecResp;
        this.frecCard = frecCard;
    }
    
    public ObjetoExpediente(){
        
    }

    public String getIdExp() {
        return idExp;
    }

    public void setIdExp(String idExp) {
        this.idExp = idExp;
    }

    public String getIdMed() {
        return idMed;
    }

    public void setIdMed(String idMed) {
        this.idMed = idMed;
    }

    public String getNs() {
        return ns;
    }

    public void setNs(String ns) {
        this.ns = ns;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getPresionArt() {
        return presionArt;
    }

    public void setPresionArt(String presionArt) {
        this.presionArt = presionArt;
    }

    public String getFec() {
        return fec;
    }

    public void setFec(String fec) {
        this.fec = fec;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public int getFrecResp() {
        return frecResp;
    }

    public void setFrecResp(int frecResp) {
        this.frecResp = frecResp;
    }

    public int getFrecCard() {
        return frecCard;
    }

    public void setFrecCard(int frecCard) {
        this.frecCard = frecCard;
    }


    
    
}
