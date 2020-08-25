package com.example.coronago;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AvailabilityActivity extends AppCompatActivity {

    Button ppe,face_mask,face_shield,glove,sanitizer,add;
    String passed_value;
    String number;
    Boolean available;

    public static String temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability);

        number = session();

        ppe = (Button) findViewById(R.id.ppeBtn);
        ppe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp ="ppe";
                startActivity(new Intent(AvailabilityActivity.this, AvailableProducts.class));
            }
        });

        face_mask = (Button) findViewById(R.id.faceMaskBtn);

        face_mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp ="face_mask";
                startActivity(new Intent(AvailabilityActivity.this, AvailableProducts.class));
            }
        });

        face_shield = (Button) findViewById(R.id.faceShieldBtn);

        face_shield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp ="face_shield";
                startActivity(new Intent(AvailabilityActivity.this, AvailableProducts.class));
            }
        });

        glove = (Button) findViewById(R.id.gloveBtn);

        glove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp ="glove";
                startActivity(new Intent(AvailabilityActivity.this, AvailableProducts.class));
            }
        });

        sanitizer = (Button) findViewById(R.id.sanitizerBtn);

        sanitizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp ="sanitizer";
                startActivity(new Intent(AvailabilityActivity.this, AvailableProducts.class));
            }
        });

    }

    private String session() {

        System.out.println("Hello 2");
        SessionManagement sessionManagement = new SessionManagement(AvailabilityActivity.this);
        return sessionManagement.getSession();
    }

}
