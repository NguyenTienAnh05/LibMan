package servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.LibraryCardDAO7;
import dao.ReaderDAO7;
import model.Employee7;
import model.Library_card7;
import model.Member7;
import model.Reader7;

/**
 * Modul đăng kí thẻ bạn đọc.
 * doGet : hiển thị form đăng kí (Register_card_view7 = bd/gddangkithe.jsp).
 * doPost: kiểm tra dữ liệu, gọi ReaderDAO7.saveReader() và LibraryCardDAO7.saveCard(),
 *         rồi chuyển sang Card_detail_view7 (bd/gdchitietthe.jsp).
 */
public class RegisterServlet7 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String FORM = "/Reader/Register_card_view7.jsp";
    private static final String DETAIL = "/Reader/Card_detail_view7.jsp";
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE = Pattern.compile("^0\\d{9,10}$");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Member7 me = currentMember(request, response);
        if (me == null) {
            return;
        }
        // điền sẵn các thông tin đã có của thành viên
        SimpleDateFormat f = new SimpleDateFormat(DATE_FORMAT);
        request.setAttribute("inName", me.getName());
        request.setAttribute("inAddress", me.getAddress());
        request.setAttribute("inDob", me.getDob() == null ? "" : f.format(me.getDob()));
        request.setAttribute("inEmail", me.getEmail());
        request.setAttribute("inPhone", me.getPhone());
        LibraryCardDAO7 cardDAO = null;
        try {
            cardDAO = new LibraryCardDAO7();
            if (cardDAO.getCardByReader(me.getID()) != null) {
                List<String> errors = new ArrayList<String>();
                errors.add("Bạn đã có thẻ bạn đọc, không thể đăng kí thêm.");
                request.setAttribute("errors", errors);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        } catch (IllegalStateException e) {          // không kết nối được CSDL / thiếu JDBC driver
            List<String> errors = new ArrayList<String>();
            errors.add(e.getMessage());
            request.setAttribute("errors", errors);
        } finally {
            if (cardDAO != null) {
                cardDAO.close();
            }
        }
        request.getRequestDispatcher(FORM).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Member7 me = currentMember(request, response);
        if (me == null) {
            return;
        }
        String name = trim(request.getParameter("inName"));
        String address = trim(request.getParameter("inAddress"));
        String dobText = trim(request.getParameter("inDob"));
        String email = trim(request.getParameter("inEmail"));
        String phone = trim(request.getParameter("inPhone"));

        // giữ lại dữ liệu đã nhập để hiển thị lại nếu có lỗi
        request.setAttribute("inName", name);
        request.setAttribute("inAddress", address);
        request.setAttribute("inDob", dobText);
        request.setAttribute("inEmail", email);
        request.setAttribute("inPhone", phone);

        List<String> errors = new ArrayList<String>();
        Date dob = null;
        if (name.length() == 0 || address.length() == 0 || dobText.length() == 0
                || email.length() == 0 || phone.length() == 0) {
            errors.add("Vui lòng nhập đầy đủ họ tên, địa chỉ, ngày sinh, email và số điện thoại.");
        } else {
            if (name.length() > 255 || address.length() > 255) {
                errors.add("Họ tên và địa chỉ không được dài quá 255 ký tự.");
            }
            dob = parseDate(dobText);
            if (dob == null) {
                errors.add("Ngày sinh không hợp lệ (định dạng dd/MM/yyyy).");
            } else if (!dob.before(new Date(System.currentTimeMillis()))) {
                errors.add("Ngày sinh phải trước ngày hiện tại.");
            }
            if (!EMAIL.matcher(email).matches()) {
                errors.add("Email không đúng định dạng.");
            }
            if (!PHONE.matcher(phone).matches()) {
                errors.add("Số điện thoại không hợp lệ (bắt đầu bằng 0, gồm 10-11 chữ số).");
            }
        }
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher(FORM).forward(request, response);
            return;
        }

        ReaderDAO7 readerDAO = null;
        LibraryCardDAO7 cardDAO = null;
        try {
            readerDAO = new ReaderDAO7();
            cardDAO = new LibraryCardDAO7();
            if (cardDAO.getCardByReader(me.getID()) != null) {
                errors.add("Bạn đã có thẻ bạn đọc, không thể đăng kí thêm.");
                request.setAttribute("errors", errors);
                request.getRequestDispatcher(FORM).forward(request, response);
                return;
            }
            Reader7 reader = new Reader7();
            reader.setID(me.getID());
            reader.setUsername(me.getUsername());
            reader.setName(name);
            reader.setAddress(address);
            reader.setDob(dob);
            reader.setEmail(email);
            reader.setPhone(phone);
            reader = readerDAO.saveReader(reader);               // lưu thông tin bạn đọc

            Date issue = new Date(System.currentTimeMillis());   // ngày cấp = hôm nay
            Calendar cal = Calendar.getInstance();
            cal.setTime(issue);
            cal.add(Calendar.YEAR, 3);                           // thẻ có hiệu lực 3 năm
            cal.add(Calendar.DAY_OF_MONTH, -1);
            Library_card7 card = new Library_card7(issue, new Date(cal.getTimeInMillis()));
            card = cardDAO.saveCard(card, reader);               // lưu thẻ của bạn đọc vừa lưu

            request.getSession().setAttribute("member", reader); // thành viên giờ đã là bạn đọc
            request.setAttribute("reader", reader);
            request.setAttribute("card", card);
            request.getRequestDispatcher(DETAIL).forward(request, response);
        } catch (IllegalStateException e) {          // không kết nối được CSDL / thiếu JDBC driver
            errors.add(e.getMessage());
            request.setAttribute("errors", errors);
            request.getRequestDispatcher(FORM).forward(request, response);
        } catch (SQLException e) {
            errors.add("Không lưu được dữ liệu: " + e.getMessage());
            request.setAttribute("errors", errors);
            request.getRequestDispatcher(FORM).forward(request, response);
        } finally {
            if (readerDAO != null) {
                readerDAO.close();
            }
            if (cardDAO != null) {
                cardDAO.close();
            }
        }
    }

    /** Lấy thành viên đang đăng nhập; chuyển về trang đăng nhập nếu chưa đăng nhập hoặc là nhân viên. */
    private Member7 currentMember(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        Member7 me = session == null ? null : (Member7) session.getAttribute("member");
        if (me == null || me instanceof Employee7) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return null;
        }
        return me;
    }

    private static String trim(String s) {
        return s == null ? "" : s.trim();
    }

    /** Đọc ngày dd/MM/yyyy (không cho phép ngày không tồn tại, ví dụ 31/02); null nếu sai. */
    static Date parseDate(String s) {
        SimpleDateFormat f = new SimpleDateFormat(DATE_FORMAT);
        f.setLenient(false);
        try {
            return new Date(f.parse(s).getTime());
        } catch (ParseException e) {
            return null;
        }
    }
}
