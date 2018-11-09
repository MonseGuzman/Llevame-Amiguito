package com.monse.andrea.proyectofinal.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.monse.andrea.proyectofinal.R;

public class ConductorFragment extends Fragment
{
    private EditText DireccionEditText;
    private Button BuscarCondurButton;


    public ConductorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_conductor, container, false);
        iniciar(v);

        //prueba
        BuscarCondurButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Conductor", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    private void iniciar(View v)
    {
        DireccionEditText = (EditText)v.findViewById(R.id.DireccionEditText);
        BuscarCondurButton = (Button)v.findViewById(R.id.BuscarCondurButton);
    }

    private void mensaje()
    {

    }

}
