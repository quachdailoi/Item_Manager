/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBService;

import static DBService.ItemService.driver;
import static DBService.ItemService.openConnection;
import EntityPkg.ItemPkg.Item;
import EntityPkg.SupplierPkg.Supplier;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 *
 * @author DELL
 */
public class SupplierService {

    static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static String url = "jdbc:sqlserver://MSI\\SQLEXPRESS:1433; databaseName=ItemDB;" + "user=sa; password=olivier";

    public static Connection openConnection() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        return DriverManager.getConnection(url);
    }

    static public Vector<Supplier> loadData(int numRowOnceLoad, int atPage) {
        Vector<Supplier> listSup = new Vector<>();
        int fromRow = numRowOnceLoad * (atPage - 1) + 1;
        int toRow = atPage * numRowOnceLoad;
        int curRow = 0;
        try (
                Connection con = openConnection();
                Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery("SELECT * FROM ( SELECT ROW_NUMBER() OVER (ORDER BY supCode) No, * FROM SUPPLIERS) anything WHERE No BETWEEN " + fromRow + " AND " + toRow);
            while (rs.next()) {
                String code = rs.getString("supCode").trim();
                String name = rs.getString("supName").trim();
                String address = rs.getString("address").trim();
                boolean colloborating = rs.getBoolean("colloborating");
                Supplier sup = new Supplier(code, name, address, colloborating);
                listSup.add(sup);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSup;
    }

    static public int getMaxOfRowInSource() {
        int numRow = 0;
        try (
                Connection con = openConnection();
                Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery("SELECT COUNT(*) as numRow FROM SUPPLIERS");
            if (rs.next()) {
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
            ResultSet rs = stm.executeQuery("SELECT COUNT(*) as numRow FROM SUPPLIERS");
            if (rs.next()) {
                numRow = rs.getInt("numRow");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (numRow % rowOfAPage == 0) ? numRow / rowOfAPage : numRow / rowOfAPage + 1;
    }

    static public Supplier getSupplierByCode(String code) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM SUPPLIERS WHERE SUPCODE = ?";
        try (
                Connection c = openConnection();
                PreparedStatement preSta = c.prepareStatement(sql)) {
            preSta.setString(1, code);
            ResultSet rs = preSta.executeQuery();
            if (rs.next()) {
                return new Supplier(rs.getString("supCode"), rs.getString("supName"), rs.getString("Address"), rs.getBoolean("colloborating"));
            }
        }
        return null;
    }

    static public int insertSup(Supplier sup) throws ClassNotFoundException, SQLException {
        String sql = "INSERT INTO SUPPLIERS VALUES(?, ?, ?, ?)";
        try (
                Connection con = openConnection();
                PreparedStatement preSta = con.prepareStatement(sql)) {
            preSta.setString(1, sup.getCode());
            preSta.setString(2, sup.getName());
            preSta.setString(3, sup.getAddress());
            preSta.setBoolean(4, sup.isColloborating());
            return preSta.executeUpdate();
        }
    }

    static public int updateSup(Supplier sup) throws ClassNotFoundException, SQLException {
        String sql = "UPDATE SUPPLIERS SET SUPNAME = ?, ADDRESS = ?, COLLOBORATING = ? WHERE SUPCODE = ?";
        try (
                Connection con = openConnection();
                PreparedStatement preSta = con.prepareStatement(sql)) {
            preSta.setString(1, sup.getName());
            preSta.setString(2, sup.getAddress());
            preSta.setBoolean(3, sup.isColloborating());
            preSta.setString(4, sup.getCode());
            return preSta.executeUpdate();
        }
    }

    static public int deleteSup(Supplier sup) throws ClassNotFoundException, SQLException {
        String sql = "DELETE FROM SUPPLIERS WHERE SUPCODE = ?";
        try (
                Connection c = openConnection();
                PreparedStatement preSta = c.prepareStatement(sql)) {
            preSta.setString(1, sup.getCode());
            return preSta.executeUpdate();
        }
    }

    static public boolean checkRefItemToSup(String supCode) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM ITEMS WHERE SUPCODE = ?";
        try (
                Connection c = openConnection();
                PreparedStatement preSta = c.prepareStatement(sql)) {
            preSta.setString(1, supCode);
            ResultSet rs = preSta.executeQuery();
            while (rs.next()) {
                return true;
            }
        }
        return false;
    }
}
