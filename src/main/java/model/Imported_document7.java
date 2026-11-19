package model;

import java.io.Serializable;

/**
 * Tài liệu nhập (theo sơ đồ thiết kế):
 * - ID : integer
 * - Quantity : integer
 * - supplier : Supplier7
 * - document : Document7
 */
public class Imported_document7 implements Serializable {
    private static final long serialVersionUID = 1L;

    private int ID;
    private int quantity;
    private Supplier7 supplier;
    private Document7 document;

    public Imported_document7() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Supplier7 getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier7 supplier) {
        this.supplier = supplier;
    }

    public Document7 getDocument() {
        return document;
    }

    public void setDocument(Document7 document) {
        this.document = document;
    }
}
