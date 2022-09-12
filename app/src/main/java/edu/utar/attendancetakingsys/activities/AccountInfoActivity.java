package edu.utar.attendancetakingsys.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

import edu.utar.attendancetakingsys.R;
import edu.utar.attendancetakingsys.classes.DAOStudent;
import edu.utar.attendancetakingsys.classes.Student2;

public class AccountInfoActivity extends AppCompatActivity {

    TextView fullname, stuid, email, phone, faculty, year, semester;
    MaterialButton editName_btn, finishedit_btn, canceledit_btn;
    EditText editName;
    String TAG = "AccInfoAct";

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://webappdev-e8304-default-rtdb.asia-southeast1.firebasedatabase.app/");
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

        fullname = findViewById(R.id.profile_fullname_value);
        stuid = findViewById(R.id.profile_stuid_value);
        email = findViewById(R.id.email_profile);
        phone = findViewById(R.id.phone_profile);
        faculty = findViewById(R.id.faculty_profile);
        year = findViewById(R.id.year_profile);
        semester = findViewById(R.id.semester_profile);

        editName = findViewById(R.id.profile_edit_fullname);

        SharedPreferences sharedPref_stuInfo = getApplicationContext().getSharedPreferences("StudentInfo", 0);

        editName_btn = findViewById(R.id.editprofile_btn);
        finishedit_btn = findViewById(R.id.profile_finishedit_btn);
        canceledit_btn = findViewById(R.id.profile_canceledit_btn);

        mDatabase = database.getReference();

        mDatabase.child("studentlist").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Map<String, String>> result = (Map<String, Map<String, String>>) snapshot.getValue();

                String stuID = sharedPref_stuInfo.getString("StudentID", "Fail to retrieve Student ID");

                if (stuID!="Fail to retrieve Student ID"){
                    Map<String, String> stuInfo = result.get( stuID );
                    Student2 student = new Student2(stuInfo.get("email"), stuInfo.get("faculty"), stuInfo.get("phone"), stuInfo.get("semester"), stuInfo.get("studentID"),stuInfo.get("student_address"), stuInfo.get("student_gender"), stuInfo.get("student_sts"), stuInfo.get("studentname"), stuInfo.get("year"));

                    fullname.setText( student.getStudentname() );
                    stuid.setText( student.getStudentID() );
                    email.setText( student.getEmail() );
                    phone.setText( student.getPhone() );
                    faculty.setText( student.getFaculty() );
                    year.setText( student.getYear() );
                    semester.setText( student.getSemester() );

                    Log.d("firebase", student.getEmail());
                }
                else{
                    Log.w("ERROR", "Fail to retrieve Student ID from Shared Pref");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });


        editName_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editName.setVisibility(view.VISIBLE);
                fullname.setVisibility(view.INVISIBLE);
                finishedit_btn.setVisibility(view.VISIBLE);
                canceledit_btn.setVisibility(view.VISIBLE);
                editName_btn.setVisibility(view.INVISIBLE);
            }
        });


        finishedit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullname.setText(editName.getText().toString());
                DAOStudent daoStudent = new DAOStudent();

                daoStudent.editName(
                    sharedPref_stuInfo.getString("Current user ID", "Fail to retrieve UserID from shared preferences"),
                    editName.getText().toString()
                );

                editName.setVisibility(view.INVISIBLE);
                fullname.setVisibility(view.VISIBLE);
                editName_btn.setVisibility(view.VISIBLE);
                finishedit_btn.setVisibility(view.INVISIBLE);
                canceledit_btn.setVisibility(view.INVISIBLE);
            }
        });

        canceledit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editName.setVisibility(view.INVISIBLE);
                fullname.setVisibility(view.VISIBLE);
                editName_btn.setVisibility(view.VISIBLE);
                finishedit_btn.setVisibility(view.INVISIBLE);
                canceledit_btn.setVisibility(view.INVISIBLE);
            }
        });

    }
}