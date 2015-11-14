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
    ArrayList<File> mySongs2;
    String lockMusic;
    String unlockMusic;
    Uri u;
    Uri u2;
    private static final String TAG = SecondScreen.class.getSimpleName();


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

            if(action.equals("my.action")) {
                Bundle b = intent.getExtras();
                mySongs = (ArrayList) b.getParcelableArrayList("songlistLock");
                int position = b.getInt("posLock", 0);

                u = Uri.parse(mySongs.get(position).toString());

                unlockMusic = "x";
                lockMusic = "x";
            }
            if(action.equals("my.action.unlock")) {
                Bundle b = intent.getExtras();
                mySongs2 = (ArrayList) b.getParcelableArrayList("songlistUnlock");
                int position = b.getInt("posUnlock", 0);

                u2 = Uri.parse(mySongs2.get(position).toString());

                unlockMusic = b.getString("unlockSound");
                lockMusic = null;
                unlockMusic = null;
            }
        if (action.equals(Intent.ACTION_SCREEN_ON))
        {
            stopPlaying();
            mp = MediaPlayer.create(context, u2);
            mp.start();

        }
        else if (action.equals(Intent.ACTION_SCREEN_OFF))
        {
            stopPlaying();
            mp = MediaPlayer.create(context, u);
            mp.start();
        }
    }

    private void stopPlaying() {
        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
        }
    }
}