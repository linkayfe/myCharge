package com.example.mycharge.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.mycharge.bean.UserInfo;

public abstract class AboutUserDbHelper extends SQLiteOpenHelper {

    protected static String TAG = "AboutUserDbHelper";
    protected static String db_name = "bill.sqlite";
    protected Context mContext;
    protected int mVersion;
    protected SQLiteDatabase mReadDB;
    protected SQLiteDatabase mWriteDB;
    protected String mTableName;
    protected String mCreateSQL;
    protected String mSelectSQL;

    public AboutUserDbHelper(@Nullable Context context, @Nullable String name,
                             @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
        mVersion = version;
        mWriteDB = this.getWritableDatabase();
        mReadDB = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        mCreateSQL = "CREATE TABLE IF NOT EXISTS user_info (" +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "username INTEGER NOT NULL," +
                "password VARCHAR NOT NULL);";
        Log.d(TAG,"user_sql:"+mCreateSQL);
        db.execSQL(mCreateSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    protected abstract UserInfo query(String sql);

    public UserInfo queryByUserId(Long userId){
        String sql = " user_id="+userId+";";
        return query(sql);
    }
}
