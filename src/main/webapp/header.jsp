<%@ page pageEncoding="UTF-8" %>
<%@ page import="model.*" %>
<%!
    /** Mã hóa ký tự đặc biệt của HTML để tránh XSS khi in dữ liệu ra trang. */
    static String esc(Object o) {
        if (o == null) {
            return "";
        }
        String s = String.valueOf(o);
        StringBuilder sb = new StringBuilder(s.length() + 16);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '&': sb.append("&amp;"); break;
                case '<': sb.append("&lt;"); break;
                case '>': sb.append("&gt;"); break;
                case '"': sb.append("&quot;"); break;
                case '\'': sb.append("&#39;"); break;
                default: sb.append(c);
            }
        }
        return sb.toString();
    }

    /** Định dạng ngày dd/MM/yyyy. */
    static String fmtDate(java.util.Date d) {
        return d == null ? "" : new java.text.SimpleDateFormat("dd/MM/yyyy").format(d);
    }

    /** Mã hóa giá trị đưa vào URL. */
    static String enc(String s) {
        try {
            return java.net.URLEncoder.encode(s == null ? "" : s, "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            return "";
        }
    }
%>
<%
    // ---- Kiểm tra đăng nhập và quyền theo thư mục (bd: bạn đọc, ql: quản lý, tv: thư viện) ----
    String ctx = request.getContextPath();
    Member7 me = (Member7) session.getAttribute("member");
    String uri = request.getRequestURI();
    boolean allowed = (me != null);
    if (allowed && uri.startsWith(ctx + "/Management_employee/")) {
        allowed = me instanceof Management_employee7;
    } else if (allowed && uri.startsWith(ctx + "/Library_employee/")) {
        allowed = me instanceof Library_employee7;
    } else if (allowed && uri.startsWith(ctx + "/Reader/")) {
        allowed = !(me instanceof Employee7);
    }
    if (!allowed) {
        response.sendRedirect(ctx + "/login.jsp");
        return;
    }
    String vaiTro = me instanceof Management_employee7 ? "Nhân viên quản lý"
            : me instanceof Library_employee7 ? "Nhân viên thư viện"
            : me instanceof Reader7 ? "Bạn đọc" : "Thành viên";
%>
<div class="topbar">
    <span class="brand">LibMan - Quản lý thư viện</span>
    <span><%= esc(me.getName()) %> (<%= vaiTro %>) <a href="<%= ctx %>/logout.jsp">Đăng xuất</a></span>
</div>
