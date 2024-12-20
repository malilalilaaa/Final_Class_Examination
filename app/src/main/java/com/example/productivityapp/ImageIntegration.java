package com.example.productivityapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class ImageIntegration extends AppCompatActivity {

    ImageView imgGallery;
    Button btnGallery;
    Button btnCamera;

    // Define an ActivityResultLauncher to handle the result from the gallery
    ActivityResultLauncher<Intent> galleryResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    imgGallery.setImageURI(result.getData().getData()); // Display selected image
                }
            });

    // Define an ActivityResultLauncher to handle the result from the camera
    ActivityResultLauncher<Intent> cameraResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                    imgGallery.setImageBitmap(bitmap); // Display captured image
                }
            });

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_integration);

        imgGallery = findViewById(R.id.imgGallery);
        btnGallery = findViewById(R.id.btnGallery);
        btnCamera = findViewById(R.id.btnCamera);

        // Open gallery when button is clicked
        btnGallery.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryResultLauncher.launch(intent);  // Use the launcher to start the gallery activity
        });

        // Open camera when button is clicked
        btnCamera.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraResultLauncher.launch(intent);  // Use the launcher to start the camera activity
        });
    }
}
