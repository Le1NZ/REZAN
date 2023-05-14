package com.example.rezan.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rezan.data.db.Product;
import com.example.rezan.databinding.FragmentShopBinding;
import com.example.rezan.ui.adapters.ProductsAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ShopFragment extends Fragment {

    private ProductsAdapter adapter;
    static public FragmentShopBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentShopBinding.inflate(inflater, container, false);

        binding.shopRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Products"), Product.class)
                        .build();

        adapter = new ProductsAdapter(options);
        binding.shopRecycler.setAdapter(adapter);
        binding.shopRecycler.setItemAnimator(null);
        adapter.startListening();

        return binding.getRoot();
    }

}