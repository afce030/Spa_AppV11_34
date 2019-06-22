package com.example.spa_appv11_34;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraX;
import androidx.camera.core.FlashMode;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.spa_appv11_34.Referencias.UsuarioReferencias;
import com.example.spa_appv11_34.localAdapters.galleryAdapterSimple;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.UploadTask;
import com.kbeanie.multipicker.api.CacheLocation;
import com.kbeanie.multipicker.api.CameraImagePicker;
import com.kbeanie.multipicker.api.ImagePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.callbacks.ImagePickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenImage;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SubirFotoActivity extends AppCompatActivity implements ImagePickerCallback{

    private Button galeria;
    private Button camara;
    private Button guardar;

    private ImageView imageView;
    private TextureView textureView;
    private ImageButton take;

    private CircleImageView fotoPerfil;

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private RecyclerView rvSearch;
    private galleryAdapterSimple adapter;
    private ConstraintLayout constraintLayout;
    private List<Button> buttons = new ArrayList<>();

    private UsuarioReferencias usuarioReferencias = UsuarioReferencias.getInstance();

    private boolean activated = false;
    private String pickerPath;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_foto);

        //Image in personalizar activity
        //fotoPerfil = findViewById(R.id.CambiarFotoPerfil);

        galeria = findViewById(R.id.pickUsingGallery);
        camara = findViewById(R.id.pickUsingCamera);
        guardar = findViewById(R.id.guardarFoto);

        imageView = findViewById(R.id.imgChosenImage);
        textureView = findViewById(R.id.txvCamera);
        take = findViewById(R.id.takePicture);

        constraintLayout = findViewById(R.id.lyGalleryPerfil);
        constraintLayout.setVisibility(View.GONE);

        buttons.add(galeria);
        buttons.add(camara);
        buttons.add(guardar);

        rvSearch = findViewById(R.id.rvSearchGallerySimple);//RECYCLER PARA MOSTRAR LA GALERIA
        //adapter = new galleryAdapterSimple(SubirFotoActivity.this,new ArrayList<String>(),imageView,buttons,constraintLayout);

        Intent intent = getIntent();
        final String image = intent.getStringExtra("img");

        Glide.with(SubirFotoActivity.this).load(image).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(uri != null) {
                    //Pasar imagen usando intents
                    Intent intencion = new Intent(SubirFotoActivity.this, imageViewer.class);
                    intencion.putExtra("img", uri.toString());
                    intencion.putExtra("local", "0");
                    startActivity(intencion);
                }
                else{
                    Intent intencion = new Intent(SubirFotoActivity.this, imageViewer.class);
                    intencion.putExtra("img", image);
                    intencion.putExtra("local", "1");
                    startActivity(intencion);
                }

            }
        });

        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*askPermission();

                buttons.get(0).setVisibility(View.GONE);
                buttons.get(1).setVisibility(View.GONE);
                buttons.get(2).setVisibility(View.GONE);

                constraintLayout.setVisibility(View.VISIBLE);*/
                pickImageSingle();

            }
        });

        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*com.github.dhaval2404.imagepicker.ImagePicker.Companion.with(SubirFotoActivity.this)
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();*/
                //takePicture();

                textureView.setVisibility(View.VISIBLE);
                take.setVisibility(View.VISIBLE);

                imageView.setVisibility(View.GONE);
                galeria.setVisibility(View.GONE);
                camara.setVisibility(View.GONE);
                guardar.setVisibility(View.GONE);

                PreviewConfig previewConfig =
                        new PreviewConfig.Builder()
                                .setTargetRotation(getWindowManager().getDefaultDisplay().getRotation())
                                .setLensFacing(CameraX.LensFacing.BACK)
                                .build();

                Preview preview = new Preview(previewConfig);

                preview.setOnPreviewOutputUpdateListener(
                        new Preview.OnPreviewOutputUpdateListener() {
                            @Override
                            public void onUpdated(Preview.PreviewOutput previewOutput) {
                                // Your code here. For example, use previewOutput.getSurfaceTexture()
                                // and post to a GL renderer.
                                //Requiere android:hardwareAccelerated="true" en el manifest
                                textureView.setSurfaceTexture(previewOutput.getSurfaceTexture());
                            }
                        });

                CameraX.bindToLifecycle((LifecycleOwner) SubirFotoActivity.this, preview);

            }
        });

        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageCaptureConfig config =
                        new ImageCaptureConfig.Builder()
                                .setTargetRotation(getWindowManager().getDefaultDisplay().getRotation())
                                .setFlashMode(FlashMode.ON)
                                .setCaptureMode(ImageCapture.CaptureMode.MAX_QUALITY)
                                .build();

                ImageCapture imageCapture = new ImageCapture(config);

                CameraX.bindToLifecycle((LifecycleOwner) SubirFotoActivity.this, imageCapture);

                Date currentTime = Calendar.getInstance().getTime();
                File file = new File("/sdcard/DCIM/Camera/F"+currentTime+".jpg");
                imageCapture.takePicture(file, new ImageCapture.OnImageSavedListener() {
                    @Override
                    public void onImageSaved(@NonNull File file) {
                        Toast.makeText(SubirFotoActivity.this, R.string.imagenAlmacenada, Toast.LENGTH_SHORT).show();
                        uri = Uri.fromFile(file);
                        imageView.setImageURI(uri);

                        textureView.setVisibility(View.GONE);
                        take.setVisibility(View.GONE);

                        imageView.setVisibility(View.VISIBLE);
                        galeria.setVisibility(View.VISIBLE);
                        camara.setVisibility(View.VISIBLE);
                        guardar.setVisibility(View.VISIBLE);

                        CameraX.unbindAll();

                    }

                    @Override
                    public void onError(@NonNull ImageCapture.UseCaseError useCaseError, @NonNull String message, @Nullable Throwable cause) {

                    }
                });

            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activated == true){

                    //uri = adapter.getUri_selected();
                    Uri uploadUri = Uri.fromFile(new File(uri.toString()));

                    Task<Void> uploadImage;
                    UploadTask uploadTask = usuarioReferencias.getMyProfileImages().child("myUserPhotos").child(ServerValue.TIMESTAMP.toString()).putFile(uploadUri);

                    final String[] sdownload_url = new String[1];
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful()) ;
                            Uri downloadUrl = urlTask.getResult();

                            //TOMAMOS LA URL DE LA IMAGEN
                            sdownload_url[0] = String.valueOf(downloadUrl);
                            //https://stackoverflow.com/questions/51056397/how-to-use-getdownloadurl-in-recent-versions
                        }
                    });

                    uploadImage = Tasks.whenAll(uploadTask);
                    uploadImage.addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            String user = usuarioReferencias.getUser();
                            Task task = usuarioReferencias.getAllCenters().child(user).child("URL_Foto").setValue(sdownload_url[0]);

                            Task<Void> uploadPostContent;
                            uploadPostContent = Tasks.whenAll(task);

                            uploadPostContent.addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Toast.makeText(SubirFotoActivity.this, R.string.imagenModificada, Toast.LENGTH_SHORT).show();
                                    //Pasar imagen usando intents
                                    Intent intencion = new Intent(SubirFotoActivity.this, PersonalizarPerfil.class);
                                    intencion.putExtra("img", uri.toString());
                                    startActivity(intencion);

                                }
                            });

                        }
                    });

                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
/*
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

    public void askPermission(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(SubirFotoActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(SubirFotoActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(SubirFotoActivity.this,
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
        final String orderBy = MediaStore.Images.Media._ID;
        //Stores all the images from the gallery in Cursor
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                null, orderBy);

        List<String> listOfAllImages = new ArrayList<>();
        while (cursor.moveToNext()) {
            String absolutePathOfImage = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
            listOfAllImages.add(absolutePathOfImage);
        }

        int c = listOfAllImages.size();

        adapter = new galleryAdapterSimple(SubirFotoActivity.this,listOfAllImages,imageView,buttons,constraintLayout);
        GridLayoutManager l = new GridLayoutManager(this, 2);
        rvSearch.setLayoutManager(l);
        rvSearch.setAdapter(adapter);

    }
*/

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

    private CameraImagePicker cameraPicker;

    public void takePicture() {
        cameraPicker = new CameraImagePicker(this);
        //cameraPicker.setDebugglable(true);
        cameraPicker.setCacheLocation(CacheLocation.EXTERNAL_STORAGE_APP_DIR);
        cameraPicker.setImagePickerCallback(this);
        //cameraPicker.shouldGenerateMetadata(true);
        //cameraPicker.shouldGenerateThumbnails(true);
        pickerPath = cameraPicker.pickImage();
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
            else if(requestCode == Picker.PICK_IMAGE_CAMERA){
                if (cameraPicker == null) {
                    cameraPicker = new CameraImagePicker(this);
                    cameraPicker.setImagePickerCallback(this);
                    cameraPicker.reinitialize(pickerPath);
                }
                cameraPicker.submit(data);
            }
        }
    }

    @Override
    public void onImagesChosen(List<ChosenImage> list) {
        Toast.makeText(SubirFotoActivity.this, "Seleccionado", Toast.LENGTH_SHORT).show();
        activated = true;
        guardar.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button));
        uri = Uri.fromFile(new File(list.get(0).getThumbnailSmallPath()));
        imageView.setImageURI(uri);
        //Glide.with(SubirFotoActivity.this).load(uri).into(imageView);
    }

    @Override
    public void onError(String s) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // You have to save path in case your activity is killed.
        // In such a scenario, you will need to re-initialize the CameraImagePicker
        outState.putString("picker_path", pickerPath);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // After Activity recreate, you need to re-intialize these
        // two values to be able to re-intialize CameraImagePicker
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("picker_path")) {
                pickerPath = savedInstanceState.getString("picker_path");
            }
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

}
