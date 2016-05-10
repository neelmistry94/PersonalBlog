package edu.umd.bferraro.personalblog;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.widget.*;

import java.util.ArrayList;

public class HomePageActivity extends ListActivity {

    ListView postListView;
    Button newPostButton, backupButton,restoreButton;
    TextView noPostsTextView;
    Intent newPostIntent, backupIntent, restoreIntent;
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

                textView.setTextColor(Color.BLUE);

                return view;
            }
        };
        setListAdapter(adapter);
        noPostsTextView.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            AlertDialog alertDialog = new AlertDialog.Builder(HomePageActivity.this).create();
            alertDialog.setTitle("Successfully backed up!");
            alertDialog.show();
            return;
        }
        else if(requestCode == 0 || requestCode == 2) {
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
        newViewPost.setId(position+1);
        Intent viewPostIntent = new Intent(HomePageActivity.this, ViewPostActivity.class);
        viewPostIntent.putExtra("ViewPost", newViewPost);
        startActivityForResult(viewPostIntent, 2);
    }
}
