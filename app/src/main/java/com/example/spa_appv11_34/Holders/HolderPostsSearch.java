package com.example.spa_appv11_34.Holders;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.SliderLayout;

import de.hdodenhof.circleimageview.CircleImageView;

public class HolderPostsSearch extends RecyclerView.ViewHolder {

    SliderLayout container;
    TextView username;
    CircleImageView userPhoto;
    TextView likes;
    ImageButton likesButton;

    public HolderPostsSearch(@NonNull View itemView) {
        super(itemView);
    }

}
