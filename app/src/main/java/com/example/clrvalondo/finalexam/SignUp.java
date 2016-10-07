package com.example.clrvalondo.finalexam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends Activity
{

    boolean emailValidator(CharSequence email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    boolean passwordValidator(String password) {
        if(password.length() > 7) {
            return true;
        } else {
            return false;
        }
    }

    boolean nameValidator(String name) {
        String flname = "[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(flname);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    EditText remail, rpass, rconfpass, rfname, rlname, runame;
    Button register;
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();

        remail =(EditText)findViewById(R.id.email);
        rpass=(EditText)findViewById(R.id.pwd);
        rconfpass=(EditText)findViewById(R.id.cpwd);
        rfname=(EditText)findViewById(R.id.fname);
        rlname=(EditText)findViewById(R.id.lname);
        runame=(EditText)findViewById(R.id.uname);
        register =(Button)findViewById(R.id.ok_btn);
        register.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String email= remail.getText().toString();
                String password=rpass.getText().toString();
                String confirmPassword=rconfpass.getText().toString();
                String fname=rfname.getText().toString();
                String lname=rlname.getText().toString();
                String username=runame.getText().toString();


                if(email.equals("")||password.equals("")||confirmPassword.equals("")||fname.equals("")||lname.equals("")||username.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Please fill out all the field.", Toast.LENGTH_LONG).show();
                    return;
                }

                if(!nameValidator(rfname.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "Invalid First name.", Toast.LENGTH_LONG).show();
                    return;
                }

                if(!nameValidator(rlname.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "Invalid Last name.", Toast.LENGTH_LONG).show();
                    return;
                }

                if(emailValidator(runame.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "Username cannot be an email format.", Toast.LENGTH_LONG).show();
                    return;
                }

                if(emailValidator(remail.getText())==false){
                    Toast.makeText(getApplicationContext(), "Invalid Email address.", Toast.LENGTH_LONG).show();
                    return;
                }

                if(!password.equals(confirmPassword))
                {
                    Toast.makeText(getApplicationContext(), "Password does not match.", Toast.LENGTH_LONG).show();
                    return;
                }

                if(loginDataBaseAdapter.existingValidator(runame.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "Username already exist.", Toast.LENGTH_LONG).show();
                    return;
                }

                if(loginDataBaseAdapter.existingValidator(remail.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "Email address already exist.", Toast.LENGTH_LONG).show();
                    return;
                }

                else
                {
                    if(emailValidator(remail.getText()) == true &&
                            passwordValidator(rpass.getText().toString()) == true) {
                        loginDataBaseAdapter.insertEntry(remail.getText().toString(), rpass.getText().toString(), rfname.getText().toString(), rlname.getText().toString(), runame.getText().toString());
                        Toast.makeText(getApplicationContext(), "Account Successfully Created.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    else if(emailValidator(remail.getText()) == true &&
                            passwordValidator(rpass.getText().toString()) == false){
                        Toast.makeText(getApplicationContext(), "Password must be at least 8 character.", Toast.LENGTH_LONG).show();
                        return;
                    }
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

