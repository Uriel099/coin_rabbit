package com.example.coinrabit.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coinrabit.ListAdapter;
import com.example.coinrabit.ListElement;
import com.example.coinrabit.R;

import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {
    List<ListElement> elements;
    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        String colorIngreso = "#5CB300";
        String colorEgreso = "#FF0000";
        String colorInconoIngreso = "#D1BE00";
        String colorInconoEgreso = "#FF0000";
        Integer iconoEgreso = R.drawable.ic_transit_enterexit_black_24dp;
        Integer iconoIngreso =R.drawable.ic_paid_black_24dp;
        elements=new ArrayList<>();
        elements.add(new ListElement("Ingreso","Pago avance del proyecto","27/05/2021","$8000",colorIngreso,colorInconoIngreso,iconoIngreso));
        elements.add(new ListElement("Egreso","Pago renta mensual","30/05/2021","$3500",colorEgreso,colorInconoEgreso,iconoEgreso));
        elements.add(new ListElement("Ingreso","Pago avance del proyecto","27/05/2021","$8000",colorIngreso,colorInconoIngreso,iconoIngreso));
        elements.add(new ListElement("Egreso","Pago renta mensual","30/05/2021","$3500",colorEgreso,colorInconoEgreso,iconoEgreso));
        elements.add(new ListElement("Ingreso","Pago avance del proyecto","27/05/2021","$8000",colorIngreso,colorInconoIngreso,iconoIngreso));
        elements.add(new ListElement("Egreso","Pago renta mensual","30/05/2021","$3500",colorEgreso,colorInconoEgreso,iconoEgreso));
        elements.add(new ListElement("Ingreso","Pago avance del proyecto","27/05/2021","$8000",colorIngreso,colorInconoIngreso,iconoIngreso));
        elements.add(new ListElement("Egreso","Pago renta mensual","30/05/2021","$3500",colorEgreso,colorInconoEgreso,iconoEgreso));
        elements.add(new ListElement("Ingreso","Pago avance del proyecto","27/05/2021","$8000",colorIngreso,colorInconoIngreso,iconoIngreso));
        ListAdapter listAdapter = new ListAdapter(elements,this.getContext());
        RecyclerView recyclerView = root.findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(listAdapter);




        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }
}