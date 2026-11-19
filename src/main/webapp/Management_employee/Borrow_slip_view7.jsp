<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Map, model.Borrow_slip7" %>
<%
    Borrow_slip7 slip = (Borrow_slip7) request.getAttribute("slip");
    String outReaderName = (String) request.getAttribute("outReaderName");
    String outBorrowDate = (String) request.getAttribute("outBorrowDate");
    Map<String, Integer> outSlipDetailList = (Map<String, Integer>) request.getAttribute("outSlipDetailList");

    if (slip != null) {
        if (outReaderName == null && slip.getReader() != null) {
            outReaderName = slip.getReader().getName();
        }
        if (outBorrowDate == null && slip.getBorrowDate() != null) {
            outBorrowDate = fmtDate(slip.getBorrowDate());
        }
        if (outSlipDetailList == null) {
            outSlipDetailList = slip.getDocumentQuantities();
        }
    }

    String startDate = (String) request.getAttribute("startDate");
    String endDate = (String) request.getAttribute("endDate");
    String documentID = (String) request.getAttribute("documentID");
    int totalBorrowed = 0;
    if (outSlipDetailList != null) {
        for (Integer quantity : outSlipDetailList.values()) {
            if (quantity != null) totalBorrowed += quantity;
        }
    }
    if (slip == null) {                          // Vào trực tiếp, không qua StatisticServlet7
        response.sendRedirect(request.getContextPath() + "/Management_employee/Choose_date_view7.jsp");
        return;
    }
    String backAction = request.getContextPath() + "/thongke";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>LibMan - Chi tiết phiếu mượn</title>
<%@ include file="/WEB-INF/style.jspf" %>
<style>
    #outSlipDetailList { width: 100%; table-layout: auto; border-collapse: collapse; }
    #outSlipDetailList th, #outSlipDetailList td { padding: 7px 10px; vertical-align: middle; }
    #outSlipDetailList th:first-child, #outSlipDetailList td:first-child { width: 52px; text-align: center; }
    #outSlipDetailList th:last-child, #outSlipDetailList td:last-child { width: 145px; text-align: center; }
</style>
</head>
<body>
<%@ include file="../header.jsp" %>
<div class="container">
    <h2>Chi tiết phiếu mượn</h2>
    <!-- Borrow_slip_view7.jsp: outReaderName : String, outBorrowDate : String, outSlipDetailList : Table, subBack : submit -->
    <p>Họ tên: <b><%= esc(outReaderName) %></b></p>
    <p>Ngày mượn: <b><%= esc(outBorrowDate) %></b></p>
    <table class="data" id="outSlipDetailList">
        <thead>
            <tr><th style="width:50px">TT</th><th>Tài liệu</th><th style="width:140px">Số quyển mượn</th></tr>
        </thead>
        <tbody>
        <% int tt = 1; if (outSlipDetailList != null) {
               for (Map.Entry<String, Integer> e : outSlipDetailList.entrySet()) { %>
        <tr><td><%= tt++ %></td><td><%= esc(e.getKey()) %></td><td><%= e.getValue() %></td></tr>
        <%     }
           } %>
        </tbody>
    </table>
    <p><strong>Tổng số quyển mượn: <%= totalBorrowed %></strong></p>
    <form action="<%= backAction %>" method="get" style="display:inline;">
        <input type="hidden" name="action" value="history">
        <input type="hidden" name="documentID" value="<%= esc(documentID) %>">
        <input type="hidden" name="inStartDate" value="<%= esc(startDate) %>">
        <input type="hidden" name="inEndDate" value="<%= esc(endDate) %>">
        <div class="actions">
            <input type="submit" id="subBack" name="subBack" class="btn" value="Quay lại">
        </div>
    </form>
</div>
</body>
</html>


