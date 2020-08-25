package com.example.coronago;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateStockActivity2 extends AppCompatActivity {

    TextView remainingQtyLabel;
    EditText qtyEntered;
    Button added, subtracted;
    String quantity;
    Integer remQty;

    TextView text_to_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_stock2);

        text_to_show = (TextView) findViewById(R.id.qtyLabel);


        qtyEntered = findViewById(R.id.enterQty);

        added = findViewById(R.id.addBtn);
        subtracted = findViewById(R.id.subBtn);


        //    receving the passed value through intent

        Intent intent = getIntent();
        quantity = intent.getStringExtra(Intent.EXTRA_TEXT);

        remQty = Integer.parseInt(quantity);

        remainingQtyLabel = (TextView) findViewById(R.id.qtyLabel);
//        System.out.println("############### "+quantity);
        remainingQtyLabel.setText("Remaining Qty: "+quantity);
//
//        }
    }


    public void subBtnClicked(View view) {

        String value =  qtyEntered.getText().toString();
        if(value.equals("")){

            Toast.makeText(this, "Please Enter the quantity", Toast.LENGTH_SHORT).show();
        }
        else{
//            Integer oriValue = Integer.parseInt(remainingQtyLabel.getText().toString());
            Integer subValue = Integer.parseInt(value);

            if(remQty<subValue){
                Toast.makeText(this, "Your Stock to low for that "+quantity, Toast.LENGTH_SHORT).show();
            }
            else{
                Integer totalQty = remQty - subValue;
                text_to_show.setText("Remaining Qty: "+totalQty);

                updateDatabase(totalQty);
                Toast.makeText(this, "Your Stock is Updated "+totalQty , Toast.LENGTH_SHORT).show();
            }



        }
    }

    public void addBtnClicked(View view) {

        String value =  qtyEntered.getText().toString();
//        System.out.println("$%$%$%$% "+value);
        if(value.equals("")){

            Toast.makeText(this, "Please Enter the quantity", Toast.LENGTH_SHORT).show();
        }
        else{
//            Integer oriValue = Integer.parseInt(remainingQtyLabel.getText().toString());
            Integer addValue = Integer.parseInt(value);

            Integer totalQty = remQty + addValue;

            text_to_show.setText("Remaining Qty: "+totalQty);

            updateDatabase(totalQty);

            Toast.makeText(this, "Your Stock is Updated "+totalQty, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateDatabase(Integer totalQty) {

        String user_mobile = getUserMobile();
        //get inside the covid data with particular id
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Tools").child(user_mobile);

        databaseReference.child(activity_stock2.temp).setValue(totalQty);


    }

    private String getUserMobile() {

        SessionManagement sessionManagement = new SessionManagement(UpdateStockActivity2.this);
        return sessionManagement.getSession();
    }
}
