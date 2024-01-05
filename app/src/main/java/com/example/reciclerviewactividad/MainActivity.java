package com.example.reciclerviewactividad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.reciclerviewactividad.Modelo.Categoria;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import WebServices.Asynchtask;
import WebServices.WebService;

public class MainActivity extends AppCompatActivity implements Asynchtask {

    private Spinner spCategoria;
    private Spinner spSubCategoria;
    private List<Categoria> categorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spCategoria = findViewById(R.id.spCategoria);
        spSubCategoria = findViewById(R.id.spSubCategoria);

        Map<String, String> datos = new HashMap<>();
        WebService ws = new WebService("https://uealecpeterson.net/turismo/categoria/getlistadoCB",
                datos, MainActivity.this, MainActivity.this);
        ws.execute("GET");
    }

    @Override
    public void processFinish(String result) throws JSONException {
        categorias = new ArrayList<>();
        List<String> subCategorias = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(result);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String id = jsonObject.getString("id");
            String descripcion = jsonObject.getString("descripcion");
            categorias.add(new Categoria(id, descripcion));
        }

        ArrayAdapter<String> categoriaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, obtenerNombresCategorias());
        categoriaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(categoriaAdapter);

        ArrayAdapter<String> subCategoriaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subCategorias);
        subCategoriaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSubCategoria.setAdapter(subCategoriaAdapter);

        spCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String categoriaSeleccionada = spCategoria.getSelectedItem().toString();

                obtenerSubCategorias(categoriaSeleccionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    private void obtenerSubCategorias(String categoria) {
        String url = "https://uealecpeterson.net/turismo/subcategoria/getlistadoCB/" + obtenerIdCategoria(categoria);
        Map<String, String> datos = new HashMap<>();
        WebService ws = new WebService(url, datos, MainActivity.this, new Asynchtask() {
            @Override
            public void processFinish(String result) throws JSONException {
                List<String> subCategorias = new ArrayList<>();

                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String descripcion = jsonObject.getString("descripcion");
                    subCategorias.add(descripcion);
                }
                ArrayAdapter<String> subCategoriaAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, subCategorias);
                subCategoriaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spSubCategoria.setAdapter(subCategoriaAdapter);
            }
        });
        ws.execute("GET");
    }

    private List<String> obtenerNombresCategorias() {
        List<String> nombresCategorias = new ArrayList<>();
        for (Categoria Categoria : categorias) {
            nombresCategorias.add(Categoria.getDescripcion());
        }
        return nombresCategorias;
    }

    private String obtenerIdCategoria(String categoria) {
        for (Categoria Categoria : categorias) {
            if (Categoria.getDescripcion().equals(categoria)) {
                return Categoria.getId();
            }
        }
        return "";
    }
}