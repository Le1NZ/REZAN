package com.example.rezan.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.rezan.databinding.FragmentMapObjectBinding;
import com.example.rezan.ui.viewModels.MapFragmentViewModel;

public class MapObjectFragment extends Fragment {

    private FragmentMapObjectBinding binding;
    private MapFragmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMapObjectBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(getActivity()).get(MapFragmentViewModel.class);

        String key = (String) getArguments().getSerializable("key");

        viewModel.map.observe(getViewLifecycleOwner(), objectMap -> {
            binding.thisMapObjectHead.setText(objectMap.get(key).getHead());
            binding.thisMapObjectDesc.setText(objectMap.get(key).getDesc());
            Glide.with(getContext()).load(objectMap.get(key).getPhoto()).into(binding.thisMapObjectPhoto);
        });

        binding.backToMap.setOnClickListener(v -> Navigation.findNavController(binding.getRoot()).popBackStack());

        return binding.getRoot();
    }
}