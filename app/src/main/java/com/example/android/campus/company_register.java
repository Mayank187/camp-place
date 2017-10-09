package com.example.android.campus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Created by MAYANK on 09-10-2017.
 */

public class company_register extends AppCompatActivity {

    private static final String TAG = "company_register";
    private Context context=this;

    private Button b;
    private EditText ed1,ed2,ed3,ed4,ed5,ed6,ed7,ed8,ed9,ed10,ed11,ed12;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private String name,seats,cgpa,att,hr,ph,mail,ad1,ad2,ad,city,state,pin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_register);

        b=(Button)findViewById(R.id.crb);

        ed1=(EditText)findViewById(R.id.cName);
        ed2=(EditText)findViewById(R.id.cSeats);
        ed3=(EditText)findViewById(R.id.cCgpa);
        ed4=(EditText)findViewById(R.id.cAtt);
        ed5=(EditText)findViewById(R.id.hr);
        ed6=(EditText)findViewById(R.id.hrph);
        ed7=(EditText)findViewById(R.id.hrMail);
        ed8=(EditText)findViewById(R.id.ad1);
        ed9=(EditText)findViewById(R.id.ad2);
        ed10=(EditText)findViewById(R.id.cCity);
        ed11=(EditText)findViewById(R.id.cState);
        ed12=(EditText)findViewById(R.id.cPin);

        mAuth=FirebaseAuth.getInstance();
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mRef=mFirebaseDatabase.getReference().child("Company");

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

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Insterting values");

                name=ed1.getText().toString();
                seats=ed2.getText().toString();
                cgpa=ed3.getText().toString();
                att=ed4.getText().toString();
                hr=ed5.getText().toString();
                ph=ed6.getText().toString();
                mail=ed7.getText().toString();
                ad1=ed8.getText().toString();
                ad2=ed9.getText().toString();
                city=ed10.getText().toString();
                state=ed11.getText().toString();
                pin=ed11.getText().toString();

                ad=ad1+ad2;

                HashMap<String,String> dataMap=new HashMap<String, String>();
                dataMap.put("Name",name);
                dataMap.put("Seats",seats);
                dataMap.put("CGPA",cgpa);
                dataMap.put("Att",att);
                dataMap.put("HR",hr);
                dataMap.put("Phone",ph);
                dataMap.put("Mail",mail);
                dataMap.put("Ad",ad);
                dataMap.put("City",city);
                dataMap.put("State",state);
                dataMap.put("Pin",pin);

                FirebaseUser user=mAuth.getCurrentUser();
                String userID=user.getUid();
                mRef.child(userID);

                mRef.push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(company_register.this,"Registered",Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(context,company_view.class);
                            startActivity(i);

                        }
                        else
                        {
                            Toast.makeText(company_register.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

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
