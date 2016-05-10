package edu.umd.bferraro.personalblog;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.widget.*;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.OpenFileActivityBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class HomePageActivity extends ListActivity {

    ListView postListView;
    Button newPostButton, backupButton,restoreButton;
    TextView noPostsTextView;
    Intent newPostIntent, backupIntent, restoreIntent;
    ArrayAdapter<String> adapter;
    DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        dbManager = new DatabaseManager(this);

        postListView = getListView();
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
                backupIntent = new Intent(HomePageActivity.this, DriveBackupActivity.class);
                startActivityForResult(backupIntent, 1);


            }
        });

        restoreButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                restoreIntent = new Intent(HomePageActivity.this, DriveRestoreActivity.class);
                startActivityForResult(restoreIntent, 2);

            }
        });

        populateTable();
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

    private void populateTable(){
        ViewPost temp;
        ArrayList<String> listItems = new ArrayList<String>();

        for(int i=1; i<=dbManager.getDBCount(); i++){
            temp = dbManager.getViewPost(i);
            listItems.add(temp.getTitle());
        }

        //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =super.getView(position, convertView, parent);

                TextView textView=(TextView) view.findViewById(android.R.id.text1);

                textView.setTextColor(Color.BLACK);

                return view;
            }
        };
        setListAdapter(adapter);
        if(dbManager.getDBCount() == 0) {
            noPostsTextView.setVisibility(View.VISIBLE);
        }
        else{
            noPostsTextView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ResultCallback<DriveApi.DriveContentsResult> contentsOpenedCallback =
                new ResultCallback<DriveApi.DriveContentsResult>() {
                    @Override
                    public void onResult(DriveApi.DriveContentsResult result) {
                        if (!result.getStatus().isSuccess()) {
                            // display an error saying file can't be opened
                            return;
                        }
                        // DriveContents object contains pointers
                        // to the actual byte stream
                        DriveContents contents = result.getDriveContents();
                        try{
                            InputStream is = contents.getInputStream();
                            // write the inputStream to a FileOutputStream
                            OutputStream outputStream = new FileOutputStream(new File("/data/data/edu.umd.bferraro.personalblog/databases/PersonalBlogDB.db"));
                            int read = 0;
                            byte[] bytes = new byte[1024];

                            while ((read = is.read(bytes)) != -1) {
                                outputStream.write(bytes, 0, read);
                            }
                        }
                        catch(Exception e){}
                    }
                };

        if(requestCode == 2){
            restoreIntent = new Intent(HomePageActivity.this, DriveRestoreActivity.class);
            restoreIntent.putExtra("driveid",data.getStringExtra(OpenFileActivityBuilder.EXTRA_RESPONSE_DRIVE_ID));
            startActivityForResult(restoreIntent, 3);
        }

        if(requestCode == 1){
            AlertDialog alertDialog = new AlertDialog.Builder(HomePageActivity.this).create();
            alertDialog.setTitle("Successfully backed up!");
            alertDialog.show();
            return;
        }
        else if(requestCode == 0 || requestCode == 3) {
            populateTable();
        } else if (resultCode == 2){
            adapter.clear();
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);

        ViewPost newViewPost = dbManager.getViewPost(position+1);
        Intent viewPostIntent = new Intent(HomePageActivity.this, ViewPostActivity.class);
        viewPostIntent.putExtra("ViewPost", newViewPost);
        startActivityForResult(viewPostIntent, 0);
    }
}
