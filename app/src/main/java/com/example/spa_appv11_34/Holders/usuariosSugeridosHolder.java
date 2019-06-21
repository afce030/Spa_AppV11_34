package com.example.spa_appv11_34.Holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spa_appv11_34.R;

public class usuariosSugeridosHolder extends RecyclerView.ViewHolder {

    private TextView user;

    public usuariosSugeridosHolder(@NonNull View itemView) {
        super(itemView);

        user = itemView.findViewById(R.id.tvUserSuggested);

    }

    public TextView getUser() {
        return user;
    }

    public void setUser(TextView user) {
        this.user = user;
    }

}
