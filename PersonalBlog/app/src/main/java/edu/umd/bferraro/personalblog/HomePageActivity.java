package edu.umd.bferraro.personalblog;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.VideoView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.widget.*;

public class HomePageActivity extends AppCompatActivity {
    final int REQUEST_PHOTO = 0;
    final int REQUEST_VIDEO = 1;
    final int REQUEST_GALLERY = 2;

    VideoView video;
    ImageButton mPlayButton;

    ListView postListView;
    Button newPostButton, backupButton,restoreButton;
    TextView noPostsTextView;

    Intent newPostIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Button mButtonPhoto = (Button)findViewById(R.id.PhotoPost);
        Button mButtonGallery = (Button)findViewById(R.id.OpenGallery);
        Button mButtonVideo = (Button)findViewById(R.id.VideoPost);

        openCameraForPhotos(mButtonPhoto);
        openCameraForVideos(mButtonVideo);
        openGallery(mButtonGallery);

        postListView = (ListView) findViewById(R.id.postListView);
        newPostButton = (Button) findViewById(R.id.newPostTextView);
        backupButton = (Button) findViewById(R.id.backup);
        restoreButton = (Button) findViewById(R.id.restore);
        noPostsTextView = (TextView) findViewById(R.id.noPosts);


        newPostButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                newPostIntent = new Intent(HomePageActivity.this, NewPostActivity.class);
                startActivityForResult(newPostIntent, 0);
            }
        });

        backupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // TODO - Implement backup button


            }
        });

        backupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // TODO - Implement restore button


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

    private void openCameraForPhotos(Button mButtonPhoto){
        mButtonPhoto.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        startActivityForResult(intent, REQUEST_PHOTO);
                    }
                }
        );
    }

    private void openGallery(Button mButtonGallery){
        mButtonGallery.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/* video/*");
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GALLERY);
                    }
                }
        );
    }

    private void openCameraForVideos(Button mButtonVideo){
        mButtonVideo.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("android.media.action.VIDEO_CAPTURE");
                        startActivityForResult(intent, REQUEST_VIDEO);
                    }
                }
        );
    }

    //The code for the following method was created using the following website as a reference
    //http://www.theappguruz.com/blog/android-take-photo-camera-gallery-code-sample
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_PHOTO) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                File destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ImageView photo = (ImageView) findViewById(R.id.ivImage);
                photo.setImageBitmap(thumbnail);
            } else if (requestCode == REQUEST_GALLERY) {
                Uri selectedImage = data.getData();

                if(selectedImage.toString().contains("images")) {
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    ImageView photo = (ImageView) findViewById(R.id.ivImage);
                    // Set the Image in ImageView after decoding the String
                    photo.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
                }
                else if(selectedImage.toString().contains("video")){
                    video = (VideoView)findViewById(R.id.VideoView);
                    video.setVideoURI(selectedImage);
                    // Setup a play button to start the video
                    mPlayButton = (ImageButton) findViewById(R.id.play_button);
                    mPlayButton.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            video.start();
                            mPlayButton.setVisibility(View.GONE);
                        }
                    });
                }
            } else if (requestCode == REQUEST_VIDEO) {
                Uri videoUri = data.getData();
                video = (VideoView)findViewById(R.id.VideoView);
                video.setVideoURI(videoUri);
                // Setup a play button to start the video
                mPlayButton = (ImageButton) findViewById(R.id.play_button);
                mPlayButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        video.start();
                        mPlayButton.setVisibility(View.GONE);
                    }
                });
            }
        }
    }
}
