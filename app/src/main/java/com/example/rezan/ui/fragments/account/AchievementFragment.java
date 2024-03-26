package com.example.rezan.ui.fragments.account;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.rezan.R;
import com.example.rezan.data.network.AchievementsForFirebase;
import com.example.rezan.databinding.FragmentAchievementBinding;
import com.example.rezan.ui.viewModels.AchievementFragmentViewModel;
import com.example.rezan.ui.viewModels.MapFragmentViewModel;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.location.Location;
import com.yandex.mapkit.location.LocationListener;
import com.yandex.mapkit.location.LocationManager;
import com.yandex.mapkit.location.LocationStatus;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class AchievementFragment extends Fragment {

    private FragmentAchievementBinding binding;
    private AchievementFragmentViewModel viewModel;
    private MapFragmentViewModel viewModelMap;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private final Double EPS = 0.01;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAchievementBinding.inflate(inflater, container, false);

        // This ViewModel
        viewModel = new ViewModelProvider(this).get(AchievementFragmentViewModel.class);
        // ViewModel for map and info about mapObject
        viewModelMap = new ViewModelProvider(getActivity()).get(MapFragmentViewModel.class);
        // Key of this mapObject
        String key = getArguments().getSerializable("mapObject").toString();
        // Initialize MapKit
        MapKitFactory.initialize(requireContext());
        // LocationManager
        locationManager = MapKitFactory.getInstance().createLocationManager();
        // IsDone var
        AtomicReference<Boolean> isDone = new AtomicReference<>(false);

        // Filling info about this mapObject
        viewModel.onRefreshed(getArguments());
        viewModel.achievement.observe(getViewLifecycleOwner(), achievement -> {
            binding.thisAchievementName.setText(achievement.getName());
            binding.thisAchievementScore.setText(achievement.getScore() + " очков");
            Glide.with(getContext()).load(achievement.getPhoto()).into(binding.thisAchievementImage);
            if (achievement.getDone()) {
                binding.thisAchievementIsDone.setText("Выполнено!");
            } else {
                binding.thisAchievementIsDone.setText("Еще не выполнено:(");
            }
            isDone.set(achievement.getDone());
        });

        AtomicReference<Double> latitude = new AtomicReference<>(0.0);
        AtomicReference<Double> longitude = new AtomicReference<>(0.0);

        // Getting point of this mapObject from ViewModel of Map
        viewModelMap.download();
        viewModelMap.map.observe(getViewLifecycleOwner(), objectMap -> {
            latitude.set(objectMap.get(key).getLatitude());
            longitude.set(objectMap.get(key).getLongitude());
        });

        // Listener for check location
        locationListener = new LocationListener() {
            @Override
            public void onLocationUpdated(@NonNull Location location) {
                binding.progressBar.setVisibility(View.GONE);
                if (location.getPosition().getLongitude() - longitude.get() <= EPS
                        && location.getPosition().getLatitude() - latitude.get() <= EPS) {

                    AchievementsForFirebase.addUserToAchievement((String) getArguments().getSerializable("header"));
                    AchievementsForFirebase.addScoreToUser((Integer) getArguments().getSerializable("score"));
                    binding.thisAchievementIsDone.setText("Выполнено!");
                    Toast.makeText(requireContext(), "Очки начислены", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(requireContext(), "Вы где-то не там...", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onLocationStatusUpdated(@NonNull LocationStatus locationStatus) {
            }
        };

        // ButtonBack
        binding.backToAchievements.setOnClickListener(v -> Navigation.findNavController(binding.getRoot()).popBackStack());

        // ButtonToPlace
        binding.thisAchievementToPlace.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("key", key);
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_achievementFragment_to_mapObjectFragment, bundle);
        });

        // CheckLocation
        binding.thisAchievementCheckLocation.setOnClickListener(v -> {

            if (!isDone.get()) {
                binding.progressBar.setVisibility(View.VISIBLE);
                locationManager.requestSingleUpdate(locationListener);
            } else {
                Toast.makeText(requireContext(), "Вы уже получили награду за это достижение!", Toast.LENGTH_LONG).show();
            }

        });

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        MapKitFactory.getInstance().onStop();
    }

}