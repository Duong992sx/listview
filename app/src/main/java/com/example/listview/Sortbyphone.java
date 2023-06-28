package com.example.listview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Sortbyphone {
    public static void Sorter(ArrayList<Contact> data)
    {
        Collections.sort(data,new Comparator<Contact>()

        {

            @Override
            public int compare(Contact o1, Contact o2) {
                return (new Integer(((Contact) o1).getPhone())).compareTo(new Integer(((Contact) o2).getPhone()));


            }
        });
    }
}
