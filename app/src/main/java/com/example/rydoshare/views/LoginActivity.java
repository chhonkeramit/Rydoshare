package com.example.rydoshare.views;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rydoshare.R;
import com.example.rydoshare.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.example.rydoshare.views.viewmodels.ProviderViewModel;
import com.example.rydoshare.views.repositories.ProviderRepository;

public class LoginActivity extends AppCompatActivity  implements  View.OnClickListener{

    private final String TAG = this.getClass().getCanonicalName();
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    private SharedPreferences prefs;
    private ProviderViewModel providerViewModel;
    private ProviderRepository providerRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sign_in);

        this.binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        this.binding.btnLogin.setOnClickListener(this);
        this.binding.tvRegisterHere.setOnClickListener(this);

        prefs = getApplicationContext().getSharedPreferences(getPackageName(), MODE_PRIVATE);

        if (prefs.contains("USER_EMAIL")){
            this.binding.etLoginEmail.setText(this.prefs.getString("USER_EMAIL", ""));

        }
        if (prefs.contains("USER_PASSWORD")){
            this.binding.etLoginPass.setText(this.prefs.getString("USER_PASSWORD", ""));
        }

        this.mAuth = FirebaseAuth.getInstance();
        this.providerViewModel = ProviderViewModel.getInstance(this.getApplication());
        this.providerRepository = this.providerViewModel.getproviderrepository();
    }

    @Override
    public void onClick(View view) {
        if (view != null){
            switch (view.getId()){
                case R.id.btnLogin:{
                    Log.d(TAG, "onClick: Sign In Button Clicked");
                    this.validateData();
                    break;
                }
                case R.id.tvRegisterHere:{
                    Log.d(TAG, "onClick: Sign Up Button Clicked");

                    Intent signUpIntent = new Intent(this, RegisterActivity.class);
                    startActivity(signUpIntent);
                    break;
                }
            }
        }
    }

    private void validateData(){
        Boolean validData = true;
        String email = "";
        String password = "";

        if (this.binding.etLoginEmail.getText().toString().isEmpty()){
            this.binding.etLoginEmail.setError("Email Cannot be Empty");
            validData = false;
        }else{
            email = this.binding.etLoginEmail.getText().toString();
        }

        if (this.binding.etLoginPass.getText().toString().isEmpty()){
            this.binding.etLoginPass.setError("Password Cannot be Empty");
            validData = false;
        }else {
            password = this.binding.etLoginPass.getText().toString();
        }

        if (validData){
            this.signIn(email, password);
        }else{
            Toast.makeText(this, "Please provide correct inputs", Toast.LENGTH_SHORT).show();
        }
    }

    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "onComplete: Sign In Successful");
                            saveToPrefs(email, password);
                            //set the loggedInUserEmail in FriendRepository
                            providerRepository.loggedInUserEmail = email;
                            providerViewModel.getallproviders();
                            goToMain();
                        }else{
                            Log.e(TAG, "onComplete: Sign In Failed", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveToPrefs(String email, String password){

        if (this.binding.swtRemember.isChecked()) {
            prefs.edit().putString("USER_EMAIL", email).apply();
            prefs.edit().putString("USER_PASSWORD", password).apply();
        }else{
            if (prefs.contains("USER_EMAIL")){
                prefs.edit().remove("USER_EMAIL").apply();
            }
            if (prefs.contains("USER_PASSWORD")){
                prefs.edit().remove("USER_PASSWORD").apply();
            }
        }
    }

    private void goToMain(){

        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }

}