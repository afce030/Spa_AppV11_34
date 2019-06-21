package com.example.spa_appv11_34.Regular_Classes;

import android.text.Spanned;

public class notification {

    private Spanned text;
    private String photo;

    public notification(Spanned text, String photo) {
        this.text = text;
        this.photo = photo;
    }

    public Spanned getText() {
        return text;
    }

    public void setText(Spanned text) {
        this.text = text;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
