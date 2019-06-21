package com.example.spa_appv11_34;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class Register extends AppCompatActivity {

    private FloatingActionButton fbNext;
    private DatePickerDialog.OnDateSetListener mData;

    private EditText nombreRegister;
    private EditText lastNameRegister;
    private EditText emailRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fbNext = findViewById(R.id.fbNextRegister);

        nombreRegister = findViewById(R.id.etNombreRegister);
        lastNameRegister = findViewById(R.id.etApellidosRegister);
        emailRegister = findViewById(R.id.etEmailRegister);

        fbNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre, apellidos, email;

                nombre = nombreRegister.getText().toString();
                apellidos = lastNameRegister.getText().toString();
                email = emailRegister.getText().toString();

                Intent intencion = new Intent(Register.this,Register2.class);

                intencion.putExtra("nombre", nombre);
                intencion.putExtra("apellidos", apellidos);
                intencion.putExtra("email", email);

                if(validateEmail(email) && validateName(nombre,"nombre")
                        && validateName(apellidos,"apellidos") ){
                    startActivity(intencion);
                }

            }
        });

    }

    public boolean validateEmail(String email){

        if(email.isEmpty()){
            AlertDialog.Builder dialog = new AlertDialog.Builder(Register.this);
            dialog.setTitle("Debes ingresar una dirección de correo electrónico");
            dialog.setPositiveButton("Aceptar", null);

            AlertDialog cuadro = dialog.create();
            cuadro.show();
            return false;
        }
        //Validar si el correo tiene @
        if(!email.matches(".*@.*")){

            AlertDialog.Builder dialog = new AlertDialog.Builder(Register.this);
            dialog.setTitle("El correo debe contener @");
            dialog.setPositiveButton("Aceptar", null);

            AlertDialog cuadro = dialog.create();
            cuadro.show();

            return false;
        }
        //Validar si el correo tiene algún ccTLD válido
        if( !email.matches(".*\\.[a-z]{2,3}") ){

            AlertDialog.Builder dialog = new AlertDialog.Builder(Register.this);
            dialog.setTitle("El correo debe terminar en .com o algún ccTLD válido");
            dialog.setPositiveButton("Aceptar", null);

            AlertDialog cuadro = dialog.create();
            cuadro.show();

            return false;
        }
        //Validar si el correo tiene algún caracter especial
        if(!email.matches("[a-zA-Z0-9_\\.]*@.*")){

            AlertDialog.Builder dialog = new AlertDialog.Builder(Register.this);
            dialog.setTitle("Correo electrónico invalido");
            dialog.setMessage("El correo electrónico no puede contener espacios o caracteres especiales");
            dialog.setPositiveButton("Aceptar", null);

            AlertDialog cuadro = dialog.create();
            cuadro.show();

            return false;
        }
        //Validar si el correo está vacío
        if(!email.matches("[a-zA-Z0-9_\\.]{1,}@.*")){

            AlertDialog.Builder dialog = new AlertDialog.Builder(Register.this);
            dialog.setTitle("Correo electrónico invalido");
            dialog.setMessage("Tu dirección de correo no puede estar vacía");
            dialog.setPositiveButton("Aceptar", null);

            AlertDialog cuadro = dialog.create();
            cuadro.show();

            return false;
        }
        //Validar si el proveedor está vacío
        if(!email.matches("[a-zA-Z0-9_\\.]{1,}@[a-z]{1,}\\.[a-z]{2,3}")){

            AlertDialog.Builder dialog = new AlertDialog.Builder(Register.this);
            dialog.setTitle("Correo electrónico invalido");
            dialog.setMessage("Debes utilizar un proveedor válido de correo electrónico");
            dialog.setPositiveButton("Aceptar", null);

            AlertDialog cuadro = dialog.create();
            cuadro.show();

            return false;
        }

        return true;
    }

    public boolean validateName(String text, String id){
        if(text.isEmpty()){
            AlertDialog.Builder dialog = new AlertDialog.Builder(Register.this);
            dialog.setTitle(id+" incorrecto");
            dialog.setMessage("Tu "+id+" no puede estar vacía");
            dialog.setPositiveButton("Aceptar", null);

            AlertDialog cuadro = dialog.create();
            cuadro.show();
            return false;
        }
        if(!text.matches("[a-zA-Z\\ ]{1,}")){

            AlertDialog.Builder dialog = new AlertDialog.Builder(Register.this);
            dialog.setTitle(id+" incorrecto");
            dialog.setMessage("Tu "+id+" no puede contener número o caracteres especiales");
            dialog.setPositiveButton("Aceptar", null);

            AlertDialog cuadro = dialog.create();
            cuadro.show();

            return false;
        }
        return true;
    }

}
