package com.example.rezan.ui.fragments.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.rezan.databinding.FragmentAddPostBinding;
import com.example.rezan.ui.viewModels.AddPostFragmentViewModel;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddPostFragment extends Fragment {

    private FragmentAddPostBinding binding;
    private final int PICK_IMAGE = 1;
    private AddPostFragmentViewModel viewModel;
    private InputStream inputStream;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddPostBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(AddPostFragmentViewModel.class);

        binding.btnAddPhotos.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        });

        viewModel.isAddedPhotos.observe(getViewLifecycleOwner(), isAddedPhoto -> {
            if (isAddedPhoto)
                binding.btnAddPost.setEnabled(true);
        });

        binding.btnAddPost.setOnClickListener(view -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            viewModel.addPost(inputStream, binding.etHeader.getText().toString(), binding.etBody.getText().toString());
        });

        viewModel.isAddedPost.observe(getViewLifecycleOwner(), isAdded -> {
            if (isAdded) {
                Navigation.findNavController(binding.getRoot()).popBackStack();
                binding.progressBar.setVisibility(View.GONE);
            }
        });

        binding.btnBack.setOnClickListener(v -> {
            Navigation.findNavController(binding.getRoot()).popBackStack();
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            try {
                inputStream = requireContext().getContentResolver().openInputStream(data.getData());
                viewModel.isAddedPhotos.setValue(true);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}