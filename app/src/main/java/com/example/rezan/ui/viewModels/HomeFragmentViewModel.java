package com.example.rezan.ui.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rezan.data.db.Weather;
import com.example.rezan.data.network.WeatherAPI;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragmentViewModel extends ViewModel {
    public MutableLiveData<Weather> weather = new MutableLiveData<>();
    Disposable disposable;

    public void onRefreshed() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeatherAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);
        disposable = weatherAPI.getWeather(WeatherAPI.CITY, WeatherAPI.API_KEY, WeatherAPI.LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(thisWeather -> {
                    weather.setValue(thisWeather);
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        disposable.dispose();
    }
}
