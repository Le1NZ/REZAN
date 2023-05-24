package com.example.rezan.ui.fragments;


import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rezan.R;
import com.example.rezan.data.db.News;
import com.example.rezan.databinding.FragmentHomeBinding;
import com.example.rezan.ui.adapters.NewsAdapter;
import com.example.rezan.ui.viewModels.HomeFragmentViewModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment extends Fragment {

    NewsAdapter adapter;
    public static FragmentHomeBinding binding;
    private HomeFragmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);


        if (isNetworkAvailable(requireContext())) {

            viewModel = new ViewModelProvider(this).get(HomeFragmentViewModel.class);
            viewModel.onRefreshed();
            viewModel.weather.observe(getViewLifecycleOwner(), thisWeather -> {
                binding.topWeatherDeg.setText(Math.round(thisWeather.main.temp - 273.15) + " °C");
                if (thisWeather.weather.get(0).main.equals("Thunderstorm"))
                    binding.topWeatherIcon.setImageResource(R.drawable.ic_thunderstorm);
                else if (thisWeather.weather.get(0).main.equals("Rain"))
                    binding.topWeatherIcon.setImageResource(R.drawable.ic_rain);
                else if (thisWeather.weather.get(0).main.equals("Snow"))
                    binding.topWeatherIcon.setImageResource(R.drawable.ic_snow);
                else if (thisWeather.weather.get(0).main.equals("Drizzle"))
                    binding.topWeatherIcon.setImageResource(R.drawable.ic_rain);
                else if (thisWeather.weather.get(0).main.equals("Clear"))
                    binding.topWeatherIcon.setImageResource(R.drawable.ic_clear_sky);
                else
                    binding.topWeatherIcon.setImageResource(R.drawable.ic_clouds);
            });

        } else {
            binding.topWeatherDeg.setText("НЕТ СЕТИ");
        }

        FirebaseRecyclerOptions<News> options =
                new FirebaseRecyclerOptions.Builder<News>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("News"), News.class)
                        .build();

        binding.homeRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new NewsAdapter(options);
        binding.homeRecycler.setAdapter(adapter);
        binding.homeRecycler.setItemAnimator(null);
        adapter.startListening();

        return binding.getRoot();
    }

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

}