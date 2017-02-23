package id.web.suryadi.logreg.activity.AdminActivity;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import id.web.suryadi.logreg.R;
import id.web.suryadi.logreg.app.AppConfig;
import id.web.suryadi.logreg.app.AppController;
import id.web.suryadi.logreg.helper.posting.SQLiteHandler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sandi Suryadi on 1/28/2017.
 * Web : suryadi.web.id
 * Email : sandi@suryadi.web.id
 */

public class Adapter_Ap extends ArrayAdapter<list_item>{
    private Activity activity;
    int id;
    ArrayList<list_item> items;
    public Adapter_Ap(Activity context, int resource, ArrayList<list_item> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.id = resource;
        this.items = objects;
    }
    private SQLiteHandler db;

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            LayoutInflater inflater=activity.getLayoutInflater();
            convertView=inflater.inflate(id,null);
        }
        final list_item item = items.get(position);
        TextView ap_name = (TextView) convertView.findViewById(R.id.ap_name);
        TextView ap_post = (TextView) convertView.findViewById(R.id.ap_post);
        TextView ap_datetime = (TextView) convertView.findViewById(R.id.ap_datetime);
        final Button ap_btnApprove = (Button) convertView.findViewById(R.id.ap_btnAprove);

        db = new SQLiteHandler(getContext());
        ap_name.setText(item.getName());
        ap_post.setText(item.getPost());
        ap_datetime.setText(item.getDatetime());
        if (item.getApprove() == 1){
            ap_btnApprove.setText(R.string.unapprove);
        } else {
            ap_btnApprove.setText(R.string.approve);
        }

        ap_btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeApproval(item.getId());
                db.updateApprove(item.getId(), item.getApprove());
                if(AdminActivity.observeApprove.get()){
                    AdminActivity.observeApprove.set(false);
                } else {
                    AdminActivity.observeApprove.set(true);
                }
            }
        });
        return convertView;
    }


    /**
     * change the approval value on server database
     * @param post_id database row
     */
    private void changeApproval (final String post_id){
        RequestQueue queue = Volley.newRequestQueue(AppController.getInstance());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_APPROVING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", post_id);
                return params;
            }
        };

        queue.add(stringRequest);
    }
}
