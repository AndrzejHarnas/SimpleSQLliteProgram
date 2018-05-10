package com.example.golabek.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseManager extends SQLiteOpenHelper {

    private static final String DEBUG_TAG="DataBaseMANAGER";

    private static final int DB_VERSION = 1;
    private static final String DB_Name = "database.db";
    private static final String DB_USERS_TABLE = "users";

    public static final String KEY_ID = "_id";
    public static final String ID_OPTION = "integer primary key autoincrement";
    public static final int ID_COLUMN = 0;
    public static final String USER_NAME = "Name";
    public static final String USER_OPTION = "text not null";
    public static final int USER_NAME_COLUMN = 1;
    public static final String PASSWORD = "Password";
    public static final String PASSWORD_OPTION = "text not null";
    public static final int PASSWORD_COLUMN = 2;

    private static final String DB_CREATE_USERS_TABLE = "create table " + DB_USERS_TABLE + "(  " + KEY_ID +
            " " + ID_OPTION + ", " + USER_NAME + " " + USER_OPTION + ", " + PASSWORD + " " + PASSWORD_OPTION + ");";

    private static final String DB_DROP_USERS_TABLE = "DROP TABLE IF EXISTS " +DB_USERS_TABLE;




    public DataBaseManager(Context context){

        super(context,"Base.db",null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DB_CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addRecord(String name,String passw){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues properties = new ContentValues();
        properties.put(USER_NAME,name);
        properties.put(PASSWORD,passw);
        db.insertOrThrow(DB_USERS_TABLE,null,properties);
    }

    public void delAllRecords(){

        SQLiteDatabase db = getWritableDatabase();
        String [] args ={"*"};
        db.delete(DB_USERS_TABLE,null,null);
    }

    public Cursor getAllRecords(){
        String[] columns = {KEY_ID,USER_NAME,PASSWORD};
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(DB_USERS_TABLE,columns,null,null,null,null,null);
        return cursor;
    }






}