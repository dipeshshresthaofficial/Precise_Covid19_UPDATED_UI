package com.example.coronago;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddActivity extends AppCompatActivity {

    TextView hname,hmask,hsanitizer,hgloves,hshield,hppe;
    Button register;
    String name,mask,sanitizer,glove,shield,ppe;

    //variable for firebase database
    private FirebaseDatabase rootNode;
    DatabaseReference reference;

    String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        hname = (TextView)findViewById(R.id.hospital_name);

        hmask = (TextView)findViewById(R.id.entermask);
        hsanitizer = (TextView)findViewById(R.id.sanitize);
        hgloves = (TextView)findViewById(R.id.addgloves);
        hshield = (TextView)findViewById(R.id.shield);
        hppe = (TextView)findViewById(R.id.ppe_enter);

        register =(Button) findViewById(R.id.update_now);

        number = session();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change();
            }
        });

    }
    public void change(){

        name = hname.getText().toString();
        mask = hmask.getText().toString();
        sanitizer = hsanitizer.getText().toString();
        glove = hgloves.getText().toString();
        shield = hshield.getText().toString();
        ppe = hppe.getText().toString();
        if(name.equals("")||mask.equals("")||sanitizer.equals("")||glove.equals("")||shield.equals("")||ppe.equals("")){
            Toast.makeText(AddActivity.this,"Please enter all the field",Toast.LENGTH_SHORT).show();
        }

        else{
            //inserting into database
            rootNode = FirebaseDatabase.getInstance();//include all the tables of the database
            reference = rootNode.getReference("Tools");

            AddDataClass m1 = new AddDataClass(Integer.parseInt(shield),Integer.parseInt(glove),Integer.parseInt(ppe),Integer.parseInt(sanitizer),Integer.parseInt(mask),name);
            //stored in database
            //reference.setValue("First Data stored");
            reference.child(number).setValue(m1);
            Toast.makeText(this, "Registered successflly ", Toast.LENGTH_SHORT).show();

            Intent myIntent = new Intent(AddActivity.this, activity_stock2.class);
            startActivity(myIntent);
        }

    }

    private String session() {

        System.out.println("Hello 2");
        SessionManagement sessionManagement = new SessionManagement(AddActivity.this);
        return sessionManagement.getSession();
    }
}
