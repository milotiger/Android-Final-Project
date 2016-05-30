package com.example.hmtri1312624.foodyapp;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by M-Tae on 5/29/2016.
 */
public class MyAlertDialog {
    public static void ShowDialog(String text, Context context){
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Alert Dialog");
        alertDialog.setMessage(text);

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
