package id.web.suryadi.logreg.activity.AdminActivity;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import id.web.suryadi.logreg.R;

import java.util.ArrayList;

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

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            LayoutInflater inflater=activity.getLayoutInflater();
            convertView=inflater.inflate(id,null);
        }
        list_item item = items.get(position);
        TextView ap_name = (TextView) convertView.findViewById(R.id.ap_name);
        TextView ap_post = (TextView) convertView.findViewById(R.id.ap_post);
        TextView ap_datetime = (TextView) convertView.findViewById(R.id.ap_datetime);
        Button ap_btnApprove = (Button) convertView.findViewById(R.id.ap_btnAprove);

        ap_name.setText(item.getName());
        ap_post.setText(item.getPost());
        ap_datetime.setText(item.getDatetime());
        if (item.getApprove() == 0){
            ap_btnApprove.setText(R.string.approve);
        } else {
            ap_btnApprove.setText(R.string.unapprove);
        }
        return convertView;
    }
}
