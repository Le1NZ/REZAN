package com.example.rezan.ui.fragments.registration;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rezan.R;
import com.example.rezan.databinding.FragmentEnterNameAndPhotoBinding;
import com.example.rezan.ui.fragments.account.AccountFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EnterNameAndPhotoFragment extends Fragment {

    FragmentEnterNameAndPhotoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEnterNameAndPhotoBinding.inflate(inflater, container, false);
        FirebaseAuth mAuth = AccountFragment.mAuth;

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Users").child(mAuth.getCurrentUser().getUid());

        binding.enterName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.child("Name").setValue(binding.etEnteredName.getText().toString());
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_enterNameAndPhotoFragment_to_registeredFragment);
            }
        });



        return binding.getRoot();
    }
}