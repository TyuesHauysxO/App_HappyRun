package com.example.myapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity  implements View.OnClickListener{
    Button login;
    EditText name;
    EditText pwd;
    TextView enroll;
    TextView forgetPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /** 去除标题栏*/
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.login);
        name = (EditText)findViewById(R.id.login_user) ;
        pwd = (EditText)findViewById(R.id.login_pwd);
        enroll = (TextView)findViewById(R.id.enroll);
        forgetPwd = (TextView)findViewById(R.id.forget_pwd);

        login.setOnClickListener(this);
        enroll.setOnClickListener(this);
        forgetPwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String strName = name.getText().toString();
        String strPwd = pwd.getText().toString();


        switch (v.getId()){
            case R.id.login:
                if(TextUtils.isEmpty(strName)||TextUtils.isEmpty(strPwd)){
                    new AlertDialog.Builder(LoginActivity.this).setTitle("错误")
                            .setMessage("帐号或密码不能空").
                            setPositiveButton("确定", null).show();

                    Intent intentContent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intentContent);
                    finish();
                }
                else {
                    isMatch(strName, strPwd);
                }
                break;
            case R.id.enroll:
                Intent intentEnroll = new Intent(LoginActivity.this, EnrollActivity.class);
                startActivity(intentEnroll);
                break;
            case R.id.forget_pwd:
                Intent intentSetPwd = new Intent(LoginActivity.this, SetPwdActivity.class);
                startActivity(intentSetPwd);
                break;
            default:
                break;
        }
    }

    public boolean isMatch(String name, String pwd){
        User user = queryPwd(name);

        /*密码验证正确*/
        if(pwd.equals(user.getPwd())){
            Toast.makeText(LoginActivity.this, "登陆成功",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(LoginActivity.this, "用户名密码不正确",
                    Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public User queryPwd(String name){
        UserDAO userDAO = new UserDAO(LoginActivity.this);
        return userDAO.query(name);
    }
}
