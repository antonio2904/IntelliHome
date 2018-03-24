package com.antony.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Control extends AppCompatActivity {
    ImageView iv;

    ToggleButton button1,button2,button3,button4;

    Button button5,button6;

    OutputStream in;

    static double start1,start2,start3,start4;
    static double duration1,duration2,duration3,duration4;

    ActivityDataBaseAdapter activityDataBaseAdapter;
    ApplianceDataBaseAdapter applianceDataBaseAdapter;

    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        date = df.format(c.getTime());

        activityDataBaseAdapter = new ActivityDataBaseAdapter(this);
        activityDataBaseAdapter = activityDataBaseAdapter.open();
        applianceDataBaseAdapter = new ApplianceDataBaseAdapter(this);
        applianceDataBaseAdapter = applianceDataBaseAdapter.open();

        in = ListViews.getOutStream();

        iv=(ImageView)findViewById(R.id.imageView2);
        iv.setBackgroundResource(R.drawable.bulb);
        iv=(ImageView)findViewById(R.id.imageView3);
        iv.setBackgroundResource(R.drawable.fan);
        iv=(ImageView)findViewById(R.id.imageView4);
        iv.setBackgroundResource(R.drawable.bulb);
        iv=(ImageView)findViewById(R.id.imageView5);
        iv.setBackgroundResource(R.drawable.fan);

        button1=(ToggleButton)findViewById(R.id.toggleButton);
        button2=(ToggleButton)findViewById(R.id.toggleButton2);
        button3=(ToggleButton)findViewById(R.id.toggleButton3);
        button4=(ToggleButton)findViewById(R.id.toggleButton4);
        button5=(Button)findViewById(R.id.toggleButton8);
        button6=(Button)findViewById(R.id.toggleButton9);
        if(in==null){
            button1.setEnabled(false);
            button2.setEnabled(false);
            button3.setEnabled(false);
            button4.setEnabled(false);
            button5.setEnabled(false);
            button6.setEnabled(false);
        }
        if(Global.device1stat)
        {
            button1.setChecked(true);
        }
        if(Global.device2stat)
        {
            button2.setChecked(true);
        }
        if(Global.device3stat)
        {
            button3.setChecked(true);
        }
        if(Global.device4stat)
        {
            button4.setChecked(true);
        }
    }
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }
    public void device1(View view){
        try {
            if(!button1.isChecked()) {
                Global.device1stat=false;
               in.write("TF1".getBytes());
                if(start1!=0) {
                    duration1 = (System.currentTimeMillis() - start1);
                    if (activityDataBaseAdapter.getSinlgeEntry(date) == -1) {
                        activityDataBaseAdapter.insertEntry(date, (((duration1 / 3600000) * ((applianceDataBaseAdapter.getSinlgeEntry("Device 1"))/1000))));
                        start1 = 0;
                    }
                    else{
                        double z = activityDataBaseAdapter.getSinlgeEntry(date);
                        activityDataBaseAdapter.updateEntry(date, z + (((duration1 / 3600000 * applianceDataBaseAdapter.getSinlgeEntry("Device 1")/1000))));
                        start1=0;}
                }
            }
            else{
                Global.device1stat=true;
                in.write("TO1".getBytes());
                start1 = System.currentTimeMillis();
            }
        }
        catch(Exception e){
            msg("Not Connected");
        }
    }
    public void device2(View view){
        try {
            if(!button2.isChecked()) {
                Global.device2stat=false;
                in.write("TF2".getBytes());
                if(start2!=0) {
                    duration2 = (System.currentTimeMillis() - start2);
                    if (activityDataBaseAdapter.getSinlgeEntry(date) == -1) {
                        activityDataBaseAdapter.insertEntry(date, (((duration2 / 3600000) * ((applianceDataBaseAdapter.getSinlgeEntry("Device 2")) / 1000))));
                        start2 = 0;
                    }
                    else {
                        double z = activityDataBaseAdapter.getSinlgeEntry(date);
                        activityDataBaseAdapter.updateEntry(date, z + (((duration2 / 3600000) * ((applianceDataBaseAdapter.getSinlgeEntry("Device 2"))/1000))));
                        start2=0;
                    }
                }

            }
            else{
                Global.device2stat=true;
                in.write("TO2".getBytes());
                start2 = System.currentTimeMillis();

            }
        }
        catch(Exception e){
            msg("Not Connected");
        }

    }
    public void device3(View view){
        try {
            if(!button3.isChecked()) {
                Global.device3stat=false;
                in.write("TF3".getBytes());
                if(start3!=0) {
                    duration3 = (System.currentTimeMillis() - start3);
                    if (activityDataBaseAdapter.getSinlgeEntry(date) == -1) {
                        activityDataBaseAdapter.insertEntry(date, (((duration3 / 3600000) * ((applianceDataBaseAdapter.getSinlgeEntry("Device 3")) / 1000))));
                        start3 = 0;
                    }
                    else {
                        double z = activityDataBaseAdapter.getSinlgeEntry(date);
                        activityDataBaseAdapter.updateEntry(date, z + (((duration3 / 3600000) * ((applianceDataBaseAdapter.getSinlgeEntry("Device 3"))/1000))));
                        start3=0;
                    }
                }

            }
            else{
                Global.device3stat=true;
                in.write("TO3".getBytes());
                start3 = System.currentTimeMillis();

            }
        }
        catch(Exception e){
            msg("Not Connected");
        }

    }
    public void device4(View view){
        try {
            if(!button4.isChecked()) {
                Global.device4stat=false;
                in.write("TF4".getBytes());
                if(start4!=0) {
                    duration4 = (System.currentTimeMillis() - start4);
                    if (activityDataBaseAdapter.getSinlgeEntry(date) == -1) {
                        activityDataBaseAdapter.insertEntry(date, (((duration4 / 3600000) * ((applianceDataBaseAdapter.getSinlgeEntry("Device 4")) / 1000))));
                        start4 = 0;
                    }
                    else {
                        double z = activityDataBaseAdapter.getSinlgeEntry(date);
                        activityDataBaseAdapter.updateEntry(date, z + (((duration4 / 3600000) * ((applianceDataBaseAdapter.getSinlgeEntry("Device 4"))/1000))));
                        start4=0;
                    }
                }
            }
            else{
                Global.device4stat=true;
                in.write("TO4".getBytes());
                start4 = System.currentTimeMillis();
            }
        }
        catch(Exception e){
            msg("Not Connected");
        }
    }
    public void deviceallon(View view) {
        try {
            Global.device1stat = true;
            Global.device2stat = true;
            Global.device3stat = true;
            Global.device4stat = true;
            button1.setChecked(true);
            button2.setChecked(true);
            button3.setChecked(true);
            button4.setChecked(true);
            in.write("TO5".getBytes());
            if(start1==0)
                start1=System.currentTimeMillis();
            if(start2==0)
                start2=System.currentTimeMillis();
            if(start3==0)
                start3=System.currentTimeMillis();
            if(start4==0)
                start4=System.currentTimeMillis();

        } catch (Exception e) {
            msg("Not Connected");
        }
    }
    public void devicealloff(View view){
        try {
                Global.device1stat=false;
                Global.device2stat=false;
                Global.device3stat=false;
                Global.device4stat=false;
                button1.setChecked(false);
                button2.setChecked(false);
                button3.setChecked(false);
                button4.setChecked(false);
                in.write("TF5".getBytes());
            if(start1!=0){
                duration1 = (System.currentTimeMillis() - start1);
                if (activityDataBaseAdapter.getSinlgeEntry(date) == -1) {
                    activityDataBaseAdapter.insertEntry(date, (((duration1 / 3600000) * ((applianceDataBaseAdapter.getSinlgeEntry("Device 1")) / 1000))));
                    start1 = 0;
                }
                else {
                    double z = activityDataBaseAdapter.getSinlgeEntry(date);
                    activityDataBaseAdapter.updateEntry(date, z + (((duration1 / 3600000) * ((applianceDataBaseAdapter.getSinlgeEntry("Device 1"))/1000))));
                    start1=0;
                }
            }
            if(start2!=0){
                duration2 = (System.currentTimeMillis() - start2);
                if (activityDataBaseAdapter.getSinlgeEntry(date) == -1) {
                    activityDataBaseAdapter.insertEntry(date, ((duration2 / 3600000) * ((applianceDataBaseAdapter.getSinlgeEntry("Device 2")) / 1000)));
                    start2 = 0;
                }
                else {
                    double z = activityDataBaseAdapter.getSinlgeEntry(date);
                    activityDataBaseAdapter.updateEntry(date, z + ((duration2 / 3600000) * ((applianceDataBaseAdapter.getSinlgeEntry("Device 2"))/1000)));
                    start2=0;
                }
            }
            if(start3!=0){
                duration3 = (System.currentTimeMillis() - start3);
                if (activityDataBaseAdapter.getSinlgeEntry(date) == -1) {
                    activityDataBaseAdapter.insertEntry(date, (((duration3 / 3600000) * ((applianceDataBaseAdapter.getSinlgeEntry("Device 3")) / 1000))));
                    start3 = 0;
                }
                else {
                    double z = activityDataBaseAdapter.getSinlgeEntry(date);
                    activityDataBaseAdapter.updateEntry(date, z + (((duration3 / 3600000) * ((applianceDataBaseAdapter.getSinlgeEntry("Device 3"))/1000))));
                    start3=0;
                }
            }
            if(start4!=0){
                duration4 = (System.currentTimeMillis() - start4);
                if (activityDataBaseAdapter.getSinlgeEntry(date) == -1) {
                    activityDataBaseAdapter.insertEntry(date, (((duration4 / 3600000) * ((applianceDataBaseAdapter.getSinlgeEntry("Device 4")) / 1000))));
                    start4 = 0;
                }
                else {
                    double z = activityDataBaseAdapter.getSinlgeEntry(date);
                    activityDataBaseAdapter.updateEntry(date, z + (((duration4 / 3600000) * ((applianceDataBaseAdapter.getSinlgeEntry("Device 4"))/1000))));
                    start4=0;
                }
            }

        }
        catch(Exception e){
            msg("Not Connected");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        applianceDataBaseAdapter.close();
        activityDataBaseAdapter.close();
    }
}
