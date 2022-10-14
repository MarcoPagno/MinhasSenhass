package com.minhassenhas.repositories;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.minhassenhas.AddPasswordObjectActivity;
import com.minhassenhas.MainActivity;
import com.minhassenhas.database.MyDataBaseHelper;
import com.minhassenhas.models.PasswordObject;

import java.util.ArrayList;

public class PasswordObjectRepository {

    private static PasswordObjectRepository repository;
    private ArrayList<PasswordObject> passwords = new ArrayList<>();

    public static PasswordObjectRepository getInstance(){
        if(repository == null)
        {
            repository = new PasswordObjectRepository();
        }
        return repository;
    }

    private PasswordObjectRepository(){ }

    public void save(PasswordObject password, Context context){
        MyDataBaseHelper myDB = new MyDataBaseHelper(context);
        myDB.addPassword(password);
        passwords.add(password);
    }

    public void delete(PasswordObject password, Context context){
        MyDataBaseHelper myDB = new MyDataBaseHelper(context);
        myDB.deletePassword(password);
        passwords.remove(password);
    }

    public ArrayList<PasswordObject> getAll(Context context){

        MyDataBaseHelper myDB = new MyDataBaseHelper(context);
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() > 0)
        {
            while (cursor.moveToNext())
            {
                passwords.add(new PasswordObject(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)));
            }
        }

        return passwords;
    }

    public PasswordObject getByIndex(int index){
        return passwords.get(index);
    }

    public void update(int index, PasswordObject password, Context context){
        MyDataBaseHelper myDB = new MyDataBaseHelper(context);
        myDB.updatePassword(password);
        passwords.set(index, password);
    }

    public void saveMainPassword(String password, Context context) {
        MyDataBaseHelper myDB = new MyDataBaseHelper(context);
        myDB.addMainPassword(password);
    }

}
