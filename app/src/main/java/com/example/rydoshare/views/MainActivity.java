package com.example.rydoshare.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.rydoshare.R;
import com.example.rydoshare.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;
    private final String TAG = this.getClass().getCanonicalName();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);

        this.binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        this.binding.btnpassenger.setOnClickListener(this);
        this.binding.btnprovider.setOnClickListener(this);
        this.binding.btnLogout.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        if(view != null){
           switch (view.getId()){
               case R.id.btnprovider: {
                   Log.d(TAG, "onClick: Providermainactivity Button Clicked");
                   Intent Provider = new Intent(this, ProviderMainActivity.class);
                   startActivity(Provider);
                   break;
               }
               case R.id.btnpassenger:{
                   Log.d(TAG, "onClick: passenger activity Clicked");
                   Intent Passenger = new Intent(this, PassengerMainActivity.class);
                   startActivity(Passenger);
                   break;
               }
               case R.id.btnLogout:{
                   Log.d(TAG, "onClick:Logout Clicked");
                   Intent Login = new Intent(this, LoginActivity.class);
                   startActivity(Login);
                   break;
               }

           }
        }
    }
}
