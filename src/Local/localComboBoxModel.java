
package Local;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ListDataListener;


/**
 *
 * @author wrbraga
 */
public final class localComboBoxModel extends DefaultComboBoxModel implements ComboBoxModel {
    private List<String> linha =  new ArrayList<>();
    private String item;

    public localComboBoxModel(List dados) {        
        carregarLista(dados);
        try {
            item = linha.get(0);
        } catch(Exception e) {
            
        }
    }

    @Override
    public void setSelectedItem(Object anItem) {
        item = (String) anItem;
    }

    @Override
    public Object getSelectedItem() {
        return item;
    }
    
    public void addItem(String i) {
        linha.add(i);        
        setSelectedItem(i);
    }
    
    @Override
    public int getSize() {
        return linha.size();
    }

    @Override
    public Object getElementAt(int index) {
        return linha.get(index);
    }
    
    public void carregarLista(List dados) {
        limpar();
        linha.addAll(dados);
    }
    
    @Override
    public void removeElementAt(int index) {
        linha.remove(index);
        item = linha.get(0);
    }
    
    public void removeElementAt(String obj) {        
        linha.remove(obj);
        item = linha.get(0);
    }
    
    public void limpar() {
        try {
            linha.clear();
        } catch(Exception e) {
            
        }
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        
    }
    
    
}
