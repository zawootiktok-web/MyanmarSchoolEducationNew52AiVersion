//package mm.pndaza.thupyadictionary.fragment;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.database.Cursor;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentTransaction;
//import androidx.recyclerview.widget.DividerItemDecoration;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//
//import mm.pndaza.thupyadictionary.R;
//import mm.pndaza.thupyadictionary.adapters.StoryAdapter;
//import mm.pndaza.thupyadictionary.database.DBOpenHelper;
//import mm.pndaza.thupyadictionary.models.StoryType;
//import mm.pndaza.thupyadictionary.utils.MDetect;
//import mm.pndaza.thupyadictionary.utils.Rabbit;
//
//public class HomeFragment extends Fragment implements StoryAdapter.OnItemClickListener {
//
//    private final ArrayList<StoryType> storyTypes = new ArrayList<>();
//    private final ArrayList<StoryType> filterWords = new ArrayList<StoryType>();
//    private final StoryAdapter adapter = new StoryAdapter(storyTypes, this);
//    private TextView tv_empty_info;
//
//    private static final String TAG = "HomeFragment";
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        getActivity().setTitle(MDetect.getDeviceEncodedText(getString(R.string.app_name_mm)));
//        return inflater.inflate(R.layout.fragment_home, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        DBOpenHelper dbHelper = new DBOpenHelper(getContext());
//        dbHelper.createDatabase();
//        dbHelper.openDatabase();
//
////        RecyclerView recyclerView = view.findViewById(R.id.wordListView);
////        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
////        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
////        recyclerView.setAdapter(adapter);
//        RecyclerView recyclerView = view.findViewById(R.id.wordListView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(adapter);
//
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5)); // 2 columns
//
//        new LoadStoryType().execute();
//
//        tv_empty_info = view.findViewById(R.id.empty_info);
//        final EditText searchInput = view.findViewById(R.id.search);
//        final ImageButton btnClear = view.findViewById(R.id.btn_clear);
//        btnClear.setOnClickListener(v -> searchInput.setText(""));
////        setupClearButton(btnClear, searchInput);
//        searchInput.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                setViewStateTo(btnClear, editable.toString().isEmpty());
//                doFilter(editable.toString());
//            }
//        });
//
//
//    }
//
//    public class LoadStoryType extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//
//            DBOpenHelper dbHelper = new DBOpenHelper(getContext());
//            dbHelper.createDatabase();
//            dbHelper.openDatabase();
//            int count = 0;
//            Cursor cursor = dbHelper.getStoryTypes();
//            if (cursor != null && cursor.moveToFirst()) {
//                do {
//                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
//                    @SuppressLint("Range") String string = cursor.getString(cursor.getColumnIndex("name_unicode"));
//                    storyTypes.add(new StoryType(id, string));
//                    if( storyTypes.size() + 10 > count){
//                        count = storyTypes.size();
//                        publishProgress();
//                    }
//                } while (cursor.moveToNext());
//
//            }
//            cursor.close();
//            return null;
//        }
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//            super.onProgressUpdate(values);
//            adapter.notifyDataSetChanged();
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            adapter.notifyDataSetChanged();
//        }
//    }
//    @Override
//    public void onItemClick(StoryType word) {
//        Log.d(TAG, "onItemClick: " + word.getId());
//        int titleId=word.getId();
//        // Create a new instance of StoryTitleFragment
//        StoryTitleFragment storyTitleFragment = new StoryTitleFragment();
//
//        // Create a bundle to pass the titleId
//        Bundle bundle = new Bundle();
//        bundle.putInt("titleId", titleId);
//
//        // Set the arguments to the fragment
//        storyTitleFragment.setArguments(bundle);
//
////        // Begin a fragment transaction to replace or add the fragment
////        FragmentTransaction transaction = getFragmentManager().beginTransaction();
////        transaction.replace(R.id.nav_host_fragment, storyTitleFragment);  // Replace with your container's ID
////        transaction.addToBackStack(null);  // Optional: Add this transaction to the back stack
////        transaction.commit();
////    }
//
//        // Begin a fragment transaction to replace or add the fragment
//        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.nav_host_fragment, storyTitleFragment);  // Replace with container ID
//        transaction.addToBackStack(null);  // Add this transaction to the back stack
//        transaction.commit();
//    }
//
//
//
//    private void doFilter(String filter) {
//        filterWords.clear();
//        if (filter.isEmpty()) {
//            adapter.setFilteredWordList(storyTypes);
//            adapter.setFilterText("");
//        } else {
//            if(!MDetect.isUnicode()){
//                filter = Rabbit.zg2uni(filter);
//            }
//            for (StoryType word : storyTypes) {
//                if (word.getNameUnicode().contains(filter)) {
//                    filterWords.add(word);
//                }
//            }
//            adapter.setFilteredWordList(filterWords);
//            adapter.setFilterText(filter);
//            if (filterWords.size() == 0) {
//                tv_empty_info.setText(MDetect.getDeviceEncodedText("ရှာမတွေ့ပါ!"));
//            } else {
//                tv_empty_info.setText("");
//            }
//        }
//
//    }
//
//    private void setupClearButton(ImageButton btnClear, final EditText search) {
//
//        btnClear.setOnClickListener(view -> search.setText(""));
//    }
//
//    private void setViewStateTo(ImageButton imageButton, boolean isEmpty) {
//
//        if (isEmpty) {
//            imageButton.setVisibility(View.INVISIBLE);
//        } else {
//            imageButton.setVisibility(View.VISIBLE);
//        }
//    }
//}



package com.mmschooledu.MyanmarSpelling.fragment;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


import com.mmschooledu.MyanmarSpelling.adapters.StoryAdapter;
import com.mmschooledu.MyanmarSpelling.database.DBOpenHelper;
import com.mmschooledu.MyanmarSpelling.models.StoryType;
import com.mmschooledu.MyanmarSpelling.utils.MDetect;
import com.mmschooledu.MyanmarSpelling.utils.Rabbit;
import com.mmschooledu.R;

public class HomeFragment extends Fragment implements StoryAdapter.OnItemClickListener {

    private final ArrayList<StoryType> storyTypes = new ArrayList<>();
    private final ArrayList<StoryType> filterWords = new ArrayList<>();
    private StoryAdapter adapter;
    private TextView tv_empty_info;

    private static final String TAG = "HomeFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(MDetect.getDeviceEncodedText(getString(R.string.mmspelling)));
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.wordListView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));
        adapter = new StoryAdapter(filterWords, this);
        recyclerView.setAdapter(adapter);

        tv_empty_info = view.findViewById(R.id.newempty_info);
        final EditText searchInput = view.findViewById(R.id.newsearch);
        final ImageButton btnClear = view.findViewById(R.id.btn_clear);

        btnClear.setOnClickListener(v -> searchInput.setText(""));

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                setViewStateTo(btnClear, editable.toString().isEmpty());
                doFilter(editable.toString());
            }
        });

        new LoadStoryType().execute();
    }

    public class LoadStoryType extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            storyTypes.clear();
            filterWords.clear();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            DBOpenHelper dbHelper = new DBOpenHelper(getContext());
            dbHelper.createDatabase();
            dbHelper.openDatabase();

            Cursor cursor = dbHelper.getStoryTypes();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                    @SuppressLint("Range") String string = cursor.getString(cursor.getColumnIndex("name_unicode"));
                    storyTypes.add(new StoryType(id, string));
                } while (cursor.moveToNext());
                cursor.close();
            }
            filterWords.addAll(storyTypes);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(StoryType word) {
        Log.d(TAG, "onItemClick: " + word.getId());
        int titleId = word.getId();
        Fragment storyTitleFragment = new StoryTitleFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("titleId", titleId);
        storyTitleFragment.setArguments(bundle);

        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, storyTitleFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private void doFilter(String filter) {
        filterWords.clear();
        if (filter.isEmpty()) {
            adapter.setFilteredWordList(storyTypes);
            adapter.setFilterText("");
        } else {
            if(!MDetect.isUnicode()){
                filter = Rabbit.zg2uni(filter);
            }
            for (StoryType word : storyTypes) {
                if (word.getNameUnicode().contains(filter)) {
                    filterWords.add(word);
                }
            }
            adapter.setFilteredWordList(filterWords);
            adapter.setFilterText(filter);
            if (filterWords.size() == 0) {
                tv_empty_info.setText(MDetect.getDeviceEncodedText("ရှာမတွေ့ပါ!"));
            } else {
                tv_empty_info.setText("");
            }
        }

    }

    private void setupClearButton(ImageButton btnClear, final EditText search) {

        btnClear.setOnClickListener(view -> search.setText(""));
    }

    private void setViewStateTo(ImageButton imageButton, boolean isEmpty) {

        if (isEmpty) {
            imageButton.setVisibility(View.INVISIBLE);
        } else {
            imageButton.setVisibility(View.VISIBLE);
        }
    }
}
