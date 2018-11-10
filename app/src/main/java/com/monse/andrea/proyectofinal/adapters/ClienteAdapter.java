package com.monse.andrea.proyectofinal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.monse.andrea.proyectofinal.R;
import com.squareup.picasso.Picasso;

public class ClienteAdapter extends BaseAdapter
{
    private Context context;
    //private VwPedidos lista[];
    private int layout;

    public ClienteAdapter(Context context, int layout/*, VwPedidos lista[]*/)
    {
        this.context = context;
        this.layout = layout;
        //this.lista = lista;
    }

    @Override
    public int getCount() {
       /* if(lista != null)
            return lista.length;
        else*/ return 0;
    }

    @Override
    public Object getItem(int position) {
        return 0/*lista[position]*/;
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ClienteAdapter.ViewHolder vh;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layout, null);
            vh = new ClienteAdapter.ViewHolder();

            vh.ContactoImageView = (ImageView) convertView.findViewById(R.id.ContactoImageView);
            vh.nombreTextView = (TextView) convertView.findViewById(R.id.nombreTextView);
            vh.domicilioTextView = (TextView) convertView.findViewById(R.id.domicilioTextView);

            convertView.setTag(vh);
        } else
            vh = (ClienteAdapter.ViewHolder) convertView.getTag();

        //VwPedidos pedidos = lista[position];

        //imagen
        Picasso.get().load("url").into(vh.ContactoImageView);

        vh.nombreTextView.setText("");
        vh.domicilioTextView.setText("");

        return convertView;
    }

    public class ViewHolder
    {
        ImageView ContactoImageView;
        TextView nombreTextView, domicilioTextView;
    }
}
