package com.example.spa_appv11_34;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class postViewerActivity extends AppCompatActivity {

    private Button back;
    private SliderLayout postImages;

    private CircleImageView userPhoto;
    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_viewer);

        userPhoto = findViewById(R.id.civFotoUserPostViewerAct);
        username = findViewById(R.id.tvUserNamePostViewerAct);

        Intent intent = getIntent();
        String username_obt = intent.getStringExtra("usuario");
        String fotoURL_obt = intent.getStringExtra("foto");

        Glide.with(postViewerActivity.this).load(fotoURL_obt).into(userPhoto);
        username.setText(username_obt);

        back = findViewById(R.id.btnBackPostViewerAct);
        postImages = findViewById(R.id.slShowPostImagesPostViewerAct);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        List<String> imagesList = new ArrayList<>();
        imagesList.add(intent.getStringExtra("img1"));
        imagesList.add(intent.getStringExtra("img2"));
        imagesList.add(intent.getStringExtra("img3"));
        imagesList.add(intent.getStringExtra("img4"));

        for(int j = 0; j < imagesList.size(); j++) {
            if (!imagesList.get(j).equals("NoPhoto")) {
                TextSliderView textSliderView = new TextSliderView(postViewerActivity.this);
                textSliderView
                        .image(imagesList.get(j));

                if(j == 0){//El texto solo se añade en la primera imagen
                    textSliderView.description(intent.getStringExtra("texto"));
                }

                postImages.addSlider(textSliderView);
            }
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
