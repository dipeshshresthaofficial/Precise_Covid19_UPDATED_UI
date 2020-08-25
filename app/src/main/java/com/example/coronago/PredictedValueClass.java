package com.example.coronago;

public class PredictedValueClass {

    //empty constructor
    public PredictedValueClass(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    // This is for the "Tools " table of firebase database


    String number,effeced;
    public PredictedValueClass(String effeced, String number) {
        this.number = number;
        this.effeced = effeced;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEffeced() {
        return effeced;
    }

    public void setEffeced(String effeced) {
        this.effeced = effeced;
    }
}
