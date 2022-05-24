package com.example.rydoshare.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.rydoshare.R;
import com.example.rydoshare.databinding.ActivityPassengerMainBinding;
import com.example.rydoshare.databinding.ActivityProviderMainBinding;

public class PassengerMainActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityPassengerMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_passenger_main);

        this.binding = ActivityPassengerMainBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());
        this.binding.btnpassengerlogout.setOnClickListener(this);
        this.binding.btnFindproviders.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view != null) {
            switch (view.getId()) {
                case R.id.btnproviderlogout: {
                    Intent Logout = new Intent(this, LoginActivity.class);
                    startActivity(Logout);
                    break;
                }
                case R.id.btn_findproviders: {
                    Intent Providerlist = new Intent(this, ProviderListActivity.class);
                    startActivity(Providerlist);
                    break;
                }
            }
        }
    }
}