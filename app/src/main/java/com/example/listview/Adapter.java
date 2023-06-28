package com.example.listview;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class Adapter extends BaseAdapter {
    private ArrayList<Contact> data;//nguon du lieu cho baseadapter,gom danh sach contact
    private Activity context;
    private LayoutInflater inflater;

    public Adapter() {

    }

    public Adapter(ArrayList<Contact> data, Activity activity) {
        this.data = data;
        this.context = context;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }//tra ve mot phan tu

    @Override
    public long getItemId(int i) {
        return data.get(i).getId();
    }//tra ve

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (v == null)
            v = inflater.inflate(R.layout.itemlistview, null);
        ImageView imageView = v.findViewById(R.id.imageView);
        TextView tvname = v.findViewById(R.id.tvName);
        tvname.setText(data.get(i).getName());
        TextView tvphone = v.findViewById(R.id.tvPhone);
        tvphone.setText(data.get(i).getPhone());
        return v;


    }
}
