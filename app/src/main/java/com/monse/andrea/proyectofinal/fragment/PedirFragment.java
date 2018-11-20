package com.monse.andrea.proyectofinal.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.monse.andrea.proyectofinal.R;
import com.monse.andrea.proyectofinal.clases.Conductores;

import java.util.List;

public class PedirFragment extends Fragment
{
    private EditText DireccionEditText;
    private Button BuscarCondurButton;
    private ListView listitaListView;

    private DatabaseReference databaseReference;
    private ValueEventListener mPostListener;
    private DatabaseReference mPostReference;

    public PedirFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pedir, container, false);
        iniciar(v);

        hideKeyboardFrom(getActivity(), DireccionEditText);

        mPostReference = FirebaseDatabase.getInstance().getReference().child("conductores");

        BuscarCondurButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //código para la búsqueda
                Toast.makeText(getActivity(), "Busca", Toast.LENGTH_SHORT).show();
                buscar();
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

    private void buscar()
    {
        List<Conductores> lista;
        Query query = FirebaseDatabase.getInstance().getReference().child("conductores");

        //ver mañana
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("ah", dataSnapshot.getValue().toString());

                Log.d("a", dataSnapshot.getValue(Conductores.class).getColor());

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
    }
}