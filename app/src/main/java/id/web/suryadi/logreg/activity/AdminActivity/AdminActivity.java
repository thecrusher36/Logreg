package id.web.suryadi.logreg.activity.AdminActivity;

import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import id.web.suryadi.logreg.R;
import id.web.suryadi.logreg.activity.LoginActivity;
import id.web.suryadi.logreg.app.AppConfig;
import id.web.suryadi.logreg.app.AppController;
import id.web.suryadi.logreg.helper.SessionManager;
import id.web.suryadi.logreg.helper.jFunction;
import id.web.suryadi.logreg.helper.users.SQLiteHandler;
import id.web.suryadi.logreg.helper.jFunction;

public class AdminActivity extends Activity {

    private TextView txtName;
    private TextView txtEmail;
    private Button btnLogout;

    private SQLiteHandler db;
    private SessionManager session;
    private ProgressDialog pDialog;

    private id.web.suryadi.logreg.helper.posting.SQLiteHandler db_post;
    private ArrayList<list_item> arrayList = new ArrayList<>();
    private Adapter_Ap adapter_ap;
    private ListView listView;
    private JSONArray result;
    private jFunction jF;
    private String x = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        txtName = (TextView) findViewById(R.id.name);
        txtEmail = (TextView) findViewById(R.id.email);
        btnLogout = (Button) findViewById(R.id.btnLogout);


        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());
        db_post = new id.web.suryadi.logreg.helper.posting.SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        // Displaying the user details on the screen
        txtName.setText(name);
        txtEmail.setText(email);

        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        if (db_post.checkDatabase()) db_post.deletePosting();
        pDialog.setMessage("Getting data ...");
        showDialog();

        //get post data from server
        StringRequest stringRequest = new StringRequest(AppConfig.URL_GET_POSTING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j;
                        try {
                            j = new JSONObject(response);
                            result = j.getJSONArray("result");
                            db_post.storePost(result);
                            handleList();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        hideDialog();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();
                    }
                });

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Adding request to the queue
        requestQueue.add(stringRequest);


    }

    /**
     * handle the listview
     */
    private void handleList(){
        SQLiteDatabase db_ = db_post.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + id.web.suryadi.logreg.helper.posting.SQLiteHandler.TABLE_USER;
        try {
            Cursor cursor = db_.rawQuery(selectQuery, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        list_item item = new list_item();
                        item.setName(cursor.getString(1));
                        item.setPost(cursor.getString(2));
                        item.setDatetime(cursor.getString(3));
                        item.setApprove(Integer.parseInt(cursor.getString(4)));
                        arrayList.add(item);
                    } while (cursor.moveToNext());
                    cursor.close();
                }
            }
        } catch (SQLException e){}

        adapter_ap = new Adapter_Ap(this,R.layout.aproving_list,arrayList);
        listView = (ListView) findViewById(R.id.list_app);
        listView.setAdapter(adapter_ap);
        adapter_ap.notifyDataSetChanged();
    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();
//        db_post.deletePosting();

        // Launching the login activity
        Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
