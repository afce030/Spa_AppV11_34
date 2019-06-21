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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.spa_appv11_34.Holders.PostHolder;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class userPostsActivity extends AppCompatActivity {

    private TextView username;
    private TextView fullname;
    private TextView history;
    private CircleImageView fotoPerfil;

    private TextView followersCounter;
    private TextView likesUserCounter;

    private Button seguir;

    private FirebaseRecyclerAdapter adapter;
    private RecyclerView rvSearch;

    private UserReferences userReferences = UserReferences.getInstance();
    private UsuarioDatabase usuarioDatabase = new UsuarioDatabase();

    private long likes = 0;
    private long totalLikes = 0;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Intent intent = new Intent(userPostsActivity.this, home_spaApp.class);

            switch (item.getItemId()) {
                case R.id.searchItem:
                    //adapter.stopListening();
                    intent.putExtra("button","1");
                    startActivity(intent);
                    finish();
                    return true;

                case R.id.homeItem:
                    //adapter.stopListening();
                    intent.putExtra("button","2");
                    startActivity(intent);
                    finish();
                    return true;

                case R.id.notificationItem:
                    //adapter.stopListening();
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
        setContentView(R.layout.activity_user_posts);

        username = findViewById(R.id.tvUsernameUserViewerAct);
        fullname = findViewById(R.id.tvFullnameUserViewerAct);
        history = findViewById(R.id.tvHistoryUserViewerAct);
        fotoPerfil = findViewById(R.id.civFotoPerfilUserViewerAct);
        seguir = findViewById(R.id.btnFollowUserViewerAct);

        followersCounter = findViewById(R.id.tvFollowersCounterUserViewerAct);
        likesUserCounter = findViewById(R.id.tvLikesCounterUserViewerAct);

        rvSearch = findViewById(R.id.rvSearchUserViewerAct);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation8);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Intent intent = getIntent();
        final String userKey_obt = intent.getStringExtra("clave");
        String name_obt = intent.getStringExtra("nombre");
        String lastName_obt = intent.getStringExtra("apellidos");
        final String username_obt = intent.getStringExtra("usuario");
        String history_obt = intent.getStringExtra("historia");
        final String fotoURL_obt = intent.getStringExtra("foto");

        //Clave del usuario que quiere seguir a otro
        final String current_user = userReferences.getUser();

        userReferences.followersCounter(userKey_obt, new UserReferences.IDcountFollowers() {
            @Override
            public void followersCounter(long c, List<String> followerKeys) {
                followersCounter.setText(String.valueOf(c));
                followersCounter.setText(String.valueOf(45));
            }
        });

        username.setText(username_obt);
        fullname.setText(name_obt + " " + lastName_obt);
        history.setText(history_obt);
        Glide.with(userPostsActivity.this).load(fotoURL_obt).into(fotoPerfil);

        seguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!userKey_obt.equals(current_user)) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(userPostsActivity.this);
                    builder.setCancelable(false);
                    builder.setView(R.layout.progress_bar);
                    final AlertDialog dialog = builder.create();

                    dialog.show();

                    final Collection<UploadTask> taskUpload = new ArrayList<>();

                    DatabaseReference ref1 = FirebaseDatabase.getInstance()
                            .getReference().child("Followers").child(userKey_obt).child(current_user);
                    DatabaseReference ref2 = FirebaseDatabase.getInstance().
                            getReference().child("Following").child(current_user).child(userKey_obt);

                    Object createdTimestamp = ServerValue.TIMESTAMP;
                    Task task1 = ref1.child("createdTimestamp").setValue(createdTimestamp);
                    Task task2 = ref2.child("createdTimestamp").setValue(createdTimestamp);

                    Task<Void> allTask;
                    allTask = Tasks.whenAll(task1, task2);

                    allTask.addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            dialog.dismiss();
                            Toast.makeText(userPostsActivity.this, R.string.ahoraSigues + username_obt, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        Query query = FirebaseDatabase.getInstance().getReference().child("MyPosts").child(userKey_obt).limitToLast(20);

        FirebaseRecyclerOptions<UserPostDatabase> options =
                new FirebaseRecyclerOptions.Builder<UserPostDatabase>()
                        .setQuery(query, UserPostDatabase.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<UserPostDatabase, PostHolder>(options) {

            @NonNull
            @Override
            public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.post_item, parent, false);
                return new PostHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final PostHolder holder, int i, @NonNull final UserPostDatabase model) {

                holder.getUserNamePost().setText(model.getUsuario());

                String key_post = model.getUid_post();
                String userKey = key_post.substring(key_post.lastIndexOf("_")+1);
                holder.getPostContent().setText("");

                //Función para extraer foto y nombre del creador del post
                putPersonalData(userKey,holder.getFotoUser(),holder.getUserNamePost());

                userReferences.postLikesCounter(userKey, key_post, new UserReferences.IDcountLikes() {
                    @Override
                    public void likesCounter(long c, List<String> Keys) {
                        likes = c;
                        holder.getLikesPost().setText(String.valueOf(likes));

                        totalLikes = totalLikes+likes;
                        likesUserCounter.setText(String.valueOf(totalLikes));
                        likesUserCounter.setText(String.valueOf(25));
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

                                String[] ActionChoice = getResources().getStringArray(R.array.PostActionUserAct);

                                AlertDialog.Builder box = new AlertDialog.Builder(userPostsActivity.this);
                                box.setTitle(R.string.queQuieres);
                                box.setItems(ActionChoice, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(which == 0) {
                                            //Visitar post
                                            Intent intencion = new Intent(userPostsActivity.this, postViewerActivity.class);
                                            intencion.putExtra("img1", model.getURL_Foto1());
                                            intencion.putExtra("img2", model.getURL_Foto2());
                                            intencion.putExtra("img3", model.getURL_Foto3());
                                            intencion.putExtra("img4", model.getURL_Foto4());
                                            intencion.putExtra("texto", model.getTexto());
                                            intencion.putExtra("usuario", username_obt);
                                            intencion.putExtra("foto", fotoURL_obt);
                                            startActivity(intencion);
                                        }
                                        else if(which == 1){
                                            //Me gusta este post
                                            //Escribir en mylikes y en myfavs (con fecha)

                                            dateObject date = new dateObject();

                                            Task task1 = userReferences.getLikesAnyPost()
                                                    .child(userKey_obt)
                                                    .child(model.getUid_post())
                                                    .child(userReferences.getUser())
                                                    .setValue(date);

                                            Task task2 = userReferences.getMyFavs().child(model.getUid_post()).setValue(model);

                                            Task<Void> upload = Tasks.whenAll(task1,task2);

                                            upload.addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(userPostsActivity.this,
                                                            R.string.ahoraTeGusta, Toast.LENGTH_SHORT).show();
                                                }
                                            });

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

        LinearLayoutManager l = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);
        rvSearch.setLayoutManager(l);
        rvSearch.setAdapter(adapter);

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
                Glide.with(userPostsActivity.this).load(usuarioDatabase.getURL_Foto()).into(foto);
                nombre.setText(usuarioDatabase.getNombreUsuario());
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
