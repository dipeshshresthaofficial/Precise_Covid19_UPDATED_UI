package com.example.coronago;

import android.content.Intent;
import android.os.Bundle;
import android.se.omapi.Session;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    Button login, logout;

    TextView name,date_of_birth;
    String number;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_profile,container,false);

        number = session();
        //Toast.makeText((MainActivity)getActivity(),"number is "+number,Toast.LENGTH_SHORT).show();

        name = (TextView) view.findViewById(R.id.name);
        date_of_birth =(TextView) view.findViewById(R.id.date_of_birth);
        getData();

        login = (Button) view.findViewById(R.id.loginBtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent((MainActivity)getActivity(), LoginActivity.class);
                startActivity(myIntent);
            }
        });

        logout = (Button) view.findViewById(R.id.logoutBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                for Fragment can use only getActivity() for other activity need to use "Activity_name.this"
                SessionManagement sessionManagement = new SessionManagement(getActivity());
                sessionManagement.removeSession();
                String temp = sessionManagement.getSession();
                System.out.println("$$$$$$$$$$$$$$$$$$"+temp);

//              move to login activity
                Intent myIntent = new Intent((MainActivity)getActivity(), LoginActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(myIntent);
            }
        });

        if(number.equals("none")){

            //Toast.makeText((MainActivity)getActivity(),"no user is login",Toast.LENGTH_SHORT).show();

            logout.setVisibility(View.GONE);

            name.setVisibility(View.GONE);
            date_of_birth.setVisibility(View.GONE);

        }
        else{
            //login.setVisibility();
            //Toast.makeText((MainActivity)getActivity(),"user is already logined",Toast.LENGTH_SHORT).show();

            login.setVisibility(View.GONE);
        }

        return view;
    }
    private String session() {

        System.out.println("Hello 2");
        SessionManagement sessionManagement = new SessionManagement((MainActivity)getActivity());
        return sessionManagement.getSession();
    }

    //function to get user name and dateof birth
    public void getData(){

        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Customers");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data: dataSnapshot.getChildren()){
                    //data.getKey();
                    if(data.getKey().equals(number)){
                        //setting the name and dateof birth
                        System.out.println("children " + data.getKey());
                        Object l = dataSnapshot.child(data.getKey()).child("name").getValue();
                        name.setText("Name: "+l.toString());
                        Object l1 = dataSnapshot.child(data.getKey()).child("date").getValue();
                        date_of_birth.setText("DOB:  "+l1.toString());

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
}
