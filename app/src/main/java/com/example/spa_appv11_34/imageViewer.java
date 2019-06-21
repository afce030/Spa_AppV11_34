package com.example.spa_appv11_34;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class imageViewer extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        imageView = findViewById(R.id.imgRecibida);

        Intent intent = getIntent();
        Uri uri = Uri.parse(intent.getStringExtra("img"));
        String opt = intent.getStringExtra("local");

        if(opt.equals("0")) {
            imageView.setImageURI(uri);
        }
        else if(opt.equals("1")) {
            Glide.with(imageViewer.this).load(uri).into(imageView);
        }
    }
/*
    @Override
    public void onBackPressed() {

    }
*/
}
