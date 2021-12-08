package com.example.mycharge;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mycharge.bean.UserInfo;
import com.example.mycharge.database.BillDBHelper;
import com.example.mycharge.database.UserDBHelper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText username;
    private EditText userPassword;
    private Button login_bt;
    private TextView register;
    private UserDBHelper userDBHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("USER",MODE_PRIVATE);
        if(!sharedPreferences.getAll().isEmpty()){
            Intent toMain = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(toMain);
            finish();
        }

        setContentView(R.layout.activity_login);
        //去除顶部状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //初始化控件对象
        username = (EditText) findViewById(R.id.username);
        userPassword = (EditText) findViewById(R.id.userPassword);
        login_bt = (Button) findViewById(R.id.login_bt);
        register = (TextView) findViewById(R.id.register);

        login_bt.setOnClickListener(this);
        register.setOnClickListener(this);

        login_bt.setOnTouchListener(new Touch());
        register.setOnTouchListener(new Touch());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_bt:
                if("".equals(username.getText().toString())
                        || "".equals(userPassword.getText().toString()))
                    Toast.makeText(getApplication(),getResources()
                            .getString(R.string.no_complete),Toast.LENGTH_SHORT).show();
                else {
                    String account = username.getText().toString();
                    String password = userPassword.getText().toString();
                    userDBHelper = UserDBHelper.getInstance(this);
                    UserInfo userInfo = userDBHelper.queryByUserId(account,password);
                    if (userInfo == null){
                        Toast.makeText(getApplication(),getResources()
                                .getString(R.string.login_fail),Toast.LENGTH_SHORT).show();
                    }else {
                        Intent toCharge = new Intent(this,BillPagerActivity.class);
                        toCharge.putExtra("username",account);
                        startActivity(toCharge);
                    }
                }
                break;
            case R.id.register:
                Intent toReg = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(toReg,1);
                break;
        }
    }

//    private class Click implements View.OnClickListener {
//
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.login_bt:
//                    if("".equals(username.getText().toString()) || "".equals(userPassword.getText().toString()))
//                        Toast.makeText(getApplication(),getResources().getString(R.string.no_complete),Toast.LENGTH_SHORT).show();
//                    else {
//                        String account = username.getText().toString();
//                        String password = userPassword.getText().toString();
////                        userDBHelper = UserDBHelper.getInstance(this);
//
//                    }
//                    break;
//                case R.id.register:
//                    Intent toReg = new Intent(LoginActivity.this, RegisterActivity.class);
//                    startActivityForResult(toReg,1);
//                    break;
//            }
//        }
//    }

    private class Touch implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (v.getId()) {
                case R.id.login_bt:
                    if(event.getAction() == MotionEvent.ACTION_DOWN)
                        login_bt.setBackgroundColor(Color.argb(80,19,86,255));
                    else if(event.getAction() == MotionEvent.ACTION_UP)
                        login_bt.setBackgroundResource(R.drawable.bt_bg);
                    break;
                case R.id.register:
                    if(event.getAction() == MotionEvent.ACTION_DOWN)
                        register.setTextColor(Color.BLUE);
                    else if(event.getAction() == MotionEvent.ACTION_UP)
                        register.setTextColor(Color.BLACK);
                    break;

            }
            return false;
        }
    }

    private void init() {
        username = (EditText) findViewById(R.id.username);
        userPassword = (EditText) findViewById(R.id.userPassword);
        login_bt = (Button) findViewById(R.id.login_bt);
        register = (TextView) findViewById(R.id.register);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (1) {
                case 1:
                    Toast.makeText(getApplication(),"登录成功",Toast.LENGTH_SHORT).show();
                    Intent toMain = new Intent(LoginActivity.this, MainActivity.class);
                    toMain.putExtra("isVisit",false);
                    startActivity(toMain);
                    finish();
                    break;
                case 2:
                    Toast.makeText(getApplication(),"用户不存在",Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(getApplication(),"密码错误",Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(getApplication(),"网络错误",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
