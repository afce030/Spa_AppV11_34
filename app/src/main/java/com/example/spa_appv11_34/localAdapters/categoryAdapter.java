package com.example.spa_appv11_34.localAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.spa_appv11_34.AjustesActivity;
import com.example.spa_appv11_34.Holders.holderCategory;
import com.example.spa_appv11_34.Holders.holderGallerySimple;
import com.example.spa_appv11_34.Login;
import com.example.spa_appv11_34.R;
import com.example.spa_appv11_34.SearchActivity2;

import java.util.ArrayList;
import java.util.List;

public class categoryAdapter extends RecyclerView.Adapter<holderCategory>{

    private Context c;
    private List<String> categories;
    private List<List<Integer>> imagesList;

    public categoryAdapter(Context c, List<String> categories, List<List<Integer>> imagesList) {
        this.c = c;
        this.categories = categories;
        this.imagesList = imagesList;
    }

    @NonNull
    @Override
    public holderCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slider_item, parent, false);
        return new holderCategory(vista);    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull holderCategory holder, int position) {

        if( true ) {
            holder.getCategoryName().setText(categories.get(position));

            for (int j = 0; j < imagesList.get(position).size(); j++) {
                DefaultSliderView defaultSliderView = new DefaultSliderView(c);
                defaultSliderView
                        .image(imagesList.get(position).get(j));

                holder.getImagesContainer().addSlider(defaultSliderView);
            }

            //holder.getImagesContainer().stopAutoCycle();
            holder.getImagesContainer().setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
            holder.getImagesContainer().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    c.startActivity(new Intent(c, SearchActivity2.class));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

}
