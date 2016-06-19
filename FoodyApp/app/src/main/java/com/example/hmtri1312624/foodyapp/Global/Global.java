package com.example.hmtri1312624.foodyapp.Global;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.hmtri1312624.foodyapp.Model.FoodyMenuItem;

import java.util.List;

/**
 * Created by M-Tae on 5/30/2016.
 */
public class Global {
    public static String CurrentQuery;
    public static List<String> currentImageList;
    public static List<FoodyMenuItem> currentMenuList;
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

    public static void hideSoftInput(Activity activity)
    {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
