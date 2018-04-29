package com.pdfdocumentreader.utils;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ToastMessage {

    public static void toastMsgShot(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void toastMsgLong(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void toastMsgFailed(Context context) {
        Toast.makeText(context, "Failed! try again", Toast.LENGTH_LONG).show();
    }

    public static void toastMsgNoInternet(Context context) {
        Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG)
                .show();
    }

    //*******************************

    public static void snackBarMsgIndefinite(Activity activity, String msg) {

        View mLayout = activity.findViewById(android.R.id.content);

        final Snackbar snackbar = Snackbar.make(mLayout, msg, Snackbar.LENGTH_INDEFINITE);

        View snackbarView = snackbar.getView();
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(5);

        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        snackbar.show();

    }

    public static void snackBarMsgIndefiniteNoInternet(Activity activity) {

        //new CommenUtilsFunctions().closeKeyboardProg(activity);
        View mLayout = activity.findViewById(android.R.id.content);
        String msg = "No Internet Connection";
        final Snackbar snackbar = Snackbar.make(mLayout, msg, Snackbar.LENGTH_INDEFINITE);

        View snackbarView = snackbar.getView();
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(5);

        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        snackbar.show();

    }

    public static void snackBarMsgLong(Activity activity, String msg) {

        View mLayout = activity.findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar
                .make(mLayout, msg, Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    public static void snackBarMsgLongNoInternet(Activity activity) {
        String msg = "No Internet Connection";
        View mLayout = activity.findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar
                .make(mLayout, msg, Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    public static void snackBarMsgShort(Activity activity, String msg) {

        View mLayout = activity.findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar
                .make(mLayout, msg, Snackbar.LENGTH_SHORT);

        snackbar.show();
    }
}