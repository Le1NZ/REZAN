package com.example.rezan.ui.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rezan.data.db.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShopFragmentViewModel extends ViewModel {

    public MutableLiveData<Boolean> isUserAdmin = new MutableLiveData<>();
    private static FirebaseAuth mAuth;
    private DatabaseReference ref;

    public void isUserAdmin() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
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
}
