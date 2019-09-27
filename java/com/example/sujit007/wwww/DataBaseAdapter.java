package com.example.sujit007.wwww;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Sujit007 on 4/30/2017.
 */

public class DataBaseAdapter {

    DataBaseHelper dataBaseHelper;

    public DataBaseAdapter(Context context) {

        dataBaseHelper = dataBaseHelper.getDbHelper(context);

    }

    // -------------------- INSERTING DATA IN THE TABLE ------------

    public long Insert(String text, String replay) {
        long did;

        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.Text, text);
        contentValues.put(DataBaseHelper.Replay, replay);

        did = db.insert(DataBaseHelper.TableName, null, contentValues);

        db.close();

        return did;
    }


    // -------------------- DELETING DATA FROM CLASS TABLE ---------


    public void Delete(String id) {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();

        String[] cid = {id};

        db.delete(DataBaseHelper.TableName, DataBaseHelper.id + " = ?", cid);
        db.close();
    }

//------------------------UPDATING DATA FROM CLASS TABLE --------------

    public void Update(String id, String text, String replay) {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();

        String[] cid = {id};

        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.Text, text);
        contentValues.put(DataBaseHelper.Replay, replay);

        db.update(DataBaseHelper.TableName, contentValues, DataBaseHelper.id + " = ?", cid);

        db.close();
    }


    // ------------------- GETTING ALL THE DATA FROM THE Student TABLE ---------------

    public ArrayList<Data> getStudentData() {
        ArrayList<Data> studentInfoArrayList = new ArrayList<Data>();
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        String[] strings = {DataBaseHelper.id, DataBaseHelper.Text, DataBaseHelper.Replay};

        Cursor cursor = db.query(DataBaseHelper.TableName, strings, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.id));
            String text = cursor.getString(cursor.getColumnIndex(DataBaseHelper.Text));
            String replay = cursor.getString(cursor.getColumnIndex(DataBaseHelper.Replay));
            Data data = new Data();
            data.setId(id);
            data.setText(text);
            data.setReplay(replay);

            //    Log.v("DBHelper: ", "Name: "+id + "  " + class_id);
            //    Log.v("DBHelper: ", "Details: " + name);

            studentInfoArrayList.add(data);

        }
        db.close();
        cursor.close();
        return studentInfoArrayList;

    }


    //------------- Getting Replay----------------


    public String GetReplay(String text) {
        String replay = "";

        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        String[] strings = {DataBaseHelper.id, DataBaseHelper.Replay};
        String[] Text = {text};

        Cursor cursor = db.query(DataBaseHelper.TableName, strings, DataBaseHelper.Text + " = ?", Text, null, null, null);
        cursor.moveToNext();
        if(cursor.getCount() == 0){

            replay = "Replay Not Found";

        }else {
            int id = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.id));
            replay = cursor.getString(cursor.getColumnIndex(DataBaseHelper.Replay));
        }
        db.close();
        cursor.close();
        return replay;
    }


}
