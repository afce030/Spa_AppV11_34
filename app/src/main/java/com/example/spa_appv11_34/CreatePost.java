package com.example.spa_appv11_34;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.spa_appv11_34.Clases_Interaccion.CentroPostDatabase;
import com.example.spa_appv11_34.Clases_Interaccion.UsuarioDatabase;
import com.example.spa_appv11_34.Referencias.UsuarioReferences;
import com.example.spa_appv11_34.localAdapters.suggestedUsersAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CreatePost extends AppCompatActivity {

    private List<String> contenedor = new ArrayList<>();
    private Button post;
    private Button back;
    private EditText postContent;
    private ConstraintLayout suggestions;
    private RecyclerView rvSearchUsers;

    public static final int UP = 1;

    private Task<Void> uploadPostImages;
    private Task<Void> uploadPostContent;

    //CLASE USADA PARA OBTENER LAS REFERENCIAS DE LAS RUTAS DONDE SE SUBIRÁN O CARGARÁN LOS DATOS
    private UsuarioReferences usuarioReferences = UsuarioReferences.getInstance();
    private String postCounter;

    private String current_user;
    private UsuarioDatabase usuarioDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        Intent intent = getIntent();

        //RECIBIENDO IMÁGENES DE LA ACTIVIDAD ANTERIOR
        for(int j = 0; j < 4; j++) {
            //CONDICIONAL USADO PARA PREGUNTAR POR CADA UNA DE LAS CUATRO IMÁGENES
            if(intent.hasExtra("img"+String.valueOf(j))){
                String image = intent.getStringExtra("img" + String.valueOf(j));
                contenedor.add(image);
            }
            else{
                //contenedor.add("NoPhoto");
            }
        }

        //Toast.makeText(this, String.valueOf(contenedor.size()), Toast.LENGTH_SHORT).show();

        //DECLARACIÓN DE CONTROLES
        postContent = findViewById(R.id.etPostCreatedContent);//TEXTO INGRESADO POR EL USUARIO
        post = findViewById(R.id.btnPost);//BOTÓN PARA POSTEAR
        back = findViewById(R.id.btnBackCP);
        suggestions = findViewById(R.id.userSuggestion);
        rvSearchUsers = findViewById(R.id.rvSuggestedUsers);
        rvSearchUsers.setHasFixedSize(true);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreatePost.this, createPost0.class));
                finish();
            }
        });

        postContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                String texto = s.toString();

                if(texto.length()>1){
                    post.setBackground(ContextCompat.getDrawable(CreatePost.this
                            , R.drawable.rounded_button));
                }
                else{
                    post.setBackground(ContextCompat.getDrawable(CreatePost.this
                            , R.drawable.rounded_button_disabled));
                }

                String lastWord = texto.substring(texto.lastIndexOf(" ")+1);
                if(lastWord.matches("@[a-zA-Z0-9_\\.]{1,15}")){

                    suggestions.setVisibility(View.VISIBLE);
                    Query query = usuarioReferences.getAllUsers().
                            orderByChild("nombreUsuario").startAt(lastWord.substring(1))
                            .endAt(lastWord.substring(1)+"zzzzzzzzzzzzz").limitToFirst(20);

                    final suggestedUsersAdapter adapter = new suggestedUsersAdapter(postContent, suggestions);
                    ValueEventListener valueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for(DataSnapshot child: dataSnapshot.getChildren()) {
                                UsuarioDatabase usuarioDatabase = child.getValue(UsuarioDatabase.class);
                                adapter.addUser(usuarioDatabase.getNombreUsuario());
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    };
                    query.addListenerForSingleValueEvent(valueEventListener);

                    LinearLayoutManager l = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,
                            false);
                    rvSearchUsers.setAdapter(adapter);
                    rvSearchUsers.setLayoutManager(l);

                }
                else{
                    suggestions.setVisibility(View.GONE);
                }

            }
        });

        //ACCIÓN REALIZADA CUANDO EL USUARIO OPRIME EL BOTÓN DE POSTEAR
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(CreatePost.this);
                builder.setCancelable(false);
                builder.setView(R.layout.progress_bar);
                final AlertDialog dialog = builder.create();

                dialog.show();

                //CUANDO SE OPRIME EL BOTÓN DE POSTEAR SUBIMOS EN PRIMER LUGAR LA IMÁGENES
                //LUEGO SE OBTIENEN LAS URL DE LAS IMÁGENES
                //POR ÚLTIMO SE SUBE EL CONTENIDO DEL POST A REALTIME DATABASE EN 2 UBICACIONES DIFERENTES

                //OBTENIENDO EL NOMBRE DEL USUARIO PARA INCLUIRLO EN EL postID
                current_user = usuarioReferences.getUser();

                //LAS IMÁGENES SE SUBEN USANDO LA CLASE TASKS LA CUAL ESPERA A QUE SE COMPLETEN TODAS LAS TAREAS
                //DE IGUAL MANERA SE PROCEDE CON EL CONTENIDO DEL POST CUANDO SE SUBE A DOS UBICACIONES DIFERENTES

                //TOMAMOS EL CONTENIDO DEL CAMPO DE TEXTO Y LO VALIDAMOS CON LA FUNCIÓN validatePost(textPost)
                String textPost = postContent.getText().toString();

                if(validatePost(textPost)) {

                    //CONSTRUYENDO EL ID DEL POST CON EL NÚMERO DEL POST Y EL UID DEL USUARIO
                    final String postID = postCounter + "_" + current_user;

                    //CLASE UTILIZADA PARA SUBIR EL CONTENIDO DEL POST A DOS UBICACIONES DIFERENTES
                    final CentroPostDatabase centroPostDatabase = new CentroPostDatabase();
                    //LISTA CREADA PARA OBTENER LAS URL DE LAS IMÁGENES SUBIDAS
                    final List<String> imagesURL = new ArrayList<>();
                    //LISTA DE TAREAS  A COMPLETAR
                    final Collection<UploadTask> taskUpload = new ArrayList<>();

                    //CICLO USADO PARA CREAR LAS TAREAS DE SUBIDA DE IMÁGENES
                    for (int j = 0; j < contenedor.size(); j++) {

                        Uri image = Uri.parse(contenedor.get(j));
                        //ES NECESARIO APUNTAR AL ARCHIVO, POR ESTA RAZÓN SE CREA UN NUEVO URI
                        Uri uploadUri = Uri.fromFile(new File(image.toString()));

                        UploadTask uploadTask = usuarioReferences.getMyPostImages().child(postID)
                                .child(uploadUri.getLastPathSegment()).putFile(uploadUri);

                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                //here
                                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!urlTask.isSuccessful()) ;
                                Uri downloadUrl = urlTask.getResult();

                                //TOMAMOS LA URL DE LA IMAGEN
                                final String sdownload_url = String.valueOf(downloadUrl);
                                //https://stackoverflow.com/questions/51056397/how-to-use-getdownloadurl-in-recent-versions
                                imagesURL.add(sdownload_url);
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                System.out.println("Upload is " + progress + "% done");
                            }
                        });

                        //TAREAS A SUBIR
                        taskUpload.add(uploadTask);

                    }

                    //SUBIMOS LAS IMÁGENES
                    uploadPostImages = Tasks.whenAll(taskUpload);

                    //SE DECLARAN LAS DIFERENTES POSIBILIDADES(DEPENDIENDO DE CUANTAS IMÁGENES TENGA EL POST
                    uploadPostImages.addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            int post_images_count = imagesURL.size();//VARIABLE QUE CUENTA EL NÚMERO DE IMÁGENES QUE SUBIÓ EL USUARIO
                            if (post_images_count == contenedor.size()) {

                                centroPostDatabase.setTexto(postContent.getText().toString());
                                centroPostDatabase.setUid_post(postID);

                                String username = usuarioDatabase.getNombreUsuario();
                                centroPostDatabase.setUsuario(username);

                                centroPostDatabase.setURL_Foto1(imagesURL.get(0));

                                if (post_images_count > 1) {
                                    centroPostDatabase.setURL_Foto2(imagesURL.get(1));
                                } else {
                                    centroPostDatabase.setURL_Foto2("NoPhoto");
                                }

                                if (post_images_count > 2) {
                                    centroPostDatabase.setURL_Foto3(imagesURL.get(2));
                                } else {
                                    centroPostDatabase.setURL_Foto3("NoPhoto");
                                }

                                if (post_images_count > 3) {
                                    centroPostDatabase.setURL_Foto4(imagesURL.get(3));
                                } else {
                                    centroPostDatabase.setURL_Foto4("NoPhoto");
                                }

                                Task task1 = usuarioReferences.getPostList().child(postID).setValue(centroPostDatabase);
                                Task task2 = usuarioReferences.getMyPost().child(postID).setValue(centroPostDatabase);

                                uploadPostContent = Tasks.whenAll(task1, task2);

                                uploadPostContent.addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        dialog.dismiss();

                                        Toast.makeText(CreatePost.this, R.string.postPublicado, Toast.LENGTH_SHORT).show();
                                        Intent intencion = new Intent(CreatePost.this, home_spaApp.class);
                                        intencion.putExtra("button", "3");
                                        startActivity(intencion);

                                    }
                                });

                            } else {
                                Toast.makeText(CreatePost.this, "No son iguales: " + String.valueOf(post_images_count) +
                                        " " + String.valueOf(contenedor.size()), Toast.LENGTH_LONG).show();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CreatePost.this, R.string.errorConexion, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        //INTERFAZ USADA PARA OBTENER CUANTOS POST TIENE EL USUARIO
        usuarioReferences.myPostCounter(new UsuarioReferences.IDcountMyPost() {
            @Override
            public void myPostCounter(long c) {
                postCounter = String.valueOf(c);
            }
        });

        current_user = usuarioReferences.getUser();
        usuarioReferences.getAllUsers().child(current_user).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuarioDatabase = dataSnapshot.getValue(UsuarioDatabase.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public boolean validatePost(String text) {

        if (text.isEmpty()) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(CreatePost.this);
            dialog.setTitle(R.string.historyEmpty);
            dialog.setPositiveButton(R.string.aceptar, null);

            AlertDialog cuadro = dialog.create();
            cuadro.show();
            return false;
        }
        if (!text.matches(".{2,144}")) {

            AlertDialog.Builder dialog = new AlertDialog.Builder(CreatePost.this);
            dialog.setTitle(R.string.historyVal);
            dialog.setMessage(R.string.historyExtension);
            dialog.setPositiveButton(R.string.aceptar, null);

            AlertDialog cuadro = dialog.create();
            cuadro.show();

            return false;
        }

        return true;
    }

}
