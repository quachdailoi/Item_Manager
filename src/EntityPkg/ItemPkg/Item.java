/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityPkg.ItemPkg;

/**
 *
 * @author DELL
 */
public class Item {
    String code, name, supplier, unit;
    int price;
    boolean supply;

    public Item(String code, String name, String supplier, String unit, int price, boolean supply) {
        this.code = code;
        this.name = name;
        this.supplier = supplier;
        this.unit = unit;
        this.price = price;
        this.supply = supply;
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

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isSupply() {
        return supply;
    }

    public void setSupply(boolean supply) {
        this.supply = supply;
    }

    @Override
    public String toString() {
        return "Item{" + "code=" + code + ", name=" + name + ", supplier=" + supplier + ", unit=" + unit + ", price=" + price + ", supply=" + supply + '}';
    }
    
    
}
