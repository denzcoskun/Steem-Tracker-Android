package com.denzcoskun.steemtrackerandroid.profile.fragments.models;

/**
 * Created by Denx on 24.02.2018.
 */

public class CurrencyModel {

    private int image;
    private String type;
    private String text;
    private String value;

    public CurrencyModel(int image, String type, String text, String value) {
        this.image = image;
        this.type = type;
        this.text = text;
        this.value = value;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
