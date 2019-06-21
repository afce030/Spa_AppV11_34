package com.example.spa_appv11_34;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.spa_appv11_34.Fragmentos.HomeFragment;
import com.example.spa_appv11_34.Fragmentos.NotificationFragment;
import com.example.spa_appv11_34.Fragmentos.SearchFragment;
import com.example.spa_appv11_34.Clases_Interaccion.UsuarioDatabase;
import com.example.spa_appv11_34.References.UsuarioReferences;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class home_spaApp extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener,
        NotificationFragment.OnFragmentInteractionListener, SearchFragment.OnFragmentInteractionListener{

    private TextView nombrePerfil;
    private CircleImageView imagenPerfil;

    private FirebaseAuth firebaseAuth;
    private String current_user;
    private UsuarioDatabase usuarioDatabase;
    private UsuarioReferences usuarioReferences = UsuarioReferences.getInstance();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selected_fragment = null;

            switch (item.getItemId()) {
                case R.id.searchItem:
                    selected_fragment = new SearchFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer,selected_fragment).commit();
                    return true;

                case R.id.homeItem:
                    selected_fragment = new HomeFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer,selected_fragment).commit();
                    return true;

                case R.id.notificationItem:
                    selected_fragment = new NotificationFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer,selected_fragment).commit();
                    return true;

            }
            return false;
        }
    };

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_spa_app);

        nombrePerfil = (TextView) findViewById(R.id.tvnameUserHome);
        imagenPerfil = (CircleImageView) findViewById(R.id.imgPerfilHome);

        imagenPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intencion = new Intent(home_spaApp.this, MyPostActivity.class);

                intencion.putExtra("nombres",usuarioDatabase.getNombre());
                intencion.putExtra("apellidos", usuarioDatabase.getApellidos());
                intencion.putExtra("username", usuarioDatabase.getNombreUsuario());
                intencion.putExtra("historia", usuarioDatabase.getHistoria());
                intencion.putExtra("foto", usuarioDatabase.getURL_Foto());

                startActivity(intencion);
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Intent intencion = getIntent();
        String button_selected = intencion.getStringExtra("button");

        //Toast.makeText(this, button_selected, Toast.LENGTH_SHORT).show();

        if(button_selected.equals("1")) {
            navigation.setSelectedItemId(R.id.searchItem);
        }
        else if(button_selected.equals("2")){
            navigation.setSelectedItemId(R.id.homeItem);
        }
        else if(button_selected.equals("3")){
            navigation.setSelectedItemId(R.id.notificationItem);
        }

    }

    @Override
    public void onBackPressed() {

    }

    public void asignarControles(){
        nombrePerfil.setText(usuarioDatabase.getNombreUsuario());
        Glide.with(home_spaApp.this).load(usuarioDatabase.getURL_Foto()).into(imagenPerfil);
    }

    @Override
    protected void onStart() {
        super.onStart();

        current_user = usuarioReferences.getUser();
        usuarioReferences.getAllUsers().child(current_user).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuarioDatabase = dataSnapshot.getValue(UsuarioDatabase.class);
                asignarControles();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
