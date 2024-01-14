package com.example.reciclerviewactividad.Adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.reciclerviewactividad.Modelo.Lugar;
import com.example.reciclerviewactividad.R;

import java.util.List;

public class AdaptadorLugar extends RecyclerView.Adapter<AdaptadorLugar.LugarTuristicoViewHolder> {

    private Context Ctx;
    private List<Lugar> lstLugares;

    public AdaptadorLugar(Context mCtx, List<Lugar> lstLugares2) {
        this.lstLugares = lstLugares2;
        Ctx = mCtx;
    }

    @Override
    public LugarTuristicoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(Ctx);
        View view = inflater.inflate(R.layout.itemlugar, null);
        return new LugarTuristicoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LugarTuristicoViewHolder holder, int position) {

        Lugar Lugar = lstLugares.get(position);

        holder.textViewName.setText(Lugar.getNombre());
        holder.textViewDireccion.setText(Lugar.getDireccion());
        holder.textViewTelefono.setText(Lugar.getTelefono());

        Glide.with(Ctx)
                .load(Lugar.getUrlLOgo())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return lstLugares.size();
    }

    class LugarTuristicoViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewDireccion, textViewTelefono;
        ImageView imageView;

        public LugarTuristicoViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.txtName);
            textViewDireccion = itemView.findViewById(R.id.txtDireccion);
            textViewTelefono = itemView.findViewById(R.id.txtTelefono);
            imageView = itemView.findViewById(R.id.imgLogo);
        }
    }
}
