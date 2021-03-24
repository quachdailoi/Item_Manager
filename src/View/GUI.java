/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import View.ItemView.ManageItems;
import View.SupplierView.ManageSuppliers;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.RescaleOp;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.border.LineBorder;

/**
 *
 * @author DELL
 */
public class GUI extends JFrame{
    JTabbedPane tabPane;
    ManageItems mngItem;
    ManageSuppliers mngSupplier;
    JComboBox<String> cbxSup;
    
    Locale curLocaleItem;
    Locale curLocaleSup;
    ResourceBundle resourceItem;
    ResourceBundle resourceSup;
    String country;
    String language;
    JButton btnLang;
    JButton btnLangItem;
    JButton btnLangSup;
    
    public GUI(String country, String language) {
        this.language = language;
        this.country = country;
        try {
            initComponents();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void initComponents() throws ClassNotFoundException, SQLException{
        btnLangItem = new JButton();
        btnLangSup = new JButton();
        btnLang = new JButton("Vietnamese");
        btnLang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnChangeLanguage();
            }
        });
        btnLang.setPreferredSize(new Dimension(200, 30));
        this.add(btnLang);
        curLocaleItem = new Locale(language, country);
        curLocaleSup = new Locale(language, country);
        String baseItem = "View.Internationalization/ResourcesItem_en_US";
        String baseSup = "View.Internationalization/ResourcesSup_en_US";
        resourceItem = ResourceBundle.getBundle(baseItem, curLocaleItem);
        resourceSup = ResourceBundle.getBundle(baseSup, curLocaleSup);
        
        this.setTitle(resourceItem.getString("frameTittle"));
        
        cbxSup = new JComboBox<>();
        this.setLayout(new FlowLayout(3));
        tabPane = new JTabbedPane();
        tabPane.setPreferredSize(new Dimension(975, 340));
        //tabPane.setBorder(new LineBorder(Color.BLACK, 1));
        this.add(tabPane);
        mngSupplier = new ManageSuppliers(cbxSup, resourceSup, btnLangSup);
        tabPane.addTab("Manage Suppliers", mngSupplier);
        mngItem = new ManageItems(cbxSup, resourceItem, btnLangItem);
        tabPane.addTab("Manage Items", mngItem);
    }
    
    public void btnChangeLanguage(){
        String lang = btnLang.getText();
        if(lang.equals("Vietnamese")){
            btnLang.setText("English");
            String baseItem = "View.Internationalization/ResourcesItem_vi_VN";
            String baseSup = "View.Internationalization/ResourcesSup_vi_VN";
            
            resourceItem = ResourceBundle.getBundle(baseItem, curLocaleItem);
            resourceSup = ResourceBundle.getBundle(baseSup, curLocaleSup);
        }
        else{
            btnLang.setText("Vietnamese");
            String baseItem = "View.Internationalization/ResourcesItem_en_US";
            String baseSup = "View.Internationalization/ResourcesSup_en_US";
            resourceSup = ResourceBundle.getBundle(baseSup, curLocaleSup);
            resourceItem = ResourceBundle.getBundle(baseItem, curLocaleItem);
        }
        this.setTitle(resourceItem.getString("frameTittle"));
        tabPane.setTitleAt(0, resourceItem.getString("tabTittleSupplier"));
        tabPane.setTitleAt(1, resourceItem.getString("tabTittleItem"));
        mngItem.setResource(resourceItem);
        mngSupplier.setResourceSup(resourceSup);
        btnLangItem.doClick();
        btnLangSup.doClick();
    }
    
    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        GUI g = new GUI("en", "US");
        g.setSize(1000, 430);
        g.setDefaultCloseOperation(EXIT_ON_CLOSE);
        g.setLocationRelativeTo(null);
        g.setVisible(true);
    }
}
