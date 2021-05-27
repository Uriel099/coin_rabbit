package com.example.coinrabit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class list extends AppCompatActivity {
    List<ListElement> elements;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        init();
    }

    public void init(){
        elements=new ArrayList<>();
        elements.add(new ListElement("Ingreso","Pago avance del proyecto","27/05/2021","$8000","#0000FF"));
        elements.add(new ListElement("Egreso","Pago renta mensual","27/05/2021","$3500","#FF0000"));
        elements.add(new ListElement("Ingreso","Pago nomina quincenal","30/05/2021","$8000","#0000FF"));
        elements.add(new ListElement("Egreso","Pago cena","30/05/2021","$5000","#FF0000"));

        ListAdapter listAdapter = new ListAdapter(elements,this);
        RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);



    }

}