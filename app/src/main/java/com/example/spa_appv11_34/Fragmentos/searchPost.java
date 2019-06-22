package com.example.spa_appv11_34.Fragmentos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.spa_appv11_34.Holders.miniPostHolder;
import com.example.spa_appv11_34.Clases_Interaccion.CentroPostDatabase;
import com.example.spa_appv11_34.Clases_Interaccion.UsuarioDatabase;
import com.example.spa_appv11_34.Clases_Interaccion.dateObject;
import com.example.spa_appv11_34.R;
import com.example.spa_appv11_34.Referencias.UsuarioReferences;
import com.example.spa_appv11_34.postViewerActivity;
import com.example.spa_appv11_34.userPostsActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link searchPost.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link searchPost#newInstance} factory method to
 * create an instance of this fragment.
 */
public class searchPost extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private RecyclerView rvSearch;
    private FirebaseRecyclerAdapter adapter;
    private long likes;

    private UsuarioReferences usuarioReferences = UsuarioReferences.getInstance();
    private UsuarioDatabase usuarioDatabase = new UsuarioDatabase();

    public searchPost() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment searchPost.
     */
    // TODO: Rename and change types and number of parameters
    public static searchPost newInstance(String param1, String param2) {
        searchPost fragment = new searchPost();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_search_post, container, false);

        rvSearch = vista.findViewById(R.id.rvSearchPostsSearchFrag);
        List<String> userKeys = new ArrayList<>();

        Query query = usuarioReferences.getPostList().limitToLast(4);

        FirebaseRecyclerOptions<CentroPostDatabase> options =
                new FirebaseRecyclerOptions.Builder<CentroPostDatabase>()
                        .setQuery(query, CentroPostDatabase.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<CentroPostDatabase, miniPostHolder>(options) {

            @NonNull
            @Override
            public miniPostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.mini_post_item, parent, false);

                return new miniPostHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final miniPostHolder holder, int i, @NonNull final CentroPostDatabase model) {

                holder.getUserNamePost().setText(model.getUsuario());

                String key_post = model.getUid_post();
                String userKey = key_post.substring(key_post.lastIndexOf("_")+1);

                //Función para extraer foto y nombre del creador del post
                putPersonalData(userKey,holder.getFotoUser(),holder.getUserNamePost());

                usuarioReferences.postLikesCounter(userKey, key_post, new UsuarioReferences.IDcountLikes() {
                    @Override
                    public void likesCounter(long c, List<String> Keys) {
                        likes = c;
                        holder.getLikesPost().setText(String.valueOf(likes));
                    }
                });

                List<String> imagesList = new ArrayList<>();

                imagesList.add(model.getURL_Foto1());
                imagesList.add(model.getURL_Foto2());
                imagesList.add(model.getURL_Foto3());
                imagesList.add(model.getURL_Foto4());

                for(int j = 0; j < imagesList.size(); j++) {
                    if (!imagesList.get(j).equals("NoPhoto")) {
                        TextSliderView textSliderView = new TextSliderView(getContext());
                        textSliderView
                                .image(imagesList.get(j));

                        if(j == 0){//El texto solo se añade en la primera imagen
                            textSliderView.description(model.getTexto());
                        }

                        textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                            @Override
                            public void onSliderClick(BaseSliderView slider) {

                                String[] ActionChoice = getResources().getStringArray(R.array.PostAction);
                                final int SelectedAction = 0;

                                AlertDialog.Builder box = new AlertDialog.Builder(getActivity());
                                box.setTitle("¿Qué deseas hacer?");
                                box.setItems(ActionChoice, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        String userKey = model.getUid_post();//uid del creador del post
                                        userKey = userKey.substring(userKey.lastIndexOf("_")+1);

                                        if(which == 0) {
                                            //Visitar post
                                            Intent intencion = new Intent(getActivity(), postViewerActivity.class);
                                            intencion.putExtra("img1",model.getURL_Foto1());
                                            intencion.putExtra("img2",model.getURL_Foto2());
                                            intencion.putExtra("img3",model.getURL_Foto3());
                                            intencion.putExtra("img4",model.getURL_Foto4());
                                            intencion.putExtra("texto",model.getTexto());
                                            intencion.putExtra("usuario",usuarioDatabase.getNombreUsuario());
                                            intencion.putExtra("foto",usuarioDatabase.getURL_Foto());
                                            startActivity(intencion);
                                        }
                                        else if(which == 1) {
                                            //Visitar perfil
                                            Intent intencion = new Intent(getActivity(), userPostsActivity.class);

                                            intencion.putExtra("clave",userKey);
                                            intencion.putExtra("nombre",usuarioDatabase.getNombre());
                                            intencion.putExtra("apellidos",usuarioDatabase.getApellidos());
                                            intencion.putExtra("usuario",usuarioDatabase.getNombreUsuario());
                                            intencion.putExtra("historia",usuarioDatabase.getHistoria());
                                            intencion.putExtra("foto",usuarioDatabase.getURL_Foto());
                                            startActivity(intencion);
                                        }
                                        else if(which == 2) {
                                            //Me gusta este post
                                            //Escribir en mylikes y en myfavs (con fecha)

                                            dateObject date = new dateObject();

                                            Task task1 = usuarioReferences.getLikesAnyPost()
                                                    .child(userKey)
                                                    .child(model.getUid_post())
                                                    .child(usuarioReferences.getUser())
                                                    .setValue(date);

                                            Task task2 = usuarioReferences.getMyFavs().child(model.getUid_post()).setValue(model);

                                            Task<Void> upload = Tasks.whenAll(task1,task2);

                                            upload.addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(getActivity(), "Ahora te gusta esta publicación", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }
                                    }
                                });
                                box.show();

                            }
                        });

                        holder.getImagePost().addSlider(textSliderView);
                    }
                }
                holder.getImagePost().stopAutoCycle();

            }

        };

        GridLayoutManager l = new GridLayoutManager(getContext(), 2);
        rvSearch.setLayoutManager(l);
        rvSearch.setAdapter(adapter);

        adapter.startListening();

        return vista;
    }


    public void putPersonalData(String userKey, final CircleImageView foto, final TextView nombre){

        final TaskCompletionSource<DataSnapshot> dbSource = new TaskCompletionSource<>();
        usuarioReferences.getAllUsers().child(userKey).addListenerForSingleValueEvent(new ValueEventListener() {
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
                Glide.with(getActivity()).load(usuarioDatabase.getURL_Foto()).into(foto);
                nombre.setText(usuarioDatabase.getNombreUsuario());
            }
        });

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
