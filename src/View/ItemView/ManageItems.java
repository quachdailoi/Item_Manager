/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.ItemView;

import DBService.ItemService;
import DBService.SupplierService;
import EntityPkg.ItemPkg.Item;
import View.ItemView.ItemPageList;
import EntityPkg.SupplierPkg.Supplier;
import View.SupplierView.SupplierPageList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;


/**
 *
 * @author DELL
 */
public class ManageItems extends JPanel {

    JTable tblItem;
    JButton btnDone;
    JLabel lblItemsList;
    JPanel pnlItemDetails;
    JButton btnSearch;
    JButton btnNew;
    JButton btnSave;
    JButton btnDelete;
    JLabel lblCode;
    JLabel lblName;
    JLabel lblSupplier;
    JLabel lblUnit;
    JLabel lblPrice;
    JLabel lblSupplying;
    JTextField txtCode;
    JTextField txtName;
    JTextField txtUnit;
    JTextField txtPrice;
    JComboBox<String> cbxSupplier;
    JCheckBox ckbSuppling;
    JPanel pnlLeft;
    JPanel pnlLeftOfLeft;
    JPanel pnlRightOfLeft;
    JPanel pnlRight;
    JScrollPane scrTable;
    
    JButton btnLangItem;

    JButton btnBack;
    JButton btnF;
    JButton btnS;
    JButton btnT;
    JButton btnNext;
    JPanel pnlPaging;
    

    int[] indexes = {0, 1, 2, 3, 4, 5};
    String[] header = null;

    final int SUCCESS = 1;
    final boolean SEARCHMODE = true;
    final boolean NORMALMODE = false;
    boolean mode = NORMALMODE;
    final int FAIL = 0;
    String curKeySearch = "";
    ItemPageList pageList;
    GridBagConstraints gbc = new GridBagConstraints();
    boolean addNew = true;
    
    ResourceBundle resourceItem;

    public ManageItems(JComboBox<String> cbxSup, ResourceBundle resource, JButton btnLangItem) throws ClassNotFoundException, SQLException {
        this.btnLangItem = btnLangItem;
        this.btnLangItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    changeLanguage();
                } catch (ClassNotFoundException ex) {
                } catch (SQLException ex) {
                }
            }
        });
        this.resourceItem = resource;
        String[] header = {resource.getString("code"), resource.getString("name"), resource.getString("supplier"), resource.getString("unit"), resource.getString("price"), resource.getString("supplying")};
        this.header = header;
        this.setLayout(new FlowLayout());
        tblItem = new JTable();
        tblItem.setRowSelectionAllowed(true);
        tblItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tblItemDoClick();
            }

        });
        pnlLeft = new JPanel();
        pnlLeft.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1, true), resource.getString("itemList"), 1, 2, new Font("Arial", 1, 15), Color.BLUE));
        pnlRight = new JPanel();
        pnlLeft.setPreferredSize(new Dimension(630, 300));
        pnlRight.setPreferredSize(new Dimension(330, 300));
        pnlLeft.setLayout(new FlowLayout(1));
        pnlRight.setLayout(new FlowLayout(1));
        //pnlRight.setBorder(new LineBorder(Color.yellow));
        this.add(pnlLeft);
        this.add(pnlRight);

        scrTable = new JScrollPane(tblItem);
        scrTable.setPreferredSize(new Dimension(620, 210));
        pnlLeft.add(scrTable);

        pnlPaging = new JPanel();
        btnBack = new JButton("<");
        btnF = new JButton("1");
        btnS = new JButton("2");
        btnT = new JButton("3");
        btnNext = new JButton(">");
        btnBack.setPreferredSize(new Dimension(35, 35));
        btnF.setPreferredSize(new Dimension(35, 35));
        btnS.setPreferredSize(new Dimension(35, 35));
        btnT.setPreferredSize(new Dimension(35, 35));
        btnNext.setPreferredSize(new Dimension(35, 35));
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnBackDoClick();
            }
        });
        btnF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    btnFirstDoClick();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    btnSecondDoClick();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    btnThirdDoClick();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnNextDoClick();
            }
        });
        pnlPaging.setPreferredSize(new Dimension(250, 40));
        //pnlPaging.setBorder(new LineBorder(Color.BLACK, 1));
        pnlPaging.setLayout(new FlowLayout(1));

        pageList = new ItemPageList(ItemService.loadData(5, 1), header, indexes, btnBack, btnF, btnS, btnT, btnNext, btnF, tblItem, mode, curKeySearch);
        tblItem.getColumnModel().getColumn(0).setWidth(30);
        tblItem.setRowHeight(tblItem.getRowHeight() + 20);

        pnlLeftOfLeft = new JPanel();
        pnlRightOfLeft = new JPanel();
        pnlLeftOfLeft.setPreferredSize(new Dimension(300, 50));
        pnlRightOfLeft.setPreferredSize(new Dimension(300, 50));
        //pnlLeftOfLeft.setBorder(new LineBorder(Color.yellow));
        //pnlRightOfLeft.setBorder(new LineBorder(Color.yellow));
        pnlLeft.add(pnlLeftOfLeft);
        pnlLeft.add(pnlRightOfLeft);
        btnDone = new JButton(resource.getString("btnDone"));
        btnDone.setPreferredSize(new Dimension(80, 40));
        btnDone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    btnDoneDoClick();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        pnlLeftOfLeft.add(btnDone);
        btnDone.setVisible(false);
        pnlRightOfLeft.add(pnlPaging);
        pnlPaging.add(btnBack);
        pnlPaging.add(btnF);
        pnlPaging.add(btnS);
        pnlPaging.add(btnT);
        pnlPaging.add(btnNext);

        pnlItemDetails = new JPanel(new GridBagLayout());
        pnlItemDetails.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1, true), resource.getString("itemDetails"), 1, 2, new Font("Arial", 1, 12), Color.BLUE));
        pnlItemDetails.setPreferredSize(new Dimension(330, 250));
        pnlRight.add(pnlItemDetails);
        lblCode = new JLabel(resource.getString("lblCode"));
        lblCode.setFont(new Font("Times new Roman", 1, 15));
        lblName = new JLabel(resource.getString("lblName"));
        lblName.setFont(new Font("Times new Roman", 1, 15));
        lblSupplier = new JLabel(resource.getString("lblSupplier"));
        lblSupplier.setFont(new Font("Times new Roman", 1, 15));
        lblUnit = new JLabel(resource.getString("lblUnit"));
        lblUnit.setFont(new Font("Times new Roman", 1, 15));
        lblPrice = new JLabel(resource.getString("lblPrice"));
        lblPrice.setFont(new Font("Times new Roman", 1, 15));
        lblSupplying = new JLabel(resource.getString("lblSupplying"));
        lblSupplying.setFont(new Font("Times new Roman", 1, 16));

        txtCode = new JTextField();
        txtCode.setPreferredSize(new Dimension(150, 30));
        txtName = new JTextField();
        txtName.setPreferredSize(new Dimension(150, 30));
        cbxSupplier = cbxSup;
        cbxSupplier.setPreferredSize(new Dimension(150, 30));
        cbxSupplier.setModel(ItemService.getDataComboBoxModel());

        txtUnit = new JTextField();
        txtUnit.setPreferredSize(new Dimension(150, 30));
        txtPrice = new JTextField();
        txtPrice.setPreferredSize(new Dimension(150, 30));
        ckbSuppling = new JCheckBox();
        ImageIcon ii = new ImageIcon("icon\\search_button.png");
        btnSearch = new JButton(ii);
        btnSearch.setPreferredSize(new Dimension(50, 40));
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    btnSearchDoClick();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlItemDetails.add(lblCode, gbc);
        gbc.gridy = 1;
        pnlItemDetails.add(lblName, gbc);
        gbc.gridy = 2;
        pnlItemDetails.add(lblSupplier, gbc);
        gbc.gridy = 3;
        pnlItemDetails.add(lblUnit, gbc);
        gbc.gridy = 4;
        pnlItemDetails.add(lblPrice, gbc);
        gbc.gridy = 5;
        pnlItemDetails.add(lblSupplying, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlItemDetails.add(txtCode, gbc);
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        pnlItemDetails.add(txtName, gbc);
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        pnlItemDetails.add(cbxSupplier, gbc);
        gbc.gridy = 3;
        pnlItemDetails.add(txtUnit, gbc);
        gbc.gridy = 4;
        pnlItemDetails.add(txtPrice, gbc);
        gbc.gridy = 5;
        pnlItemDetails.add(ckbSuppling, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        pnlItemDetails.add(btnSearch, gbc);

        btnSave = new JButton(resource.getString("btnSave"));
        btnNew = new JButton(resource.getString("btnNew"));
        btnDelete = new JButton(resource.getString("btnDelete"));
        btnSave.setPreferredSize(new Dimension(90, 30));
        btnNew.setPreferredSize(new Dimension(90, 30));
        btnDelete.setPreferredSize(new Dimension(90, 30));
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    btnSaveDoClick();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnNewDoClick();
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    btnDeleteDoClick();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        pnlRight.add(btnSave);
        pnlRight.add(btnNew);
        pnlRight.add(btnDelete);
    }

    public void setResource(ResourceBundle resource) {
        this.resourceItem = resource;
    }

    
    
    public void changeLanguage() throws ClassNotFoundException, SQLException{
        pnlLeft.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1, true), resourceItem.getString("itemList"), 1, 2, new Font("Arial", 1, 15), Color.BLUE));
        btnDone = new JButton(resourceItem.getString("btnDone"));
        pnlItemDetails.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1, true), resourceItem.getString("itemDetails"), 1, 2, new Font("Arial", 1, 12), Color.BLUE));
        lblCode.setText(resourceItem.getString("lblCode"));
        lblName.setText(resourceItem.getString("lblName"));
        lblSupplier.setText(resourceItem.getString("lblSupplier"));
        lblUnit.setText(resourceItem.getString("lblUnit"));
        lblPrice.setText(resourceItem.getString("lblPrice"));
        lblSupplying.setText(resourceItem.getString("lblSupplying"));
        btnSave.setText(resourceItem.getString("btnSave"));
        btnNew.setText(resourceItem.getString("btnNew"));
        btnDelete.setText(resourceItem.getString("btnDelete"));
        String[] headerNew = {resourceItem.getString("code"), resourceItem.getString("name"), resourceItem.getString("supplier"), resourceItem.getString("unit"), resourceItem.getString("price"), resourceItem.getString("supplying")};
        this.header = headerNew;
        System.out.println(pageList.getBtnCurPage().getText());
        pageList = new ItemPageList(ItemService.loadData(5, Integer.parseInt(pageList.btnCurPage.getText())), header, indexes, btnBack, btnF, btnS, btnT, btnNext, pageList.btnCurPage, tblItem, mode, curKeySearch);
    }
    
    public Item checkReadyData() throws ClassNotFoundException, SQLException {
        Item item = null;
        String code = txtCode.getText();
        String name = txtName.getText();
        String supplier = (String) cbxSupplier.getSelectedItem();
        String unit = txtUnit.getText();
        String priceStr = txtPrice.getText();
        boolean supplying = ckbSuppling.isSelected();
        if (code.isEmpty()) {
            JOptionPane.showMessageDialog(null, resourceItem.getString("err1"));
        } else if (addNew && ItemService.getItemByCode(code) != null) {
            JOptionPane.showMessageDialog(null, resourceItem.getString("err2"));
        } else if (name.isEmpty()) {
            JOptionPane.showMessageDialog(null, resourceItem.getString("err3"));
        } else if (unit.isEmpty()) {
            JOptionPane.showMessageDialog(null, resourceItem.getString("err4"));
        } else if (priceStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, resourceItem.getString("err5"));
        } else if (!priceStr.matches("[\\d]+")) {
            JOptionPane.showMessageDialog(null, resourceItem.getString("err6"));
        } else {
            item = new Item(code, name, ItemService.getSupCodeBySupName(supplier), unit, Integer.parseInt(priceStr), supplying);
        }
        return item;
    }
    
    public void btnDoneDoClick() throws ClassNotFoundException, SQLException{
        int curPageNum = Integer.parseInt(pageList.btnCurPage.getText());
        mode = NORMALMODE;
        pageList = new ItemPageList(ItemService.loadData(5, curPageNum), header, indexes, btnBack, btnF, btnS, btnT, btnNext, pageList.btnCurPage, tblItem, mode, curKeySearch);
        tblItem.updateUI();
        btnDone.setVisible(false);
        btnSearch.setEnabled(true);
        btnNewDoClick();
    }
    
    public void btnSearchDoClick() throws ClassNotFoundException, SQLException{
        Vector<Item> dataFound = new Vector();
        String partOfName = "";
        if(mode == SEARCHMODE) partOfName = curKeySearch;
        else partOfName = txtName.getText();
        if (partOfName.isEmpty()) {
            JOptionPane.showMessageDialog(null, resourceItem.getString("err7"));
        } else {
            try {
                dataFound = ItemService.searchItem(partOfName, 5, 1);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (dataFound.isEmpty()) {
                JOptionPane.showMessageDialog(null, resourceItem.getString("err8"));
            } else {
                btnDone.setVisible(true);
                btnSearch.setEnabled(false);
                curKeySearch = partOfName;
                mode = SEARCHMODE;
                pageList = new ItemPageList(dataFound, header, indexes, btnBack, btnF, btnS, btnT, btnNext, pageList.btnCurPage, tblItem, mode, curKeySearch);
            }
        }
        tblItem.updateUI();
        for (Item item : dataFound) {
            System.out.println(item.getName());
        }
        
    }

    public void btnSaveDoClick() throws ClassNotFoundException, SQLException {
        int curPageNumber = Integer.parseInt(pageList.getBtnCurPage().getText());
        System.out.println(addNew);
        if (addNew) {
            Item itemAdd = checkReadyData();
            if (itemAdd != null) {
                ItemService.insertItem(itemAdd);
                JOptionPane.showMessageDialog(null, resourceItem.getString("err9"));
                btnNewDoClick();
                pageList = new ItemPageList(ItemService.loadData(5, curPageNumber), header, indexes, btnBack, btnF, btnS, btnT, btnNext, pageList.btnCurPage, tblItem, mode, curKeySearch);
                if(mode == SEARCHMODE) btnSearchDoClick();
            }
        } else {
            Item itemUpdate = checkReadyData();
            if (itemUpdate != null) {
                ItemService.updateItem(itemUpdate);
                JOptionPane.showMessageDialog(null, resourceItem.getString("err10"));
                btnNewDoClick();
                pageList.getModel().setDataSetToPage(ItemService.loadData(5, curPageNumber));
                if(mode == SEARCHMODE) btnSearchDoClick();
            }
        }
        tblItem.updateUI();
    }

    public void btnNewDoClick() {
        addNew = true;
        txtCode.setText("");
        txtName.setText("");
        cbxSupplier.setSelectedIndex(0);
        txtUnit.setText("");
        txtPrice.setText("");
        ckbSuppling.setSelected(false);
    }

    public void btnDeleteDoClick() throws ClassNotFoundException, SQLException {
        Item itemR = checkReadyData();
        int curPageNumber = Integer.parseInt(pageList.getBtnCurPage().getText());
        if (itemR != null) {
            int oldMaxOfPage = ItemService.getMaxOfPageinSource(5);
            if (ItemService.getItemByCode(itemR.getCode()) != null) {
                ItemService.deleteItem(itemR);
                JOptionPane.showMessageDialog(null, resourceItem.getString("err11"));
                btnNewDoClick();
                pageList = new ItemPageList(ItemService.loadData(5, curPageNumber), header, indexes, btnBack, btnF, btnS, btnT, btnNext, pageList.btnCurPage, tblItem, oldMaxOfPage, mode, curKeySearch);
                if(mode == SEARCHMODE) btnSearchDoClick();
                tblItem.updateUI();
            } else {
                JOptionPane.showMessageDialog(null, resourceItem.getString("err12"));
            }
        }
    }

    public void setSelectedInComboBox(String supName) {
        for (int i = 0; i < cbxSupplier.getItemCount(); i++) {
            if (cbxSupplier.getItemAt(i).equals(supName)) {
                cbxSupplier.setSelectedIndex(i);
                System.out.println(i);
            }

        }
    }

    public void btnBackDoClick() {
        tblItem.clearSelection();
        pageList.backPage(btnBack, btnF, btnS, btnT, btnNext, mode);
        tblItem.updateUI();
    }

    public void btnFirstDoClick() throws ClassNotFoundException, SQLException {
        tblItem.clearSelection();
        pageList.btnFPageDoClick(btnBack, btnF, btnS, btnT, btnNext, mode);
        tblItem.updateUI();
    }

    public void btnSecondDoClick() throws ClassNotFoundException, SQLException {
        tblItem.clearSelection();
        pageList.btnSPageDoClick(btnBack, btnF, btnS, btnT, btnNext, mode);
        tblItem.updateUI();
    }

    public void btnThirdDoClick() throws ClassNotFoundException, SQLException {
        tblItem.clearSelection();
        pageList.btnTPageDoClick(btnBack, btnF, btnS, btnT, btnNext, mode);
        tblItem.updateUI();
    }

    public void btnNextDoClick() {
        tblItem.clearSelection();
        pageList.nextPage(btnBack, btnF, btnS, btnT, btnNext, mode);
        tblItem.updateUI();
    }

    public void tblItemDoClick() {
        int rowIndex = tblItem.getSelectedRow();
        Item item = pageList.getModel().getDataSetToPage().get(rowIndex);
        txtCode.setText(item.getCode());
        txtName.setText(item.getName());
        setSelectedInComboBox(ItemService.getSupNameBySupCode(item.getSupplier()));
        txtUnit.setText(item.getUnit());
        txtPrice.setText(item.getPrice() + "");
        ckbSuppling.setSelected(item.isSupply());
        addNew = false;
    }

}
