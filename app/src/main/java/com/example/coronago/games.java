package com.example.coronago;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class games extends AppCompatActivity {

    Button facebook,youtube,game,instagram,messenger;
    public static String website_to_send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        facebook = (Button)findViewById(R.id.facebook);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent startfacebook = new Intent(games.this,GamesActivity.class);
                website_to_send ="https://www.facebook.com/";
                startActivity(startfacebook);

            }
        });

        youtube = (Button)findViewById(R.id.youtube);
        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent startyoutube = new Intent(games.this,GamesActivity.class);
                website_to_send ="https://www.youtube.com/";
                startActivity(startyoutube);


            }
        });

        game = (Button)findViewById(R.id.game);
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent startgames = new Intent(games.this,GamesActivity.class);
                website_to_send ="https://www.crazygames.com/";
                startActivity(startgames);

            }
        });

        instagram = (Button)findViewById(R.id.instagram);
        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent startinstagram = new Intent(games.this,GamesActivity.class);
                website_to_send ="https://www.instagram.com/";
                startActivity(startinstagram);

            }
        });

        messenger = (Button)findViewById(R.id.messenger);
        messenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent startmessenger = new Intent(games.this,GamesActivity.class);
                website_to_send ="https://www.messenger.com/";
                startActivity(startmessenger);

            }
        });
    }
}
