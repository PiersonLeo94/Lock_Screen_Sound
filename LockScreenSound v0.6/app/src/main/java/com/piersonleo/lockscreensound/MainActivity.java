package com.piersonleo.lockscreensound;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public  void ConfirmMessage(View view){
        Toast.makeText(this, "Sound has been set", Toast.LENGTH_LONG).show();

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
        startActivityForResult(getNameScreenIntent, resultLock);

    }

    public void GetMusicListUnlock(View view) {

        Intent getNameScreenIntent = new Intent(this, SecondScreen.class);
        final int resultUnlock=2;
        startActivityForResult(getNameScreenIntent, resultUnlock);

    }

    @Override
    //get result from the chosen music
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        TextView lockMusic = (TextView) findViewById(R.id.LocktextView);
        TextView unlockMusic = (TextView) findViewById(R.id.UnlocktextView);

        if (requestCode == 1 && resultCode == RESULT_OK) {

            String chosenLockMusic = data.getStringExtra("dataSend");

            lockMusic.setText(chosenLockMusic);

            String lName = data.getStringExtra("lname");
            File file = new File(lName);
            Toast.makeText(this, file.getName().toString(), Toast.LENGTH_LONG).show();
        }

        if(requestCode==2 && resultCode == RESULT_OK){

            String chosenUnlockMusic = data.getStringExtra("dataSend");

            unlockMusic.setText(chosenUnlockMusic);

            String lName = data.getStringExtra("lname");
            File file = new File(lName);
            Toast.makeText(this, file.getName().toString(), Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}