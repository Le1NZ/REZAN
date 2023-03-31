package com.example.rezan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.rezan.databinding.FragmentProductBinding;

public class ProductFragment extends Fragment {
    private String name, photo;
    private Integer cost;

    FragmentProductBinding binding;

    public ProductFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProductBinding.inflate(inflater, container, false);

        name = (String) getArguments().getSerializable("name");
        photo = (String) getArguments().getSerializable("photo");
        cost = (Integer) getArguments().getSerializable("cost");
        binding.productMainName.setText(name);
        binding.productMainCost.setText(cost + " рублей");
        Glide.with(getContext()).load(photo).into(binding.productMainImage);

        binding.backToShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_productFragment_to_navigation_shop);
            }
        });

        return binding.getRoot();
    }
    private void onBackPressed() {
        Navigation.findNavController(binding.getRoot()).popBackStack();
    }

}