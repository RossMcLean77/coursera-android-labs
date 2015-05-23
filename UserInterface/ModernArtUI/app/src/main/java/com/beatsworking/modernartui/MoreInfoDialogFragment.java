package com.beatsworking.modernartui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;


/**
 * Created by Ross on 20/04/2015.
 */
public class MoreInfoDialogFragment extends DialogFragment {

    static private final String TAG = "modernartui";
    // Use this instance of the interface to deliver action events
    MoreInfoDialogListener mListener;

    /* The activity that creates an instance of this dialog fragment must
    * implement this interface in order to receive event callbacks.
    * Each method passes the DialogFragment in case the host needs to query it. */
    public interface MoreInfoDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
        public void onDialogNeutralClick(DialogFragment dialog);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.i(TAG,"Entered onCreateDialog()");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.moreinfo_dialog, null))

                .setTitle(R.string.dialog_moreinfo_title)
            //.setMessage(R.string.dialog_moreinfo_inspired)
            //.setPositiveButton(R.string.dialog_moreinfo_momo, new DialogInterface.OnClickListener() {
            //    public void onClick(DialogInterface dialog, int id) {
            //        // Send the positive button event back to the calling activity
            //        mListener.onDialogPositiveClick(MoreInfoDialogFragment.this);
            //    }
            //})
            .setNegativeButton(R.string.dialog_moreinfo_notnow, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    // Send the negative button event back to the calling activity
                    mListener.onDialogNegativeClick(MoreInfoDialogFragment.this);
                }
            })
            .setNeutralButton(R.string.dialog_moreinfo_moma, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    // Send the neutral button event back to the calling activity
                    mListener.onDialogNeutralClick(MoreInfoDialogFragment.this);
                }
            })
        ;

        return builder.create();
    }


    // Override the Fragment.onAttach() method to instantiate the MoreInfoDialogListener
    @Override
    public void onAttach(Activity activity) {
        Log.i(TAG,"Entered onAttach()");
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (MoreInfoDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
