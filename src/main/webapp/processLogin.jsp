<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="dao.MemberDAO7, model.*, javax.servlet.http.HttpSession" %>
<%
    request.setCharacterEncoding("UTF-8");
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    if (username == null || password == null || username.trim().length() == 0 || password.length() == 0) {
        response.sendRedirect("login.jsp?err=1");
        return;
    }
    MemberDAO7 dao = null;
    Member7 m = null;
    try {
        dao = new MemberDAO7();
        m = dao.checkLogin(username.trim(), password);
    } catch (Exception e) {
        response.sendRedirect("login.jsp?err=2");
        return;
    } finally {
        if (dao != null) {
            dao.close();
        }
    }
    if (m == null) {
        response.sendRedirect("login.jsp?err=1");
        return;
    }
    HttpSession s = request.getSession(true);
    s.invalidate();                       // tạo phiên mới sau khi đăng nhập
    s = request.getSession(true);
    s.setAttribute("member", m);
    String ctx = request.getContextPath();
    if (m instanceof Management_employee7) {
        response.sendRedirect(ctx + "/Management_employee/Main_view7.jsp");
    } else if (m instanceof Library_employee7) {
        response.sendRedirect(ctx + "/Library_employee/Main_view.jsp");
    } else {
        response.sendRedirect(ctx + "/Reader/Main_view7.jsp");
    }
%>
