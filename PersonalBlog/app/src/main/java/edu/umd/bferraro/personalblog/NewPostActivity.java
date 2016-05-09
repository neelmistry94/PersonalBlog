package edu.umd.bferraro.personalblog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.location.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class NewPostActivity extends Activity {
    final int REQUEST_PHOTO = 0;
    final int REQUEST_VIDEO = 1;
    final int REQUEST_GALLERY = 2;

    // Location
    private static final long ONE_MIN = 1000 * 60;
    private static final long TWO_MIN = ONE_MIN * 2;
    private static final long FIVE_MIN = ONE_MIN * 5;
    private static final long MEASURE_TIME = 1000 * 30;
    private static final long POLLING_FREQ = 1000 * 10;
    private static final float MIN_ACCURACY = 25.0f;
    private static final float MIN_LAST_READ_ACCURACY = 500.0f;
    private static final float MIN_DISTANCE = 10.0f;


    ImageButton addPicture, addVideo, addAudio, addLocation;
    VideoView newPostVideo;
    Button backButton, postButton;
    EditText title, postText;
    boolean photoLoaded = false, videoLoaded = false, image = false;
    Intent viewPostIntent;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private Location mBestReading;

    private final String TAG = "NewPostActivity";

    //These variables are used to create a new ViewPost
    String titleStr, textStr, photoPath, videoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);




        title = (EditText) findViewById(R.id.titleEditText);
        postText = (EditText) findViewById(R.id.textEditText);

//        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // BEST READING
//        mBestReading = bestLastKnownLocation(MIN_LAST_READ_ACCURACY, FIVE_MIN);



        //The following methods will handle the creation of posts using photo or video
        addPicture = (ImageButton) findViewById(R.id.addPictureImageButton);
        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(NewPostActivity.this).create();
                alertDialog.setTitle("Choose Photo From");

                alertDialog.setButton("Camera", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        openCameraForPhotos();
                    }
                });

                alertDialog.setButton2("Gallery", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        image = true;
                        openGallery();
                    }
                });

                if (photoLoaded) {
                    alertDialog.setButton3("Remove Media", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            addPicture.setImageBitmap(null);
                            addPicture.setImageResource(R.mipmap.add_picture);
                            photoLoaded = false;
                        }
                    });
                }

                alertDialog.show();
            }
        });

        addVideo = (ImageButton) findViewById(R.id.addVideoImageButton);
        addVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(NewPostActivity.this).create();
                alertDialog.setTitle("Choose Video From");

                alertDialog.setButton("Camera", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        openCameraForVideos();
                    }
                });

                alertDialog.setButton2("Gallery", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        image = false;
                        openGallery();
                    }
                });

                if(videoLoaded) {
                    alertDialog.setButton3("Remove Media", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            newPostVideo.setVideoURI(null);
                            newPostVideo.setAlpha(0);
                            videoLoaded = false;
                        }
                    });
                }

                alertDialog.show();
            }
        });


        addAudio = (ImageButton) findViewById(R.id.addAudioImageButton);
        addAudio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // TODO - implement addAudio button

                Intent i = new Intent(NewPostActivity.this, AudioRecord.class);

                startActivity(i);



            }
        });
        addLocation = (ImageButton) findViewById(R.id.addLocationImageButton);
        addLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // TODO - implement addLocation button




            }
        });

        backButton = (Button) findViewById(R.id.backButton);
        postButton = (Button) findViewById(R.id.postButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });

        postButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                titleStr = title.getText().toString();
                textStr = postText.getText().toString();
                ViewPost newViewPost = new ViewPost(titleStr, textStr, photoPath, videoPath);

                viewPostIntent = new Intent(NewPostActivity.this, ViewPostActivity.class);
                viewPostIntent.putExtra("ViewPost", newViewPost);

                startActivityForResult(viewPostIntent, 0);

                Toast.makeText(NewPostActivity.this, "Post Successful", Toast.LENGTH_LONG).show();
                finish();
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

    private void openCameraForPhotos(){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, REQUEST_PHOTO);
    }

    private void openGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(image) {
            intent.setType("image/*");
        }else{
            intent.setType("video/*");
        }
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GALLERY);
    }

    private void openCameraForVideos(){
        Intent intent = new Intent("android.media.action.VIDEO_CAPTURE");
        startActivityForResult(intent, REQUEST_VIDEO);
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

                addPicture.setImageBitmap(thumbnail);
                photoPath = destination.getPath();
                System.out.print(photoPath);
                photoLoaded = true;
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

                    addPicture.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
                    photoPath = getRealPathFromURI(selectedImage);
                    photoLoaded = true;
                }
                else if(selectedImage.toString().contains("video")){
                    newPostVideo = (VideoView)findViewById(R.id.videoView);
                    newPostVideo.setVideoURI(selectedImage);
                    newPostVideo.setAlpha(1);
                    newPostVideo.start();
                    videoPath = getRealPathFromURI(selectedImage);
                    videoLoaded = true;
                }
            } else if (requestCode == REQUEST_VIDEO) {
                Uri selectedImage = data.getData();
                newPostVideo = (VideoView)findViewById(R.id.videoView);
                newPostVideo.setVideoURI(selectedImage);
                newPostVideo.setAlpha(1);
                newPostVideo.start();
                videoPath = getRealPathFromURI(selectedImage);
                videoLoaded = true;
            }
        }
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
}
