package com.example.spa_appv11_34.Holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spa_appv11_34.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class holderNotificaciones extends RecyclerView.ViewHolder {

    private CircleImageView fotoPerfil;
    private TextView texto;

    public holderNotificaciones(@NonNull View itemView) {
        super(itemView);

        fotoPerfil = itemView.findViewById(R.id.civFotoPerfilNotificationItem);
        texto = itemView.findViewById(R.id.tvNotificationItem);

    }

    public CircleImageView getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(CircleImageView fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public TextView getTexto() {
        return texto;
    }

    public void setTexto(TextView texto) {
        this.texto = texto;
    }
}
