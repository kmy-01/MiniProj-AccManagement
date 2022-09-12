package edu.utar.attendancetakingsys.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import edu.utar.attendancetakingsys.R;

public class LogInActivity extends AppCompatActivity {

    MaterialTextView forgotpw;
    TextInputEditText email_input, password_input;
    MaterialButton login_btn;
    private static final String TAG_login = "LogInActivity";
    FirebaseAuth mAuth; // Singleton
    private static final String TAG = "LogInActivity";

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://webappdev-e8304-default-rtdb.asia-southeast1.firebasedatabase.app/");
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        String AndroidID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        setContentView(R.layout.activity_log_in);

        login_btn = findViewById(R.id.login_btn);
        forgotpw = findViewById(R.id.forgotpw_label);
        Context context = LogInActivity.this;

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email_input = findViewById(R.id.login_email_input);
                password_input = findViewById(R.id.login_password_input);
                String email = email_input.getText().toString();
                String password = password_input.getText().toString();

                String email_name = email;
                String[] parts = email_name.split("\\.");
                mDatabase = database.getReference();
                if ( parts.length > 0){
                    mDatabase.child("email-studentID").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Map<String, Map<String, String>> result = (Map<String, Map<String, String>>) snapshot.getValue();
                            Map<String, String> stuInfo = result.get(parts[0]);
                            String studentDevice = stuInfo.get("osID");

                            if (studentDevice.equals(AndroidID)){
                                logInToAccount(mAuth, context, email, password);

                                Log.d(TAG_login, email);
                                Log.d(TAG_login, password);
                            }else{
                                Toast.makeText(getApplicationContext(), "Please use your phone to log in.", Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) { }
                    });
                }


            }
        });

        forgotpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG_login, "Clicked Forgot Password TextView");
                ShowResetPwDialog();
            }
        });
    }

    private void logInToAccount(FirebaseAuth mAuth, Context context, String email, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG_login, "signInWithEmail: success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            // TODO: Output UserID
                            String currentUserID = user.getUid();

                            SharedPreferences sharedPref_userID = getSharedPreferences("StudentInfo", 0 );
                            SharedPreferences.Editor sharedPrefEditor_userID = sharedPref_userID.edit();
                            sharedPrefEditor_userID.putString("Current user ID", currentUserID);
                            sharedPrefEditor_userID.commit();

                            // Go to page if log in is successful
                            Intent accInfo_intent = new Intent(context , AccountInfoActivity.class );
                            startActivity(accInfo_intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG_login, "signInWithEmail:failure", task.getException());
                            Toast.makeText(context, "Authentication failed.\nPlease try again.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
        // [END sign_in_with_email]
    }

    public void ShowResetPwDialog(){
        final Dialog dialog = new Dialog(LogInActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE );
        dialog.setCancelable(false);
        dialog.setContentView( R.layout.custom_dialog );

        MaterialButton submitbtn = dialog.findViewById( R.id.reset_pw_button );

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText email_reset_pw = dialog.findViewById( R.id.email_reset_input );
                String emailAddress = email_reset_pw.getText().toString().trim();


                if (emailAddress.isEmpty()){
                    email_reset_pw.setError("Email is required");
                    email_reset_pw.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){
                    email_reset_pw.setError("Please provide a valid email");
                    email_reset_pw.requestFocus();
                    return;
                }

                FirebaseAuth auth = FirebaseAuth.getInstance();

                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LogInActivity.this, "Link to reset password is sent to " + emailAddress, Toast.LENGTH_LONG).show();
                                    Log.d(TAG, "Password Reset Email sent.");
                                }
                                else{
                                    Toast.makeText(LogInActivity.this, "Fail to reset password", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                dialog.dismiss();
            }
        });

        dialog.show();
    }
}