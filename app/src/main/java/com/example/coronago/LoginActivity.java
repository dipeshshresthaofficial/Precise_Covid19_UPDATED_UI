package com.example.coronago;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EditText mobile;
    EditText pwd;
    Button mButtonLogin;
    TextView mTextViewRegister;

    String dmobile, dpwd;
    String oriType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mobile=(EditText)findViewById(R.id.loginMobile);
        pwd=(EditText)findViewById(R.id.loginPwd);

        mButtonLogin=(Button) findViewById(R.id.button_login);
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });


        mTextViewRegister=(TextView) findViewById(R.id.textview_register);
        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                Intent registerIntent1=new Intent( LoginActivity.this, Register2Activity.class);
                startActivity(registerIntent1);
            }
        });


    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        checkSession();
//    }
//
//    private void checkSession() {
//
//        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
//        String user_mobile = sessionManagement.getSession();
//
//        System.out.println("##################"+user_mobile);
//
//        if(user_mobile!="none"){
//            moveToMainActivity();
//        }
//        else{
//            //do nothing
//        }
//    }

    public void loginUser(){



        dmobile = mobile.getText().toString();
        dpwd = pwd.getText().toString();


            final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Customers");

            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int count= 0;

                    for(DataSnapshot data:dataSnapshot.getChildren()){

                        if(dmobile.equals(data.getKey())){
                            // Get Post object and use the values to update the UI
                            UserHelperClass post = dataSnapshot.child(dmobile).getValue(UserHelperClass.class);

                            String oriPwd = post.password;  //retriving original password from database.
                            oriType = post.type;


                            if(dpwd.equals(oriPwd)){
//                                Toast.makeText(LoginActivity.this, "Successfull", Toast.LENGTH_SHORT).show();
                                Toast.makeText(LoginActivity.this, "Welcome Back "+post.name, Toast.LENGTH_SHORT).show();


                                //Creating a session of user(Saved session of user)
                                System.out.println("$$$$$$$$$$$$$$$$$$$$$$$Login Activity "+oriType);

                                userSession user = new userSession(dmobile);
                                SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
                                sessionManagement.saveSession(user);

                                moveToMainActivity();
                                count++;
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "Unsuccessful Please Check Your Password!", Toast.LENGTH_SHORT).show();
                                count++;
                            }

                        }
                    }

                    if(count ==0){
                        Toast.makeText(LoginActivity.this, "Your Mobile number is incorrect!", Toast.LENGTH_SHORT).show();
                    }


                    // ...
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

    private void moveToMainActivity() {

        //It will redirect you to the Home Fragment of MainActivity because in MainActivity, I have set Home Fragment as Default Fragment

        Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//        myIntent.putExtra("user_type",post.type);
        startActivity(myIntent);
    }

//    public void temp(){
//        Intent registerIntent1=new Intent( LoginActivity.this, activity_stock2.class);
//        startActivity(registerIntent1);
//    }
}
