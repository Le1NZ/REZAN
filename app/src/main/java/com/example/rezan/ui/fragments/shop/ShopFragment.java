package com.example.rezan.ui.fragments.shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rezan.R;
import com.example.rezan.data.db.Product;
import com.example.rezan.databinding.FragmentShopBinding;
import com.example.rezan.ui.adapters.ProductsAdapter;
import com.example.rezan.ui.viewModels.ShopFragmentViewModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ShopFragment extends Fragment {

    private ProductsAdapter adapter;
    static public FragmentShopBinding binding;
    private ShopFragmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentShopBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(ShopFragmentViewModel.class);
        viewModel.isUserAdmin();

        binding.shopRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Products"), Product.class)
                        .build();

        adapter = new ProductsAdapter(options);
        binding.shopRecycler.setAdapter(adapter);
        binding.shopRecycler.setItemAnimator(null);
        adapter.startListening();


        viewModel.isUserAdmin.observe(getViewLifecycleOwner(), isAdmin -> {
            if (isAdmin) {
                binding.addNewProduct.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
            }
        });

        binding.addNewProduct.setOnClickListener(v -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_shop_to_addProductFragment);
        });

        return binding.getRoot();
    }

}