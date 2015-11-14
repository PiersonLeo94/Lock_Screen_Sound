package com.piersonleo.lockscreensound;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    File fileLock;
    File fileUnlock;

    TextView lockMusic;
    TextView unlockMusic;
    String chosenLockMusic;
    String dirNameLock;
    String dirNameUnlock;

    File f = Environment.getExternalStorageDirectory();
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(getApplicationContext(), LockScreenService.class));

    }

    public  void ConfirmMessage(View view){
       // Toast.makeText(this, "Sound has been set", Toast.LENGTH_LONG).show();
        startService(new Intent(getApplicationContext(), LockScreenService.class));
    }
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
    }

    public void GetMusicListLock(View view) {

        Intent getNameScreenIntent = new Intent(this, SecondScreen.class);
        final int resultLock=1;
        getNameScreenIntent.putExtra("Lock","LockScreen");
        startActivityForResult(getNameScreenIntent, resultLock);

    }

    public void GetMusicListUnlock(View view) {

        Intent getNameScreenIntent = new Intent(this, SecondScreen.class);
        final int resultUnlock=2;
        getNameScreenIntent.putExtra("Unlock","UnlockScreen");
        startActivityForResult(getNameScreenIntent, resultUnlock);

    }

    @Override
    //get result from the chosen music
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        lockMusic = (TextView) findViewById(R.id.LocktextView);
        unlockMusic = (TextView) findViewById(R.id.UnlocktextView);

        if (requestCode == 1 && resultCode == RESULT_OK) {

            chosenLockMusic = data.getStringExtra("dataSend");

            lockMusic.setText(chosenLockMusic);

            String lName = data.getStringExtra("lname");
       //     fileLock = new File(lName);
      //      file = new File(f, lName);
       //     dirNameLock = getExternalFilesDir(lName).toString();
      //      Toast.makeText(this, fileLock.getName().toString(), Toast.LENGTH_LONG).show();
        }

        if (requestCode==2 && resultCode == RESULT_OK){

            String chosenUnlockMusic = data.getStringExtra("dataSend");

            unlockMusic.setText(chosenUnlockMusic);

            String lName = data.getStringExtra("lname");
          //  fileUnlock = new File(lName);
          //  dirNameUnlock = getExternalFilesDir(lName).toString();
          //  Toast.makeText(this, dirNameUnlock, Toast.LENGTH_LONG).show();

        }
    }



}