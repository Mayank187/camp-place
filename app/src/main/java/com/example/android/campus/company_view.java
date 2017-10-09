package com.example.android.campus;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
 * Created by MAYANK on 09-10-2017.
 */

public class company_view extends AppCompatActivity {

    private static final String TAG = "company_view";
    private Context context=this;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String uid;

    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_view);

        mListView=(ListView)findViewById(R.id.list_c);

        mAuth=FirebaseAuth.getInstance();
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mRef=mFirebaseDatabase.getReference().child("Company");
        FirebaseUser user=mAuth.getCurrentUser();
        uid=user.getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Toast.makeText(context,"Success"+user.getEmail(),Toast.LENGTH_LONG).show();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        mRef.addValueEventListener(new ValueEventListener() {
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
            companyinfo compInfo=new companyinfo();
            compInfo.setName(ds.child(uid).getValue(companyinfo.class).getName());
            compInfo.setCgpa(ds.child(uid).getValue(companyinfo.class).getCgpa());
            compInfo.setCity(ds.child(uid).getValue(companyinfo.class).getCity());
            compInfo.setSeats(ds.child(uid).getValue(companyinfo.class).getSeats());
            compInfo.setState(ds.child(uid).getValue(companyinfo.class).getState());
            compInfo.setAd(ds.child(uid).getValue(companyinfo.class).getAd());
            compInfo.setPh(ds.child(uid).getValue(companyinfo.class).getPh());
            compInfo.setMail(ds.child(uid).getValue(companyinfo.class).getMail());
            compInfo.setAtt(ds.child(uid).getValue(companyinfo.class).getAtt());
            compInfo.setHr(ds.child(uid).getValue(companyinfo.class).getHr());
            compInfo.setPin(ds.child(uid).getValue(companyinfo.class).getPin());

            /*Log.d(TAG,"showData: name"+compInfo.getName());
            Log.d(TAG,"showData: mail"+compInfo.getMail());
            Log.d(TAG,"showData: dob"+compInfo.getDob());
            Log.d(TAG,"showData: col"+compInfo.getCol());
            Log.d(TAG,"showData: grad"+compInfo.getGrad());
            Log.d(TAG,"showData: cgpa"+compInfo.getCgpa());
            Log.d(TAG,"showData: deg"+compInfo.getDeg());
            Log.d(TAG,"showData: bra"+compInfo.getBra());*/

            ArrayList<String> arr=new ArrayList<>();
            arr.add(compInfo.getName());
            arr.add(compInfo.getSeats());
            arr.add(compInfo.getCgpa());
            arr.add(compInfo.getAtt());
            arr.add(compInfo.getHr());
            arr.add(compInfo.getPh());
            arr.add(compInfo.getMail());
            arr.add(compInfo.getAd());
            arr.add(compInfo.getCity());
            arr.add(compInfo.getState());
            arr.add(compInfo.getPin());

            ArrayAdapter adapter=new ArrayAdapter(this,R.layout.company_view,arr);
            mListView.setAdapter(adapter);

        }
    }


    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
