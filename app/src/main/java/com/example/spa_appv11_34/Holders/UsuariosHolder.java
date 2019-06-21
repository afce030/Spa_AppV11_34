package com.example.spa_appv11_34.Holders;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spa_appv11_34.R;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsuariosHolder extends RecyclerView.ViewHolder {

    private CircleImageView fotoPerfilBuscador;
    private TextView username;
    private TextView followersCounter;
    private Button follow;

    public UsuariosHolder(@NonNull View itemView) {
        super(itemView);

        fotoPerfilBuscador = itemView.findViewById(R.id.civFotoPerfilSearch);
        username = itemView.findViewById(R.id.tvUsernameSearch);
        followersCounter = itemView.findViewById(R.id.tvFollowersUserItem);
        follow = itemView.findViewById(R.id.btnFollowSearch);

    }

    public CircleImageView getFotoPerfilBuscador() {
        return fotoPerfilBuscador;
    }

    public void setFotoPerfilBuscador(CircleImageView fotoPerfilBuscador) {
        this.fotoPerfilBuscador = fotoPerfilBuscador;
    }

    public TextView getUsername() {
        return username;
    }

    public void setUsername(TextView username) {
        this.username = username;
    }

    public Button getFollow() {
        return follow;
    }

    public void setFollow(Button follow) {
        this.follow = follow;
    }

    public TextView getFollowersCounter() {
        return followersCounter;
    }

    public void setFollowersCounter(TextView followersCounter) {
        this.followersCounter = followersCounter;
    }
}
