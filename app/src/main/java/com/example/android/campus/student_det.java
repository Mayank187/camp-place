package com.example.android.campus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

/**
 * Created by MAYANK on 10-09-2017.
 */

public class student_det extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_det);

        stud_view svo=new stud_view();

        String name;
        String mail;
        String dob;
        String col;
        String grad;
        String cgpa;
        String deg;
        String bra;

        EditText ed1=(EditText)findViewById(R.id.ed1);
        EditText ed2=(EditText)findViewById(R.id.ed2);
        EditText ed3=(EditText)findViewById(R.id.ed3);
        EditText ed4=(EditText)findViewById(R.id.ed4);
        EditText ed5=(EditText)findViewById(R.id.ed5);
        EditText ed6=(EditText)findViewById(R.id.ed6);
        EditText ed7=(EditText)findViewById(R.id.ed7);
        EditText ed8=(EditText)findViewById(R.id.ed8);

    }
};