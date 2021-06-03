package com.example.coinrabit.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.coinrabit.Charts;
import com.example.coinrabit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;



public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    TextView leyendaSalud;
    Charts charts = new Charts();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    String uid ;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        TextView textView = root.findViewById(R.id.text_home);
        TextView textViewNombre = root.findViewById(R.id.textViewNombre);

        charts.setPieChart(root.findViewById(R.id.pieChartLayout));
        //charts.createCharts();
        textViewNombre.setText(user.getDisplayName());
        uid = firebaseAuth.getUid();
        carga_ahorro();
        textView.setText("Bienvenido");
        Button btn = root.findViewById(R.id.button2);
        Button btn4 = root.findViewById(R.id.button4);
        leyendaSalud=root.findViewById(R.id.leyendaSalud);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_slideshow);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_gallery);
            }
        });
        //carga_ahorro();
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });



        return root;
    }

    private void carga_ahorro(){
        String url = "https://humanservices21.tk/android/get_ahorro.php?uaid="+uid;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, (response) ->{
            JSONObject jsonObject = null;
            try {
                jsonObject = response.getJSONObject(0);
                int[] saleNew = new int[]{ Integer.parseInt(jsonObject.getString("ingreso")) ,Integer.parseInt(jsonObject.getString("gastos"))};
                charts.setSale(saleNew);
                charts.createCharts();
                analisis(Integer.parseInt(jsonObject.getString("ingreso")),Integer.parseInt(jsonObject.getString("gastos")));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {
            Toast.makeText(this.getContext(), "error" + error.toString(), Toast.LENGTH_SHORT).show();
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
        requestQueue.add(jsonArrayRequest);
    }
    public void formatTextView(String color,String leyenda, TextView tv){
        tv.setText(leyenda);
        tv.setTextColor(Color.parseColor(color));

    }

    public void analisis(int ingresos,int egresos){

        String positiva="Tu Salud Financiera se encuentra bien";
        String negativa="Cuidado, tu salud financiera esta en riesgo";
        String neutral="Estas gastando lo mismo que ganas, ten cuidado";
        if((ingresos-egresos)>0){
            formatTextView("#5CB300",positiva,leyendaSalud);
        }
        else if((ingresos-egresos)<0){
            formatTextView("#FFF44336",negativa,leyendaSalud);
        }
        else if((ingresos-egresos)==0){
            formatTextView("#FFF44336",neutral,leyendaSalud);
        }
    }
}