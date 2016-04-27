package edu.umd.bferraro.personalblog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.widget.Button;

public class HomePageActivity extends Activity {


    ListView postListView;
    Button newPostButton, backupButton,restoreButton;
    TextView noPostsTextView;
    Intent newPostIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

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

        restoreButton.setOnClickListener(new View.OnClickListener() {
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
}
