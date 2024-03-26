package com.example.rezan.ui.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rezan.R;
import com.example.rezan.data.db.News;
import com.example.rezan.ui.fragments.home.HomeFragment;
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

        holder.constraintLayout.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("head", news.getHead());
            bundle.putSerializable("desc", news.getDesc());
            bundle.putSerializable("photo_1", news.getPhoto_1());
            Navigation.findNavController(HomeFragment.binding.getRoot()).navigate(R.id.action_navigation_home_to_newsFragment, bundle);
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image_1;
        TextView head, desc;
        ConstraintLayout constraintLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image_1 = itemView.findViewById(R.id.NewsImageFirst);
            head = itemView.findViewById(R.id.NewsTextHeader);
            desc = itemView.findViewById(R.id.NewsTextDesc);
            constraintLayout = itemView.findViewById(R.id.newsConstraintLayout);
        }
    }
}
