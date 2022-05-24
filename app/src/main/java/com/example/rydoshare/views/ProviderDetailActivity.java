package com.example.rydoshare.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rydoshare.R;
import com.example.rydoshare.views.models.Provider;

public class ProviderDetailActivity  extends AppCompatActivity {
    TextView d1,d2,d3,d4,d5,d6,d7;
    ImageView imageView3;
//    private ProviderListActivity ProviderListActivity;

    protected void onCreate(Bundle savedInstanceState) {
        Log.e("test", "Entered: onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_detail);

//        Provider display = getIntent().;
        imageView3 = findViewById(R.id.imageView3);
        d1 = findViewById(R.id.d1);
        d2 = findViewById(R.id.d2);
        d3 = findViewById(R.id.d3);
        d4 = findViewById(R.id.d4);
        d5 = findViewById(R.id.d5);
        d6 = findViewById(R.id.d6);
        d7 = findViewById(R.id.d7);


        d1.setText(getIntent().getStringExtra("name"));
        d2.setText(getIntent().getStringExtra("phone"));
        d3.setText(getIntent().getStringExtra("origin"));
        d4.setText(getIntent().getStringExtra("destination"));
        d5.setText(getIntent().getStringExtra("date"));
        d6.setText(getIntent().getStringExtra("time"));
        d7.setText(getIntent().getStringExtra("price"));

    }
}