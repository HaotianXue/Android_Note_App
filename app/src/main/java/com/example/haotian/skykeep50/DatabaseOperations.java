package com.example.haotian.skykeep50;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.haotian.skykeep50.TableData.TableInfo;

/**
 * Created by xuehaotian on 20/03/2016.
 */

/**Author:HaotianXue u5689296
 * Reference:https://www.youtube.com/watch?v=cp2rL3sAFmI**/

public class DatabaseOperations extends SQLiteOpenHelper {

    MainActivity mainActivity;
    public static final int database_version = 1;
    public String CREATE_QUERY = "CREATE TABLE "+TableInfo.TABLE_NAME
            +"("+TableInfo.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+TableInfo.CONTENT+" TEXT NOT NULL "+
            ")";


    public DatabaseOperations(Context context){
        super(context, TableInfo.DATABSE_NAME,null,database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void putInformation(SQLiteDatabase db,String content){
//        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
//        contentValues.put(TableInfo.ID,TableInfo.ID);
        contentValues.put(TableInfo.CONTENT,content);

        long result = db.insert(TableInfo.TABLE_NAME,null,contentValues);
    }

    public Cursor getInformation(SQLiteDatabase db){
//        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        Cursor cursor;
        String[] projection = {TableInfo.ID,TableInfo.CONTENT};
        cursor = db.query(TableInfo.TABLE_NAME,projection,null,null,null,null,null);
        return cursor;
    }

    public void deleteInformation(long id,SQLiteDatabase sqLiteDatabase){
        String selection = TableInfo.ID+" LIKE ?";
        String[] selection_args = {""+id};
        sqLiteDatabase.delete(TableInfo.TABLE_NAME, selection, selection_args);

//        mainActivity.listDataAdapter.notifyDataSetChanged();
    }

    public void updateInformation(String content,long id,SQLiteDatabase sqLiteDatabase){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableInfo.CONTENT,content);
//        contentValues.put(TableInfo.IMAGE,image);
//        contentValues.put(TableInfo.DESC,desc);
//        contentValues.put(TableInfo.PLACE,place);
        String selection = TableInfo.ID+" LIKE ?";
        String[] selection_args = {""+id};
        int count = sqLiteDatabase.update(TableInfo.TABLE_NAME,contentValues,selection,selection_args);
    }

}
