<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String err = request.getParameter("err");
    String msg = null;
    if ("1".equals(err)) {
        msg = "Sai tên đăng nhập hoặc mật khẩu.";
    } else if ("2".equals(err)) {
        msg = "Không kết nối được cơ sở dữ liệu. Hãy kiểm tra DAO7.java và JDBC driver trong WEB-INF/lib.";
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>LibMan - Đăng nhập</title>
<%@ include file="/WEB-INF/style.jspf" %>
</head>
<body>
<div class="topbar"><span class="brand">LibMan - Quản lý thư viện</span></div>
<div class="container narrow">
    <h2>Đăng nhập</h2>
    <% if (msg != null) { %><div class="error"><%= msg %></div><% } %>
    <form action="processLogin.jsp" method="post">
        <div class="row"><label for="username">Tên đăng nhập</label><input type="text" id="username" name="username" autofocus></div>
        <div class="row"><label for="password">Mật khẩu</label><input type="password" id="password" name="password"></div>
        <div class="actions"><input type="submit" name="subLogin" value="Đăng nhập"></div>
    </form>
</div>
</body>
</html>
