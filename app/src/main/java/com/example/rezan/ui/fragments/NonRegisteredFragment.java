package com.example.rezan.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.rezan.R;
import com.example.rezan.databinding.FragmentNonRegisteredBinding;

public class NonRegisteredFragment extends Fragment {

    FragmentNonRegisteredBinding binding;

    public NonRegisteredFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNonRegisteredBinding.inflate(inflater, container, false);

        binding.goLogIn.setOnClickListener(v -> Navigation.findNavController(binding.getRoot()).navigate(R.id.action_nonRegisteredFragment_to_verificationPhoneFragment));

        return binding.getRoot();
    }
}