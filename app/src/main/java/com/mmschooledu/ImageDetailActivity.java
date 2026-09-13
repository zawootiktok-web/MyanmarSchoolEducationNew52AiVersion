package com.mmschooledu;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.InputStream;
import java.util.Objects;

public class ImageDetailActivity extends AppCompatActivity {

    private ImageView imageView;
    private Uri imageUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        imageView = findViewById(R.id.imageView);

        // Retrieve the Uri passed from ImageViewerActivity
        imageUri = getIntent().getParcelableExtra("imageUri");
        if (imageUri != null) {
            // Load the image into the ImageView
            loadImageFromUri(imageUri);
        } else {
            // Handle null URI
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Handle back button click
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.action_share) {
            // Share image
            shareImage();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadImageFromUri(Uri uri) {
        try (InputStream inputStream = getContentResolver().openInputStream(uri)) {
            if (inputStream != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void shareImage() {
        if (imageUri != null) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            startActivity(Intent.createChooser(shareIntent, "Share Image"));
        }
    }
}

//
//import android.content.Intent;
//        import android.graphics.Bitmap;
//        import android.graphics.BitmapFactory;
//        import android.net.Uri;
//        import android.os.Bundle;
//        import android.view.Menu;
//        import android.view.MenuItem;
//import android.widget.ImageView;
//
//import androidx.annotation.NonNull;
//        import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//        import androidx.appcompat.widget.Toolbar;
//
//        import java.io.File;
//        import java.util.Objects;
//
//public class ImageDetailActivity extends AppCompatActivity {
//
//    private ImageView imageView;
//    private String imageUriList;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.image_detail);
//
//
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//
//        imageView = findViewById(R.id.imageView);
//        imageUriList = getIntent().getStringExtra("imagePath");
//
//        // Load image into ImageView
//        Bitmap bitmap = BitmapFactory.decodeFile(imageUriList);
//        imageView.setImageBitmap(bitmap);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_detail, menu);
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            // Handle back button click
//            onBackPressed();
//            return true;
//        } else if (item.getItemId() == R.id.action_share) {
//            shareImage();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//
//    private void shareImage() {
//        Intent shareIntent = new Intent(Intent.ACTION_SEND);
//        shareIntent.setType("image/*");
//        File imageFile = new File(imageUriList);
//        Uri uri = Uri.fromFile(imageFile);
//        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
//        startActivity(Intent.createChooser(shareIntent, "Share Image"));
//    }
//
//
//}