package com.example.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetPwdActivity extends AppCompatActivity {
    EditText name;
    EditText pwd;
    EditText pwdConf;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pwd);

        getSupportActionBar().hide();

        name = (EditText)findViewById(R.id.enteruser);
        pwd = (EditText)findViewById(R.id.enterpwd);
        pwdConf = (EditText)findViewById(R.id.enterpwd2);
        button = (Button)findViewById(R.id.set_pwd);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strName = name.getText().toString();
                String strPwd = pwd.getText().toString();
                String strPwdConf = pwdConf.getText().toString();

                if(TextUtils.isEmpty(strName)){
                    Toast.makeText(SetPwdActivity.this, "请输入用户名",
                            Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(strPwd)){
                    Toast.makeText(SetPwdActivity.this, "请设置密码",
                            Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(strPwdConf)){
                    Toast.makeText(SetPwdActivity.this, "请确认密码",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    if(strPwd.length() < 8 && strPwd.length() > 20){
                        Toast.makeText(SetPwdActivity.this, "无效密码，请重试",
                                Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(strPwd.equals(strPwdConf)){
                            modifyPwd(strName, strPwd);
                            Toast.makeText(SetPwdActivity.this, "修改完成!",
                                    Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(SetPwdActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(SetPwdActivity.this, "两次输入密码不一致",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    public void modifyPwd(String name, String pwd){
        UserDAO userDAO = new UserDAO(SetPwdActivity.this);
        userDAO.update(name, pwd);
    }
}
