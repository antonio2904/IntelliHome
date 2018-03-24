package com.antony.project;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Set extends Activity {
    EditText text1,text2,text3,text4;

    ApplianceDataBaseAdapter applianceDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        applianceDataBaseAdapter=new ApplianceDataBaseAdapter(this);
        applianceDataBaseAdapter=applianceDataBaseAdapter.open();

        text1=(EditText)findViewById(R.id.editText);
        text2=(EditText)findViewById(R.id.editText2);
        text3=(EditText)findViewById(R.id.editText3);
        text4=(EditText)findViewById(R.id.editText6);

        text3.clearFocus();
    }
    public void device1set(View view){
        String device1=text1.getText().toString();
        if(device1.isEmpty()){
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
            text1.requestFocus();
        }
        else{
            Integer a=Integer.valueOf(device1);
            String s= "Device 1";
            if(applianceDataBaseAdapter.getSinlgeEntry("Device 1")==-1){
                applianceDataBaseAdapter.insertEntry(s, a);
            }
            else{
                applianceDataBaseAdapter.updateEntry("Device 1",a);
            }
            Toast.makeText(getApplicationContext(), "Successfull ", Toast.LENGTH_LONG).show();
        }
    }
    public void device2set(View view){
        String device2=text2.getText().toString();
        if(device2.isEmpty()){
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
            text2.requestFocus();
        }
        else{
            Integer a=Integer.valueOf(device2);
            String s= "Device 2";
            if(applianceDataBaseAdapter.getSinlgeEntry("Device 2")==-1){
                applianceDataBaseAdapter.insertEntry(s, a);
            }
            else{
                applianceDataBaseAdapter.updateEntry("Device 2",a);
            }
            Toast.makeText(getApplicationContext(), "Successfull ", Toast.LENGTH_LONG).show();
        }
    }
    public void device3set(View view){
        String device3=text3.getText().toString();
        if(device3.isEmpty()){
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
            text3.requestFocus();
        }
        else{
            Integer a=Integer.valueOf(device3);
            String s= "Device 3";
            if(applianceDataBaseAdapter.getSinlgeEntry("Device 3")==-1){
                applianceDataBaseAdapter.insertEntry(s, a);
            }
            else{
                applianceDataBaseAdapter.updateEntry("Device 3",a);
            }
            Toast.makeText(getApplicationContext(), "Successfull ", Toast.LENGTH_LONG).show();
        }
    }
    public void device4set(View view){
        String device4=text4.getText().toString();
        if(device4.isEmpty()){
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
            text4.requestFocus();
        }
        else{
            Integer a=Integer.valueOf(device4);
            String s= "Device 4";
            if(applianceDataBaseAdapter.getSinlgeEntry("Device 4")==-1){
                applianceDataBaseAdapter.insertEntry(s, a);
            }
            else{
                applianceDataBaseAdapter.updateEntry("Device 4",a);
            }
            Toast.makeText(getApplicationContext(), "Successfull ", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        applianceDataBaseAdapter.close();
    }
}
