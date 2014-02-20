package DB;

import java.util.Date;

/**
 *
 * @author wesley
 */
public class AfastamentosProcurador {
    Date datainicio;
    Date datafim;
    String obs;
    String procurador;
    String sigla;
    
    public AfastamentosProcurador(Date datainicio, Date datafim, String obs, String procurador, String sigla) {
        this.datainicio = datainicio;
        this.datafim = datafim;
        this.obs = obs;
        this.procurador = procurador;
        this.sigla = sigla;        
    }

    public AfastamentosProcurador() {
        
    }
    
    public Date getDataInicio() {
        return this.datainicio;
    }
    
    public void setDataInicio(Date datainicio) {
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
        
    public void setProcurador(String procurador) {
        this.procurador = procurador;
    }
    
    public String getProcurador() {
        return this.procurador;
    }
    
    public String getSigla() {
        return this.sigla;
    }
    
    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
}
