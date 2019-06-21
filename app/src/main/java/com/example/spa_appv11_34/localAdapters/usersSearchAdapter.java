package com.example.spa_appv11_34.localAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.spa_appv11_34.Holders.HolderUsuariosSearch;
import com.example.spa_appv11_34.Holders.UsuariosHolder;
import com.example.spa_appv11_34.Holders.usuariosSugeridosHolder;
import com.example.spa_appv11_34.Interaction_Classes.UsuarioDatabase;
import com.example.spa_appv11_34.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class usersSearchAdapter extends RecyclerView.Adapter<HolderUsuariosSearch>  implements Filterable {

    private List<UsuarioDatabase> users = new ArrayList<>();
    private List<UsuarioDatabase> visibleUsers = new ArrayList<>();
    private Context c;

    public usersSearchAdapter(List<UsuarioDatabase> users, Context c) {
        this.users = users;
        visibleUsers.addAll(users);
        this.c = c;
    }

    @NonNull
    @Override
    public HolderUsuariosSearch onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_search_item, parent, false);

        return new HolderUsuariosSearch(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderUsuariosSearch holder, int position) {
        //Glide.with(c).load(visibleUsers.get(position).getURL_Foto()).into(holder.getCivPhotoUser());
        holder.getTvCenterName().setText(visibleUsers.get(position).getNombreUsuario());
        holder.getTvCenterType().setText("Tipo");
    }

    @Override
    public int getItemCount() {
        return visibleUsers.size();
    }


    @Override
    public Filter getFilter() {
        return resultsFilter;
    }

    Filter resultsFilter = new Filter(){
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults filteredValues = new FilterResults();
            List<UsuarioDatabase> elements = new ArrayList<>();
            elements.addAll(users);
            
            String pattern = constraint.toString().toLowerCase().trim();

            if(pattern != null){

                List<UsuarioDatabase> found = new ArrayList<>();

                for(UsuarioDatabase useri: elements){
                    if(useri.getNombreUsuario().toLowerCase().trim().contains(pattern)){
                        found.add(useri);
                    }
                }

                filteredValues.values = found;
                filteredValues.count = found.size();
            }
            else if(constraint == null){
                filteredValues.values = users;
                filteredValues.count = users.size();
            }

            return filteredValues;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            visibleUsers.clear();
            visibleUsers.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

}
