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
public class AboutBillDbHelper extends SQLiteOpenHelper {
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
		Log.d(TAG,"AboutBillDbHelper============================================================");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		mCreateSQL = "CREATE TABLE IF NOT EXISTS bill_info ("
				+ "_id INTEGER PRIMARY KEY  AUTOINCREMENT NOT NULL,"
				+ "date VARCHAR NOT NULL," + "month INTEGER NOT NULL," + "type INTEGER NOT NULL,"
				+ "amount DOUBLE NOT NULL," + "desc VARCHAR NOT NULL,"
				+ "create_time VARCHAR NOT NULL," + "update_time VARCHAR NULL"
				+ "user_id INTEGER NOT NULL"
				+ ")";
		Log.d(TAG, "create_sql:" + mCreateSQL);
		db.execSQL(mCreateSQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "ALTER TABLE bill_info ADD user_id INTEGER NOT NULL";
		Log.d(TAG,"===================================================update_sql:"+sql);
		System.out.println("=======================================sql="+sql);
		db.execSQL(sql);
	}

	// 根据序号删除记录
	public void delete(int id) {
		String delete_sql = String.format("delete from %s where _id=%d;", mTableName, id);
		Log.d(TAG, "delete sql="+delete_sql);
		mWriteDB.execSQL(delete_sql);
	}

	// 根据行号删除记录
	public void deleteByRowid(long rowid) {
		String delete_sql = String.format("delete from %s where rowid=%d;", mTableName, rowid);
		Log.d(TAG, "delete sql="+delete_sql);
		mWriteDB.execSQL(delete_sql);
	}

	protected List<?> query(String sql){
		return null;
	}

	// 根据序号查询记录
	public List<?> queryById(int id) {
		String sql = " _id=" + id + ";";
		return query(sql);
	}

	// 根据行号查询记录
	public List<?> queryByRowid(long rowid) {
		String sql = " rowid=" + rowid + ";";
		return query(sql);
	}

	// 查询所有记录
	public List<?> queryAll() {
		String sql = " 1=1;";
		return query(sql);
	}

	// 统计记录数量
	public int queryCount(String sql) {
		int count = 0;
		Cursor cursor = mReadDB.rawQuery(sql, null);
		count = cursor.getCount();
		cursor.close();
		Log.d(TAG, "count="+count+",sql="+sql);
		return count;
	}
}
