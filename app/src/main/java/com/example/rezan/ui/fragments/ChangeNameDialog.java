package com.example.rezan.ui.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.example.rezan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangeNameDialog extends DialogFragment {

    FirebaseAuth mAuth = AccountFragment.mAuth;
    public static String TAG = "ChangeNameDialog";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_change_name, null);

        EditText changerName = view.findViewById(R.id.changeName);

        builder.setView(view)
                .setPositiveButton("Сменить ник", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        final FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference ref = database.getReference("Users").child(mAuth.getCurrentUser().getUid());
                        ref.child("Name").setValue(changerName.getText().toString());
                        RegisteredFragment.changeData();
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ChangeNameDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
