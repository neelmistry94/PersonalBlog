package edu.umd.bferraro.personalblog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.io.File;

public class ViewPostActivity extends Activity {

    Button deleteButton, backButton, voiceView;
    TextView textView, titleView;
    ImageView imageView;
    VideoView videoView;
    ViewPost viewPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_detail_page);
        viewPost = (ViewPost)this.getIntent().getSerializableExtra("ViewPost");

        String voiceURL = "";

        deleteButton = (Button) findViewById(R.id.deleteButton);
        backButton = (Button) findViewById(R.id.backButton);

        titleView = (TextView)findViewById(R.id.postTitleView);
        textView = (TextView)findViewById(R.id.textContent);
        imageView = (ImageView)findViewById(R.id.imageContent);
        videoView = (VideoView)findViewById(R.id.videoContent);
        voiceView = (Button)findViewById(R.id.voiceMemoContent);

        //This sets the title of the new post
        if (viewPost.getTitle() == null || viewPost.getTitle().isEmpty()) {
            titleView.setVisibility(View.GONE);
        } else {
            titleView.setVisibility(View.VISIBLE);
            titleView.setText(viewPost.getTitle());
        }

        //This sets the text of the new post
        if (viewPost.getTextPost() == null || viewPost.getTextPost().isEmpty()) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(viewPost.getTextPost());
        }

        //This sets the photo of the new post
        if (viewPost.getPhotoPath() == null){
            imageView.setVisibility(View.GONE);
        } else {
            File image = new File(viewPost.getPhotoPath());
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);
            imageView.setImageBitmap(bitmap);
        }

        //This sets the video of the new post
        if(viewPost.getVideoPath() == null){
            videoView.setVisibility(View.GONE);
        } else {
            videoView.setVisibility(View.VISIBLE);
            videoView.setVideoURI(Uri.parse(viewPost.getVideoPath()));
        }

        //This sets the audio of the new post
        if (viewPost.getAudioPath() == null){
            voiceView.setVisibility(View.GONE);
            Log.d("ViewPostAct", viewPost.getAudioPath());
        } else {
            voiceView.setVisibility(View.VISIBLE);
                Uri audioUri = Uri.parse(viewPost.getAudioPath());
                Log.d("ViewPostAct", viewPost.getAudioPath());
                MediaPlayer mPlayer = MediaPlayer.create(ViewPostActivity.this, audioUri);
                if(mPlayer.isPlaying()){
                    mPlayer.pause();
                } else {
                    mPlayer.start();
                }
        }


        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
                textView = null;
                titleView = null;
                imageView = null;
                videoView = null;
                viewPost = null;
                Toast.makeText(ViewPostActivity.this, "Post Deleted", Toast.LENGTH_LONG).show();
            }
        });

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
//                MediaPlayer mPlayer = MediaPlayer.create(ViewPostActivity.this, R.raw.myfile);
//                if(mPlayer.isPlaying()){
//                    mPlayer.pause();
//                } else {
//                    mPlayer.start();
//                }
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
