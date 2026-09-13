package com.mmschooledu.examresults.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

//import com.lotee.examresults.R;
//import com.lotee.examresults.model.Bookmark;

import com.mmschooledu.R;
import com.mmschooledu.examresults.model.Bookmark;

import java.util.List;
public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {
    private List<Bookmark> bookmarkList;
    private OnDeleteButtonClickListener onDeleteButtonClickListener;
    private OnItemClickListener onItemClickListener;

    public interface OnDeleteButtonClickListener {
        void onDeleteButtonClick(int position);
    }

    public interface OnItemClickListener {
        void onItemClickListener(String title, String url);
    }

    public BookmarkAdapter(List<Bookmark> bookmarkList, OnDeleteButtonClickListener onDeleteButtonClickListener, OnItemClickListener onItemClickListener) {
        this.bookmarkList = bookmarkList;
        this.onDeleteButtonClickListener = onDeleteButtonClickListener;
        this.onItemClickListener = onItemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView yearTextView;
        public TextView titleTextView;
        public TextView urlTextView;
        public CardView deleteButton;
        public CardView cv_item;

        public ViewHolder(View itemView) {
            super(itemView);
            yearTextView = itemView.findViewById(R.id.yearTextView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            urlTextView = itemView.findViewById(R.id.urlTextView);
            deleteButton = itemView.findViewById(R.id.cv_delete);
            cv_item = itemView.findViewById(R.id.cv_item);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bookmark, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Bookmark bookmark = bookmarkList.get(position);
        holder.yearTextView.setText(bookmark.getTitle().split(",,")[0]);
        holder.titleTextView.setText(bookmark.getTitle().split(",,")[1]);
        holder.urlTextView.setText(bookmark.getDate());

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteButtonClickListener != null) {
                    onDeleteButtonClickListener.onDeleteButtonClick(position);
                }
            }
        });

        holder.cv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClickListener(bookmark.getTitle(), bookmark.getUrl());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookmarkList.size();
    }
}
