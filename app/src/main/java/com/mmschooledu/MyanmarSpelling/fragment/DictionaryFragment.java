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


import com.mmschooledu.MyanmarSpelling.adapters.DictionaryAdapter;
import com.mmschooledu.MyanmarSpelling.adapters.StoryAdapter;
import com.mmschooledu.MyanmarSpelling.database.DBOpenHelper;
import com.mmschooledu.MyanmarSpelling.database.DictionaryDBOpenHelper;
import com.mmschooledu.MyanmarSpelling.models.StoryType;
import com.mmschooledu.MyanmarSpelling.utils.MDetect;
import com.mmschooledu.MyanmarSpelling.utils.Rabbit;
import com.mmschooledu.R;

public class DictionaryFragment extends Fragment implements DictionaryAdapter.OnItemClickListener {

    private final ArrayList<StoryType> storyTypes = new ArrayList<>();
    private final ArrayList<StoryType> filterWords = new ArrayList<>();
    private DictionaryAdapter adapter;
    private TextView tv_empty_info;

    private static final String TAG = "HomeFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(MDetect.getDeviceEncodedText(getString(R.string.mmspelling)));
        return inflater.inflate(R.layout.dictionary_fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.wordListView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));
        adapter = new DictionaryAdapter(filterWords, this);
        recyclerView.setAdapter(adapter);

        tv_empty_info = view.findViewById(R.id.dictionaryempty_info);
        final EditText searchInput = view.findViewById(R.id.dictionarysearch);
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
            DictionaryDBOpenHelper dictionarydbHelper = new DictionaryDBOpenHelper(getContext());
            dictionarydbHelper.createDatabase();
            dictionarydbHelper.openDatabase();

            Cursor cursor = dictionarydbHelper.getStoryTypes();
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
        Fragment dictionaryTitleFragment = new DictionaryTitleFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("titleId", titleId);
        dictionaryTitleFragment.setArguments(bundle);

        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, dictionaryTitleFragment);
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
