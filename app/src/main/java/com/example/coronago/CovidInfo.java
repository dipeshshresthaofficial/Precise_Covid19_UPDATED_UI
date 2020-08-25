package com.example.coronago;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Locale;

public class CovidInfo extends AppCompatActivity {

    WebView covidInfoView;
    TextToSpeech textToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_info);

        covidInfoView = (WebView) findViewById(R.id.covidInfo);
        Intent infoIntent = getIntent();
//        getting the details from first activity
        final String message = infoIntent.getStringExtra("DETAIL");

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                textToSpeech.setLanguage(Locale.ENGLISH);
                openView(message);
            }
        });
    }

    public void openView(String Message){
//        typecasting the webview
        covidInfoView = (WebView) findViewById(R.id.covidInfo);
//        setting the webview
        WebSettings webSettings = covidInfoView.getSettings();
//        to run the javascript code run smoothly
        webSettings.setJavaScriptEnabled(true);
        covidInfoView.loadUrl("https://www.worldometers.info/coronavirus/");
        //prevent urls to brows in the browser
        covidInfoView.setWebViewClient(new WebViewClient());

    }
    //function gets performend when we backpress on the webview
    @Override
    public void onBackPressed() {
        if(covidInfoView.canGoBack()){
            covidInfoView.goBack();
        }
        else {
            super.onBackPressed();
        }
    }
}
