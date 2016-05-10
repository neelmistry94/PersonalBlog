package edu.umd.bferraro.personalblog;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.widget.TextView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.widget.Button;

import java.util.ArrayList;

public class HomePageActivity extends ListActivity {

    ListView postListView;
    Button newPostButton, backupButton,restoreButton;
    TextView noPostsTextView;
    Intent newPostIntent, backupIntent;
    ArrayAdapter<String> adapter;
    String name;
    DatabaseManager dbManager;
    private SimpleCursorAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
     //   setSupportActionBar(toolbar);



         dbManager = new DatabaseManager(this);
         //dbManager.deleteDatabase();

//        Cursor c = dbManager.readAllNames();
//        mAdapter = new SimpleCursorAdapter(getApplicationContext(), R.layout., c,
//                dbManager.columns, new int[] {R.id.id_col, R.id.name_col},
//                0);

     //   setListAdapter(mAdapter);


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
                startActivityForResult(backupIntent, 0);


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 0) {
            ArrayList<String> listItems = new ArrayList<String>();
            listItems.add("Post 1");

            //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
            adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1,
                    listItems);
            setListAdapter(adapter);
            noPostsTextView.setVisibility(View.GONE);
        } else if (resultCode == 2){
            adapter.clear();
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);

        ViewPost newViewPost = new ViewPost("Post 1", "Here is some text for my first post", null, null, null);

        Intent viewPostIntent = new Intent(HomePageActivity.this, ViewPostActivity.class);
        viewPostIntent.putExtra("ViewPost", newViewPost);
        startActivityForResult(viewPostIntent, 2);
    }
}
