package com.afra55.trainingfirstapp.module.shortcuts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.afra55.trainingfirstapp.utils.ShortcutHelper;

@RequiresApi(api = Build.VERSION_CODES.N_MR1)
public class ShortcutReceiver extends BroadcastReceiver {
    public ShortcutReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if (Intent.ACTION_LOCALE_CHANGED.equals(intent.getAction())) {
            // Refresh all shortcut to update the labels.
            // (Right now shortcut labels don't contain localized strings though.)
            new ShortcutHelper(context).refreshShortcuts(/*force=*/ true);
        }
    }
}
