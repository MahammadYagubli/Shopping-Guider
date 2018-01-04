package com.example.mahammad.shopping_guider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class database extends SQLiteOpenHelper {
    public static final String database_name = "Data.db";
    public static final String table_name = "topics";
    private String nameoftopic = "";
    public database(Context context) {

        super(context, database_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + table_name + " ( id integer primary key autoincrement, topic text)");
        db.execSQL("create table putextra ( id integer primary key autoincrement, string text)");
    }

    public void shopping_list(String tablename) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("create table " + tablename + " ( id integer primary key autoincrement, name text)");
        db.close();
    }
    public void renew() {
        SQLiteDatabase db = this.getWritableDatabase();
              db.execSQL("drop table if exists putextra ");
               db.execSQL("create table putextra ( id integer primary key autoincrement, string text)");
               db.close();
    }


    public boolean insert_New_Item(String tablename, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_vallue = new ContentValues();
        content_vallue.put("name", name);
        long result = db.insert(tablename, null, content_vallue);

        if (result == -1) {

            return false;

        } else {

            return true;

        }

    }
    public boolean temporily( String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_vallue = new ContentValues();
        content_vallue.put("string", name);
        long result = db.insert("putextra", null, content_vallue);

        if (result == -1) {

            return false;

        } else {

            return true;

        }

    }
    public void removeSingleItem(String tablename, String title) {
        //Open the database
        SQLiteDatabase database = this.getWritableDatabase();

        //Execute sql query to remove from database
        //NOTE: When removing by String in SQL, value must be enclosed with ''
        database.execSQL("DELETE FROM " + tablename + " WHERE  name= '" + title + "'");

        //Close the database
        database.close();
    }

    public void Rename(String asa, String oldName, String newName) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("UPDATE " + asa + " SET name= '" + newName + "' WHERE name= '" + oldName+"';" );
        db.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + table_name);

    }

    public boolean insert_New_Topic_Name(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content_vallue = new ContentValues();
        content_vallue.put("topic", name);
        long result = db.insert(table_name, null, content_vallue);

        if (result == -1) {

            return false;

        } else {

            return true;

        }

    }
      public Cursor getAllData(String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + table, null);

        return res;
    }




    public void removeSingleContact(String title) {
        //Open the database
        SQLiteDatabase database = this.getWritableDatabase();

        //Execute sql query to remove from database
        //NOTE: When removing by String in SQL, value must be enclosed with ''
        database.execSQL("DELETE FROM " + table_name + " WHERE  topic= '" + title + "'");

        //Close the database
        database.close();
    }

    public void Rename(String oldName, String newName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + table_name + " SET topic='" + newName + "' WHERE topic='" + oldName + "' ");
        db.close();
    }

    public void rename_table(String oldName, String newName) {
        SQLiteDatabase db = this.getWritableDatabase();
         db.beginTransaction();
        try{
            db.execSQL(" ALTER TABLE " + oldName + " RENAME TO " + newName+";" );
            db.setTransactionSuccessful();
        }
        finally{
            db.endTransaction();
        }
        db.close();
    }

}
