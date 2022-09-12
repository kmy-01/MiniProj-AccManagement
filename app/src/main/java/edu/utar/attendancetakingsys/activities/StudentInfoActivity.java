package edu.utar.attendancetakingsys.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import edu.utar.attendancetakingsys.R;
import edu.utar.attendancetakingsys.classes.DAOStudent;
import edu.utar.attendancetakingsys.classes.Student;

public class StudentInfoActivity extends AppCompatActivity {

    TextView emailaddr;
    TextInputEditText stuID_input;
    MaterialButton submit_btn;


    private static final String TAG_stuInfo = "StudentInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);

        SharedPreferences sharedPref_userID = getApplicationContext().getSharedPreferences("StudentInfo", 0);
        String firebaseAuthUserID = sharedPref_userID.getString( "Current user ID", "Fail to retrieve userID from shared preferences" );
        String studentEmail = sharedPref_userID.getString( "Current user email", "Fail to retrieve student email from shared preferences" );

        Log.d(TAG_stuInfo, "SharedPref UID: " + firebaseAuthUserID);

        submit_btn = findViewById(R.id.submit_btn);
        stuID_input = findViewById(R.id.info_id_input);
        emailaddr = findViewById(R.id.info_emailaddr);
        emailaddr.setText(sharedPref_userID.getString( "Current user email", "Fail to retrieve userID from shared preferences" ));

        Context context = StudentInfoActivity.this;

        submit_btn.setOnClickListener(view -> {
            DAOStudent daoStudent = new DAOStudent();

            // Unique ID for the Android OS
            String AndroidID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

            String stuID = stuID_input.getText().toString();
            Student student = new Student(firebaseAuthUserID, stuID, studentEmail, AndroidID);

            daoStudent.add( student ).addOnSuccessListener( suc->{
                Toast.makeText(context, "Data is recorded", Toast.LENGTH_LONG).show();

                SharedPreferences sharedPref_StudentInfo = getSharedPreferences("StudentInfo", 0 );
                SharedPreferences.Editor sharedPrefEditor_userID = sharedPref_StudentInfo.edit();
                sharedPrefEditor_userID.putString("StudentID", student.studentID);
//                sharedPrefEditor_userID.putString("Student Full Name", student.fullname);
                sharedPrefEditor_userID.commit();

//                // TODO:  Change to main activity
                startActivity( new Intent( StudentInfoActivity.this, AccountInfoActivity.class ) );
            }).addOnFailureListener( err->{
                Toast.makeText(context, ""+ err.getMessage(), Toast.LENGTH_LONG).show();
            });
        });

    }

}