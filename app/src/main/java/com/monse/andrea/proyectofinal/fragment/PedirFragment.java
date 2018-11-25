package com.monse.andrea.proyectofinal.fragment;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.monse.andrea.proyectofinal.R;
import com.monse.andrea.proyectofinal.adapters.ConductoresAdapter;
import com.monse.andrea.proyectofinal.clases.Conductores;

import java.util.ArrayList;

public class PedirFragment extends Fragment
{
    private TextView VamosTextView;
    private ListView listitaListView;

    private DatabaseReference databaseReference;
    SharedPreferences preferences;

    public PedirFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pedir, container, false);
        iniciar(v);

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        VamosTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //código para la búsqueda
                dialogo();
            }
        });

        return v;
    }

    private void iniciar(View v)
    {
        VamosTextView = (TextView) v.findViewById(R.id.VamosTextView);
        listitaListView = (ListView)v.findViewById(R.id.listitaListView);
    }

    private void dialogo()
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
                buscar(DireccionDestinoEditText.getText().toString(), DireccionOrigenEditText.getText().toString());

                mensaje.dismiss();
            }
        });
        mensaje.setCancelable(true);
        mensaje.show();
    }


    private void buscar(String destino, String origen)
    {
        String id = preferences.getString("id", "");

        if(!id.isEmpty())
        {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("cliente").child(id);

            databaseReference.child("ubicacion").setValue(origen);
            databaseReference.child("destino").setValue(destino);

            consulta c = new consulta(destino);
            c.execute();
        }
    }

    public class consulta extends AsyncTask<Void, Void, Void >
    {
        String ubicacion;
        ArrayList<Conductores> list = new ArrayList<>();

        public consulta(String ubicacion)
        {
            this.ubicacion = ubicacion;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Query query = FirebaseDatabase.getInstance().getReference().child("conductores");

            query.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    Log.d("addChildEventListener", dataSnapshot.getValue().toString());

                    if(dataSnapshot.getValue(Conductores.class).getDestino().equals(ubicacion))
                    {
                        Conductores conductores = new Conductores(
                                dataSnapshot.getValue(Conductores.class).getNombre(), dataSnapshot.getValue(Conductores.class).getUbicacion(),
                                dataSnapshot.getValue(Conductores.class).getDestino(), dataSnapshot.getValue(Conductores.class).getFoto(),
                                dataSnapshot.getValue(Conductores.class).getColor(), dataSnapshot.getValue(Conductores.class).getMarca(),
                                dataSnapshot.getValue(Conductores.class).getPlaca());

                        list.add(conductores);
                    }
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

            cargarLista(list);
        }
    }

    private void cargarLista(ArrayList<Conductores> list)
    {
        ConductoresAdapter adapter = new ConductoresAdapter(getActivity(), R.layout.datitos, list);
        listitaListView.setAdapter(adapter);
    }
}