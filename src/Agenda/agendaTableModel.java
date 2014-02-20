package Agenda;

import DB.Agenda;
import DB.AgendaUtil;
import Utilitarios.Calendario;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author wrbraga
 */
public class agendaTableModel extends AbstractTableModel{
    List<Agenda> linha = new ArrayList<>();
    String coluna[] = {"ID","DIA","HORA","N.PROC.","CLASSE","ASSUNTO","LOCAL","PROCURADOR"};
    AgendaUtil agendaUtil = new AgendaUtil();

    @Override
    public int getRowCount() {
        return linha.size();
    }

    @Override
    public int getColumnCount() {
        return coluna.length;
    }

    public Agenda getAgenda(int rowIndex) {
        return linha.get(rowIndex);
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case 0:
                return linha.get(rowIndex).getIdagenda();
            case 1:
                return Calendario.dateToString(linha.get(rowIndex).getDia());
            case 2:
                return Calendario.timeToString(linha.get(rowIndex).getHora());
            case 3:
                return linha.get(rowIndex).getProcesso();
            case 4:                
                return agendaUtil.getClasseByID(linha.get(rowIndex).getIdclasse());                
            case 5:
                return agendaUtil.getAssuntoByID(linha.get(rowIndex).getIdassunto());                
            case 6:
                return agendaUtil.getLocalByID(linha.get(rowIndex).getIdlocal());
            case 7:
                return agendaUtil.getProcuradorByID(linha.get(rowIndex).getIdprocurador());
            default:
                return null;
        }
        
    }
    
    @Override
    public void setValueAt(Object valor, int rowIndex, int colIndex) {
        Agenda agenda = linha.get(rowIndex);
        Agenda dados = (Agenda)valor;
        
        switch(colIndex) {
            case 0:
                agenda.setIdagenda(dados.getIdagenda());
                break;
            case 1:
                agenda.setDia(dados.getDia());
                break;
            case 2:
                agenda.setHora(dados.getHora());
                break;
            case 3:
                agenda.setProcesso(dados.getProcesso());
                break;
            case 4:                
                agenda.setIdclasse(dados.getIdclasse());                
                break;
            case 5:
                agenda.setIdassunto(dados.getIdassunto());
                break;
            case 6:
                agenda.setIdlocal(dados.getIdlocal());
                break;
            case 7:
                agenda.setIdprocurador(dados.getIdprocurador()); 
        }        
        
        fireTableDataChanged();
    }

    public void setValueAt(Agenda dados, int ilinha) {
        linha.get(ilinha).setIdagenda(dados.getIdagenda());
        linha.get(ilinha).setDia(dados.getDia());
        linha.get(ilinha).setHora(dados.getHora());
        linha.get(ilinha).setProcesso(dados.getProcesso());
        linha.get(ilinha).setIdclasse(dados.getIdclasse());                
        linha.get(ilinha).setIdassunto(dados.getIdassunto());
        linha.get(ilinha).setIdlocal(dados.getIdlocal());
        linha.get(ilinha).setIdprocurador(dados.getIdprocurador()); 
        fireTableDataChanged();
    }
    
    public void addAgenda(Agenda a) {
        linha.add(a);
        fireTableDataChanged();
    }
    
    @Override
    public String getColumnName(int i) {
        return coluna[i];
        
    }
    
    public void removerAgenda(int row) {
        linha.remove(row);
        fireTableDataChanged();
    }
    
    public void limpar() {
        linha.clear();
        fireTableDataChanged();
    }
    
    
}
