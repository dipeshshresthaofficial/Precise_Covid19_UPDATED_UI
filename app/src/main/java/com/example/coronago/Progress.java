package com.example.coronago;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class Progress extends AppCompatActivity {

    ProgressBar pb;
    int counter =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        prog();

    }

    public void prog(){

        pb = (ProgressBar)findViewById(R.id.bar);

        final Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {

                counter++;
                pb.setProgress(counter);

                if(counter == 25){
                    t.cancel();
                }

//                if(counter ==100){
//                    t.cancel();
//                }
            }
        };

        t.schedule(tt,0,500);
    }
}
