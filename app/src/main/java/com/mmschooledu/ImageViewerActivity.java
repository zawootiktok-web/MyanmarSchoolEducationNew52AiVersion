package com.mmschooledu;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Objects;

public class ImageViewerActivity extends AppCompatActivity {

    private final ArrayList<Uri> imageUriList = new ArrayList<>();
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageview);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        gridView = findViewById(R.id.gridView);

        // Load images from the folder
        loadImagesFromDownloadsFolder();
    }

    private void loadImagesFromDownloadsFolder() {
        String folderName = "MyanmarSchoolEduImage";

        ContentResolver contentResolver = getContentResolver();
        Uri collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String selection = MediaStore.Images.Media.RELATIVE_PATH + " LIKE ?";
        String[] selectionArgs = new String[]{"Download/" + folderName + "%"};

        String[] projection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME
        };

        try (Cursor cursor = contentResolver.query(
                collection,
                projection,
                selection,
                selectionArgs,
                null
        )) {
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
                    Uri contentUri = Uri.withAppendedPath(collection, String.valueOf(id));
                    imageUriList.add(contentUri);
                }
            } else {
                Toast.makeText(this, "No images found in the folder", Toast.LENGTH_SHORT).show();
            }

            populateGridView();
        } catch (Exception e) {
            Toast.makeText(this, "Error loading images: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void populateGridView() {
        ArrayAdapter<Uri> adapter = new ArrayAdapter<Uri>(this, R.layout.grid_item_layout, imageUriList) {
            @NonNull
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.grid_item_layout, parent, false);
                }

                ImageView imageView = convertView.findViewById(R.id.imageViewGridItem);
                Uri imageUri = getItem(position);
                if (imageUri != null) {
                    imageView.setImageURI(imageUri);
                }

                return convertView;
            }
        };

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
           // Uri selectedImageUri = imageUriList.get(position);
           // Toast.makeText(this, "Clicked: " + selectedImageUri, Toast.LENGTH_SHORT).show();
            // Open detail view or do further processing
       // });
            Intent intent = new Intent(ImageViewerActivity.this, ImageDetailActivity.class);
            intent.putExtra("imageUri", imageUriList.get(position)); // Pass the Uri
            startActivity(intent);

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}



//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.DocumentsContract;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.GridView;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//
//import java.util.ArrayList;
//import java.util.Objects;
//
//public class ImageViewerActivity extends AppCompatActivity {
//
//    private final ArrayList<Uri> imageUriList = new ArrayList<>();
//    private GridView gridView;
//    private static final String DIRECTORY_NAME = "MyanmarSchoolEduImage";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_imageview);
//
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//
//        gridView = findViewById(R.id.gridView);
//
//        // SAF Picker Launcher
//        ActivityResultLauncher<Intent> folderPickerLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
//                        Uri folderUri = result.getData().getData();
//                        if (folderUri != null) {
//                            loadImagesFromFolder(folderUri);
//                        }
//                    }
//                });
//
//        // Trigger SAF Folder Picker
//        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
//        folderPickerLauncher.launch(intent);
//    }
//
//    private void loadImagesFromFolder(Uri folderUri) {
//        try {
//            Uri childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(folderUri,
//                    DocumentsContract.getTreeDocumentId(folderUri));
//
//            // Query the folder for child documents
//            try (android.database.Cursor cursor = getContentResolver().query(childrenUri, null, null, null, null)) {
//                if (cursor != null) {
//                    while (cursor.moveToNext()) {
//                        String mimeType = cursor.getString(cursor.getColumnIndexOrThrow(DocumentsContract.Document.COLUMN_MIME_TYPE));
//                        String documentId = cursor.getString(cursor.getColumnIndexOrThrow(DocumentsContract.Document.COLUMN_DOCUMENT_ID));
//
//                        if (mimeType != null && mimeType.startsWith("image/")) {
//                            Uri documentUri = DocumentsContract.buildDocumentUriUsingTree(folderUri, documentId);
//                            imageUriList.add(documentUri);
//                        }
//                    }
//                }
//            }
//
//            if (imageUriList.isEmpty()) {
//                Toast.makeText(this, "No images found in the selected folder", Toast.LENGTH_SHORT).show();
//            } else {
//                populateGridView();
//            }
//
//        } catch (Exception e) {
//            Toast.makeText(this, "Failed to load images: " + e.getMessage(), Toast.LENGTH_LONG).show();
//        }
//    }
//
//    private void populateGridView() {
//        ArrayAdapter<Uri> adapter = new ArrayAdapter<Uri>(this, R.layout.grid_item_layout, imageUriList) {
//            @NonNull
//            public View getView(int position, View convertView, ViewGroup parent) {
//                if (convertView == null) {
//                    convertView = getLayoutInflater().inflate(R.layout.grid_item_layout, parent, false);
//                }
//
//                ImageView imageView = convertView.findViewById(R.id.imageViewGridItem);
//                Uri imageUri = getItem(position);
//                if (imageUri != null) {
//                    imageView.setImageURI(imageUri);
//                }
//
//                return convertView;
//            }
//        };
//
//        gridView.setAdapter(adapter);
//
//        gridView.setOnItemClickListener((parent, view, position, id) -> {
//            Uri selectedImageUri = imageUriList.get(position);
//            Intent intent = new Intent(ImageViewerActivity.this, ImageDetailActivity.class);
//            intent.putExtra("imageUri", selectedImageUri.toString());
//            startActivity(intent);
//        });
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            finish();
//        }
//        return super.onOptionsItemSelected(item);
//    }
//}
//




//
//import android.Manifest;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Environment;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.GridView;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.core.content.ContextCompat;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.Objects;
//
//public class ImageViewerActivity extends AppCompatActivity {
//
//    private final ArrayList<String> imagePathList = new ArrayList<>();
//    private GridView gridView;
//    private static final int REQUEST_PERMISSION_CODE = 101;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_imageview);
//
//
//
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//
//        gridView = findViewById(R.id.gridView);
//
//        // Initialize the permission launcher
//        // Permission granted
//        // Permission denied
//        // Handle accordingly
//        ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
//            if (isGranted) {
//                // Permission granted
//                loadImagesFromPath();
//            } else {
//                // Permission denied
//                // Handle accordingly
//                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        // Check if permission is granted
//        if (ContextCompat.checkSelfPermission(ImageViewerActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            // Permission is not granted, request it
//            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
//        } else {
//            // Permission already granted
//            loadImagesFromPath();
//        }
//    }
//
//    private void loadImagesFromPath() {
//        // Get the folder path
//        String folderPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//                + File.separator
//                + "MyanmarSchoolEduImage" + File.separator;
//
//        // Assuming you have the paths to the images in an ArrayList<String>
//        // Replace this with your logic to fetch image paths
//        // For example, you can read images from a specific directory
//        // and add their paths to the imagePathList
//        // Example:
//        File directory = new File(folderPath);
//        File[] files = directory.listFiles();
//        if (files != null && files.length > 0) {
//            for (File file : files) {
//                if (file.isFile()) {
//                    imagePathList.add(file.getAbsolutePath());
//                }
//            }
//        } else {
//            // If no images found, add a placeholder image or handle it accordingly
//            Toast.makeText(this, "No images found in the directory", Toast.LENGTH_SHORT).show();
//        }
//
//        // After adding image paths to imagePathList, create adapter and set to grid view
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.grid_item_layout, imagePathList) {
//            @NonNull
//            public View getView(int position, View convertView, ViewGroup parent) {
//                if (convertView == null) {
//                    convertView = getLayoutInflater().inflate(R.layout.grid_item_layout, parent, false);
//                }
//
//                ImageView imageView = convertView.findViewById(R.id.imageViewGridItem);
//                // Set image resource
//                String imagePath = getItem(position);
//                // Assuming imagePath is a valid path to an image file
//                assert imagePath != null;
//                imageView.setImageURI(Uri.fromFile(new File(imagePath)));
//
//                return convertView;
//            }
//        };
//
//        // Set adapter to grid view
//        gridView.setAdapter(adapter);
//
//        // Grid item click listener
//        gridView.setOnItemClickListener((parent, view, position, id) -> {
//            // Handle grid item click
//            String selectedImagePath = imagePathList.get(position);
//            // Open DetailActivity with the selected image
//            Intent intent = new Intent(ImageViewerActivity.this, ImageDetailActivity.class);
//            intent.putExtra("imagePath", selectedImagePath);
//            startActivity(intent);
//        });
//
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//        if (item.getItemId() == android.R.id.home)
//        {
//            finish();
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//}



