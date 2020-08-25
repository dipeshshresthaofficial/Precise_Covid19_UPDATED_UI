package com.example.coronago;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.PublicKey;
import java.util.Locale;

public class Predicted extends AppCompatActivity {

    //initializing the number picker ,textview button etc
    TextView input;

    //the tflite filename and the interpreter
    String modelFile="multiple.tflite";
    Interpreter tflite;
    String effected ;
    String number;
    public TextToSpeech textToSpeech;

    //declared for firebase
    private FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predicted);

        textToSpeech =new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                textToSpeech.setLanguage(Locale.ENGLISH);
            }
        });

        number = session();

        //getting by reference to id
        input = findViewById(R.id.text_to_show);

        //trying to load the tflite file
        try {
            tflite = new Interpreter(loadModelFile());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        float prediction = doInference();
        input.setText(Float.toString(prediction));
        if(prediction>0.5){
            effected="yes";
            highRisk();
            input.setText("YOU HAVE HIGH RISK OF BEING EFFECTED BY CORONA WITH "+prediction*100+" %");

        }

        else{
            effected="no";
            lowRisk();
            input.setText("YOU HAVE LOW RISK OF BEING EFFECTED BY CORONA "+prediction*100+"%");
        }

        //inserting into database
        rootNode = FirebaseDatabase.getInstance();//include all the tables of the database
        reference = rootNode.getReference("Covidinfo");

        PredictedValueClass m1 = new PredictedValueClass(effected,number);
        //stored in database
        //reference.setValue("First Data stored");
        reference.child(number).setValue(m1);
        Toast.makeText(this, "Succesfully Registered in database", Toast.LENGTH_SHORT).show();



    }

    private void lowRisk() {

        MainActivity.textToSpeech.speak("YOU HAVE LOW RISK ",TextToSpeech.QUEUE_FLUSH,null);
    }

    private void highRisk() {

        MainActivity.textToSpeech.speak("YOU HAVE HIGH RISK ",TextToSpeech.QUEUE_FLUSH,null);
    }

    public float doInference(){
        //getting the userinputed value
        float[] inputVal = {CovidTestActivity.gender1,CovidTestActivity.age1,CovidTestActivity.symptoms,CovidTestActivity.temperature,CovidTestActivity.travel_history,
        CovidTestActivity.disease_history};
        //inputVal[0] = Float.valueOf(inputString);
        float[][] outputval = new float[1][1];
        tflite.run(inputVal,outputval);
         float value_return = outputval[0][0];
         return value_return;
    }


    //code to load the model file
    private MappedByteBuffer loadModelFile() throws IOException {
        //open the model using an input stream and memory map it to load
        AssetFileDescriptor fileDescriptor = this.getAssets().openFd(modelFile);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    private String session() {

        System.out.println("Hello 2");
        SessionManagement sessionManagement = new SessionManagement(Predicted.this);
        return sessionManagement.getSession();
    }

}

