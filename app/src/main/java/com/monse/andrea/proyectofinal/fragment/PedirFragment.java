package com.monse.andrea.proyectofinal.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.monse.andrea.proyectofinal.R;

public class PedirFragment extends Fragment
{
    private EditText DireccionEditText;
    private Button BuscarCondurButton;
    private ListView listitaListView;

    public PedirFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pedir, container, false);
        iniciar(v);

        hideKeyboardFrom(getActivity(), DireccionEditText);

        BuscarCondurButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //código para la búsqueda
                Toast.makeText(getActivity(), "Busca", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    private void iniciar(View v)
    {
        DireccionEditText = (EditText)v.findViewById(R.id.DireccionEditText);
        BuscarCondurButton = (Button)v.findViewById(R.id.BuscarCondurButton);
    }

    //Este método sirve para bajar el teclado
    public static void hideKeyboardFrom(Context context, View view)
    {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}