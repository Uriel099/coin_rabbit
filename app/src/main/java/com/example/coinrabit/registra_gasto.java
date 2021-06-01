package com.example.coinrabit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class registra_gasto extends AppCompatActivity {
    EditText monto, concepto, observacion;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registra_gasto);
        monto = findViewById(R.id.montoE);
        concepto = findViewById(R.id.conceptoE);
        observacion = findViewById(R.id.observacionE);
    }

    public void regresar(View v){
        Intent i = new Intent(registra_gasto.this, gastos.class);
        startActivity(i);
    }

    public void registra(View v){
        if(valida()){
            String url = "https://humanservices21.tk/android/save_data.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getApplicationContext(), "Registrado exitosamente", Toast.LENGTH_LONG).show();
                    //Intent i = new Intent(registra_ingreso.this, ingresos.class);
                    //startActivity(i);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "error" + error.toString(), Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                protected Map<String,String> getParams() throws AuthFailureError {
                    Map<String, String> parametros = new HashMap<>();
                    parametros.put("tipo", "egreso");
                    parametros.put("monto", monto.getText().toString());
                    parametros.put("concepto", concepto.getText().toString());
                    parametros.put("observacion", observacion.getText().toString());
                    parametros.put("uaid", "prueba");
                    return parametros;
                }
            };
            requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }else {
            Toast.makeText(getApplicationContext(), "Favor de llenar todos los campos no opcionales", Toast.LENGTH_LONG).show();
        }
    }

    private boolean valida(){
        boolean res = true;
        String mon = monto.getText().toString();
        String conc = concepto.getText().toString();
        if(mon.isEmpty()) res = false;
        if(conc.isEmpty()) res = false;
        return res;
    }
}