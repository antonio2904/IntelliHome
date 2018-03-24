package com.antony.project;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ActivityDataBaseAdapter {

    static final String DATABASE_NAME = "activity.db";
    static final int DATABASE_VERSION = 1;
    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table "+"ACTIVITY"+
            "( " +"ID"+" integer primary key autoincrement,"+ "DATE  text,CONSUMPTION integer); ";
    // Variable to hold the database instance
    public SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private ActivityDataBaseHelper dbHelper;
    public  ActivityDataBaseAdapter(Context _context)
    {
        context = _context;
        dbHelper = new ActivityDataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public  ActivityDataBaseAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        db.close();
    }
    public void insertEntry(String date,double consumption)
    {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("DATE", date);
        newValues.put("CONSUMPTION",consumption);

        // Insert the row into your table
        db.insert("ACTIVITY", null, newValues);
    }
    public double getSinlgeEntry(String date)
    {
        Cursor cursor=db.query("ACTIVITY", null, " DATE=?", new String[]{date}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return -1;
        }
        cursor.moveToFirst();
        String consumption = cursor.getString(cursor.getColumnIndex("CONSUMPTION"));
        cursor.close();
        double a = Double.valueOf(consumption);
        return a;
    }
    public void  updateEntry(String date,double consumption)
    {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put("DATE", date);
        updatedValues.put("CONSUMPTION",consumption);

        String where="DATE = ?";
        db.update("ACTIVITY",updatedValues, where, new String[]{date});
    }
}
