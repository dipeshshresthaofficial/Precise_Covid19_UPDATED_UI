package com.example.coronago;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public String user_type;
    private static final int REQUEST_CALL = 1;
    //    Button mLogin;
    public static TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Navigation button
        BottomNavigationView navBtn = findViewById(R.id.bottom_navigation);
        navBtn.setOnNavigationItemSelectedListener(navListener);

//setting the default fragment of the App when it opens to HOME Fragment

//        Intent intent = getIntent();//In fragment we have to use ".getActivity()" before using ".getIntent()"
//        user_type =  intent.getStringExtra("user_type");
//        System.out.println("********************************"+user_type);
////      had to add user_type!=null because of error check this: https://stackoverflow.com/questions/27895108/nullpointerexception-attempt-to-invoke-virtual-method-boolean-java-lang-string
//        if(user_type!=null && user_type.equals("normal")){
//
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NormalHomeFragment()).commit();
//        }
//        else
//        {
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
//        }


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

        FloatingActionButton fab1 = findViewById(R.id.callPolice);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call(100);
            }
        });

        FloatingActionButton fab2 = findViewById(R.id.callAmbulance);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call(1001);
            }
        });


        textToSpeech =new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                textToSpeech.setLanguage(Locale.ENGLISH);
            }
        });
    }

//    Setting Action Listener for HOME, TIPS AND PROFILE Navigation
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;
            switch(menuItem.getItemId()){

                case R.id.nav_home:
//                    if(user_type!=null && user_type.equals("normal")){
//                        selectedFragment = new NormalHomeFragment();
//                    }
//                    else{
//                        selectedFragment = new HomeFragment();
//                    }

                    selectedFragment = new HomeFragment();
                    break;
                case R.id.nav_tips:
                    selectedFragment = new TipsFragment();
                    break;
                case R.id.nav_profile:
                    selectedFragment = new ProfileFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();

            return true;
        }
    };

    public void call(int number){

        Toast.makeText(this,"calling "+number, Toast.LENGTH_SHORT).show();
        textToSpeech.speak("You have called "+number,TextToSpeech.QUEUE_FLUSH,null);

        String number1 = Integer.toString(number);


        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }
        else{
            String dial = "tel:" + number1;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CALL){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                call(100);
            }
            else{
                Toast.makeText(this, "Permission Dennied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
