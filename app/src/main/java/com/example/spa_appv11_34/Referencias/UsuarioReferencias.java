package com.example.spa_appv11_34.Referencias;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class UsuarioReferencias {

    public interface IDcountFollowing {
        public void followingCounter(long c, List<String> followingKeys);
    }

    public interface IDcountLikes {
        public void likesCounter(long c, List<String> Keys);
    }

    private static UsuarioReferencias usuarioReferencias = null;

    private FirebaseDatabase firebaseDatabase;

    private DatabaseReference likesAnyPost;

    private DatabaseReference myFavs;
    private DatabaseReference myPreferences;

    private DatabaseReference postList;

    private DatabaseReference allCenters;
    private DatabaseReference peopleIfollow;

    private FirebaseStorage firebaseStorage;
    private StorageReference myProfileImages;

    private String user;//llave del usuario

    synchronized public static UsuarioReferencias getInstance() {
        if (usuarioReferencias == null) {
            usuarioReferencias = new UsuarioReferencias();
        }
        return usuarioReferencias;
    }

    public UsuarioReferencias() {

        user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        //Activando persistencia
        //firebaseDatabase.setPersistenceEnabled(true);

        likesAnyPost = firebaseDatabase.getReference().child("Mylikes");
        myProfileImages = firebaseStorage.getReference().child(user).child("myProfileImages");

        myFavs = firebaseDatabase.getReference().child("MyFavs").child(user);
        myPreferences = firebaseDatabase.getReference().child("Preferencias_Usuario").child(user);

        postList = firebaseDatabase.getReference("PostList");

        allCenters = firebaseDatabase.getReference().child("Centros");
        peopleIfollow = firebaseDatabase.getReference().child("Following").child(user);

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public DatabaseReference getPostList() {
        return postList;
    }

    public void setPostList(DatabaseReference postList) {
        this.postList = postList;
    }

    public DatabaseReference getAllCenters() {
        return allCenters;
    }

    public void setAllCenters(DatabaseReference allCenters) {
        this.allCenters = allCenters;
    }

    public DatabaseReference getLikesAnyPost() {
        return likesAnyPost;
    }

    public DatabaseReference getMyFavs() {
        return myFavs;
    }

    public DatabaseReference getMyPreferences() {
        return myPreferences;
    }

    public DatabaseReference getPeopleIfollow() {
        return peopleIfollow;
    }

    public StorageReference getMyProfileImages() {
        return myProfileImages;
    }

    public void setMyProfileImages(StorageReference myProfileImages) {
        this.myProfileImages = myProfileImages;
    }

    public void postLikesCounter(String creator, String keyPost, final IDcountLikes iDcountLikes) {

        likesAnyPost.child(creator).child(keyPost).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                long likes = 0;
                List<String> h = new ArrayList<>();

                if(dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot child_i : dataSnapshot.getChildren()) {
                        likes = likes + 1;
                        h.add(child_i.getKey());
                    }
                }

                iDcountLikes.likesCounter(likes, h);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void followingCounter(final IDcountFollowing iDcountFollowing) {

        peopleIfollow.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long following = 0;
                List<String> h = new ArrayList<>();

                for (DataSnapshot child_i : dataSnapshot.getChildren()) {
                    following += 1;
                    h.add(child_i.getKey());
                }

                iDcountFollowing.followingCounter(following, h);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
