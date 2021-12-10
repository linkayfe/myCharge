package com.example.mycharge;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;


import com.example.mycharge.adapter.BillPagerAdapter;
import com.example.mycharge.util.DateUtil;

import java.util.Calendar;

public class BillPagerActivity extends AppCompatActivity implements
        View.OnClickListener, DatePickerDialog.OnDateSetListener, ViewPager.OnPageChangeListener {
    protected static String TAG = "BillPagerActivity";
    private TextView tv_month;
    // 声明一个翻页视图对象
    private ViewPager vp_bill;
    private Calendar calendar = Calendar.getInstance();
    private String username;
    private Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_pager);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        userId = Long.parseLong(intent.getStringExtra("userId"));
        Log.d(TAG,"username = "+username+"\nuserId = "+userId);

        TextView tv_title = findViewById(R.id.tv_title);
        TextView tv_option = findViewById(R.id.tv_option);
        tv_month = findViewById(R.id.tv_month);
        tv_title.setText("账单列表");
        tv_option.setText("添加账单");
        findViewById(R.id.iv_back).setOnClickListener(this);
        tv_option.setOnClickListener(this);
        tv_month.setOnClickListener(this);
        tv_month.setText(DateUtil.getMonth(calendar));

        vp_bill = findViewById(R.id.vp_bill);
        // 初始化翻页视图
        initViewPager();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            finish(); // 关闭当前页面
        } else if (v.getId() == R.id.tv_option) {
            Intent intent = new Intent(this, BillAddActivity.class);
            intent.putExtra("username",username);
            intent.putExtra("userId",userId+"");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (v.getId() == R.id.tv_month) {
            // 构建一个日期对话框，该对话框已经集成了日期选择器
            // DatePickerDialog的第二个构造参数指定了日期监听器
            DatePickerDialog dialog = new DatePickerDialog(this, this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        tv_month.setText(DateUtil.getMonth(calendar));
        // 设置翻页视图显示第几页
        vp_bill.setCurrentItem(month);
    }

    // 初始化翻页视图
    private void initViewPager() {
        PagerTabStrip pts_bill = findViewById(R.id.pts_bill);
        // 设置翻页标签栏的文本大小
        pts_bill.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        // 构建一个商品图片的翻页适配器
        BillPagerAdapter adapter = new BillPagerAdapter(getSupportFragmentManager(),
                calendar.get(Calendar.YEAR),userId);
        // 设置翻页视图的适配器
        vp_bill.setAdapter(adapter);
        // 设置翻页视图显示第几页
        vp_bill.setCurrentItem(calendar.get(Calendar.MONTH));
        // 给翻页视图添加页面变更监听器
        vp_bill.addOnPageChangeListener(this);
    }

    // 翻页状态改变时触发
    public void onPageScrollStateChanged(int state) {}

    // 在翻页过程中触发
    public void onPageScrolled(int position, float ratio, int offset) {}

    // 在翻页结束后触发
    public void onPageSelected(int position) {
        calendar.set(Calendar.MONTH, position);
        tv_month.setText(DateUtil.getMonth(calendar));
    }
}
