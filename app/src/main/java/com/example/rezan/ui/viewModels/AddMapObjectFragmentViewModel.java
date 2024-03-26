package com.example.rezan.ui.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rezan.data.db.MapObject;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.yandex.mapkit.geometry.Point;

import java.io.InputStream;

public class AddMapObjectFragmentViewModel extends ViewModel {

    public MutableLiveData<Point> tapedPoint = new MutableLiveData<>(null);
    public MutableLiveData<Boolean> isAddedPhotos = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> canAddPost = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> isAddedPost = new MutableLiveData<>(false);
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    public void addPhoto() {
        isAddedPhotos.setValue(true);
        canAddPost.setValue(tapedPoint.getValue() != null);
    }

    public void addPoint(Point point) {
        tapedPoint.setValue(point);
        canAddPost.setValue(isAddedPhotos.getValue());
    }

    public void addPost(InputStream inputStream, String name, String desc) {
        MapObject mapObject = new MapObject(
                name,
                desc,
                tapedPoint.getValue().getLatitude(),
                tapedPoint.getValue().getLongitude(),
                ""
        );
        DatabaseReference databaseReference = database.getReference().child("Map").push();
        String key = databaseReference.getKey();
        addPhoto(inputStream, key, mapObject);
    }

    private void addPhoto(InputStream inputStream, String key, MapObject mapObject) {
        StorageReference storageReference = storage.getReference().child("images").child("MapObjects").child(key).child("Photo");
        storageReference.putStream(inputStream).addOnSuccessListener(taskSnapshot -> {
            getUri(storageReference, key, mapObject);
        }).addOnFailureListener(fail -> {
            isAddedPost.setValue(false);
        });

    }

    private void getUri(StorageReference storageReference, String key, MapObject mapObject) {
        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    mapObject.setPhoto(uri.toString());
                    addUriToUser(key, mapObject);
                })
                .addOnFailureListener(e -> isAddedPost.setValue(false));
    }

    private void addUriToUser(String key, MapObject mapObject) {
        DatabaseReference databaseReference = database.getReference().child("Map").child(key);
        databaseReference.setValue(mapObject).addOnCompleteListener(task -> {
            isAddedPost.setValue(task.isSuccessful());
        });
    }

}
