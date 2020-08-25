package com.example.coronago;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class OrderItems extends AppCompatActivity {

    EditText ppe,mask,shield,gloves,sanitizer,Address;

    Button order,track;

    String ppeNum,maskNum,shieldNum,glovesNum,sanitizerNum,address_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_items);

        ppe = (EditText)findViewById(R.id.orderPPE);
        mask = (EditText)findViewById(R.id.orderMask);
        shield = (EditText)findViewById(R.id.orderShield);
        gloves = (EditText)findViewById(R.id.orderGloves);
        sanitizer = (EditText)findViewById(R.id.orderSanitizer);
        Address = (EditText)findViewById(R.id.address);

        track = (Button)findViewById(R.id.trackOrder);
        track.setVisibility(View.INVISIBLE);
        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderItems.this, Progress.class);
                startActivity(intent);
            }
        });

        order = (Button)findViewById(R.id.placeOrder);

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ppeNum = ppe.getText().toString();
                maskNum = mask.getText().toString();
                shieldNum = shield.getText().toString();
                glovesNum = gloves.getText().toString();
                sanitizerNum = sanitizer.getText().toString();
                address_str = Address.getText().toString();


                if(ppe.getText().toString().equals("")&&mask.getText().toString().equals("")&&shield.getText().toString().equals("")&&
                        gloves.getText().toString().equals("")&&sanitizer.getText().toString().equals("")){
                    MainActivity.textToSpeech.speak("Please enter at least one items to order.", TextToSpeech.QUEUE_FLUSH,null);
                }

                else if(address_str.equals("")){
                    MainActivity.textToSpeech.speak("Please enter the address where you want your order to be delivered.",TextToSpeech.QUEUE_FLUSH,null);
                }

                else {
                    //MainActivity.textToSpeech.speak("Order Button is pressed.",TextToSpeech.QUEUE_FLUSH,null);

                    String location = address_str;//getting the user address
                    List<android.location.Address> addressList = null;
                    if(location!=null || location!=""){

                        Geocoder geocoder = new Geocoder(OrderItems.this);
                        try {
                            addressList = geocoder.getFromLocationName(location,1);
                            Address address = addressList.get(0);
                            LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
//
                            //MainActivity.textToSpeech.speak("Correct Address.",TextToSpeech.QUEUE_FLUSH,null);

                            final ProgressDialog dialog = new ProgressDialog(OrderItems.this);

                            dialog.setMessage("Placing your order.");
                            dialog.show();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    //startActivity(new Intent(SplashScreen.this,MainActivity.class));
                                    dialog.dismiss();
                                    track.setVisibility(View.VISIBLE);
                                }
                            },3000);

//

                        } catch (Exception e) {
                            //e.printStackTrace();
                            MainActivity.textToSpeech.speak("The address you entered never exists.",TextToSpeech.QUEUE_FLUSH,null);

                        }
//                        Address address = addressList.get(0);
//                        LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
//                        mMap.addMarker(new MarkerOptions().position(latLng).title("this is where you want to go"));
//                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                        float zoomto =  16.0f;
//                        mMap.moveCamera(CameraUpdateFactory.zoomTo(zoomto));

                    }
                }
            }
        });
    }
}
