package com.example.rezan.ui.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rezan.R;
import com.example.rezan.data.db.Achievement;
import com.example.rezan.ui.fragments.account.AccountFragment;
import com.example.rezan.ui.fragments.account.AchievementsFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;

public class AchievementsAdapter extends FirebaseRecyclerAdapter<Achievement, AchievementsAdapter.MyViewHolder> {

    FirebaseAuth mAuth = AccountFragment.mAuth;

    public AchievementsAdapter(@NonNull FirebaseRecyclerOptions<Achievement> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Achievement achievement) {

        Boolean isAchievementDone = false;

        if (achievement.getUsers() != null) {
            isAchievementDone = achievement.getUsers().get(mAuth.getCurrentUser().getUid()) != null;
        }

        holder.name.setText(achievement.getName());
        holder.score.setText(achievement.getScore() + " баллов");
        if (isAchievementDone) {
            holder.isDone.setText("Выполнено!");
        } else {
            holder.isDone.setText("Не выполнено:(");
        }
        Glide.with(holder.image.getContext()).load(achievement.getPhoto()).into(holder.image);

        Boolean finalIsAchievementDone = isAchievementDone;
        holder.constraintAchievements.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("mapObject", achievement.getMapObject());
            bundle.putSerializable("name", achievement.getName());
            bundle.putSerializable("photo", achievement.getPhoto());
            bundle.putSerializable("score", achievement.getScore());
            bundle.putSerializable("isDone", finalIsAchievementDone);
            bundle.putSerializable("header", achievement.getHeader());
            Navigation.findNavController(AchievementsFragment.binding.getRoot()).navigate(R.id.action_achievementsFragment_to_achievementFragment, bundle);
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.achievements_recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView image;
        TextView name, score, isDone;

        ConstraintLayout constraintAchievements;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.achievementImage);
            name = itemView.findViewById(R.id.achievementName);
            score = itemView.findViewById(R.id.achievementScore);
            isDone = itemView.findViewById(R.id.achievementIsDone);
            constraintAchievements = itemView.findViewById(R.id.constraintAchievements);
        }
    }

}
