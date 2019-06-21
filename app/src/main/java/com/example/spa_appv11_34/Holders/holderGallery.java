package com.example.spa_appv11_34.Holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spa_appv11_34.R;

public class holderGallery extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private RadioButton radioButton;

    public holderGallery(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.ivGallery);
        radioButton = itemView.findViewById(R.id.radioSelected);

    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public RadioButton getRadioButton() {
        return radioButton;
    }

    public void setRadioButton(RadioButton radioButton) {
        this.radioButton = radioButton;
    }

}
