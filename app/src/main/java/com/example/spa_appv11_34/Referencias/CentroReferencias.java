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

    private static CentroReferencias centroReferencias = null;

    private FirebaseDatabase firebaseDatabase;

    private DatabaseReference myLikes;
    private DatabaseReference likesAnyPost;

    private DatabaseReference myFavs;
    private DatabaseReference myPreferences;

    private DatabaseReference myPost;
    private DatabaseReference postList;

    private DatabaseReference allUsers;
    private DatabaseReference allCenters;
    private DatabaseReference peopleIfollow;
    private DatabaseReference peopleFollowingme;

    private FirebaseStorage firebaseStorage;
    private StorageReference myPostImages;
    private StorageReference myProfileImages;

    private String centro;//llave centro

    synchronized public static CentroReferencias getInstance() {
        if (centroReferencias == null) {
            centroReferencias = new CentroReferencias();
        }
        return centroReferencias;
    }

    public CentroReferencias() {

        centro = FirebaseAuth.getInstance().getCurrentUser().getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        //Activando persistencia
        //firebaseDatabase.setPersistenceEnabled(true);

        myLikes = firebaseDatabase.getReference().child("Mylikes").child(centro);
        likesAnyPost = firebaseDatabase.getReference().child("Mylikes");
        myPostImages = firebaseStorage.getReference().child(centro).child("myPosts");
        myProfileImages = firebaseStorage.getReference().child(centro).child("myProfileImages");

        myFavs = firebaseDatabase.getReference().child("MyFavs").child(centro);
        myPreferences = firebaseDatabase.getReference().child("Preferencias_Usuario").child(centro);

        myPost = firebaseDatabase.getReference().child("MyPosts").child(centro);
        postList = firebaseDatabase.getReference("PostList");

        allUsers = firebaseDatabase.getReference().child("Usuarios");
        allCenters = firebaseDatabase.getReference().child("Centros");
        peopleIfollow = firebaseDatabase.getReference().child("Following").child(centro);

        peopleFollowingme = firebaseDatabase.getReference()
                .child("Followers");//No se añade user como hijo debido a que esta referencia apunta a otro usuario

    }

    public static CentroReferencias getCentroReferencias() {
        return centroReferencias;
    }

    public static void setCentroReferencias(CentroReferencias centroReferencias) {
        CentroReferencias.centroReferencias = centroReferencias;
    }

    public FirebaseDatabase getFirebaseDatabase() {
        return firebaseDatabase;
    }

    public void setFirebaseDatabase(FirebaseDatabase firebaseDatabase) {
        this.firebaseDatabase = firebaseDatabase;
    }

    public DatabaseReference getMyLikes() {
        return myLikes;
    }

    public void setMyLikes(DatabaseReference myLikes) {
        this.myLikes = myLikes;
    }

    public DatabaseReference getLikesAnyPost() {
        return likesAnyPost;
    }

    public void setLikesAnyPost(DatabaseReference likesAnyPost) {
        this.likesAnyPost = likesAnyPost;
    }

    public DatabaseReference getMyFavs() {
        return myFavs;
    }

    public void setMyFavs(DatabaseReference myFavs) {
        this.myFavs = myFavs;
    }

    public DatabaseReference getMyPreferences() {
        return myPreferences;
    }

    public void setMyPreferences(DatabaseReference myPreferences) {
        this.myPreferences = myPreferences;
    }

    public DatabaseReference getMyPost() {
        return myPost;
    }

    public void setMyPost(DatabaseReference myPost) {
        this.myPost = myPost;
    }

    public DatabaseReference getPostList() {
        return postList;
    }

    public void setPostList(DatabaseReference postList) {
        this.postList = postList;
    }

    public DatabaseReference getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(DatabaseReference allUsers) {
        this.allUsers = allUsers;
    }

    public DatabaseReference getPeopleIfollow() {
        return peopleIfollow;
    }

    public void setPeopleIfollow(DatabaseReference peopleIfollow) {
        this.peopleIfollow = peopleIfollow;
    }

    public DatabaseReference getPeopleFollowingme() {
        return peopleFollowingme;
    }

    public void setPeopleFollowingme(DatabaseReference peopleFollowingme) {
        this.peopleFollowingme = peopleFollowingme;
    }

    public FirebaseStorage getFirebaseStorage() {
        return firebaseStorage;
    }

    public void setFirebaseStorage(FirebaseStorage firebaseStorage) {
        this.firebaseStorage = firebaseStorage;
    }

    public StorageReference getMyPostImages() {
        return myPostImages;
    }

    public void setMyPostImages(StorageReference myPostImages) {
        this.myPostImages = myPostImages;
    }

    public StorageReference getMyProfileImages() {
        return myProfileImages;
    }

    public void setMyProfileImages(StorageReference myProfileImages) {
        this.myProfileImages = myProfileImages;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public DatabaseReference getAllCenters() {
        return allCenters;
    }

    public void setAllCenters(DatabaseReference allCenters) {
        this.allCenters = allCenters;
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

    public void userLikesCounter(final IDcountLikes iDcountLikes) {

        myLikes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                long likes = 0;
                List<String> h = new ArrayList<>();

                for (DataSnapshot child_i : dataSnapshot.getChildren()) {
                    likes = likes + child_i.getChildrenCount();
                    h.add(child_i.getKey());
                }

                iDcountLikes.likesCounter(likes, h);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void followersCounter(String userkey, final IDcountFollowers iDcountFollowers) {

        peopleFollowingme.child(userkey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long followers = 0;
                List<String> h = new ArrayList<>();

                if(dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot child_i : dataSnapshot.getChildren()) {
                        followers = followers + 1;
                        h.add(child_i.getKey());
                    }
                }

                iDcountFollowers.followersCounter(followers, h);

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

    public void myPostCounter(final IDcountMyPost iDcountMyPost) {

        myPost.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                long c = 0;
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    c += 1;
                }
                iDcountMyPost.myPostCounter(c);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
