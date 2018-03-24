package com.antony.project;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.antony.project.Global.device1stat;
import static com.antony.project.Global.device2stat;
import static com.antony.project.Global.device3stat;
import static com.antony.project.Global.device4stat;


public class Analyse extends Activity{

    ApplianceDataBaseAdapter applianceDataBaseAdapter;
    ActivityDataBaseAdapter activityDataBaseAdapter;

    double c;
    String date;

    TextView txt1,dateConsumption,todayConsumption;
    EditText consumption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gauge);

        consumption = (EditText)findViewById(R.id.editText7);
        txt1=(TextView)findViewById(R.id.textView11);
        dateConsumption = (TextView)findViewById(R.id.consumptionDate);
        todayConsumption = (TextView)findViewById(R.id.textView3);

        applianceDataBaseAdapter=new ApplianceDataBaseAdapter(this);
        applianceDataBaseAdapter=applianceDataBaseAdapter.open();
        activityDataBaseAdapter = new ActivityDataBaseAdapter(this);
        activityDataBaseAdapter=activityDataBaseAdapter.open();

        Calendar d = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        date = df.format(d.getTime());
        if(activityDataBaseAdapter.getSinlgeEntry(date)==-1)
            todayConsumption.setText("NO ENTRY");
        else
            todayConsumption.setText(String.valueOf(activityDataBaseAdapter.getSinlgeEntry(date)));

        if(!device1stat&&!device2stat&&!device3stat&&device4stat){
            c=0;
        }
        if(device1stat){
            c = c + applianceDataBaseAdapter.getSinlgeEntry("Device 1");
        }
        if(device2stat){
            c = c + applianceDataBaseAdapter.getSinlgeEntry("Device 2");
        }
        if(device3stat){
            c = c + applianceDataBaseAdapter.getSinlgeEntry("Device 3");
        }
        if(device4stat){
            c = c + applianceDataBaseAdapter.getSinlgeEntry("Device 4");
        }
        txt1.setText(String.valueOf(c)+" W");
    }
    public void getConsumption(View view){
        String date = consumption.getText().toString();
        if(date.isEmpty()){
            consumption.setError("Enter a date");
        }
        else{
            if(activityDataBaseAdapter.getSinlgeEntry(date)==-1)
                dateConsumption.setText("NO ENTRY");
            else
                dateConsumption.setText(String.valueOf(activityDataBaseAdapter.getSinlgeEntry(date)));
        }

    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        activityDataBaseAdapter.close();
        applianceDataBaseAdapter.close();
    }
}