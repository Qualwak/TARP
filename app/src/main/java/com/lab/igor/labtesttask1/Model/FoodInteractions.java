package com.lab.igor.labtesttask1.Model;

/**
 * Created by Igor on 20-May-18.
 */

public class FoodInteractions {

    private int id;
    private String drugName;
    private String interaction;

    public FoodInteractions() {

    }

    public FoodInteractions(int id, String drugName, String interaction) {
        this.id = id;
        this.drugName = drugName;
        this.interaction = interaction;
    }

    public FoodInteractions(String interaction) {
        this.interaction = interaction;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getInteraction() {
        return interaction;
    }

    public void setInteraction(String interaction) {
        this.interaction = interaction;
    }
}
