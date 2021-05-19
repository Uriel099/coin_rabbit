package com.example.coinrabit;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private int clickNum = 0;
    private TextView tv_label;
    FirebaseFirestore fb = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_label = findViewById(R.id.lb_clickB);
    }

    public void Click(View v){
        clickNum++;
        tv_label.setText("You have clicked the button "+clickNum+" times!");
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Jorgito");
        user.put("last", "Aguilar");
        user.put("born", 2018);
        // Add a new document with a generated ID
        fb.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("FragmentActivity", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("FragmentActivity", "Error adding document", e);
                    }
                });
    }
}