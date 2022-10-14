package com.minhassenhas.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.minhassenhas.models.PasswordObject;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Passwords.db";
    private static final int DATABASE_VERSION = 1;


    private static final String TABLE2_NAME = "main_password";
    private static final String COLUMN_MAIN_PASS = "my_main_password";


    private static final String TABLE_NAME = "my_passwords";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "password_name";
    private static final String COLUMN_EMAIL = "password_email";
    private static final String COLUMN_ACCOUNT = "password_account";
    private static final String COLUMN_PASSWORD = "password_password";
    private static final String COLUMN_DESCRIPTION = "password_description";


    public MyDataBaseHelper(@Nullable Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_EMAIL + " TEXT, " +
                        COLUMN_ACCOUNT + " TEXT, " +
                        COLUMN_PASSWORD + " TEXT, " +
                        COLUMN_DESCRIPTION + " TEXT);";

        db.execSQL(query);

        String query2 =
                "CREATE TABLE " + TABLE2_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_MAIN_PASS + " TEXT);";

        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME);
        onCreate(db);
    }

    public void addPassword(PasswordObject password) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, password.getName());
        cv.put(COLUMN_EMAIL, password.getEmail());
        cv.put(COLUMN_ACCOUNT, password.getConta());
        cv.put(COLUMN_PASSWORD, password.getPassword());
        cv.put(COLUMN_DESCRIPTION, password.getDescription());
        long result = db.insert(TABLE_NAME, null, cv);

        if(result == -1)
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        }

    }

    public Cursor readAllData()
    {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null)
            cursor = db.rawQuery(query, null);

        return cursor;
    }

    public void updatePassword(PasswordObject password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, password.getName());
        cv.put(COLUMN_EMAIL, password.getEmail());
        cv.put(COLUMN_ACCOUNT, password.getConta());
        cv.put(COLUMN_PASSWORD, password.getPassword());
        cv.put(COLUMN_DESCRIPTION, password.getDescription());

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[] {String.valueOf(password.getId())});

        if(result == -1)
        {
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
        }
    }

    public void deletePassword(PasswordObject password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{String.valueOf(password.getId())});

        if(result == -1)
        {
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean verifyIfMainPasswordExists() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE2_NAME;

        Cursor cursor = db.rawQuery(query,null);

        if(cursor.getCount() > 0)
            return true;

        return false;
    }

    public boolean verifyPassword(String inputedPassword) {

        String query = "SELECT * FROM " + TABLE2_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null)
            cursor = db.rawQuery(query, null);

        if(cursor.getCount() > 0)
        {
            while (cursor.moveToNext())
            {
                String senha = cursor.getString(1).toString();

                return senha.equals(inputedPassword);
            }
        }

        return false;
    }

    public void addMainPassword(String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_MAIN_PASS, password);
        long result = db.insert(TABLE2_NAME, null, cv);

        if(result == -1)
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        }

    }


}
