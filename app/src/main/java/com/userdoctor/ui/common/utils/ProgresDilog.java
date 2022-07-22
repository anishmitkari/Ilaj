package com.userdoctor.ui.common.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

/**
 * Created by raghav on 29/6/18.
 */

public class ProgresDilog {

    public static ProgressDialog progress;

    public static void displayDilog(Context context) {
        if (progress != null) {
            if (progress.isShowing()) {
                progress.dismiss();
            }
        }
        progress = new ProgressDialog(context);
      //  progress.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
          progress.getWindow().setBackgroundDrawable(new ColorDrawable(Color.RED));

        progress.show();
        progress.setCancelable(true);
    }

    public static void showDilog(Context context){
        progress = new ProgressDialog(context);
        progress.setMessage("loading....");
        progress.setCancelable(false);
    }

    public static void hideDilog(Context context) {
        if (progress != null) {
            if (progress.isShowing()) {
                progress.cancel();
            }
        }
    }
}
