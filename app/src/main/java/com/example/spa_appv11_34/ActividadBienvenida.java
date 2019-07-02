package com.example.spa_appv11_34;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spa_appv11_34.Clases_Interaccion.UsuarioDatabase;
import com.example.spa_appv11_34.Clases_Interaccion.UsuarioPreferencias;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActividadBienvenida extends AppCompatActivity {

    private Button continuarMain;
    private TextView textoBienvenida;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference1;
    private DatabaseReference databaseReference2;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_bienvenida);

        continuarMain = findViewById(R.id.btnContinuarBienvenida);
        textoBienvenida = findViewById(R.id.TextoBienvenida);

        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();

        final String nombre = intent.getStringExtra("nombre");
        final String apellidos = intent.getStringExtra("apellidos");
        final String email = intent.getStringExtra("email");
        final String user = intent.getStringExtra("usuario");
        final String contraseña = intent.getStringExtra("contraseña");

        textoBienvenida.setText("Hola "+nombre+", bienvenido a Spa-App");

        continuarMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.createUserWithEmailAndPassword(email, contraseña)
                        .addOnCompleteListener(ActividadBienvenida.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    //Accedemos al usuario actual
                                    FirebaseUser currentUser = mAuth.getCurrentUser();
                                    //Creamos el nodo Usuarios/keyUsuario
                                    databaseReference1 = firebaseDatabase.getReference("Usuarios/" + currentUser.getUid());
                                    UsuarioDatabase usuarioDatabase = new UsuarioDatabase();
                                    usuarioDatabase.setNombre(nombre);
                                    usuarioDatabase.setApellidos(apellidos);
                                    usuarioDatabase.setNombreUsuario(user);
                                    usuarioDatabase.setURL_Foto(constantes.defaultPhoto);
                                    usuarioDatabase.setHistoria("Usuario Spa-App");
                                    usuarioDatabase.setEmail(email);
                                    usuarioDatabase.setFechaNacimiento("01/01/2001");
                                    usuarioDatabase.setGenero("No especifica");
                                    usuarioDatabase.setCelular("No especificado");
                                    usuarioDatabase.setLlaveUsuario(currentUser.getUid());
                                    usuarioDatabase.setLlaveCentro("No center");

                                    Task task1 = databaseReference1.setValue(usuarioDatabase);

                                    databaseReference2 = firebaseDatabase.getReference("Preferencias/" + currentUser.getUid());
                                    UsuarioPreferencias usuarioPreferencias = new UsuarioPreferencias();
                                    usuarioPreferencias.setIdioma("Español");
                                    usuarioPreferencias.setNotificaciones(true);
                                    usuarioPreferencias.setTheme("Claro");
                                    usuarioPreferencias.setCreditCard(false);
                                    usuarioPreferencias.setPayPal(false);

                                    Task task2 = databaseReference2.setValue(usuarioDatabase);

                                    Task<Void> tasks = Tasks.whenAll(task1,task2);

                                    tasks.addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(ActividadBienvenida.this, "Bienvenido", Toast.LENGTH_SHORT).show();

                                            Intent intencion = new Intent(ActividadBienvenida.this, home_spaApp.class);
                                            intencion.putExtra("button", "3");
                                            startActivity(intencion);
                                            finish();
                                        }
                                    });


                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(ActividadBienvenida.this, "No pudimos registrar tu usuario, verifica tu conexión a internet", Toast.LENGTH_SHORT).show();

                                }

                            }
                        });
            }
        });

    }

}

