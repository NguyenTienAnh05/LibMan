package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Borrow_slip_detail7 implements Serializable {
    private static final long serialVersionUID = 1L;

    private int ID;
    private Imported_document7 imported_document;
    private Borrow_slip7 borrowSlip;
    private List<Imported_document7> importedDocuments = new ArrayList<Imported_document7>();

    public Borrow_slip_detail7() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Imported_document7 getImported_document() {
        if (imported_document != null) {
            return imported_document;
        }
        if (!importedDocuments.isEmpty()) {
            return importedDocuments.get(0);
        }
        return null;
    }

    public void setImported_document(Imported_document7 imported_document) {
        this.imported_document = imported_document;
        if (imported_document != null && !importedDocuments.contains(imported_document)) {
            importedDocuments.add(imported_document);
        }
    }

    public Borrow_slip7 getBorrowSlip() {
        return borrowSlip;
    }

    public void setBorrowSlip(Borrow_slip7 borrowSlip) {
        this.borrowSlip = borrowSlip;
    }

    public List<Imported_document7> getImportedDocuments() {
        return importedDocuments;
    }

    public void setImportedDocuments(List<Imported_document7> importedDocuments) {
        this.importedDocuments = importedDocuments;
        if (importedDocuments != null && !importedDocuments.isEmpty()) {
            this.imported_document = importedDocuments.get(0);
        }
    }
}
