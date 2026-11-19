<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%
    List<String> errors = (List<String>) request.getAttribute("errors");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>LibMan - Đăng kí thẻ bạn đọc</title>
<%@ include file="/WEB-INF/style.jspf" %>
</head>
<body>
<%@ include file="../header.jsp" %>
<div class="container">
    <h2>Đăng kí thẻ bạn đọc</h2>
    <% if (errors != null && !errors.isEmpty()) { %>
    <div class="error"><ul>
        <% for (String e : errors) { %><li><%= esc(e) %></li><% } %>
    </ul></div>
    <% } %>
    <!-- Register_card_view7.jsp: inName, inAddress, inDob, inEmail, inPhone, subRegister -->
    <form action="<%= ctx %>/dangkithe" method="post">
        <div class="row">
            <label for="inName">Họ tên</label>
            <input type="text" id="inName" name="inName" value="<%= esc(request.getAttribute("inName")) %>">
        </div>
        <div class="row">
            <label for="inAddress">Địa chỉ</label>
            <input type="text" id="inAddress" name="inAddress" value="<%= esc(request.getAttribute("inAddress")) %>">
        </div>
        <div class="row">
            <label for="inDob">Ngày sinh</label>
            <input type="text" id="inDob" name="inDob" placeholder="dd/MM/yyyy" value="<%= esc(request.getAttribute("inDob")) %>">
        </div>
        <div class="row">
            <label for="inEmail">Email</label>
            <input type="text" id="inEmail" name="inEmail" value="<%= esc(request.getAttribute("inEmail")) %>">
        </div>
        <div class="row">
            <label for="inPhone">Số điện thoại</label>
            <input type="text" id="inPhone" name="inPhone" value="<%= esc(request.getAttribute("inPhone")) %>">
        </div>
        <p class="muted">Ngày cấp thẻ là ngày đăng kí, thẻ có hiệu lực 3 năm.</p>
        <div class="actions">
            <input type="submit" id="subRegister" name="subRegister" value="Đăng kí">
            <a class="btn" href="<%= ctx %>/Reader/Main_view7.jsp">Quay lại</a>
        </div>
    </form>
</div>
</body>
</html>
