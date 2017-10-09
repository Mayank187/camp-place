package com.example.android.campus;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by MAYANK on 26-09-2017.
 */

public class stud_view extends AppCompatActivity{

    private static final String TAG="stud_view";

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabase;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String uid;

    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stud_view);

        mListView=(ListView)findViewById(R.id.list_v);

        mAuth=FirebaseAuth.getInstance();
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabase=mFirebaseDatabase.getReference().child("Student");
        FirebaseUser user=mAuth.getCurrentUser();
        uid=user.getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds:dataSnapshot.getChildren())
        {
            studentinfo studInfo=new studentinfo();
            studInfo.setBra(ds.child(uid).getValue(studentinfo.class).getBra());
            studInfo.setCgpa(ds.child(uid).getValue(studentinfo.class).getCgpa());
            studInfo.setCol(ds.child(uid).getValue(studentinfo.class).getCol());
            studInfo.setDeg(ds.child(uid).getValue(studentinfo.class).getDeg());
            studInfo.setDob(ds.child(uid).getValue(studentinfo.class).getDob());
            studInfo.setMail(ds.child(uid).getValue(studentinfo.class).getMail());
            studInfo.setGrad(ds.child(uid).getValue(studentinfo.class).getGrad());
            studInfo.setName(ds.child(uid).getValue(studentinfo.class).getName());

            Log.d(TAG,"showData: name"+studInfo.getName());
            Log.d(TAG,"showData: mail"+studInfo.getMail());
            Log.d(TAG,"showData: dob"+studInfo.getDob());
            Log.d(TAG,"showData: col"+studInfo.getCol());
            Log.d(TAG,"showData: grad"+studInfo.getGrad());
            Log.d(TAG,"showData: cgpa"+studInfo.getCgpa());
            Log.d(TAG,"showData: deg"+studInfo.getDeg());
            Log.d(TAG,"showData: bra"+studInfo.getBra());

            ArrayList<String> arr=new ArrayList<>();
            arr.add(studInfo.getName());
            arr.add(studInfo.getMail());
            arr.add(studInfo.getDob());
            arr.add(studInfo.getCol());
            arr.add(studInfo.getGrad());
            arr.add(studInfo.getCgpa());
            arr.add(studInfo.getDeg());
            arr.add(studInfo.getBra());

            ArrayAdapter adapter=new ArrayAdapter(this,R.layout.stud_view,arr);
            mListView.setAdapter(adapter);

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}

