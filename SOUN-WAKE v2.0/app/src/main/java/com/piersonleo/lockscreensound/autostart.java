package com.piersonleo.lockscreensound;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Pierson Leo on 30-Nov-15.
 */
public class autostart extends BroadcastReceiver {

    public void onReceive(Context arg0, Intent arg1)
    {
        Intent intent = new Intent(arg0,LockScreenService.class);
        arg0.startService(intent);
        Log.i("Autostart", "started");
    }
}
