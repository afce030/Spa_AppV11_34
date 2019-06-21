package com.example.spa_appv11_34.Fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.daimajia.slider.library.SliderAdapter;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.spa_appv11_34.PersonalizarPerfil;
import com.example.spa_appv11_34.R;
import com.example.spa_appv11_34.localAdapters.categoryAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private RecyclerView rvCategories;
    private categoryAdapter categoryAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        View vista = inflater.inflate(R.layout.fragment_search, container, false);

        rvCategories = vista.findViewById(R.id.rvCategories);

        List<String> categories = new ArrayList<>();
        categories.add("Barber's Nation");
        categories.add("Hairstyle Creators");
        categories.add("Makeup Lovers");
        categories.add("Nail Artists");
        categories.add("Relax Masters");
        categories.add("Skin Angels");
        categories.add("Tattoo Dreamers");

        List<List<Integer>> imagesList  = new ArrayList<>();

        List<Integer> barber = new ArrayList<>();
        barber.add(R.drawable.barber_1453064_1280);
        barber.add(R.drawable.barber_2165745_1280);
        barber.add(R.drawable.barber_2345701_1280);
        imagesList.add(barber);

        List<Integer> hair = new ArrayList<>();
        hair.add(R.drawable.pink_hair_1450045_1280);
        hair.add(R.drawable.woman_801928_1280);
        hair.add(R.drawable.girl_1246525_1280);
        imagesList.add(hair);

        List<Integer> makeup = new ArrayList<>();
        makeup.add(R.drawable.girl_2366438_1280);
        makeup.add(R.drawable.model_woman_1082219_1280);
        makeup.add(R.drawable.cosmetics_2258300_1280);
        imagesList.add(makeup);

        List<Integer> nail = new ArrayList<>();
        nail.add(R.drawable.nail_art_2688565_1280);
        nail.add(R.drawable.painting_fingernails_635261_1280);
        nail.add(R.drawable.the_hand_3161974_1280);
        imagesList.add(nail);

        List<Integer> relax = new ArrayList<>();
        relax.add(R.drawable.wellness_285587_1280);
        relax.add(R.drawable.massage_1929064_1280);
        relax.add(R.drawable.massage_therapy_1612308_1280);
        imagesList.add(relax);

        List<Integer> skin = new ArrayList<>();
        skin.add(R.drawable.woman_3096664_1280);
        skin.add(R.drawable.girl_2201434_1280);
        skin.add(R.drawable.soap_2333412_1280);
        imagesList.add(skin);

        List<Integer> tattoo = new ArrayList<>();
        tattoo.add(R.drawable.woman_1210061_1280);
        tattoo.add(R.drawable.tattoo_3268988_1280);
        tattoo.add(R.drawable.skull_with_crown_2968613_1280);
        imagesList.add(tattoo);

        categoryAdapter = new categoryAdapter(getContext(),categories,imagesList);
        LinearLayoutManager l = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);

        //Se añade un DividerItemDecoration para aumentar la distancia entre las categorias
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),RecyclerView.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.item_decoration_categories));

        rvCategories.setLayoutManager(l);
        rvCategories.addItemDecoration(dividerItemDecoration);
        rvCategories.setAdapter(categoryAdapter);
        rvCategories.getRecycledViewPool().setMaxRecycledViews(0,0);

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
