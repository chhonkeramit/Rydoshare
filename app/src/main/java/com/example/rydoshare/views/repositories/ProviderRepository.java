package com.example.rydoshare.views.repositories;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.rydoshare.views.models.Provider;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProviderRepository {
    private final String TAG = this.getClass().getCanonicalName();
   private final FirebaseFirestore DB;
    private final String COLLECTION_PROVIDERS = "Providers";
    private final String COLLECTION_USERS = "MyUsers";
    private final String FIELD_NAME = "name";
    private final String FIELD_ORIGIN = "origin";
    private final String FIELD_DESTINATION = "destination";
    private final String FIELD_PHONE = "phonenumber";
    private final String FIELD_DATE = "date";
    private final String FIELD_TIME = "time";
    private final String FIELD_PRICE = "price";
    public String loggedInUserEmail = "";
    public MutableLiveData<List<Provider>> allProviders = new MutableLiveData<>();

    public ProviderRepository() {
        DB = FirebaseFirestore.getInstance();
    }

    public void addprovider(Provider newprovider) {
        try{
            Map<String, Object> data = new HashMap<>();
            data.put(FIELD_NAME, newprovider.getName());
            data.put(FIELD_ORIGIN,newprovider.getOrigin());
            data.put(FIELD_DESTINATION,newprovider.getDestination());
            data.put(FIELD_PHONE, newprovider.getPhonenumber());
            data.put(FIELD_DATE, newprovider.getDate());
            data.put(FIELD_TIME,newprovider.getTime());
            data.put(FIELD_PRICE,newprovider.getPrice());

            DB.collection(COLLECTION_PROVIDERS)
                    .add(data)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "onSuccess: Document Added with ID " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "onFailure: Error while creating document " + e.getLocalizedMessage() );
                        }
                    });
        }catch (Exception ex){
            Log.e(TAG, "addFriend: " + ex.getLocalizedMessage());
        }
    }

    public void getAllFriends(){
        try{
            DB.collection(COLLECTION_USERS)
                    .document(loggedInUserEmail)
                    .collection(COLLECTION_PROVIDERS)
                    .orderBy(FIELD_NAME, Query.Direction.ASCENDING)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                            if (error != null){
                                Log.e(TAG, "onEvent: Unable to get document changes " + error );
                                return;
                            }

                            List<Provider> ProviderList = new ArrayList<>();

                            if (snapshot != null){
                                Log.d(TAG, "onEvent: Current Changes " + snapshot.getDocumentChanges());

                                for (DocumentChange documentChange: snapshot.getDocumentChanges()){

                                    Provider currentProvider = documentChange.getDocument().toObject(Provider.class);
                                    currentProvider.setId(documentChange.getDocument().getId());
                                    Log.d(TAG, "onEvent: currentUser : " + currentProvider.toString());

                                    switch (documentChange.getType()){
                                        case ADDED:
                                            ProviderList.add(currentProvider);
                                            break;
                                        case MODIFIED:
                                            //TODO - search in friendList for existing object and replace it with new one - currentFriend
                                            break;
                                        case REMOVED:
                                            //friendList.remove(currentFriend);
                                            //break;
                                    }
                                }
                                Log.d(TAG, "onEvent: currentUser : ");
                                allProviders.postValue(ProviderList);

                            }else{
                                Log.e(TAG, "onEvent: No changes received");
                            }
                        }
                    });


        }catch(Exception ex){
            Log.e(TAG, "getAllFriends: Exception occured " + ex.getLocalizedMessage() );
            Log.e(TAG, String.valueOf(ex.getStackTrace()));
        }
    }
    }


