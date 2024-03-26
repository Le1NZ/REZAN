package com.example.rezan.ui.fragments.registration;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.rezan.R;
import com.example.rezan.databinding.FragmentVerificationPhoneBinding;
import com.example.rezan.ui.fragments.account.AccountFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class VerificationPhoneFragment extends Fragment {

    FragmentVerificationPhoneBinding binding;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    FirebaseAuth mAuth;
    private boolean buttonStatus = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVerificationPhoneBinding.inflate(inflater, container, false);

        mAuth = AccountFragment.mAuth;

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Log.w("MyAuth", e);
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Log.w("MyAuth", "The SMS quota for the project has been exceeded");
                }
            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                mVerificationId = verificationId;
                mResendToken = token;

                binding.progressBar.setVisibility(View.GONE);
                buttonStatus = true;
                binding.tvCanYouCode.setVisibility(View.VISIBLE);
                binding.etEnteredCode.setVisibility(View.VISIBLE);
                binding.verifyPhone.setText("Проверить код");
            }
        };

        binding.verifyPhone.setOnClickListener(v -> {
            if (binding.etEnteredPhone.getText().toString().equals("")) {
                Toast.makeText(requireContext(), "Вы не заполнили поле!", Toast.LENGTH_LONG).show();
            } else {
                if (!buttonStatus) {
                    if (binding.etEnteredPhone.getText().toString().length() != 12) {
                        Toast.makeText(requireContext(), "Неправильный формат номера :(", Toast.LENGTH_SHORT).show();
                    } else {
                        binding.progressBar.setVisibility(View.VISIBLE);
                        PhoneAuthOptions options =
                                PhoneAuthOptions.newBuilder(mAuth)
                                        .setPhoneNumber(binding.etEnteredPhone.getText().toString())       // Phone number to verify
                                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                        .setActivity(getActivity())                 // Activity (for callback binding)
                                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                                        .build();
                        PhoneAuthProvider.verifyPhoneNumber(options);
                    }
                } else {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, binding.etEnteredCode.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });

        return binding.getRoot();
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            final FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference ref = database.getReference("Users").child(mAuth.getCurrentUser().getUid());

                            if (task.getResult().getAdditionalUserInfo().isNewUser()) {
                                ref.child("Name").setValue("Рязанец");
                                ref.child("Score").setValue(0);
                                ref.child("Admin").setValue(false);
                                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_verificationPhoneFragment_to_enterNameAndPhotoFragment);
                            } else {
                                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_verificationPhoneFragment_to_registeredFragment);
                            }

                        } else {
                            binding.tvCanYouCode.setText("Проблемы...");
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                binding.tvCanYouCode.setText("Код неверен");
                            }
                        }
                    }
                });
    }


}