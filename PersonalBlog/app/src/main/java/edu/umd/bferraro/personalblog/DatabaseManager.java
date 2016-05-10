package edu.umd.bferraro.personalblog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by neelmistry on 4/19/16.
 * Used the tutorial at the following website to create this code
 * http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/
 */

public class DatabaseManager extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "PersonalBlogDB";
    final private Context mContext;

    // Contacts table name
    private static final String TABLE_NAMES = "names";

    // Contacts Table Columns names
    private static final String KEY_ID = "_id";
    private static final String KEY_NAME = "name";
    final static String[] columns = {KEY_ID, KEY_NAME};

    // Posts Table Name
    private static final String TABLE_POSTS = "posts";

    // Posts Table Columns names
    private static final String KEY_TITLE = "title";
    private static final String TEXT = "text";
    private static final String PHOTO_PATH = "photoPath";
    private static final String VIDEO_PATH = "videoPath";
    final static String [] postsColumn = {KEY_TITLE, TEXT, PHOTO_PATH, VIDEO_PATH};

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("Personal Blog", "onCreate");

//        String CREATE_NAMES_TABLE = "CREATE TABLE " + TABLE_NAMES + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT" + ")";

        String CREATE_POSTS_TABLE = "CREATE TABLE " + TABLE_POSTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT," + TEXT + " TEXT," + PHOTO_PATH + " TEXT," + VIDEO_PATH + " TEXT" + ")";
        System.out.println(CREATE_POSTS_TABLE);

//        db.execSQL(CREATE_NAMES_TABLE);
        db.execSQL(CREATE_POSTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("Personal Blog", "onCreate");

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMES);

        // Create tables again
        onCreate(db);
    }

    // Adding new post
    public void addPost(String titleParam, String textParam, String photoPathParam, String videoPathParam){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TITLE, titleParam);
        values.put(TEXT, textParam);
        values.put(PHOTO_PATH, photoPathParam);
        values.put(VIDEO_PATH, videoPathParam);

        db.insert(TABLE_POSTS, null, values);
        db.close();
    }


    // Adding new contact
    public void addName(String name) {
        Log.e("Personal Blog", "Adding a name to DB: " + name);
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);

        // Inserting Row
        db.insert(TABLE_NAMES, null, values);

        //  values.clear();
        db.close(); // Closing database connection
    }

    // Getting single
    public String getName(int id) {
        Log.e("Personal Blog", "Getting name");
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAMES, new String[]{KEY_ID,
                        KEY_NAME}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);


        Log.i("Personal blog", "Cursor Pos: " + Integer.toString(cursor.getPosition()));

        if (cursor != null) {
            cursor.moveToFirst(); // Picks which row
        }

        Log.i("Personal blog", "Cursor AFTER Pos: " + Integer.toString(cursor.getPosition()));

        String name = cursor.getString(1); // Get String grabs a column

           db.close();
        // return name
        return name;

    }

    public ViewPost getViewPost(int id){
        ViewPost viewPost;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_POSTS, new String[]{KEY_ID, KEY_TITLE, TEXT, PHOTO_PATH, VIDEO_PATH}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        viewPost = new ViewPost(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4), null);

        return viewPost;
    }

    // Delete all records
    public void clearAll() {
        getWritableDatabase().delete(TABLE_NAMES, null, null);
    }

    public Cursor readAllNames() {
        return getWritableDatabase().query(TABLE_NAMES,
                columns, null, new String[]{}, null, null,
                null);
    }

    // Get Count
    public int getDBCount() {
        Log.e("Personal blog", "Getting Count of DB");

        Cursor c = readAllNames();
        return c.getCount();
    }

    // Updating single
    public void update(String oldVersion, String newVersion) {
        Log.e("Personal Blog", "Updating " + oldVersion + "to" + newVersion);

        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, newVersion);

        db.update(TABLE_NAMES, values,
                KEY_NAME + "=?",
                new String[]{oldVersion});

        db.close();
    }

    // Deleting single
    public void deleteContact(String beGone) {
        Log.e("Personal Blog", "Deleting" + beGone);

        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(TABLE_NAMES,
                KEY_NAME + "=?",
                new String[]{beGone});
        db.close();

    }


    public void deleteDatabase() {
        mContext.deleteDatabase(DATABASE_NAME);
    }

    public SQLiteDatabase getDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db;
    }

}
