package com.monse.andrea.proyectofinal.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monse.andrea.proyectofinal.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PedirFragment extends Fragment {


    public PedirFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pedir, container, false);
    }

}
