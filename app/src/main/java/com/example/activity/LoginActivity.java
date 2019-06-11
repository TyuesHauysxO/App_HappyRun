package com.example.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dbModel.User;
import com.example.dbModel.UserDAO;

public class LoginActivity extends AppCompatActivity  implements View.OnClickListener{
    private Button login;
    private EditText name;
    private EditText pwd;
    private TextView enroll;
    private TextView forgetPwd;

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
    protected void onStart() {
        clearEditText();//清空输入栏
        super.onStart();
    }

    private void clearEditText() {
        name.setText("");
        pwd.setText("");
    }

    @Override
    public void onClick(View v) {

        String strName = name.getText().toString();
        String strPwd = pwd.getText().toString();


        switch (v.getId()){
            case R.id.login:
                if(TextUtils.isEmpty(strName)||TextUtils.isEmpty(strPwd)){
                    Toast.makeText(LoginActivity.this, "用户名密码不能为空",
                            Toast.LENGTH_SHORT).show();
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
                Intent intentSetPwd = new Intent(LoginActivity.this, ForgetPwdActivity.class);
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

            intent.putExtra("name", user.getName());

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
