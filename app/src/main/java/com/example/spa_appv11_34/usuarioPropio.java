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
import com.example.spa_appv11_34.Clases_Interaccion.UsuarioDatabase;
import com.example.spa_appv11_34.Clases_Interaccion.UsuarioPreferences;
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

public class usuarioPropio extends AppCompatActivity {

    private TextView username;
    private TextView fullname;
    private TextView history;
    private CircleImageView fotoPerfil;

    private TextView followersCounter;
    private TextView likesUserCounter;

    private Button post;

    private FirebaseRecyclerAdapter adapter;
    private RecyclerView rvSearch;

    private UsuarioReferencias usuarioReferencias = UsuarioReferencias.getInstance();
    private UsuarioDatabase usuarioDatabase = new UsuarioDatabase();
    private UsuarioPreferences usuarioPreferences = new UsuarioPreferences();

    private CentroDatabase centroDatabase = new CentroDatabase();
    private CentroReferencias centroReferencias = CentroReferencias.getInstance();

    private String llaveUsuario;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Intent intent = new Intent(usuarioPropio.this, home_spaApp.class);

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
        setContentView(R.layout.activity_usuario_propio);

        username = findViewById(R.id.tvUsernameUsuarioPropio);
        fullname = findViewById(R.id.tvFullnameUsuarioPropio);
        history = findViewById(R.id.tvHistoryUsuarioPropio);
        fotoPerfil = findViewById(R.id.civFotoPerfilUsuarioPropio);

        followersCounter = findViewById(R.id.tvFollowersCounter);
        likesUserCounter = findViewById(R.id.tvLikesCounter);

        rvSearch = findViewById(R.id.rvSearchMyPost);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bnvUsuarioPropio);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(usuarioPropio.this, createPost0.class));
            }
        });

        LinearLayout back_layer = findViewById(R.id.returningLayer);

        ImageButton alien_button = findViewById(R.id.nav_pan);
        alien_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinearLayout menuLayer = findViewById(R.id.menuLayer);
                menuLayer.setVisibility(View.VISIBLE);

            }
        });

        back_layer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinearLayout menuLayer = findViewById(R.id.menuLayer);
                menuLayer.setVisibility(View.GONE);

            }
        });

        LinearLayout perfil = findViewById(R.id.PersonalizarPerfil);

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intencion = new Intent(usuarioPropio.this,PersonalizarPerfil.class);
                intencion.putExtra("img", "NoPhoto");
                startActivity(intencion);
            }
        });

        LinearLayout pagos = findViewById(R.id.ConfigurationPagos);

        pagos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final TaskCompletionSource<UsuarioPreferences> task = new TaskCompletionSource<>();

                usuarioReferencias.getMyPreferences().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        task.setResult(dataSnapshot.getValue(UsuarioPreferences.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Task<UsuarioPreferences> Task = task.getTask();

                Task.addOnSuccessListener(new OnSuccessListener<UsuarioPreferences>() {
                    @Override
                    public void onSuccess(UsuarioPreferences user) {
                        usuarioPreferences.setTheme(user.getTheme());
                        usuarioPreferences.setNotificaciones(user.getNotificaciones());
                        usuarioPreferences.setIdioma(user.getIdioma());
                        usuarioPreferences.setCreditCard(user.getCreditCard());
                        usuarioPreferences.setPayPal(user.getPayPal());

                        Intent intencion = new Intent(usuarioPropio.this,PagosActivity.class);
                        intencion.putExtra("tajeta",usuarioPreferences.getCreditCard());
                        intencion.putExtra("paypal",usuarioPreferences.getPayPal());
                        startActivity(intencion);
                    }
                });
            }
        });

        LinearLayout favs =  findViewById(R.id.VerFavs);

        favs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(usuarioPropio.this,FavoritosActivity.class));
            }
        });

        LinearLayout ajustes = findViewById(R.id.VerAjustes);

        ajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final TaskCompletionSource<UsuarioPreferences> task = new TaskCompletionSource<>();

                usuarioReferencias.getMyPreferences().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        task.setResult(dataSnapshot.getValue(UsuarioPreferences.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Task<UsuarioPreferences> Task = task.getTask();

                Task.addOnSuccessListener(new OnSuccessListener<UsuarioPreferences>() {
                    @Override
                    public void onSuccess(UsuarioPreferences user) {
                        usuarioPreferences.setTheme(user.getTheme());
                        usuarioPreferences.setNotificaciones(user.getNotificaciones());
                        usuarioPreferences.setIdioma(user.getIdioma());
                        usuarioPreferences.setCreditCard(user.getCreditCard());
                        usuarioPreferences.setPayPal(user.getPayPal());

                        Intent intencion = new Intent(usuarioPropio.this,AjustesActivity.class);
                        intencion.putExtra("tema",usuarioPreferences.getTheme());
                        intencion.putExtra("idioma",usuarioPreferences.getIdioma());
                        intencion.putExtra("notificaciones",usuarioPreferences.getNotificaciones());
                        startActivity(intencion);
                    }
                });

            }
        });

        LinearLayout compartir = findViewById(R.id.ConfiguracionCompartir);

        compartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(usuarioPropio.this,CompartirActivity.class));
            }
        });

        LinearLayout soporte = findViewById(R.id.VerSoporte);

        soporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(usuarioPropio.this,SoporteActivity.class));
            }
        });


        List<String> userKeys = new ArrayList<>();

        Query query = usuarioReferencias.getMyFavs().limitToLast(4);

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

                                AlertDialog.Builder box = new AlertDialog.Builder(usuarioPropio.this);
                                box.setTitle(R.string.queQuieres);
                                box.setPositiveButton(R.string.verPublicacion, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //Visitar post
                                        Intent intencion = new Intent(usuarioPropio.this, postViewerActivity.class);
                                        intencion.putExtra("img1", model.getURL_Foto1());
                                        intencion.putExtra("img2", model.getURL_Foto2());
                                        intencion.putExtra("img3", model.getURL_Foto3());
                                        intencion.putExtra("img4", model.getURL_Foto4());
                                        intencion.putExtra("texto", model.getTexto());
                                        intencion.putExtra("centro", centroDatabase.getNombreCentro());
                                        intencion.putExtra("foto", centroDatabase.getURL_Foto());
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
        centroReferencias.getAllCenters().child(userKey).addListenerForSingleValueEvent(new ValueEventListener() {
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
                centroDatabase = data.getValue(CentroDatabase.class);
                Glide.with(usuarioPropio.this).load(centroDatabase.getURL_Foto()).into(foto);
                nombre.setText(centroDatabase.getNombreCentro());
            }
        });

    }

    @Override
    public void onBackPressed() {

    }

    public void asignarControles(){

        Intent intent = getIntent();
        username.setText(intent.getStringExtra("nombreUsuario"));
        fullname.setText(intent.getStringExtra("nombres") + " " + intent.getStringExtra("apellidos"));
        history.setText(intent.getStringExtra("historia"));
        Glide.with(usuarioPropio.this).load(intent.getStringExtra("foto")).into(fotoPerfil);

        llaveUsuario = intent.getStringExtra("llaveUsuario");

    }

    @Override
    protected void onStart() {
        super.onStart();
        asignarControles();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}
