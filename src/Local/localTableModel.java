
package Local;

import DB.Local;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author wesley
 */
public class localTableModel extends AbstractTableModel {
    private List<Local> linha;
    private static final String[] colunas = {"ID","LOCAL"};
    
    public localTableModel() {
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
    public String getColumnName(int col) {
        return colunas[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        Local dados = linha.get(row);        
        switch(col) {
            case 0:
                return dados.getIdlocal();                
            case 1:
                return dados.getLocal();
            default:
                throw new IndexOutOfBoundsException("Valor incorreto");
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {  
        return true;  
    }  
    
    @Override
    public void setValueAt(Object valor, int rowIndex, int columnIndex) {
        Local local = linha.get(rowIndex);
        Local dados = (Local)valor;
               
        switch(columnIndex) {
            case 0:
                local.setIdlocal(dados.getIdlocal());               
                break;
            case 1:
                local.setLocal(dados.getLocal());                
                break;
            default:
                throw new IndexOutOfBoundsException("Valor incorreto");
        }
        
        fireTableCellUpdated(rowIndex, columnIndex);
        fireTableDataChanged();
                               
    }
    
    public void setValueAt(Local valor, int rowIndex) {
        Local local = linha.get(rowIndex);
                
        local.setIdlocal(valor.getIdlocal());
        local.setLocal(valor.getLocal());  
        
        fireTableCellUpdated(rowIndex, 1);
        fireTableDataChanged();        
    }
    
    public Local getLocal(int rowIndex) {
        return linha.get(rowIndex);
    }
    
    public void addLocal(Local l) {
        linha.add(l);
        fireTableDataChanged();
    }
    
    public void removeLocal(int index) {
        linha.remove(index);
        fireTableDataChanged();
    }
    
    public void limpar() {
        linha.clear();
        fireTableDataChanged();
    }
}
