package edu.utar.attendancetakingsys.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import edu.utar.attendancetakingsys.R;
import edu.utar.attendancetakingsys.classes.DAOStudent;

public class AccountInfoActivity extends AppCompatActivity {

    TextView fullname, stuid;
    MaterialButton editName_btn, finishedit_btn, canceledit_btn;
    EditText editName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

        fullname = findViewById(R.id.profile_fullname_value);
        stuid = findViewById(R.id.profile_stuid_value);
        editName = findViewById(R.id.profile_edit_fullname);
        editName_btn = findViewById(R.id.editprofile_btn);
        finishedit_btn = findViewById(R.id.profile_finishedit_btn);
        canceledit_btn = findViewById(R.id.profile_canceledit_btn);

        SharedPreferences sharedPref_stuInfo = getApplicationContext().getSharedPreferences("StudentInfo", 0);

        fullname.setText(
                sharedPref_stuInfo.getString( "Student Full Name", "Fail to retrieve StudentID from shared preferences" )
        );
        stuid.setText(
                sharedPref_stuInfo.getString( "StudentID", "Fail to retrieve Student Full Name from shared preferences")
        );

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