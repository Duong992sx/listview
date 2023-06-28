package com.example.listview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
public class Sort {
    public static void Sorter(ArrayList<Contact> data){
    Collections.sort(data,new Comparator<Contact>(){

        @Override
        public int compare(Contact o1, Contact o2) {
          String name1=o1.getName().substring(o1.getName().lastIndexOf(" ")+1);
          String name2=o2.getName().substring(o2.getName().lastIndexOf(" ")+1);
          return name1.compareTo(name2);





        }
    });
}
}

