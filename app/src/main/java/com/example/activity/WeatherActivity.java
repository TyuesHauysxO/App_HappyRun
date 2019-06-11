package com.example.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WeatherActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        getSupportActionBar().hide();

        initWebview();
    }


    /*
    * WebView 专心干好 自己的解析、渲染工作就行了
    * WebViewClient 就是帮助 WebView 处理各种通知、请求事件等
    * WebChromeClient 是辅助 WebView 处理 Javascript 的对话框,网站图标,网站 title.
    *
    * */
    private void initWebview(){
        webView = (WebView)findViewById(R.id.webview);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        /*不使用缓存*/
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);


        webView.loadUrl("http://m.moji.com/weather/china/jiangsu/pukou-district");

    }
}
