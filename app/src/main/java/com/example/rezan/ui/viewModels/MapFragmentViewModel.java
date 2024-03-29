package com.example.rezan.ui.viewModels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rezan.data.db.MapObject;
import com.example.rezan.data.db.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MapFragmentViewModel extends ViewModel {

    public MutableLiveData<Map<String, MapObject>> map = new MutableLiveData<>();
    public MutableLiveData<Boolean> isUserAdmin = new MutableLiveData<>();
    private FirebaseAuth mAuth;
    private DatabaseReference ref;

    public void download() {
        FirebaseDatabase.getInstance().getReference("Map").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                map.setValue(snapshot.getValue(new GenericTypeIndicator<Map<String, MapObject>>() {
                }));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

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
