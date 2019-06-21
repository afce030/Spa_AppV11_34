package com.example.spa_appv11_34.localAdapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.spa_appv11_34.Holders.holderGallerySimple;
import com.example.spa_appv11_34.PersonalizarPerfil;
import com.example.spa_appv11_34.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class galleryAdapterSimple extends RecyclerView.Adapter<holderGallerySimple> {

    List<String> galleryList = new ArrayList<>();//LISTA DE IMÁGENES DE LA GALERÍA
    Context context;
    ImageView photoView;
    List<Button> buttons;
    ConstraintLayout constraintLayout;
    Uri uri_selected;

    public galleryAdapterSimple(Context c,List<String> galleryList, ImageView photoView, List<Button> buttons, ConstraintLayout constraintLayout) {
        this.context = c;
        this.galleryList = galleryList;
        this.photoView = photoView;
        this.buttons = buttons;
        this.constraintLayout = constraintLayout;
    }

    @NonNull
    @Override
    public holderGallerySimple onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_item_simple, parent, false);
        return new holderGallerySimple(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull holderGallerySimple holder, final int position) {

        Uri current_uri;
        current_uri = Uri.parse(galleryList.get(position));
        //holder.getImageView().setImageURI(current_uri);

        Glide.with(context)
                .load(current_uri.getPath())
                .into(holder.getImageView());

        holder.getImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uri_selected = Uri.parse(galleryList.get(position));
                //photoView.setImageURI(uri_selected);

                Glide.with(context)
                        .load(uri_selected.getPath())
                        .into(photoView);

                buttons.get(0).setVisibility(View.VISIBLE);
                buttons.get(1).setVisibility(View.VISIBLE);
                buttons.get(2).setVisibility(View.VISIBLE);

                buttons.get(2).setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_button));

                constraintLayout.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    public Uri getUri_selected() {
        return uri_selected;
    }

}
