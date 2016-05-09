package edu.umd.bferraro.personalblog;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class photoFullScreenActivity extends AppCompatActivity {

    ImageView imageView;
    Button backButton;
    ViewPost viewPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_full_screen);

        viewPost = (ViewPost)this.getIntent().getSerializableExtra("ViewPost");

        imageView = (ImageView)findViewById(R.id.imageContent);
        File image = new File(viewPost.getPhotoPath());
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);
        imageView.setImageBitmap(bitmap);

        backButton = (Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
