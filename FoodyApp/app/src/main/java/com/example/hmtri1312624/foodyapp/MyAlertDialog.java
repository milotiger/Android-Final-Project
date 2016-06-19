package com.example.hmtri1312624.foodyapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.hmtri1312624.foodyapp.Global.Global;
import com.example.hmtri1312624.foodyapp.Model.FoodyItemInfo;
import com.example.hmtri1312624.foodyapp.Model.FoodyMenuItem;
import com.example.hmtri1312624.foodyapp.Model.FoodyMenuSet;

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

    public static void ShowMenuDialog(final Context context, final FoodyItemInfo item){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
        builderSingle.setTitle("Menu Sets");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                context,
                android.R.layout.select_dialog_singlechoice);
        for (FoodyMenuSet menu : item.MenuSets)
        {
            arrayAdapter.add(menu.Name);
        }

        builderSingle.setNegativeButton(
                "cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(item.MenuSets.get(which).Dishes.size() != 0) {
                            Global.currentMenuList = item.MenuSets.get(which).Dishes;

                            Intent i = new Intent(context, MenuActivity.class);
                            context.startActivity(i);
                        }
                        else {
                            Global.currentMenuList = null;
                            Toast.makeText(context,"Menu Set này chưa có hình ảnh từng món",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                });
        builderSingle.show();
    }
}
