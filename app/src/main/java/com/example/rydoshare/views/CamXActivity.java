package com.example.rydoshare.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.rydoshare.R;
import com.example.rydoshare.databinding.ActivityCamXactivityBinding;
import com.google.common.util.concurrent.ListenableFuture;


import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class CamXActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "CamXActivity";
    private ActivityCamXactivityBinding binding;
    private ImageCapture imageCapture = null;
    private Uri resultPicUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityCamXactivityBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        this.binding.btnCameraCapture.setOnClickListener(this);

        this.imageCapture = new ImageCapture.Builder().build();
        this.startCamera();
    }

    @Override
    public void onClick(View view) {
        if (view != null){
           if (view.getId() == R.id.btn_camera_capture){
                this.takePhoto();
            }
        }
    }

    private void startCamera(){

        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try{
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                this.bindPreview(cameraProvider);
            }catch (InterruptedException | ExecutionException ex){
                //This should never be reached
                Log.e(TAG, "startCamera: " + ex.getLocalizedMessage() );
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void bindPreview(ProcessCameraProvider cameraProvider){
        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();
        preview.setSurfaceProvider(this.binding.previewViewContainer.getSurfaceProvider());

        try{
            cameraProvider.unbindAll();
            cameraProvider.bindToLifecycle(this, cameraSelector, preview, this.imageCapture);
        }catch(Exception ex){
            Log.e(TAG, "bindPreview: Use-case binding failed" + ex.getLocalizedMessage() );
        }
    }


    private void takePhoto(){
        if (this.imageCapture != null){
            //get the location to save Image file
            File outputDir = this.getOutputDirectory();
            String fileName = "CamX_" +
                    new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.getDefault()).format(System.currentTimeMillis()) +
                    ".jpeg";
            //create file to save picture in the destination directory
            File pictureFile = new File(outputDir, fileName);
            //associate the file to the ImageCapture use-case
            ImageCapture.OutputFileOptions fileOptions = new ImageCapture.OutputFileOptions.Builder(pictureFile).build();

            //capture picture
            this.imageCapture.takePicture(fileOptions, ContextCompat.getMainExecutor(this), new ImageCapture.OnImageSavedCallback() {
                @Override
                public void onImageSaved(@NonNull @NotNull ImageCapture.OutputFileResults outputFileResults) {
                    Log.e(TAG, "onImageSaved: Image is saved at " + Uri.fromFile(pictureFile) );
                    resultPicUri = Uri.fromFile(pictureFile);

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("EXTRA_PICTURE_URI", resultPicUri);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }

                @Override
                public void onError(@NonNull @NotNull ImageCaptureException exception) {
                    Log.e(TAG, "onError: Image couldn't be saved" + exception.getLocalizedMessage());

                    Intent resultIntent = new Intent();
                    setResult(RESULT_CANCELED, resultIntent);
                    finish();
                }
            });

        }else{
            Log.e(TAG, "takePhoto: ImageCapture use-case is unavailable. Couldn't click pictures.");
        }
    }

    private File getOutputDirectory(){
        if (this.getExternalMediaDirs().length > 0){
            //create a separate directory called MyCamX to save the pictures
            File camDir = new File(this.getExternalMediaDirs()[0], "MyCamX");

            if (camDir.exists()){
                Log.e(TAG, "getOutputDirectory: camDir exists");
                return camDir;
            }else if(camDir.mkdir()){
                Log.e(TAG, "getOutputDirectory: camDir created");
                return camDir;
            }
        }else{
            return this.getFilesDir();
        }

        return null;
    }
}