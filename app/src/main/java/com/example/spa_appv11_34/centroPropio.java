package com.example.spa_appv11_34;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.spa_appv11_34.Clases_Interaccion.CentroDatabase;
import com.example.spa_appv11_34.Clases_Interaccion.CentroPostDatabase;
import com.example.spa_appv11_34.Clases_Interaccion.CentroPreferencias;
import com.example.spa_appv11_34.Clases_Interaccion.UsuarioDatabase;
import com.example.spa_appv11_34.Clases_Interaccion.UsuarioPreferencias;
import com.example.spa_appv11_34.Holders.PostHolder;
import com.example.spa_appv11_34.Referencias.CentroReferencias;
import com.example.spa_appv11_34.Referencias.UsuarioReferencias;
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

public class centroPropio extends AppCompatActivity {

    private TextView centername;
    private TextView fullname;
    private TextView history;
    private CircleImageView fotoPerfil;

    private TextView followersCounter;
    private TextView likesUserCounter;

    private Button post;
    private Button myCenter;

    private FirebaseRecyclerAdapter adapter;
    private RecyclerView rvSearch;

    private UsuarioReferencias usuarioReferencias = UsuarioReferencias.getInstance();
    private UsuarioDatabase usuarioDatabase = new UsuarioDatabase();
    private UsuarioPreferencias usuarioPreferencias = new UsuarioPreferencias();

    private CentroReferencias centroReferencias = CentroReferencias.getInstance();
    private CentroDatabase centroDatabase = new CentroDatabase();
    private CentroPreferencias centroPreferencias = new CentroPreferencias();

    private long likes = 0;
    private long totalLikes = 0;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Intent intent = new Intent(centroPropio.this, home_spaApp.class);

            switch (item.getItemId()) {
                case R.id.searchItem:
                    adapter.stopListening();
                    intent.putExtra("button","1");
                    startActivity(intent);
                    finish();
                    return true;

                case R.id.homeItem:
                    adapter.stopListening();
                    intent.putExtra("button","2");
                    startActivity(intent);
                    finish();
                    return true;

                case R.id.notificationItem:
                    adapter.stopListening();
                    intent.putExtra("button","3");
                    startActivity(intent);
                    finish();
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centro_propio);

        centername = findViewById(R.id.tvCenternameCentroPropio);
        fullname = findViewById(R.id.tvFullnameCentroPropio);
        history = findViewById(R.id.tvHistoryCentroPropio);
        fotoPerfil = findViewById(R.id.civFotoPerfilCentroPropio);
        post = findViewById(R.id.btnPostCentroPropio);
        myCenter = findViewById(R.id.btnMiCentro);
        followersCounter = findViewById(R.id.tvFollowersCounterCentroPropio);
        likesUserCounter = findViewById(R.id.tvLikesCounterCentroPropio);

        rvSearch = findViewById(R.id.rvSearchCentroPropio);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bnvCentroPropio);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Clave del usuario que quiere seguir a otro
        final String current_user = usuarioReferencias.getUser();
/*
        usuarioReferencias.followersCounter(current_user,new UsuarioReferencias.IDcountFollowers() {
            @Override
            public void followersCounter(long c, List<String> followerKeys) {
                followersCounter.setText(String.valueOf(c));
            }
        });
*/
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(centroPropio.this, createPost0.class));
            }
        });

        LinearLayout back_layer = findViewById(R.id.returningLayerCentroPropio);

        ImageButton alien_button = findViewById(R.id.nav_panCentroPropio);
        alien_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinearLayout menuLayer = findViewById(R.id.menuLayerCentroPropio);
                menuLayer.setVisibility(View.VISIBLE);

            }
        });

        back_layer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinearLayout menuLayer = findViewById(R.id.menuLayerCentroPropio);
                menuLayer.setVisibility(View.GONE);

            }
        });

        LinearLayout perfil = findViewById(R.id.PersonalizarPerfilCentroPropio);

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intencion = new Intent(centroPropio.this,PersonalizarPerfil.class);
                intencion.putExtra("img", "NoPhoto");
                startActivity(intencion);
            }
        });

        LinearLayout pagos = findViewById(R.id.ConfigurationPagosCentroPropio);

        pagos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final TaskCompletionSource<CentroPreferencias> task = new TaskCompletionSource<>();

                centroReferencias.getMyPreferences().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        task.setResult(dataSnapshot.getValue(CentroPreferencias.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Task<CentroPreferencias> Task = task.getTask();

                Task.addOnSuccessListener(new OnSuccessListener<CentroPreferencias>() {
                    @Override
                    public void onSuccess(CentroPreferencias center) {

                        centroPreferencias.setTheme(center.getTheme());
                        centroPreferencias.setNotificaciones(center.getNotificaciones());
                        centroPreferencias.setIdioma(center.getIdioma());
                        centroPreferencias.setRecibirCreditCard(center.getRecibirCreditCard());
                        centroPreferencias.setRecibirPayPal(center.getRecibirPayPal());

                        Intent intencion = new Intent(centroPropio.this,PagosActivity.class);
                        intencion.putExtra("tajeta", centroPreferencias.getRecibirCreditCard());
                        intencion.putExtra("paypal", centroPreferencias.getRecibirPayPal());
                        startActivity(intencion);

                    }

                });
            }
        });

        LinearLayout favs =  findViewById(R.id.VerFavsCentroPropio);

        favs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(centroPropio.this,FavoritosActivity.class));
            }
        });

        LinearLayout ajustes = findViewById(R.id.VerAjustesCentroPropio);

        ajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final TaskCompletionSource<CentroPreferencias> task = new TaskCompletionSource<>();

                centroReferencias.getMyPreferences().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        task.setResult(dataSnapshot.getValue(CentroPreferencias.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Task<CentroPreferencias> Task = task.getTask();

                Task.addOnSuccessListener(new OnSuccessListener<CentroPreferencias>() {
                    @Override
                    public void onSuccess(CentroPreferencias center) {
                        centroPreferencias.setTheme(center.getTheme());
                        centroPreferencias.setNotificaciones(center.getNotificaciones());
                        centroPreferencias.setIdioma(center.getIdioma());
                        centroPreferencias.setRecibirCreditCard(center.getRecibirCreditCard());
                        centroPreferencias.setRecibirPayPal(center.getRecibirPayPal());

                        Intent intencion = new Intent(centroPropio.this,AjustesActivity.class);
                        intencion.putExtra("tema", centroPreferencias.getTheme());
                        intencion.putExtra("idioma", centroPreferencias.getIdioma());
                        intencion.putExtra("notificaciones", centroPreferencias.getNotificaciones());
                        startActivity(intencion);
                    }
                });

            }
        });

        LinearLayout compartir = findViewById(R.id.ConfiguracionCompartirCentroPropio);

        compartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(centroPropio.this,CompartirActivity.class));
            }
        });

        LinearLayout soporte = findViewById(R.id.VerSoporteCentroPropio);

        soporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(centroPropio.this,SoporteActivity.class));
            }
        });


        List<String> userKeys = new ArrayList<>();

        Query query = centroReferencias.getPostList().limitToLast(2);

        FirebaseRecyclerOptions<CentroPostDatabase> options =
                new FirebaseRecyclerOptions.Builder<CentroPostDatabase>()
                        .setQuery(query, CentroPostDatabase.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<CentroPostDatabase, PostHolder>(options) {

            @NonNull
            @Override
            public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.post_item, parent, false);

                return new PostHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final PostHolder holder, int i, @NonNull final CentroPostDatabase model) {

                holder.getUserNamePost().setText(model.getUsuario());

                String key_post = model.getUid_post();
                String userKey = key_post.substring(key_post.lastIndexOf("_")+1);
                holder.getPostContent().setText("");

                //Función para extraer foto y nombre del creador del post
                putPersonalData(userKey,holder.getFotoUser(),holder.getUserNamePost());

                usuarioReferencias.postLikesCounter(userKey, key_post, new UsuarioReferencias.IDcountLikes() {
                    @Override
                    public void likesCounter(long c, List<String> Keys) {
                        likes = c;
                        holder.getLikesPost().setText(String.valueOf(likes));

                        totalLikes = totalLikes+likes;
                        likesUserCounter.setText(String.valueOf(totalLikes));
                    }
                });

                List<String> imagesList = new ArrayList<>();

                imagesList.add(model.getURL_Foto1());
                imagesList.add(model.getURL_Foto2());
                imagesList.add(model.getURL_Foto3());
                imagesList.add(model.getURL_Foto4());

                for (int j = 0; j < imagesList.size(); j++) {
                    if (!imagesList.get(j).equals("NoPhoto")) {

                        TextSliderView textSliderView = new TextSliderView(getApplicationContext());
                        textSliderView
                                .description(model.getTexto())
                                .image(imagesList.get(j));


                        textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                            @Override
                            public void onSliderClick(BaseSliderView slider) {

                                AlertDialog.Builder box = new AlertDialog.Builder(centroPropio.this);
                                box.setTitle(R.string.queQuieres);
                                box.setPositiveButton(R.string.verPublicacion, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //Visitar post
                                        Intent intencion = new Intent(centroPropio.this, postViewerActivity.class);
                                        intencion.putExtra("img1", model.getURL_Foto1());
                                        intencion.putExtra("img2", model.getURL_Foto2());
                                        intencion.putExtra("img3", model.getURL_Foto3());
                                        intencion.putExtra("img4", model.getURL_Foto4());
                                        intencion.putExtra("texto", model.getTexto());
                                        intencion.putExtra("usuario", usuarioDatabase.getNombreUsuario());
                                        intencion.putExtra("foto", usuarioDatabase.getURL_Foto());
                                        startActivity(intencion);
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

        LinearLayoutManager l = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        rvSearch.setLayoutManager(l);
        rvSearch.setAdapter(adapter);

    }

    public void putPersonalData(String userKey, final CircleImageView foto, final TextView nombre){

        final TaskCompletionSource<DataSnapshot> dbSource = new TaskCompletionSource<>();
        usuarioReferencias.getAllCenters().child(userKey).addListenerForSingleValueEvent(new ValueEventListener() {
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
                Glide.with(centroPropio.this).load(usuarioDatabase.getURL_Foto()).into(foto);
                nombre.setText(usuarioDatabase.getNombreUsuario());
            }
        });

    }

    @Override
    public void onBackPressed() {

    }

    public void asignarControles(){

        Intent intent = getIntent();
        centername.setText(intent.getStringExtra("centername"));
        fullname.setText(intent.getStringExtra("centername"));
        history.setText(intent.getStringExtra("historia"));
        Glide.with(centroPropio.this).load(intent.getStringExtra("foto")).into(fotoPerfil);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        asignarControles();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
