package com.example.rezan.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rezan.data.db.Product;
import com.example.rezan.data.db.User;
import com.example.rezan.databinding.FragmentRatingBinding;
import com.example.rezan.ui.adapters.ProductsAdapter;
import com.example.rezan.ui.adapters.RatingAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class RatingFragment extends Fragment {

    private RatingAdapter adapter;
    FragmentRatingBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRatingBinding.inflate(inflater, container, false);

        LinearLayoutManager myLinearLayoutManager = new LinearLayoutManager(getActivity());
        myLinearLayoutManager.setReverseLayout(true);
        myLinearLayoutManager.setStackFromEnd(true);

        binding.recyclerRating.setLayoutManager(myLinearLayoutManager);
        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("Score").limitToFirst(50), User.class)
                        .build();

        adapter = new RatingAdapter(options);
        binding.recyclerRating.setAdapter(adapter);
        binding.recyclerRating.setItemAnimator(null);
        adapter.startListening();

        binding.backToShop.setOnClickListener(v -> Navigation.findNavController(binding.getRoot()).popBackStack());

        return binding.getRoot();
    }
}