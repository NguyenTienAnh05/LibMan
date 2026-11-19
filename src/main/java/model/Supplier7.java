package model;

import java.io.Serializable;

/**
 * Nhà cung cấp (theo sơ đồ thiết kế):
 * - ID : integer
 * - supplierName : String
 * - Address : String
 * - supplierPhone : String
 * - supplierEmail : String
 */
public class Supplier7 implements Serializable {
    private static final long serialVersionUID = 1L;

    private int ID;
    private String supplierName;
    private String address;
    private String supplierPhone;
    private String supplierEmail;

    public Supplier7() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSupplierPhone() {
        return supplierPhone;
    }

    public void setSupplierPhone(String supplierPhone) {
        this.supplierPhone = supplierPhone;
    }

    public String getSupplierEmail() {
        return supplierEmail;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }
}
