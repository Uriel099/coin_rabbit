package com.example.coinrabit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MenuActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
    private ImageView photoImageView;
    private TextView nameTextView;
    private TextView emailTextView;
    private TextView idTextView;

    private GoogleApiClient googleApiClient;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private AppBarConfiguration mAppBarConfiguration;
    Charts chart = new Charts();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

                        super.onCreate(savedInstanceState);
                        setContentView(R.layout.activity_menu_drawer);
                        Toolbar toolbar = findViewById(R.id.toolbar);
                        setSupportActionBar(toolbar);
                        FloatingActionButton fab = findViewById(R.id.fab);
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                logOut();
                            }
                        });
                        DrawerLayout drawer = findViewById(R.id.drawer_layout);
                        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                        photoImageView = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView_navheader);
                        nameTextView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.name_navheader);
                        emailTextView =  (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_navheader);

                        // Passing each menu ID as a set of Ids because each
                        // menu should be considered as top level destinations.
                        mAppBarConfiguration = new AppBarConfiguration.Builder(
                                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                                .setDrawerLayout(drawer)
                                .build();
                        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
                        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
                        NavigationUI.setupWithNavController(navigationView, navController);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,  this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            //Control de que si el usuario ya se autentifico antes vuelva a su sesion, de no ser asi lo envia al login
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //Crea objeto
                FirebaseUser user = firebaseAuth.getCurrentUser();
                //Si el objeto es diferente de null lo envia a su pantalla
                if (user != null) {
                    setUserData(user);
                }
                //Envia al login
                else {
                    goLogInScreen();
                }
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void setUserData(FirebaseUser user) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentDateandTime = simpleDateFormat.format(new Date());

        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("email", user.getEmail());
        userMap.put("name", user.getDisplayName());
        userMap.put("id", user.getUid());
        userMap.put("hourLogin",currentDateandTime);

        fb.collection("Logins")
                .add(userMap)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {

                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("FragmentActivity", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {

                    public void onFailure(@NonNull Exception e) {
                        Log.w("FragmentActivity", "Error adding document", e);
                    }
                });
        System.out.println(user.getDisplayName());
        nameTextView.setText(user.getDisplayName());
        Glide.with(this.getApplicationContext()).load(user.getPhotoUrl()).into(photoImageView);
        emailTextView.setText(user.getEmail());


    }

    @Override
    protected void onStart() {
        super.onStart();
        //Se declara un listener
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }
    //Ir al login
    private void goLogInScreen() {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    public void logOut() {
        firebaseAuth.signOut();
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    goLogInScreen();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.not_close_session, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}