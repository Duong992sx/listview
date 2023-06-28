package com.example.listview;

import java.util.Comparator;

public class Contact {


    public Object lastIndexOf;

    public void setId(int id) {
        this.id = id;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private int id;
    private String images;
    private String name;

    public String getLastName() {
        return lastName;
    }

    private String lastName;

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private String phone;
    public Contact()
    {

    }
    public Contact(int id, String images, String name, String phone)
    {

        this.id = id;
        this.images = images;
        this.name = name;
        this.phone = phone;
        String[] str=name.split("\\s");
        this.lastName=str[str.length-1];

    }

    public int getId() {
        return id;
    }

    public String getImages() {
        return images;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
    public static class NameOrder implements Comparator<Contact>
    {


        @Override
        public int compare(Contact o1, Contact o2) {
            return o1.lastName.compareTo(o2.lastName);
        }
    }



}
