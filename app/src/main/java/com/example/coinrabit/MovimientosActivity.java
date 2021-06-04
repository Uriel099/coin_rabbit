package com.example.coinrabit;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

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
    String uid = firebaseAuth.getUid();
    CarouselView carouselView;
    int[] imagenes = {R.drawable.imagen5, R.drawable.imagen1, R.drawable.imagen2, R.drawable.imagen3, R.drawable.imagen4};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimientos);
        elements = new ArrayList<>();

        ListAdapter listAdapter = new ListAdapter(elements, this);
        recyclerView = findViewById(R.id.listaMovimientos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
        context = getApplicationContext();
        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(imagenes.length);
        carouselView.setImageListener(imageListener);
        llenar();
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(imagenes[position]);
        }
    };



   private void llenar(){

        String url = "https://humanservices21.tk/android/get_all.php?uaid=" + uid;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, (response) -> {
            JSONObject jsonObject = null;
            //Toast.makeText(getApplicationContext(), response.length(), Toast.LENGTH_SHORT).show();

            String colorIngreso = "#5CB300";
            String colorEgreso = "#FF0000";
            String colorInconoIngreso = "#D1BE00";
            String colorInconoEgreso = "#FF0000";
            Integer iconoEgreso = R.drawable.ic_transit_enterexit_black_24dp;
            Integer iconoIngreso =R.drawable.ic_paid_black_24dp;
            elements = new ArrayList<>();
            for (int i = 0; i < response.length(); i++) {
                try {
                    jsonObject = response.getJSONObject(i);
                    if(jsonObject.getString("tipo").equals("Ingreso") || jsonObject.getString("tipo").equals("ingreso")){
                        elements.add(new ListElement(jsonObject.getString("tipo"), jsonObject.getString("Concepto"), jsonObject.getString("fecha"), jsonObject.getString("Monto"), colorIngreso, colorInconoIngreso, iconoIngreso, jsonObject.getString("ID_II")));
                    }else{
                        elements.add(new ListElement(jsonObject.getString("tipo"), jsonObject.getString("Concepto"), jsonObject.getString("fecha"), jsonObject.getString("Monto"), colorEgreso, colorInconoEgreso, iconoEgreso, jsonObject.getString("ID_II")));
                    }
                    //elements.add(new ListElement("Egreso","Pago renta mensual","30/05/2021","$3500",colorEgreso,colorInconoEgreso,iconoEgreso,"2"));

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "error" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            //myadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
            //listView.setAdapter(myadapter);
            ListAdapter listAdapter = new ListAdapter(elements,this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(listAdapter);
        }, error -> {
            Toast.makeText(getApplicationContext(), "error" + error.toString(), Toast.LENGTH_SHORT).show();
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonArrayRequest);
    }

}