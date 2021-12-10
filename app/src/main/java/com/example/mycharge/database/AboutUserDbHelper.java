package com.example.mycharge.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;
import android.util.Log;


import com.example.mycharge.bean.UserInfo;

@SuppressLint("DefaultLocale")
public abstract class AboutUserDbHelper extends SQLiteOpenHelper {

    protected static String TAG = "AboutUserDbHelper";
    protected static String db_name = "user.sqlite";
    protected Context mContext;
    protected int mVersion;
    protected SQLiteDatabase mReadDB;
    protected SQLiteDatabase mWriteDB;
    protected String mTableName;
    protected String mCreateSQL;
    protected String mSelectSQL;

    public AboutUserDbHelper(Context context, String name,
                            SQLiteDatabase.CursorFactory factory, int version) {
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
                "username VARCHAR NOT NULL UNIQUE," +
                "password VARCHAR NOT NULL);";
        Log.d(TAG,"user_sql:"+mCreateSQL);
        db.execSQL(mCreateSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    protected abstract UserInfo query(String sql);

    public UserInfo queryByUserId(String username,String password){
        String base64Pass = Base64.encodeToString(password.getBytes(), Base64.DEFAULT);
        String sql = " username='"+username+"' and password='"+base64Pass+"';";
        return query(sql);
    }

    public boolean register(String username,String password){
        String base64Pass = Base64.encodeToString(password.getBytes(), Base64.DEFAULT);
        ContentValues cv = new ContentValues();
        cv.put("username",username);
        cv.put("password",base64Pass);
        Log.d(TAG,"username="+username);
        return mWriteDB.insert(mTableName,"",cv)!=-1;
    }
}
