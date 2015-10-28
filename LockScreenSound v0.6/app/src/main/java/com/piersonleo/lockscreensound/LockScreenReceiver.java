package com.piersonleo.lockscreensound;

/**
 * Created by Pierson Leo on 28-Oct-15.
 */
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.Uri;

public class LockScreenReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.equals("android.intent.action.SCREEN_OFF"))
        {

        }
        else if (intent.equals("android.intent.action.SCREEN_ON"))
        {

        }
    }

}