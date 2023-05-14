package com.example.rezan.ui.viewModels;

import android.os.Bundle;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rezan.data.db.Product;

public class ProductFragmentViewModel extends ViewModel {

    public static MutableLiveData<Product> product = new MutableLiveData<>();

    public void onRefreshed(Bundle bundle) {
        Product productHelper = new Product(
                bundle.getSerializable("name").toString(),
                (Integer) bundle.getSerializable("cost"),
                bundle.getSerializable("photo").toString()
        );
        product.setValue(productHelper);
    }

}