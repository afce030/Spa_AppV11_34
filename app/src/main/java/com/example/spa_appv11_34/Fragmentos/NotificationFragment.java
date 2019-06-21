package com.example.spa_appv11_34.Fragmentos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.spa_appv11_34.R;
import com.example.spa_appv11_34.Regular_Classes.notification;
import com.example.spa_appv11_34.localAdapters.adapterNotifications;
import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.ImageQuality;
import com.fxn.utility.PermUtil;
import com.kbeanie.multipicker.api.CacheLocation;
import com.kbeanie.multipicker.api.ImagePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.callbacks.ImagePickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenImage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.RecursiveAction;

import javax.xml.transform.Result;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NotificationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private RecyclerView rvNotification;
    private adapterNotifications adapter;
    private OnFragmentInteractionListener mListener;

    private Button btPickImageSingle;
    private ImageView image;

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
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
        View vista = inflater.inflate(R.layout.fragment_notification, container, false);
/*
        rvNotification = vista.findViewById(R.id.rvNotifications);

        Spanned not1 = Html.fromHtml( "Ahora <b> London_Barbers </b> te sigue");
        Spanned not2 = Html.fromHtml( "Obtén un 20% de descuento en <b> London_Barbers </b> el día 6 de Junio de 3pm a 6pm");
        Spanned not3 = Html.fromHtml( "Ahora <b> juanfelipepp_17 </b> te sigue");

        List<notification> notificaciones = new ArrayList<>();
        notificaciones.add(new notification(not1,"https://firebasestorage.googleapis.com/v0/b/spa-appv1.appspot.com/o/london-1443715_1280.jpg?alt=media&token=51e9e2a5-e684-4ca6-a121-28406dcc3006"));
        notificaciones.add(new notification(not2,"https://firebasestorage.googleapis.com/v0/b/spa-appv1.appspot.com/o/london-1443715_1280.jpg?alt=media&token=51e9e2a5-e684-4ca6-a121-28406dcc3006"));
        notificaciones.add(new notification(not3,"https://firebasestorage.googleapis.com/v0/b/spa-appv1.appspot.com/o/dc9a06f1-adfb-4c5f-a9d0-86146d2afcea.jpeg?alt=media&token=5bd765e4-51c5-4bd1-87df-d98be9f50acd"));

        adapter = new adapterNotifications(notificaciones, getActivity());
        LinearLayoutManager l = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);

        rvNotification.setLayoutManager(l);
        rvNotification.setAdapter(adapter);
*/

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
