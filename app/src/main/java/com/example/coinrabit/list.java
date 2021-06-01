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
        String colorIngreso = "#5CB300";
        String colorEgreso = "#FF0000";
        String colorInconoIngreso = "#D1BE00";
        String colorInconoEgreso = "#FF0000";
        Integer iconoEgreso =R.drawable.ic_transit_enterexit_black_24dp;
        Integer iconoIngreso =R.drawable.ic_paid_black_24dp;


        elements=new ArrayList<>();
        elements.add(new ListElement("Ingreso","Pago avance del proyecto","27/05/2021","$8000",colorIngreso,colorInconoIngreso,iconoIngreso));
        elements.add(new ListElement("Egreso","Pago renta mensual","30/05/2021","$3500",colorEgreso,colorInconoEgreso,iconoEgreso));


        ListAdapter listAdapter = new ListAdapter(elements,this);
        RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);



    }

}