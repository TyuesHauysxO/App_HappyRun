package com.example.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fragment.MyFragmentMine;
import com.example.fragment.MyFragmentRun;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
     private TextView tvRun;
     private TextView tvMine;

     private Fragment fgRun;
     private Fragment fgMine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* 去除标题栏*/
        getSupportActionBar().hide();

        tvRun = (TextView)findViewById(R.id.bar_run);
        tvMine = (TextView)findViewById(R.id.bar_mine);

        tvRun.setOnClickListener(this);
        tvMine.setOnClickListener(this);
        /*默认打开<运动>界面*/
        tvRun.setSelected(true);
        initFgRun();

    }

    /*
    *   捕捉返回键，按下返回桌面
    * */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bar_run:
                resetSelected();
                tvRun.setSelected(true);
                initFgRun();
                break;
            case R.id.bar_mine:
                resetSelected();
                tvMine.setSelected(true);
                initFgMine();
                break;
            case R.id.logout:
                Intent iLogout = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(iLogout);
                Toast.makeText(MainActivity.this,"成功退出账号",
                        Toast.LENGTH_SHORT ).show();
                finish();
                break;
            case R.id.go_run:
                Intent iGoRun = new Intent(MainActivity.this, RunningActivity.class);
                startActivity(iGoRun);
                break;
        }
    }

    private String getUserName() {
        //接收name值
        String name = this.getIntent().getExtras().getString("name");

        return name;
    }

    public void initFgRun(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(fgRun == null){
            fgRun = new MyFragmentRun();

            //添加 fragment
            transaction.add(R.id.frame, fgRun);
        }

        //隐藏fragment
        hideFragment(transaction);

        //显示
        transaction.show(fgRun);

        /**提交事务**/
        transaction.commit();
    }

    public void initFgMine(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(fgMine == null){
            fgMine = new MyFragmentMine(getUserName());

            //添加 fragment
            transaction.add(R.id.frame, fgMine);
        }

        //隐藏fragment
        hideFragment(transaction);

        //显示
        transaction.show(fgMine);

        /**提交事务**/
        transaction.commit();
    }


    public void hideFragment(FragmentTransaction transaction){
        if(fgRun != null){
            transaction.hide(fgRun);
        }
        if(fgMine != null){
            transaction.hide(fgMine);
        }
    }

    public void resetSelected(){
        if(tvRun != null){
            tvRun.setSelected(false);
        }
        if(tvMine != null){
            tvMine.setSelected(false);
        }
    }

}
