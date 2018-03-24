package com.antony.project;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class ListViews extends AppCompatActivity {

    private TextView textView,tempvalue,humvalue;

    private String extra,address;

    ProgressDialog progress;

    BluetoothAdapter myBluetooth;
    BluetoothSocket btSocket= null;

    private boolean isBtConnected = false;

    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    BluetoothDevice dispositivo;

    private static OutputStream mOutputStream;
    private static InputStream mInputStream;

    Thread workerThread;

    byte[] readBuffer;
    int readBufferPosition;
    char identifier;
    volatile boolean stopWorker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        tempvalue = (TextView)findViewById(R.id.tempvalue);
        humvalue = (TextView)findViewById(R.id.humvalue);
        textView = (TextView) findViewById(R.id.connection);
        Bundle i = getIntent().getExtras();
        extra = i.getString("device_address");
        if (!extra.equals("NO DEVICES CONNECTED")) {
            address = extra.substring(extra.length() - 17);
            extra = extra.substring(0, extra.length() - 17);
            new ConnectBT().execute();
        } else
            textView.setText(extra);
    }
    public void control(View view){
        Intent i = new Intent(ListViews.this,Control.class);
        startActivity(i);
    }
    public void analyse(View view){
        Intent i=new Intent(ListViews.this,Analyse.class);
        startActivity(i);
    }
    public void set(View view){
        Intent i = new Intent(ListViews.this,Set.class);
        startActivity(i);
    }
    public void bluetooth(View view){
        Intent i = new Intent(ListViews.this,Bluetooth.class);
        startActivity(i);
        finish();
    }
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>
    {
        private boolean ConnectSuccess = true;

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(ListViews.this, "Connecting...", "Please wait!!!");
        }

        @Override
        protected Void doInBackground(Void... devices)
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    myBluetooth.startDiscovery();
                    dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    mOutputStream = btSocket.getOutputStream();
                    mInputStream = btSocket.getInputStream();
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Connection Failed.Try again.");
                mOutputStream=null;
            }
            else
            {
                msg("Connected.");
                textView.setText(extra);
                beginListenForData();
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }
    public void temp(View view){
        try {
            mOutputStream.write("T".getBytes());
            identifier = 'T';
            beginListenForData();
        }
        catch(Exception e){
            msg("Error");
        }
    }

    public void humidity(View view){
        try {
            mOutputStream.write("H".getBytes());
            identifier = 'H';
            beginListenForData();
        }
        catch(Exception e){
            msg("Error");
        }
    }


    public static OutputStream getOutStream(){
        return mOutputStream;
    }
    void beginListenForData()
    {
        final Handler handler = new Handler();
        final byte delimiter = 10; //This is the ASCII code for a newline character

        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];
        workerThread = new Thread(new Runnable()
        {
            public void run()
            {
                while(!Thread.currentThread().isInterrupted() && !stopWorker)
                {
                    try
                    {
                        int bytesAvailable = mInputStream.available();
                        if(bytesAvailable > 0)
                        {
                            byte[] packetBytes = new byte[bytesAvailable];
                            mInputStream.read(packetBytes);
                            for(int i=0;i<bytesAvailable;i++)
                            {
                                byte b = packetBytes[i];
                                if(b == delimiter)
                                {
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    final String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;

                                    handler.post(new Runnable()
                                    {
                                        public void run()
                                        {
                                             if(identifier == 'T')
                                                tempvalue.setText(data + "°C");
                                            else
                                                humvalue.setText(data + "%");
                                        }
                                    });
                                }
                                else
                                {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    }
                    catch (IOException ex)
                    {
                        stopWorker = true;
                    }
                }
            }
        });

        workerThread.start();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(mOutputStream!=null&&isBtConnected) {
                            try {
                                mOutputStream.close();
                            } catch (IOException e) {
                                msg("error");
                            }
                            try {
                                btSocket.close();
                            } catch (IOException e) {
                                msg("error");
                            }
                        }
                        ListViews.this.finish();
                        }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
