package com.example.rezan.ui.viewModels;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rezan.data.db.User;
import com.example.rezan.data.db.Weather;
import com.example.rezan.data.network.WeatherAPI;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragmentViewModel extends ViewModel {
    public MutableLiveData<Weather> weather = new MutableLiveData<>();
    public MutableLiveData<Boolean> isUserAdmin = new MutableLiveData<>();
    private static FirebaseAuth mAuth;
    private DatabaseReference ref;
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

    public void isUserAdmin() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth != null && mAuth.getCurrentUser() != null) {
            ref = database.getReference("Users").child(mAuth.getCurrentUser().getUid());
            ref.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    User currUser = task.getResult().getValue(User.class);
                    isUserAdmin.setValue(currUser.getAdmin());
                } else {
                    isUserAdmin.setValue(false);
                }
            });
        } else {
            isUserAdmin.setValue(false);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        disposable.dispose();
    }
}
