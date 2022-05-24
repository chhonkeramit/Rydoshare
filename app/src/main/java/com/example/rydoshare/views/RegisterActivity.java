package com.example.rydoshare.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rydoshare.R;
import com.example.rydoshare.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity  implements View.OnClickListener{

    private final String TAG = this.getClass().getCanonicalName();
    private ActivityRegisterBinding binding;

    //Step 1
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sign_up);

        this.binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        this.binding.btnRegister.setOnClickListener(this);

        //Step 2
        this.mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.btnRegister: {
                    Log.d(TAG, "onClick: Create Account button Clicked");
                    this.validateData();
                    break;
                }
            }
        }
    }

    private void validateData() {
        Boolean validData = true;
        String email = "";
        String password = "";

        if (this.binding.etRegEmail.getText().toString().isEmpty()) {
            this.binding.etRegEmail.setError("Email Cannot be Empty");
            validData = false;
        } else {
            email = this.binding.etRegEmail.getText().toString();
        }

        if (this.binding.etRegPass.getText().toString().isEmpty()) {
            this.binding.etRegPass.setError("Password Cannot be Empty");
            validData = false;
        } else {

            if (this.binding.confirmpassword.getText().toString().isEmpty()) {
                this.binding.confirmpassword.setError("Confirm Password Cannot be Empty");
                validData = false;
            } else {

                if (!this.binding.etRegPass.getText().toString().equals(this.binding.confirmpassword.getText().toString())) {
                    this.binding.confirmpassword.setError("Both passwords must be same");
                    validData = false;
                } else {
                    password = this.binding.etRegPass.getText().toString();
                }
            }
        }

        if (validData) {
            this.createAccount(email, password);
        } else {
            Toast.makeText(this, "Please provide correct inputs", Toast.LENGTH_SHORT).show();
        }
    }

    private void createAccount(String email, String password) {
        //Step 3
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //save the user/login info on Shared Prefs
                            saveToPrefs(email, password);

                            //create user document on MyUsers collection through UserViewModel
//                            userViewModel.addUser(newUser);

                            //navigate to Main screen
                            goToMain();
                        } else {
                            Log.e(TAG, "onComplete: Failed to create user with email and password" + task.getException() + task.getException().getLocalizedMessage());
                            Toast.makeText(RegisterActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveToPrefs(String email, String password) {
        SharedPreferences prefs = getApplicationContext().getSharedPreferences(getPackageName(), MODE_PRIVATE);
        prefs.edit().putString("USER_EMAIL", email).apply();
        prefs.edit().putString("USER_PASSWORD", password).apply();
    }

    private void goToMain() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }
}
