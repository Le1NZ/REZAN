package com.example.rezan.ui.fragments.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rezan.R;
import com.example.rezan.data.db.Achievement;
import com.example.rezan.databinding.FragmentAchievementsBinding;
import com.example.rezan.ui.adapters.AchievementsAdapter;
import com.example.rezan.ui.viewModels.AchievementsViewModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class AchievementsFragment extends Fragment {

    public static FragmentAchievementsBinding binding;
    AchievementsAdapter adapter;
    AchievementsViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAchievementsBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(AchievementsViewModel.class);
        viewModel.isUserAdmin();

        FirebaseRecyclerOptions<Achievement> options =
                new FirebaseRecyclerOptions.Builder<Achievement>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Achievements"), Achievement.class)
                        .build();

        adapter = new AchievementsAdapter(options);
        binding.achievementsRecycler.setAdapter(adapter);
        binding.achievementsRecycler.setItemAnimator(null);
        binding.achievementsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.startListening();

        binding.backToRegistered.setOnClickListener(v -> Navigation.findNavController(binding.getRoot()).popBackStack());

        viewModel.isUserAdmin.observe(getViewLifecycleOwner(), isAdmin -> {
            if (isAdmin) {
                binding.addNewPost.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
            }
        });

        binding.addNewPost.setOnClickListener(v -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_achievementsFragment_to_addAchievementFragment);
        });

        return binding.getRoot();
    }

}