package com.example.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class EnrollActivity extends AppCompatActivity {
    EditText name;
    EditText pwd;
    EditText pwdConf;
    Button enroll;
    TextView contract;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);

        getSupportActionBar().hide();

        name = (EditText)findViewById(R.id.enroll_user);
        pwd = (EditText)findViewById(R.id.enroll_pwd);
        pwdConf = (EditText)findViewById(R.id.enroll_pwd2);
        enroll = (Button)findViewById(R.id.enroll);
        contract = (TextView)findViewById(R.id.enroll_contact);
        radioButton = (RadioButton)findViewById(R.id.enroll_radiobutton);

        enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strName = name.getText().toString();
                String strPwd = pwd.getText().toString();
                String strPwdConf = pwdConf.getText().toString();

                if(TextUtils.isEmpty(strName)){
                    Toast.makeText(EnrollActivity.this, "请输入用户名",
                            Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(strPwd)){
                    Toast.makeText(EnrollActivity.this, "请输入密码",
                            Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(strPwdConf)){
                    Toast.makeText(EnrollActivity.this, "请确认密码",
                            Toast.LENGTH_SHORT).show();
                }
                else if(!radioButton.isChecked()){
                    Toast.makeText(EnrollActivity.this, "请仔细阅读协议",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    if(strPwd.length() < 8 && strPwd.length() > 20){
                        Toast.makeText(EnrollActivity.this, "无效密码，请重试",
                                Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (strPwd.equals(strPwdConf)) {
                            User user = new User(strName, strPwd);
                            insertUser(user);

                            Toast.makeText(EnrollActivity.this, "注册成功",
                                    Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(EnrollActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(EnrollActivity.this, "两次输入密码不一致!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        contract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnrollActivity.this, RulesActivity.class);
                startActivity(intent);
            }
        });
    }

    public void insertUser(User user){
        UserDAO userDAO = new UserDAO(EnrollActivity.this);
        userDAO.insert(user);
    }
}
