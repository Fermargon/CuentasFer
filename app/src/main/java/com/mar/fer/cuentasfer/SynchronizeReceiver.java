package com.mar.fer.cuentasfer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SynchronizeReceiver extends BroadcastReceiver {


    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        if (action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
            Toast.makeText(context, "Lowpower", Toast.LENGTH_LONG).show();
        } else if (action.equals(Intent.ACTION_POWER_CONNECTED)) {
            Toast.makeText(context, "enhadcedmode", Toast.LENGTH_LONG).show();
        }

    }
}