package com.example.spa_appv11_34.Holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class holderCentroSearch extends RecyclerView.ViewHolder {

    private ImageView foto;
    private TextView nombre;
    private TextView historia;
    private RatingBar calificación;

    public holderCentroSearch(@NonNull View itemView) {
        super(itemView);
    }

    public ImageView getFoto() {
        return foto;
    }

    public void setFoto(ImageView foto) {
        this.foto = foto;
    }

    public TextView getNombre() {
        return nombre;
    }

    public void setNombre(TextView nombre) {
        this.nombre = nombre;
    }

    public TextView getHistoria() {
        return historia;
    }

    public void setHistoria(TextView historia) {
        this.historia = historia;
    }

    public RatingBar getCalificación() {
        return calificación;
    }

    public void setCalificación(RatingBar calificación) {
        this.calificación = calificación;
    }
}
