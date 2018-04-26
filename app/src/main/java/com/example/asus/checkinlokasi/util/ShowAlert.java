package com.example.asus.checkinlokasi.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.checkinlokasi.R;

/**
 * Created by adhit on 03/01/2018.
 */

public class ShowAlert {
    public static ProgressDialog dialog;

    public static void showProgresDialog(Context context){
        dialog= new ProgressDialog(context);
        dialog.setMessage(context.getResources().getString(R.string.text_loading));
        dialog.setCancelable(false);
        dialog.show();
    }
    public static void closeProgresDialog(){
        dialog.dismiss();
    }

    public static void showToast(Context context, String text){
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.CENTER| Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }
}
