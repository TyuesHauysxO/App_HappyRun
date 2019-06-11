package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.activity.R;
import com.example.activity.RunningActivity;
import com.example.activity.WeatherActivity;

public class MyFragmentRun extends Fragment implements View.OnClickListener{
    @Nullable

    private Button goRun;
    private TextView weather;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).
                inflate(R.layout.layout_run, container, false);

        goRun = (Button)view.findViewById(R.id.go_run);
        weather = (TextView)view.findViewById(R.id.weather);

        goRun.setOnClickListener(this);
        weather.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.go_run:
                Intent iRunning = new Intent(getContext(), RunningActivity.class);
                startActivity(iRunning);
                break;
            case R.id.weather:
                Intent iWeather = new Intent(getContext(), WeatherActivity.class);

//                String address = "http://m.weather.com.cn/mweather/101190107.shtml";
//
//                Uri uri = Uri.parse(address);
//
//                iWeather.setAction(Intent.ACTION_VIEW);
//                iWeather.setData(uri);

                startActivity(iWeather);
        }
    }
}
