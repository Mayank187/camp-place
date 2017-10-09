package com.example.android.campus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

/**
 * Created by MAYANK on 02-08-2017.
 */

public class register_screen extends AppCompatActivity {


    EditText mEmail=(EditText)findViewById(R.id.un);
    EditText mPassword=(EditText)findViewById(R.id.pass);
    Context context=this;
    String userID;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabase;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText mFirstNamefield;
    private EditText mLastNameField;
    private EditText mEmailField;
    private EditText mDOB;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

        mAuth=FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Toast.makeText(register_screen.this, "Failed",Toast.LENGTH_SHORT).show();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    public void msgreg(View view)
    {

        String email=mEmail.getText().toString();
        String password=mPassword.getText().toString();
        mAuth.signInWithEmailAndPassword(email,password);
        String s="Student";

        mDatabase = FirebaseDatabase.getInstance().getReference().child(String.valueOf(s));

        mFirstNamefield=(EditText)findViewById(R.id.firstName);
        mLastNameField=(EditText)findViewById(R.id.lastName);
        String name=mFirstNamefield.getText().toString().trim()+" "+mLastNameField.getText().toString().trim();

        mEmailField=(EditText)findViewById(R.id.email);
        String mail=mEmailField.getText().toString().trim();

        mDOB=(EditText)findViewById(R.id.dob);
        String dob=mDOB.getText().toString().trim();

        mDOB=(EditText)findViewById(R.id.col);
        String col=mDOB.getText().toString().trim();

        mDOB=(EditText)findViewById(R.id.grad);
        String grad=mDOB.getText().toString().trim();

        mDOB=(EditText)findViewById(R.id.cgpa);
        String cgpa=mDOB.getText().toString().trim();

        mDOB=(EditText)findViewById(R.id.deg);
        String deg=mDOB.getText().toString().trim();

        mDOB=(EditText)findViewById(R.id.bra);
        String bra=mDOB.getText().toString().trim();

        HashMap<String,String> dataMap=new HashMap<String, String>();
        dataMap.put("Name",name);
        dataMap.put("Email",mail);
        dataMap.put("DOB",dob);
        dataMap.put("College",col);
        dataMap.put("Graduation",grad);
        dataMap.put("CGPA",cgpa);
        dataMap.put("DEGREE",deg);
        dataMap.put("BRANCH",bra);

        mDatabase.push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(register_screen.this,"Registered",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(register_screen.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mChildEventListener=new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                student_det sv=dataSnapshot.getValue(student_det.class);
                FirebaseUser user=mAuth.getCurrentUser();
                userID=user.getUid();
                Intent i=new Intent(context,stud_view.class);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addChildEventListener(mChildEventListener);


    }

}
