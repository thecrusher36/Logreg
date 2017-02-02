package id.web.suryadi.logreg.helper.posting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sandi Suryadi on 1/28/2017.
 * Web : suryadi.web.id
 * Email : sandi@suryadi.web.id
 */

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "POSTING_DB";

    // Posting table name
    public static final String TABLE_USER = "posting";

    //posting table columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_POST = "post";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_APPROVED = "approved";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_POSTING_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_POST + " TEXT," + KEY_CREATED_AT + " TEXT,"
                + KEY_APPROVED + " TEXT" + ")";
        db.execSQL(CREATE_POSTING_TABLE);

        Log.d(TAG, "Database tables created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);

    }

    /**
     * Storing post details in database
     * */
    public void storePost(JSONArray jsonArray) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (int i=0; i< jsonArray.length();i++){
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                ContentValues values = new ContentValues();
                values.put(KEY_ID, jsonObject.getString("id"));
                values.put(KEY_NAME, jsonObject.getString("name"));
                values.put(KEY_POST, jsonObject.getString("post"));
                values.put(KEY_CREATED_AT, jsonObject.getString("created_at"));
                values.put(KEY_APPROVED, jsonObject.getString("approved"));

                // Inserting Row
                long id = db.insert(TABLE_USER, null, values);
                Log.d(TAG, "New post inserted into sqlite: " + id);
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        db.close(); // Closing database connection
    }

    //check if database already exist
    public boolean checkDatabase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = getReadableDatabase();
        } catch (SQLiteException e) {

        }
        if (checkDB != null) checkDB.close();
        return checkDB != null;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deletePosting() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all post from sqlite");
    }

}
