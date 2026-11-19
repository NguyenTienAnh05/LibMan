package model;

import java.sql.Date;

public class Penalty_slip7 {
    private int ID;
    private Date penaltyDate;
    private int overdueDay;
    private double fineAmount;
    private Reader7 reader;
    private Return_slip_detail7 returnDetail;

    public Penalty_slip7() {
    }

    public int getID() { return ID; }
    public void setID(int ID) { this.ID = ID; }
    public Date getPenaltyDate() { return penaltyDate; }
    public void setPenaltyDate(Date penaltyDate) { this.penaltyDate = penaltyDate; }
    public int getOverdueDay() { return overdueDay; }
    public void setOverdueDay(int overdueDay) { this.overdueDay = overdueDay; }
    public double getFineAmount() { return fineAmount; }
    public void setFineAmount(double fineAmount) { this.fineAmount = fineAmount; }
    public Reader7 getReader() { return reader; }
    public void setReader(Reader7 reader) { this.reader = reader; }
    public Return_slip_detail7 getReturnDetail() { return returnDetail; }
    public void setReturnDetail(Return_slip_detail7 returnDetail) { this.returnDetail = returnDetail; }
}
