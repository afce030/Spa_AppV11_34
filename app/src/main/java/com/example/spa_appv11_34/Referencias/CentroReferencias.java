package com.example.spa_appv11_34.Referencias;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class CentroReferencias {

    public interface IDcountLikes {
        public void likesCounter(long c, List<String> Keys);
    }

    public interface IDcountFollowers {
        public void followersCounter(long c, List<String> followerKeys);
    }

    public interface IDcountFollowing {
        public void followingCounter(long c, List<String> followingKeys);
    }

    public interface IDcountMyPost {
        public void myPostCounter(long c);
    }

    private static UsuarioReferences usuarioReferences = null;

    private FirebaseDatabase firebaseDatabase;

    private DatabaseReference myLikes;
    private DatabaseReference likesAnyPost;

    private DatabaseReference myFavs;
    private DatabaseReference myPreferences;

    private DatabaseReference myPost;
    private DatabaseReference postList;

    private DatabaseReference allUsers;
    private DatabaseReference peopleIfollow;
    private DatabaseReference peopleFollowingme;

    private FirebaseStorage firebaseStorage;
    private StorageReference myPostImages;
    private StorageReference myProfileImages;

    private String user;


    public CentroReferencias() {

        user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        //Activando persistencia
        //firebaseDatabase.setPersistenceEnabled(true);

        myLikes = firebaseDatabase.getReference().child("Mylikes").child(user);
        likesAnyPost = firebaseDatabase.getReference().child("Mylikes");
        myPostImages = firebaseStorage.getReference().child(user).child("myPosts");
        myProfileImages = firebaseStorage.getReference().child(user).child("myProfileImages");

        myFavs = firebaseDatabase.getReference().child("MyFavs").child(user);
        myPreferences = firebaseDatabase.getReference().child("Preferencias_Usuario").child(user);

        myPost = firebaseDatabase.getReference().child("MyPosts").child(user);
        postList = firebaseDatabase.getReference("PostList");

        allUsers = firebaseDatabase.getReference().child("Usuarios");
        peopleIfollow = firebaseDatabase.getReference().child("Following").child(user);

        peopleFollowingme = firebaseDatabase.getReference()
                .child("Followers");//No se añade user como hijo debido a que esta referencia apunta a otro usuario

    }
}
