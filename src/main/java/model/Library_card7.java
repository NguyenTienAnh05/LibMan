package model;

import java.sql.Date;

/** Thẻ bạn đọc. */
public class Library_card7 {
    private int ID;
    private Date issueDate;
    private Date expiryDate;

    public Library_card7() {
    }

    public Library_card7(Date issueDate, Date expiryDate) {
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
    }

    public int getID() { return ID; }
    public void setID(int ID) { this.ID = ID; }
    public Date getIssueDate() { return issueDate; }
    public void setIssueDate(Date issueDate) { this.issueDate = issueDate; }
    public Date getExpiryDate() { return expiryDate; }
    public void setExpiryDate(Date expiryDate) { this.expiryDate = expiryDate; }
}
