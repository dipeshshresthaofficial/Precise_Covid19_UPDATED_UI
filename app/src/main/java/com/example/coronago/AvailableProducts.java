package com.example.coronago;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.coronago.MainActivity.textToSpeech;

public class AvailableProducts extends AppCompatActivity {

    public static String number;

    private static final int REQUEST_CALL = 1;

    Button newButton;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_products);

        number = session();
        Toast.makeText(AvailableProducts.this,"number is="+number,Toast.LENGTH_SHORT).show();

        layout = (LinearLayout) findViewById(R.id.linearLayout);

        //text_to_show = (TextView)findViewById(R.id.multitext1);
        getData();

    }

    //this function will help to get the multiple data from the firebase database
    String s1="";
    public void getData(){

        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Tools");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(final DataSnapshot data: dataSnapshot.getChildren()){
                    //data.getKey();
                    System.out.println("children " + data.getKey());
                    Object l = dataSnapshot.child(data.getKey()).child(AvailabilityActivity.temp).getValue();
                    Object l1 = dataSnapshot.child(data.getKey()).child("hospital_name").getValue();
                    s1  = "\nHospital name = "+l1.toString()+"\nphone number is ="+data.getKey()+"\n"+"The available "+AvailabilityActivity.temp+" is ="+l.toString()+"\n";
                    //text_to_show.setText(s1);
                    Toast.makeText(AvailableProducts.this,"the value available is ="+dataSnapshot.child(data.getKey()).child(AvailabilityActivity.temp).getValue(),Toast.LENGTH_SHORT).show();
                    newButton = new Button(AvailableProducts.this);
                    newButton.setText(s1);
                    newButton.setBackgroundResource(R.drawable.custom_btn_round);
                    newButton.setPadding(10,50,10,50);
                    //if we click the button
                    newButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //startActivity(new Intent(AvailableProducts.this, MapsActivity.class));
                            call(data.getKey());

                        }
                    });
                    layout.addView(newButton);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        };
        myRef.addValueEventListener(postListener);

    }

    public void call(String number){

        Toast.makeText(this,"calling "+number, Toast.LENGTH_SHORT).show();
        //textToSpeech.speak("You have called "+number, TextToSpeech.QUEUE_FLUSH,null);

        //String number1 = Integer.toString(number);


        if(ContextCompat.checkSelfPermission(AvailableProducts.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(AvailableProducts.this, new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }
        else{
            String dial = "tel:" + number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CALL){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //call(100);
                call(number);
            }
            else{
                Toast.makeText(this, "Permission Dennied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private String session() {

        System.out.println("Hello 2");
        SessionManagement sessionManagement = new SessionManagement(AvailableProducts.this);
        return sessionManagement.getSession();
    }
}
