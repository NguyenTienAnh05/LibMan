package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Shared database connection for all DAO classes. */
public class DAO7 {
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/libman?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "270205";

    protected Connection con;

    public DAO7() {
        initConnection();
    }

    public void DAO() {
        initConnection();
    }

    private void initConnection() {
        if (con != null) {
            return;
        }
        try {
            try {
                Class.forName(DB_DRIVER);
            } catch (ClassNotFoundException e) {
                if ("com.mysql.cj.jdbc.Driver".equals(DB_DRIVER)) {
                    Class.forName("com.mysql.jdbc.Driver");
                } else if ("com.mysql.jdbc.Driver".equals(DB_DRIVER)) {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                } else {
                    throw e;
                }
            }
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Missing JDBC driver " + DB_DRIVER
                    + " in WEB-INF/lib.", e);
        } catch (SQLException e) {
            throw new IllegalStateException("Could not connect to database: " + DB_URL
                    + " (" + e.getMessage() + ")", e);
        }
    }

    public Connection getConnection() {
        return con;
    }

    public void close() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException ignore) {
        }
    }
}
