package com.example.rezan.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rezan.R;
import com.example.rezan.data.db.Achievement;
import com.example.rezan.databinding.FragmentAchievementsBinding;
import com.example.rezan.ui.adapters.AchievementsAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class AchievementsFragment extends Fragment {

    public static FragmentAchievementsBinding binding;
    AchievementsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAchievementsBinding.inflate(inflater, container, false);

        FirebaseRecyclerOptions<Achievement> options =
                new FirebaseRecyclerOptions.Builder<Achievement>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Achievements"), Achievement.class)
                        .build();

        adapter = new AchievementsAdapter(options);
        binding.achievementsRecycler.setAdapter(adapter);
        binding.achievementsRecycler.setItemAnimator(null);
        binding.achievementsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.startListening();

        binding.backToRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(binding.getRoot()).popBackStack();
            }
        });

        return binding.getRoot();
    }

}