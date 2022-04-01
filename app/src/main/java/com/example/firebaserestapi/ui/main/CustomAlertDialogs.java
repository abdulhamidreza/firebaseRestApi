package com.example.firebaserestapi.ui.main;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.firebaserestapi.R;

public class CustomAlertDialogs {

    private AlertDialog alertDialog;


    private static CustomAlertDialogs customAlertDialogs = null;


    private CustomAlertDialogs() {
    }

    // Static method
    // Static method to create instance of Singleton class
    public static CustomAlertDialogs getInstance() {
        if (customAlertDialogs == null)
            customAlertDialogs = new CustomAlertDialogs();

        return customAlertDialogs;
    }


    private AlertDialog alert(Context context) {
        AlertDialog.Builder abuilder = new AlertDialog.Builder(context);
        abuilder.setCancelable(false);
        AlertDialog ad = abuilder.create();
        if (ad.getWindow() != null) {
            ad.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT));
            ad.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            ad.getWindow().setDimAmount(0.1f); //0 for no dim to 1 for full dim
        }
        return ad;
    }

    public void showAlert(Context context, String mMessage, String
            mExtra) {
        if (context != null) {
            if (alertDialog == null) {
                alertDialog = alert(context);
            }
            displayLayout(alertDialog, R.layout.layout_alert_message, mMessage, mExtra);

        }
    }

    private void displayLayout(AlertDialog ad, int layoutId,
                               String mMessage, String mExtra) {
        ad.setContentView(layoutId);
        Button mAlertAbortButton = ad.findViewById(R.id.button_go_to_home_screen);
        TextView mAlertMessage = ad.findViewById(R.id.textView_alert_message);
        if (mAlertMessage != null) mAlertMessage.setText(mMessage);
        if (mAlertAbortButton != null) {
            mAlertAbortButton.requestFocus();
            mAlertAbortButton.setOnClickListener(v -> {
                alertDialog.dismiss();
            });
        }
        ad.show();
    }

}
