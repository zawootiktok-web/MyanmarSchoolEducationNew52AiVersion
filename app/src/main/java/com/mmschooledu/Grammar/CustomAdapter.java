package com.mmschooledu.Grammar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mmschooledu.EbookAudioShelf.EbookBookItem;
import com.mmschooledu.R;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter {
    Context context;
    List<EbookBookItem> data,list;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, List<EbookBookItem> data) {
        this.context = applicationContext;
        this.data=data;
        list=new ArrayList<>();
        list.addAll(data);
        inflter = (LayoutInflater.from(applicationContext));
    }



    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.list_item, null);
        TextView title = (TextView)view.findViewById(R.id.tvCard);
        ImageView cover = (ImageView) view.findViewById(R.id.ivCard);
        title.setText(data.get(i).title);
        if(!data.get(i).img.isEmpty()){
            Glide.with(context).load(data.get(i).img).into(cover);
        }
        return view;
    }

    public void filter(String charText) {

        charText = charText.toLowerCase();

        data.clear();

        if (charText.isEmpty()) {

            data.addAll(list);

        } else {
            for (EbookBookItem cd : list)
            {
                if (cd.title.toLowerCase().contains(charText))
                {
                    data.add(cd);
                }
            }
        }
        notifyDataSetChanged();
    }

}
