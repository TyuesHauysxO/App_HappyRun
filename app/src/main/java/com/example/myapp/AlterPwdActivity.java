package com.example.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AlterPwdActivity extends AppCompatActivity {

    private EditText tvPwd;
    private EditText tvPwdConf;
    private EditText tvCurrPwd;
    private Button bSetPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_pwd);

        tvCurrPwd = (EditText)findViewById(R.id.alter_currentpwd);
        tvPwd = (EditText)findViewById(R.id.alter_enterpwd);
        tvPwdConf = (EditText)findViewById(R.id.alter_enterpwd2);
        bSetPwd = (Button)findViewById(R.id.alter_set_pwd);

        bSetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currPwd = tvCurrPwd.getText().toString();
                String pwd = tvPwd.getText().toString();
                String pwdConf = tvPwdConf.getText().toString();
                String username = getUserName();

                User user = queryPwd(username);

                if (TextUtils.isEmpty(currPwd)) {
                    Toast.makeText(AlterPwdActivity.this,
                            "请输入当前密码", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(pwd)) {
                    Toast.makeText(AlterPwdActivity.this,
                            "请输入新密码", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(pwdConf)) {
                    Toast.makeText(AlterPwdActivity.this,
                            "请再次输入新密码", Toast.LENGTH_SHORT).show();
                } else if(pwd.length() < 8 || pwd.length() > 20){
                    Toast.makeText(AlterPwdActivity.this,
                            "无效密码，请重试", Toast.LENGTH_SHORT).show();
                } else {
                    if(user.getPwd().equals(currPwd)){
                        if(pwd.equals(pwdConf)){
                            alterPwd(username, pwd);
                            Toast.makeText(AlterPwdActivity.this,
                                    "修改成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(AlterPwdActivity.this,
                                    "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(AlterPwdActivity.this,
                                "当前密码错误", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void alterPwd(String username, String pwd) {
        UserDAO userDAO = new UserDAO(AlterPwdActivity.this);
        userDAO.update(username, pwd);
    }


    public User queryPwd(String name){
        UserDAO userDAO = new UserDAO(AlterPwdActivity.this);
        return userDAO.query(name);
    }

    /*获取当前登陆的用户名*/
    private String getUserName(){
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("username");

        return name;
    }
}
