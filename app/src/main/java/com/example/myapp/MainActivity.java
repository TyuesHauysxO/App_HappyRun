package com.example.myapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvRun;
    private TextView tvFind;
    private TextView tvMine;

    private Fragment fgRun;
    private Fragment fgFind;
    private Fragment fgMine;

    private Button logOut;
    private Button goRun;

    private View vMine;
    private View vFind;
    private View vRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* 去除标题栏*/
        getSupportActionBar().hide();

        tvRun = (TextView)findViewById(R.id.bar_run);
        tvFind = (TextView)findViewById(R.id.bar_find);
        tvMine = (TextView)findViewById(R.id.bar_mine);

        tvRun.setOnClickListener(this);
        tvFind.setOnClickListener(this);
        tvMine.setOnClickListener(this);
        /*默认打开<我的>界面*/
        tvMine.setSelected(true);
        initFgMine();
    }


    /*
    在Activity中无法直接使用fragment中的控件,需要复写onstart
    */
    @Override
    protected void onStart() {
        super.onStart();
        logOut = (Button)findViewById(R.id.logout);
        goRun = (Button)findViewById(R.id.go_run);

        logOut.setOnClickListener(this);
//        goRun.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bar_run:
                resetSelected();
                tvRun.setSelected(true);
                initFgRun();
                break;
            case R.id.bar_find:
                resetSelected();
                tvFind.setSelected(true);
                initFgFind();
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
//            case R.id.go_run:
//                Intent iGoRun = new Intent(MainActivity.this, RunningActivity.class);
//                startActivity(iGoRun);
//                break;
        }
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

    public void initFgFind(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(fgFind == null){
            fgFind = new MyFragmentFind();

            //添加 fragment
            transaction.add(R.id.frame, fgFind);
        }

        //隐藏fragment
        hideFragment(transaction);

        //显示
        transaction.show(fgFind);

        /**提交事务**/
        transaction.commit();
    }

    public void initFgMine(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(fgMine == null){
            fgMine = new MyFragmentMine();

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
        if(fgFind != null){
            transaction.hide(fgFind);
        }
        if(fgMine != null){
            transaction.hide(fgMine);
        }
    }

    public void resetSelected(){
        if(tvRun != null){
            tvRun.setSelected(false);
        }
        if(tvFind != null){
            tvFind.setSelected(false);
        }
        if(tvMine != null){
            tvMine.setSelected(false);
        }
    }

}
