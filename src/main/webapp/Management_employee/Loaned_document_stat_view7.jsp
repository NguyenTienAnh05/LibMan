<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, model.Loaned_document_stat7" %>
<%
    List<Loaned_document_stat7> outDocumentList = (List<Loaned_document_stat7>) request.getAttribute("outDocumentList");
    if (outDocumentList == null) {
        outDocumentList = (List<Loaned_document_stat7>) request.getAttribute("stats");
    }
    String outStartDate = (String) request.getAttribute("outStartDate");
    if (outStartDate == null) {
        outStartDate = (String) request.getAttribute("startDate");
    }
    String outEndDate = (String) request.getAttribute("outEndDate");
    if (outEndDate == null) {
        outEndDate = (String) request.getAttribute("endDate");
    }
    if (outDocumentList == null) {                        // Vào trực tiếp, không qua StatisticServlet7
        response.sendRedirect(request.getContextPath() + "/Management_employee/Choose_date_view7.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>LibMan - Thống kê tài liệu</title>
<%@ include file="/WEB-INF/style.jspf" %>
<style>
    #outDocumentList { width: 100%; table-layout: fixed; border-collapse: collapse; }
    #outDocumentList th, #outDocumentList td { padding: 7px 10px; vertical-align: middle; }
    #outDocumentList th:first-child, #outDocumentList td:first-child { width: 52px; text-align: center; }
    #outDocumentList th:last-child, #outDocumentList td:last-child { width: 145px; text-align: center; }
</style>
</head>
<body>
<%@ include file="../header.jsp" %>
<div class="container">
    <h2>Thống kê tài liệu theo số lần mượn </h2>
    <!-- Loaned_document_stat_view7.jsp: outStartDate : String, outEndDate : String, outDocumentList : Table, subViewDocument : link, subBack : submit -->
    <p>Ngày bắt đầu: <b><%= esc(outStartDate) %></b> &nbsp;&nbsp; Ngày kết thúc: <b><%= esc(outEndDate) %></b></p>
    <% if (outDocumentList.isEmpty()) { %>
    <p class="muted">Không có tài liệu nào được mượn trong khoảng thời gian đã chọn.</p>
    <% } else { %>
    <table class="data" id="outDocumentList">
        <thead>
            <tr><th style="width:50px">TT</th><th>Tài liệu</th><th style="width:160px">Số lần mượn</th></tr>
        </thead>
        <tbody>
        <% int tt = 1; for (Loaned_document_stat7 s : outDocumentList) {
               String link = ctx + "/thongke?action=history&documentID=" + s.getDocument().getID()
                       + "&inStartDate=" + enc(outStartDate) + "&inEndDate=" + enc(outEndDate); %>
        <tr>
            <td><%= tt++ %></td>
            <td><a class="subViewDocument" href="<%= link %>"><%= esc(s.getDocument().getDocumentName()) %></a></td>
            <td><%= s.getLoanCount() %></td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <% } %>
    <form action="<%= ctx %>/Management_employee/Choose_date_view7.jsp" method="get" style="display:inline;">
        <input type="hidden" name="inStartDate" value="<%= esc(outStartDate) %>">
        <input type="hidden" name="inEndDate" value="<%= esc(outEndDate) %>">
        <div class="actions">
            <input type="submit" id="subBack" name="subBack" class="btn" value="Quay lại">
        </div>
    </form>
</div>
</body>
</html>
