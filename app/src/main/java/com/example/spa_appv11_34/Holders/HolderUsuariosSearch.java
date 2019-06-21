package com.example.spa_appv11_34.Holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spa_appv11_34.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class HolderUsuariosSearch extends RecyclerView.ViewHolder {

    CircleImageView civPhotoUser;
    TextView tvCenterName;
    TextView tvCenterType;

    public HolderUsuariosSearch(@NonNull View itemView) {
        super(itemView);

        civPhotoUser = itemView.findViewById(R.id.civphotoCentroSearchItem);
        tvCenterName = itemView.findViewById(R.id.tvnombreCentroSearchItem);
        tvCenterType = itemView.findViewById(R.id.tvtipoCentroSearchItem);

    }

    public CircleImageView getCivPhotoUser() {
        return civPhotoUser;
    }

    public void setCivPhotoUser(CircleImageView civPhotoUser) {
        this.civPhotoUser = civPhotoUser;
    }

    public TextView getTvCenterName() {
        return tvCenterName;
    }

    public void setTvCenterName(TextView tvCenterName) {
        this.tvCenterName = tvCenterName;
    }

    public TextView getTvCenterType() {
        return tvCenterType;
    }

    public void setTvCenterType(TextView tvCenterType) {
        this.tvCenterType = tvCenterType;
    }
}
