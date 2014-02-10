
package Classe;

import DB.Classe;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author wesley
 */
public class classeTableModel extends AbstractTableModel {
    private List<Classe> linha;
    private static final String[] colunas = {"ID","CLASSE"};
    
    public classeTableModel() {
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
        Classe dados = linha.get(row);        
        switch(col) {
            case 0:
                return dados.getIdclasse();                
            case 1:
                return dados.getClasse();
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
        Classe local = linha.get(rowIndex);
        Classe dados = (Classe)valor;
               
        switch(columnIndex) {
            case 0:
                local.setIdclasse(dados.getIdclasse());               
                break;
            case 1:
                local.setClasse(dados.getClasse());                
                break;
            default:
                throw new IndexOutOfBoundsException("Valor incorreto");
        }
        
        fireTableCellUpdated(rowIndex, columnIndex);
                               
    }
    
    public void setValueAt(Classe valor, int rowIndex) {
        Classe local = linha.get(rowIndex);
                
        local.setIdclasse(valor.getIdclasse());
        local.setClasse(valor.getClasse());  
        
        fireTableCellUpdated(rowIndex, 1);
    }
    
    public Classe getClasse(int rowIndex) {
        return linha.get(rowIndex);
    }
    
    public void addClasse(Classe l) {
        linha.add(l);
        fireTableDataChanged();
    }
    
    public void removeClasse(int index) {
        linha.remove(index);
        fireTableDataChanged();
    }
    
    public void limpar() {
        linha.clear();
        fireTableDataChanged();
    }
}
