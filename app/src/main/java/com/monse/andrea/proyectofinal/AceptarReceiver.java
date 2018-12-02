package com.monse.andrea.proyectofinal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AceptarReceiver extends BroadcastReceiver {

    SharedPreferences preferences;
    @Override
    public void onReceive(Context context, Intent intent) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String id = preferences.getString("id", "");
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("conductores").child(id).child("viajaCon").setValue(id);
    }
}
