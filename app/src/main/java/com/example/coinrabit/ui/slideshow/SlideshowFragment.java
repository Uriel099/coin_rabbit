package com.example.coinrabit.ui.slideshow;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.coinrabit.ListAdapter;
import com.example.coinrabit.ListElement;
import com.example.coinrabit.R;
import com.spark.submitbutton.SubmitButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SlideshowFragment extends Fragment {
    List<ListElement> elements;
    RecyclerView recyclerView;
    EditText etConcepto,etMonto,etObservaciones,etFecha;
    Context context;

    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        /*String colorIngreso = "#5CB300";
        String colorEgreso = "#FF0000";
        String colorInconoIngreso = "#D1BE00";
        String colorInconoEgreso = "#FF0000";
        Integer iconoEgreso = R.drawable.ic_transit_enterexit_black_24dp;
        Integer iconoIngreso =R.drawable.ic_paid_black_24dp;
        elements=new ArrayList<>();
        //elements.add(new ListElement("Ingreso","Pago avance del proyecto","27/05/2021","$8000",colorIngreso,colorInconoIngreso,iconoIngreso));
        //elements.add(new ListElement("Egreso","Pago renta mensual","30/05/2021","$3500",colorEgreso,colorInconoEgreso,iconoEgreso));

        ListAdapter listAdapter = new ListAdapter(elements,this.getContext());
        RecyclerView recyclerView = root.findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(listAdapter);*/

        recyclerView = root.findViewById(R.id.listRecyclerView);
        etConcepto= root.findViewById(R.id.etConceptoIngreso);
        etMonto=root.findViewById(R.id.etMontoIngreso);
        etObservaciones=root.findViewById(R.id.etObservacionesIngreso);
        etFecha=root.findViewById(R.id.etFechaIngreso);
        context=root.getContext();

        SubmitButton btn = root.findViewById(R.id.btnSubmitIngreso);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://humanservices21.tk/android/save_data.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(getApplicationContext(), "Registrado exitosamente", Toast.LENGTH_LONG).show();
                        //Intent i = new Intent(registra_ingreso.this, ingresos.class);
                        //startActivity(i);
                        llenar();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getApplicationContext(), "error" + error.toString(), Toast.LENGTH_LONG).show();

                    }
                }){
                    @Override
                    protected Map<String,String> getParams() throws AuthFailureError {
                        Map<String, String> parametros = new HashMap<>();
                        parametros.put("tipo", "Ingreso");
                        parametros.put("monto", etMonto.getText().toString());
                        parametros.put("concepto", etConcepto.getText().toString());
                        parametros.put("observacion", etObservaciones.getText().toString());
                        parametros.put("uaid", "rZCu37AZXGhgspwSfIjo1Ab6Nme2");
                        return parametros;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);

            }
        });


        llenar();


        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }



    private void llenar(){
        String url = "https://humanservices21.tk/android/get_data.php?tipo=ingreso&uaid=rZCu37AZXGhgspwSfIjo1Ab6Nme2";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, (response) ->{
            JSONObject jsonObject = null;
            //Toast.makeText(getApplicationContext(), response.length(), Toast.LENGTH_SHORT).show();

            String colorIngreso = "#5CB300";
            //String colorEgreso = "#FF0000";
            String colorInconoIngreso = "#D1BE00";
            //String colorInconoEgreso = "#FF0000";
            //Integer iconoEgreso = R.drawable.ic_transit_enterexit_black_24dp;
            Integer iconoIngreso =R.drawable.ic_paid_black_24dp;
            elements=new ArrayList<>();
            for (int i = 0; i < response.length(); i++){
                try{
                    jsonObject = response.getJSONObject(i);
                    elements.add(new ListElement(jsonObject.getString("tipo"),jsonObject.getString("Concepto"),jsonObject.getString("fecha"),jsonObject.getString("Monto"),colorIngreso,colorInconoIngreso,iconoIngreso, jsonObject.getString("ID_II")));
                    //elements.add(new ListElement("Egreso","Pago renta mensual","30/05/2021","$3500",colorEgreso,colorInconoEgreso,iconoEgreso,"2"));

                }catch (JSONException e){
                    Toast.makeText(this.getContext(), "error" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            //myadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
            //listView.setAdapter(myadapter);
            ListAdapter listAdapter = new ListAdapter(elements,this.getContext());
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            recyclerView.setAdapter(listAdapter);
        }, error -> {
            Toast.makeText(this.getContext(), "error" + error.toString(), Toast.LENGTH_SHORT).show();
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        requestQueue.add(jsonArrayRequest);
    }
}