package com.example.coronago;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CovidTestActivity extends AppCompatActivity {

    Button gender,male,female,age,symptom,symptom1,symptom2,symptom3,symptom4,temp,temp1,temp2,temp3, travel,travel1,travel2,disease,disease1,disease2;
    EditText enterAge;

    public static int gender1,age1,symptoms,temperature,travel_history,disease_history;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_test);

        gender = findViewById(R.id.genderBtn);
        male = findViewById(R.id.maleBtn);
        female = findViewById(R.id.femaleBtn);

        symptom = findViewById(R.id.symptomBtn);
        symptom1 = findViewById(R.id.symptomBtn1);
        symptom2 = findViewById(R.id.symptomBtn2);
        symptom3 = findViewById(R.id.symptomBtn3);
        symptom4 = findViewById(R.id.symptomBtn4);


        temp = findViewById(R.id.tempBtn);
        temp1 = findViewById(R.id.tempBtn1);
        temp2 = findViewById(R.id.tempBtn2);
        temp3 = findViewById(R.id.tempBtn3);


        travel = findViewById(R.id.travelBtn);
        travel1 = findViewById(R.id.travelBtn1);
        travel2 = findViewById(R.id.travelBtn2);

        disease = findViewById(R.id.diseaseBtn);
        disease1 = findViewById(R.id.diseaseBtn1);
        disease2 = findViewById(R.id.diseaseBtn2);


        age = findViewById(R.id.ageBtn);
        enterAge = findViewById(R.id.ageView);
        enterAge.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                //After editing the text view and On pressing Enter Button following gets executed
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (i == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    age1 = Integer.parseInt(enterAge.getText().toString());//getting the entered age
                    symptom.setVisibility(View.VISIBLE);
                    symptom1.setVisibility(View.VISIBLE);
                    symptom2.setVisibility(View.VISIBLE);
                    symptom3.setVisibility(View.VISIBLE);
                    symptom4.setVisibility(View.VISIBLE);
                    age.setVisibility(View.GONE);
                    enterAge.setVisibility(View.GONE);
                    return true;
                }

                return false;
            }
        });





    }


    //if male button is clicked
    public void genderClicked(View view) {

        gender1= 0;
        gender.setVisibility(View.GONE);
        male.setVisibility(View.GONE);
        female.setVisibility(View.GONE);
        age.setVisibility(View.VISIBLE);
        enterAge.setVisibility(View.VISIBLE);

    }

    //if female button is clicked
    public void genderClicked1(View view) {

        gender1=1;
        gender.setVisibility(View.GONE);
        male.setVisibility(View.GONE);
        female.setVisibility(View.GONE);
        age.setVisibility(View.VISIBLE);
        enterAge.setVisibility(View.VISIBLE);

    }


    //if there is any one of the symptom
    public void symptomClicked(View view) {

        symptoms=1;
        symptom.setVisibility(View.GONE);
        symptom1.setVisibility(View.GONE);
        symptom2.setVisibility(View.GONE);
        symptom3.setVisibility(View.GONE);
        symptom4.setVisibility(View.GONE);


        temp.setVisibility(View.VISIBLE);
        temp1.setVisibility(View.VISIBLE);
        temp2.setVisibility(View.VISIBLE);
        temp3.setVisibility(View.VISIBLE);
    }


    //if there is no symptom
    public void symptomClicked1(View view) {

        symptoms=0;
        symptom.setVisibility(View.GONE);
        symptom1.setVisibility(View.GONE);
        symptom2.setVisibility(View.GONE);
        symptom3.setVisibility(View.GONE);
        symptom4.setVisibility(View.GONE);


        temp.setVisibility(View.VISIBLE);
        temp1.setVisibility(View.VISIBLE);
        temp2.setVisibility(View.VISIBLE);
        temp3.setVisibility(View.VISIBLE);
    }

    //normal temperature
    public void tempClicked(View view) {

        temperature=0;
        temp.setVisibility(View.GONE);
        temp1.setVisibility(View.GONE);
        temp2.setVisibility(View.GONE);
        temp3.setVisibility(View.GONE);

        travel.setVisibility(View.VISIBLE);
        travel1.setVisibility(View.VISIBLE);
        travel2.setVisibility(View.VISIBLE);
    }

    public void tempClicked1(View view) {

        temperature = 1;
        temp.setVisibility(View.GONE);
        temp1.setVisibility(View.GONE);
        temp2.setVisibility(View.GONE);
        temp3.setVisibility(View.GONE);

        travel.setVisibility(View.VISIBLE);
        travel1.setVisibility(View.VISIBLE);
        travel2.setVisibility(View.VISIBLE);
    }

    public void tempClicked2(View view) {

        temperature= 2;
        temp.setVisibility(View.GONE);
        temp1.setVisibility(View.GONE);
        temp2.setVisibility(View.GONE);
        temp3.setVisibility(View.GONE);

        travel.setVisibility(View.VISIBLE);
        travel1.setVisibility(View.VISIBLE);
        travel2.setVisibility(View.VISIBLE);
    }

    //if there is travel history
    public void travelClicked(View view){

        travel_history =1;
        travel.setVisibility(View.GONE);
        travel1.setVisibility(View.GONE);
        travel2.setVisibility(View.GONE);

        disease.setVisibility(View.VISIBLE);
        disease1.setVisibility(View.VISIBLE);
        disease2.setVisibility(View.VISIBLE);

    }


    public void travelClicked1(View view){

        travel_history=0;
        travel.setVisibility(View.GONE);
        travel1.setVisibility(View.GONE);
        travel2.setVisibility(View.GONE);

        disease.setVisibility(View.VISIBLE);
        disease1.setVisibility(View.VISIBLE);
        disease2.setVisibility(View.VISIBLE);

    }

    //if there is disease history
    public void diseaseClicked(View view){
        disease_history=1;
        Intent predictnow = new Intent(CovidTestActivity.this,Predicted.class);
        startActivity(predictnow);
    }

    //if there is no disease history
    public void diseaseClicked1(View view){
        disease_history=0;
        Intent predictnow = new Intent(CovidTestActivity.this,Predicted.class);
        startActivity(predictnow);
    }

}
