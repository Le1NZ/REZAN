package com.example.rezan.ui.fragments.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.rezan.databinding.FragmentAddAchievementBinding;
import com.example.rezan.ui.viewModels.AddAchievementFragmentViewModel;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class AddAchievementFragment extends Fragment {

    private FragmentAddAchievementBinding binding;
    private AddAchievementFragmentViewModel viewModel;
    private final int PICK_IMAGE = 1;
    private InputStream inputStream;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddAchievementBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(AddAchievementFragmentViewModel.class);
        viewModel.starting();

        viewModel.mapObjects.observe(getViewLifecycleOwner(), result -> {
            ArrayList<String> arrayList = new ArrayList<>();
            for (int i = 0; i < result.size(); i++) {
                arrayList.add(result.get(i).getHead());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                    android.R.layout.simple_spinner_item, arrayList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.spinner.setAdapter(adapter);
            binding.progressBar.setVisibility(View.INVISIBLE);
        });

        binding.btnAddPhotos.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        });

        viewModel.isPhotoAdded.observe(getViewLifecycleOwner(), isAdded -> {
            if (isAdded) {
                binding.btnAddPost.setEnabled(true);
            }
        });

        binding.btnAddPost.setOnClickListener(v -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            viewModel.addAchievement(
                    inputStream,
                    binding.etHeader.getText().toString(),
                    Integer.parseInt(binding.etBody.getText().toString())
            );
        });

        viewModel.isAddedPost.observe(getViewLifecycleOwner(), result -> {
            binding.progressBar.setVisibility(View.GONE);
            if (result) {
                Navigation.findNavController(binding.getRoot()).popBackStack();
            }
        });

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.setSelectedItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            try {
                inputStream = requireContext().getContentResolver().openInputStream(data.getData());
                viewModel.addPhoto();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

}