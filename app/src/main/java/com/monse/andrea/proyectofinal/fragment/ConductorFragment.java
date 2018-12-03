package com.monse.andrea.proyectofinal.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.monse.andrea.proyectofinal.DatosActivity;
import com.monse.andrea.proyectofinal.R;
import com.monse.andrea.proyectofinal.adapters.ClientesAdapter;
import com.monse.andrea.proyectofinal.adapters.ConductoresAdapter;
import com.monse.andrea.proyectofinal.application.DelayedProgressDialog;
import com.monse.andrea.proyectofinal.clases.Cliente;
import com.monse.andrea.proyectofinal.clases.Conductores;

import java.util.ArrayList;
import java.util.List;

public class ConductorFragment extends Fragment implements AdapterView.OnItemClickListener
{
    private ListView listitaListView;
    private TextView VamosTextView;

    private SharedPreferences preferences;
    private DatabaseReference databaseReference;
    private List<Cliente> listaClientes;

    public ConductorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_conductor, container, false);
        iniciar(v);

        PreferenceManager.setDefaultValues(getActivity(), R.xml.preference, false);

        listaClientes = new ArrayList<Cliente>();

        VamosTextView.setText(R.string.necesitasLlevar);
        VamosTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

                boolean auto = preferences.getBoolean("auto", false);

                if(!auto)
                    dialogo();
                else
                    buscar();
            }
        });

        listitaListView.setOnItemClickListener(this);

        return v;
    }

    private void iniciar(View v)
    {
        VamosTextView = (TextView) v.findViewById(R.id.VamosTextView);
        listitaListView = (ListView)v.findViewById(R.id.listitaListView);
    }

    private void buscar()
    {
        final AlertDialog.Builder dialogo = new AlertDialog.Builder(getActivity());
        final View v = getLayoutInflater().inflate(R.layout.busca_personas, null);

        Button OkButton = (Button)v.findViewById(R.id.BuscarConductorButton);
        final EditText DireccionOrigenEditText = (EditText)v.findViewById(R.id.DireccionOrigenEditText);
        final EditText DireccionDestinoEditText = (EditText)v.findViewById(R.id.DireccionDestinoEditText);

        dialogo.setView(DireccionDestinoEditText);
        dialogo.setView(DireccionOrigenEditText);
        dialogo.setView(v);
        final AlertDialog mensaje = dialogo.create();

        OkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String id = preferences.getString("id", "");

                if(!id.isEmpty())
                {
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("conductores").child(id);

                    databaseReference.child("ubicacion").setValue(DireccionDestinoEditText.getText().toString());
                    databaseReference.child("destino").setValue(DireccionOrigenEditText.getText().toString());

                    consulta c = new consulta();
                    c.execute();
                }
                mensaje.dismiss();
            }
        });
        mensaje.setCancelable(true);
        mensaje.show();
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
                if(!PlacasEditText.getText().toString().isEmpty())
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

    private class consulta extends AsyncTask<Void, Void, Void >
    {
        private List<Cliente> list = new ArrayList<Cliente>();

        DelayedProgressDialog progressDialog = new DelayedProgressDialog();

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            progressDialog.setCancelable(false);
            progressDialog.show(getActivity().getSupportFragmentManager(), "tag");
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Query query = FirebaseDatabase.getInstance().getReference().child("cliente");

            query.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
                {

                    Log.d("addChildEventListener", dataSnapshot.getValue().toString());
                    Cliente cliente = new Cliente(
                                dataSnapshot.getValue(Cliente.class).getNombre(), dataSnapshot.getValue(Cliente.class).getUbicacion(),
                                dataSnapshot.getValue(Cliente.class).getDestino(), dataSnapshot.getValue(Cliente.class).getFoto(),
                                dataSnapshot.getValue(Cliente.class).getViajaCon());

                        list.add(cliente);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listaClientes = list;
            cargarLista(list);
            progressDialog.cancel();
        }
    }

    private void cargarLista(List<Cliente> list)
    {
        ClientesAdapter adapter = new ClientesAdapter(getActivity(), R.layout.datitos, list);
        listitaListView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //Toast.makeText(getActivity(), "funciona", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getActivity(), DatosActivity.class);
        intent.putExtra("foto", listaClientes.get(i).getFoto());
        intent.putExtra("nombre", listaClientes.get(i).getNombre());
        intent.putExtra("destino", listaClientes.get(i).getDestino());
        intent.putExtra("ubicacion", listaClientes.get(i).getUbicacion());
        intent.putExtra("conductor", true);

        startActivity(intent);
    }

}
