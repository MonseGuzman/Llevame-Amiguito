package com.monse.andrea.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity
{
    private ImageView FotitoImageView;
    private TextView NombreTextView;
    private TextView CorreoTextView;
    private TextView IDTextView;
    private Button CerrarButton;

    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciar();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
                    {
                        Toast.makeText(MainActivity.this, "ocurrio un error que desconozco", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }


    private void iniciar()
    {
        FotitoImageView = (ImageView)findViewById(R.id.FotitoImageView);

        NombreTextView = (TextView) findViewById(R.id.NombreTextView);
        CorreoTextView = (TextView)findViewById(R.id.CorreoTextView);
        IDTextView = (TextView)findViewById(R.id.IDTextView);

        CerrarButton = (Button) findViewById(R.id.CerrarButton);
    }

    private void signOut(View v)
    {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess())
                    inciarSesionNuevamente();
                else
                    Toast.makeText(MainActivity.this, "pequeño error al salir", Toast.LENGTH_SHORT).show();
            }
        });
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //checa si el usuario inicio
        OptionalPendingResult<GoogleSignInResult> option = Auth.GoogleSignInApi.silentSignIn(googleApiClient);

        if(option.isDone()) //si ya iniciamos sesion antes
        {
            GoogleSignInResult result = option.get();
            resultadosObtenidos(result);
        }
        else //si la sesión expira o termina
        {
            option.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    resultadosObtenidos(googleSignInResult);
                }
            });
        }
    }

    private void resultadosObtenidos(GoogleSignInResult result)
    {
        if (result.isSuccess())
        {
            GoogleSignInAccount account = result.getSignInAccount();

            NombreTextView.setText(account.getDisplayName());
            CorreoTextView.setText(account.getEmail());
            IDTextView.setText(account.getId());

            //hacer con picasso
            Log.d("ah", account.getPhotoUrl().toString());
        }
        else
            inciarSesionNuevamente();
    }

    private void inciarSesionNuevamente()
    {
        Intent i = new Intent(this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }


}
