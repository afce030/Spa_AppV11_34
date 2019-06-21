package com.example.spa_appv11_34.localAdapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spa_appv11_34.Holders.usuariosSugeridosHolder;
import com.example.spa_appv11_34.R;

import java.util.ArrayList;
import java.util.List;

public class suggestedUsersAdapter extends RecyclerView.Adapter<usuariosSugeridosHolder> {

    List<String> userNames = new ArrayList<>();
    EditText postContent;
    ConstraintLayout suggestions;

    public suggestedUsersAdapter(EditText campo, ConstraintLayout constraintLayout) {
        this.postContent = campo;
        this.suggestions = constraintLayout;
    }

    public void addUser(String s){
        userNames.add(s);
    }

    @NonNull
    @Override
    public usuariosSugeridosHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View vista = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.user_suggestion_item, viewGroup, false);

        return new usuariosSugeridosHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull usuariosSugeridosHolder usuariosSugeridosHolder, final int i) {
        usuariosSugeridosHolder.getUser().setText("@"+userNames.get(i).toString());

        usuariosSugeridosHolder.getUser().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentText = postContent.getText().toString();
                currentText = currentText.substring(0,currentText.lastIndexOf(" "));
                postContent.setText(currentText+" @"+userNames.get(i).toString());
                suggestions.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userNames.size();
    }
}
