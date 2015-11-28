package com.piersonleo.lockscreensound;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    int [] newPosition;
    String  [] items;

    TextView lockMusic;
    TextView unlockMusic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //startService(new Intent(getApplicationContext(), LockScreenService.class));
        Button buttonUnlock = (Button) findViewById(R.id.UnlockChangeBtn);
        Button buttonLock = (Button) findViewById(R.id.LockChangeBtn);
        Button buttonReset = (Button) findViewById(R.id.ResetBtn);
        lockMusic = (TextView) findViewById(R.id.LockTextView);
        unlockMusic = (TextView) findViewById(R.id.UnlockTextView);

        final ArrayList<File> mySongs = findSongs(Environment.getExternalStorageDirectory());

        items = new String[mySongs.size()];
        newPosition = new int[mySongs.size()];

        for(int i =0; i<mySongs.size(); i++){
            items[i]= (mySongs.get(i).getName().toString().replace(".mp3","").replace(".wav",""));
            newPosition[i] = i;
        }

        buttonLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getNameScreenIntent = new Intent(getApplicationContext(), SecondScreen.class);
                final int resultLock=1;
                getNameScreenIntent.putExtra("Lock","LockScreen").putExtra("songN",items)
                .putExtra("songPos", newPosition);
                startActivityForResult(getNameScreenIntent, resultLock);
            }
        });

       buttonUnlock.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent getNameScreenIntent = new Intent(getApplicationContext(), SecondScreen.class);
               final int resultUnlock=2;
               getNameScreenIntent.putExtra("Unlock","UnlockScreen").putExtra("songN",items)
               .putExtra("songPos", newPosition);
               startActivityForResult(getNameScreenIntent, resultUnlock);

           }
       });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Sound has been reset", Toast.LENGTH_SHORT).show();
                lockMusic.setText("Default");
                unlockMusic.setText("Default");
                stopService(new Intent(getApplicationContext(), LockScreenService.class));

                SharedPreferences sharedPref = getSharedPreferences("soundName", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("OffSound", lockMusic.getText().toString());
                editor.putString("OnSound", unlockMusic.getText().toString());
                editor.commit();
                stopService(new Intent(getApplicationContext(), LockScreenService.class));

            }
        });


    }
/* Removing the overflow menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    //get result from the chosen music
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK) {

            String  chosenLockMusic = data.getStringExtra("dataSend");

            lockMusic.setText(chosenLockMusic);

            Toast.makeText(this, chosenLockMusic+ " is set as OFF sound", Toast.LENGTH_SHORT).show();
        }

        if (requestCode==1 && resultCode == RESULT_OK){

            String chosenUnlockMusic = data.getStringExtra("dataSend");

            unlockMusic.setText(chosenUnlockMusic);

            Toast.makeText(this, chosenUnlockMusic+" is set as ON sound", Toast.LENGTH_SHORT).show();
        }

        SharedPreferences sharedPref = getSharedPreferences("soundName", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("OffSound", lockMusic.getText().toString());
        editor.putString("OnSound", unlockMusic.getText().toString());
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPref = getSharedPreferences("soundName", Context.MODE_PRIVATE);
        String offName = sharedPref.getString("OffSound","");
        String onName = sharedPref.getString("OnSound","");
        lockMusic.setText(offName);
        unlockMusic.setText(onName);
    }

    public ArrayList<File>findSongs(File root){

        ArrayList al = new ArrayList<File>();

        File[] files = root.listFiles();
        for(File singleFile : files){
            if(singleFile.isDirectory() && !singleFile.isHidden()){
                al.addAll(findSongs(singleFile));
            }
            else{
                if(singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".wav")){
                    al.add(singleFile);

                }

            }
        }
        return al;
    }

}