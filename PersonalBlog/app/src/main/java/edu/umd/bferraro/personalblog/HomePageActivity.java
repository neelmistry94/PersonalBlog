package edu.umd.bferraro.personalblog;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class HomePageActivity extends AppCompatActivity {
    String name;
    DatabaseManager dbManager;
    private SimpleCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
     //   setSupportActionBar(toolbar);

        Button mButton = (Button)findViewById(R.id.name_button);
        Button mclearButton = (Button)findViewById(R.id.clear_button);
        final EditText mEditText = (EditText)findViewById(R.id.name);
        final TextView tvDisplay = (TextView)findViewById(R.id.textViewDisplay);

         dbManager = new DatabaseManager(this);

//        Cursor c = dbManager.readAllNames();
//        mAdapter = new SimpleCursorAdapter(getApplicationContext(), R.layout., c,
//                dbManager.columns, new int[] {R.id.id_col, R.id.name_col},
//                0);

     //   setListAdapter(mAdapter);


        mButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        name = mEditText.getText().toString();

                        //SQLiteDatabase sql = dbManager.getDB();
                        //dbManager.onUpgrade(sql, 0, 1);
                        dbManager.addName(name);

//                        tvDisplay.setText(dbManager.getName(3));


                        Log.i("Personal Blog", "DB COUNT: " + Integer.toString(dbManager.getDBCount()));

                      //  c.close();
                      //  db.close();

                    }
                }
        );

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });




        mclearButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Personal Blog", "REMOVING ALL ENTRIES");
                        dbManager.clearAll();
                        dbManager.deleteDatabase();
                    }
                }
        );



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
