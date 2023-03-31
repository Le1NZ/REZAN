package com.example.rezan;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.rezan.databinding.FragmentRegisteredBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisteredFragment extends Fragment {

    static FragmentRegisteredBinding binding;
    static User currentUserInfo;
    static DatabaseReference ref;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisteredBinding.inflate(inflater, container, false);
        FirebaseAuth mAuth = AccountFragment.mAuth;

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("Users").child(mAuth.getCurrentUser().getUid());
        changeData();

        binding.userLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mAuth.signOut();
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_registeredFragment_to_navigation_account);
                } catch (Exception e) {
                    Log.w("MyAuth", "Error in OUT");
                }
            }
        });

        binding.userSources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangeNameDialog().show(
                        getChildFragmentManager(), ChangeNameDialog.TAG);
            }
        });

        return binding.getRoot();
    }

    public static void changeData() {
        ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    binding.userName.setText("Не получилось загрузить данные :(");
                } else {
                    currentUserInfo = task.getResult().getValue(User.class);
                    binding.userName.setText("@" + currentUserInfo.Name);
                    binding.userScore.setText("Ваш счет: " + currentUserInfo.Score);
                }
            }
        });
    }

}