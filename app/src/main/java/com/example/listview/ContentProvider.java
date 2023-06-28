package com.example.listview;

import android.app.Activity;
import android.database.Cursor;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.widget.Toast;

import java.util.ArrayList;

public class ContentProvider {
    public ContentProvider(Activity activity) {
        this.activity = activity;
    }

    private Activity activity;

    public ArrayList<Contact> getAllContact() {
        ArrayList<Contact> listContact = new ArrayList<>();
        String[] projection = new String[]//danh sach cot ta can lay
                {
                        ContactsContract.CommonDataKinds.Phone._ID,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI,
                };
        Cursor cursor = activity.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection, null, null, null);//tham so thu 2,3 la dieu kien de chon ,cot 4 laf orderby theo cot nao
        if (cursor.moveToFirst()) {
            do {
                Contact c = new Contact(cursor.getInt(0), cursor.getString(3),
                        cursor.getString(1), cursor.getString(2));
                listContact.add(c);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listContact;

    }

    public void getCallLog() {
        //ArrayList<Contact> listContact=new ArrayList<>();

        String[] projection = new String[]//danh sach cot ta can lay
                {
                        CallLog.Calls.DATE,
                        CallLog.Calls.NUMBER,
                        CallLog.Calls.DURATION
                };
        Cursor cursor = activity.getContentResolver().query(
                CallLog.Calls.CONTENT_URI,
                projection, CallLog.Calls.DURATION + "<?", new String[]{"30"}, CallLog.Calls.DATE + "Asc");
        //tham so thu 2,3 la dieu kien de chon ,cot 4 laf orderby theo cot nao
        cursor.moveToFirst();
        String s = "";
        while (cursor.isAfterLast() == false) {
            for (int i = 0; i < cursor.getColumnCount(); i++) {
               s+=cursor.getString(i)+"-";
            }
//             s+= "\n";
//                cursor.moveToNext();
        }
        cursor.close();
        Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
    }

}
