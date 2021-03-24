/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBService;

import EntityPkg.ItemPkg.Item;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author DELL
 */
public class ItemService {

    static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static String url = "jdbc:sqlserver://MSI\\SQLEXPRESS:1433; databaseName=ItemDB;" + "user=sa; password=olivier";

    public static Connection openConnection() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        return DriverManager.getConnection(url);
    }

    static public Vector<Item> loadData(int numRowOnceLoad, int atPage) {
        /*Vector<Item> listItem = new Vector<>();
        int fromRow = numRowOnceLoad * (atPage - 1) + 1;
        int toRow = atPage * numRowOnceLoad;
        int curRow = 0;
        try (
                Connection con = openConnection();
                Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery("SELECT * FROM ITEMS ORDER BY itemName DESC");
            while (rs.next()) {
                curRow++;
                if (curRow >= fromRow && curRow <= toRow) {
                    String code = rs.getString("itemCode").trim();
                    String name = rs.getString("itemName").trim();
                    String supplier = rs.getString("supCode").trim();
                    String unit = rs.getString("unit").trim();
                    int price = rs.getInt("price");
                    boolean supplying = rs.getBoolean("supplying");
                    Item item = new Item(code, name, supplier, unit, price, supplying);
                    listItem.add(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listItem;*/

        Vector<Item> listItem = new Vector<>();
        int fromRow = numRowOnceLoad * (atPage - 1) + 1;
        int toRow = atPage * numRowOnceLoad;
        try (
                Connection con = openConnection();
                Statement stm = con.createStatement()) {
                ResultSet rs = stm.executeQuery("SELECT * FROM ( SELECT ROW_NUMBER() OVER (ORDER BY itemCode) No, * FROM Items) anything WHERE No BETWEEN " + fromRow + " AND " + toRow);
            while (rs.next()) {
                String code = rs.getString("itemCode").trim();
                String name = rs.getString("itemName").trim();
                String supplier = rs.getString("supCode").trim();
                String unit = rs.getString("unit").trim();
                int price = rs.getInt("price");
                boolean supplying = rs.getBoolean("supplying");
                Item item = new Item(code, name, supplier, unit, price, supplying);
                listItem.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listItem;
    }

    static public String getSupNameBySupCode(String supCode) {
        String supName = "";
        try (
                Connection con = openConnection();
                Statement preSta = con.createStatement()) {
            ResultSet rs = preSta.executeQuery("SELECT * FROM SUPPLIERS");
            while (rs.next()) {
                if (rs.getString("supCode").equals(supCode)) {
                    return rs.getString("supName");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return supName;
    }

    static public String getSupCodeBySupName(String supName) {
        String supCode = "";
        try (
                Connection con = openConnection();
                Statement preSta = con.createStatement()) {
            ResultSet rs = preSta.executeQuery("SELECT * FROM SUPPLIERS");
            while (rs.next()) {
                if (rs.getString("supName").equals(supName)) {
                    return rs.getString("supCode");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return supCode;
    }

    static public Item getItemByCode(String code) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM ITEMS WHERE ITEMCODE = ?";
        try (
                Connection c = openConnection();
                PreparedStatement preSta = c.prepareStatement(sql)) {
            preSta.setString(1, code);
            ResultSet rs = preSta.executeQuery();
            if (rs.next()) {
                return new Item(rs.getString("itemCode"), rs.getString("itemName"), rs.getString("supCode"), rs.getString("unit"), rs.getInt("price"), rs.getBoolean("supplying"));
            }
        }
        return null;
    }

    static public int getMaxOfRowInSource() {
        int numRow = 0;
        try (
                Connection con = openConnection();
                Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery("SELECT COUNT(*) as numRow FROM ITEMS");
            if(rs.next()){
                numRow = rs.getInt("numRow");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return numRow;
    }

    static public int getMaxOfPageinSource(int rowOfAPage) {
        int numRow = 0;
        try (
                Connection con = openConnection();
                Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery("SELECT COUNT(*) as numRow FROM ITEMS");
            if(rs.next()){
                numRow = rs.getInt("numRow");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (numRow % rowOfAPage == 0) ? numRow / rowOfAPage : numRow / rowOfAPage + 1;
    }

    static public int getMaxOfPageinSearch(int rowOfAPage, String partOfName) {
        int numRow = 0;
        String sql = "SELECT COUNT(*) as numRow FROM ITEMS WHERE ITEMNAME LIKE ?";
        try (
                Connection con = openConnection();
                PreparedStatement preStm = con.prepareStatement(sql)) {
            preStm.setString(1, "%" + partOfName + "%");
            ResultSet rs = preStm.executeQuery();
            if(rs.next()){
                numRow = rs.getInt("numRow");
            }

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return (numRow % rowOfAPage == 0) ? numRow / rowOfAPage : numRow / rowOfAPage + 1;
    }

    static public int getMaxOfRowInSearch(String partOfName) {
        int numRow = 0;
        String sql = "SELECT COUNT(*) as numRow FROM ITEMS WHERE itemName like ?";
        try (
                Connection con = openConnection();
                PreparedStatement preStm = con.prepareStatement(sql)) {
            preStm.setString(1, "%" + partOfName + "%");
            ResultSet rs = preStm.executeQuery();
            if(rs.next())
                numRow = rs.getInt("numRow");

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return numRow;
    }

    synchronized static public DefaultComboBoxModel<String> getDataComboBoxModel() {
        DefaultComboBoxModel<String> cbxModel = new DefaultComboBoxModel<>();
        String supName;
        try (
                Connection con = openConnection();
                Statement preSta = con.createStatement()) {
            ResultSet rs = preSta.executeQuery("SELECT * FROM SUPPLIERS");
            while (rs.next()) {
                supName = rs.getString("supName");
                cbxModel.addElement(supName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cbxModel;
    }

    static public boolean haveChangeSup(DefaultComboBoxModel<String> cbxModel) {
        DefaultComboBoxModel<String> curModel = getDataComboBoxModel();
        if (curModel.getSize() != cbxModel.getSize()) {
            return true;
        }
        for (int i = 0; i < cbxModel.getSize(); i++) {
            if (cbxModel.getElementAt(i) != curModel.getElementAt(i)) {
                return true;
            }
        }
        return false;
    }

    static public int insertItem(Item item) throws ClassNotFoundException, SQLException {
        String sql = "INSERT INTO ITEMS VALUES(?, ?, ?, ?, ?, ?)";
        try (
                Connection con = openConnection();
                PreparedStatement preSta = con.prepareStatement(sql)) {
            preSta.setString(1, item.getCode());
            preSta.setString(2, item.getName());
            preSta.setString(3, item.getSupplier());
            preSta.setString(4, item.getUnit());
            preSta.setInt(5, item.getPrice());
            preSta.setBoolean(6, item.isSupply());
            System.out.println(item.toString());
            return preSta.executeUpdate();
        }
    }

    static public int updateItem(Item item) throws ClassNotFoundException, SQLException {
        String sql = "UPDATE ITEMS SET ITEMNAME = ?, SUPCODE = ?, UNIT = ?, PRICE = ?, SUPPLYING = ? WHERE ITEMCODE = ?";
        try (
                Connection con = openConnection();
                PreparedStatement preSta = con.prepareStatement(sql)) {
            preSta.setString(1, item.getName());
            preSta.setString(2, item.getSupplier());
            preSta.setString(3, item.getUnit());
            preSta.setInt(4, item.getPrice());
            preSta.setBoolean(5, item.isSupply());
            preSta.setString(6, item.getCode());
            return preSta.executeUpdate();
        }
    }

    static public int deleteItem(Item item) throws ClassNotFoundException, SQLException {
        String sql = "DELETE FROM ITEMS WHERE ITEMCODE = ?";
        try (
                Connection c = openConnection();
                PreparedStatement preSta = c.prepareStatement(sql)) {
            preSta.setString(1, item.getCode());
            return preSta.executeUpdate();
        }
    }

    public static Vector<Item> searchItem(String keyName, int numRowOnceLoad, int atPage) throws ClassNotFoundException, SQLException {
        Vector<Item> listFound = new Vector<>();
        String sql = "SELECT * FROM ITEMS WHERE ITEMNAME LIKE ?";
        int fromRow = numRowOnceLoad * (atPage - 1) + 1;
        int toRow = atPage * numRowOnceLoad;
        int curRow = 0;
        try (
                Connection con = openConnection();
                PreparedStatement preStm = con.prepareStatement(sql)) {
            preStm.setString(1, "%" + keyName + "%");
            ResultSet rs = preStm.executeQuery();
            while (rs.next()) {
                curRow++;
                if (curRow >= fromRow && curRow <= toRow) {
                    Item item = new Item(rs.getString("itemCode"), rs.getString("itemName"), rs.getString("supCode"), rs.getString("unit"), rs.getInt("price"), rs.getBoolean("supplying"));
                    listFound.add(item);
                }
            }
            return listFound;
        }
    }
}
