package com.piersonleo.lockscreensound;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Pierson Leo on 13-Oct-15.
 */
public class SecondScreen extends Activity {

    ListView lv;
    SearchView sv;

    String  [] items;
    String lockIsChosen=null;
    String unlockIsChosen= null;
    ArrayAdapter<String> adp;
    private static final String TAG = SecondScreen.class.getSimpleName();

    int newPosition2;
    int newPosition3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_layout);
        startService(new Intent(getApplicationContext(), LockScreenService.class));

        Intent activityThatCalled = getIntent();
        Bundle b = activityThatCalled.getExtras();
        lockIsChosen =  b.getString("Lock");
        unlockIsChosen =  b.getString("Unlock");

        lv = (ListView) findViewById(R.id.lvMusicList);
        sv = (SearchView) findViewById(R.id.inputSearch);



       // mySongs = (ArrayList)b.getParcelableArrayList("songFile");
        final ArrayList<File> mySongs = findSongs(Environment.getExternalStorageDirectory());


        items = new String[mySongs.size()];
        items = activityThatCalled.getStringArrayExtra("songN");

        adp = new ArrayAdapter<String>(getApplicationContext(), R.layout.song_layout, R.id.textView, items);
        lv.setAdapter(adp);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adp.getFilter().filter(newText);
                return false;
            }


        });


        //Listener for when the music is chosen
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(lockIsChosen!=null) {


                        lockIsChosen = null;

                        String data_send = (String) lv.getItemAtPosition(position);
                    //passing file directory to previous activity
                       // String filepath = Environment.getExternalStorageDirectory().getPath();

                        for (int i = 0; i < mySongs.size(); i++) {

                            if (items[i] == data_send) {

                                newPosition2 = i;

                                break;
                            }
                        }

                      /*  File file = new File(filepath, mySongs.get(newPosition[newPosition2]).toString());
                        File f2 = new File(file.getAbsolutePath());
                        String path = f2.getAbsolutePath();
*/
                    Intent goingBack = new Intent();
                    goingBack.putExtra("dataSend", data_send);

                    try {
                        Intent i = new Intent("my.action");
                        i.putExtra("posLock", newPosition2).putExtra("songlistLock", mySongs).putExtra("lockSound", "lock");
                        sendBroadcast(i);
                    }catch (Exception e) {
                        Log.e(TAG, "Intent error");
                    }

                    setResult(RESULT_OK, goingBack);

                    finish();

                }
                if(unlockIsChosen!=null) {

                    unlockIsChosen = null;
                    String data_send = (String) lv.getItemAtPosition(position);

                    Intent goingBack = new Intent();
                    goingBack.putExtra("dataSend", data_send);

                    for (int i = 0; i < mySongs.size(); i++) {

                        if (items[i] == data_send) {

                            newPosition3 = i;

                            break;
                        }
                    }

                    try {
                        Intent i = new Intent("my.action.unlock");
                        i.putExtra("posUnlock", newPosition3).putExtra("songlistUnlock", mySongs).putExtra("unlockSound", "unlock");
                        sendBroadcast(i);
                    }catch (Exception e) {
                        Log.e(TAG, "Intent error2");
                    }

                    setResult(RESULT_OK, goingBack);

                    finish();
                }
            }
        });

    }

    //function for findSongs
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

