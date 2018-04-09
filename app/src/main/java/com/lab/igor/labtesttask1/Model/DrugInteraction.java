package com.lab.igor.labtesttask1.Model;

/**
 * Created by Igor on 09-Apr-18.
 */

public class DrugInteraction {


    private String name;
    private String drugName;
    private String description;

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public DrugInteraction(String name, String drugName, String description) {
        this.name = name;
        this.drugName = drugName;

        this.description = description;
    }

    public DrugInteraction() {

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
}
