package com.example.android.campus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * Created by MAYANK on 16-08-2017.
 */

public class first_register extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_register);

    }

    public void nextOne(View view)
    {
        RadioButton r;
        RadioButton c;
        r=(RadioButton)findViewById(R.id.stud);
        c=(RadioButton)findViewById(R.id.comp);
        if(r.isChecked())
        {
            final Context context=this;
            Intent i=new Intent(context,reg.class);
            startActivity(i);
        }
        if (c.isChecked())
        {
            final Context context=this;
            Intent i=new Intent(context,regc.class);
            startActivity(i);
            String messa="Company Registration!";
            Toast.makeText(first_register.this,messa,Toast.LENGTH_SHORT).show();
        }

    }
}
