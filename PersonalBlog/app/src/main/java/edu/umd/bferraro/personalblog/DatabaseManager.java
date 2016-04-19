package edu.umd.bferraro.personalblog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by neelmistry on 4/19/16.
 * Used the tutorial at the following website to create this code
 * http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/
 */
public class DatabaseManager extends SQLiteOpenHelper{
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "PersonalBlogDB";

    // Contacts table name
    private static final String TABLE_NAMES = "names";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NAMES_TABLE = "CREATE TABLE " + TABLE_NAMES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT" + ")";
        System.out.println(CREATE_NAMES_TABLE);
        db.execSQL(CREATE_NAMES_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMES);

        // Create tables again
        onCreate(db);
    }

    // Adding new contact
    public void addName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);

        // Inserting Row
        db.insert(TABLE_NAMES, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    public String getName(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAMES, new String[] { KEY_ID,
                        KEY_NAME}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        String name = cursor.getString(1);
        // return contact
        return name;

    }

//    // Getting All Contacts
//    public List<Contact> getAllContacts() {}
//
//    // Getting contacts Count
//    public int getContactsCount() {}
//    // Updating single contact
//    public int updateContact(Contact contact) {}
//
//    // Deleting single contact
//    public void deleteContact(Contact contact) {}
}
