package com.example.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/** 数据库基本操作
*   1 插入
*   2 查询
*   3 修改
*   4 删除
* */
public class UserDAO {

    private MyOpenHelper helper;

    public UserDAO(Context context) {
        helper = new MyOpenHelper(context);
    }

    /*添加*/
    public void insert(User user){
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put("name", user.getName());
        contentValues.put("pwd", user.getPwd());
        database.insert("user", null, contentValues);
        database.close();
    }

    /*查询*/
    public User query(String name){
        User user = new User();
        SQLiteDatabase database = helper.getWritableDatabase();
        Cursor cursor = database.query("user", null, "name=?",
                new String[]{name}, null, null, null);
        while (cursor.moveToNext()){
            user.setName(cursor.getString(cursor.getColumnIndex("name")));
            user.setPwd(cursor.getString(cursor.getColumnIndex("pwd")));
        }

        database.close();
        return user;
    }

    /*修改*/
    public void update(String name, String pwd){
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("pwd", pwd);
        database.update("user", cv, "name=?", new String[]{name});
        database.close();
    }

    /*删除*/
    public void delete(String name){
        SQLiteDatabase database = helper.getWritableDatabase();
        database.delete("user", "name=?", new String[]{name});
        database.close();
    }
}
