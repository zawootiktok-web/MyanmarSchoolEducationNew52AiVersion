package com.mmschooledu.MyanmarSpelling.adapters;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//
//
//import mm.pndaza.thupyadictionary.R;
//import mm.pndaza.thupyadictionary.models.Story;
//
//public class DictionaryTitleAdapter extends RecyclerView.Adapter<DictionaryTitleAdapter.DictionaryTitleViewHolder> {
//
//    private ArrayList<Story> storyList;
//    private OnItemClickListener listener;
//
//    private String filterText = "";
//    public interface OnItemClickListener {
//        void onItemClick(Story story);
//    }
//
//    public DictionaryTitleAdapter(ArrayList<Story> storyList, OnItemClickListener listener) {
//        this.storyList = storyList;
//        this.listener = listener;
//    }
//
//    @NonNull
//    @Override
//    public DictionaryTitleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dictionary_title_item, parent, false);
//        return new DictionaryTitleViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull DictionaryTitleViewHolder holder, int position) {
//        Story story = storyList.get(position);
//        holder.nameUnicode.setText(story.getNameUnicode());
//        holder.descriptionUnicode.setText(story.getDescriptionUnicode());
//
//        holder.itemView.setOnClickListener(v -> listener.onItemClick(story));
//    }
//
//    @Override
//    public int getItemCount() {
//        return storyList.size();
//    }
//
//    public void setFilteredWordList(ArrayList<Story> filteredList) {
//        this.storyList = filteredList;
//        notifyDataSetChanged();
//    }
//
//    public static class DictionaryTitleViewHolder extends RecyclerView.ViewHolder {
//        TextView nameUnicode, descriptionUnicode;
//
//        public DictionaryTitleViewHolder(@NonNull View itemView) {
//            super(itemView);
//            nameUnicode = itemView.findViewById(R.id.tv_name_unicode);
//            descriptionUnicode = itemView.findViewById(R.id.tv_description_unicode);
//        }
//    }
//}


//
//
//import android.graphics.Color;
//import android.text.SpannableString;
//import android.text.Spanned;
//import android.text.style.BackgroundColorSpan;
//import android.text.style.ForegroundColorSpan;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
//
//import java.util.ArrayList;
//
//import mm.pndaza.thupyadictionary.R;
//import mm.pndaza.thupyadictionary.models.Story;
//import mm.pndaza.thupyadictionary.utils.MDetect;
//import mm.pndaza.thupyadictionary.utils.SharePref;
//
//public class DictionaryTitleAdapter extends RecyclerView.Adapter<DictionaryTitleAdapter.ViewHolder>
//        implements FastScrollRecyclerView.SectionedAdapter {
//
//    private ArrayList<Story> storyList;
//    private OnItemClickListener listener;
//    private String filterText = "";
//    private int fontSize;
//
//    public DictionaryTitleAdapter(ArrayList<Story> storyList, OnItemClickListener listener) {
//        this.storyList = storyList;
//        this.listener = listener;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        fontSize = SharePref.getInstance(parent.getContext()).getPrefFontSize();
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dictionary_title_item, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Story story = storyList.get(position);
//
//        if (filterText.isEmpty()) {
//            holder.nameUnicode.setText(MDetect.getDeviceEncodedText(story.getNameUnicode()));
//            holder.descriptionUnicode.setText(MDetect.getDeviceEncodedText(story.getDescriptionUnicode()));
//        } else {
//            holder.nameUnicode.setText(getHighlightedText(story.getNameUnicode()));
//            holder.descriptionUnicode.setText(getHighlightedText(story.getDescriptionUnicode()));
//        }
//
//        holder.nameUnicode.setTextSize(fontSize);
//        holder.descriptionUnicode.setTextSize(fontSize);
//
//        // Click event for both left and right columns
//        holder.nameUnicode.setOnClickListener(v -> listener.onItemClick(story));
//        holder.descriptionUnicode.setOnClickListener(v -> listener.onItemClick(story));
//    }
//
//    @Override
//    public int getItemCount() {
//        return storyList.size();
//    }
//
//    public void setFilteredWordList(ArrayList<Story> filteredList) {
//        this.storyList = filteredList;
//        notifyDataSetChanged();
//    }
//
//    public void setFilterText(String filterText) {
//        this.filterText = filterText;
//    }
//
//    private SpannableString getHighlightedText(String word) {
//        String wordInDeviceEncoding = MDetect.getDeviceEncodedText(word);
//        String filterInDeviceEncoding = MDetect.getDeviceEncodedText(filterText);
//
//        SpannableString highlightedText = new SpannableString(wordInDeviceEncoding);
//        int start_index = wordInDeviceEncoding.indexOf(filterInDeviceEncoding);
//
//        if (start_index != -1) {
//            int end_index = start_index + filterInDeviceEncoding.length();
//
//            highlightedText.setSpan(
//                    new ForegroundColorSpan(Color.RED),
//                    start_index, end_index,
//                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
//            );
//
//            highlightedText.setSpan(
//                    new BackgroundColorSpan(Color.YELLOW),
//                    start_index, end_index,
//                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
//            );
//        }
//
//        return highlightedText;
//    }
//
//    @NonNull
//    @Override
//    public String getSectionName(int position) {
//        char firstChar = storyList.get(position).getNameUnicode().charAt(0);
//        return String.valueOf(firstChar);
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        TextView nameUnicode, descriptionUnicode;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            nameUnicode = itemView.findViewById(R.id.tv_name_unicode);
//            descriptionUnicode = itemView.findViewById(R.id.tv_description_unicode);
//        }
//    }
//
//    public interface OnItemClickListener {
//        void onItemClick(Story story);
//    }
//}




import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


import com.mmschooledu.MyanmarSpelling.models.Story;
import com.mmschooledu.MyanmarSpelling.utils.MDetect;
import com.mmschooledu.MyanmarSpelling.utils.SharePref;
import com.mmschooledu.R;

public class DictionaryTitleAdapter extends RecyclerView.Adapter<DictionaryTitleAdapter.ViewHolder> {

    private ArrayList<Story> storyList;
    private ArrayList<Story> allStoryList; // For keeping original data
    private OnItemClickListener listener;
    private String filterText = "";
    private static int fontSize = 18;
    public interface OnItemClickListener {
        void onItemClick(Story story);
    }

    public DictionaryTitleAdapter(ArrayList<Story> storyList, OnItemClickListener listener) {
        this.storyList = new ArrayList<>(storyList);
        this.allStoryList = storyList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dictionary_title_item, parent, false);
        fontSize = SharePref.getInstance(parent.getContext()).getPrefFontSize();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Story story = storyList.get(position);

        String nameUnicode = MDetect.getDeviceEncodedText(story.getNameUnicode());
        String descriptionUnicode = MDetect.getDeviceEncodedText(story.getDescriptionUnicode());

        // Highlight Search Text
        if (!filterText.isEmpty()) {
            holder.nameUnicode.setText(getHighlightedText(nameUnicode, filterText));
            holder.descriptionUnicode.setText(getHighlightedText(descriptionUnicode, filterText));
        } else {
            holder.nameUnicode.setText(nameUnicode);
            holder.descriptionUnicode.setText(descriptionUnicode);
        }

        holder.nameUnicode.setOnClickListener(v -> listener.onItemClick(story));
        holder.descriptionUnicode.setOnClickListener(v -> listener.onItemClick(story));
    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }

    public void filter(String query) {
        filterText = query;
        if (query.isEmpty()) {
            storyList = new ArrayList<>(allStoryList);
        } else {
            ArrayList<Story> filteredList = new ArrayList<>();
            for (Story story : allStoryList) {
                String nameUnicode = MDetect.getDeviceEncodedText(story.getNameUnicode());
                String descriptionUnicode = MDetect.getDeviceEncodedText(story.getDescriptionUnicode());

                if (nameUnicode.contains(query) || descriptionUnicode.contains(query)) {
                    filteredList.add(story);
                }
            }
            storyList = filteredList;
        }
        notifyDataSetChanged();
    }

    private SpannableString getHighlightedText(String text, String query) {
        SpannableString spannable = new SpannableString(text);
        int start = text.indexOf(query);
        if (start >= 0) {
            int end = start + query.length();
            spannable.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new BackgroundColorSpan(Color.YELLOW), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannable;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameUnicode;
        TextView descriptionUnicode;

        public ViewHolder(View itemView) {
            super(itemView);
            nameUnicode = itemView.findViewById(R.id.tv_name_unicode);
            descriptionUnicode = itemView.findViewById(R.id.tv_description_unicode);

//            tv_name_unicode = itemView.findViewById(R.id.tv_word);
            nameUnicode.setTextSize(fontSize);
            descriptionUnicode.setTextSize(fontSize); // Font size is applied here
//            itemView.setOnClickListener(this);
        }
    }
}
