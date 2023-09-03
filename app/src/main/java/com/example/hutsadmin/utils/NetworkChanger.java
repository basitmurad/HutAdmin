package com.example.hutsadmin.utils;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.widget.Toast;

import java.nio.file.FileSystemNotFoundException;
import java.util.EventListener;

public class NetworkChanger extends BroadcastReceiver {


    private AlertDialog internetDialog; // Reference to the dialog

    public NetworkChanger() {
        internetDialog = null;

    }
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!isNetworkConnected(context))
        {

            showNoInternetDialog(context);
        }
        else {
            dismissDialog();
        }


    }
    private void dismissDialog() {
        if (internetDialog != null && internetDialog.isShowing()) {
            internetDialog.dismiss();
            internetDialog = null; // Reset the reference
        }
    }
    private void showNoInternetDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You are not connected to internet\n please check your internet connection");

        builder.setPositiveButton("Open Setting", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                context.startActivity(intent);
                dismissDialog();
            }
        });

        internetDialog = builder.create();
        internetDialog.show();
    }

    private boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
