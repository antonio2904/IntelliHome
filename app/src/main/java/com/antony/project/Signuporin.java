package com.antony.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Signuporin extends Activity implements View.OnClickListener{

    private EditText editTextUsername;
    private EditText editTextPassword;

    private String username;
    private String password;

    boolean flag;

    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signuporin);

        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        editTextUsername = (EditText) findViewById(R.id.editText4);
        editTextPassword = (EditText) findViewById(R.id.editText5);
    }
    public void signup(View view)
    {
        Intent i = new Intent(Signuporin.this,SignUPActivity.class);
        startActivity(i);
        finish();
    }
    private void userLogin() {
        flag = true;
        username = editTextUsername.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        if (username.isEmpty()) {
            editTextUsername.setError("Enter Username");
            flag = false;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Enter Password");
            flag = false;
        }
        if(flag){
            if(password.equals(loginDataBaseAdapter.getSinlgeEntry(username))){
                openProfile();
            }
            else{
                Toast.makeText(this,"Wrong Username or Password",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void openProfile(){
        Intent intent = new Intent(this, ListViews.class);
        intent.putExtra("device_address","NO DEVICES CONNECTED");
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        userLogin();
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        loginDataBaseAdapter.close();
    }
}