package com.piersonleo.lockscreensound;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Pierson Leo on 13-Oct-15.
 */
public class SecondScreen extends Activity {
    ListView lv;
    String  [] items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.music_layout);

        Intent activityThatCalled = getIntent();

        lv = (ListView) findViewById(R.id.lvMusicList);

        //Array for finding Music File
        ArrayList<File> mySongs = findSongs(Environment.getExternalStorageDirectory());

        //For storing the music names
        items = new String[mySongs.size()];

        //Show music names
        for(int i =0; i<mySongs.size(); i++){
            //toast(mySongs.get(i).getName().toString());
            items[i]= (mySongs.get(i).getName().toString().replace(".mp3","").replace(".wav",""));

        }

        ArrayAdapter<String> adp = new ArrayAdapter<String>(getApplicationContext(), R.layout.song_layout, R.id.textView, items);
        lv.setAdapter(adp);

        //Listener for when the music is chosen
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String data_send = (String)lv.getItemAtPosition(position);

                String filepath = Environment.getExternalStorageDirectory().getPath();

                File file = new File(filepath);
                File f2= new File(file.getAbsolutePath());
                String path = f2.getAbsolutePath();

                Intent goingBack = new Intent();
                goingBack.putExtra("dataSend", data_send).putExtra("lname", path);


                setResult(RESULT_OK, goingBack);

                finish();
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

