package com.monse.andrea.proyectofinal;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.monse.andrea.proyectofinal.clases.Cliente;

import java.util.Random;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificacionReceiver extends BroadcastReceiver {

    String CHANNEL_ID = "mx.edu.itcg.diego.notificacion";
    public static String KEY_TEXT_REPLY = "key_text_reply";
    SharedPreferences preferences;

    @Override
    public void onReceive(final Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();


        databaseReference.child("cliente")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                        for (DataSnapshot child: children)
                        {
                            Cliente cliente = child.getValue(Cliente.class);
                            if(cliente.getViajaCon() == preferences.getString("id", ""))
                            {
                                createNotificationChannel(context);

                                // Create an explicit intent for an Activity in your app
                                Intent intent2 = new Intent(context, AceptarReceiver.class);
                                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent2, 0);


                                // crea la notificacion
                                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                                        .setSmallIcon(android.R.drawable.stat_notify_chat)
                                        .setContentTitle("Solicitud")
                                        .setContentText(cliente.getNombre() + "quiere viajar contigo")
                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                        //.setContentIntent(pendingIntent)
                                        .addAction(android.R.drawable.ic_menu_send, "Aceptar", pendingIntent)
                                        .setAutoCancel(true);

                                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

                                // notificationId is a unique int for each notification that you must define
                                notificationManager.notify(new Random().nextInt(), mBuilder.build());

                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }


    private void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

        CharSequence name = "nombreCanal";
        String description = "descripcionCanal";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);

    }
}
