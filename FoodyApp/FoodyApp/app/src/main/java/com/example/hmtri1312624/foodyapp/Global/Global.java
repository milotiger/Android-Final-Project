package com.example.hmtri1312624.foodyapp.Global;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.hmtri1312624.foodyapp.Model.FoodyItemInfo;

import java.util.List;

/**
 * Created by ADMIN on 22-May-16.
 */
public class Global {
    public static String Query = "hamburger";
    private static ProgressDialog dialog;
    public static List<FoodyItemInfo> data;

    public static void ShowPreloader(Context context)
    {
        dialog = new ProgressDialog(context); // this = YourActivity
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Searching...Please wait...:)");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public static void HidePreloader()
    {
        if (dialog!=null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
