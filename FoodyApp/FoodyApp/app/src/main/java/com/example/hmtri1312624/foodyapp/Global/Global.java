package com.example.hmtri1312624.foodyapp.Global;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by ADMIN on 29-May-16.
 */
public class Global {
    public static String CurrentQuery;
    static ProgressDialog dialog;

    public static void showPreloader(Context context,String Message)
    {
        dialog = new ProgressDialog(context); // this = YourActivity
        dialog.setMessage(Message);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    public static void hidePreloader()
    {
        if (dialog != null && dialog.isShowing())
        {
            dialog.dismiss();
            dialog = null;
        }
    }
}
