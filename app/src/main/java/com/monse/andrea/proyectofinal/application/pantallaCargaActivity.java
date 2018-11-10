package com.monse.andrea.proyectofinal.application;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.monse.andrea.proyectofinal.AmiguitoActivity;
import com.monse.andrea.proyectofinal.LoginActivity;

public class pantallaCargaActivity extends AppCompatActivity
{
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        configuracionGoogle();

        OptionalPendingResult<GoogleSignInResult> option = Auth.GoogleSignInApi.silentSignIn(googleApiClient);

        Intent amiguito = new Intent(this, AmiguitoActivity.class);
        Intent login = new Intent(this, LoginActivity.class);

        if(option.isDone()) //si ya iniciamos sesion antes
            startActivity(amiguito);
        else //si la sesión expira o termina
            startActivity(login);

        finish();
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
                        Toast.makeText(pantallaCargaActivity.this, "ocurrio un error que desconozco", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }
}
