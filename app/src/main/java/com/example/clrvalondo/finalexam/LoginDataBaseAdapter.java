package com.example.clrvalondo.finalexam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LoginDataBaseAdapter {
    static final String DATABASE_NAME = "users.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table " + "USERS" +
            "( " + "ID" + " integer primary key autoincrement," + "EMAIL  text,PASSWORD text,FNAME text,LNAME text,UNAME text); ";

    public SQLiteDatabase db;
    private final Context context;
    private DataBaseHelper dbHelper;

    public LoginDataBaseAdapter(Context _context) {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public LoginDataBaseAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public SQLiteDatabase getDatabaseInstance() {
        return db;
    }

    public void insertEntry(String email, String pword, String fname, String lname, String uname) {
        ContentValues newValues = new ContentValues();
        newValues.put("EMAIL", email);
        newValues.put("PASSWORD", pword);
        newValues.put("FNAME", fname);
        newValues.put("LNAME", lname);
        newValues.put("UNAME", uname);

        db.insert("USERS", null, newValues);
        /**Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();**/
    }

    public String getSinlgeEntry(String em) {
        Cursor cursor = db.query("USERS", null, " EMAIL=?", new String[]{em}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();

            Cursor cursor2 = db.query("USERS", null, " UNAME=?", new String[]{em}, null, null, null);
            if (cursor2.getCount() < 1) // UserName Not Exist
            {
                cursor2.close();
                return "NOT EXIST";
            }

            cursor2.moveToFirst();
            String password2 = cursor2.getString(cursor2.getColumnIndex("PASSWORD"));
            cursor2.close();
            return password2;

        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;
    }


    public boolean existingValidator(String em) {
        Cursor cursor = db.query("USERS", null, " EMAIL=?", new String[]{em}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();

            Cursor cursor2 = db.query("USERS", null, " UNAME=?", new String[]{em}, null, null, null);
            if (cursor2.getCount()< 1) // UserName Not Exist
            {
                cursor2.close();
                return false;
                //not exist
            }

            cursor2.moveToFirst();
            String password2 = cursor2.getString(cursor2.getColumnIndex("PASSWORD"));
            cursor2.close();
            return true;

        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return true;
    }
}