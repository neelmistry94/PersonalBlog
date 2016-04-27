package edu.umd.bferraro.personalblog;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

public class NewPostActivity extends Activity {

    ImageButton addPicture, addVideo, addAudio, addLocation;
    Button backButton, postButton;
    EditText title, postText;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);


        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });

        postButton = (Button) findViewById(R.id.postButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = getIntent();
                // TODO - implement post button

                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        title = (EditText) findViewById(R.id.titleEditText);
        postText = (EditText) findViewById(R.id.textEditText);

        addPicture = (ImageButton) findViewById(R.id.addPictureImageButton);
        addPicture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // TODO - implement addPicture button


            }
        });
        addVideo = (ImageButton) findViewById(R.id.addVideoImageButton);
        addVideo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // TODO - implement addVideo button


            }
        });
        addAudio = (ImageButton) findViewById(R.id.addAudioImageButton);
        addAudio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // TODO - implement addAudio button


            }
        });
        addLocation = (ImageButton) findViewById(R.id.addLocationImageButton);
        addLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // TODO - implement addLocation button


            }
        });

        videoView = (VideoView) findViewById(R.id.videoView);
        //videoView.setVideoURI(Uri.parse("http://images.apple.com/media/us/ipad-pro/2016/8242d954_d694_42b8_b6b7_a871bba6ed54/films/feature/ipadpro-9-7inch-feature-cc-us-20160321_r848-9dwc.mov"));
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
