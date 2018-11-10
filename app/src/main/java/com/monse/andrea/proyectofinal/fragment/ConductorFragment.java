package com.monse.andrea.proyectofinal.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceManager;
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

public class ConductorFragment extends Fragment
{
    private EditText DireccionEditText;
    private Button BuscarCondurButton;
    private ListView listitaListView;

    private SharedPreferences preferences;

    public ConductorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_conductor, container, false);
        iniciar(v);

        PreferenceManager.setDefaultValues(getActivity(), R.xml.preference, false);

        hideKeyboardFrom(getActivity(), DireccionEditText);
        BuscarCondurButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

                boolean auto = preferences.getBoolean("auto", false);

                if(!auto)
                    dialogo();
                else
                {
                    //código para la búsqueda
                    Toast.makeText(getActivity(), "Busca", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    private void iniciar(View v)
    {
        DireccionEditText = (EditText)v.findViewById(R.id.DireccionEditText);
        BuscarCondurButton = (Button)v.findViewById(R.id.BuscarCondurButton);
    }

    private void dialogo()
    {
        final AlertDialog.Builder dialogo = new AlertDialog.Builder(getActivity());
        final View v = getLayoutInflater().inflate(R.layout.dialogo, null);

        Button OkButton = (Button)v.findViewById(R.id.OkButton);
        final EditText PlacasEditText = (EditText)v.findViewById(R.id.PlacasEditText);

        dialogo.setView(PlacasEditText);
        dialogo.setView(v);
        final AlertDialog mensaje = dialogo.create();

        OkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(getActivity(), PlacasEditText.getText().toString(), Toast.LENGTH_SHORT).show();
                guardarPreferencias(PlacasEditText.getText().toString());
                mensaje.dismiss();
                hideKeyboardFrom(getActivity(), PlacasEditText);
            }
        });
        mensaje.setCancelable(true);
        mensaje.show();
    }

    private void guardarPreferencias(String placas)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("placas", placas);
        editor.putBoolean("auto", true);
        editor.commit(); // empieza a guardar los put*
        editor.apply(); //guarda todos los cambios aunque no se guarden todos
    }

    //Este método sirve para bajar el teclado
    public static void hideKeyboardFrom(Context context, View view)
    {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /*@Override
    public void onResume() {
        super.onResume();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        boolean auto = preferences.getBoolean("auto", false);
        String placas = preferences.getString("placas", "");
        String color = preferences.getString("color", "");
        String marca = preferences.getString("marca", "");
    }*/
}
