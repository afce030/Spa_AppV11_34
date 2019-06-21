package com.example.spa_appv11_34.Holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.SliderLayout;
import com.example.spa_appv11_34.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class miniPostHolder extends RecyclerView.ViewHolder {

    private TextView userNamePost;
    private CircleImageView FotoUser;
    private TextView likesPost;
    private SliderLayout imagePost;

    public miniPostHolder(@NonNull View itemView) {
        super(itemView);
        userNamePost = itemView.findViewById(R.id.tvUserNamePostSearch);
        FotoUser = itemView.findViewById(R.id.civFotoUserPostSearch);
        likesPost = itemView.findViewById(R.id.tvPostLikesSearch);
        imagePost = itemView.findViewById(R.id.slShowPostImagesSearch);
    }

    public TextView getUserNamePost() {
        return userNamePost;
    }

    public void setUserNamePost(TextView userNamePost) {
        this.userNamePost = userNamePost;
    }

    public CircleImageView getFotoUser() {
        return FotoUser;
    }

    public void setFotoUser(CircleImageView fotoUser) {
        FotoUser = fotoUser;
    }

    public TextView getLikesPost() {
        return likesPost;
    }

    public void setLikesPost(TextView likesPost) {
        this.likesPost = likesPost;
    }

    public SliderLayout getImagePost() {
        return imagePost;
    }

    public void setImagePost(SliderLayout imagePost) {
        this.imagePost = imagePost;
    }
}
