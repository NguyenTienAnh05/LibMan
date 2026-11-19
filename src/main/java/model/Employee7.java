package model;

/** Nhân viên: kế thừa Member7, thêm vị trí công việc. */
public class Employee7 extends Member7 {
    private String position;

    public Employee7() {
    }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
}
