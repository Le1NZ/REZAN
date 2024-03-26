package com.example.rezan.ui.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
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
            Glide.with(requireContext()).load(news.getPhoto_1()).into(binding.thisNewsPhoto);
        });

        binding.backToAchievements.setOnClickListener(v -> {
            Navigation.findNavController(binding.getRoot()).popBackStack();
        });

        return binding.getRoot();
    }
}