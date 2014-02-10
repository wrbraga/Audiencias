package DB;

import java.util.Date;

public class Afastamentos implements java.io.Serializable  {
    
     private int idafastamento;
     private Date datainicio;
     private Date datafim;
     private String obs;
     private int idprocurador;
    
     public Afastamentos() {
         
     }
     
     public Afastamentos(int idafastamento, Date datainicio, Date datafim, String obs, int idprocurador) {
         this.idafastamento = idafastamento;
         this.datainicio = datainicio;
         this.datafim = datafim;
         this.obs = obs;
         this.idprocurador = idprocurador;
     }
     
     public int getIdafastamento() {
         return this.idafastamento;
     }
     
     public void setIdafastamento(int idafastamento) {
         this.idafastamento = idafastamento;
     }
     
     public Date getDatainicio() {
         return this.datainicio;
     }
     
     public void setDatainicio(Date datainicio) {
         this.datainicio = datainicio;
     }
     
     public Date getDatafim() {
         return this.datafim;
     }
     
     public void setDatafim(Date datafim) {
         this.datafim = datafim;
     }     

     public String getObs() {
         return this.obs;         
     }
     
     public void setObs(String obs) {
         this.obs = obs;
     }
     
     public int getIdprocurador() {
         return this.idprocurador;
     }
     
     public void setIdprocurador(int idprocurador) {
         this.idprocurador = idprocurador;
     }
}
