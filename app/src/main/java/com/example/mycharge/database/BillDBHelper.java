package com.example.mycharge.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;


import com.example.mycharge.bean.BillInfo;
import com.example.mycharge.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class BillDBHelper extends AboutBillDbHelper {
    private static String table_name = "bill_info";

    private BillDBHelper(Context context) {
        super(context, db_name, null, 8);
        mTableName = table_name;
        mSelectSQL = String.format("select rowid,_id,date,month,type,amount,desc,create_time,update_time" +
                        " from %s where "
                , mTableName);
    }

    private static BillDBHelper mHelper = null; // 数据库帮助器的实例
    // 利用单例模式获取数据库帮助器的唯一实例
    public static BillDBHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new BillDBHelper(context);
        }
        return mHelper;
    }

    // 往该表添加一条记录
    public long insert(BillInfo info) {
        List<BillInfo> infoList = new ArrayList<BillInfo>();
        infoList.add(info);
        return insert(infoList);
    }

    // 往该表添加多条记录
    public long insert(List<BillInfo> infoList) {
        long result = -1;
        for (int i = 0; i < infoList.size(); i++) {
            BillInfo info = infoList.get(i);
            List<BillInfo> tempList = new ArrayList<BillInfo>();
            // 不存在唯一性重复的记录，则插入新记录
            ContentValues cv = new ContentValues();
            cv.put("date", info.getDate());
            cv.put("month", info.getMonth());
            cv.put("type", info.getType());
            cv.put("amount", info.getAmount());
            cv.put("desc", info.getDesc());
            cv.put("create_time", info.getCreate_time());
            cv.put("update_time", info.getUpdate_time());
            cv.put("user_id",info.getUserId());
            Log.d(TAG, "month="+info.getMonth());
            // 执行插入记录动作，该语句返回插入记录的行号
            result = mWriteDB.insert(mTableName, "", cv);
            if (result == -1) { // 添加成功则返回行号，添加失败则返回-1
                return result;
            }
        }
        Log.d(TAG, "result="+result);
        return result;
    }

    // 根据条件更新指定的表记录
    public int update(BillInfo info, String condition) {
        ContentValues cv = new ContentValues();
        cv.put("date", info.getDate());
        cv.put("month", info.getMonth());
        cv.put("type", info.getType());
        cv.put("amount", info.getAmount());
        cv.put("desc", info.getDesc());
        cv.put("create_time", info.getCreate_time());
        cv.put("update_time", info.getUpdate_time());
        // 执行更新记录动作，该语句返回更新的记录数量
        return mWriteDB.update(mTableName, cv, condition, null);
    }

    public int update(BillInfo info) {
        // 执行更新记录动作，该语句返回更新的记录数量
        return update(info, "rowid=" + info.getRowid());
    }

    // 根据指定条件查询记录，并返回结果数据列表
    public List<BillInfo> query(String condition) {
        String sql = mSelectSQL + condition;
        Log.d(TAG, "query sql: " + sql);
        List<BillInfo> infoList = new ArrayList<BillInfo>();
        // 执行记录查询动作，该语句返回结果集的游标
        Cursor cursor = mReadDB.rawQuery(sql, null);
        // 循环取出游标指向的每条记录
        while (cursor.moveToNext()) {
            BillInfo info = new BillInfo();
            info.setRowid(cursor.getLong(0));// 取出长整型数
            info.setXuhao(cursor.getInt(1));   // 取出整型数
            info.setDate(cursor.getString(2));  // 取出字符串
            info.setMonth(cursor.getInt(3));   // 取出整型数
            info.setType(cursor.getInt(4));  // 取出整型数
            info.setAmount(cursor.getDouble(5));  // 取出双精度数
            info.setDesc(cursor.getString(6));   // 取出字符串
            info.setCreate_time(cursor.getString(7));   // 取出字符串
            info.setUpdate_time(cursor.getString(8));  // 取出字符串
            infoList.add(info);
        }
        cursor.close(); // 查询完毕，关闭数据库游标
        Log.d(TAG, "infoList.size="+infoList.size());
        return infoList;
    }

    public List<BillInfo> queryByMonth(int month,String username) {
        Long id = UserDBHelper.getIdByUsername(username);
        return query("month="+month+" and user_id="+id+" order by date asc");
    }

    public void save(BillInfo bill,String username) {
        //通过username获取用户id
        Long id = UserDBHelper.getIdByUsername(username);
        bill.setUserId(id);
        // 根据序号寻找对应的账单记录
        List<BillInfo> bill_list = (List<BillInfo>) queryById(bill.getXuhao());
        BillInfo info = null;
        if (bill_list.size() > 0) { // 有找到账单记录
            info = bill_list.get(0);
        }
        // 已存在该账单信息，则更新账单
        if (info != null) {
            bill.setRowid(info.getRowid());
            bill.setCreate_time(info.getCreate_time());
            bill.setUpdate_time(DateUtil.getNowDateTime(""));
            // 更新数据库记录
            update(bill);
        }
        // 未存在该账单信息，则添加账单
        else {
            bill.setCreate_time(DateUtil.getNowDateTime(""));
            // 添加数据库记录
            insert(bill);
        }
    }
}
