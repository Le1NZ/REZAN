package com.example.rezan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rezan.databinding.FragmentAccountBinding;
import com.example.rezan.databinding.FragmentHomeBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    NewsAdapter adapter;
    static FragmentHomeBinding binding;
    Disposable disposable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        binding.homeRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseRecyclerOptions<News> options =
                new FirebaseRecyclerOptions.Builder<News>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("News"), News.class)
                        .build();

        adapter = new NewsAdapter(options);
        binding.homeRecycler.setAdapter(adapter);
        binding.homeRecycler.setItemAnimator(null);

        setWeather();

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    private void onError(Throwable throwable) {
        Log.wtf("MyError", throwable.toString());
        binding.topWeatherDeg.setText("Нет сети");
    }

    private void onSuccess(Weather weather) {
        binding.topWeatherDeg.setText(Math.round(weather.main.temp - 273.15) + " °C");
        if (weather.weather.get(0).main.equals("Thunderstorm"))
            binding.topWeatherIcon.setImageResource(R.drawable.ic_thunderstorm);
        else if (weather.weather.get(0).main.equals("Rain"))
            binding.topWeatherIcon.setImageResource(R.drawable.ic_rain);
        else if (weather.weather.get(0).main.equals("Snow"))
            binding.topWeatherIcon.setImageResource(R.drawable.ic_snow);
        else if (weather.weather.get(0).main.equals("Drizzle"))
            binding.topWeatherIcon.setImageResource(R.drawable.ic_rain);
        else if (weather.weather.get(0).main.equals("Clear"))
            binding.topWeatherIcon.setImageResource(R.drawable.ic_clear_sky);
        else
            binding.topWeatherIcon.setImageResource(R.drawable.ic_clouds);
    }

    public void setWeather() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeatherAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);
        disposable = weatherAPI.getWeather(WeatherAPI.CITY, WeatherAPI.API_KEY, WeatherAPI.LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError);
    }
}