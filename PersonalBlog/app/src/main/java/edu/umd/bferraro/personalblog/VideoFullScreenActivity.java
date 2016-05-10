package edu.umd.bferraro.personalblog;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoFullScreenActivity extends AppCompatActivity {

    VideoView videoView;
    Button backButton;
    ViewPost viewPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_full_screen);

        viewPost = (ViewPost)this.getIntent().getSerializableExtra("ViewPost");

        videoView = (VideoView)findViewById(R.id.videoContent);
        videoView.setVideoURI(Uri.parse(viewPost.getVideoPath()));
        videoView.setZOrderOnTop(true);
        videoView.start();

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        videoView.start();

        backButton = (Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
