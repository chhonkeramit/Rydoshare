package com.example.rydoshare.views;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rydoshare.R;
import com.example.rydoshare.databinding.ActivityProviderListBinding;
import com.example.rydoshare.views.models.Provider;
import com.example.rydoshare.views.viewmodels.ProviderViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class ProviderListActivity extends AppCompatActivity {

    //private ActivityProviderListBinding binding;
    private ProviderViewModel providerViewModel;
    private final String TAG = this.getClass().getCanonicalName();
    //ArrayList<Provider> providerlist = new ArrayList<>();

    RecyclerView recview;
    ArrayList<Provider> datalist;
    MyAdapter adapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_provider_list);

        recview=(RecyclerView)findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));
        datalist = new ArrayList<>();
        adapter = new MyAdapter(datalist, new MyAdapter.ItemClickListener() {
            @Override
            public void onItemClick(Provider provider) {
            showToast(provider.getName()+"Clicked!");
//                startActivity(new Intent(ProviderListActivity.this,ProviderDetailActivity.class));

                Intent intent = new Intent(ProviderListActivity.this, ProviderDetailActivity.class);
                intent.putExtra("name",provider.getName());
                String phone = String.valueOf(provider.getPhonenumber());
                intent.putExtra("phone",phone);
                intent.putExtra("origin",provider.getOrigin());
                intent.putExtra("destination",provider.getDestination());
                intent.putExtra("date",provider.getDate());
                intent.putExtra("time",provider.getTime());
                String price = String.valueOf(provider.getPrice());
                intent.putExtra("price",price);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
        recview.setAdapter(adapter);

       // this.binding = ActivityProviderListBinding.inflate(getLayoutInflater());
        // setContentView(this.binding.getRoot());

//        this.providerViewModel = ProviderViewModel.getInstance(this.getApplication());

        //display all the friends
//        this.providerViewModel.allproviders.observe(this, new Observer<List<Provider>>() {
//            @Override
//            public void onChanged(List<Provider> providers) {
//                if (providers != null){
//                    for(Provider provider : providers){
//                        Log.d(TAG, "onChanged: " + provider.toString());
//                       providerlist.add(provider);
//                        Log.d(TAG, "new provider: " + providerlist);
//                        //optionally, add it in local array list, display it in List or RecyclerView
//                    }
//                    adapter.notifyDataSetChanged();
//                }
//            }
//        });

        db=FirebaseFirestore.getInstance();
        db.collection("Providers").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list)
                        {
                            Provider obj = d.toObject(Provider.class);
                            Log.d(TAG,"obj" + obj);
                            datalist.add(obj);
                            Log.d(TAG,"datalist" + datalist);
                        }
                      adapter.notifyDataSetChanged();
                    }
                });

//        RecyclerView recyclerView = findViewById(R.id.recyclerview);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new MyAdapter(this, newprovider);
//        recyclerView.setAdapter(adapter);


        //now if we click on any item of listview
//        recview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                Toast.makeText(ProviderListActivity.this,"item clicked",Toast.LENGTH_SHORT).show();
//                Intent detailIntent = new Intent(ProviderListActivity.this, ProviderDetailActivity.class);
//                Log.d("DETAIL", "HAVE CLICKED TO DETAIL ACTIVITY");
//
//                startActivity(detailIntent);
//            }
//        });

    }
 private void showToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
 }
}