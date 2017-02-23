package id.web.suryadi.logreg.helper;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import id.web.suryadi.logreg.activity.AdminActivity.AdminActivity;
import id.web.suryadi.logreg.activity.MainActivity;
import id.web.suryadi.logreg.app.AppConfig;
import id.web.suryadi.logreg.app.AppController;

/**
 * Created by Sandi Suryadi on 1/26/2017.
 * Web : suryadi.web.id
 * Email : sandi@suryadi.web.id
 */

public class jFunction {

    public void executeLogin(Context context, String auth){
        int in = Integer.parseInt(auth);
        Intent intent = new Intent();
        switch (in){
            case 1:
                intent = new Intent(context, AdminActivity.class);
                break;
            case 2:
                intent = new Intent(context, MainActivity.class);
                break;
        }
        context.startActivity(intent);
    }

}
