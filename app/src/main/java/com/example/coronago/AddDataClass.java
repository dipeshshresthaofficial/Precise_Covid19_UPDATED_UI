package com.example.coronago;

public class AddDataClass {

    public AddDataClass(){

    }
    // This is for the "Tools " table of firebase database

    public AddDataClass(int face_shield, int glove, int ppe, int sanitizer, int face_mask,String hname) {
        this.face_shield = face_shield;
        this.glove = glove;
        this.ppe = ppe;
        this.sanitizer = sanitizer;
        this.face_mask = face_mask;
        this.hospital_name =hname;
    }

    public int getFace_shield() {
        return face_shield;
    }

    public void setFace_shield(int face_shield) {
        this.face_shield = face_shield;
    }

    public int getGlove() {
        return glove;
    }

    public void setGlove(int glove) {
        this.glove = glove;
    }

    public int getPpe() {
        return ppe;
    }

    public void setPpe(int ppe) {
        this.ppe = ppe;
    }

    public int getSanitizer() {
        return sanitizer;
    }

    public void setSanitizer(int sanitizer) {
        this.sanitizer = sanitizer;
    }

    public int getFace_mask() {
        return face_mask;
    }

    public void setFace_mask(int face_mask) {
        this.face_mask = face_mask;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    int face_shield;
    int glove;
    int ppe;
    int sanitizer;
    int face_mask;
    String hospital_name;

}
