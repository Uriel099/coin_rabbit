package com.example.coinrabit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.santalu.maskara.widget.MaskEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class detailsCardView extends AppCompatActivity {
    String idMovimiento;
    EditText etidMovimiento,etConcepto,etMonto,etDescripcion;
    MaskEditText etFecha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_card_view);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            idMovimiento= extras.getString("idMovimiento");
        }
        etidMovimiento= findViewById(R.id.etIdMovientoConsulta);

        etidMovimiento.setText(idMovimiento);

        etConcepto = findViewById(R.id.etConceptoConsulta);
        etMonto = findViewById(R.id.etMontoConsulta);
        etDescripcion = findViewById(R.id.etObservacionesConsulta);
        etFecha = findViewById(R.id.etFechaConsulta);
        carga_edita();
    }

    private void carga_edita(){
        String url = "https://humanservices21.tk/android/single_move.php?id="+etidMovimiento.getText().toString();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, (response) ->{
            JSONObject jsonObject = null;
            for (int i = 0; i < response.length(); i++){
                try{
                    jsonObject = response.getJSONObject(i);
                    etMonto.setText(jsonObject.getString("Monto"));
                    etConcepto.setText(jsonObject.getString("Concepto"));
                    etDescripcion.setText(jsonObject.getString("Observacion"));
                    etFecha.setText(jsonObject.getString("fecha"));
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

    public void registra_cambios(View v){
        if(valida()){
            String url = "https://humanservices21.tk/android/update.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getApplicationContext(), "Registrado exitosamente", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(detailsCardView.this, MenuActivity.class);
                    startActivity(i);
                    //Navigation.findNavController(v).navigate(R.id.nav_home);
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
                    parametros.put("monto", etMonto.getText().toString());
                    parametros.put("concepto", etConcepto.getText().toString());
                    parametros.put("observacion", etDescripcion.getText().toString());
                    parametros.put("fecha", etFecha.getText().toString());
                    parametros.put("id", etidMovimiento.getText().toString());
                    return parametros;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }else{
            Toast.makeText(getApplicationContext(), "Favor de llenar todos los campos no opcionales", Toast.LENGTH_LONG).show();
        }
    }

    public void elimina(View v){
        String url = "https://humanservices21.tk/android/eliminar_movimiento.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "eliminado exitosamente", Toast.LENGTH_LONG).show();
                Intent i = new Intent(detailsCardView.this, MenuActivity.class);
                startActivity(i);
                //Navigation.findNavController(v).navigate(R.id.nav_home);
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
                parametros.put("id", etidMovimiento.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private boolean valida(){
        boolean res = true;
        String monto = etMonto.getText().toString();
        String conc = etConcepto.getText().toString();
        String obs = etDescripcion.getText().toString();
        String fecha = etFecha.getText().toString();
        if(monto.isEmpty()) res = false;
        if(conc.isEmpty()) res = false;
        if(obs.isEmpty()) res = false;
        if(fecha.isEmpty()) res = false;
        return res;
    }
}