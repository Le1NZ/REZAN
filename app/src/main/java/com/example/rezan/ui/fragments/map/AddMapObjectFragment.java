package com.example.rezan.ui.fragments.map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.rezan.databinding.FragmentAddMapObjectBinding;
import com.example.rezan.ui.viewModels.AddMapObjectFragmentViewModel;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.InputListener;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.map.Rect;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddMapObjectFragment extends Fragment {

    private FragmentAddMapObjectBinding binding;
    private final Point centerRzn = new Point(54.629224, 39.736880);
    private AddMapObjectFragmentViewModel viewModel;
    private PlacemarkMapObject currPlacemark;
    private InputListener inputListener;
    private final int PICK_IMAGE = 1;
    private InputStream inputStream;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddMapObjectBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(AddMapObjectFragmentViewModel.class);
        inputListener = createInputListener();
        initializeMap();

        binding.btnAddPhotos.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        });

        viewModel.canAddPost.observe(getViewLifecycleOwner(), canAddPost -> {
            if (canAddPost) {
                binding.btnAddPost.setEnabled(true);
            }
        });

        binding.btnAddPost.setOnClickListener(view -> {
            viewModel.addPost(
                    inputStream,
                    binding.etHeader.getText().toString(),
                    binding.etBody.getText().toString()
            );
            binding.progressBar.bringToFront();
            binding.progressBar.setVisibility(View.VISIBLE);
        });

        viewModel.isAddedPost.observe(getViewLifecycleOwner(), isAddedPost -> {
            if (isAddedPost) {
                binding.progressBar.setVisibility(View.GONE);
                Navigation.findNavController(binding.getRoot()).popBackStack();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onStop() {
        super.onStop();
        binding.addObjectMapView.onStop();
    }

    @Override
    public void onStart() {
        binding.addObjectMapView.getMap().addInputListener(inputListener);
        binding.addObjectMapView.onStart();
        super.onStart();
    }

    private IconStyle createIconStyle() {
        return new IconStyle()
                .setAnchor(new PointF(0.5f, 0.5f))
                .setScale(1.5f).setTappableArea(
                        new Rect(new PointF(-3f, -3f), new PointF(3f, 3f))
                );
    }

    private InputListener createInputListener() {
        return new InputListener() {
            @Override
            public void onMapTap(@NonNull Map map, @NonNull Point point) {
                viewModel.addPoint(point);
                if (currPlacemark != null) {
                    binding.addObjectMapView.getMap().getMapObjects().remove(currPlacemark);
                }
                Log.d("myMap", point.toString());
                currPlacemark = binding.addObjectMapView.getMap().getMapObjects().addPlacemark(point);
                currPlacemark.setIconStyle(createIconStyle());
            }

            @Override
            public void onMapLongTap(@NonNull Map map, @NonNull Point point) {

            }
        };
    }

    private void initializeMap() {
        binding.addObjectMapView.getMap().move(new CameraPosition(centerRzn,
                12.0f, 0.0f, 0.0f));
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