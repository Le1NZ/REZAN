package com.example.rezan.ui.fragments.shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.rezan.R;
import com.example.rezan.databinding.FragmentProductBinding;
import com.example.rezan.ui.viewModels.NewsFragmentViewModel;
import com.example.rezan.ui.viewModels.ProductFragmentViewModel;

public class ProductFragment extends Fragment {
    private String name, photo;
    private Integer cost;

    private FragmentProductBinding binding;
    private ProductFragmentViewModel viewModel;

    public ProductFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProductBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this).get(ProductFragmentViewModel.class);

        viewModel.onRefreshed(getArguments());
        viewModel.product.observe(getViewLifecycleOwner(), product -> {
            binding.productMainName.setText(product.getName());
            binding.productMainCost.setText(product.getCost() + " рублей");
            Glide.with(getContext()).load(product.getPhoto()).into(binding.productMainImage);
        });

        binding.backToShop.setOnClickListener(v -> Navigation.findNavController(binding.getRoot()).popBackStack());
        binding.productBuy.setOnClickListener(v -> Navigation.findNavController(binding.getRoot()).navigate(R.id.action_productFragment_to_placeOfShopFragment));

        return binding.getRoot();
    }

}