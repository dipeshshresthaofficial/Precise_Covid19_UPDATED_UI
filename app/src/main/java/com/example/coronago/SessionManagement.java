package com.example.coronago;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_SESSION_NAME = "session";
    String SESSION_KEY;
//    String SESSION_TYPE;
    String smobile,stype;


//    constructor

    public SessionManagement(Context context){

        sharedPreferences = context.getSharedPreferences(SHARED_SESSION_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(userSession user){

    //saving the session of user

        smobile = user.getMobile();
//        stype = user.getType();
        System.out.println("%%%%%%%%%%%%%"+smobile);
//        System.out.println("%%%%%%%%%%%%% Session Management "+stype);
        editor.putString(SESSION_KEY,smobile).commit();
//        editor.putString(SESSION_TYPE,stype).commit();
        System.out.println("%%%%%%%%%%%%% SESSION KEY"+SESSION_KEY);
    }

    public String getSession(){

        //Retriving the information of the logged in user

        return sharedPreferences.getString(smobile, "none");
    }

//    public String getUserType(){
//
//        return sharedPreferences.getString(stype,"normal");
//    }





    public void removeSession(){

        // Deleting the session of user
        editor.putString(smobile, "none").commit();
//        editor.putString(stype, "none").commit();
    }
}
