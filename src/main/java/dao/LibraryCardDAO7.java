package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Library_card7;
import model.Reader7;

/** DAO cho thẻ bạn đọc (modul đăng kí thẻ bạn đọc). */
public class LibraryCardDAO7 extends DAO7 {

    public LibraryCardDAO7() {
        super();
    }

    /**
     * Lưu thẻ của bạn đọc vừa được lưu (tương ứng getCard_detail_view7() của pha phân tích).
     * Trả về thẻ đã lưu (có ID) để hiển thị chi tiết thẻ.
     */
    public Library_card7 saveCard(Library_card7 c, Reader7 r) throws SQLException {
        String sql = "INSERT INTO tblLibrary_card7 (issueDate, expiryDate, readerID) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            ps.setDate(1, c.getIssueDate());
            ps.setDate(2, c.getExpiryDate());
            ps.setInt(3, r.getID());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    c.setID(rs.getInt(1));
                }
            }
        }
        r.setCard(c);
        return c;
    }

    /** Lấy thẻ của một bạn đọc (null nếu chưa có thẻ). */
    public Library_card7 getCardByReader(int readerID) throws SQLException {
        String sql = "SELECT ID, issueDate, expiryDate FROM tblLibrary_card7 WHERE readerID = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, readerID);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                Library_card7 c = new Library_card7(rs.getDate("issueDate"), rs.getDate("expiryDate"));
                c.setID(rs.getInt("ID"));
                return c;
            }
        }
    }
}
