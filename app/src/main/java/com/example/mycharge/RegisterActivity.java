package com.example.mycharge;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mycharge.database.UserDBHelper;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText reg_username;
    private EditText reg_userpassword;
    private EditText reg_userpassword_sure;
    private Button reg_bt;
    private UserDBHelper userDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //去除顶部状态栏

        //初始化控件对象
        reg_username = (EditText) findViewById(R.id.reg_username);
        reg_userpassword = (EditText) findViewById(R.id.reg_userpassword);
        reg_userpassword_sure = (EditText) findViewById(R.id.reg_userpassword_sure);
        reg_bt = (Button) findViewById(R.id.reg_bt);

        reg_bt.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if("".equals(reg_username.getText().toString())
                || "".equals(reg_userpassword.getText().toString())
                || "".equals(reg_userpassword_sure.getText().toString()))
            Toast.makeText(getApplication(),getResources()
                    .getString(R.string.no_complete),Toast.LENGTH_SHORT).show();
        else if(!reg_userpassword.getText().toString()
                .equals(reg_userpassword_sure.getText().toString()))
            Toast.makeText(getApplication(),getResources()
                    .getString(R.string.no_same),Toast.LENGTH_SHORT).show();
        else {
            userDBHelper = UserDBHelper.getInstance(this);
//            userDBHelper.getWritableDatabase();
            boolean success = userDBHelper.register(reg_username.getText().toString(),
                    reg_userpassword.getText().toString());
            if (success){
                Intent toCharge = new Intent(this,LoginActivity.class);
                startActivity(toCharge);
            }else {
                Toast.makeText(getApplication(),getResources()
                        .getString(R.string.register_fail),Toast.LENGTH_SHORT).show();
            }
        }
    }



}
