package com.example.rydoshare.views.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.rydoshare.views.models.Provider;
import com.example.rydoshare.views.repositories.ProviderRepository;

import java.util.List;

public class ProviderViewModel extends AndroidViewModel {

    private static ProviderViewModel ourInstance;
    private final ProviderRepository providerRepository = new ProviderRepository();
    public MutableLiveData<List<Provider>> allproviders;

    public ProviderViewModel(Application application) {
       super(application);
    }

    public static ProviderViewModel getInstance(Application application){
        if (ourInstance == null){
            ourInstance = new ProviderViewModel(application);
        }
        return ourInstance;
    }

    public void addProvider(Provider newprovider) {
        this.providerRepository.addprovider(newprovider);
    }
    public ProviderRepository getproviderrepository(){
        return this.providerRepository;
    }

    public void getallproviders(){
        this.providerRepository.getAllFriends();
        this.allproviders = this.providerRepository.allProviders;
    }

}
