package com.example.spa_appv11_34.localAdapters;


import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.spa_appv11_34.Holders.holderGallery;
import com.example.spa_appv11_34.R;
import com.google.android.material.shape.ShapeAppearanceModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class imagesGalleryAdapter extends RecyclerView.Adapter<holderGallery> {

    List<String> galleryList = new ArrayList<>();//LISTA DE IMÁGENES DE LA GALERÍA
    List<ImageView> listImageView;//VISORES DE IMAGEN PARA MOSTRAR LAS IMÁGENES CARGADAS

    android.content.Context context;
    Button siguiente;

    int counter = 0;//CONTADOR PARA SABER CUANTAS IMÁGENES HAN SIDO CARGADAS
    List<String> contenedor = new ArrayList<>();//SE ENCARGA DE LLEVAR EL REGISTRO DE CUANTAS IMÁGENES HAN SIDO SELECCIONADAS

    public imagesGalleryAdapter(android.content.Context context,List<String> imagesList, List<ImageView> listImageView, Button siguiente) {
        this.context = context;
        this.galleryList = imagesList;
        this.listImageView = listImageView;
        this.siguiente = siguiente;
    }

    @NonNull
    @Override
    public holderGallery onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View vista = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.gallery_item, viewGroup, false);

        return new holderGallery(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull final holderGallery holderGallery, final int i) {

        //holderGallery.getImageView().setImageURI(Uri.parse(galleryList.get(i)));

        Uri current_uri;
        current_uri = Uri.parse(galleryList.get(i));
        //holder.getImageView().setImageURI(current_uri);

        Glide.with(context)
                .load(current_uri.getPath())
                .into(holderGallery.getImageView());


        //CUANDO SE PRESIONA UNA IMAGEN
        holderGallery.getImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //VERIFICAMOS SI LA IMAGEN ESTÁ SELECCIONADA
                boolean selected = holderGallery.getRadioButton().isChecked();

                if(!selected){//CUANDO ES SELECCIONADA
                    counter += 1;//CONTADOR PARA SABER CUANTAS IMÁGENES HAY CARGADAS
                    if(counter < 5) {
                        contenedor.add(galleryList.get(i));
                        holderGallery.getRadioButton().setChecked(!selected);//ACTIVAMOS EL ESTADO CONTRARIO
                    }
                }
                else{//CUANDO ES DESELECCIONADA
                    counter -= 1;
                    holderGallery.getRadioButton().setChecked(!selected);//ACTIVAMOS EL ESTADO CONTRARIO

                    String removedImage = galleryList.get(i);//SE OBTIENE LA IMAGEN DESELECCIONADA
                    //SE BUSCA LA IMAGEN DENTRO DE LOS VISORES Y SE BORRA
                    for(int j = 0; j < contenedor.size(); j++){
                        if( removedImage.equals(contenedor.get(j)) ){contenedor.remove(j);}
                    }

                    if(counter == -1){counter = 0;}
                }

                //CONDICIONALES PARA CADA CASO DEL CONTADOR
                if(counter == 0){//SI HAY CERO IMÁGENES CARGADAS
                    listImageView.get(0).setImageResource(R.drawable.ccp_down_arrow);
                    listImageView.get(1).setImageResource(R.drawable.ccp_down_arrow);
                    listImageView.get(2).setImageResource(R.drawable.ccp_down_arrow);
                    listImageView.get(3).setImageResource(R.drawable.ccp_down_arrow);
                    siguiente.setBackground(ContextCompat.getDrawable(context
                            , R.drawable.rounded_button_disabled));
                }
                else if(counter == 1){//SI HAY UNA IMAGEN CARGADA
                    listImageView.get(0).setImageURI(Uri.parse(contenedor.get(0)));
                    listImageView.get(1).setImageResource(R.drawable.ccp_down_arrow);
                    listImageView.get(2).setImageResource(R.drawable.ccp_down_arrow);
                    listImageView.get(3).setImageResource(R.drawable.ccp_down_arrow);
                    siguiente.setBackground(ContextCompat.getDrawable(context
                            , R.drawable.rounded_button));
                }
                else if(counter == 2){//SI HAY DOS IMÁGENES
                    listImageView.get(0).setImageURI(Uri.parse(contenedor.get(0)));
                    listImageView.get(3).setImageURI(Uri.parse(contenedor.get(1)));
                    listImageView.get(1).setImageResource(R.drawable.ccp_down_arrow);
                    listImageView.get(2).setImageResource(R.drawable.ccp_down_arrow);
                    siguiente.setBackground(ContextCompat.getDrawable(context
                            , R.drawable.rounded_button));
                }
                else if(counter == 3){
                    listImageView.get(0).setImageURI(Uri.parse(contenedor.get(0)));
                    listImageView.get(3).setImageURI(Uri.parse(contenedor.get(1)));
                    listImageView.get(1).setImageURI(Uri.parse(contenedor.get(2)));
                    listImageView.get(2).setImageResource(R.drawable.ccp_down_arrow);
                    siguiente.setBackground(ContextCompat.getDrawable(context
                            , R.drawable.rounded_button));
                }
                else if(counter == 4){
                    listImageView.get(0).setImageURI(Uri.parse(contenedor.get(0)));
                    listImageView.get(3).setImageURI(Uri.parse(contenedor.get(1)));
                    listImageView.get(1).setImageURI(Uri.parse(contenedor.get(2)));
                    listImageView.get(2).setImageURI(Uri.parse(contenedor.get(3)));
                    siguiente.setBackground(ContextCompat.getDrawable(context
                            , R.drawable.rounded_button));
                }

                if(counter == 5){counter = 4;}//SI EL CONTADOR SE EXCEDE LO LIMITAMOS

            }
        });

    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    public List<String> getContenedor() {
        return contenedor;
    }

    public void setContenedor(List<String> contenedor) {
        this.contenedor = contenedor;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}

