package com.example.android.campus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;


public class MainActivity extends AppCompatActivity {

    private static final String TAG="MainActivity";

    private DatabaseReference mDatabase;

    public FirebaseAuth mAuth= FirebaseAuth.getInstance();
    private EditText mFirstNamefield;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if(mAuth.getCurrentUser()!=null)
            startActivity(new Intent(getApplicationContext(),company_register.class));

    }

    public void msglog(View view)
    {
                 String err="Login!";
                Toast.makeText(MainActivity.this,err,Toast.LENGTH_SHORT).show();
        EditText editText=(EditText)findViewById(R.id.user_name);
        EditText editText1=(EditText)findViewById(R.id.password);
        /*if (editText.getText().toString().equals("Mayank") && editText1.getText().toString().equals("Khandelwal"))
        {
            String messa="Login Successful!";
            Toast.makeText(MainActivity.this,messa,Toast.LENGTH_SHORT).show();
        }
        else {
            err = "Login Failed!";
            Toast.makeText(MainActivity.this, err, Toast.LENGTH_SHORT).show();
        }*/



        //mDatabase = FirebaseDatabase.getInstance().getReference();
        //mFirstNamefield=(EditText)findViewById(R.id.user_name);
        //String name=mFirstNamefield.getText().toString().trim();
        //mDatabase.push().setValue(name);

        mAuth.signInWithEmailAndPassword(editText.getText().toString(), editText1.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        Toast.makeText(MainActivity.this, "Success",Toast.LENGTH_SHORT).show();

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(MainActivity.this, "Failed",Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });



    }
    public void msgreg(View view)
    {
        String err="Register!";
        Toast.makeText(MainActivity.this,err,Toast.LENGTH_SHORT).show();

        final Context context=this;
        Intent i=new Intent(context,first_register.class);
        startActivity(i);
    }

}
