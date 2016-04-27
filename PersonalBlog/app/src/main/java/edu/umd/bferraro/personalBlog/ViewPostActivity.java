package edu.umd.bferraro.personalblog;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

public class ViewPostActivity extends Activity {


    ScrollView mainListView;
    Button deleteButton, backButton, voiceView;
    TextView textView;
    ImageView imageView;
    VideoView videoView;


    Intent viewPostIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        String voiceURL = "";

        mainListView = (ScrollView) findViewById(R.id.mainListView);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        backButton = (Button) findViewById(R.id.backButton);

        textView = (TextView)findViewById(R.id.textContent);
        imageView = (ImageView)findViewById(R.id.imageContent);
        videoView = (VideoView)findViewById(R.id.videoContent);
        voiceView = (Button)findViewById(R.id.voiceMemoContent);

        String text = textView.getText().toString().trim();
        if (text == null || text.isEmpty()) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
        }

        if (imageView == null){
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);
        }

        if (voiceView == null){
            voiceView.setVisibility(View.GONE);
        } else {
            voiceView.setVisibility(View.VISIBLE);
        }

        if (voiceURL.length() == 0){
            voiceView.setVisibility(View.GONE);
        } else {
            voiceView.setVisibility(View.VISIBLE);
        }


        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                viewPostIntent = new Intent(ViewPostActivity.this, DeletePopWindow.class);
                startActivityForResult(viewPostIntent, 0);
            }
        });

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MediaPlayer mPlayer = MediaPlayer.create(ViewPostActivity.this, R.raw.myfile);
                if(mPlayer.isPlaying()){
                    mPlayer.pause();
                } else {
                    mPlayer.start();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
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


}
