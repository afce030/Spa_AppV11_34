package com.example.spa_appv11_34;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;


public class AjustesActivity extends AppCompatActivity {

    private TextView logout;

    private Switch spanish;
    private Switch english;

    private CompoundButton.OnCheckedChangeListener listerner1;
    private CompoundButton.OnCheckedChangeListener listerner2;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Intent intent = new Intent(AjustesActivity.this, home_spaApp.class);

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
        setContentView(R.layout.activity_ajustes);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation5);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        spanish = findViewById(R.id.swSPanish);
        english = findViewById(R.id.swEnglish);

        Intent intent = getIntent();
        String tema = intent.getStringExtra("tema");
        String idioma = intent.getStringExtra("idioma");
        boolean notificaciones = intent.getBooleanExtra("notificaciones",true);

        boolean español = true;
        if(!idioma.equals("Español")){español = false;}

        spanish.setChecked(español);
        english.setChecked(!español);

        CompoundButton.OnCheckedChangeListener listerner1 = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                english.setOnCheckedChangeListener(null);
                english.setChecked(!english.isChecked());
                english.setOnCheckedChangeListener(listerner2);

                if(isChecked) {
                    Locale locale = new Locale("es");
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                }
                else {
                    Locale locale = new Locale("en");
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                }
            }
        };

        CompoundButton.OnCheckedChangeListener listerner2 = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                spanish.setOnCheckedChangeListener(null);
                spanish.setChecked(!spanish.isChecked());
                spanish.setOnCheckedChangeListener(listerner1);

                if(isChecked) {
                    Locale locale = new Locale("en");
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                }
                else {
                    Locale locale = new Locale("es");
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                }

            }
        };

        spanish.setOnCheckedChangeListener(listerner1);
        english.setOnCheckedChangeListener(listerner2);

        logout = findViewById(R.id.btnLogout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AjustesActivity.this,Login.class));
                //Add firebase close statement
            }
        });

    }
}
