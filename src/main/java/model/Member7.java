package model;

import java.io.Serializable;
import java.sql.Date;

/**
 * Thành viên hệ thống (theo sơ đồ thiết kế):
 * - ID : integer
 * - username : String
 * - password : String
 * - name : String
 * - Dob : Date
 * - Email : String
 * - Phone : String
 */
public class Member7 implements Serializable {
    private static final long serialVersionUID = 1L;

    private int ID;
    private String username;
    private String password;
    private String name;
    private String address;
    private Date dob;
    private String email;
    private String phone;

    public Member7() {
    }

    public int getID() { return ID; }
    public void setID(int ID) { this.ID = ID; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Date getDob() { return dob; }
    public void setDob(Date dob) { this.dob = dob; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
