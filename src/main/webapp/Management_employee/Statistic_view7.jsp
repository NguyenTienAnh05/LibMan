<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>LibMan - Chọn loại thống kê</title>
<%@ include file="/WEB-INF/style.jspf" %>
<style>
    .view-button { font-size: 18px; padding: 9px 28px; }
</style>
</head>
<body>
<%@ include file="../header.jsp" %>
<div class="container">
    <h2>Chọn thống kê</h2>
    <form action="<%= ctx %>/Management_employee/Choose_date_view7.jsp" method="get">
        <div class="row">
            <label>Loại thống kê</label>
            <span>
                <label style="width:auto;font-weight:normal">
                    <input type="radio" id="inputStat_type" name="inputStat_type" value="document" checked>
                    Tài liệu theo số lượt mượn
                </label><br>
                <label style="width:auto;font-weight:normal" class="muted">
                    <input type="radio" name="inputStat_type" value="reader" disabled>
                    Bạn đọc theo số lượt mượn (chưa hỗ trợ)
                </label><br>
                <label style="width:auto;font-weight:normal" class="muted">
                    <input type="radio" name="inputStat_type" value="supplier" disabled>
                    Nhà cung cấp theo số lượng tài liệu nhập (chưa hỗ trợ)
                </label>
            </span>
        </div>
        <div class="actions">
            <input type="submit" id="subView" class="view-button" value="Xem">
            <a class="btn" href="<%= ctx %>/Management_employee/Main_view7.jsp">Quay lại</a>
        </div>
    </form>
</div>
</body>
</html>
