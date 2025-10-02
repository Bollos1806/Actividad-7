package com.bollos18.actividad_7;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView imgPreview;

    // Launcher para cámara (miniatura)
    private ActivityResultLauncher<Intent> takePictureLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Bundle extras = result.getData().getExtras();
                    if (extras != null) {
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        if (imageBitmap != null) imgPreview.setImageBitmap(imageBitmap);
                    }
                } else {
                    Toast.makeText(this, "Fotografía cancelada", Toast.LENGTH_SHORT).show();
                }
            });

    // Launcher para galería (GetContent)
    private ActivityResultLauncher<String> pickImageLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    imgPreview.setImageURI(uri);
                } else {
                    Toast.makeText(this, "Selección cancelada", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgPreview = findViewById(R.id.imgPreview);

        Button btnDial = findViewById(R.id.btnDial);
        Button btnMaps = findViewById(R.id.btnMaps);
        Button btnShare = findViewById(R.id.btnShare);
        Button btnEmail = findViewById(R.id.btnEmail);
        Button btnCamera = findViewById(R.id.btnCamera);
        Button btnGallery = findViewById(R.id.btnGallery);

        btnDial.setOnClickListener(v -> openDialer("5551234567"));
        btnMaps.setOnClickListener(v -> openMapQuery("Av. Insurgentes Sur 3000, CDMX"));
        btnShare.setOnClickListener(v -> shareText("Hola, compartido desde mi app."));
        btnEmail.setOnClickListener(v -> composeEmail(
                new String[]{"soporte@empresa.com"},
                "Soporte de mi App",
                "Describe tu problema aquí..."
        ));
        btnCamera.setOnClickListener(v -> takePicture());
        btnGallery.setOnClickListener(v -> pickFromGallery());
    }

    /* ----------  Acciones ---------- */

    private void openDialer(String number) {
        Uri uri = Uri.parse("tel:" + number);
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            showNoAppMsg();
        }
    }

    private void openMapQuery(String q) {
        Uri geo = Uri.parse("geo:0,0?q=" + Uri.encode(q));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, geo);
        // mapIntent.setPackage("com.google.android.apps.maps"); // opcional
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            showNoAppMsg();
        }
    }

    private void shareText(String text) {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, "Compartir con...");
        startActivity(shareIntent);
    }

    private void composeEmail(String[] addresses, String subject, String body) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, addresses);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        } else {
            showNoAppMsg();
        }
    }

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            takePictureLauncher.launch(takePictureIntent);
        } else {
            showNoAppMsg();
        }
    }

    private void pickFromGallery() {
        pickImageLauncher.launch("image/*");
    }

    private void showNoAppMsg() {
        Toast.makeText(this, "No hay una app para manejar esta acción.", Toast.LENGTH_SHORT).show();
    }
}