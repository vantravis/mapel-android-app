package com.example.myapplication.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

public class PopupUtil {
    private static final String TAG = "PopupUtil";
    private static ProgressDialog dialog;
    public static final int SHORT = Toast.LENGTH_SHORT;
    public static final int LONG = Toast.LENGTH_LONG;

    public static final void showMsg(final Context context, final String messange, int duration) {
        try {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            //Toast toast = Toast.makeText(context, "Transaksi Pembelian\nHanya Dapat Dilakukan Di Toko Yang Sama", Toast.LENGTH_LONG);
            Toast toast = Toast.makeText(context, messange, duration);
            View view = layoutInflater.inflate(R.layout.toast_layout, null);
            //view.setBackgroundColor(Color.parseColor("#c6c6c6"));
            TextView text = (TextView) view.findViewById(R.id.tv_messsage);

            text.setText(messange);
            toast.setView(view);
            /*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
            toast.show();
        } catch (Exception e) {
        }
    }

    public static final void showLoading(final Context context, final String title, final String msg) {
        try {
            dialog = ProgressDialog.show(context, title, msg);
        } catch (Exception e) {
        }
    }

    public static final void showProgress(final Context context, final String title, final String msg) {
        try {
            dialog = new ProgressDialog(context);
            dialog.setTitle(title);
            dialog.setMessage(msg);
            //dialog.setIndeterminate(true);
            dialog.setProgress(0);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMax(100);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final void updateProgress(int progress) {
        try {
            if (dialog != null) {
                Log.d(TAG, "Updating progress: " + progress);
                dialog.setProgress(progress);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final void dismissDialog() {
        try {
            if (dialog != null) dialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final void alert(final Context context, final String msg,
                                   final String positif, final String negatif, final DialogInterface.OnClickListener listener) {
        try {
            if (context == null || listener == null) throw new NullPointerException();
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setMessage(msg);
            alert.setPositiveButton(positif, listener);
            if (negatif != null) alert.setNegativeButton(negatif, listener);
            else alert.setCancelable(false);
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}