package com.example.rezan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class NewsAdapter extends FirebaseRecyclerAdapter<News, NewsAdapter.MyViewHolder> {

    public NewsAdapter(@NonNull FirebaseRecyclerOptions<News> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull News news) {
        holder.head.setText(news.getHead());
        holder.desc.setText(news.getDesc());
        Glide.with(holder.image_1.getContext()).load(news.getPhoto_1()).into(holder.image_1);
        Glide.with(holder.image_2.getContext()).load(news.getPhoto_2()).into(holder.image_2);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recycler_item, parent,false);
        return new MyViewHolder(view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image_1, image_2;
        TextView head,desc;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            image_1 = itemView.findViewById(R.id.NewsImageFirst);
            image_2 = itemView.findViewById(R.id.NewsImageSecond);
            head = itemView.findViewById(R.id.NewsTextHeader);
            desc = itemView.findViewById(R.id.NewsTextDesc);
        }
    }
}
