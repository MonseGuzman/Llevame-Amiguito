package com.monse.andrea.proyectofinal.fragment;

import android.app.AlertDialog;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.monse.andrea.proyectofinal.DatosActivity;
import com.monse.andrea.proyectofinal.R;
import com.monse.andrea.proyectofinal.adapters.ConductoresAdapter;
import com.monse.andrea.proyectofinal.application.DelayedProgressDialog;
import com.monse.andrea.proyectofinal.clases.Conductores;

import java.util.ArrayList;
import java.util.List;

public class PedirFragment extends Fragment implements AdapterView.OnItemClickListener
{
    private TextView VamosTextView;
    private ListView listitaListView;
    private List<Conductores> listaConductores;

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
        databaseReference = FirebaseDatabase.getInstance().getReference("cliente");

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        listaConductores = new ArrayList<>();

        VamosTextView.setText(R.string.necesitasIrte);
        VamosTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //código para la búsqueda
                dialogo();
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //Toast.makeText(getActivity(), "funciona", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getActivity(), DatosActivity.class);
        intent.putExtra("foto", listaConductores.get(i).getFoto());
        intent.putExtra("nombre", listaConductores.get(i).getNombre());
        intent.putExtra("destino", listaConductores.get(i).getDestino());
        intent.putExtra("placa", listaConductores.get(i).getPlaca());
        intent.putExtra("color", listaConductores.get(i).getColor());

        startActivity(intent);
    }

    private class consulta extends AsyncTask<Void, Void, Void >
    {
        String ubicacion;
        private List<Conductores> list;
        DelayedProgressDialog progressDialog = new DelayedProgressDialog();

        public consulta(String ubicacion)
        {
            this.ubicacion = ubicacion;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            progressDialog.setCancelable(false);
            progressDialog.show(getActivity().getSupportFragmentManager(), "tag");
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Query query = FirebaseDatabase.getInstance().getReference().child("conductores");

            list = new ArrayList<>();
            query.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                  //  if(dataSnapshot.getValue(Conductores.class).getDestino().contains(ubicacion))
                  //  {
                        Log.d("addChildEventListener", dataSnapshot.getValue().toString());

                        Conductores conductores = new Conductores(
                                dataSnapshot.getValue(Conductores.class).getNombre(), dataSnapshot.getValue(Conductores.class).getUbicacion(),
                                dataSnapshot.getValue(Conductores.class).getDestino(), dataSnapshot.getValue(Conductores.class).getFoto(),
                                dataSnapshot.getValue(Conductores.class).getColor(), dataSnapshot.getValue(Conductores.class).getMarca(),
                                dataSnapshot.getValue(Conductores.class).getPlaca(), dataSnapshot.getValue(Conductores.class).getEstado(),
                                dataSnapshot.getValue(Conductores.class).getViajaCon());

                        list.add(conductores);

                    //}
                }

                private void showData(DataSnapshot dataSnapshot)
                {

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    list = new ArrayList<>();
                    //  if(dataSnapshot.getValue(Conductores.class).getDestino().contains(ubicacion))
                    //  {
                    Log.d("addChildEventListener", dataSnapshot.getValue().toString());

                    Conductores conductores = new Conductores(
                            dataSnapshot.getValue(Conductores.class).getNombre(), dataSnapshot.getValue(Conductores.class).getUbicacion(),
                            dataSnapshot.getValue(Conductores.class).getDestino(), dataSnapshot.getValue(Conductores.class).getFoto(),
                            dataSnapshot.getValue(Conductores.class).getColor(), dataSnapshot.getValue(Conductores.class).getMarca(),
                            dataSnapshot.getValue(Conductores.class).getPlaca(), dataSnapshot.getValue(Conductores.class).getEstado(),
                            dataSnapshot.getValue(Conductores.class).getViajaCon());

                    list.add(conductores);

                    //}
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
            listaConductores = list;
            cargarLista(listaConductores);
            progressDialog.cancel();
        }
    }

    private void cargarLista(List<Conductores> list)
    {
        ConductoresAdapter adapter = new ConductoresAdapter(getActivity(), R.layout.datitos, list);
        listitaListView.setAdapter(adapter);
    }
}