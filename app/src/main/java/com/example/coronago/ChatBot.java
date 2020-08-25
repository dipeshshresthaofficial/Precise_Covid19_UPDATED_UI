package com.example.coronago;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChatBot extends AppCompatActivity {

    Button college,covid;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);

        college = (Button)findViewById(R.id.collegeBot);
        covid = (Button)findViewById(R.id.covidBot);

        college.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                url = "https://web-chat.global.assistant.watson.cloud.ibm.com/preview.html?region=eu-gb&integrationID=8b564059-d49c-4e41-954c-6b990a609ef3&serviceInstanceID=ef69f94f-897f-4301-b468-b3c8b4cf124d";
                Intent i = new Intent(ChatBot.this,EntertainmentActivity.class);
                i.putExtra("link",url);
                startActivity(i);

            }
        });

        covid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                url = "https://web-chat.global.assistant.watson.cloud.ibm.com/preview.html?region=eu-gb&integrationID=7c8205d0-0c66-4746-b102-1066c6808f0c&serviceInstanceID=ef69f94f-897f-4301-b468-b3c8b4cf124d";
                Intent i = new Intent(ChatBot.this,EntertainmentActivity.class);
                i.putExtra("link",url);
                startActivity(i);


            }
        });
    }
}
