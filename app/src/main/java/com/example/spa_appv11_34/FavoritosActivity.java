package com.example.spa_appv11_34;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.spa_appv11_34.Holders.miniPostHolder;
import com.example.spa_appv11_34.Interaction_Classes.UserPostDatabase;
import com.example.spa_appv11_34.Interaction_Classes.UsuarioDatabase;
import com.example.spa_appv11_34.Interaction_Classes.dateObject;
import com.example.spa_appv11_34.References.UserReferences;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FavoritosActivity extends AppCompatActivity {

    private RecyclerView rvSearch;
    private FirebaseRecyclerAdapter adapter;
    private long likes;

    private UserReferences userReferences = UserReferences.getInstance();
    private UsuarioDatabase usuarioDatabase = new UsuarioDatabase();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Intent intent = new Intent(FavoritosActivity.this, home_spaApp.class);

            switch (item.getItemId()) {
                case R.id.searchItem:
                    intent.putExtra("button","1");
                    startActivity(intent);
                    return true;

                case R.id.homeItem:
                    intent.putExtra("button","2");
                    startActivity(intent);
                    return true;

                case R.id.notificationItem:
                    intent.putExtra("button","3");
                    startActivity(intent);
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation4);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        rvSearch = findViewById(R.id.rvSearchPostFavs);
        List<String> userKeys = new ArrayList<>();

        Query query = userReferences.getMyFavs().limitToLast(4);

        FirebaseRecyclerOptions<UserPostDatabase> options =
                new FirebaseRecyclerOptions.Builder<UserPostDatabase>()
                        .setQuery(query, UserPostDatabase.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<UserPostDatabase, miniPostHolder>(options) {

            @NonNull
            @Override
            public miniPostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.mini_post_item, parent, false);

                return new miniPostHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final miniPostHolder holder, int i, @NonNull final UserPostDatabase model) {

                holder.getUserNamePost().setText(model.getUsuario());

                String key_post = model.getUid_post();
                String userKey = key_post.substring(key_post.lastIndexOf("_")+1);

                //Función para extraer foto y nombre del creador del post
                putPersonalData(userKey,holder.getFotoUser(),holder.getUserNamePost());

                userReferences.postLikesCounter(userKey, key_post, new UserReferences.IDcountLikes() {
                    @Override
                    public void likesCounter(long c, List<String> Keys) {
                        likes = c;
                        holder.getLikesPost().setText(String.valueOf(likes));
                    }
                });

                List<String> imagesList = new ArrayList<>();

                imagesList.add(model.getURL_Foto1());
                imagesList.add(model.getURL_Foto2());
                imagesList.add(model.getURL_Foto3());
                imagesList.add(model.getURL_Foto4());

                for(int j = 0; j < imagesList.size(); j++) {
                    if (!imagesList.get(j).equals("NoPhoto")) {
                        TextSliderView textSliderView = new TextSliderView(FavoritosActivity.this);
                        textSliderView
                                .image(imagesList.get(j));

                        if(j == 0){//El texto solo se añade en la primera imagen
                            textSliderView.description(model.getTexto());
                        }


                        textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                            @Override
                            public void onSliderClick(BaseSliderView slider) {

                                String[] ActionChoice = getResources().getStringArray(R.array.PostActionFavs);
                                final int SelectedAction = 0;

                                AlertDialog.Builder box = new AlertDialog.Builder(FavoritosActivity.this);
                                box.setTitle(R.string.queQuieres);
                                box.setItems(ActionChoice, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        String userKey = model.getUid_post();//uid del creador del post
                                        userKey = userKey.substring(userKey.lastIndexOf("_")+1);

                                        if(which == 0) {
                                            //Visitar post
                                            Intent intencion = new Intent(FavoritosActivity.this, postViewerActivity.class);
                                            intencion.putExtra("img1",model.getURL_Foto1());
                                            intencion.putExtra("img2",model.getURL_Foto2());
                                            intencion.putExtra("img3",model.getURL_Foto3());
                                            intencion.putExtra("img4",model.getURL_Foto4());
                                            intencion.putExtra("texto",model.getTexto());
                                            intencion.putExtra("usuario",usuarioDatabase.getNombreUsuario());
                                            intencion.putExtra("foto",usuarioDatabase.getURL_Foto());
                                            startActivity(intencion);
                                        }
                                        else if(which == 1) {
                                            //Visitar perfil
                                            Intent intencion = new Intent(FavoritosActivity.this, userPostsActivity.class);

                                            intencion.putExtra("clave",userKey);
                                            intencion.putExtra("nombre",usuarioDatabase.getNombre());
                                            intencion.putExtra("apellidos",usuarioDatabase.getApellidos());
                                            intencion.putExtra("usuario",usuarioDatabase.getNombreUsuario());
                                            intencion.putExtra("historia",usuarioDatabase.getHistoria());
                                            intencion.putExtra("foto",usuarioDatabase.getURL_Foto());
                                            startActivity(intencion);
                                        }

                                    }
                                });
                                box.show();

                            }
                        });


                        holder.getImagePost().addSlider(textSliderView);
                    }
                }
                holder.getImagePost().stopAutoCycle();

            }
        };

        GridLayoutManager l = new GridLayoutManager(FavoritosActivity.this, 2);
        rvSearch.setLayoutManager(l);
        rvSearch.setAdapter(adapter);

        adapter.startListening();

    }

    public void putPersonalData(String userKey, final CircleImageView foto, final TextView nombre){

        final TaskCompletionSource<DataSnapshot> dbSource = new TaskCompletionSource<>();
        userReferences.getAllUsers().child(userKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dbSource.setResult(dataSnapshot);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                dbSource.setException(databaseError.toException());
            }
        });

        final Task<DataSnapshot> dbTask = dbSource.getTask();

        Task<Void> allTask;
        allTask = Tasks.whenAll(dbTask);

        allTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                DataSnapshot data = dbTask.getResult();
                usuarioDatabase = data.getValue(UsuarioDatabase.class);
                Glide.with(FavoritosActivity.this).load(usuarioDatabase.getURL_Foto()).into(foto);
                nombre.setText(usuarioDatabase.getNombreUsuario());
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {

    }

}
