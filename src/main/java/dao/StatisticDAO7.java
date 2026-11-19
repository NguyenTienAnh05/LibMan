package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Borrow_slip7;
import model.Borrow_slip_detail7;
import model.Document7;
import model.Imported_document7;
import model.Loaned_document_stat7;
import model.Reader7;

/**
 * DAO cho modul thống kê tài liệu theo số lần mượn (theo sơ đồ thiết kế):
 * + StatisticDAO7()
 * + getLoanedDocuments(StartDate : date, EndDate : date) : Loaned_document_stat7[]
 * + getLoanHistory(...)
 */
public class StatisticDAO7 extends DAO7 {

    public StatisticDAO7() {
        super();
    }

    /**
     * Thống kê số lần mượn của từng tài liệu trong khoảng ngày [startDate, endDate],
     * sắp xếp giảm dần theo số lần mượn (tương ứng getLoanedDocuments() trong sơ đồ thiết kế).
     */
    public List<Loaned_document_stat7> getLoanedDocuments(Date startDate, Date endDate) throws SQLException {
        String sql = "SELECT d.ID, d.documentName, COUNT(DISTINCT bs.ID) AS loanCount "
                + "FROM tblDocument7 d "
                + "JOIN tblImported_document7 i ON i.documentID = d.ID "
                + "JOIN tblBorrow_slip_detail7 bd ON bd.ID = i.borrow_slip_detailID "
                + "JOIN tblBorrow_slip7 bs ON bs.ID = bd.borrowSlipID "
                + "WHERE bs.borrowDate BETWEEN ? AND ? "
                + "GROUP BY d.ID, d.documentName "
                + "ORDER BY loanCount DESC, d.documentName";
        List<Loaned_document_stat7> list = new ArrayList<Loaned_document_stat7>();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, startDate);
            ps.setDate(2, endDate);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Document7 d = new Document7();
                    d.setID(rs.getInt("ID"));
                    d.setDocumentName(rs.getString("documentName"));

                    Imported_document7 imp = new Imported_document7();
                    imp.setDocument(d);

                    Borrow_slip_detail7 bsd = new Borrow_slip_detail7();
                    bsd.setImported_document(imp);

                    Loaned_document_stat7 s = new Loaned_document_stat7();
                    s.setLoaned_document(bsd);
                    s.setDocument(d);
                    s.setLoanCount(rs.getInt("loanCount"));
                    list.add(s);
                }
            }
        }
        return list;
    }

    /** Trả về mảng Loaned_document_stat7[] đúng kiểu trả về trong sơ đồ thiết kế */
    public Loaned_document_stat7[] getLoanedDocumentsArray(Date startDate, Date endDate) throws SQLException {
        List<Loaned_document_stat7> list = getLoanedDocuments(startDate, endDate);
        return list.toArray(new Loaned_document_stat7[list.size()]);
    }

    /**
     * Các lần một tài liệu được mượn trong khoảng ngày (tương ứng getLoanHistory() trong sơ đồ thiết kế).
     * Mỗi phần tử là một chi tiết phiếu mượn kèm phiếu mượn và bạn đọc; sắp xếp theo ngày mượn.
     */
    public List<Borrow_slip_detail7> getLoanHistory(int documentID, Date startDate, Date endDate) throws SQLException {
        String sql = "SELECT bs.ID AS slipID, bs.borrowDate, bs.dueDate, MIN(bd.ID) AS detailID, "
                + "m.ID AS readerID, m.name, m.Email, m.Phone "
                + "FROM tblBorrow_slip7 bs "
                + "JOIN tblBorrow_slip_detail7 bd ON bd.borrowSlipID = bs.ID "
                + "JOIN tblImported_document7 i ON i.borrow_slip_detailID = bd.ID "
                + "JOIN tblMember7 m ON m.ID = bs.readerID "
                + "WHERE i.documentID = ? AND bs.borrowDate BETWEEN ? AND ? "
                + "GROUP BY bs.ID, bs.borrowDate, bs.dueDate, m.ID, m.name, m.Email, m.Phone "
                + "ORDER BY bs.borrowDate, bs.ID";
        List<Borrow_slip_detail7> list = new ArrayList<Borrow_slip_detail7>();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, documentID);
            ps.setDate(2, startDate);
            ps.setDate(3, endDate);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Reader7 r = new Reader7();
                    r.setID(rs.getInt("readerID"));
                    r.setName(rs.getString("name"));
                    r.setEmail(rs.getString("Email"));
                    r.setPhone(rs.getString("Phone"));

                    Borrow_slip7 slip = new Borrow_slip7();
                    slip.setID(rs.getInt("slipID"));
                    slip.setBorrowDate(rs.getDate("borrowDate"));
                    slip.setDueDate(rs.getDate("dueDate"));
                    slip.setReader(r);

                    Borrow_slip_detail7 d = new Borrow_slip_detail7();
                    d.setID(rs.getInt("detailID"));
                    d.setBorrowSlip(slip);

                    Document7 doc = new Document7();
                    doc.setID(documentID);
                    Imported_document7 imp = new Imported_document7();
                    imp.setDocument(doc);
                    d.setImported_document(imp);

                    slip.getBorrowslipdetail().add(d);
                    list.add(d);
                }
            }
        }
        return list;
    }

    /** Trả về mảng Borrow_slip_detail7[] cho getLoanHistory */
    public Borrow_slip_detail7[] getLoanHistoryArray(int documentID, Date startDate, Date endDate) throws SQLException {
        List<Borrow_slip_detail7> list = getLoanHistory(documentID, startDate, endDate);
        return list.toArray(new Borrow_slip_detail7[list.size()]);
    }

    /**
     * Chi tiết một phiếu mượn: ngày mượn, hạn trả, bạn đọc và các tài liệu mượn.
     * Trả về null nếu không có phiếu.
     */
    public Borrow_slip7 getBorrowSlipDetail(int borrowSlipID) throws SQLException {
        Borrow_slip7 slip = null;
        String sql1 = "SELECT bs.ID, bs.borrowDate, bs.dueDate, m.ID AS readerID, m.name, m.Email, m.Phone "
                + "FROM tblBorrow_slip7 bs JOIN tblMember7 m ON m.ID = bs.readerID WHERE bs.ID = ?";
        try (PreparedStatement ps = con.prepareStatement(sql1)) {
            ps.setInt(1, borrowSlipID);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                Reader7 r = new Reader7();
                r.setID(rs.getInt("readerID"));
                r.setName(rs.getString("name"));
                r.setEmail(rs.getString("Email"));
                r.setPhone(rs.getString("Phone"));
                slip = new Borrow_slip7();
                slip.setID(rs.getInt("ID"));
                slip.setBorrowDate(rs.getDate("borrowDate"));
                slip.setDueDate(rs.getDate("dueDate"));
                slip.setReader(r);
            }
        }
        String sql2 = "SELECT bd.ID AS detailID, i.ID AS importedID, i.Quantity, d.ID AS documentID, d.documentName "
                + "FROM tblBorrow_slip_detail7 bd "
                + "JOIN tblImported_document7 i ON i.borrow_slip_detailID = bd.ID "
                + "JOIN tblDocument7 d ON d.ID = i.documentID "
                + "WHERE bd.borrowSlipID = ? ORDER BY d.documentName, i.ID";
        try (PreparedStatement ps = con.prepareStatement(sql2)) {
            ps.setInt(1, borrowSlipID);
            try (ResultSet rs = ps.executeQuery()) {
                Borrow_slip_detail7 current = null;
                while (rs.next()) {
                    int detailID = rs.getInt("detailID");
                    if (current == null || current.getID() != detailID) {
                        current = null;
                        for (Borrow_slip_detail7 x : slip.getBorrowslipdetail()) {
                            if (x.getID() == detailID) {
                                current = x;
                            }
                        }
                        if (current == null) {
                            current = new Borrow_slip_detail7();
                            current.setID(detailID);
                            current.setBorrowSlip(slip);
                            slip.getBorrowslipdetail().add(current);
                        }
                    }
                    Document7 d = new Document7();
                    d.setID(rs.getInt("documentID"));
                    d.setDocumentName(rs.getString("documentName"));
                    Imported_document7 i = new Imported_document7();
                    i.setID(rs.getInt("importedID"));
                    i.setQuantity(rs.getInt("Quantity"));
                    i.setDocument(d);
                    current.setImported_document(i);
                    current.getImportedDocuments().add(i);
                }
            }
        }
        return slip;
    }

    /** Lấy một tài liệu (để hiển thị tên tài liệu ở màn hình lịch sử mượn); null nếu không có. */
    public Document7 getDocument(int documentID) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(
                "SELECT ID, documentName, Category, authors FROM tblDocument7 WHERE ID = ?")) {
            ps.setInt(1, documentID);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                Document7 d = new Document7();
                d.setID(rs.getInt("ID"));
                d.setDocumentName(rs.getString("documentName"));
                d.setCategory(rs.getString("Category"));
                d.setAuthors(rs.getString("authors"));
                return d;
            }
        }
    }
}
