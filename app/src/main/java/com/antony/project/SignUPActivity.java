package com.antony.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUPActivity extends Activity implements View.OnClickListener {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextConfirm;

    private Button buttonRegister;

    boolean flag;

    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        editTextUsername = (EditText) findViewById(R.id.editTextUserName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextConfirm = (EditText)findViewById(R.id.editTextConfirmPassword);

        buttonRegister = (Button) findViewById(R.id.buttonCreateAccount);

        buttonRegister.setOnClickListener(this);
    }

    private void registerUser() {
        flag = true;
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String confirm = editTextConfirm.getText().toString();
        if (username.trim().isEmpty()) {
            editTextUsername.setError("Enter Username");
            flag = false;
        }
        if (password.trim().isEmpty()) {
           editTextPassword.setError("Enter Password");
            flag = false;
        }
        else if(password.length()<8){
            editTextPassword.setError("Password must be 8 characters long");
            flag = false;
        }
        if(confirm.trim().isEmpty()){
            editTextConfirm.setError("Confirm Password");
            flag = false;
        }
        if(flag){
            if(password.equals(confirm)){
               if(loginDataBaseAdapter.getSinlgeEntry(username).equals("noentry")) {
                   loginDataBaseAdapter.insertEntry(username, password);
                   Toast.makeText(this,"Account Created Successfully",Toast.LENGTH_LONG).show();
                   Intent intent = new Intent(this, Signuporin.class);
                   startActivity(intent);
                   finish();
               }
               else
                   editTextUsername.setError("Username already exist");
            }
            else
                editTextConfirm.setError("Password does not match");
        }
    }
    @Override
    public void onClick(View v) {
        if (v == buttonRegister) {
            registerUser();
        }
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        loginDataBaseAdapter.close();
    }
}
