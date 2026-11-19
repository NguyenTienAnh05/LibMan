package model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Borrow_slip7 implements Serializable {
    private static final long serialVersionUID = 1L;

    private int ID;
    private Date borrowDate;
    private Date dueDate;
    private List<Borrow_slip_detail7> borrowslipdetail = new ArrayList<Borrow_slip_detail7>();
    private Reader7 reader;

    public Borrow_slip7() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Reader7 getReader() {
        return reader;
    }

    public void setReader(Reader7 reader) {
        this.reader = reader;
    }

    public List<Borrow_slip_detail7> getBorrowslipdetail() {
        return borrowslipdetail;
    }

    public void setBorrowslipdetail(List<Borrow_slip_detail7> borrowslipdetail) {
        this.borrowslipdetail = borrowslipdetail;
    }

    public Borrow_slip_detail7[] getBorrowslipdetailArray() {
        return borrowslipdetail == null ? new Borrow_slip_detail7[0]
                : borrowslipdetail.toArray(new Borrow_slip_detail7[borrowslipdetail.size()]);
    }

    // Tương thích ngược với getDetails() / setDetails()
    public List<Borrow_slip_detail7> getDetails() {
        return borrowslipdetail;
    }

    public void setDetails(List<Borrow_slip_detail7> details) {
        this.borrowslipdetail = details;
    }

    /**
     * Gom tài liệu trong phiếu theo tên: mỗi đầu tài liệu chỉ tính là một quyển.
     */
    public Map<String, Integer> getDocumentQuantities() {
        Map<String, Integer> result = new TreeMap<String, Integer>();
        for (Borrow_slip_detail7 d : borrowslipdetail) {
            Imported_document7 single = d.getImported_document();
            if (single != null && single.getDocument() != null) {
                String key = single.getDocument().getDocumentName();
                result.put(key, 1);
            }
            for (Imported_document7 i : d.getImportedDocuments()) {
                if (i == single || i.getDocument() == null) {
                    continue;
                }
                String key = i.getDocument().getDocumentName();
                result.put(key, 1);
            }
        }
        return result;
    }
}
