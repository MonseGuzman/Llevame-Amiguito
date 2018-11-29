package com.monse.andrea.proyectofinal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.monse.andrea.proyectofinal.R;
import com.monse.andrea.proyectofinal.clases.Cliente;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ClientesAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Cliente> lista;

    public ClientesAdapter(Context context, int layout, List<Cliente> lista) {
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
        ClientesAdapter.ViewHolder vh;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layout, null);
            vh = new ClientesAdapter.ViewHolder();

            vh.ContactoImageView = (ImageView) convertView.findViewById(R.id.ContactoImageView);
            vh.nombreTextView = (TextView) convertView.findViewById(R.id.nombreTextView);
            vh.domicilioTextView = (TextView) convertView.findViewById(R.id.domicilioTextView);
            vh.destinoTextView = (TextView) convertView.findViewById(R.id.destinoTextView);

            convertView.setTag(vh);
        } else
            vh = (ClientesAdapter.ViewHolder) convertView.getTag();

        Cliente cliente = lista.get(position);


        Picasso.get().load(cliente.getFoto()).into(vh.ContactoImageView);
        vh.nombreTextView.setText(cliente.getNombre());
        vh.domicilioTextView.setText(cliente.getUbicacion());
        vh.destinoTextView.setText(cliente.getDestino());

        return convertView;
    }

    public class ViewHolder {
        ImageView ContactoImageView;
        TextView nombreTextView, domicilioTextView, destinoTextView;
    }
}