package com.example.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.example.service.AccelerometerSensorListener;
import com.example.service.AccelerometerSensorService;

import java.text.DecimalFormat;

public class RunningActivity extends AppCompatActivity
        implements View.OnClickListener, Chronometer.OnChronometerTickListener {

    private static final String TAG = "android";
    private TextView tvDistance;
    private TextView tvPace;

    private Button bRun;
    private Button bRunReset;

    private Chronometer cTimes;

    private static int stepLen = 80;//步长(cm)

    private long seconds;//总时长
    private int steps;//步数
    private float distance;//路程
    private float pace;//配速


    /*
    *
    * 跑步时触发，实时获取当前步数
    *
    * */
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            steps = AccelerometerSensorListener.CURRENT_SETP;
            addRecord();
        }
    };

    private void addRecord() {
        float msPace, mDistance;

        mDistance = (float) (1.0 * steps * stepLen / 100);
        distance = mDistance / 1000;

        if(seconds == 0){
            msPace = 0;
        }
        else {
            /* m/s = 3.6 * km/h */
            msPace = mDistance / seconds;
            pace = (float) (msPace * 3.6);
        }

        /*
        * 构造方法的字符格式这里如果小数不足2位,会以0补足
        * 格式化数据显示到小数点后两位
        *
        * */
        DecimalFormat decimalFormat =new DecimalFormat("0.00");
        String distanceString = decimalFormat.format(distance);
        String paceString = decimalFormat.format(pace);

        tvDistance.setText(distanceString);
        tvPace.setText(paceString);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);

        tvDistance = (TextView)findViewById(R.id.distance);
        tvPace = (TextView)findViewById(R.id.pace);
        cTimes = (Chronometer)findViewById(R.id.chronometer);
        bRun = (Button)findViewById(R.id.run_control);
        bRunReset = (Button)findViewById(R.id.run_reset);

        resetRecord();//初始化界面

        mThread();

        bRun.setOnClickListener(this);
        bRunReset.setOnClickListener(this);
        cTimes.setOnChronometerTickListener(this);

    }

    /*
    *
    * 创建一个子线程获取步数对数据计算
    *
    * */
    private void mThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(AccelerometerSensorService.isRun){
//                        Log.e(TAG, "子线程");
                        Message msg = new Message();
                        handler.sendMessage(msg);
                    }
                }

            }
        }).start();
    }

    /*
    *
    * 重置数据
    *
    * */
    private void resetRecord() {
        seconds = 0;
        steps = 0;
        distance = 0;
        pace = 0;
        tvDistance.setText("0.00");
        tvPace.setText("0.00");
        cTimes.setText("00:00:00");
    }

    @Override
    public void onClick(View v) {

        /*
           用intent作为中间者启动service
        */
        Intent intent = new Intent(RunningActivity.this,
                AccelerometerSensorService.class);

        switch (v.getId()){
            case R.id.run_control:
                if(bRun.getText().toString().equals("开始")){
                    cTimes.setBase(SystemClock.elapsedRealtime());//设置计时器的基准时间
                    cTimes.start();//开始计时
                    bRun.setText("暂停");
                    startService(intent);//启动服务
                }
                else if(bRun.getText().toString().equals("暂停")){
                    cTimes.stop();
                    bRun.setText("继续");
                    stopService(intent);//暂停服务
                }
                else if(bRun.getText().toString().equals("继续")){
                    cTimes.start();
                    bRun.setText("暂停");
                    startService(intent);//重启服务
                }
                break;
            case R.id.run_reset://按下重置按钮
                cTimes.stop();
                resetRecord();
                if(bRun.getText().toString().equals("暂停") ||
                        bRun.getText().toString().equals("继续")){
                    bRun.setText("开始");
                }
                stopService(intent);//停止服务
                break;
            default:
                break;
        }
    }



    /*
    * 捕捉计时动作  该方法1秒调用一次
    *
    * */
    @Override
    public void onChronometerTick(Chronometer chronometer) {
        seconds++;
        cTimes.setText(formatTimes());
    }

    private String formatTimes() {
        String hh = (seconds / 3600) > 9 ? (seconds / 3600) + "" :
                "0" + (seconds / 3600) ;
        String mm = ((seconds % 3600) / 60) > 9 ? (seconds % 3600) / 60+ "" :
                "0" + (seconds % 3600) / 60 ;
        String ss = ((seconds % 3600) % 60) > 9 ? ((seconds % 3600) % 60) + "" :
                "0" + ((seconds % 3600) % 60) ;
        return hh + ":" + mm + ":" + ss;
    }

}

