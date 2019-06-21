package com.example.spa_appv11_34;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText email, password;
    private Button signin, create_acc;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        create_acc = findViewById(R.id.btnCrearCuenta);
        signin = findViewById(R.id.btnIngresarLogin);
        email = findViewById(R.id.etEmailLogin);
        password = findViewById(R.id.etContraseñaLogin);

        mAuth = FirebaseAuth.getInstance();


        create_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Register.class));
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String correo = email.getText().toString();
                String contraseña = password.getText().toString();

                Toast.makeText(Login.this, correo, Toast.LENGTH_SHORT).show();

                mAuth.signInWithEmailAndPassword(correo, contraseña)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    Toast.makeText(Login.this, "bienvenido", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(Login.this, home_spaApp.class);
                                    intent.putExtra("button", "3");
                                    startActivity(intent);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Login.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent intencion = new Intent(Login.this, home_spaApp.class);
            intencion.putExtra("button","3");
            startActivity(intencion);
        } else {
            // No user is signed in
        }

    }

}
