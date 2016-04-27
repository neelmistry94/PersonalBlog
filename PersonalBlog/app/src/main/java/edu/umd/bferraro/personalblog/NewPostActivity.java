package edu.umd.bferraro.personalblog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class NewPostActivity extends Activity {
    final int REQUEST_PHOTO = 0;
    final int REQUEST_VIDEO = 1;
    final int REQUEST_GALLERY = 2;

    ImageButton addPicture, addVideo, addAudio, addLocation;
    Button backButton, postButton;
    EditText title, postText;

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

        //The following methods will handle the creation of posts using photo or video
        addPicture = (ImageButton) findViewById(R.id.addPictureImageButton);
        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(NewPostActivity.this).create();
                alertDialog.setTitle("Choose Photo From");

                alertDialog.setButton("Camera", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        openCameraForPhotos(addPicture);
                    }
                });
                alertDialog.setButton2("Gallery", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        openGallery();
                    }
                });


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
                        openCameraForVideos(addVideo);
                    }
                });
                alertDialog.setButton2("Gallery", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        openGallery();
                    }
                });


                alertDialog.show();
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


    private void openCameraForPhotos(ImageButton mButtonPhoto){
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

    private void openGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/* video/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GALLERY);
    }

    private void openCameraForVideos(ImageButton mButtonVideo){
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

                addPicture.setImageBitmap(thumbnail);
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
                }
                else if(selectedImage.toString().contains("video")){

                }
            } else if (requestCode == REQUEST_VIDEO) {
                Uri videoUri = data.getData();
                addVideo.setImageURI(videoUri);
            }
        }
    }



}
