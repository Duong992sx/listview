package com.example.listview;

import static com.example.listview.R.*;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Sub_activity extends AppCompatActivity {
    private EditText etId;
    private EditText etFullName;
    private EditText etPhone;
    private Button btnOk;
    private Button btnCancel;
    private ImageView ivImage;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_sub);
        etId=findViewById(R.id.etId);
        etFullName=findViewById(R.id.editfullname);
        etPhone=findViewById(R.id.editphone);
        ivImage=findViewById(R.id.imageView);
        btnOk=findViewById(R.id.btnOk);
        btnCancel=findViewById(R.id.btnCancel);
        //lay intent tu mainactivity chuyen sang
        Intent intent=getIntent();
        //lay bundle
        Bundle bundle=intent.getExtras();
        if(bundle!=null)
        {
            int id=bundle.getInt("Id");
            String image=bundle.getString("Image");
            String name=bundle.getString("Name");
            String phone=bundle.getString("Phone");
            etId.setText(String.valueOf(id));
            etFullName.setText(name);
            etPhone.setText(phone);
            btnOk.setText("Edit");


        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lay du lieu gui ve cho main activity
                int id=Integer.parseInt(etId.getText().toString());
                String name=etFullName.getText().toString();
                String phone=etPhone.getText().toString();
                Intent intent=new Intent();
                 Bundle b=new Bundle();
                b.putInt("Id",id);
                b.putString("Name",name);
                b.putString("Phone",phone);
                intent.putExtras(b);
                setResult(150,intent);
                finish();
            }
        });


    }
}
etId=findViewById(R.id.etId);
        etFullName=findViewById(R.id.editfullname);
        etPhone=findViewById(R.id.editphone);
        ivImage=findViewById(R.id.imageView);
        btnOk=findViewById(R.id.btnOk);
        btnCancel=findViewById(R.id.btnCancel);
        //lay intent tu mainactivity chuyen sang
        Intent intent=getIntent();
        //lay bundle
        Bundle bundle=intent.getExtras();
        if(bundle!=null)
        {
            int id=bundle.getInt("Id");
            String image=bundle.getString("Image");
            String name=bundle.getString("Name");
            String phone=bundle.getString("Phone");
            etId.setText(String.valueOf(id));
            etFullName.setText(name);
            etPhone.setText(phone);
            btnOk.setText("Edit");


        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lay du lieu gui ve cho main activity
                int id=Integer.parseInt(etId.getText().toString());
                String name=etFullName.getText().toString();
                String phone=etPhone.getText().toString();
                Intent intent=new Intent();
                 Bundle b=new Bundle();
                b.putInt("Id",id);
                b.putString("Name",name);
                b.putString("Phone",phone);
                intent.putExtras(b);
                setResult(150,intent);
                finish();
            }
        });

private EditText etId;
    private EditText etten;
    private EditText etsdt;
    private RadioButton Gioitinh;
    private Spinner quequan;
    private CheckBox sothich;

    private Button btnOk;

   // private Button btnCancel;
    private ImageView ivImage;
