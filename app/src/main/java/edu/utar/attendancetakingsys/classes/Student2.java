package edu.utar.attendancetakingsys.classes;

public class Student2 {
    private String email;
    private String faculty;
    private String phone;
    private String semester;
    private String studentID;
    private String student_address;
    private String student_gender;
    private String student_sts;
    private String studentname;
    private String year;

    public Student2(String email, String faculty, String phone, String semester, String studentID, String student_address, String student_gender, String student_sts, String studentname, String year) {
        this.email = email;
        this.faculty = faculty;
        this.phone = phone;
        this.semester = semester;
        this.studentID = studentID;
        this.student_address = student_address;
        this.student_gender = student_gender;
        this.student_sts = student_sts;
        this.studentname = studentname;
        this.year = year;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudent_address() {
        return student_address;
    }

    public void setStudent_address(String student_address) {
        this.student_address = student_address;
    }

    public String getStudent_gender() {
        return student_gender;
    }

    public void setStudent_gender(String student_gender) {
        this.student_gender = student_gender;
    }

    public String getStudent_sts() {
        return student_sts;
    }

    public void setStudent_sts(String student_sts) {
        this.student_sts = student_sts;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
