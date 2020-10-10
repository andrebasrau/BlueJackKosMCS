package com.example.bluejackkos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogBook extends AppCompatDialogFragment {
    private DialogBookListener listener;
    int position;
    public DialogBook (int position){
        this.position = position;
    }
    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
        builder.setTitle ("Warning !!").setMessage("Do you want to cancel this booking?")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onYesClicked(position);
                    }
                });
        return builder.create();

    }
    public interface DialogBookListener {void onYesClicked(int position);}
    @Override
    public void onAttach (Context context) {

        super.onAttach(context);
        try {
            listener = (DialogBookListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+" must implement ExampleDialogListener");
        }


    }
}
