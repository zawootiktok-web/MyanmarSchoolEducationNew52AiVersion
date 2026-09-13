package com.mmschooledu.Story.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


import com.mmschooledu.R;
import com.mmschooledu.Story.adapters.StoryAdapter;
import com.mmschooledu.Story.database.DBOpenHelper;
import com.mmschooledu.Story.models.StoryType;
import com.mmschooledu.Story.utils.MDetect;
import com.mmschooledu.Story.utils.Rabbit;


public class HomeFragment extends Fragment implements StoryAdapter.OnItemClickListener {

    private final ArrayList<StoryType> storyTypes = new ArrayList<>();
    private final ArrayList<StoryType> filterWords = new ArrayList<StoryType>();
    private final StoryAdapter adapter = new StoryAdapter(storyTypes, this);
    private TextView tv_empty_info;

    private static final String TAG = "HomeFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(MDetect.getDeviceEncodedText(getString(R.string.app_name_story)));
        return inflater.inflate(R.layout.newfragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DBOpenHelper dbHelper = new DBOpenHelper(getContext());
        dbHelper.createDatabase();
        dbHelper.openDatabase();

        RecyclerView recyclerView = view.findViewById(R.id.wordListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        new LoadStoryType().execute();

        tv_empty_info = view.findViewById(R.id.empty_info);
        final EditText searchInput = view.findViewById(R.id.newsearch);
        final ImageButton btnClear = view.findViewById(R.id.btn_clear);
        btnClear.setOnClickListener(v -> searchInput.setText(""));
//        setupClearButton(btnClear, searchInput);
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                setViewStateTo(btnClear, editable.toString().isEmpty());
                doFilter(editable.toString());
            }
        });


    }

    public class LoadStoryType extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            DBOpenHelper dbHelper = new DBOpenHelper(getContext());
            dbHelper.createDatabase();
            dbHelper.openDatabase();
            int count = 0;
            Cursor cursor = dbHelper.getStoryTypes();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                    @SuppressLint("Range") String string = cursor.getString(cursor.getColumnIndex("name_unicode"));
                    storyTypes.add(new StoryType(id, string));
                    if( storyTypes.size() + 10 > count){
                        count = storyTypes.size();
                        publishProgress();
                    }
                } while (cursor.moveToNext());

            }
            cursor.close();
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            adapter.notifyDataSetChanged();
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
        int titleId=word.getId();
        // Create a new instance of StoryTitleFragment
        StoryTitleFragment storyTitleFragment = new StoryTitleFragment();

        // Create a bundle to pass the titleId
        Bundle bundle = new Bundle();
        bundle.putInt("titleId", titleId);

        // Set the arguments to the fragment
        storyTitleFragment.setArguments(bundle);

//        // Begin a fragment transaction to replace or add the fragment
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.replace(R.id.nav_host_fragment, storyTitleFragment);  // Replace with your container's ID
//        transaction.addToBackStack(null);  // Optional: Add this transaction to the back stack
//        transaction.commit();
//    }

        // Begin a fragment transaction to replace or add the fragment
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, storyTitleFragment);  // Replace with container ID
        transaction.addToBackStack(null);  // Add this transaction to the back stack
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


