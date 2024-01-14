package com.example.reciclerviewactividad.Modelo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Lugar {
    String Nombre;
    String Direccion;
    String Telefono;
    String UrlLogo;

    public Lugar(JSONObject a) throws JSONException {
        Nombre =  a.getString("nombre_lugar").toString() ;

        Direccion =  a.getString("direccion").toString() ;
        Telefono =  a.getString("telefono").toString() ;
        UrlLogo = "https://uealecpeterson.net/turismo/assets/imgs/logos_gifs/" + a.getString("logo").toString() ;
    }

    public static ArrayList<Lugar> JsonObjectsBuild(JSONArray datos) throws JSONException {
        ArrayList<Lugar> listalugares = new ArrayList<>();

        for (int i = 0; i < datos.length(); i++) {
            listalugares.add(new Lugar(datos.getJSONObject(i)));
        }
        return listalugares;
    }


    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getUrlLOgo() {
        return UrlLogo;
    }

    public void setUrlLOgo(String urlLOgo) {
        UrlLogo = urlLOgo;
    }
}
