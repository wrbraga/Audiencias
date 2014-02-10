package Procurador;

import javax.swing.table.AbstractTableModel;
import DB.Procurador;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wesley
 */
public class procuradorTableModel extends AbstractTableModel {
    private final List<Procurador> linha;
    private static final String[] colunas = {"ID", "NOME","SIGLA","ÁREA","ANTIGUIDADE"};
    
    public procuradorTableModel() {
        linha = new ArrayList<>();
    }
    
    @Override
    public int getRowCount() {
        return linha.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int index) {
        return colunas[index];
    }
    
    @Override
    public Object getValueAt(int l, int coluna) {
        switch(coluna) {
            case 0:
                return linha.get(l).getIdProcurador();
            case 1:
                return linha.get(l).getProcurador();
            case 2:
                return linha.get(l).getSigla();
            case 3:
                return linha.get(l).getArea();
            case 4:
                return linha.get(l).getAntiguidade();
            case 5:
                return linha.get(l).getUltimo();
            case 6:
                return linha.get(l).getAtuando();
            default:
                throw new IndexOutOfBoundsException("Indice de coluna inválido!");
        }
    }
    
    public void addProcurador(Procurador a) {
        linha.add(a);
        fireTableDataChanged();
    }
    
    public void removeProcurador(int index) {
        linha.remove(index);
        fireTableDataChanged();
    }
    
    @Override
    public void setValueAt(Object o, int ilinha, int icoluna) {
        Procurador procurador = (Procurador)o;
        
        switch(icoluna) {
            case 0:
                linha.get(ilinha).setIdProcurador(procurador.getIdProcurador());
                break;
            case 1:
                linha.get(ilinha).setProcurador(procurador.getProcurador());
                break;
            case 2:                    
                linha.get(ilinha).setSigla(procurador.getSigla());
                break;
            case 3:
                linha.get(ilinha).setArea(procurador.getArea());
                break;
            case 4:
                linha.get(ilinha).setAntiguidade(procurador.getAntiguidade());
                break;
            case 5:
                linha.get(ilinha).setUltimo(procurador.getUltimo());
                break;
            case 6:
                linha.get(ilinha).setAtuando(procurador.getAtuando());
        }
        
        fireTableCellUpdated(ilinha, ilinha);
    }
    
    public void setValueAt(Procurador procurador, int ilinha) {
        linha.get(ilinha).setIdProcurador(procurador.getIdProcurador());
        linha.get(ilinha).setProcurador(procurador.getProcurador());
        linha.get(ilinha).setSigla(procurador.getSigla());
        linha.get(ilinha).setArea(procurador.getArea());
        linha.get(ilinha).setAntiguidade(procurador.getAntiguidade());
        linha.get(ilinha).setUltimo(procurador.getUltimo());
        linha.get(ilinha).setAtuando(procurador.getAtuando());
        fireTableDataChanged();        
    }
    
    public void limpar() {
        linha.clear();
        fireTableDataChanged();
    }
}
