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
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailsActivity extends AppCompatActivity {

    public static String number;

    private static final int REQUEST_CALL = 1;

    Button newButton;
    LinearLayout layout;
    public String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        title = getIntent().getStringExtra("title");
        Toast.makeText(DetailsActivity.this,"title is "+title,Toast.LENGTH_SHORT).show();

        layout = (LinearLayout) findViewById(R.id.linear);

        newButton = (Button)findViewById(R.id.newButton);

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
                    //Object l = dataSnapshot.child(data.getKey()).child(AvailabilityActivity.temp).getValue();
                    Object l1 = dataSnapshot.child(data.getKey()).child("hospital_name").getValue();
                    Object l2 = dataSnapshot.child(data.getKey()).child("face_mask").getValue();
                    Object l3 = dataSnapshot.child(data.getKey()).child("face_shield").getValue();
                    Object l4 = dataSnapshot.child(data.getKey()).child("glove").getValue();
                    Object l5 = dataSnapshot.child(data.getKey()).child("ppe").getValue();
                    Object l6 = dataSnapshot.child(data.getKey()).child("sanitizer").getValue();

                    if(l1.toString().equals(title)) {
                        s1 = "\nHospital name = " + l1.toString() + "\n\nphone number is =" + data.getKey() + "\n\n" + "face mask is =" + l2.toString() + "\n\n"
                                + "face shield is =" + l3.toString() + "\n\n"+ "gloves is =" + l4.toString() + "\n\n"+ "ppe is =" + l5.toString() + "\n\n"+ "sanitizer is =" + l6.toString() + "\n";

                        System.out.println(s1);

                        newButton.setText(s1);
                        //if we click the button
                        newButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                //startActivity(new Intent(AvailableProducts.this, MapsActivity.class));
                                call(data.getKey());

                            }
                        });
                    }
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

        if(ContextCompat.checkSelfPermission(DetailsActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(DetailsActivity.this, new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
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

}
