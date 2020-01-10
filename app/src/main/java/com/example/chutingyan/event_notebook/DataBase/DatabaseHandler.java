package com.example.chutingyan.event_notebook.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.chutingyan.event_notebook.Model.Event;
import com.example.chutingyan.event_notebook.Utils.Database_Constants;


import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private Context context;
    public DatabaseHandler(Context context) {
        super(context, Database_Constants.DB_NAME, null, Database_Constants.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_EVENT_TABLE = "CREATE TABLE " + Database_Constants.TABLE_NAME + "("
                + Database_Constants.KEY_ID + " INTEGER PRIMARY KEY," + Database_Constants.KEY_EVENT_TIME
                + " TEXT," + Database_Constants.KET_EVENT_DESCRIPTION + " TEXT);";

        db.execSQL(CREATE_EVENT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + Database_Constants.TABLE_NAME);
        onCreate(db);
    }

    //Add
    public void AddEvent(Event event){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Database_Constants.KEY_EVENT_TIME, event.getTime());
        values.put(Database_Constants.KET_EVENT_DESCRIPTION, event.getDescription());

        db.insert(Database_Constants.TABLE_NAME, null, values);

    }



    //Get All Events
    public List<Event> GetAllEvents(){

        SQLiteDatabase db = this.getReadableDatabase();

        List<Event> events = new ArrayList<>();

        Cursor cursor = db.query(Database_Constants.TABLE_NAME, new String[]{Database_Constants.KEY_ID,
         Database_Constants.KEY_EVENT_TIME, Database_Constants.KET_EVENT_DESCRIPTION}, null,
                null,null, null, Database_Constants.KEY_ID + " DESC");

        if (cursor.moveToFirst()){
            do {
                Event event = new Event();
                event.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Database_Constants.KEY_ID))));
                event.setTime(cursor.getString(cursor.getColumnIndex(Database_Constants.KEY_EVENT_TIME)));
                event.setDescription(cursor.getString(cursor.getColumnIndex(Database_Constants.KET_EVENT_DESCRIPTION)));

                events.add(event);

            }while (cursor.moveToNext());
        }

        return events;
    }


    //Update
    public int UpdateEvents(Event event){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Database_Constants.KEY_EVENT_TIME, event.getTime());
        values.put(Database_Constants.KET_EVENT_DESCRIPTION, event.getDescription());



        return db.update(Database_Constants.TABLE_NAME, values, Database_Constants.KEY_ID + "=?",
                new String[]{String.valueOf(event.getId())});
    }


    //Delete
    public void DeleteEvents(int id){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Database_Constants.TABLE_NAME, Database_Constants.KEY_ID + "=?",
                new String[]{String.valueOf(id)});

        db.close();

    }



}
