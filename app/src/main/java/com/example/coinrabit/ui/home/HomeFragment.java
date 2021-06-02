package com.example.coinrabit.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.coinrabit.Charts;
import com.example.coinrabit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.atomic.AtomicInteger;



public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    Charts charts = new Charts();
    FirebaseAuth firebaseAuth;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        TextView textView = root.findViewById(R.id.text_home);
        charts.setPieChart(root.findViewById(R.id.pieChartLayout));
        charts.createCharts();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        textView.setText("Bienvenido");
        //carga_ahorro();
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });



        return root;
    }

    private int carga_ahorro(){
        String url = "https://humanservices21.tk/android/get_ahorro.php?uaid=prueba";
        AtomicInteger num = new AtomicInteger();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, (response) ->{
            JSONObject jsonObject = null;

            for (int i = 0; i < response.length(); i++){
                try{
                    jsonObject = response.getJSONObject(i);
                    //ahorro.setText(jsonObject.getString("ahorro"));
                    //Toast.makeText(this.getContext(), jsonObject.getString("ahorro"), Toast.LENGTH_SHORT).show();
                    num.set(Integer.valueOf(jsonObject.getString("ahorro")));
                }catch (JSONException e){
                    Toast.makeText(this.getContext(), "error" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, error -> {
            //Toast.makeText(getApplicationContext(), "error" + error.toString(), Toast.LENGTH_SHORT).show();
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        requestQueue.add(jsonArrayRequest);
        return num.get();
    }

    public void guardar(View v){
        if(1<2){
            Toast.makeText(this.getContext(), "Comprendou", Toast.LENGTH_LONG).show();


        }

    }



}