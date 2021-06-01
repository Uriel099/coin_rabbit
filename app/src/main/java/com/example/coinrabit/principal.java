package com.example.coinrabit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class principal extends AppCompatActivity {
    TextView ahorro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        ahorro = findViewById(R.id.ahorro);
        carga_ahorro();
    }

    private void carga_ahorro(){
        String url = "https://humanservices21.tk/android/get_ahorro.php?uaid=prueba";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, (response) ->{
            JSONObject jsonObject = null;
            for (int i = 0; i < response.length(); i++){
                try{
                    jsonObject = response.getJSONObject(i);
                    ahorro.setText(jsonObject.getString("ahorro"));
                    //Toast.makeText(getApplicationContext(), jsonObject.getString("ahorro"), Toast.LENGTH_SHORT).show();
                }catch (JSONException e){
                    Toast.makeText(getApplicationContext(), "error" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, error -> {
            Toast.makeText(getApplicationContext(), "error" + error.toString(), Toast.LENGTH_SHORT).show();
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void ingresos(View v){
        Intent i = new Intent(principal.this, ingresos.class);
        startActivity(i);
    }

    public void gastos(View v){
        Intent i = new Intent(principal.this, gastos.class);
        startActivity(i);
    }
}