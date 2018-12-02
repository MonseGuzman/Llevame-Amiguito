package com.monse.andrea.proyectofinal.application;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import com.monse.andrea.proyectofinal.AmiguitoActivity;
import com.monse.andrea.proyectofinal.R;

public class NotificationHandler extends ContextWrapper
{
    private NotificationManager manager;

    public static final String CHANNEL_HIGH_ID = "1";
    private final String CHANNEL_HIGH_NAME = "HIGH CHANNEL";

    public static final String CHANNEL_LOW_ID = "2";
    private final String CHANNEL_LOW_NAME = "LOW CHANNEL";
    //grupo de notificaciones
    private final int SUMMARY_GROUP_ID = 23;
    private final String SUMARY_GROUP_NAME = "Mis NOTIFICACIONES";

    public NotificationHandler(Context context) {
        super(context);
        createChannels();
    }

    public NotificationManager getManager() {
        if(manager == null)
        {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    private void createChannels()
    {
        if(Build.VERSION.SDK_INT >= 26)
        {
            //CREACION DE NOTIFICACIONES

            NotificationChannel highChannel = new NotificationChannel(CHANNEL_HIGH_ID, CHANNEL_HIGH_NAME, NotificationManager.IMPORTANCE_HIGH);
            //solo en el icono en el toolbar
            NotificationChannel lowChannel = new NotificationChannel(CHANNEL_LOW_ID, CHANNEL_LOW_NAME, NotificationManager.IMPORTANCE_LOW);

            //configuraciones extras
            highChannel.enableLights(true); //enciende luz
            highChannel.setLightColor(Color.BLUE); //cambia el color de la luz
            highChannel.setShowBadge(true); //habilita las notificaciones de puntitos (NO TODOS SON COMPATIBLES)
            highChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC); //sirve para configurar como se muestren las notificaciones si en pantalla, en bloqueo y eso
            //Cambia el sonido de la notifiacion
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            highChannel.setSound(defaultSoundUri, null);


            lowChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE); //sirve para configurar como se muestren las notificaciones si en pantalla, en bloqueo y eso

            getManager().createNotificationChannel(highChannel);
            getManager().createNotificationChannel(lowChannel);
        }
    }

    public Notification.Builder createNotification(String titulo, String mensaje, boolean importancia)
    {
        if(Build.VERSION.SDK_INT >= 26)
        {
            if(importancia)
                return this.createNotificationWithChannel(titulo, mensaje, CHANNEL_HIGH_ID);

            return this.createNotificationWithChannel(titulo, mensaje, CHANNEL_LOW_ID);
        }
        return this.createNotificationWithoutChannel(titulo, mensaje);
    }

    public void mensajedos()
    {
        Toast.makeText(this, "prueba", Toast.LENGTH_SHORT).show();
    }

    private Notification.Builder createNotificationWithChannel(String titulo, String mensaje, String idImportancia)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            Intent intent = new Intent(this, AmiguitoActivity.class);
            intent.putExtra("tittle", titulo);
            intent.putExtra("message", mensaje);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            //se abre cuando clickeas una notifiacion
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);


            Notification.Action action = new Notification.Action.Builder(
                    Icon.createWithResource(this, android.R.drawable.ic_menu_add),
                    "Aceptar",
                    pendingIntent).build();

            Notification.Action action2 = new Notification.Action.Builder(
                    Icon.createWithResource(this, android.R.drawable.ic_media_rew),
                    "Rechazar",
                    pendingIntent
            ).build();

            return  new Notification.Builder(getApplicationContext(), idImportancia)
                    .setContentTitle(titulo) //ponerle el titulo
                    .setContentText(mensaje) //ponerle el mensaje
                    .setSmallIcon(R.drawable.ic_car) //icono de la notifiacion
                    .setColor(getColor(R.color.colorPrimary))
                    .addAction(action)
                    .addAction(action2)
                    .setGroup(SUMARY_GROUP_NAME) //nombre del grupo de notifiaciones (pertenece a este)
                    .setContentIntent(pendingIntent) //abre la actividad
                    .setAutoCancel(true); //para borrar notificacion
        }
        return null;
    }

    private Notification.Builder createNotificationWithoutChannel(String titulo, String mensaje)
    {
        return  new Notification.Builder(getApplicationContext())
                .setContentTitle(titulo) //ponerle el titulo
                .setContentText(mensaje) //ponerle el mensaje
                .setSmallIcon(R.drawable.ic_car) //icono de la notifiacion
                .setAutoCancel(true); //para borrar notificacion
    }

    public void publishNotifiacitionSummaryGroup(boolean isHighImportance)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = (isHighImportance) ? CHANNEL_HIGH_ID : CHANNEL_LOW_ID;
            Notification sumarryNotification = new Notification.Builder(getApplicationContext(), channelId)
                    .setGroup(SUMARY_GROUP_NAME)
                    .setGroupSummary(true)
                    .build();

            getManager().notify(SUMMARY_GROUP_ID, sumarryNotification);
        }
    }
}
