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
    private Button mAlertAbortButton;
    private TextView mNameTxt;
    private TextView mEmailTxt;
    private TextView mPhoneTxt;

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

    public void showAlert(Context context, String name, String
            email, String phone) {
        if (context != null) {
            if (alertDialog == null) {
                alertDialog = alert(context);
            }
            alertDialog.setContentView(R.layout.layout_alert_message);
            mAlertAbortButton = alertDialog.findViewById(R.id.button_go_to_home_screen);
            mNameTxt = alertDialog.findViewById(R.id.textView_alert_message_name);
            if (mNameTxt != null) mNameTxt.setText(name);
            mEmailTxt = alertDialog.findViewById(R.id.textView_alert_message_email);
            if (mEmailTxt != null) mEmailTxt.setText(email);
            mPhoneTxt = alertDialog.findViewById(R.id.textView_alert_message_phone);
            if (mPhoneTxt != null) mPhoneTxt.setText(phone);

            if (mAlertAbortButton != null) {
                mAlertAbortButton.requestFocus();
                mAlertAbortButton.setOnClickListener(v -> {
                    this.alertDialog.dismiss();
                });
            }
            alertDialog.show();

        }
    }

}
