package com.example.spa_appv11_34;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CompartirActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Intent intent = new Intent(CompartirActivity.this, home_spaApp.class);

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
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compartir);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bnvCompartirActivity);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }
}
