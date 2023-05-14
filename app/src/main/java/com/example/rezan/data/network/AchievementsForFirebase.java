package com.example.rezan.data.network;

import android.util.Log;

import com.example.rezan.ui.fragments.AccountFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.atomic.AtomicReference;

public class AchievementsForFirebase {

    static FirebaseAuth mAuth = AccountFragment.mAuth;
    static FirebaseUser user = mAuth.getCurrentUser();
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference ref;


    public static void addUserToAchievement(String numberOfAchievement) {
        ref = database.getReference("Achievements").child(numberOfAchievement).child("users")
                .child(mAuth.getCurrentUser().getUid());
        ref.setValue(true);
    }

    public static void addScoreToUser(Integer scoreOfAchievement) {
        ref = database.getReference("Users").child(mAuth.getCurrentUser().getUid());

        ref.child("Score").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Integer scoreNow = task.getResult().getValue(Integer.class);
                ref.child("Score").setValue(scoreNow + scoreOfAchievement);
            }
        });

    }

}
