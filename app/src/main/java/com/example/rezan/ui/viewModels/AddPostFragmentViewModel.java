package com.example.rezan.ui.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rezan.data.db.News;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;

public class AddPostFragmentViewModel extends ViewModel {

    public MutableLiveData<Boolean> isAddedPhotos = new MutableLiveData<>();
    public MutableLiveData<Boolean> isAddedPost = new MutableLiveData<>();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    public void addPost(InputStream inputStream, String name, String desc) {
        News currNews = new News(name, desc, "");
        DatabaseReference databaseReference = database.getReference().child("News").push();
        String key = databaseReference.getKey();
        addPhoto(inputStream, key, currNews);
    }

    private void addPhoto(InputStream inputStream, String key, News currNews) {
        StorageReference storageReference = storage.getReference().child("images").child("News").child(key).child("Photo");
        storageReference.putStream(inputStream).addOnSuccessListener(taskSnapshot -> {
            getUri(storageReference, key, currNews);
        }).addOnFailureListener(fail -> {
            isAddedPost.setValue(false);
        });

    }

    private void getUri(StorageReference storageReference, String key, News currNews) {
        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    currNews.setPhoto_1(uri.toString());
                    addUriToUser(key, currNews);
                })
                .addOnFailureListener(e -> isAddedPost.setValue(false));
    }

    private void addUriToUser(String key, News currNews) {
        DatabaseReference databaseReference = database.getReference().child("News").child(key);
        databaseReference.setValue(currNews).addOnCompleteListener(task -> {
            isAddedPost.setValue(task.isSuccessful());
        });
    }

}
