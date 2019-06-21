package com.example.spa_appv11_34.Holders;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spa_appv11_34.R;

public class holderGallerySimple extends RecyclerView.ViewHolder {

    private ImageView imageView;

    public holderGallerySimple(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.ivGallerySimple);

    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

}
