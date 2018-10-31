package com.monse.andrea.proyectofinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener
{
    private GoogleApiClient googleApiClient;
    private static int CODE_LOGIN = 1;

    private SignInButton SignButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        iniciar();

        //para una autentificacion con más datos
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                .requestEmail() //trae el correo
                .build();

        //sirve para sincronizar el ciclo de vida de la activity y google
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //acción del botón
        SignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, CODE_LOGIN);
            }
        });
    }

    //este método se ejecuta cuando ocurre un error en la comexión
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        Toast.makeText(this, "Ops! pequeño error", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CODE_LOGIN)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            resultadoLogin(result);
        }
    }

    private void resultadoLogin(GoogleSignInResult result)
    {
        if(result.isSuccess())
            abrirActivity();
        else
            Toast.makeText(this, "Uy! no se puedo iniciar", Toast.LENGTH_SHORT).show();
    }

    private void abrirActivity()
    {
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    private void iniciar()
    {
        SignButton = (SignInButton)findViewById(R.id.SignButton);
        SignButton.setSize(SignInButton.SIZE_WIDE);
        SignButton.setColorScheme(SignInButton.COLOR_AUTO);
    }
}
