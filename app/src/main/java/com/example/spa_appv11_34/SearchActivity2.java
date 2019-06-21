package com.example.spa_appv11_34;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.spa_appv11_34.Fragmentos.searchPost;
import com.example.spa_appv11_34.Fragmentos.searchUsers;
import com.example.spa_appv11_34.Interaction_Classes.UsuarioDatabase;
import com.example.spa_appv11_34.localAdapters.usersSearchAdapter;
import com.example.spa_appv11_34.localAdapters.viewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity2 extends AppCompatActivity implements searchPost.OnFragmentInteractionListener,
        searchUsers.OnFragmentInteractionListener {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private viewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;

    private RecyclerView rvSearch;
    private usersSearchAdapter adapter;
    private SearchView searchView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Intent intent = new Intent(SearchActivity2.this, home_spaApp.class);

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
        setContentView(R.layout.activity_search2);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation9);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        rvSearch = findViewById(R.id.rvSearchUsers);
        searchView = findViewById(R.id.svSearchBarActivityResult2);

        //toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tablayout);

        viewPagerAdapter = new viewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        List<UsuarioDatabase> users = new ArrayList<>();

        UsuarioDatabase usuarioDatabase = new UsuarioDatabase();
        usuarioDatabase.setNombreUsuario("julian");
        users.add(usuarioDatabase);

        UsuarioDatabase usuarioDatabase1 = new UsuarioDatabase();
        usuarioDatabase1.setNombreUsuario("pepe");
        users.add(usuarioDatabase1);

        UsuarioDatabase usuarioDatabase2 = new UsuarioDatabase();
        usuarioDatabase2.setNombreUsuario("andres");
        users.add(usuarioDatabase2);

        UsuarioDatabase usuarioDatabase3 = new UsuarioDatabase();
        usuarioDatabase3.setNombreUsuario("andres2500");
        users.add(usuarioDatabase3);

        UsuarioDatabase usuarioDatabase4 = new UsuarioDatabase();
        usuarioDatabase4.setNombreUsuario("Pepe2");
        users.add(usuarioDatabase4);

        adapter = new usersSearchAdapter(users,this);
        LinearLayoutManager l = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);

        rvSearch.setLayoutManager(l);
        rvSearch.setAdapter(adapter);
        rvSearch.getRecycledViewPool().setMaxRecycledViews(0,0);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
