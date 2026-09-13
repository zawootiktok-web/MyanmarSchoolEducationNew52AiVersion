package com.mmschooledu.Mp3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mmschooledu.R;

import java.util.ArrayList;
import java.util.List;

public class AudioOpenActivity extends AppCompatActivity {

    private static final String AUDIO_FOLDER_NAME = "MyanmarSchoolEducationMp3";
    private List<AudioItem> audioList = new ArrayList<>();
    private ListView lv;
    private MyAdapter adapter;
    androidx.appcompat.widget.Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        tb = findViewById(R.id.nnl_toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv = findViewById(R.id.lv);
        loadAudioFiles();

        adapter = new MyAdapter();
        lv.setAdapter(adapter);

        lv.setOnItemClickListener((parent, view, position, id) -> playAudioFile(audioList.get(position).getUri()));

        lv.setOnItemLongClickListener((parent, view, position, id) -> {
            // Handle long click actions here
            Toast.makeText(this, "Long click on: " + audioList.get(position).getName(), Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void loadAudioFiles() {
        // Using MediaStore or SAF to load audio files
        Uri downloadsUri = DocumentsContract.buildDocumentUri(
                "com.android.providers.media.documents",
                "primary:Download/" + AUDIO_FOLDER_NAME
        );

        // Validate that the folder exists
        if (downloadsUri == null) {
            Toast.makeText(this, "Audio folder not found.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Mock data for testing (replace with actual MediaStore or SAF logic to fetch real data)
        audioList.add(new AudioItem("SampleAudio1.mp3", downloadsUri));
        audioList.add(new AudioItem("SampleAudio2.mp3", downloadsUri));
    }

    public void playAudioFile(Uri audioUri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(audioUri, "audio/*");
        startActivity(intent);
    }

    public class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return audioList.size();
        }

        @Override
        public Object getItem(int position) {
            return audioList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(AudioOpenActivity.this).inflate(R.layout.gallery_item, parent, false);
            }
            ImageView iv = convertView.findViewById(R.id.iv_item);
            TextView tv = convertView.findViewById(R.id.tv_item);

            AudioItem item = audioList.get(position);

            iv.setImageResource(R.drawable.audiolist); // Placeholder icon for audio
            tv.setText(item.getName());
            return convertView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    static class AudioItem {
        private final String name;
        private final Uri uri;

        public AudioItem(String name, Uri uri) {
            this.name = name;
            this.uri = uri;
        }

        public String getName() {
            return name;
        }

        public Uri getUri() {
            return uri;
        }
    }
}


//package com.mmschooledu.Mp3;
//import android.Manifest;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Environment;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.AdapterView.OnItemLongClickListener;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//
//import com.mmschooledu.R;
//
//import java.io.File;
//
//public class AudioOpenActivity extends AppCompatActivity {
//
//
//    final int storageRequestCode = 123;
//    private static final int resultCode = 0;
//    static String[] titles, patch;
//    private ListView lv;
//    MyAdapter adapter;
//    androidx.appcompat.widget.Toolbar tb;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_gallery);
//
//        tb = (androidx.appcompat.widget.Toolbar) findViewById(R.id.nnl_toolbar);
//        setSupportActionBar(tb);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        requestAppPermissions();
//
//        lv = (ListView) findViewById(R.id.lv);
//        movieList();
//        adapter = new MyAdapter();
//        lv.setAdapter(adapter);
//        lv.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                playAudioFile(patch[position]);
//            }
//        });
//
//        lv.setOnItemLongClickListener(new OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                // Handle long click actions here
//                return true;
//            }
//        });
//    }
//
//    private void movieList() {
//        try {
//            //String file=bname+".pdf";
//
//
//            //String mBaseFolderPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//            //+ File.separator
//            //+ "MyanmarSchoolEducation" + File.separator;
//
//            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(), "MyanmarSchoolEducationMp3");
//            File[] files = file.listFiles();
//
//
//            //File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "MyanmarSchoolEducation");
//            //File[] files = file.listFiles();
//
//            patch = new String[files.length];
//            titles = new String[files.length];
//            for (int i = 0; i < files.length; i++) {
//                patch[i] = files[i].getAbsolutePath();
//                titles[i] = files[i].getName();
//            }
//        }catch (Exception e){
//
//        }
//    }
//
//    private void requestAppPermissions() {
//        ActivityCompat.requestPermissions(AudioOpenActivity.this,
//                new String[]{
//                        Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE
//                },
//                resultCode);
//    }
//
//    public class MyAdapter extends BaseAdapter {
//        @Override
//        public int getCount() {
//            return titles.length;
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return titles[position];
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            View view = LayoutInflater.from(AudioOpenActivity.this).inflate(R.layout.gallery_item, parent, false);
//            ImageView iv = view.findViewById(R.id.iv_item);
//            TextView tv = view.findViewById(R.id.tv_item);
//
//            File file = new File(patch[position]);
//            if (file.getName().endsWith(".mp3")) {
//                iv.setImageResource(R.drawable.audiolist);
//            }
//
//            tv.setText(file.getName());
//            return view;
//        }
//    }
//
//    public void playAudioFile(String path) {
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        File file = new File(path);
//        Uri audioUri = Uri.fromFile(file);
//        intent.setDataAndType(audioUri, "audio/*");
//        startActivity(intent);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == android.R.id.home) {
//            finish();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == storageRequestCode) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Storage permission granted. You can now access files.", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Storage permission denied. You cannot access files.", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//}
