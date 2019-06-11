package com.example.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class RulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        /*设置滚动条*/
        setContentView(R.layout.activity_rules);

        TextView rules = (TextView)findViewById(R.id.privacy_rules);

        rules.setMovementMethod(ScrollingMovementMethod.getInstance());
    }
}
