package com.example.rezan.ui.viewModels;

import android.os.Bundle;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rezan.data.db.News;

import io.reactivex.disposables.Disposable;

public class NewsFragmentViewModel extends ViewModel {

    public static MutableLiveData<News> news = new MutableLiveData<>();

    public void onRefreshed(Bundle bundle) {
        News newsHelper = new News(
                bundle.getSerializable("head").toString(),
                bundle.getSerializable("desc").toString(),
                bundle.getSerializable("photo_1").toString()
        );
        news.setValue(newsHelper);
    }
}
