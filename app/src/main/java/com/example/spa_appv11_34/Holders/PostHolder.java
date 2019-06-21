package com.example.spa_appv11_34.Holders;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.SliderLayout;
import com.example.spa_appv11_34.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostHolder extends RecyclerView.ViewHolder {

    private TextView userNamePost;
    private CircleImageView FotoUser;
    private TextView postContent;
    private TextView likesPost;
    private SliderLayout imagePost;

    public PostHolder(@NonNull final View itemView) {
        super(itemView);
        userNamePost = itemView.findViewById(R.id.tvUserNamePost);
        FotoUser = itemView.findViewById(R.id.civFotoUserPost);
        postContent = itemView.findViewById(R.id.tvPostHistory);
        likesPost = itemView.findViewById(R.id.tvPostLikes);
        imagePost = itemView.findViewById(R.id.slShowPostImages);
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

    public TextView getPostContent() {
        return postContent;
    }

    public void setPostContent(TextView postContent) {
        this.postContent = postContent;
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
