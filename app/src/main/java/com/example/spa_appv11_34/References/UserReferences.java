package com.example.spa_appv11_34.References;

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

public class UserReferences {

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

    private static UserReferences userReferences = null;

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

    public UserReferences() {

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
        myPreferences = firebaseDatabase.getReference().child("Preferencias").child(user);

        myPost = firebaseDatabase.getReference().child("MyPosts").child(user);
        postList = firebaseDatabase.getReference("PostList");

        allUsers = firebaseDatabase.getReference().child("Usuarios");
        peopleIfollow = firebaseDatabase.getReference().child("Following").child(user);

        peopleFollowingme = firebaseDatabase.getReference()
                .child("Followers");//No se añade user como hijo debido a que esta referencia apunta a otro usuario

    }

    synchronized public static UserReferences getInstance() {
        if (userReferences == null) {
            userReferences = new UserReferences();
        }
        return userReferences;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public DatabaseReference getMyPost() {
        return myPost;
    }

    public void setMyPost(DatabaseReference myPost) {
        this.myPost = myPost;
    }

    public StorageReference getMyPostImages() {
        return myPostImages;
    }

    public void setMyPostImages(StorageReference myPostImages) {
        this.myPostImages = myPostImages;
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

    public DatabaseReference getMyLikes() {
        return myLikes;
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

    public DatabaseReference getPeopleFollowingme() {
        return peopleFollowingme;
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
