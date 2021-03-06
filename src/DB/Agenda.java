package DB;
// Generated 26/11/2013 11:14:23 by Hibernate Tools 3.2.1.GA


import java.util.Date;

/**
 * Agenda generated by hbm2java
 */
public class Agenda  implements java.io.Serializable {


     private int idagenda;
     private Date dia;
     private Date hora;
     private String processo;
     private int idclasse;
     private int idprocurador;
     private int idassunto;
     private int idlocal;

    public Agenda() {
    }

    public Agenda(int idagenda, Date dia, Date hora, String processo, int idclasse, int idprocurador, int idassunto, int idlocal) {
       this.idagenda = idagenda;
       this.dia = dia;
       this.hora = hora;
       this.processo = processo;
       this.idclasse = idclasse;
       this.idprocurador = idprocurador;
       this.idassunto = idassunto;
       this.idlocal = idlocal;
    }
   
    public int getIdagenda() {
        return this.idagenda;
    }
    
    public void setIdagenda(int idagenda) {
        this.idagenda = idagenda;
    }
    public Date getDia() {
        return this.dia;
    }
    
    public void setDia(Date dia) {
        this.dia = dia;
    }
    public Date getHora() {
        return this.hora;
    }
    
    public void setHora(Date hora) {
        this.hora = hora;
    }
    public String getProcesso() {
        return this.processo;
    }
    
    public void setProcesso(String processo) {
        this.processo = processo;
    }
    public int getIdclasse() {
        return this.idclasse;
    }
    
    public void setIdclasse(int idclasse) {
        this.idclasse = idclasse;
    }
    public int getIdprocurador() {
        return this.idprocurador;
    }
    
    public void setIdprocurador(int idprocurador) {
        this.idprocurador = idprocurador;
    }
    public int getIdassunto() {
        return this.idassunto;
    }
    
    public void setIdassunto(int idassunto) {
        this.idassunto = idassunto;
    }
    public int getIdlocal() {
        return this.idlocal;
    }
    
    public void setIdlocal(int idlocal) {
        this.idlocal = idlocal;
    }




}


