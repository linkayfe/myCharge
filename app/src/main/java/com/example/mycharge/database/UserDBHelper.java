package com.example.mycharge.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.mycharge.bean.UserInfo;


public class UserDBHelper extends AboutUserDbHelper{

    private static String table_name = "user_info";

    public UserDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mTableName = table_name;
        mSelectSQL = String.format("select user_id,username,password from %s where ",mTableName);
    }

    @Override
    protected UserInfo query(String condition) {
        String sql = mSelectSQL+condition;
        Log.d(TAG,"query sql:"+sql);
        Cursor cursor = mReadDB.rawQuery(sql,null);
        UserInfo info = new UserInfo();
        if (cursor.moveToFirst()){
            info.setId(cursor.getLong(0));
            info.setUsername(cursor.getInt(1));
            info.setPassword(cursor.getString(2));
        }
        cursor.close();
        Log.d(TAG,"userInfo="+info);
        return info;
    }


}
