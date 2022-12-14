package edu.utar.attendancetakingsys.classes;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Student {

    public String userID;
    public String studentID;
    public String studentEmail;
    public String osID;

    public Student() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Student(String userID, String studentID, String studentEmail, String osID) {
        this.userID = userID;
        this.studentID = studentID;
        this.studentEmail = studentEmail;
        this.osID = osID;
    }

}
