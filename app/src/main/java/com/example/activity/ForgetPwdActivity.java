package com.example.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dbModel.UserDAO;

public class ForgetPwdActivity extends AppCompatActivity {
    private EditText name;
    private EditText pwd;
    private EditText pwdConf;
    private Button button;

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                this.finish(); // back button
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pwd);

//        /*返回键*/
//        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
//        actionBar = getSupportActionBar();
//        if(actionBar != null){
//            actionBar.setHomeButtonEnabled(true);
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }

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
                    Toast.makeText(ForgetPwdActivity.this, "请输入用户名",
                            Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(strPwd)){
                    Toast.makeText(ForgetPwdActivity.this, "请设置密码",
                            Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(strPwdConf)){
                    Toast.makeText(ForgetPwdActivity.this, "请确认密码",
                            Toast.LENGTH_SHORT).show();
                }
                else if(strPwd.length() < 8 || strPwd.length() > 20){
                    Toast.makeText(ForgetPwdActivity.this, "无效密码，请重试",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    if(strPwd.equals(strPwdConf)){
                        alterPwd(strName, strPwd);
                        Toast.makeText(ForgetPwdActivity.this, "修改成功",
                                Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ForgetPwdActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(ForgetPwdActivity.this, "两次输入密码不一致",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void alterPwd(String name, String pwd){
        UserDAO userDAO = new UserDAO(ForgetPwdActivity.this);
        userDAO.update(name, pwd);
    }
}
