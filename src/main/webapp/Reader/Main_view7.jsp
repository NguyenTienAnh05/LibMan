<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>LibMan - Trang chủ bạn đọc</title>
<%@ include file="/WEB-INF/style.jspf" %>
</head>
<body>
<%@ include file="../header.jsp" %>
<div class="container">
    <h2>Trang chủ bạn đọc</h2>
    <p class="info">Chọn chức năng bên dưới.</p>
    <!-- Main_view7.jsp: subRegister_card : submit -->
    <form action="<%= ctx %>/dangkithe" method="get">
        <div class="actions">
            <input type="submit" id="subRegister_card" name="subRegister_card" value="Đăng kí thẻ">
        </div>
    </form>
</div>
</body>
</html>
