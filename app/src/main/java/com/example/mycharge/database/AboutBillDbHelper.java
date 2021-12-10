package com.example.mycharge.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

@SuppressLint("DefaultLocale")
public abstract class AboutBillDbHelper extends SQLiteOpenHelper {
	protected static String TAG = "AboutBillDbHelper";
	protected static String db_name = "bill.sqlite";
	protected Context mContext;
	protected int mVersion;
	protected SQLiteDatabase mReadDB;
	protected SQLiteDatabase mWriteDB;
	protected String mTableName;
	protected String mCreateSQL;
	protected String mSelectSQL;

	public AboutBillDbHelper(Context context, String name,
							 CursorFactory factory, int version) {
		super(context, name, factory, version);
		mContext = context;
		mVersion = version;
		mWriteDB = this.getWritableDatabase();
		mReadDB = this.getReadableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		mCreateSQL = "CREATE TABLE IF NOT EXISTS bill_info ("
				+ "_id INTEGER PRIMARY KEY  AUTOINCREMENT NOT NULL,"
				+ "date VARCHAR NOT NULL,"
				+ "month INTEGER NOT NULL,"
				+ "type INTEGER NOT NULL,"
				+ "amount DOUBLE NOT NULL,"
				+ "describe VARCHAR NOT NULL,"
				+ "create_time VARCHAR NOT NULL,"
				+ "update_time VARCHAR NULL,"
				+ "user_id INTEGER NOT NULL"
				+ ")";
		Log.d(TAG, "create_sql:" + mCreateSQL);
		db.execSQL(mCreateSQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	// 根据序号删除记录
	public void delete(int id) {
		String delete_sql = String.format("delete from %s where _id=%d;", mTableName, id);
		Log.d(TAG, "delete sql="+delete_sql);
		mWriteDB.execSQL(delete_sql);
	}

	protected abstract List<?> query(String sql);

	// 根据序号查询记录
	public List<?> queryById(int id) {
		String sql = " _id=" + id + ";";
		return query(sql);
	}


}
