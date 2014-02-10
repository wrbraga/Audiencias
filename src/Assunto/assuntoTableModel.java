package Assunto;

import javax.swing.table.AbstractTableModel;
import DB.Assunto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wesley
 */
public class assuntoTableModel extends AbstractTableModel {
    private List<Assunto> linha;
    private static final String[] colunas = {"ID", "ASSUNTO"};
    
    public assuntoTableModel() {
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
                return linha.get(l).getIdassunto();
            case 1:
                return linha.get(l).getAssunto();
            default:
                throw new IndexOutOfBoundsException("Indice de coluna inválido!");
        }
    }
    
    public void addAssunto(Assunto a) {
        linha.add(a);
        fireTableDataChanged();
    }
    
    public void removeAssunto(int index) {
        linha.remove(index);
        fireTableDataChanged();
    }
    
    @Override
    public void setValueAt(Object o, int ilinha, int icoluna) {
        Assunto assunto = (Assunto)o;
        
        switch(icoluna) {
            case 0:
                linha.get(ilinha).setIdassunto(assunto.getIdassunto());
                break;
            case 1:
                linha.get(ilinha).setAssunto(assunto.getAssunto());
        }
        
        fireTableCellUpdated(ilinha, ilinha);
    }
    
    public void setValueAt(Assunto a, int ilinha) {
        linha.get(ilinha).setIdassunto(a.getIdassunto());
        linha.get(ilinha).setAssunto(a.getAssunto());
        fireTableDataChanged();        
    }
    
    public void limpar() {
        linha.clear();
        fireTableDataChanged();
    }
}
