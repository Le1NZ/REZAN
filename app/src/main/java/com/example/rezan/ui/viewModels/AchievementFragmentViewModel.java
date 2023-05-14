package com.example.rezan.ui.viewModels;

import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rezan.data.db.Achievement;

public class AchievementFragmentViewModel extends ViewModel {


    public static MutableLiveData<Achievement> achievement = new MutableLiveData<>();

    public void onRefreshed(Bundle bundle) {
        Achievement achievementHelper = new Achievement(
                bundle.getSerializable("name").toString(),
                bundle.getString("photo"),
                (Integer) bundle.getSerializable("score"),
                (Boolean) bundle.getSerializable("isDone")
        );
        achievement.setValue(achievementHelper);
    }

    

}
