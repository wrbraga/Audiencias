package Utilitarios;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wesley
 */
public class Calendario {
    static Calendar cal = Calendar.getInstance();
    
    public static void setData(String dia) {
        cal.setTime(stringToDate(dia));
        cal.setFirstDayOfWeek(Calendar.SUNDAY);    
    }
    
    public static void setData(Date dia) {
        cal.setTime(dia);
        cal.setFirstDayOfWeek(Calendar.SUNDAY);    
    }
    
    public static String getDataString() {    
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
        return df.format(cal.getTime());
    }
    
    public static String getHoraString() {
        DateFormat hf = DateFormat.getTimeInstance(DateFormat.SHORT);        
        return hf.format(cal.getTime());
    }
    
    public static Date stringToDate(String sData) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date data = null;
        try {
            data = df.parse(sData);
        } catch (ParseException ex) {
            Logger.getLogger(Calendario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return data;
    }
    
    public static String dateToString(Date dDate) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(dDate);
    }
    
    public static Date stringToTime(String sData) {
        SimpleDateFormat df = new SimpleDateFormat("hh:mm");
        Date data = null;
        try {
            data = df.parse(sData);
        } catch (ParseException ex) {
            Logger.getLogger(Calendario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return data;
    }
    
    public static String timeToString(Date dDate) {
        SimpleDateFormat df = new SimpleDateFormat("hh:mm");
        return df.format(dDate);
    }
    
    public static int semana(String dia) {
        cal.setTime(stringToDate(dia));
        cal.setFirstDayOfWeek(Calendar.SUNDAY);
        return cal.get(Calendar.WEEK_OF_MONTH);        
    }
    
    public static int diaDaSemana() {
        return cal.get(Calendar.DAY_OF_WEEK);
    }
    
    public static int dia(Date data) {
        setData(data);
        return dia();
    }
    
    public static int dia() {
        return cal.get(Calendar.DAY_OF_MONTH);
    }
    
    public static int mes() {
        return cal.get(Calendar.MONTH);
    }
    
    public static int ano() {
        return cal.get(Calendar.YEAR);
    }
    
    public static String nomeMes(int i) {
        String MES[] = {"JANEIRO", "FEVEREIRO", "MARÇO", "ABRIL", "MAIO", "JUNHO", "JULHO", "AGOSTO", "SETEMBRO", "OUTUBRO", "NOVEMBRO", "DEZEMBRO"};
        return MES[i];
    }
    
    public static String nomeSemana(int i) {
        String[] SEMANA = {"DOMINGO", "SEGUNDA", "TERÇA", "QUARTA", "QUINTA", "SEXTA", "SÁBADO"};
        return SEMANA[i];
    }
        
}
