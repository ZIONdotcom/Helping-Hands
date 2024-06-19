package com.example.unityserve;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class joinDialogFragment extends DialogFragment {

    private EditText phone, message;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_join_dialog, null);

        phone = view.findViewById(R.id.phone);
        message = view.findViewById(R.id.message);

        builder.setView(view)
                .setTitle("Let them know you want to help!")
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Handle OK button click
                        String userInput1 = phone.getText().toString();
                        String userInput2 = message.getText().toString();
                        // Do something with the inputs
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        joinDialogFragment.this.getDialog().cancel();
                    }
                });
        // Create the dialog
        AlertDialog dialog = builder.create();

        // Set button colors after the dialog is shown
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                if (positiveButton != null) {
                    positiveButton.setTextColor(Color.parseColor("#5F81E4"));
                }

                if (negativeButton != null) {
                    negativeButton.setTextColor(Color.parseColor("grey"));
                }
            }
        });

        return dialog;
    }
}
