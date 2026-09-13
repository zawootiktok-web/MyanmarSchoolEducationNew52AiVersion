package com.mmschooledu.MyanmarSpelling.fragment;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


import com.mmschooledu.MyanmarSpelling.adapters.DictionaryTitleAdapter;

import com.mmschooledu.MyanmarSpelling.database.DictionaryDBOpenHelper;
import com.mmschooledu.MyanmarSpelling.models.Story;
import com.mmschooledu.MyanmarSpelling.models.StoryType;
import com.mmschooledu.MyanmarSpelling.utils.MDetect;
import com.mmschooledu.MyanmarSpelling.utils.Rabbit;
import com.mmschooledu.R;

public class DictionaryTitleFragment extends Fragment implements DictionaryTitleAdapter.OnItemClickListener {

    private final ArrayList<Story> storyTitles = new ArrayList<>();
    private final ArrayList<Story> filterWords = new ArrayList<Story>();
    private final DictionaryTitleAdapter adapter = new DictionaryTitleAdapter(storyTitles, this);
    private OnWordClickListener onWordClickListener;
    private TextView tv_empty_info;
    private int titleId;

    private static final String TAG = "DictionaryTitleFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(MDetect.getDeviceEncodedText(getString(R.string.mmspelling)));
        // Retrieve the passed data from the bundle
        if (getArguments() != null) {
            titleId = getArguments().getInt("titleId", -1);  // -1 is the default value if titleId is not found
        }

        return inflater.inflate(R.layout.mmspelling_fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DictionaryDBOpenHelper dictionarydbHelper = new DictionaryDBOpenHelper(getContext());
        dictionarydbHelper.createDatabase();
        dictionarydbHelper.openDatabase();

//        RecyclerView recyclerView = view.findViewById(R.id.wordListView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
//        recyclerView.setAdapter(adapter);

        RecyclerView recyclerView = view.findViewById(R.id.wordListView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        //  adapter = new StoryAdapter(filterWords, this);
        recyclerView.setAdapter(adapter);
        new LoadStoryType().execute();

        tv_empty_info = view.findViewById(R.id.mmempty_info);
        final EditText searchInput = view.findViewById(R.id.mmsearch);
        final ImageButton btnClear = view.findViewById(R.id.btn_clear);
        btnClear.setOnClickListener(v -> searchInput.setText(""));
//        setupClearButton(btnClear, searchInput);
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
////                setViewStateTo(btnClear, editable.toString().isEmpty());
////                doFilter(editable.toString());
//            }
//        });
//
//
//    }


        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                adapter.filter(editable.toString());
            }
        });

        new LoadStoryType().execute();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onWordClickListener = (OnWordClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implemented OnWordListSelectedListener");

        }
    }


    public class LoadStoryType extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            DictionaryDBOpenHelper dictionarydbHelper = new DictionaryDBOpenHelper(getContext());
            dictionarydbHelper.createDatabase();
            dictionarydbHelper.openDatabase();

            int count = 0;
            Cursor cursor = null;

            try {
                // Get the story titles using the storyTypeId
                cursor = dictionarydbHelper.getStoryTitles(titleId);

                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        // Get the column indices
                        int idColumnIndex = cursor.getColumnIndex("id");
                        int idStoryTypeColumnIndex = cursor.getColumnIndex("story_type_id");
                        int nameUnicodeColumnIndex = cursor.getColumnIndex("name_Unicode");
                        int descriptionUnicodeIndex = cursor.getColumnIndex("description_unicode");

                        // Validate if columns exist in the cursor
                        if (idStoryTypeColumnIndex != -1 && nameUnicodeColumnIndex != -1) {
                            // Extract values only if valid columns exist
                            int id = cursor.getInt(idColumnIndex);
                            int idStoryType = cursor.getInt(idStoryTypeColumnIndex);
                            String nameUnicode = cursor.getString(nameUnicodeColumnIndex);
                            String descriptionUnicode = cursor.getString(descriptionUnicodeIndex);
                            storyTitles.add(new Story(id,idStoryType,nameUnicode,descriptionUnicode));

                            if (storyTitles.size() + 10 > count) {
                                count = storyTitles.size();
                                publishProgress();
                            }
                        } else {
                            Log.e("LoadStoryType", "Columns not found in cursor");
                        }
                    } while (cursor.moveToNext());
                }
            } catch (Exception e) {
                Log.e("LoadStoryType", "Error while fetching data: " + e.getMessage());
            } finally {
                // Ensure cursor is closed
                if (cursor != null) {
                    cursor.close();
                }
            }

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
            adapter.filter(""); // Initial load
        }
    }
    @Override
    public void onItemClick(Story word) {
        Log.d(TAG, "onItemClick: " + word.getId());
        Log.d(TAG, "onItemClick: " + word.getNameUnicode());
        Log.d(TAG, "onItemClick: " + word.getDescriptionUnicode());
        //  onWordClickListener.onWordClick(word);
        //  Log.d(TAG, "onItemClick: " + word.getId());
        int titleId = word.getId();

        DictionaryTitleFragment2 dictionaryTitleFragment2 = new DictionaryTitleFragment2();
        Bundle bundle = new Bundle();
        bundle.putInt("story_type_id", word.getId());
        dictionaryTitleFragment2.setArguments(bundle);

        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, dictionaryTitleFragment2);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public interface OnWordClickListener {
        void onWordClick(Story story);
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

