package com.piersonleo.lockscreensound;

/**
 * Created by Pierson Leo on 28-Oct-15.
 */
import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class LockScreenService extends Service {

    BroadcastReceiver receiver;
    private static final String TAG = SecondScreen.class.getSimpleName();


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onCreate() {
        Log.d(TAG, "Service start");

        //Start listening for the Screen On, Screen Off, and Boot completed actions
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_BOOT_COMPLETED);

        //Set up a receiver to listen for the Intents in this Service
        receiver = new LockScreenReceiver();
        registerReceiver(receiver, filter);
        registerReceiver( receiver, new IntentFilter( "my.action" ) );
        registerReceiver( receiver, new IntentFilter( "my.action.unlock" ) );

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        LockScreenReceiver.stopPlaying();
        super.onDestroy();
    }
}