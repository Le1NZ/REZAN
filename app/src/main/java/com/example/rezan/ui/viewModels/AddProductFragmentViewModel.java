package com.example.rezan.ui.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rezan.data.db.News;
import com.example.rezan.data.db.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;

public class AddProductFragmentViewModel extends ViewModel {

    public MutableLiveData<Boolean> isAddedPhotos = new MutableLiveData<>();
    public MutableLiveData<Boolean> isAddedProduct = new MutableLiveData<>();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    public void addPost(InputStream inputStream, String name, Integer cost) {
        Product currProduct = new Product(name, cost, "");
        DatabaseReference databaseReference = database.getReference().child("Products").push();
        String key = databaseReference.getKey();
        addPhoto(inputStream, key, currProduct);
    }

    private void addPhoto(InputStream inputStream, String key, Product currProduct) {
        StorageReference storageReference = storage.getReference().child("images").child("Products").child(key).child("Photo");
        storageReference.putStream(inputStream).addOnSuccessListener(taskSnapshot -> {
            getUri(storageReference, key, currProduct);
        }).addOnFailureListener(fail -> {
            isAddedProduct.setValue(false);
        });

    }

    private void getUri(StorageReference storageReference, String key, Product currProduct) {
        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    currProduct.setPhoto(uri.toString());
                    addUriToUser(key, currProduct);
                })
                .addOnFailureListener(e -> isAddedProduct.setValue(false));
    }

    private void addUriToUser(String key, Product currProduct) {
        DatabaseReference databaseReference = database.getReference().child("Products").child(key);
        databaseReference.setValue(currProduct).addOnCompleteListener(task -> {
            isAddedProduct.setValue(task.isSuccessful());
        });
    }

}
