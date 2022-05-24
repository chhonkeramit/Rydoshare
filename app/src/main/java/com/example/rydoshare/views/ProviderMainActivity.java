package com.example.rydoshare.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.rydoshare.R;
import com.example.rydoshare.databinding.ActivityProviderMainBinding;
import com.example.rydoshare.views.models.Provider;
import com.example.rydoshare.views.viewmodels.ProviderViewModel;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ProviderMainActivity extends AppCompatActivity  implements View.OnClickListener {
    private final String TAG = this.getClass().getCanonicalName();
    private ActivityProviderMainBinding binding;
    private ProviderViewModel providerviewmodel;
    private Provider newprovider;

    private final int GALLERY_PICTURE_REQUEST_CODE = 102;
    private final int CAMERA_PERMISSION_REQUEST_CODE = 103;
    private final String[] permissionsArray = new String[]{
            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_provider_main);

        this.binding = ActivityProviderMainBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        this.binding.btnSave.setOnClickListener(this);
        this.binding.btnproviderlogout.setOnClickListener(this);
        this.binding.editDate.setFocusable(false);
        this.binding.editDate.setOnClickListener(this);

        this.providerviewmodel = providerviewmodel.getInstance(this.getApplication());
        this.newprovider = new Provider();

        this.binding.btnGetPicture.setOnClickListener(this);

        this.checkPermissions();
    }

    @Override
    public void onClick(View view) {

        if (view != null) {
            switch (view.getId()) {
                case R.id.btn_save: {
                    Log.d(TAG, "onClick: save friend " + this.newprovider.toString());
                     if (this.validateData()) {

                         String price = this.binding.editPrice.getText().toString();
                         String number =  this.binding.editMobilenumber.getText().toString();

                    this.newprovider.setName(this.binding.editName.getText().toString());
                    this.newprovider.setOrigin(this.binding.editOrigin.getText().toString());
                    this.newprovider.setDestination(this.binding.editDestination.getText().toString());
                this.newprovider.setPhonenumber(Long.parseLong(number));
               this.newprovider.setPrice(Float.parseFloat(price));
              this.newprovider.setTime(this.binding.editTime.getText().toString());

                    Log.d(TAG, "onClick: New Friend " + this.newprovider.toString());
                    this.providerviewmodel.addProvider(this.newprovider);

//                    Intent Passenger = new Intent(this, PassengerMainActivity.class);
//                    startActivity(Passenger);
                         Toast.makeText(this, "Data is Saved", Toast.LENGTH_SHORT).show();


                     }
                    break;
                }
                case R.id.edit_date: {
                    chooseDate();
                    break;
                }
                case R.id.btnproviderlogout:{
                    Intent Logout = new Intent(this, LoginActivity.class);
                    startActivity(Logout);
                    break;
                }
                case R.id.btn_get_picture: {

                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

                    String[] actionItems = new String[]{getString(R.string.take_picture), getString(R.string.choose_from_gallery), getString(R.string.cancel)};
                    alertBuilder.setTitle("Select Profile Picture");

                    alertBuilder.setItems(actionItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int index) {
                            switch (index){
                                case 0:
                                    Log.d(TAG, "Taking new picture");
                                    openCamera();
                                    break;
                                case 1:
                                    Log.d(TAG, "Getting Picture from Gallery");
                                    selectFromGallery();
                                    break;
                                case 2:
                                    dialogInterface.dismiss();
                                default:
                                    dialogInterface.dismiss();
                            }
                        }
                    });

                    alertBuilder.show();
                }

                    break;
                }
            }

        }
    private Boolean validateData(){
        Boolean validData = true;

        if (this.binding.editName.getText().toString().isEmpty()){
            this.binding.editName.setError("Name cannot be empty");
            validData = false;
        }
        if (this.binding.editOrigin.getText().toString().isEmpty()){
            this.binding.editOrigin.setError("Origin cannot be empty");
            validData = false;
        }
        if (this.binding.editDestination.getText().toString().isEmpty()){
            this.binding.editDestination.setError("Destination cannot be empty");
            validData = false;
        }
        if (this.binding.editMobilenumber.getText().toString().isEmpty()){
            this.binding.editMobilenumber.setError("Mobile Number cannot be empty");
            validData = false;
        }
        if (this.binding.editTime.getText().toString().isEmpty()){
            this.binding.editTime.setError("Time cannot be empty");
            validData = false;
        }
        if (this.binding.editPrice.getText().toString().isEmpty()){
            this.binding.editPrice.setError("Price cannot be empty");
            validData = false;
        }
//        if (this.binding.editDate.getText().toString().isEmpty()){

//            this.binding.editDate.setError("Date cannot be empty");
//            validData = false;
////        }
//        if(this.binding.editMobilenumber.getText().toString().length() == 10)
//        {
//            this.binding.editDate.setError("Mobile number should be 10 digits");
//            validData = false;
//        }

        //TODO - check for other validations such as 10 digits for phone number

        return validData;
    }

    private void chooseDate(){
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                binding.editDate.setText(sdf.format(calendar.getTime()).toString());
                String date = sdf.format(calendar.getTime()).toString();
                Log.d(TAG, "onDateSet: Date : " + sdf.format(calendar.getTime()).toString());
                newprovider.setDate(date);
            }
        }, year, month, day);

       // datePickerDialog.getDatePicker().setMaxDate(long );

        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dialogInterface.dismiss();
            }
        });

        datePickerDialog.show();
    }

    private void checkPermissions(){
        if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) &&
                !(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)){

            this.binding.btnGetPicture.setEnabled(false);
            ActivityCompat.requestPermissions(this, this.permissionsArray, this.CAMERA_PERMISSION_REQUEST_CODE);
        }else{
            Log.e(TAG, "Camera permission granted");
            Log.e(TAG, "External storage write permission granted");
            this.binding.btnGetPicture.setEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            this.binding.btnGetPicture.setEnabled(true);
        }
    }

    private void selectFromGallery(){
        Log.e(TAG, "Getting picture from Gallery");

        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivityForResult(pickPhotoIntent, GALLERY_PICTURE_REQUEST_CODE);
    }

    private void openCamera(){
        Log.e(TAG, "Getting picture from Camera");
        Intent camIntent = new Intent(this, CamXActivity.class);
        startActivityForResult(camIntent, CAMERA_PERMISSION_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK){
            if (requestCode == GALLERY_PICTURE_REQUEST_CODE && data != null){
                this.binding.imgProfilePic.setImageURI(data.getData());
            }else if (requestCode == CAMERA_PERMISSION_REQUEST_CODE && data != null){
                Log.e(TAG, "onActivityResult: Received data from CamXActivity");
                Uri pictureUri = data.getParcelableExtra("EXTRA_PICTURE_URI");
                this.binding.imgProfilePic.setImageURI(pictureUri);
            }
        }
    }
}