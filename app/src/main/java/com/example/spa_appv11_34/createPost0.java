package com.example.spa_appv11_34;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.spa_appv11_34.Clases_Interaccion.UsuarioDatabase;
import com.example.spa_appv11_34.Referencias.UsuarioReferences;
import com.example.spa_appv11_34.localAdapters.imagesGalleryAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.kbeanie.multipicker.api.ImagePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.callbacks.ImagePickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenImage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class createPost0 extends AppCompatActivity implements ImagePickerCallback {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private RecyclerView rvSearch;
    private imagesGalleryAdapter adapter;

    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private Button back;
    private Button siguiente;
    private List<String> contenedor = new ArrayList<>();

    List<String> listOfAllImages = new ArrayList<>();

    private UsuarioReferences usuarioReferences = UsuarioReferences.getInstance();
    private UsuarioDatabase usuarioDatabase = new UsuarioDatabase();

    private ImageButton pickGallery;
    private ImageButton takePhoto;

    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post0);

        siguiente = findViewById(R.id.btnNextStepPost);//BOTÓN PARA ENVIAR LAS IMÁGENES A LA SIGUIENTE ACTIVIDAD
        rvSearch = findViewById(R.id.rvSearchGallery);//RECYCLER PARA MOSTRAR LA GALERIA

        imageView1 = findViewById(R.id.imagePost1);
        imageView2 = findViewById(R.id.imagePost2);
        imageView3 = findViewById(R.id.imagePost3);
        imageView4 = findViewById(R.id.imagePost4);
        back = findViewById(R.id.btnBackCP0);

        pickGallery = findViewById(R.id.btnpickGalleryCreatePost);
        takePhoto = findViewById(R.id.btntakePhotoCreatePost);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Deseleccionar imagen
            }
        });

        pickGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageSingle();
            }
        });

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int l = adapter.getCounter();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final TaskCompletionSource<DataSnapshot> dbSource = new TaskCompletionSource<>();
                usuarioReferences.getAllUsers().child(usuarioReferences.getUser()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dbSource.setResult(dataSnapshot);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        dbSource.setException(databaseError.toException());
                    }
                });

                final Task<DataSnapshot> dbTask = dbSource.getTask();

                Task<Void> allTask;
                allTask = Tasks.whenAll(dbTask);

                allTask.addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DataSnapshot data = dbTask.getResult();
                        usuarioDatabase = data.getValue(UsuarioDatabase.class);

                        Intent intencion = new Intent(createPost0.this,MyPostActivity.class);

                        intencion.putExtra("nombres",usuarioDatabase.getNombre());
                        intencion.putExtra("apellidos", usuarioDatabase.getApellidos());
                        intencion.putExtra("username", usuarioDatabase.getNombreUsuario());
                        intencion.putExtra("historia", usuarioDatabase.getHistoria());
                        intencion.putExtra("foto", usuarioDatabase.getURL_Foto());

                        startActivity(intencion);
                        finish();

                    }
                });

            }
        });

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contenedor.size()>0) {
                    Intent intencion = new Intent(createPost0.this, CreatePost.class);
                    for(int j = 0; j < contenedor.size(); j++){
                        intencion.putExtra("img"+String.valueOf(j),contenedor.get(j));
                    }
                    startActivity(intencion);
                    finish();
                }
                else{
                    Toast.makeText(createPost0.this, "No has seleccionado ninguna imagen", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            showGallery();
        }
        else{
            Toast.makeText(this, "Access denied", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        askPermission();
    }

    public void askPermission(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(createPost0.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(createPost0.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(createPost0.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        0);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        else{
            showGallery();
        }
    }

    public void showGallery(){

        final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;

        //Stores all the images from the gallery in Cursor
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                null, null);

        cursor.moveToLast();

        while (cursor.moveToPrevious()) {
            String absolutePathOfImage = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));

            //File whatsappMediaDirectoryName = new File(Environment.getExternalStorageDirectory()
                    //.getAbsolutePath() + "/camera");

            //absolutePathOfImage = whatsappMediaDirectoryName.toString();

            listOfAllImages.add(absolutePathOfImage);
        }

        int c = listOfAllImages.size();

        List<ImageView> imageViewList = new ArrayList<>();

        imageViewList.add(imageView1);
        imageViewList.add(imageView2);
        imageViewList.add(imageView3);
        imageViewList.add(imageView4);

        adapter = new imagesGalleryAdapter(createPost0.this,listOfAllImages, imageViewList, siguiente);
        GridLayoutManager l = new GridLayoutManager(this, 2);
        rvSearch.setLayoutManager(l);
        rvSearch.setAdapter(adapter);

        contenedor = adapter.getContenedor();
    }

    private ImagePicker imagePicker;

    public void pickImageSingle() {
        imagePicker = new ImagePicker(this);
        //imagePicker.setDebugglable(true);
        imagePicker.setRequestId(1234);
        //imagePicker.ensureMaxSize(500, 500);
        //imagePicker.shouldGenerateMetadata(true);
        //imagePicker.shouldGenerateThumbnails(true);
        imagePicker.setImagePickerCallback(this);
        Bundle bundle = new Bundle();
        bundle.putInt("android.intent.extras.CAMERA_FACING", 1);
        imagePicker.pickImage();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (true) {
            if (requestCode == Picker.PICK_IMAGE_DEVICE) {
                if (imagePicker == null) {
                    imagePicker = new ImagePicker(this);
                    imagePicker.setImagePickerCallback(this);
                }
                imagePicker.submit(data);
            }
        }
    }

    @Override
    public void onImagesChosen(List<ChosenImage> list) {
        Toast.makeText(createPost0.this, "Seleccionado", Toast.LENGTH_SHORT).show();
        uri = Uri.fromFile(new File(list.get(0).getThumbnailSmallPath()));

        int l = adapter.getCounter();
        List<String> paths = adapter.getContenedor();

        switch (l){
            case 0:
                Glide.with(createPost0.this).load(uri).into(imageView1);
                adapter.setCounter(l+1);
                paths.add(uri.toString());
                adapter.setContenedor(paths);
                break;
            case 1:
                Glide.with(createPost0.this).load(uri).into(imageView2);
                adapter.setCounter(l+1);
                paths.add(uri.toString());
                adapter.setContenedor(paths);
                break;
            case 2:
                Glide.with(createPost0.this).load(uri).into(imageView3);
                adapter.setCounter(l+1);
                paths.add(uri.toString());
                adapter.setContenedor(paths);
                break;
            case 3:
                Glide.with(createPost0.this).load(uri).into(imageView4);
                adapter.setCounter(l+1);
                paths.add(uri.toString());
                adapter.setContenedor(paths);
                break;
            case 4:
                Toast.makeText(this, "No puedes cargar más de cuatro imágenes", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onError(String s) {

    }



}
