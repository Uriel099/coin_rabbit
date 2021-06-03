package com.example.coinrabit;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovimientosActivity extends AppCompatActivity {
    List<ListElement> elements;
    RecyclerView recyclerView;
    Context context;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    String uaid = firebaseAuth.getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimientos);
        /*String colorIngreso = "#5CB300";
        String colorEgreso = "#FF0000";
        String colorInconoIngreso = "#D1BE00";
        String colorInconoEgreso = "#FF0000";
        Integer iconoEgreso = R.drawable.ic_transit_enterexit_black_24dp;
        Integer iconoIngreso =R.drawable.ic_paid_black_24dp;*/
        elements=new ArrayList<>();

        ListAdapter listAdapter = new ListAdapter(elements,this);
        recyclerView = findViewById(R.id.listaMovimientos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
        context=getApplicationContext();

        llenar();
    }

    private void llenar(){
        String url = "https://humanservices21.tk/android/get_all.php?uaid="+uaid;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, (response) ->{
            JSONObject jsonObject = null;
            //Toast.makeText(getApplicationContext(), response.length(), Toast.LENGTH_SHORT).show();

            String colorIngreso = "#5CB300";
            String colorEgreso = "#FF0000";
            String colorInconoIngreso = "#D1BE00";
            String colorInconoEgreso = "#FF0000";
            Integer iconoEgreso = R.drawable.ic_transit_enterexit_black_24dp;
            Integer iconoIngreso =R.drawable.ic_paid_black_24dp;
            elements=new ArrayList<>();
            for (int i = 0; i < response.length(); i++){
                try{
                    jsonObject = response.getJSONObject(i);
                    if((jsonObject.getString("tipo" ) == "Ingreso") || (jsonObject.getString("tipo" ) == "ingreso") ){
                        elements.add(new ListElement(jsonObject.getString("tipo"),jsonObject.getString("Concepto"),jsonObject.getString("fecha"),jsonObject.getString("Monto"),colorIngreso,colorInconoIngreso,iconoIngreso, jsonObject.getString("ID_II")));
                    }else if(jsonObject.getString("tipo" ) == "Egreso" || jsonObject.getString("tipo" ) == "egreso"){
                        elements.add(new ListElement(jsonObject.getString("tipo"),jsonObject.getString("Concepto"),jsonObject.getString("fecha"),jsonObject.getString("Monto"),colorEgreso,colorInconoEgreso,iconoEgreso, jsonObject.getString("ID_II")));
                    }
                }catch (JSONException e){
                    Toast.makeText(context, "error" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            ListAdapter listAdapter = new ListAdapter(elements,getApplicationContext());
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(listAdapter);
        }, error -> {
            Toast.makeText(getApplicationContext(), "error" + error.toString(), Toast.LENGTH_SHORT).show();
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonArrayRequest);
    }
}