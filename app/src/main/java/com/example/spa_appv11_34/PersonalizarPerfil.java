package com.example.spa_appv11_34;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.spa_appv11_34.Clases_Interaccion.UsuarioDatabase;
import com.example.spa_appv11_34.References.UserReferences;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.kbeanie.multipicker.api.ImagePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalizarPerfil extends AppCompatActivity{

    private UsuarioDatabase usuarioDatabase;

    private CircleImageView fotoPerfil;
    private TextView FullName;
    private TextView history;
    private EditText username;
    private EditText email;
    private EditText phone;
    private EditText fechaNacimiento;
    private EditText genero;
    private Button guardarCambios;
    String fecha;
    private ImagePicker imagePicker;
    private Uri uri;
    private boolean actualizar = false;//variable que indica si hay cambios por guardar

    private UserReferences userReferences = UserReferences.getInstance();

    private String current_user;
    private String image;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Intent intent = new Intent(PersonalizarPerfil.this, home_spaApp.class);

            switch (item.getItemId()) {
                case R.id.searchItem:
                    intent.putExtra("button","1");
                    startActivity(intent);
                    finish();
                    return true;

                case R.id.homeItem:
                    intent.putExtra("button","2");
                    startActivity(intent);
                    finish();
                    return true;

                case R.id.notificationItem:
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
        setContentView(R.layout.activity_personalizar_perfil);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation2);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fotoPerfil = findViewById(R.id.CambiarFotoPerfil);
        FullName = findViewById(R.id.ChangeNombre);
        history = findViewById(R.id.ChangeHistory);
        username = findViewById(R.id.etUserNamePersonalizar);
        email = findViewById(R.id.etEmailchange);
        phone = findViewById(R.id.ChangePhone);
        fechaNacimiento = findViewById(R.id.etFechaNacimiento);
        genero = findViewById(R.id.etGender);

        guardarCambios = findViewById(R.id.btnGuardarCambios);
        guardarCambios.setBackground(ContextCompat.getDrawable(PersonalizarPerfil.this, R.drawable.rounded_button_disabled));

        Intent intent = getIntent();
        image = intent.getStringExtra("img");

        if(!image.equals("NoPhoto")) {
            uri = Uri.parse(image);
            fotoPerfil.setImageURI(uri);
            actualizar = true;
        }
        else {
            //Glide.with(PersonalizarPerfil.this).load(usuarioDatabase.getURL_Foto()).into(fotoPerfil);
        }

        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intencion = new Intent(PersonalizarPerfil.this, SubirFotoActivity.class);
                intencion.putExtra("img", image);
                startActivity(intencion);
            }
        });

        FullName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                final EditText type_name = new EditText(PersonalizarPerfil.this);

                AlertDialog.Builder name = new AlertDialog.Builder(PersonalizarPerfil.this);
                name.setTitle("Escribe tu nombre completo");
                name.setView(type_name);
                name.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FullName.setText(type_name.getText().toString());
                    }
                });
                name.setNegativeButton(R.string.cancelar, null);

                name.show();
*/
                Toast.makeText(PersonalizarPerfil.this, R.string.noCambiarNombre, Toast.LENGTH_SHORT).show();
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText type_name = new EditText(PersonalizarPerfil.this);
                final String anterior = history.getText().toString();
                type_name.setText(anterior);

                AlertDialog.Builder name = new AlertDialog.Builder(PersonalizarPerfil.this);
                name.setTitle(R.string.cambiarHistoria);
                name.setView(type_name);
                name.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String historia = type_name.getText().toString();
                        if(historia.length() <= 144) {
                            history.setText(historia);
                            if(!anterior.equals(history.getText().toString())) {
                                guardarCambios.setBackground(ContextCompat.getDrawable(PersonalizarPerfil.this, R.drawable.rounded_button));
                                actualizar = true;
                            }
                        }
                        else{
                            Toast.makeText(PersonalizarPerfil.this, R.string.historiaMayorA144, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                name.setNegativeButton(R.string.cancelar, null);

                name.show();

            }
        });

        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText type_name = new EditText(PersonalizarPerfil.this);
                final String anterior = username.getText().toString();
                type_name.setText(anterior);

                AlertDialog.Builder name = new AlertDialog.Builder(PersonalizarPerfil.this);
                name.setTitle(R.string.escribirNombreUsuario);
                name.setView(type_name);
                name.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String usuario = type_name.getText().toString();
                        username.setText(usuario);
                        String newusername = username.getText().toString();
                        if(!anterior.equals(newusername) && validateUser(newusername)){
                            guardarCambios.setBackground(ContextCompat.getDrawable(PersonalizarPerfil.this, R.drawable.rounded_button));
                            actualizar = true;
                        }
                        else{
                            username.setText(anterior);
                        }
                    }
                });
                name.setNegativeButton(R.string.cancelar, null);

                name.show();

            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PersonalizarPerfil.this, R.string.noPuedesCambiarCorreo, Toast.LENGTH_SHORT).show();
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText type_phone = new EditText(PersonalizarPerfil.this);
                final String anterior = phone.getText().toString();
                type_phone.setText(anterior);

                AlertDialog.Builder name = new AlertDialog.Builder(PersonalizarPerfil.this);
                name.setTitle(R.string.escribirNumeroCelular);
                name.setView(type_phone);
                name.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        phone.setText(type_phone.getText().toString());
                        String number = phone.getText().toString();
                        if (!anterior.equals(number) && validatePhoneCol(number)) {
                            guardarCambios.setBackground(ContextCompat.getDrawable(PersonalizarPerfil.this, R.drawable.rounded_button));
                            actualizar = true;
                        }
                        else{
                            phone.setText(anterior);
                        }
                    }
                });
                name.setNegativeButton(R.string.cancelar, null);

                name.show();

            }
        });

        fechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder name = new AlertDialog.Builder(PersonalizarPerfil.this);
                name.setTitle(R.string.seleccionarFechaNacimiento);
                name.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final Calendar calendario = Calendar.getInstance();
                        DatePickerDialog datePickerDialog = new DatePickerDialog(PersonalizarPerfil.this,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        calendario.set(Calendar.YEAR, year);
                                        calendario.set(Calendar.MONTH, month);
                                        calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                        Calendar calendar2 = Calendar.getInstance();
                                        Date date = calendario.getTime();

                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                        fecha = simpleDateFormat.format(date);
                                        fechaNacimiento.setText(fecha);
                                        guardarCambios.setBackground(ContextCompat.getDrawable(PersonalizarPerfil.this, R.drawable.rounded_button));
                                        actualizar = true;
                                    }
                                }, calendario.get(Calendar.YEAR)-18, calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH));

                        datePickerDialog.show();
                    }
                });

                name.show();

            }
        });

        genero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] GenderChoice = getResources().getStringArray(R.array.GenderSelectionBox);
                final String anterior = genero.getText().toString();
                int SelectedGender = 0;

                //Seleccionando el género inicial
                switch(anterior) {
                    case "Masculino":
                        SelectedGender = 0;
                        break;
                    case "Femenino":
                        SelectedGender = 1;
                        break;
                    case "No especificar":
                        SelectedGender = 2;
                        break;
                }

                AlertDialog.Builder box = new AlertDialog.Builder(PersonalizarPerfil.this);
                box.setTitle(R.string.seleccionarGenero);
                box.setSingleChoiceItems(GenderChoice, SelectedGender, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0) {
                            genero.setText(R.string.masculino);
                        }
                        else if(which == 1) {
                            genero.setText(R.string.femenino);
                        }
                        else if(which == 2) {
                            genero.setText(R.string.noEspecificaGenero);
                        }
                    }
                }).setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!anterior.equals(genero.getText().toString())){
                            guardarCambios.setBackground(ContextCompat.getDrawable(PersonalizarPerfil.this, R.drawable.rounded_button));
                            actualizar = true;
                        }
                    }
                }).setNegativeButton(R.string.cancelar, null);
                box.show();

            }
        });

        guardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(actualizar == true) {

                    usuarioDatabase.setHistoria(history.getText().toString());
                    usuarioDatabase.setNombreUsuario(username.getText().toString());
                    usuarioDatabase.setEmail(email.getText().toString());
                    usuarioDatabase.setCelular(phone.getText().toString());
                    usuarioDatabase.setFechaNacimiento(fechaNacimiento.getText().toString());
                    usuarioDatabase.setGenero(genero.getText().toString());

                    Task task = userReferences.getAllUsers().child(current_user).setValue(usuarioDatabase);

                    Task<Void> changeInfoUser = Tasks.whenAll(task);
                    changeInfoUser.addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(PersonalizarPerfil.this, R.string.infoAlmacenada, Toast.LENGTH_SHORT).show();
                            guardarCambios.setBackground(ContextCompat.getDrawable(PersonalizarPerfil.this, R.drawable.rounded_button_disabled));
                            actualizar = false;
                        }
                    });

                }
                else{
                    Toast.makeText(PersonalizarPerfil.this, R.string.noHayCambios, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void asignarControles() {
        FullName.setText(usuarioDatabase.getNombre()+" "+usuarioDatabase.getApellidos());
        history.setText(usuarioDatabase.getHistoria());
        username.setText(usuarioDatabase.getNombreUsuario());
        email.setText(usuarioDatabase.getEmail());
        phone.setText(usuarioDatabase.getCelular());
        fechaNacimiento.setText(usuarioDatabase.getFechaNacimiento());
        genero.setText(usuarioDatabase.getGenero());


        Intent intent = getIntent();
        image = intent.getStringExtra("img");
        if(image.equals("NoPhoto")) {
            Glide.with(PersonalizarPerfil.this).load(usuarioDatabase.getURL_Foto()).into(fotoPerfil);
            image = usuarioDatabase.getURL_Foto();
        }
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onStart() {
        super.onStart();

        current_user = userReferences.getUser();
        userReferences.getAllUsers().child(current_user).addListenerForSingleValueEvent(new ValueEventListener() {
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

    public boolean validateUser(String user){
        if(user.isEmpty()){
            AlertDialog.Builder dialog = new AlertDialog.Builder(PersonalizarPerfil.this);
            dialog.setTitle(R.string.userValEmpty);
            dialog.setPositiveButton(R.string.aceptar, null);

            AlertDialog cuadro = dialog.create();
            cuadro.show();
            return false;
        }
        if(!user.matches("[a-zA-Z0-9_\\.]*")){
            AlertDialog.Builder dialog = new AlertDialog.Builder(PersonalizarPerfil.this);
            dialog.setTitle(R.string.userVal);
            dialog.setMessage(R.string.userValtxt);
            dialog.setPositiveButton(R.string.aceptar, null);

            AlertDialog cuadro = dialog.create();
            cuadro.show();
            return false;
        }
        if(user.length()<3 && user.length()>15){
            AlertDialog.Builder dialog = new AlertDialog.Builder(PersonalizarPerfil.this);
            dialog.setTitle(R.string.userVal);
            dialog.setMessage(R.string.userValExtension);
            dialog.setPositiveButton(R.string.aceptar, null);

            AlertDialog cuadro = dialog.create();
            cuadro.show();
            return false;
        }
        return true;
    }

    public boolean validatePhoneCol(String phone){

        if(!phone.matches("[0-9]{10}")){
            AlertDialog.Builder dialog = new AlertDialog.Builder(PersonalizarPerfil.this);
            dialog.setTitle(R.string.numberVal);
            dialog.setMessage(R.string.numberValExtension);
            dialog.setPositiveButton(R.string.aceptar, null);

            AlertDialog cuadro = dialog.create();
            cuadro.show();
            return false;
        }

        return true;
    }

}
