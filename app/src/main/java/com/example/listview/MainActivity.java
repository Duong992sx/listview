package com.example.listview;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.Manifest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.*;

public class MainActivity extends AppCompatActivity {
    //khai bao doi tuong luu tru danh sach cac contact
    private ArrayList<Contact> ContactList;
    private Adapter ListAdapter;
    private EditText etSearch;
    private ListView lstContact;
    private int SelectedItemId;
    private MyDB db;

    private ContentProvider cp;
    ConnectionReciever connectionReciever;
    IntentFilter intentFilter;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(requestCode==100)
//        {
//            if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
//            {
//                //permission is granted
//                this.ShowContact();
//            }
//            else {
//                Toast.makeText(this, "Until you grant the permission,", Toast.LENGTH_SHORT).show();
//            }
//        }
         if(requestCode==200)
        {
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                //permission is granted
                this.ShowContact();
            }
            else {
                Toast.makeText(this, "Until you grant the permission,", Toast.LENGTH_SHORT).show();
            }
        }
    }
    // ConnectionRecevicer recevicer;




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.mnuSortName:

                SortByName();

                break;
            case R.id.mnuSortPhone:
                Sortbyphone.Sorter(ContactList);
                ListAdapter.notifyDataSetChanged();
            case R.id.mnuBroadcast:
                Intent intent=new Intent("com.example.listview.SOME_ACTION");
                sendBroadcast(intent);





                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            super.onCreateContextMenu(menu, v, menuInfo);
            MenuInflater inflater=new MenuInflater(this);
           inflater.inflate(R.menu.edit_delete, menu);
    }
    public void resetData()
    {
        db=new MyDB(MainActivity.this,"ContacDB",null,1);
        ContactList=db.getAllContact();
        ListAdapter=new Adapter(ContactList,MainActivity.this);
        lstContact.setAdapter(ListAdapter);
    }



    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Contact c=ContactList.get(SelectedItemId);
        switch (item.getItemId())
        {

            case R.id.mnuEdit:
                //1.tao intent de mo subactivity
                Intent intent=new Intent(MainActivity.this,Sub_activity.class);
                //2.truyen du lieu sang subactivity bang bundle neu can

                Bundle b=new Bundle();
                b.putInt("Id",c.getId());
                b.putString("Image",c.getImages());
                b.putString("Name",c.getName());
                b.putString("Phone",c.getPhone());
                intent.putExtras(b);
                //3 mo subactivity bang cach goi ham
                //staractivity hoac staractivityforresult;
                startActivityForResult(intent,100);
                break;
            case R.id.mnuDelete:
              AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Xóa?");
                builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // ContactList.remove(SelectedItemId);
                       // lstContact.setAdapter(ListAdapter);
                        db.deleteContact(ContactList.get(SelectedItemId).getId());
                        resetData();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            case R.id.mnuCall:

                Intent in=new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:" +
                        c.getPhone()));
                startActivity(in);
                break;
            case R.id.mnumessage:
                Intent intent1 = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto: " + c.getPhone()));
                intent1.putExtra("sms_body", "Nội dung tin nhắn");
                startActivity(intent1);
                break;
            case R.id.mnuMail:
                Intent intentmail = new Intent(Intent.ACTION_SEND);
                intentmail.setData(Uri.parse("mailto:"));
                intentmail.setType("text/plain");
                intentmail.putExtra(Intent.EXTRA_EMAIL, new String[]{"email@address.com"});
                intentmail.putExtra(Intent.EXTRA_SUBJECT, "Tiêu đề email");
                intentmail.putExtra(Intent.EXTRA_TEXT, "Soạn nội dung email ở đây");
                try {
                   startActivity(Intent.createChooser(intentmail, "Gửi email..."));
                finish();
                  } catch (ActivityNotFoundException exception) {
                   Toast.makeText(MainActivity.this, "Không có email nào được cài đặt", Toast.LENGTH_SHORT).show();
                  }
            case R.id.mnuAlarm:
                Intent intentAlarm=new Intent(AlarmClock.ACTION_SHOW_ALARMS);
                startActivity(intentAlarm);



        }
        return super.onContextItemSelected(item);
    }

    private FloatingActionButton btnAdd;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=new MenuInflater(this);
        inflater.inflate(R.menu.optionmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle b=data.getExtras();
        int id=b.getInt("Id");
        String name=b.getString("Name");
        String phone=b.getString("Phone");
        Contact newcontact=new Contact(id,"Image",name,phone);
        if(requestCode==100 && resultCode==150)
        {
           // ContactList.add(new Contact(id,"",name,phone));
            db.addContact(newcontact);

        }
        else if(requestCode==200 && resultCode==150)
        {
          // ContactList.set(SelectedItemId,new Contact(id,"",name,phone));
            db.updateContact(id,newcontact);
        }
        ListAdapter.notifyDataSetChanged();
        lstContact.setAdapter(ListAdapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // ContactList=new ArrayList<Contact>();
        //thiet lap du lieu mau
       // ContactList.add(new Contact(1,"img1","Nguyễn Văn An ","0987976589"));
     //   ContactList.add(new Contact(2,"img2"," Đào Thu Hà ","0987976588"));
      //  ContactList.add(new Contact(3,"img3","Nguyễn  Thị Lụa  ","0987976587"));
    //     ContactList.add(new Contact(3,"img3","Nguyễn  Thị Ngoc  ","0987976587"));
        db=new MyDB(this,"ContacDB",
                null,1);
        //them du lieu LAN DAU vao db
       // db.addContact(new Contact(1,"img1","Nguyễn Văn An","0987976589"));
       // db.addContact(new Contact(2,"img3","Đào Thu Hà","0987976588"));
      //  db.addContact(new Contact(3,"img2","Nguyễn  Thị Lụa","0987976587"));
        ContactList =db.getAllContact();
        ListAdapter=new Adapter(ContactList,this);
        etSearch=findViewById(R.id.etSearch);
        lstContact=findViewById(R.id.lstContact);
        btnAdd=findViewById(R.id.btnAdd);
        lstContact.setAdapter(ListAdapter);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.tAO intent de mo activity
                Intent intent= new Intent(MainActivity.this,Sub_activity.class);
                //2 truyen du lieu sang subactivity bang bundle neu can
                //3 mo subactivity bang cach goi ham
                //staractivity hoac staractivityforresult;
                startActivityForResult(intent,200);

            }
        });

       // ShowContact();
        ShowCallLog();

        lstContact.setAdapter(ListAdapter);
        registerForContextMenu(lstContact);
        lstContact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {
                SelectedItemId=i;
                return false;
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        connectionReciever=new ConnectionReciever();
        intentFilter=new IntentFilter("com.example.listview.SOME_ACTION");
        intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANCE");
        registerReceiver(connectionReciever,intentFilter);




    }
    private void SortByName()
    {
        Collections.sort(ContactList, new Contact.NameOrder());
        ListAdapter.notifyDataSetChanged();
    }
    private void ShowContact() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                    100);
        } else {
            cp = new ContentProvider(this);
            ContactList = cp.getAllContact();
            ListAdapter = new Adapter(ContactList, this);
            lstContact.setAdapter(ListAdapter);
        }
    }
        private void ShowCallLog()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M &&
        checkSelfPermission(Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                    200);
        }else {
            cp=new ContentProvider(this);
            ContactList=cp.getAllContact();
            ListAdapter=new Adapter(ContactList,this);
            lstContact.setAdapter(ListAdapter);
        }
    }




}