<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>LibMan - Trang chủ quản lý</title>
<%@ include file="/WEB-INF/style.jspf" %>
</head>
<body>
<%@ include file="../header.jsp" %>
<div class="container">
    <h2>Trang chủ quản lý</h2>
    <!-- Main_view7.jsp: subViewStat : submit -->
    <form action="<%= ctx %>/Management_employee/Statistic_view7.jsp" method="get">
        <div class="actions">
            <input type="submit" id="subViewStat" name="subViewStat" value="Xem thống kê">
        </div>
    </form>
</div>
</body>
</html>
