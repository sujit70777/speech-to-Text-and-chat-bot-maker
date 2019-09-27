package com.example.sujit007.wwww;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Sujit007 on 4/30/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DatabaseName = "MyDataBase";
    public static final int DatabaseVersion = 1;

    // Class Table Attributes
    public static final String TableName = "dataTable";
    public static final String id = "id";
    public static final String Text = "text";
    public static final String Replay = "replay";
    public static final String Query = "CREATE TABLE " + TableName + " (" + id + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " + Text + " TEXT NOT NULL, " + Replay + " TEXT NOT NULL);";


    private static DataBaseHelper dataBaseHelper;

    private DataBaseHelper(Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
        Log.d("Table Created", "Database-------------");
    }

    public static DataBaseHelper getDbHelper(Context context) {
        if (dataBaseHelper == null) {
            dataBaseHelper = new DataBaseHelper(context);
        }
        return dataBaseHelper;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Query);
        Log.d("DataBase Created", "Database-------------");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
