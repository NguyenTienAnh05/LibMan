package servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.StatisticDAO7;
import model.Borrow_slip7;
import model.Borrow_slip_detail7;
import model.Document7;
import model.Loaned_document_stat7;
import model.Management_employee7;
import model.Member7;

/**
 * Modul thống kê tài liệu theo số lần mượn (theo sơ đồ thiết kế):
 * + doGet(request : HttpServletRequest, response : HttpServletResponse) : void
 * + doPost(request : HttpServletRequest, response : HttpServletResponse) : void
 */
public class StatisticServlet7 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String CHOOSE_DATE = "/Management_employee/Choose_date_view7.jsp";
    private static final String STAT_VIEW = "/Management_employee/Loaned_document_stat_view7.jsp";
    private static final String HISTORY_VIEW = "/Management_employee/Loaned_history_view7.jsp";
    private static final String SLIP_VIEW = "/Management_employee/Borrow_slip_view7.jsp";
    private static final String DATE_FORMAT_VN = "dd/MM/yyyy";
    private static final String DATE_FORMAT_ISO = "yyyy-MM-dd";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handle(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handle(request, response);
    }

    private void handle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (!isManager(request)) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        String action = request.getParameter("action");
        if (action == null || action.length() == 0) {
            action = "stat";
        }
        String startText = trim(request.getParameter("inStartDate"));
        String endText = trim(request.getParameter("inEndDate"));

        // Kiểm tra khoảng ngày (ngày bắt đầu không được lớn hơn ngày kết thúc, không để trống)
        Date start = parseDate(startText);
        Date end = parseDate(endText);
        if (start == null || end == null || start.after(end)) {
            request.setAttribute("error", start == null || end == null
                    ? "Vui lòng nhập ngày bắt đầu và ngày kết thúc hợp lệ (dd/MM/yyyy hoặc chọn lịch)."
                    : "Ngày bắt đầu không được lớn hơn ngày kết thúc.");
            request.setAttribute("startDate", startText);
            request.setAttribute("endDate", endText);
            request.getRequestDispatcher(CHOOSE_DATE).forward(request, response);
            return;
        }

        SimpleDateFormat displayFormat = new SimpleDateFormat(DATE_FORMAT_VN);
        String startFormatted = displayFormat.format(start);
        String endFormatted = displayFormat.format(end);

        // Đặt thuộc tính theo sơ đồ thiết kế (outStartDate, outEndDate)
        request.setAttribute("outStartDate", startFormatted);
        request.setAttribute("outEndDate", endFormatted);
        request.setAttribute("startDate", startFormatted);
        request.setAttribute("endDate", endFormatted);

        StatisticDAO7 dao = null;
        try {
            dao = new StatisticDAO7();
            if ("history".equals(action)) {
                int documentID = parseInt(request.getParameter("documentID"));
                Document7 document = dao.getDocument(documentID);
                if (document == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy tài liệu");
                    return;
                }
                List<Borrow_slip_detail7> history = dao.getLoanHistory(documentID, start, end);

                // Thuộc tính theo sơ đồ thiết kế (Loaned_history_view7.jsp: outDocumentName, outLoanHistoryList)
                request.setAttribute("outDocumentName", document.getDocumentName());
                request.setAttribute("outLoanHistoryList", history);
                request.setAttribute("document", document);
                request.setAttribute("history", history);

                request.getRequestDispatcher(HISTORY_VIEW).forward(request, response);
            } else if ("slip".equals(action)) {
                int slipID = parseInt(request.getParameter("slipID"));
                Borrow_slip7 slip = dao.getBorrowSlipDetail(slipID);
                if (slip == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy phiếu mượn");
                    return;
                }

                // Thuộc tính theo sơ đồ thiết kế (Borrow_slip_view7.jsp: outReaderName, outBorrowDate, outSlipDetailList)
                String readerName = slip.getReader() != null ? slip.getReader().getName() : "";
                String borrowDateStr = slip.getBorrowDate() != null ? displayFormat.format(slip.getBorrowDate()) : "";
                String dueDateStr = slip.getDueDate() != null ? displayFormat.format(slip.getDueDate()) : "";

                request.setAttribute("outReaderName", readerName);
                request.setAttribute("outBorrowDate", borrowDateStr);
                request.setAttribute("outDueDate", dueDateStr);
                request.setAttribute("outSlipDetailList", slip.getDocumentQuantities());
                request.setAttribute("slip", slip);
                request.setAttribute("documentID", String.valueOf(parseInt(request.getParameter("documentID"))));

                request.getRequestDispatcher(SLIP_VIEW).forward(request, response);
            } else {
                List<Loaned_document_stat7> stats = dao.getLoanedDocuments(start, end);

                // Thuộc tính theo sơ đồ thiết kế (Loaned_document_stat_view7.jsp: outDocumentList)
                request.setAttribute("outDocumentList", stats);
                request.setAttribute("stats", stats);

                request.getRequestDispatcher(STAT_VIEW).forward(request, response);
            }
        } catch (IllegalStateException e) {          // Không kết nối được CSDL / thiếu JDBC driver
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher(CHOOSE_DATE).forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        } finally {
            if (dao != null) {
                dao.close();
            }
        }
    }

    private boolean isManager(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Member7 me = session == null ? null : (Member7) session.getAttribute("member");
        return me instanceof Management_employee7;
    }

    private static String trim(String s) {
        return s == null ? "" : s.trim();
    }

    private static int parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /** Đọc ngày hỗ trợ cả định dạng dd/MM/yyyy và yyyy-MM-dd; null nếu sai. */
    static Date parseDate(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        String[] patterns = new String[] { DATE_FORMAT_VN, DATE_FORMAT_ISO };
        for (String pattern : patterns) {
            SimpleDateFormat f = new SimpleDateFormat(pattern);
            f.setLenient(false);
            try {
                return new Date(f.parse(s).getTime());
            } catch (ParseException ignore) {
            }
        }
        return null;
    }
}
