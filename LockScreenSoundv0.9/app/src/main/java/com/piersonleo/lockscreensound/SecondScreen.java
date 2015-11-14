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
    int [] newPosition;
    private static final String TAG = SecondScreen.class.getSimpleName();

    int newPosition2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_layout);

        Intent activityThatCalled = getIntent();
        lockIsChosen =  activityThatCalled.getExtras().getString("Lock");
        unlockIsChosen =  activityThatCalled.getExtras().getString("Unlock");

        lv = (ListView) findViewById(R.id.lvMusicList);
        sv = (SearchView) findViewById(R.id.inputSearch);

        //Array for finding Music File
        final ArrayList<File> mySongs = findSongs(Environment.getExternalStorageDirectory());

        //For storing the music names
        items = new String[mySongs.size()];
        newPosition = new int[mySongs.size()];

        //Show music names
        for(int i =0; i<mySongs.size(); i++){
            //toast(mySongs.get(i).getName().toString());
            items[i]= (mySongs.get(i).getName().toString().replace(".mp3","").replace(".wav",""));
            newPosition[i] = i;
        }

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
                        String filepath = Environment.getExternalStorageDirectory().getPath();

                        for (int i = 0; i < mySongs.size(); i++) {

                            if (items[i] == data_send) {

                                newPosition2 = i;

                                break;
                            }
                        }

                        File file = new File(filepath, mySongs.get(newPosition[newPosition2]).toString());
                        File f2 = new File(file.getAbsolutePath());
                        String path = f2.getAbsolutePath();

                    Intent goingBack = new Intent();
                    goingBack.putExtra("dataSend", data_send).putExtra("lname", path);

                    try {
                        //  Intent intent = new Intent("my.action");
                        Intent i = new Intent("my.action");
                        i.putExtra("pos", newPosition2).putExtra("songlist", mySongs).putExtra("lockSound", "lock");
                        sendBroadcast(i);
                    }catch (Exception e) {
                        Log.e(TAG, "Intent error");
                    }

                    setResult(RESULT_OK, goingBack);

                    finish();



                }
                else if(unlockIsChosen!=null && lockIsChosen==null) {

                    unlockIsChosen = null;
                    String data_send = (String) lv.getItemAtPosition(position);
                    String filepath = Environment.getExternalStorageDirectory().getPath();

                    for (int i = 0; i < mySongs.size(); i++) {

                        if (items[i] == data_send) {

                            newPosition2 = i;

                            break;
                        }
                    }

                    File file = new File(filepath, mySongs.get(newPosition[newPosition2]).toString());
                    File f2 = new File(file.getAbsolutePath());
                    String path = f2.getAbsolutePath();

                    Intent goingBack = new Intent();
                    goingBack.putExtra("dataSend", data_send).putExtra("lname", path);

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
    //  public void toast(String text){
    //    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    //}


}

