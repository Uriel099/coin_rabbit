package com.example.coinrabit;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class detailsCardView extends AppCompatActivity {
    String idMovimiento;
    EditText etidMovimiento,etConcepto,etDescripcion,etMonto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_card_view);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            idMovimiento= extras.getString("idMovimiento");
        }
        etidMovimiento= findViewById(R.id.etIdMoviento);

        etidMovimiento.setText(idMovimiento);
    }
}