package com.example.clrvalondo.finalexam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    Button login_btn, showPass;
    TextView txtsign;
    EditText pass;
    LoginDataBaseAdapter loginDataBaseAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        txtsign = (TextView) findViewById(R.id.txtsignup);
        pass = (EditText) findViewById(R.id.password_txt);
        login_btn = (Button) findViewById(R.id.login_btn);
        showPass = (Button) findViewById(R.id.show_btn);


        txtsign.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {

            final EditText editTextEmail = (EditText) findViewById(R.id.email_txt);
            final EditText ediTextPassword = (EditText) findViewById(R.id.password_txt);

            public void onClick(View v) {

                String email = editTextEmail.getText().toString();
                String password = ediTextPassword.getText().toString();
                String storedPassword = loginDataBaseAdapter.getSinlgeEntry(email);

                if (password.equals(storedPassword)) {
                    Toast.makeText(MainActivity.this, "Successfully logged in.", Toast.LENGTH_LONG).show();
                    //Toast.makeText(MainActivity.this, loginDataBaseAdapter.getSinlgeEntry(email), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), OnTouchListener.class);
                    startActivity(intent);
                    finish();
                 


                } else {
                    if (email.equals("") && password.equals("")) {
                        Toast.makeText(getApplicationContext(), "Please fill out all the field.", Toast.LENGTH_LONG).show();
                        return;
                    } else if (email.equals("") || password.equals("")) {
                        Toast.makeText(getApplicationContext(), "Please fill out the field.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Toast.makeText(MainActivity.this, "Incorrect Email or Password.", Toast.LENGTH_LONG).show();

                }

            }
        });

        showPass.setOnTouchListener(new View .OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent motionEvent)
            {
                int cursor = pass.getSelectionStart();

                switch(motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        pass.setTransformationMethod(null);
                        Log.d("Classname", "ACTION_DOWN");
                        pass.setSelection(cursor);
                        return true;
                    case MotionEvent.ACTION_UP:
                        //pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        pass.setTransformationMethod(new PasswordTransformationMethod());
                        Log.d("Classname", "ACTION_UP");
                        pass.setSelection(cursor);
                        return true;
                    case MotionEvent.ACTION_CANCEL:
                        //pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        pass.setTransformationMethod(new PasswordTransformationMethod());
                        pass.setSelection(cursor);
                        return true;
                    default:
                        return true;
                }

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginDataBaseAdapter.close();
    }
}
