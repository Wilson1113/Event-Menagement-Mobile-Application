package com.fit2081.assignment3.events;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fit2081.assignment3.R;

public class EventGoogleResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_google_result);

        WebView webView = findViewById(R.id.webview);
        String url = getIntent().getExtras().getString("url");

        String webPageUrl = "https://www.google.com/search?q="+url;

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(webPageUrl);
    }
}