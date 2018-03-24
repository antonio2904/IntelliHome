package com.antony.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ApplianceDataBaseAdapter {
    static final String DATABASE_NAME = "appliance.db";
    static final int DATABASE_VERSION = 1;
    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table "+"APPLIANCE"+
            "( " +"ID"+" integer primary key autoincrement,"+ "DEVICE  text,POWER integer); ";
    // Variable to hold the database instance
    public SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private ApplianceDataBaseHelper dbHelper;
    public  ApplianceDataBaseAdapter(Context _context)
    {
        context = _context;
        dbHelper = new ApplianceDataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public  ApplianceDataBaseAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        db.close();
    }
    public void insertEntry(String device,int power)
    {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("DEVICE", device);
        newValues.put("POWER",power);

        // Insert the row into your table
        db.insert("APPLIANCE", null, newValues);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }
    public int getSinlgeEntry(String device)
    {
        Cursor cursor=db.query("APPLIANCE", null, " DEVICE=?", new String[]{device}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return -1;
        }
        cursor.moveToFirst();
        String power= cursor.getString(cursor.getColumnIndex("POWER"));
        cursor.close();
        int a = Integer.valueOf(power);
        return a;
    }
    public void  updateEntry(String device,int power)
    {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put("DEVICE", device);
        updatedValues.put("POWER",power);

        String where="DEVICE = ?";
        db.update("APPLIANCE",updatedValues, where, new String[]{device});
    }
}