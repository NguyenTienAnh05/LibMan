package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Reader7;

/** DAO cho bạn đọc (modul đăng kí thẻ bạn đọc). */
public class ReaderDAO7 extends DAO7 {

    public ReaderDAO7() {
        super();
    }

    /**
     * Lưu thông tin bạn đọc (tương ứng getRegister_card_view7() của pha phân tích):
     * cập nhật thông tin cá nhân của thành viên đã đăng nhập (r.getID()) và tạo bản ghi bạn đọc
     * (kèm mã bạn đọc) nếu thành viên chưa là bạn đọc. Trả về Reader7 đã có mã bạn đọc.
     */
    public Reader7 saveReader(Reader7 r) throws SQLException {
        boolean oldAuto = con.getAutoCommit();
        con.setAutoCommit(false);
        try {
            try (PreparedStatement ps = con.prepareStatement(
                    "UPDATE tblMember7 SET name = ?, address = ?, dob = ?, Email = ?, Phone = ? WHERE ID = ?")) {
                ps.setString(1, r.getName());
                ps.setString(2, r.getAddress());
                ps.setDate(3, r.getDob());
                ps.setString(4, r.getEmail());
                ps.setString(5, r.getPhone());
                ps.setInt(6, r.getID());
                if (ps.executeUpdate() != 1) {
                    throw new SQLException("Không tìm thấy thành viên có ID = " + r.getID());
                }
            }
            String code = null;
            try (PreparedStatement ps = con.prepareStatement("SELECT readerCode FROM tblReader7 WHERE ID = ?")) {
                ps.setInt(1, r.getID());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        code = rs.getString(1);
                    }
                }
            }
            if (code == null) {
                code = String.format("DG%05d", r.getID());
                try (PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO tblReader7 (ID, readerCode) VALUES (?, ?)")) {
                    ps.setInt(1, r.getID());
                    ps.setString(2, code);
                    ps.executeUpdate();
                }
            }
            con.commit();
            r.setReaderCode(code);
            return r;
        } catch (SQLException e) {
            con.rollback();
            throw e;
        } finally {
            con.setAutoCommit(oldAuto);
        }
    }

    /** Lấy bạn đọc theo ID (null nếu thành viên chưa là bạn đọc). */
    public Reader7 getReader(int id) throws SQLException {
        String sql = "SELECT m.ID, m.username, m.name, m.address, m.dob, m.Email, m.Phone, r.readerCode "
                + "FROM tblMember7 m JOIN tblReader7 r ON r.ID = m.ID WHERE m.ID = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                Reader7 r = new Reader7();
                r.setID(rs.getInt("ID"));
                r.setUsername(rs.getString("username"));
                r.setName(rs.getString("name"));
                r.setAddress(rs.getString("address"));
                r.setDob(rs.getDate("dob"));
                r.setEmail(rs.getString("Email"));
                r.setPhone(rs.getString("Phone"));
                r.setReaderCode(rs.getString("readerCode"));
                return r;
            }
        }
    }
}
