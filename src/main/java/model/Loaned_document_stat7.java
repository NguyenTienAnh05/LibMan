package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Thống kê tài liệu theo số lần mượn (theo sơ đồ thiết kế):
 * - loanCount : int
 * - loaned_document : Borrow_slip_detail7
 */
public class Loaned_document_stat7 implements Serializable {
    private static final long serialVersionUID = 1L;

    private int loanCount;
    private Borrow_slip_detail7 loaned_document;

    // Hỗ trợ truy cập nhanh Document7 và danh sách chi tiết
    private Document7 document;
    private List<Borrow_slip_detail7> loanDetails = new ArrayList<Borrow_slip_detail7>();

    public Loaned_document_stat7() {
    }

    public Loaned_document_stat7(int loanCount, Borrow_slip_detail7 loaned_document) {
        this.loanCount = loanCount;
        setLoaned_document(loaned_document);
    }

    public int getLoanCount() {
        return loanCount;
    }

    public void setLoanCount(int loanCount) {
        this.loanCount = loanCount;
    }

    public Borrow_slip_detail7 getLoaned_document() {
        return loaned_document;
    }

    public void setLoaned_document(Borrow_slip_detail7 loaned_document) {
        this.loaned_document = loaned_document;
        if (loaned_document != null && loaned_document.getImported_document() != null) {
            this.document = loaned_document.getImported_document().getDocument();
        }
    }

    public Document7 getDocument() {
        if (document != null) {
            return document;
        }
        if (loaned_document != null && loaned_document.getImported_document() != null) {
            return loaned_document.getImported_document().getDocument();
        }
        return null;
    }

    public void setDocument(Document7 document) {
        this.document = document;
        if (this.loaned_document == null) {
            Borrow_slip_detail7 bsd = new Borrow_slip_detail7();
            Imported_document7 imp = new Imported_document7();
            imp.setDocument(document);
            bsd.setImported_document(imp);
            this.loaned_document = bsd;
        } else if (this.loaned_document.getImported_document() == null) {
            Imported_document7 imp = new Imported_document7();
            imp.setDocument(document);
            this.loaned_document.setImported_document(imp);
        } else {
            this.loaned_document.getImported_document().setDocument(document);
        }
    }

    public List<Borrow_slip_detail7> getLoanDetails() {
        return loanDetails;
    }

    public void setLoanDetails(List<Borrow_slip_detail7> loanDetails) {
        this.loanDetails = loanDetails;
    }
}
