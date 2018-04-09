package com.lab.igor.labtesttask1.Model;

/**
 * Created by Igor on 08-Apr-18.
 */

public class Drug {

    private int id;
    private String name;
    private String description;
    private String type;


    public Drug() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Drug(int id, String name, String description, String type) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
    }
}
