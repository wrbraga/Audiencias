package Afastamentos;

import DB.Afastamentos;
import DB.AgendaUtil;
import Utilitarios.Calendario;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author wrbraga
 */
public class afastamentosTableModel extends AbstractTableModel{
    List<Afastamentos> linha = new ArrayList<>();
    String coluna[] = {"ID","DATAINICIO","DATAFIM","OBS","PROCURADOR"};
    AgendaUtil agendaUtil = new AgendaUtil();

    @Override
    public int getRowCount() {
        return linha.size();
    }

    @Override
    public int getColumnCount() {
        return coluna.length;
    }

    public Afastamentos getAfastamento(int rowIndex) {
        return linha.get(rowIndex);
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case 0:
                return linha.get(rowIndex).getIdafastamento();
            case 1:
                return Calendario.dateToString(linha.get(rowIndex).getDatainicio());
            case 2:
                return Calendario.dateToString(linha.get(rowIndex).getDatafim());
            case 3:
                return linha.get(rowIndex).getObs();
            case 4:                
                return agendaUtil.getProcuradorByID(linha.get(rowIndex).getIdprocurador());
            default:
                return null;
        }
        
    }
    
    @Override
    public void setValueAt(Object valor, int rowIndex, int colIndex) {
        Afastamentos afastamento = linha.get(rowIndex);
        Afastamentos dados = (Afastamentos)valor;
        
        switch(colIndex) {
            case 0:
                afastamento.setIdafastamento(dados.getIdafastamento());
                break;
            case 1:
                afastamento.setDatainicio(dados.getDatainicio());                
                break;
            case 2:
                afastamento.setDatafim(dados.getDatafim());
                break;
            case 3:
                afastamento.setObs(dados.getObs());
                break;
            case 4:                
                afastamento.setIdprocurador(dados.getIdprocurador()); 
                break;
        }        
        
        fireTableDataChanged();
    }

    public void setValueAt(Afastamentos dados, int ilinha) {
        linha.get(ilinha).setIdafastamento(dados.getIdafastamento());
        linha.get(ilinha).setDatainicio(dados.getDatainicio());                
        linha.get(ilinha).setDatafim(dados.getDatafim());
        linha.get(ilinha).setDatafim(dados.getDatafim());
        linha.get(ilinha).setObs(dados.getObs());
        linha.get(ilinha).setIdprocurador(dados.getIdprocurador()); 
        fireTableDataChanged();
    }
    
    public void addAfastamento(Afastamentos a) {
        linha.add(a);
        fireTableDataChanged();
    }
    
    public void removerAfastamento(int index) {
        linha.remove(index);
        fireTableRowsDeleted(index, index);
    }
    
    @Override
    public String getColumnName(int i) {
        return coluna[i];
        
    }
    
    public void limpar() {
        linha.clear();
        fireTableDataChanged();
    }
    
    
}
