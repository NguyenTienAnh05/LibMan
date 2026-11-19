<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String error = (String) request.getAttribute("error");
    String startDate = (String) request.getAttribute("startDate");
    String endDate = (String) request.getAttribute("endDate");
    if (startDate == null) startDate = request.getParameter("inStartDate");
    if (endDate == null) endDate = request.getParameter("inEndDate");
    if (startDate == null) startDate = (String) request.getAttribute("outStartDate");
    if (endDate == null) endDate = (String) request.getAttribute("outEndDate");
    startDate = toInputDate(startDate);
    endDate = toInputDate(endDate);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>LibMan - Chọn ngày thống kê</title>
<%@ include file="/WEB-INF/style.jspf" %>
<style>
    .date-input { width: 260px; padding: 10px 12px; font-size: 18px; }
    .date-row label { width: 180px; font-size: 18px; }
</style>
</head>
<body>
<%@ include file="../header.jsp" %>
<div class="container">
    <h2>Ch&#7885;n ng&#224;y th&#7889;ng k&#234;</h2>
    <% if (error != null) { %><div class="error"><%= esc(error) %></div><% } %>
    <form action="<%= ctx %>/thongke" method="post">
        <input type="hidden" name="action" value="stat">
        <div class="row date-row">
            <label for="inStartDate">Ng&#224;y b&#7855;t &#273;&#7847;u</label>
            <input class="date-input" type="date" id="inStartDate" name="inStartDate" value="<%= esc(startDate) %>" required>
        </div>
        <div class="row date-row">
            <label for="inEndDate">Ng&#224;y k&#7871;t th&#250;c</label>
            <input class="date-input" type="date" id="inEndDate" name="inEndDate" value="<%= esc(endDate) %>" required>
        </div>
        <div class="actions">
            <input type="submit" id="subView" name="subView" value="Xem">
            <a class="btn" href="<%= ctx %>/Management_employee/Statistic_view7.jsp">Quay l&#7841;i</a>
        </div>
    </form>
</div>
</body>
</html>
<%!
    private String toInputDate(String value) {
        if (value == null || value.length() == 0) return "";
        if (value.matches("\\d{4}-\\d{2}-\\d{2}")) return value;
        if (value.matches("\\d{2}/\\d{2}/\\d{4}")) {
            String[] parts = value.split("/");
            return parts[2] + "-" + parts[1] + "-" + parts[0];
        }
        return "";
    }
%>
