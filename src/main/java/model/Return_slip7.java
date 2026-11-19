package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Return_slip7 {
    private int ID;
    private Date returnDate;
    private Reader7 reader;
    private List<Return_slip_detail7> details = new ArrayList<Return_slip_detail7>();

    public Return_slip7() {
    }

    public int getID() { return ID; }
    public void setID(int ID) { this.ID = ID; }
    public Date getReturnDate() { return returnDate; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }
    public Reader7 getReader() { return reader; }
    public void setReader(Reader7 reader) { this.reader = reader; }
    public List<Return_slip_detail7> getDetails() { return details; }
    public void setDetails(List<Return_slip_detail7> details) { this.details = details; }
}
