package com.example.coronago;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class activity_stock2 extends AppCompatActivity {

    Button ppeBtn;
    Button faceMaskBtn;
    Button faceShieldBtn;
    Button glovesBtn;
    Button sanitizerBtn;

    Button add;
    public static String temp;

    public String user_mobile;
    Integer dFaceMask,dFaceShield,dGlove,dSanitizer;
    Integer dppe;
    String EVEN = "check";

    public Boolean temp_variable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock2);

        //retirving values from Tools Table in database
        database();


//        getData();
//
//


        //if ppe button is pressed
        ppeBtn = (Button) findViewById(R.id.ppe);
        ppeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                temp="ppe";
                Intent intent = new Intent(activity_stock2.this, UpdateStockActivity2.class);

                intent.putExtra(Intent.EXTRA_TEXT, Integer.toString(dppe));

                startActivity(intent);

            }
        });

        //if faceMaskBtn is pressed
        faceMaskBtn = (Button) findViewById(R.id.faceMask);
        faceMaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp="face_mask";
                Intent intent = new Intent(activity_stock2.this, UpdateStockActivity2.class);
                intent.putExtra(Intent.EXTRA_TEXT,Integer.toString(dFaceMask));

                startActivity(intent);
            }
        });


        //if faceshield is pressed
        faceShieldBtn = (Button) findViewById(R.id.faceSheild);
        faceShieldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp="face_shield";
                Intent intent = new Intent(activity_stock2.this, UpdateStockActivity2.class);
                intent.putExtra(Intent.EXTRA_TEXT,Integer.toString(dFaceShield));
                startActivity(intent);
            }
        });

        //if gloves button is pressed
        glovesBtn = (Button) findViewById(R.id.addgloves);
        glovesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp="glove";
                Intent intent = new Intent(activity_stock2.this, UpdateStockActivity2.class);
                intent.putExtra(Intent.EXTRA_TEXT,Integer.toString(dGlove));
                startActivity(intent);
            }
        });

        //if sanntizer button is pressed
        sanitizerBtn = (Button) findViewById(R.id.sanitizer);
        sanitizerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp = "sanitizer";
                Intent intent = new Intent(activity_stock2.this, UpdateStockActivity2.class);
                intent.putExtra(Intent.EXTRA_TEXT,Integer.toString(dSanitizer));

                startActivity(intent);
            }
        });

        //if add button is pressed
        add = (Button) findViewById(R.id.add_data);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //temp =""
                startActivity(new Intent(activity_stock2.this, AddActivity.class));

            }
        });



    }

////    function to get user name and dateof birth
//    public void getData(){
//
//        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Tools");
//
//        ValueEventListener postListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                for(DataSnapshot data: dataSnapshot.getChildren()){
//                    //data.getKey();
//
//
//                    System.out.println("Checking/.................. "+data.getKey()+user_mobile);
//                    if(data.getKey().equals(user_mobile)){
//
//                        //Toast.makeText(activity_stock2.this,"data is available",Toast.LENGTH_SHORT).show();
//                        //available = true;
//                        temp_variable =true;
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // ...
//            }
//        };
//        myRef.addValueEventListener(postListener);
//
//    }


    private void database() {

        //retriving user mobile number from session

        System.out.println("Hello 1");
        user_mobile = session();


        System.out.println("################## "+user_mobile);
        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Tools");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("Hello 3");
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    System.out.println("Hello 4");
                    if(user_mobile.equals(data.getKey())){
                        System.out.println("Hello 5");

                        temp_variable=true;
                        includeADDBtn();
                        // Get Post object and use the values to update the UI
                        AddDataClass post = dataSnapshot.child(user_mobile).getValue(AddDataClass.class);
                        dppe = post.ppe;
                        dFaceMask = post.face_mask;
                        dFaceShield = post.face_shield;
                        dSanitizer = post.sanitizer;
                        dGlove = post.glove;
                        System.out.println("Hello 3");

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
//                    Toast.makeText(getApplicationContext(),"Data base not connected",Toast.LENGTH_SHORT);

                // Getting Post failed, log a message
                Log.w("asdjfj", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        myRef.addValueEventListener(postListener);
    }

    private void includeADDBtn() {
        System.out.println("Returned Value available................ "+user_mobile+temp_variable);
        Toast.makeText(activity_stock2.this,"available is "+temp_variable,Toast.LENGTH_SHORT).show();

        if(temp_variable){
            add.setVisibility(View.GONE);
        }
        else{
            add.setVisibility(View.VISIBLE);
            ppeBtn.setVisibility(View.GONE);
            faceShieldBtn.setVisibility(View.GONE);
            faceMaskBtn.setVisibility(View.GONE);
            glovesBtn.setVisibility(View.GONE);
            sanitizerBtn.setVisibility(View.GONE);
        }
    }

    private String session() {

        System.out.println("Hello 2");
        SessionManagement sessionManagement = new SessionManagement(activity_stock2.this);
        return sessionManagement.getSession();
    }
}
