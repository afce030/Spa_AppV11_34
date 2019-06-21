package com.example.spa_appv11_34.Holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.SliderLayout;
import com.example.spa_appv11_34.R;
import com.example.spa_appv11_34.sliderLayoutNoSwipe;

public class holderCategory extends RecyclerView.ViewHolder {

    private TextView categoryName;
    private sliderLayoutNoSwipe imagesContainer;

    public holderCategory(@NonNull View itemView) {
        super(itemView);

        categoryName = itemView.findViewById(R.id.tvLabelText);
        imagesContainer = itemView.findViewById(R.id.slShowLabel);

    }

    public TextView getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(TextView categoryName) {
        this.categoryName = categoryName;
    }

    public sliderLayoutNoSwipe getImagesContainer() {
        return imagesContainer;
    }

    public void setImagesContainer(sliderLayoutNoSwipe imagesContainer) {
        this.imagesContainer = imagesContainer;
    }
}
