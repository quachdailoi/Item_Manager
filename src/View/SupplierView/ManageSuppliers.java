/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.SupplierView;

import DBService.ItemService;
import DBService.SupplierService;
import EntityPkg.SupplierPkg.Supplier;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author DELL
 */
public class ManageSuppliers extends JPanel{
    JTable tblSupplier;
    JPanel pnlSupplierDetails;
    JButton btnNew;
    JButton btnSave;
    JButton btnDelete;
    JLabel lblCode;
    JLabel lblName;
    JLabel lblAddress;
    JLabel lblColloborating;
    JTextField txtCode;
    JTextField txtName;
    JTextField txtAddress;
    JCheckBox ckbColloborating;
    JPanel pnlLeft;
    JPanel pnlLeftOfLeft;
    JPanel pnlRightOfLeft;
    JPanel pnlRight;
    JScrollPane scrTable;
    
    
    JButton btnBack;
    JButton btnF;
    JButton btnS;
    JButton btnT;
    JButton btnNext;
    JPanel pnlPaging;

    
    int[] indexes = {0,1,2,3};
    String[] header = {"Code", "Name", "Address", "Colloborating"};
    
    final int SUCCESS = 1;
    final int FAIL = 0;
    SupplierPageList pageList;
    GridBagConstraints gbc = new GridBagConstraints();
    boolean addNew = true;
    JComboBox<String> cbxSup;
    
    JButton btnLangSup;
    ResourceBundle resourceSup;
    
    public ManageSuppliers(JComboBox<String> cbxSup, ResourceBundle resourceSup, JButton btnLangSup){
        this.btnLangSup = btnLangSup;
        this.resourceSup = resourceSup;
        
        this.btnLangSup = btnLangSup;
        this.btnLangSup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeLanguage();
            }
        });
        
        this.resourceSup = resourceSup;
        String[] header = {resourceSup.getString("code"), resourceSup.getString("name"), resourceSup.getString("address"), resourceSup.getString("colloborating")};
        this.header = header;
        
        this.cbxSup = cbxSup;
        this.setLayout(new FlowLayout());
        tblSupplier = new JTable();
        tblSupplier.setRowSelectionAllowed(true);
        tblSupplier.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tblSupplierDoClick();
            }
            
        });
        pnlLeft = new JPanel();
        pnlLeft.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1, true), resourceSup.getString("supList"), 1, 2, new Font("Arial", 1, 15), Color.BLUE));
        pnlRight = new JPanel();
        pnlLeft.setPreferredSize(new Dimension(630, 300));
        pnlRight.setPreferredSize(new Dimension(330, 300));
        pnlLeft.setLayout(new FlowLayout(1));
        pnlRight.setLayout(new FlowLayout(1));
        //pnlRight.setBorder(new LineBorder(Color.yellow));
        this.add(pnlLeft);
        this.add(pnlRight);
        
        scrTable = new JScrollPane(tblSupplier);
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
                btnFirstDoClick();
            }
        });
        btnS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSecondDoClick();
            }
        });
        btnT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnThirdDoClick();
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
        
        
        pageList = new SupplierPageList(SupplierService.loadData(5, 1), header, indexes, btnBack, btnF, btnS, btnT, btnNext, btnF, tblSupplier);
        tblSupplier.getColumnModel().getColumn(0).setWidth(30);
        tblSupplier.setRowHeight(tblSupplier.getRowHeight()+20);
        
        pnlLeftOfLeft = new JPanel();
        pnlRightOfLeft = new JPanel();
        pnlLeftOfLeft.setPreferredSize(new Dimension(300, 50));
        pnlRightOfLeft.setPreferredSize(new Dimension(300,50));
        //pnlLeftOfLeft.setBorder(new LineBorder(Color.yellow));
        //pnlRightOfLeft.setBorder(new LineBorder(Color.yellow));
        pnlLeft.add(pnlLeftOfLeft);
        pnlLeft.add(pnlRightOfLeft);
        
        pnlRightOfLeft.add(pnlPaging);
        pnlPaging.add(btnBack);
        pnlPaging.add(btnF);
        pnlPaging.add(btnS);
        pnlPaging.add(btnT);
        pnlPaging.add(btnNext);
        
        pnlSupplierDetails = new JPanel(new GridBagLayout());
        pnlSupplierDetails.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1, true), resourceSup.getString("supDetails"), 1, 2, new Font("Arial", 1, 12), Color.BLUE));
        pnlSupplierDetails.setPreferredSize(new Dimension(330, 250));
        pnlRight.add(pnlSupplierDetails);
        lblCode = new JLabel(resourceSup.getString("lblCode"));
        lblCode.setFont(new Font("Times new Roman", 1, 15));
        lblName = new JLabel(resourceSup.getString("lblName"));
        lblName.setFont(new Font("Times new Roman", 1, 15));
        lblAddress = new JLabel(resourceSup.getString("lblAddress"));
        lblAddress.setFont(new Font("Times new Roman", 1, 15));
        lblColloborating = new JLabel(resourceSup.getString("lblColloborating"));
        lblColloborating.setFont(new Font("Times new Roman", 1, 15));
        
        txtCode = new JTextField();
        txtCode.setPreferredSize(new Dimension(150, 30));
        txtName = new JTextField();
        txtName.setPreferredSize(new Dimension(150, 30));
        txtAddress = new JTextField();
        txtAddress.setPreferredSize(new Dimension(150, 30));
        ckbColloborating = new JCheckBox();
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlSupplierDetails.add(lblCode, gbc);
        gbc.gridy = 1;
        pnlSupplierDetails.add(lblName, gbc);
        gbc.gridy = 2;
        pnlSupplierDetails.add(lblAddress, gbc);
        gbc.gridy = 3;
        pnlSupplierDetails.add(lblColloborating, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlSupplierDetails.add(txtCode, gbc);
        gbc.gridy = 1;
        pnlSupplierDetails.add(txtName, gbc);
        gbc.gridy = 2;
        pnlSupplierDetails.add(txtAddress, gbc);
        gbc.gridy = 3;
        pnlSupplierDetails.add(ckbColloborating, gbc);
        
        btnSave = new JButton(resourceSup.getString("btnSave"));
        btnNew = new JButton(resourceSup.getString("btnNew"));
        btnDelete = new JButton(resourceSup.getString("btnDelete"));
        btnSave.setPreferredSize(new Dimension(90, 30));
        btnNew.setPreferredSize(new Dimension(90, 30));
        btnDelete.setPreferredSize(new Dimension(90, 30));
        pnlRight.add(btnSave);
        pnlRight.add(btnNew);
        pnlRight.add(btnDelete);
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
    }

    public void setResourceSup(ResourceBundle resourceSup) {
        this.resourceSup = resourceSup;
    }
    
    
    public void changeLanguage(){
        pnlLeft.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1, true), resourceSup.getString("supList"), 1, 2, new Font("Arial", 1, 15), Color.BLUE));
        pnlSupplierDetails.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1, true), resourceSup.getString("supDetails"), 1, 2, new Font("Arial", 1, 12), Color.BLUE));
        lblCode.setText(resourceSup.getString("lblCode"));
        lblName.setText(resourceSup.getString("lblName"));
        lblAddress.setText(resourceSup.getString("lblAddress"));
        lblColloborating.setText(resourceSup.getString("lblColloborating"));

        btnSave.setText(resourceSup.getString("btnSave"));
        btnNew.setText(resourceSup.getString("btnNew"));
        btnDelete.setText(resourceSup.getString("btnDelete"));
        String[] headerNew = {resourceSup.getString("code"), resourceSup.getString("name"), resourceSup.getString("address"), resourceSup.getString("colloborating")};
        this.header = headerNew;
        System.out.println(pageList.getBtnCurPage().getText());
        pageList = new SupplierPageList(SupplierService.loadData(5, Integer.parseInt(pageList.btnCurPage.getText())), header, indexes, btnBack, btnF, btnS, btnT, btnNext, pageList.btnCurPage, tblSupplier);
    }
    
    public Supplier checkReadyData() throws ClassNotFoundException, SQLException{
        Supplier sup = null;
        String code = txtCode.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        boolean colloborating = ckbColloborating.isSelected();
        if(code.isEmpty()) JOptionPane.showMessageDialog(null, resourceSup.getString("err1"));
        else if(addNew && SupplierService.getSupplierByCode(code) != null) JOptionPane.showMessageDialog(null, resourceSup.getString("err2"));
        else if(name.isEmpty()) JOptionPane.showMessageDialog(null, resourceSup.getString("err3"));
        else if(address.isEmpty()) JOptionPane.showMessageDialog(null, resourceSup.getString("err4"));
        else{
            sup = new Supplier(code, name, address, colloborating);
        }
        return sup;
    }
    
    public void btnSaveDoClick() throws ClassNotFoundException, SQLException{
        int oldMaxOfPage = SupplierService.getMaxOfPageinSource(5);
        int curPageNumber = Integer.parseInt(pageList.getBtnCurPage().getText());
        System.out.println(addNew);
        if(addNew){
            Supplier supAdd = checkReadyData();
            if(supAdd != null){
                SupplierService.insertSup(supAdd);
                JOptionPane.showMessageDialog(null, resourceSup.getString("err5"));
                btnNewDoClick();
                this.cbxSup.setModel(ItemService.getDataComboBoxModel());
                pageList = new SupplierPageList(SupplierService.loadData(5, curPageNumber), header, indexes, btnBack, btnF, btnS, btnT, btnNext, pageList.btnCurPage, tblSupplier, oldMaxOfPage);
            }
        }
        else{
            Supplier supUpdate = checkReadyData();
            if(supUpdate != null){
                SupplierService.updateSup(supUpdate);
                JOptionPane.showMessageDialog(null, resourceSup.getString("err6"));
                btnNewDoClick();
                this.cbxSup.setModel(ItemService.getDataComboBoxModel());
                pageList.getModel().setDataSetToPage(SupplierService.loadData(5, curPageNumber));
            }
        }
        tblSupplier.updateUI();
    }
        
    public void btnNewDoClick(){
        addNew = true;
        txtCode.setText("");
        txtName.setText("");
        txtAddress.setText("");
        ckbColloborating.setSelected(false);
    }
    
    public void btnDeleteDoClick() throws ClassNotFoundException, SQLException{
        Supplier supR = checkReadyData();
        int curPageNumber = Integer.parseInt(pageList.getBtnCurPage().getText());
        if(supR != null){
            if(SupplierService.checkRefItemToSup(supR.getCode())) JOptionPane.showMessageDialog(null, resourceSup.getString("err7"));
            else{
                int oldMaxOfPage = SupplierService.getMaxOfPageinSource(5);
                if(SupplierService.getSupplierByCode(supR.getCode()) != null){
                    SupplierService.deleteSup(supR);
                    JOptionPane.showMessageDialog(null, resourceSup.getString("err8"));
                    btnNewDoClick();
                    this.cbxSup.setModel(ItemService.getDataComboBoxModel());
                    pageList = new SupplierPageList(SupplierService.loadData(5, curPageNumber), header, indexes, btnBack, btnF, btnS, btnT, btnNext, pageList.btnCurPage, tblSupplier, oldMaxOfPage);
                    tblSupplier.updateUI();
                }
                else{
                    JOptionPane.showMessageDialog(null, resourceSup.getString("err9"));
                }
            }
        };
    }                                         
    
    public void btnBackDoClick(){
        tblSupplier.clearSelection();
        pageList.backPage(btnBack, btnF, btnS, btnT, btnNext);
        tblSupplier.updateUI();
    }
    public void btnFirstDoClick(){
        tblSupplier.clearSelection();
        pageList.btnFPageDoClick(btnBack, btnF, btnS, btnT, btnNext);
        tblSupplier.updateUI();
    }
    public void btnSecondDoClick(){
        tblSupplier.clearSelection();
        pageList.btnSPageDoClick(btnBack, btnF, btnS, btnT, btnNext);
        tblSupplier.updateUI();
    }
    public void btnThirdDoClick(){
        tblSupplier.clearSelection();
        pageList.btnTPageDoClick(btnBack, btnF, btnS, btnT, btnNext);
        tblSupplier.updateUI();
    }
    public void btnNextDoClick(){
        tblSupplier.clearSelection();
        pageList.nextPage(btnBack, btnF, btnS, btnT, btnNext);
        tblSupplier.updateUI();
    }
    
    public void tblSupplierDoClick(){
        int rowIndex = tblSupplier.getSelectedRow();
        Supplier sup = pageList.getModel().getDataSetToPage().get(rowIndex);
        txtCode.setText(sup.getCode());
        txtName.setText(sup.getName());
        txtAddress.setText(sup.getAddress());
        ckbColloborating.setSelected(sup.isColloborating());
        addNew = false;
    }
}
