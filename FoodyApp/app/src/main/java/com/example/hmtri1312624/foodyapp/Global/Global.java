package com.example.hmtri1312624.foodyapp.Global;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.hmtri1312624.foodyapp.FavoriteActivity;
import com.example.hmtri1312624.foodyapp.MainActivity;
import com.example.hmtri1312624.foodyapp.Model.Account;
import com.example.hmtri1312624.foodyapp.Model.FoodyItemInfo;
import com.example.hmtri1312624.foodyapp.Model.FoodyMenuItem;
import com.example.hmtri1312624.foodyapp.Model.FoodyMenuSet;
import com.example.hmtri1312624.foodyapp.MyAlertDialog;
import com.example.hmtri1312624.foodyapp.Service.RestService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by M-Tae on 5/30/2016.
 */
public class Global {
    public static String CurrentQuery;
    public static List<String> currentImageList = null;
    public static List<FoodyMenuItem> currentMenuList = null;
    public static List<FoodyMenuSet> currentMenuSet = null;
    public static Account currentAcc = null;
    public static Boolean HaveList = false;
    public static String MyPref = "MyPrefs";
    public static Boolean isAlready = false; // check if Exists in Favorite List
    public static Boolean isUpdate = false; // check if update Favorite List
    public static Boolean needReload = false; // check if add or remove in Favorite activity
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

    public static void LoadAlreadyFavoriteList(){
        RestService restService = new RestService();

        Call<List<FoodyItemInfo>> call = restService.getService().ShowBookmark(Global.currentAcc.userid);

        call.enqueue(new Callback<List<FoodyItemInfo>>() {
            @Override
            public void onResponse(Call<List<FoodyItemInfo>> call, Response<List<FoodyItemInfo>> response) {
                Global.currentAcc.Favorite = response.body();
                if (Global.currentAcc.Favorite == null)
                {
                    Global.HaveList = false;
                    return;
                }
                Global.HaveList = true;
            }
            @Override
            public void onFailure(Call<List<FoodyItemInfo>> call, Throwable t) {
            }
        });
    }

    public static void LoadNewFavoriteList(final Context context){
        RestService restService = new RestService();

        Call<List<FoodyItemInfo>> call = restService.getService().ShowBookmark(Global.currentAcc.userid);

        call.enqueue(new Callback<List<FoodyItemInfo>>() {
            @Override
            public void onResponse(Call<List<FoodyItemInfo>> call, Response<List<FoodyItemInfo>> response) {
                Global.currentAcc.Favorite = response.body();;
                if (Global.currentAcc.Favorite == null)
                {
                    Global.HaveList = false;
                    MyAlertDialog.ShowDialog("You dont have any favorite food place", context);
                    //if null when remove
                    if(Global.isUpdate) {
                        Intent i = new Intent(context, MainActivity.class);
                        context.startActivity(i);
                    }
                }
                else {
                    Global.HaveList = true;
                    Intent i = new Intent(context, FavoriteActivity.class);
                    context.startActivity(i);
                }
            }
            @Override
            public void onFailure(Call<List<FoodyItemInfo>> call, Throwable t) {
            }
        });
    }
}
