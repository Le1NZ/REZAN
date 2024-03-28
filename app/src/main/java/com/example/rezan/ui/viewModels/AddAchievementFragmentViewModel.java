package com.example.rezan.ui.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rezan.data.db.Achievement;
import com.example.rezan.data.db.MapObject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddAchievementFragmentViewModel extends ViewModel {


    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Integer selectedItem;
    private List<String> keysOfItems = new ArrayList<>();

    public MutableLiveData<List<MapObject>> mapObjects = new MutableLiveData<>();
    public MutableLiveData<Boolean> isAddedPost = new MutableLiveData<>();
    public MutableLiveData<Boolean> isPhotoAdded = new MutableLiveData<>(false);

    public void starting() {
        List<MapObject> list = new ArrayList<>();
        database.getReference().child("Map").get()
                .addOnSuccessListener(result -> {
                    for (DataSnapshot postSnapshot : result.getChildren()) {
                        keysOfItems.add(postSnapshot.getKey());
                        MapObject mapObject = postSnapshot.getValue(MapObject.class);
                        list.add(mapObject);
                        mapObjects.setValue(list);
                    }
                });
    }

    public void addPhoto() {
        isPhotoAdded.setValue(true);
    }

    public void addAchievement(InputStream inputStream, String name, Integer score) {
        Achievement achievement = new Achievement(
                score,
                keysOfItems.get(selectedItem),
                name
        );
        DatabaseReference databaseReference = database.getReference().child("Achievements").push();
        String key = databaseReference.getKey();
        addPhoto(inputStream, key, achievement);
    }

    private void addPhoto(InputStream inputStream, String key, Achievement achievement) {
        StorageReference storageReference = storage.getReference().child("images").child("Achievements").child(key).child("Photo");
        storageReference.putStream(inputStream).addOnSuccessListener(taskSnapshot -> {
            getUri(storageReference, key, achievement);
        }).addOnFailureListener(fail -> {
            isAddedPost.setValue(false);
        });

    }

    private void getUri(StorageReference storageReference, String key, Achievement achievement) {
        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    achievement.setPhoto(uri.toString());
                    achievement.setHeader(key);
                    addUriToUser(key, achievement);
                })
                .addOnFailureListener(e -> isAddedPost.setValue(false));
    }

    private void addUriToUser(String key, Achievement achievement) {
        DatabaseReference databaseReference = database.getReference().child("Achievements").child(key);
        databaseReference.setValue(achievement).addOnCompleteListener(task -> {
            isAddedPost.setValue(task.isSuccessful());
        });
    }

    public void setSelectedItem(Integer selectedItem) {
        this.selectedItem = selectedItem;
    }
}
