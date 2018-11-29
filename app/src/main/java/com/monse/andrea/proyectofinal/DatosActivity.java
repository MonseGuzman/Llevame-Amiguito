package com.monse.andrea.proyectofinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DatosActivity extends AppCompatActivity
{
    private TextView nombrePersonaTextView;
    private TextView SuDestinoPersonaTextView;
    private TextView DatosConductorTextView;
    private Button PedirButton;
    private ImageView FotoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);

        iniciar();

        Bundle bundle = getIntent().getExtras();
        cargarDatos(bundle);

    }

    private void cargarDatos(Bundle bundle)
    {
        String nombre = bundle.getString("nombre");
        String foto = bundle.getString("foto");
        String destino = bundle.getString("destino");
        String placa = bundle.getString("placa");
        String color = bundle.getString("color");

        nombrePersonaTextView.setText(nombre);
        SuDestinoPersonaTextView.setText(destino);
        DatosConductorTextView.setText("PLACAS : " + placa + "\n"+
                                        "COLOR : " + color + "\n");
        Picasso.get().load(foto).into(FotoImageView);

    }

    private void iniciar()
    {
        nombrePersonaTextView = (TextView)findViewById(R.id.nombrePersonaTextView);
        SuDestinoPersonaTextView = (TextView)findViewById(R.id.SuDestinoPersonaTextView);
        DatosConductorTextView = (TextView)findViewById(R.id.DatosConductorTextView);
        PedirButton = (Button) findViewById(R.id.PedirButton);
        FotoImageView = (ImageView) findViewById(R.id.FotoImageView);
    }
}
