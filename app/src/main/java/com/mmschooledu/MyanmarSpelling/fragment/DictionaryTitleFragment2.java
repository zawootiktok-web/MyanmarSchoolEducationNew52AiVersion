package com.mmschooledu.MyanmarSpelling.fragment;


import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


import com.mmschooledu.MyanmarSpelling.adapters.StoryAdapter;
import com.mmschooledu.MyanmarSpelling.adapters.StoryTitleAdapter;
import com.mmschooledu.MyanmarSpelling.database.DBOpenHelper;
import com.mmschooledu.MyanmarSpelling.database.DictionaryDBOpenHelper;
import com.mmschooledu.MyanmarSpelling.models.Story;
import com.mmschooledu.MyanmarSpelling.models.StoryType;
import com.mmschooledu.MyanmarSpelling.utils.MDetect;
import com.mmschooledu.MyanmarSpelling.utils.Rabbit;
import com.mmschooledu.R;

public class DictionaryTitleFragment2 extends Fragment implements StoryTitleAdapter.OnItemClickListener {

    private final ArrayList<Story> storyTitles2 = new ArrayList<>();
    private final ArrayList<Story> filterWords = new ArrayList<Story>();
    private final StoryTitleAdapter adapter = new StoryTitleAdapter(storyTitles2, this);
    private OnWordClickListener onWordClickListener;
    private TextView tv_empty_info;
    private int titleId;

    private static final String TAG = "DictionaryTitleFragment2";


    private TextView tv_detail;
    private int currentId;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(MDetect.getDeviceEncodedText(getString(R.string.mmspelling)));
        // Retrieve the passed data from the bundle
        if (getArguments() != null) {
            titleId = getArguments().getInt("story_type_id", -1);  // -1 is the default value if titleId is not found
        }

        return inflater.inflate(R.layout.dfragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DictionaryDBOpenHelper dictionarydbHelper = new DictionaryDBOpenHelper(getContext());
        dictionarydbHelper.createDatabase();
        dictionarydbHelper.openDatabase();

        RecyclerView recyclerView = view.findViewById(R.id.wordListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        new LoadStoryType().execute();

        tv_empty_info = view.findViewById(R.id.dempty_info);
        final EditText searchInput = view.findViewById(R.id.dsearch);
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



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_tools, menu);
        MenuItem fav = menu.findItem(R.id.menu_favourite);
        setIcon(fav);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menu_copy) {
            copyToClipboard();
            return true;
        } else if (itemId == R.id.menu_favourite) {
            manageFavourites(item);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }



    private void copyToClipboard() {
        String textToCopy = tv_detail.getText().toString();
        ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("copy", textToCopy);
        clipboard.setPrimaryClip(clip);
        Snackbar.make(tv_detail, MDetect.getDeviceEncodedText("ကော်ပီကူးယူပြီးပါပြီ"), Snackbar.LENGTH_SHORT).show();
    }

    private void setIcon(MenuItem item) {
        item.setIcon(isFavouriteExist(currentId) ? R.drawable.ic_added_favorite : R.drawable.ic_add_to_favourite);
    }

    private boolean isFavouriteExist(int id) {
        DictionaryDBOpenHelper dictionarydbHelper = new DictionaryDBOpenHelper(requireContext());
        dictionarydbHelper.openDatabase();
        return dictionarydbHelper.isFavouriteExist(id);
    }

    private void manageFavourites(MenuItem item) {
        if (isFavouriteExist(currentId)) {
            removeFromFavourite(currentId);
            item.setIcon(R.drawable.ic_add_to_favourite);
        } else {
            addToFavourite(currentId);
            item.setIcon(R.drawable.ic_added_favorite);
        }
    }

    private void addToFavourite(int id) {
        DictionaryDBOpenHelper dictionarydbHelper = new DictionaryDBOpenHelper(requireContext());
        dictionarydbHelper.openDatabase();
        dictionarydbHelper.addToFavourite(id);
        Snackbar.make(tv_detail, MDetect.getDeviceEncodedText("စိတ်ကြိုက်စာရင်းသို့ ထည့်လိုက်ပါပြီ။"), Snackbar.LENGTH_SHORT).show();
    }

    private void removeFromFavourite(int id) {
        DictionaryDBOpenHelper dictionarydbHelper = new DictionaryDBOpenHelper(requireContext());
        dictionarydbHelper.openDatabase();
        dictionarydbHelper.removeFromFavourite(id);
        Snackbar.make(tv_detail, MDetect.getDeviceEncodedText("စိတ်ကြိုက်စာရင်းမှ ပယ်ဖျက်လိုက်ပါပြီ"), Snackbar.LENGTH_SHORT).show();
    }

    private void manageRecent(int storyId) {
        DictionaryDBOpenHelper dictionarydbHelper = new DictionaryDBOpenHelper(requireContext());
        dictionarydbHelper.openDatabase();
        boolean exists = dictionarydbHelper.isRecentExist(storyId);
        Log.d(TAG, "Checking if StoryId exists: " + storyId + " | Exists: " + exists);
        if (!exists) {
            dictionarydbHelper.addToRecent(storyId);
            Log.d(TAG, "Added to recent: StoryId=" + storyId);
        } else {
            Log.d(TAG, "StoryId already exists in recent: " + storyId);
        }
        dictionarydbHelper.close();
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
                cursor = dictionarydbHelper.getStoryTitles2(titleId);

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
                            storyTitles2.add(new Story(id,idStoryType,nameUnicode,descriptionUnicode));

                            if (storyTitles2.size() + 10 > count) {
                                count = storyTitles2.size();
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
        }
    }
    @Override
    public void onItemClick(Story word) {
        Log.d(TAG, "onItemClick: " + word.getId());
        Log.d(TAG, "onItemClick: " + word.getNameUnicode());
        Log.d(TAG, "onItemClick: " + word.getDescriptionUnicode());
        onWordClickListener.onWordClick(word);
    }

    public interface OnWordClickListener {
        void onWordClick(Story story);
    }

    private void doFilter(String filter) {
        filterWords.clear();
        if (filter.isEmpty()) {
            adapter.setFilteredWordList(storyTitles2);
            adapter.setFilterText("");
        } else {
            if(!MDetect.isUnicode()){
                filter = Rabbit.zg2uni(filter);
            }
            for (Story word : storyTitles2) {
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

