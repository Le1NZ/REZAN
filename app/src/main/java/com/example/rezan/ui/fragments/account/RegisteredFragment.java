package com.example.rezan.ui.fragments.account;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.rezan.R;
import com.example.rezan.data.db.User;
import com.example.rezan.databinding.FragmentRegisteredBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisteredFragment extends Fragment {

    static FragmentRegisteredBinding binding;
    static User currentUserInfo;
    static DatabaseReference ref;
    private FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisteredBinding.inflate(inflater, container, false);
        mAuth = FirebaseAuth.getInstance();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("Users").child(mAuth.getCurrentUser().getUid());
        changeData();

        binding.userLogOut.setOnClickListener(v -> {
            try {
                mAuth.signOut();
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_registeredFragment_to_nonRegisteredFragment);
            } catch (Exception e) {
                Log.w("MyAuth", "Error in OUT");
            }
        });

        binding.userSources.setOnClickListener(v -> new ChangeNameDialog().show(
                getChildFragmentManager(), ChangeNameDialog.TAG));

        binding.userAchievements.setOnClickListener(v -> Navigation.findNavController(binding.getRoot()).navigate(R.id.action_registeredFragment_to_achievementsFragment));
        binding.userAboutUs.setOnClickListener(v -> Navigation.findNavController(binding.getRoot()).navigate(R.id.action_registeredFragment_to_ratingFragment));

        return binding.getRoot();
    }

    public static void changeData() {
        ref.get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                binding.userName.setText("Не получилось загрузить данные :(");
            } else {
                currentUserInfo = task.getResult().getValue(User.class);
                binding.userName.setText(currentUserInfo.getName());
                binding.userScore.setText("Ваш счет: " + currentUserInfo.getScore());
            }
        });
    }

}