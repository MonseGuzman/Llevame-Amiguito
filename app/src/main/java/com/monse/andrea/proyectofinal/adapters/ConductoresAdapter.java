package com.monse.andrea.proyectofinal.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.monse.andrea.proyectofinal.R;
import com.monse.andrea.proyectofinal.clases.Conductores;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ConductoresAdapter extends BaseAdapter
{
    private Context context;
    private int layout;
    private List<Conductores> lista;

    public ConductoresAdapter(Context context, int layout, List<Conductores> lista) {
        this.context = context;
        this.layout = layout;
        this.lista = lista;
    }

    @Override
    public int getCount() {
       return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ConductoresAdapter.ViewHolder vh;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layout, null);
            vh = new ConductoresAdapter.ViewHolder();

            vh.ContactoImageView = (ImageView) convertView.findViewById(R.id.ContactoImageView);
            vh.nombreTextView = (TextView) convertView.findViewById(R.id.nombreTextView);
            vh.domicilioTextView = (TextView) convertView.findViewById(R.id.domicilioTextView);
            vh.destinoTextView = (TextView) convertView.findViewById(R.id.destinoTextView);

            convertView.setTag(vh);
        } else
            vh = (ConductoresAdapter.ViewHolder) convertView.getTag();

        Conductores conductores = lista.get(position);

        if(!conductores.getFoto().equals(""))
            Picasso.get().load(conductores.getFoto()).into(vh.ContactoImageView);
        vh.nombreTextView.setText(conductores.getNombre());
        vh.domicilioTextView.setText(conductores.getUbicacion());
        vh.destinoTextView.setText(conductores.getDestino());

        return convertView;
    }

    public class ViewHolder
    {
        ImageView ContactoImageView;
        TextView nombreTextView, domicilioTextView, destinoTextView;
    }


}
