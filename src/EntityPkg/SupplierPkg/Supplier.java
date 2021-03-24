/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityPkg.SupplierPkg;

/**
 *
 * @author DELL
 */
public class Supplier {
    String code;
    String name;
    String address;
    boolean colloborating;

    public Supplier(String code, String name, String address, boolean colloborating) {
        this.code = code;
        this.name = name;
        this.address = address;
        this.colloborating = colloborating;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isColloborating() {
        return colloborating;
    }

    public void setColloborating(boolean colloborating) {
        this.colloborating = colloborating;
    }
    
    
}
