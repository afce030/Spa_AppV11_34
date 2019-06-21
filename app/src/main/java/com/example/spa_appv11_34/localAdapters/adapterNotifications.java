package com.example.spa_appv11_34.localAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.spa_appv11_34.Holders.holderCategory;
import com.example.spa_appv11_34.Holders.holderNotificaciones;
import com.example.spa_appv11_34.R;
import com.example.spa_appv11_34.Regular_Classes.notification;

import java.util.List;

public class adapterNotifications extends RecyclerView.Adapter<holderNotificaciones> {

    List<notification> notificaciones;
    Context c;

    public adapterNotifications(List<notification> notificaciones, Context c) {
        this.notificaciones = notificaciones;
        this.c = c;
    }

    @NonNull
    @Override
    public holderNotificaciones onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item, parent, false);
        return new holderNotificaciones(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull holderNotificaciones holder, int position) {
        Glide.with(c).load(notificaciones.get(position).getPhoto()).into(holder.getFotoPerfil());
        holder.getTexto().setText(notificaciones.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return notificaciones.size();
    }

}
