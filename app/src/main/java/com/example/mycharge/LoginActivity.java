package com.example.mycharge;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mycharge.bean.UserInfo;
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
                    if (userInfo.getUsername()==null){
                        Toast.makeText(getApplication(),getResources()
                                .getString(R.string.login_fail),Toast.LENGTH_SHORT).show();
                    }else {
                        Intent toCharge = new Intent(this,BillPagerActivity.class);
                        toCharge.putExtra("username",account);
                        toCharge.putExtra("userId",userDBHelper.getUserId(account)+"");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
