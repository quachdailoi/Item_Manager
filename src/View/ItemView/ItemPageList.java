/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.ItemView;

import EntityPkg.ItemPkg.*;
import DBService.ItemService;
import View.ItemView.ItemTableModel;
import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author DELL
 */
public class ItemPageList {

    ArrayList<ItemPage> listPage;
    ItemTableModel<Item> model;
    JButton btnCurPage;
    int maxOfPage;
    final boolean SEARCHMODE = true;
    final boolean NORMALMODE = false;
    String partOfName = "";

    public void pushDataPageToTable(boolean mode) throws ClassNotFoundException, SQLException {
        Vector<Item> dataSetToPage;
        if (mode == SEARCHMODE) {
            dataSetToPage = ItemService.searchItem(partOfName, 5, Integer.parseInt(btnCurPage.getText()));
        } else {
            dataSetToPage = ItemService.loadData(5, Integer.parseInt(btnCurPage.getText()));
        }
        model.setDataSetToPage(dataSetToPage);
    }

    public ItemPageList(Vector<Item> dataSetToPage, int[] indexes, String[] header) {
        model = new ItemTableModel(header, indexes);
        this.getModel().getDataSetToPage().addAll(dataSetToPage);
    }

    public ItemPageList(Vector<Item> dataOfPage, String[] header, int[] indexes, JButton btnBack, JButton btnFPage, JButton btnSPage, JButton btnTPage, JButton btnNext, JButton btnCurPage, JTable tblEmp, boolean mode, String partOfName) throws ClassNotFoundException, SQLException {
        this.partOfName = partOfName;
        listPage = new ArrayList<>();
        model = new ItemTableModel<>(header, indexes);
        tblEmp.setModel(model);
        model.setDataSetToPage(dataOfPage);
        if(mode == NORMALMODE) setMaxOfPage(ItemService.getMaxOfRowInSource(), 5); //---> number of rows in a page
        else setMaxOfPage(ItemService.getMaxOfRowInSearch(partOfName), 5);
        int newMaxOfPage = getMaxOfPage();

        btnBack.setEnabled(false);
        this.btnCurPage = btnCurPage;

        btnFPage.setVisible(true);
        btnSPage.setVisible(true);
        btnTPage.setVisible(true);

        if (newMaxOfPage == 1) {
            btnFPage.setVisible(false);
            btnSPage.setVisible(false);
            btnTPage.setVisible(false);
            btnNext.setEnabled(false);
            btnBack.setEnabled(false);
        }
        if (newMaxOfPage == 2) {
            btnTPage.setVisible(false);
            btnNext.setEnabled(false);
        }
        if (newMaxOfPage > 3) {
            btnNext.setEnabled(true);
        } else {
            btnNext.setEnabled(false);
        }
        System.out.println(newMaxOfPage + "page(s)");
        btnCurPage.setBackground(Color.YELLOW);
        if (this.btnCurPage == btnFPage) {
            btnFPageDoClick(btnBack, btnFPage, btnSPage, btnTPage, btnNext, mode);
        } else if (this.btnCurPage == btnTPage) {
            btnTPageDoClick(btnBack, btnFPage, btnSPage, btnTPage, btnNext, mode);
        } else {
            btnSPageDoClick(btnBack, btnFPage, btnSPage, btnTPage, btnNext, mode);
        }
    }

    public ItemPageList(Vector<Item> sourceData, String[] header, int[] index, JButton btnBack, JButton btnFPage, JButton btnSPage, JButton btnTPage, JButton btnNext, JButton btnCurPage, JTable tblEmp, int oldMaxOfPage, boolean mode, String partOfName) throws ClassNotFoundException, SQLException {
        this.partOfName = partOfName;
        listPage = new ArrayList<>();
        model = new ItemTableModel<>(header, index);
        tblEmp.setModel(model);
        model.setDataSetToPage(sourceData);
        if(mode == NORMALMODE) setMaxOfPage(ItemService.getMaxOfRowInSource(), 5); //---> number of rows in a page
        else setMaxOfPage(ItemService.getMaxOfRowInSearch(partOfName), 5);
        int newMaxOfPage = getMaxOfPage();
        btnBack.setEnabled(false);
        this.btnCurPage = btnCurPage;
        btnCurPage.setBackground(Color.YELLOW);
        if (Integer.parseInt(btnFPage.getText()) == 1) {
            btnBack.setEnabled(false);
        } else {
            btnBack.setEnabled(true);
        }
        if (Integer.parseInt(btnTPage.getText()) == newMaxOfPage) {
            btnNext.setEnabled(false);
        } else {
            btnNext.setEnabled(true);
        }
        if (newMaxOfPage > oldMaxOfPage ) { // increase page
                if (newMaxOfPage == 2) {
                    btnFPage.setVisible(true);
                    btnSPage.setVisible(true);
                } else if (newMaxOfPage == 3) {
                    btnTPage.setVisible(true);
                }
                btnNext.setEnabled(true);
        } else{ // decrease page
            System.out.println("flag");
            if (oldMaxOfPage > 3) {
                if (Integer.parseInt(btnTPage.getText()) == oldMaxOfPage) {
                    btnTPage.setText("" + (oldMaxOfPage - 1));
                    btnSPage.setText("" + (oldMaxOfPage - 2));
                    btnFPage.setText("" + (oldMaxOfPage - 3));
                    if (btnCurPage == btnSPage) {
                        setBtnCurPage(btnTPage);
                    } else if (btnCurPage == btnFPage) {
                        setBtnCurPage(btnSPage);
                    }
                    btnNext.setEnabled(false);
                }
            } else {
                if (oldMaxOfPage == 3) {
                    System.out.println("here1");
                    if (btnCurPage == btnTPage) {
                        setBtnCurPage(btnSPage);
                    } else if (btnCurPage == btnSPage) {
                        setBtnCurPage(btnFPage);
                    }
                    btnTPage.setVisible(false);
                    btnNext.setEnabled(false);
                } else if (oldMaxOfPage == 2) {
                    System.out.println("here");
                    if (btnCurPage == btnSPage) {
                        setBtnCurPage(btnFPage);
                    }
                    btnSPage.setVisible(false);
                    btnNext.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(btnCurPage, "EmpTy Data!");
                }
            }
        }
        System.out.println(newMaxOfPage + "page(s)");
        if (this.btnCurPage == btnFPage) {
            btnFPageDoClick(btnBack, btnFPage, btnSPage, btnTPage, btnNext, mode);
        } else if (this.btnCurPage == btnTPage) {
            btnTPageDoClick(btnBack, btnFPage, btnSPage, btnTPage, btnNext, mode);
        } else {
            btnSPageDoClick(btnBack, btnFPage, btnSPage, btnTPage, btnNext, mode);
        }
    }

    public ItemTableModel<Item> getModel() {
        return model;
    }

    public void setModel(ItemTableModel<Item> model) {
        this.model = model;
    }

    public JButton getBtnCurPage() {
        return btnCurPage;
    }

    public void setBtnCurPage(JButton btnCurPage) {
        this.btnCurPage = btnCurPage;
    }

    public ItemPageList() {
        listPage = new ArrayList<>();
    }

    public ArrayList<ItemPage> getListPage() {
        return listPage;
    }

    public void setListPage(ArrayList<ItemPage> listPage) {
        this.listPage = listPage;
    }

    public void setMaxOfPage(int maxOfRow, int rowInAPage) {
        int numOfPage = (maxOfRow % rowInAPage == 0) ? maxOfRow / rowInAPage : maxOfRow / rowInAPage + 1;
        for (int i = 0; i < numOfPage; i++) {
            JButton btnPage = new JButton((i + 1) + "");
            ItemPage iPage = new ItemPage(btnPage);
            listPage.add(iPage);
        }
        maxOfPage = listPage.size();
    }

    public int getMaxOfPage() {
        return maxOfPage;
    }

    public ItemPage getDataCurPage() {
        int indexbtnCurPage = Integer.parseInt(btnCurPage.getText());
        return listPage.get(indexbtnCurPage);
    }

    public void nextPage(JButton btnBack, JButton btnFPage, JButton btnSPage, JButton btnTPage, JButton btnNext, boolean mode) {
        int indexCurPage = Integer.parseInt(btnCurPage.getText());
        int maxIndexPage = getMaxOfPage();
        if (btnCurPage == btnTPage) {
            btnFPage.setText("" + (indexCurPage - 1));
            btnSPage.setText("" + indexCurPage);
            btnTPage.setText("" + (indexCurPage + 1));
            if (Integer.parseInt(btnTPage.getText()) == maxIndexPage) {
                btnNext.setEnabled(false);
            }
            btnCurPage = btnTPage;
            btnTPage.doClick();
            btnBack.setEnabled(true);
        } else if (btnCurPage == btnSPage) {
            btnSPage.setBackground(new JButton().getBackground());
            btnCurPage = btnTPage;
            btnCurPage.setBackground(Color.YELLOW);
            btnCurPage.doClick();
            btnBack.setEnabled(true);

        } else {
            btnCurPage = btnSPage;
            btnFPage.setBackground(new JButton().getBackground());
            btnCurPage.setBackground(Color.YELLOW);
            btnCurPage.doClick();
            if (Integer.parseInt(btnCurPage.getText()) > 1) {
                btnBack.setEnabled(true);
            }

        }
    }

    public void backPage(JButton btnBack, JButton btnFPage, JButton btnSPage, JButton btnTPage, JButton btnNext, boolean mode) {
        int indexCurPage = Integer.parseInt(btnCurPage.getText());
        if (btnCurPage == btnTPage) {
            btnCurPage = btnSPage;
            btnTPage.setBackground(new JButton().getBackground());
            btnCurPage.setBackground(Color.YELLOW);
            btnCurPage.doClick();
            btnNext.setEnabled(true);
        } else if (btnCurPage == btnSPage) {
            btnCurPage = btnFPage;
            if (Integer.parseInt(btnFPage.getText()) == 1) {
                btnBack.setEnabled(false);
            }
            btnSPage.setBackground(new JButton().getBackground());
            btnCurPage.setBackground(Color.YELLOW);
            btnCurPage.doClick();
            btnNext.setEnabled(true);

        } else {
            if (Integer.parseInt(btnFPage.getText()) == 1) {
                btnBack.setEnabled(false);
            } else {
                btnFPage.setText("" + (indexCurPage - 1));
                btnSPage.setText("" + indexCurPage);
                btnTPage.setText("" + (indexCurPage + 1));
                if (Integer.parseInt(btnFPage.getText()) == 1) {
                    btnBack.setEnabled(false);
                }
            }
            btnFPage.doClick();
            btnNext.setEnabled(true);
        }
    }

    public void btnFPageDoClick(JButton btnBack, JButton btnFPage, JButton btnSPage, JButton btnTPage, JButton btnNext, boolean mode) throws ClassNotFoundException, SQLException {
        if (Integer.parseInt(btnFPage.getText()) > 1) {
            btnBack.setEnabled(true);
        } else {
            btnBack.setEnabled(false);
        }
        if (Integer.parseInt(btnFPage.getText()) < getMaxOfPage()) {
            btnNext.setEnabled(true);
        } else {
            btnNext.setEnabled(false);
        }
        this.setBtnCurPage(btnFPage);
        btnFPage.setBackground(Color.YELLOW);
        btnSPage.setBackground(new JButton().getBackground());
        btnTPage.setBackground(new JButton().getBackground());
        this.pushDataPageToTable(mode);
    }

    public void btnSPageDoClick(JButton btnBack, JButton btnFPage, JButton btnSPage, JButton btnTPage, JButton btnNext, boolean mode) throws ClassNotFoundException, SQLException {
        if (Integer.parseInt(btnSPage.getText()) == getMaxOfPage()) {
            btnNext.setEnabled(false);
        } else {
            btnNext.setEnabled(true);
        }
        btnBack.setEnabled(true);
        this.setBtnCurPage(btnSPage);
        btnSPage.setBackground(Color.YELLOW);
        btnFPage.setBackground(new JButton().getBackground());
        btnTPage.setBackground(new JButton().getBackground());
        this.pushDataPageToTable(mode);
    }

    public void btnTPageDoClick(JButton btnBack, JButton btnFPage, JButton btnSPage, JButton btnTPage, JButton btnNext, boolean mode) throws ClassNotFoundException, SQLException {
        if (Integer.parseInt(btnTPage.getText()) == getMaxOfPage()) {
            btnNext.setEnabled(false);
        } else {
            btnNext.setEnabled(true);
        }
        btnBack.setEnabled(true);
        this.setBtnCurPage(btnTPage);
        btnTPage.setBackground(Color.YELLOW);
        btnFPage.setBackground(new JButton().getBackground());
        btnSPage.setBackground(new JButton().getBackground());
        this.pushDataPageToTable(mode);
    }
}
