package com.example.reciclerviewactividad.Listener;

import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.reciclerviewactividad.Modelo.Categoria;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import WebServices.Asynchtask;

public class spListener implements Asynchtask {

    Spinner cb;
    String campoID, campoDesc;
    Boolean addAllItem;
    public spListener(Spinner cb, String campoID, String campoDesc, Boolean addAllItem) {
        this.cb = cb;
        this.campoID = campoID;
        this.campoDesc = campoDesc;
        this.addAllItem = addAllItem;
    }

    @Override
    public void processFinish(String result) throws JSONException {
        ArrayList<Categoria> datos = new ArrayList<Categoria>();
        if(addAllItem) datos.add( new Categoria(0, "Seleccione una opción"));
        JSONArray JSONlista =  new JSONArray(result);
        for(int i=0; i< JSONlista.length();i++){
            JSONObject lugar=  JSONlista.getJSONObject(i);
            datos.add( new Categoria(lugar.getInt(campoID),
                    lugar.getString(campoDesc)));
        }

        ArrayAdapter<Categoria> adaptador =
                new ArrayAdapter<Categoria>(cb.getContext(),
                        android.R.layout.simple_spinner_item, datos);


        adaptador.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        cb.setAdapter(adaptador);
    }
}