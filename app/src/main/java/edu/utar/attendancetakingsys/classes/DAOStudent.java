package edu.utar.attendancetakingsys.classes;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class DAOStudent {
    private DatabaseReference databaseReference;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://webappdev-e8304-default-rtdb.asia-southeast1.firebasedatabase.app/");
    private DatabaseReference mDatabase = database.getReference();
    private static final String TAG = "DAO Student";

    public DAOStudent(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference( Student.class.getSimpleName() );
    }

    public Task<Void> add( Student stu ){      // Separate business logic & ui
        String email_name = stu.studentEmail;
        String[] parts = email_name.split("\\.");

        if ( parts.length > 0){
            return mDatabase.child("email-studentID").child( parts[0] ).setValue( stu );
        }
        else{
            return mDatabase.child("email-studentID").child( "invalid email" ).setValue( stu );
        }
//        return mDatabase.child("email-studentID").child(stu.studentID).setValue( stu );
    }

    public Task<Void> editName( String userID, String newName ){      // Separate business logic & ui
        Map<String, String> name = new HashMap<>();
        name.put("fullname",newName);
        return mDatabase.child("Student").child(userID).setValue( name );

        // we are use add value event listener method
        // which is called with database reference.
    }
}
