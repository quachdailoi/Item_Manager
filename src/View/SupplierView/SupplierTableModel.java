/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.SupplierView;

import EntityPkg.SupplierPkg.Supplier;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author DELL
 */
public class SupplierTableModel<E> extends AbstractTableModel{
    String[] header;
    int[] indexes;
    Vector<Supplier> dataSetToPage;
    
    public SupplierTableModel(String[] header, int[] indexes){
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

    public Vector<Supplier> getDataSetToPage() {
        return dataSetToPage;
    }

    public void setDataSetToPage(Vector<Supplier> data) {
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
        Supplier sup = dataSetToPage.get(rowIndex);
        switch(indexes[columnIndex]){
            case 0: return sup.getCode();
            case 1: return sup.getName();
            case 2: return sup.getAddress();
            case 3: return sup.isColloborating();
        }
        return null;    
    }
    
}
