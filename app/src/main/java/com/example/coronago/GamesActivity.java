package com.example.coronago;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class GamesActivity extends AppCompatActivity {

    WebView web_to_show;
    String website;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games2);

        Intent intent = new Intent();
        //website =intent.getStringExtra(Intent.EXTRA_TEXT);
        website = games.website_to_send;
        Toast.makeText(GamesActivity.this,website,Toast.LENGTH_SHORT).show();

        web_to_show= (WebView) findViewById(R.id.webopen);
        openView();

    }

    private void openView() {

        //web_to_show = (WebView) findViewById(R.id.);

        WebSettings webSettings = web_to_show.getSettings();

        webSettings.setJavaScriptEnabled(true);
        web_to_show.loadUrl(website);

        web_to_show.setWebViewClient(new WebViewClient());
    }

    @Override
    public void onBackPressed() {
        if(web_to_show.canGoBack()){
            web_to_show.goBack();
        }
        else {
            super.onBackPressed();
        }
    }
}
