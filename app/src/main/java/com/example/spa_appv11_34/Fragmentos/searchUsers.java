package com.example.spa_appv11_34.Fragmentos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.spa_appv11_34.Holders.UsuariosHolder;
import com.example.spa_appv11_34.Clases_Interaccion.UsuarioDatabase;
import com.example.spa_appv11_34.Clases_Interaccion.dateObject;
import com.example.spa_appv11_34.R;
import com.example.spa_appv11_34.References.UserReferences;
import com.example.spa_appv11_34.userPostsActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link searchUsers.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link searchUsers#newInstance} factory method to
 * create an instance of this fragment.
 */
public class searchUsers extends Fragment {
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

    private UserReferences userReferences = UserReferences.getInstance();
    private UsuarioDatabase usuarioDatabase = new UsuarioDatabase();

    public searchUsers() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment searchUsers.
     */
    // TODO: Rename and change types and number of parameters
    public static searchUsers newInstance(String param1, String param2) {
        searchUsers fragment = new searchUsers();
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
        View vista = inflater.inflate(R.layout.fragment_search_users, container, false);

        rvSearch = vista.findViewById(R.id.rvBuscarUsuarios);
        List<String> userKeys = new ArrayList<>();

        Query query = userReferences.getAllUsers().limitToFirst(4);

        FirebaseRecyclerOptions<UsuarioDatabase> options =
                new FirebaseRecyclerOptions.Builder<UsuarioDatabase>()
                        .setQuery(query, UsuarioDatabase.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<UsuarioDatabase, UsuariosHolder>(options) {

            @NonNull
            @Override
            public UsuariosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.usuarios_item, parent, false);

                return new UsuariosHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final UsuariosHolder holder, int i, @NonNull final UsuarioDatabase model) {

                Glide.with(getActivity()).load(model.getURL_Foto()).into(holder.getFotoPerfilBuscador());
                holder.getUsername().setText(model.getNombreUsuario());

                final String userKey = model.getUserKey();

                userReferences.followersCounter(userKey, new UserReferences.IDcountFollowers() {
                    @Override
                    public void followersCounter(long c, List<String> followerKeys) {
                        likes = c;
                        holder.getFollowersCounter().setText(String.valueOf(likes));
                    }
                });

                holder.getFollow().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dateObject date = new dateObject();

                        Task task1 = userReferences.getPeopleIfollow().child(userKey).setValue(date);

                        Task task2 = userReferences.getPeopleFollowingme().child(userKey)
                                .child(userReferences.getUser()).setValue(date);

                        Task<Void> tasks = Tasks.whenAll(task1,task2);

                        tasks.addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(), "Ahora sigues este perfil", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                holder.getFotoPerfilBuscador().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                });

            }

        };

        GridLayoutManager l = new GridLayoutManager(getContext(), 3);
        rvSearch.setLayoutManager(l);
        rvSearch.setAdapter(adapter);

        adapter.startListening();

        return vista;
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
