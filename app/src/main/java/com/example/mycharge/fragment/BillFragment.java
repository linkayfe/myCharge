package com.example.mycharge.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;


import com.example.mycharge.R;
import com.example.mycharge.adapter.BillListAdapter;
import com.example.mycharge.bean.BillInfo;
import com.example.mycharge.database.BillDBHelper;

import java.util.ArrayList;
import java.util.List;

public class BillFragment extends Fragment {
    // 声明一个视图对象
    protected View mView;
    // 声明一个上下文对象
    protected Context mContext;
    // 当前选择的月份
    private int mMonth;
    // 声明一个列表视图对象
    private ListView lv_bill;
    private List<BillInfo> mBillList = new ArrayList<BillInfo>();
    private static Long userId;

    // 获取该碎片的一个实例
    public static BillFragment newInstance(int month,Long theUserId) {
        // 创建该碎片的一个实例
        BillFragment fragment = new BillFragment();
        // 创建一个新包裹
        Bundle bundle = new Bundle();
        // 往包裹存入月份
        bundle.putInt("month", month);
        // 把包裹塞给碎片
        fragment.setArguments(bundle);
        userId = theUserId;
        // 返回碎片实例
        return fragment;
    }

    // 创建碎片视图
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 获取活动页面的上下文
        mContext = getActivity();
        if (getArguments() != null) {
            mMonth = getArguments().getInt("month", 1);
        }
        // 根据布局文件fragment_bill.xml生成视图对象
        mView = inflater.inflate(R.layout.fragment_bill, container, false);
        // 从布局视图中获取名叫lv_bill的列表视图
        lv_bill = mView.findViewById(R.id.lv_bill);
        return mView; // 返回该碎片的视图对象
    }

    @Override
    public void onStart() {
        super.onStart();
        BillDBHelper helper = BillDBHelper.getInstance(mContext);
        // 查询指定月份和对应user_id的账单列表
        mBillList = helper.queryByMonth(mMonth,userId);
        if (mBillList!=null && mBillList.size()>0) {
            double income=0, expend=0;
            for (BillInfo bill : mBillList) {
                if (bill.getType() == 0) { // 收入金额累加
                    income += bill.getAmount();
                } else {
                    // 支出金额累加
                    expend += bill.getAmount();
                }
            }
            BillInfo sum = new BillInfo();
            sum.setDate("总计");
            double money = income-expend;
            sum.setDesc(String.format("收入"+income+"元\n支出"+expend+"元", income, expend));
            sum.setRemark(String.format("本月流水总计：%s元", money>0?"+"+money:money));
//            sum.setDesc(String.format("收入%d元\n支出%d元", (int) income, (int) expend));
//            sum.setRemark(String.format("本月流水总计：%d元", (int) (income-expend)));
            mBillList.add(sum); // 往账单信息列表末尾添加一个总计行
        }
        // 构建一个当月账单的列表适配器
        BillListAdapter listAdapter = new BillListAdapter(mContext, mBillList,userId);
        // 设置列表视图的适配器
        lv_bill.setAdapter(listAdapter);
        // 设置列表视图的点击监听器
        lv_bill.setOnItemClickListener(listAdapter);
        // 设置列表视图的长按监听器
        lv_bill.setOnItemLongClickListener(listAdapter);
    }


}
