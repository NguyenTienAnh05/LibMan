package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Employee7;
import model.Library_employee7;
import model.Management_employee7;
import model.Member7;
import model.Reader7;

/**
 * Đăng nhập (use case Login7). Lớp bổ sung để chạy được hệ thống,
 * không nằm trong hai modul được thiết kế chi tiết.
 */
public class MemberDAO7 extends DAO7 {

    public MemberDAO7() {
        super();
    }

    /**
     * Kiểm tra đăng nhập. Trả về đối tượng đúng loại:
     * Management_employee7, Library_employee7, Reader7 hoặc Member7 (thành viên chưa có thẻ);
     * trả về null nếu sai tên đăng nhập hoặc mật khẩu.
     */
    public Member7 checkLogin(String username, String password) throws SQLException {
        String sql = "SELECT m.ID, m.username, m.name, m.address, m.dob, m.Email, m.Phone, "
                + "e.Position, mg.ID AS mgID, le.ID AS leID, r.ID AS rID, r.readerCode "
                + "FROM tblMember7 m "
                + "LEFT JOIN tblEmployee7 e ON e.ID = m.ID "
                + "LEFT JOIN tblManagement_employee7 mg ON mg.ID = m.ID "
                + "LEFT JOIN tblLibrary_employee7 le ON le.ID = m.ID "
                + "LEFT JOIN tblReader7 r ON r.ID = m.ID "
                + "WHERE m.username = ? AND m.password = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                Member7 m;
                if (rs.getObject("mgID") != null) {
                    Management_employee7 x = new Management_employee7();
                    x.setPosition(rs.getString("Position"));
                    m = x;
                } else if (rs.getObject("leID") != null) {
                    Library_employee7 x = new Library_employee7();
                    x.setPosition(rs.getString("Position"));
                    m = x;
                } else if (rs.getObject("rID") != null) {
                    Reader7 x = new Reader7();
                    x.setReaderCode(rs.getString("readerCode"));
                    m = x;
                } else {
                    m = new Member7();
                }
                m.setID(rs.getInt("ID"));
                m.setUsername(rs.getString("username"));
                m.setName(rs.getString("name"));
                m.setAddress(rs.getString("address"));
                m.setDob(rs.getDate("dob"));
                m.setEmail(rs.getString("Email"));
                m.setPhone(rs.getString("Phone"));
                return m;
            }
        }
    }

    /** true nếu thành viên là nhân viên (thư viện hoặc quản lý). */
    public static boolean isEmployee(Member7 m) {
        return m instanceof Employee7;
    }
}
