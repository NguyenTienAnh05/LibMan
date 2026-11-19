<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    model.Library_card7 card = (model.Library_card7) request.getAttribute("card");
    model.Reader7 reader = (model.Reader7) request.getAttribute("reader");
    if (card == null) {                       // Vào trực tiếp, không qua RegisterServlet7
        response.sendRedirect(request.getContextPath() + "/Reader/Main_view7.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>LibMan - Chi tiết thẻ bạn đọc</title>
<%@ include file="/WEB-INF/style.jspf" %>
</head>
<body>
<%@ include file="../header.jsp" %>
<div class="container narrow">
    <!-- Card_detail_view7.jsp: outMessage, outIssueDate, outExpiryDate, subContinue -->
    <div class="success" id="outMessage">Đăng kí thành công!</div>
    <% if (reader != null) { %>
    <div class="row"><label>Mã bạn đọc</label><span><%= esc(reader.getReaderCode()) %></span></div>
    <% } %>
    <div class="row"><label>Ngày cấp</label><span id="outIssueDate"><%= fmtDate(card.getIssueDate()) %></span></div>
    <div class="row"><label>Ngày hết hạn</label><span id="outExpiryDate"><%= fmtDate(card.getExpiryDate()) %></span></div>
    <form action="<%= ctx %>/Reader/Main_view7.jsp" method="get">
        <div class="actions">
            <input type="submit" id="subContinue" name="subContinue" value="Tiếp tục">
        </div>
    </form>
</div>
</body>
</html>
