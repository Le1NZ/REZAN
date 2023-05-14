package com.example.rezan.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.rezan.R;
import com.example.rezan.data.db.News;
import com.example.rezan.databinding.FragmentNewsBinding;
import com.example.rezan.ui.viewModels.NewsFragmentViewModel;


public class NewsFragment extends Fragment {

    FragmentNewsBinding binding;
    NewsFragmentViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNewsBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(NewsFragmentViewModel.class);

        viewModel.onRefreshed(getArguments());
        viewModel.news.observe(getViewLifecycleOwner(), news -> {
            binding.thisNewsHead.setText(news.getHead());
            binding.thisNewsDesc.setText(news.getDesc());
            Glide.with(getContext()).load(news.getPhoto_1()).into(binding.thisNewsPhoto);
        });

        binding.backToAchievements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(binding.getRoot()).popBackStack();
            }
        });

        return binding.getRoot();
    }
}