package com.example.spa_appv11_34;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Register2 extends AppCompatActivity {

    private FloatingActionButton fbNext;

    private EditText nameUser;
    private EditText password;
    private EditText confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        nameUser = findViewById(R.id.userNameRegister2);
        password = findViewById(R.id.passwordRegister2);
        confirmPassword = findViewById(R.id.password2Register2);
        fbNext = findViewById(R.id.fbNextRegister2);

        fbNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent intent = getIntent();

                final String nombre = intent.getStringExtra("nombre");
                final String apellidos = intent.getStringExtra("apellidos");
                final String email = intent.getStringExtra("email");
                final String user = nameUser.getText().toString();
                final String contraseña = password.getText().toString();
                final String confirmar = confirmPassword.getText().toString();

                AlertDialog.Builder terminos = new AlertDialog.Builder(Register2.this);
                terminos.setTitle("Términos y condiciones estipulados por Spa-App");
                terminos.setMessage("dfdffgdfdgsdfsdfdsfsdgfsdg");
                terminos.setPositiveButton("Acepto", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (validateUser(user) && validatePassword(contraseña, confirmar)) {

                            Intent intentSend = new Intent(Register2.this, ActividadBienvenida.class);

                            intentSend.putExtra("nombre", nombre);
                            intentSend.putExtra("apellidos", apellidos);
                            intentSend.putExtra("email", email);
                            intentSend.putExtra("usuario", user);
                            intentSend.putExtra("contraseña", contraseña);

                            startActivity(intentSend);
                        }
                    }
                });
                terminos.setNegativeButton("No Acepto", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog cuadroDialogo = terminos.create();

                if( validateUser(user) && validatePassword(contraseña,confirmar) ){
                    cuadroDialogo.show();
                }

            }
        });

    }

    public boolean validatePassword(String p1, String p2){

        if( !p1.equals(p2) ){

            AlertDialog.Builder dialog = new AlertDialog.Builder(Register2.this);
            dialog.setTitle("Ambas contraseñas deben coincidir");
            dialog.setPositiveButton("Aceptar", null);

            AlertDialog cuadro = dialog.create();
            cuadro.show();

            return false;
        }
        if( p1.length() < 6 || p1.length() > 16 ){

            AlertDialog.Builder dialog = new AlertDialog.Builder(Register2.this);
            dialog.setTitle("La contraseña debe contener entre 6 y 16 caracteres");
            dialog.setPositiveButton("Aceptar", null);

            AlertDialog cuadro = dialog.create();
            cuadro.show();

            return false;
        }

        return true;
    }

    public boolean validateUser(String user){
        if(user.isEmpty()){
            AlertDialog.Builder dialog = new AlertDialog.Builder(Register2.this);
            dialog.setTitle("Debes ingresar tu nombre de usario");
            dialog.setPositiveButton("Aceptar", null);

            AlertDialog cuadro = dialog.create();
            cuadro.show();
            return false;
        }
        if(!user.matches("[a-zA-Z0-9_\\.]*")){
            AlertDialog.Builder dialog = new AlertDialog.Builder(Register2.this);
            dialog.setTitle("Nombre de usuario incorrecto");
            dialog.setMessage("Tu nombre de usuario no puede contener espacios o caracteres especiales");
            dialog.setPositiveButton("Aceptar", null);

            AlertDialog cuadro = dialog.create();
            cuadro.show();
            return false;
        }
        if(user.length()<3 && user.length()>15){
            AlertDialog.Builder dialog = new AlertDialog.Builder(Register2.this);
            dialog.setTitle("Nombre de usuario incorrecto");
            dialog.setMessage("Tu nombre de usuario debe contener entre 3 y 15 caracteres");
            dialog.setPositiveButton("Aceptar", null);

            AlertDialog cuadro = dialog.create();
            cuadro.show();
            return false;
        }
        return true;
    }

}
