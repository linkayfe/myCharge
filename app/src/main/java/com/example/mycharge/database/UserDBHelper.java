package com.example.mycharge.database;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;


import com.example.mycharge.bean.UserInfo;


public class UserDBHelper extends AboutUserDbHelper{

    private static String table_name = "user_info";

    private UserDBHelper(Context context) {
        super(context, db_name, null, 7);
        mTableName = table_name;
        mSelectSQL = String.format("select user_id,username,password from %s where ",mTableName);
    }

    private static UserDBHelper mHelper = null;
    public static UserDBHelper getInstance(Context context){
        if (mHelper == null){
            mHelper = new UserDBHelper(context);
        }
        return mHelper;
    }

    @Override
    protected UserInfo query(String condition) {
        String sql = mSelectSQL+condition;
        Log.d(TAG,"query sql:"+sql);
        Cursor cursor = mReadDB.rawQuery(sql,null);
        UserInfo info = new UserInfo();
        if (cursor.moveToFirst()){
            info.setId(cursor.getLong(0));
            info.setUsername(cursor.getString(1));
            info.setPassword(cursor.getString(2));
        }
        cursor.close();
        Log.d(TAG,"userInfo="+info);
        return info;
    }

    public static Long getIdByUsername(String username){
        String sql = "select user_id from user_info where username = "+username;
        if (mHelper.mReadDB==null){
            mHelper.mReadDB = mHelper.getReadableDatabase();
        }
        Cursor cursor = mHelper.mReadDB.rawQuery(sql,null);
        Log.d(TAG,"getIdByUsernameSql="+sql);
        if (cursor.moveToFirst()) {
            return cursor.getLong(0);
        } else {
            return -1L;
        }
    }
    public Long getUserId(String username){
        String sql = "select user_id from user_info where username = '"+username+"'";
        Cursor cursor = mReadDB.rawQuery(sql,null);
        if (cursor.moveToFirst()){
            return cursor.getLong(0);
        }else {
            return -1L;
        }
    }

}
