package com.monse.andrea.proyectofinal;

import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.monse.andrea.proyectofinal.adapters.TabsAdapter;
import com.monse.andrea.proyectofinal.application.NotificationHandler;
import com.monse.andrea.proyectofinal.clases.Cliente;
import com.monse.andrea.proyectofinal.clases.Conductores;
import com.monse.andrea.proyectofinal.preferences.PreferenciasActitvity;

public class AmiguitoActivity extends AppCompatActivity
{
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private Button button;
    private NotificationHandler notificationHandler;

    private GoogleApiClient googleApiClient;
    private DatabaseReference databaseReference;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amiguito);

        inicia();
        configuracionGoogle();
        tab();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        notificationHandler = new NotificationHandler(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notification.Builder nb = notificationHandler.createNotification(getString(R.string.app_name),"mensaje", true);
                //se le incrementa antes de mandarse
                notificationHandler.getManager().notify(0, nb.build()); //lanza la notificacion
                notificationHandler.publishNotifiacitionSummaryGroup(true);
            }
        });
    }

    private void configuracionGoogle()
    {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
                    {
                        Toast.makeText(AmiguitoActivity.this, "ocurrio un error que desconozco", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void tab()
    {
        //crea tab y darle una gravedad
        tabLayout.addTab(tabLayout.newTab().setText("Pedir auto"));
        tabLayout.addTab(tabLayout.newTab().setText("Se conductor"));
        //tabLayout.setSelectedTabIndicatorColor(getColor(R.color.primaryTextColor));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        //viewpager
        TabsAdapter adapter = new TabsAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                //cuando se selecciona un tab
                int posicion = tab.getPosition();
                viewPager.setCurrentItem(posicion);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.setOffscreenPageLimit(2); //mantener las tabs en vista
    }

    private void inicia()
    {
        tabLayout = (TabLayout)findViewById(R.id.tabs);
        viewPager = (ViewPager)findViewById(R.id.viewPager);

        button = (Button)findViewById(R.id.button);
    }

    private void iniciarNuevamente()
    {
        preferences.edit().clear().apply();

        Intent i = new Intent(this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    private void alerta()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.acercaDe)
                .setMessage("Integrantes: \n Andrea Monserrat Guzmán López " +
                        "\n Diego Osvaldo Solorio Lara \n Victor Topete Arce" )
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setCancelable(false)
        .show();
    }

    private void verificarConductor()
    {
        OptionalPendingResult<GoogleSignInResult> option = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        if(option.isDone())
        {
            GoogleSignInResult result = option.get();

            if (result.isSuccess())
            {
                GoogleSignInAccount account = result.getSignInAccount();

                boolean auto = preferences.getBoolean("auto", false);
                String placas = preferences.getString("placas", "");
                String color = preferences.getString("color", "");
                String marca = preferences.getString("marca", "");

                if (auto) //conductor
                {
                    Conductores conductores = new Conductores(account.getDisplayName(), "", "", account.getPhotoUrl().toString(), color, marca, placas, "");
                    databaseReference.child("conductores").child(account.getId())
                            .setValue(conductores);

                    guardarLogin(account.getId(), account.getDisplayName(), account.getPhotoUrl().toString(), "", "");
                    Log.d("a", "envio los datos conductor");
                }
                else //cliente
                {
                    Cliente cliente = new Cliente(account.getDisplayName(), "", "", account.getPhotoUrl().toString());
                    databaseReference.child("cliente").child(account.getId())
                            .setValue(cliente);

                    guardarLogin(account.getId(), account.getDisplayName(), account.getPhotoUrl().toString(), "", "", "Disponible");
                    Log.d("a", "envio los datos cliente");
                }
            }
        }
    }

    private void guardarLogin(String id, String nombre, String foto, String origen, String destino, String... estado)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("id", id);
        editor.putString("nombre", nombre);
        editor.putString("foto", foto);
        editor.putString("origen", origen);
        editor.putString("destino", destino);
        editor.putString("estado", String.valueOf(estado)); //revisa luego, lo hice rápido

        editor.commit(); // empieza a guardar los put*
        editor.apply(); //guarda todos los cambios aunque no se guarden todos
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menusito, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.cerrar_menu:
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.isSuccess())
                        {
                            Snackbar.make(findViewById(R.id.amiguitoConstraint), R.string.graciasSesion, Snackbar.LENGTH_SHORT).show();
                            iniciarNuevamente();
                        }
                        else
                            Toast.makeText(AmiguitoActivity.this, "pequeño error al salir", Toast.LENGTH_SHORT).show();
                    }
                });
                FirebaseAuth.getInstance().signOut();
                preferences.edit().clear().apply();
                break;
            case R.id.miAuto_menu:
                Intent intent = new Intent(this, PreferenciasActitvity.class);
                startActivity(intent);
                break;
            case R.id.acerca_menu:
                alerta();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        verificarConductor();
    }
}
