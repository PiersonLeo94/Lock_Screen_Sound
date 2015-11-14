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
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;


public class LockScreenReceiver extends BroadcastReceiver {

    MediaPlayer mp;
    ArrayList<File> mySongs;
    String lockMusic = null;
    String unlockMusic = null;
    private static final String TAG = SecondScreen.class.getSimpleName();


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        try {
            Bundle b = intent.getExtras();
            mySongs = (ArrayList) b.getParcelableArrayList("songlist");
            int position = b.getInt("pos", 0);

            Uri u = Uri.parse(mySongs.get(position).toString());
            mp = MediaPlayer.create(context, u);

            lockMusic = intent.getExtras().getString("lockSound");
        }catch (Exception e){
            Log.e(TAG,"Intent error");
        }

        if (action.equals(Intent.ACTION_SCREEN_ON))
        {

        }
        else if (action.equals(Intent.ACTION_SCREEN_OFF))
        {
            if(lockMusic!=null){
                mp.start();
            }
            else
            {
                Toast.makeText(context, lockMusic, Toast.LENGTH_SHORT).show();
            }

        }
    }

}