package com.example.myapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {

    public MyOpenHelper(Context context) {
        super(context, "uims.db", null, 1);
    }

    @Override
    /*新建一张表*/
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table user (" +
                "id integer primary key autoincrement," +
                "name varchar(20)," +
                "pwd varchar(20)" +
                ")";
        db.execSQL(sql);
    }

    @Override
    /*更新表*/
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table if exists user";
        db.execSQL(sql);
        onCreate(db);
    }
}
