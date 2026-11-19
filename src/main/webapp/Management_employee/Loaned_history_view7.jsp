<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, model.*" %>
<%
    String outDocumentName = (String) request.getAttribute("outDocumentName");
    Document7 document = (Document7) request.getAttribute("document");
    if (outDocumentName == null && document != null) {
        outDocumentName = document.getDocumentName();
    }
    List<Borrow_slip_detail7> outLoanHistoryList = (List<Borrow_slip_detail7>) request.getAttribute("outLoanHistoryList");
    if (outLoanHistoryList == null) {
        outLoanHistoryList = (List<Borrow_slip_detail7>) request.getAttribute("history");
    }
    String startDate = (String) request.getAttribute("startDate");
    String endDate = (String) request.getAttribute("endDate");
    if (outDocumentName == null || outLoanHistoryList == null) {   // Vào trực tiếp, không qua StatisticServlet7
        response.sendRedirect(request.getContextPath() + "/Management_employee/Choose_date_view7.jsp");
        return;
    }
    String backAction = request.getContextPath() + "/thongke";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>LibMan - Lịch sử mượn tài liệu</title>
<%@ include file="/WEB-INF/style.jspf" %>
<style>
    #outLoanHistoryList { width: 100%; table-layout: auto; border-collapse: collapse; }
    #outLoanHistoryList th, #outLoanHistoryList td { padding: 7px 10px; vertical-align: middle; }
    #outLoanHistoryList th:first-child, #outLoanHistoryList td:first-child { width: 52px; text-align: center; }
    #outLoanHistoryList th:nth-child(3), #outLoanHistoryList td:nth-child(3),
    #outLoanHistoryList th:nth-child(4), #outLoanHistoryList td:nth-child(4),
    #outLoanHistoryList th:last-child, #outLoanHistoryList td:last-child { text-align: center; }
</style>
</head>
<body>
<%@ include file="../header.jsp" %>
<div class="container">
    <h2>Lịch sử mượn tài liệu</h2>
    <!-- Loaned_history_view7.jsp: outDocumentName : String, outLoanHistoryList : Table, subViewReader : link, subBack : submit -->
    <p>Tài liệu: <b><%= esc(outDocumentName) %></b></p>
    <table class="data" id="outLoanHistoryList">
        <thead>
            <tr><th style="width:50px">TT</th><th>Họ tên</th><th>Email</th><th>Số điện thoại</th><th>Ngày mượn</th></tr>
        </thead>
        <tbody>
        <% int tt = 1; for (Borrow_slip_detail7 d : outLoanHistoryList) {
               Borrow_slip7 slip = d.getBorrowSlip();
               int docID = document != null ? document.getID() : (d.getImported_document() != null && d.getImported_document().getDocument() != null ? d.getImported_document().getDocument().getID() : 0);
               String link = ctx + "/thongke?action=slip&slipID=" + slip.getID() + "&documentID=" + docID
                       + "&inStartDate=" + enc(startDate) + "&inEndDate=" + enc(endDate); %>
        <tr>
            <td><%= tt++ %></td>
            <td><a class="subViewReader" href="<%= link %>"><%= esc(slip.getReader() == null ? "" : slip.getReader().getName()) %></a></td>
            <td><%= esc(slip.getReader() == null ? "" : slip.getReader().getEmail()) %></td>
            <td><%= esc(slip.getReader() == null ? "" : slip.getReader().getPhone()) %></td>
            <td><%= fmtDate(slip.getBorrowDate()) %></td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <form action="<%= backAction %>" method="post" style="display:inline;">
        <input type="hidden" name="action" value="stat">
        <input type="hidden" name="inStartDate" value="<%= esc(startDate) %>">
        <input type="hidden" name="inEndDate" value="<%= esc(endDate) %>">
        <div class="actions">
            <input type="submit" id="subBack" name="subBack" class="btn" value="Quay lại">
        </div>
    </form>
</div>
</body>
</html>
