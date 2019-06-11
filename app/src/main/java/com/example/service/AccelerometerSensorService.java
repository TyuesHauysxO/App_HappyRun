package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

import static android.content.ContentValues.TAG;

/*
 *
 *  Service负责后台的需要长期运行的任务
 *  计步服务
 *  运行在后台的服务程序，完成了界面部分的开发后
 *  就可以开发后台的服务类StepService
 *
 * */

public class AccelerometerSensorService extends Service {

    private SensorManager sensorManager;//传感器管理服务
    private AccelerometerSensorListener accelerometerSL;//传感器监听服务
    Sensor accelerometer;

    public static boolean isRun;//是否正在运行


    /*绑定Service*/
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                initAccelerometer();//初始化加速度传感器
            }
        }).start();
    }


    private void initAccelerometer() {
        isRun = false;

        /*SensorManager获取所有传感器*/
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Log.e(TAG, "传感器管理");

        /*获取加速度传感器*/
        if(sensorManager != null){
            accelerometer = sensorManager
                    .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            Log.e(TAG, "加速度传感器");
        }


        /*
        * 为加速度传感器注册监听事件
        *
        * sensorManager.regesterListener(SensorEventListener listener, Sensor sensor, int rate);
        *
        * 第一个参数为传感器监听
        * 第二个参数为当前注册的传感器
        * 第三个参数是延迟时间的精密度
        * */
        if(accelerometer != null){
            accelerometerSL = new AccelerometerSensorListener();

            sensorManager.registerListener(accelerometerSL,
                    accelerometer, SensorManager.SENSOR_DELAY_GAME);

            Log.e(TAG, "传感器注册");
        }


        /*判断是否已正常初始化*/
        if(sensorManager != null && accelerometer != null
                && accelerometerSL != null){
            isRun = true;
            Log.e(TAG, "成功创建监听器");
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {

        /*取消注册*/
        if(accelerometerSL != null){
            sensorManager.unregisterListener(accelerometerSL);
        }
        isRun = false;

        super.onDestroy();
    }
}
