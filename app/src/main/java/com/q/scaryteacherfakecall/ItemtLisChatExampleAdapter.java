package com.q.scaryteacherfakecall;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

class ItemLisChatExampleAdapter extends RecyclerView.Adapter<ItemLisChatExampleAdapter.MyViewHolder> {


    private String[] listModel;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;


        public MyViewHolder(View view) {
            super(view);

            title =  view.findViewById(R.id.textItem);

        }

        @Override
        public void onClick(View view) {

        }
    }


    public ItemLisChatExampleAdapter(String[] listModel) {
        this.listModel = listModel;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chatexample_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.title.setText(listModel[position]);

    }

    @Override
    public int getItemCount() {
        return listModel.length;
    }
}
