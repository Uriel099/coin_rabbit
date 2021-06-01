package com.example.coinrabit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class gastos extends AppCompatActivity {
    private ListView listView;
    private List<String> lista = new ArrayList<>();
    private ArrayAdapter<String> myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastos);
        listView = findViewById(R.id.datos_e);
        llenar();
    }

    private void llenar(){
        String url = "https://humanservices21.tk/android/get_data.php?tipo=egreso&uaid=prueba";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, (response) ->{
            JSONObject jsonObject = null;
            //Toast.makeText(getApplicationContext(), response.length(), Toast.LENGTH_SHORT).show();
            for (int i = 0; i < response.length(); i++){
                try{
                    jsonObject = response.getJSONObject(i);
                    //identificador.setText(jsonObject.getString("id"));
                    String texto = "ID: "+jsonObject.getString("ID_II")+" - TIPO: "+jsonObject.getString("tipo")+" - MONTO: "+jsonObject.getString("Monto")+" - CONCEPTO: "+jsonObject.getString("Concepto")+" - OBSERVACION: "+jsonObject.getString("Observacion")+" - FECHA: "+jsonObject.getString("fecha");
                    lista.add(texto);
                    //Toast.makeText(getApplicationContext(), texto, Toast.LENGTH_SHORT).show();
                }catch (JSONException e){
                    Toast.makeText(getApplicationContext(), "error" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            myadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
            listView.setAdapter(myadapter);
        }, error -> {
            Toast.makeText(getApplicationContext(), "error" + error.toString(), Toast.LENGTH_SHORT).show();
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void regresar(View v){
        Intent i = new Intent(gastos.this, principal.class);
        startActivity(i);
    }

    public void registrar(View v){
        Intent i = new Intent(gastos.this, registra_gasto.class);
        startActivity(i);
    }
}