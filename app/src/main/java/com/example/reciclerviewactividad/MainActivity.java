package com.example.reciclerviewactividad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.reciclerviewactividad.Adaptador.AdaptadorLugar;
import com.example.reciclerviewactividad.Listener.spListener;
import com.example.reciclerviewactividad.Modelo.Categoria;
import com.example.reciclerviewactividad.Modelo.Lugar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import WebServices.Asynchtask;
import WebServices.WebService;



public class MainActivity extends AppCompatActivity implements Asynchtask, AdapterView.OnItemSelectedListener  {

    private Spinner spCategoria;
    private Spinner spSubCategoria;
    private List<Categoria> categorias;

    Map<String, String> datosWS = new HashMap<String, String>();

    RecyclerView rvListaLugares;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spCategoria = findViewById(R.id.spCategoria);
        spSubCategoria = findViewById(R.id.spSubCategoria);
        rvListaLugares = findViewById(R.id.gridlugares);
        spCategoria.setOnItemSelectedListener(this);
        spSubCategoria.setOnItemSelectedListener(this);


        WebService ws= new WebService(
                "https://uealecpeterson.net/turismo/categoria/getlistadoCB",
                datosWS, MainActivity.this,
                new spListener(spCategoria, "id", "descripcion", true) );
        ws.execute("GET");

        rvListaLugares.setHasFixedSize(true);
        rvListaLugares.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void processFinish(String result) throws JSONException {
        ArrayList<Lugar> lstLugares;

        JSONObject JSONlista = new JSONObject(result);
        JSONArray JSONlistaLugares = JSONlista.getJSONArray("data");
        lstLugares = Lugar.JsonObjectsBuild(JSONlistaLugares);

        int numberOfColumns = 2;
        rvListaLugares.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        rvListaLugares.setHasFixedSize(true);

        AdaptadorLugar adaptadorLugaresT = new AdaptadorLugar(this, lstLugares);
        rvListaLugares.setAdapter(adaptadorLugaresT);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        int IDCat=0, IDSubCat=0;
        int IDItemSeleccionado = ((Categoria)parent.getItemAtPosition(position)).getId();
        if(parent == spCategoria) {
            if (IDItemSeleccionado>0){
                IDCat = IDItemSeleccionado;
                WebService ws2= new WebService(
                        "https://uealecpeterson.net/turismo/subcategoria/getlistadoCB/" + IDCat,
                        datosWS, MainActivity.this,
                        new spListener(spSubCategoria, "id", "descripcion", true) );
                ws2.execute("GET");
            }

        }else if (parent == spSubCategoria){
            if (spCategoria.getSelectedItemPosition()!=AdapterView.INVALID_POSITION){
                IDCat = ((Categoria)spCategoria.getSelectedItem()).getId();
                IDSubCat = IDItemSeleccionado;
            }
        }

        WebService ws3 = new WebService(
                "https://uealecpeterson.net/turismo/lugar_turistico/json_getlistadoGridLT/" +
                        IDCat + "/" + IDSubCat,
                datosWS, MainActivity.this, MainActivity.this);
        ws3.execute("GET");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}