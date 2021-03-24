/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.ItemView;

import EntityPkg.ItemPkg.Item;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author DELL
 */
public class ItemTableModel<E> extends AbstractTableModel{
    String[] header;
    int[] indexes;
    Vector<Item> dataSetToPage;
    
    public ItemTableModel(String[] header, int[] indexes){
        this.header = new String[header.length];
        this.indexes = new int[indexes.length];
        System.arraycopy(header, 0, this.header, 0, header.length);
        System.arraycopy(indexes, 0, this.indexes, 0, indexes.length);
        this.dataSetToPage = new Vector<>();
    }

    public String[] getHeader() {
        return header;
    }

    public void setHeader(String[] header) {
        this.header = header;
    }

    public int[] getIndexes() {
        return indexes;
    }

    public void setIndexes(int[] indexes) {
        this.indexes = indexes;
    }

    public Vector<Item> getDataSetToPage() {
        return dataSetToPage;
    }

    public void setDataSetToPage(Vector<Item> data) {
        this.dataSetToPage = data;
    }

    
    
    @Override
    public int getRowCount() {
        return dataSetToPage.size();
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }

    @Override
    public String getColumnName(int column) {
        return (column < 0 || column >= header.length) ? "" : header[column];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(rowIndex < 0 || rowIndex > getRowCount() || columnIndex < 0 || columnIndex > getColumnCount())
            return null;
        Item item = dataSetToPage.get(rowIndex);
        switch(indexes[columnIndex]){
            case 0: return item.getCode();
            case 1: return item.getName();
            case 2: return item.getSupplier();
            case 3: return item.getUnit();
            case 4: return item.getPrice();
            case 5: return item.isSupply();
        }
        return null;    
    }
    
}
