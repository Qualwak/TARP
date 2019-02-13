package com.lab.igor.labtesttask1.model;

/**
 * Created by Igor on 21-May-18.
 */

public class DrugInteractionsN {
    private int id, drug1Id, drug2Id;
    private String description, nameOfDrugInteraction;

    public DrugInteractionsN() {

    }

    public DrugInteractionsN(String description, String nameOfDrugInteraction) {
        this.description = description;
        this.nameOfDrugInteraction = nameOfDrugInteraction;
    }

    public DrugInteractionsN(int id, int drug1Id, int drug2Id, String description) {
        this.id = id;
        this.drug1Id = drug1Id;
        this.drug2Id = drug2Id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDrug1Id() {
        return drug1Id;
    }

    public void setDrug1Id(int drug1Id) {
        this.drug1Id = drug1Id;
    }

    public int getDrug2Id() {
        return drug2Id;
    }

    public void setDrug2Id(int drug2Id) {
        this.drug2Id = drug2Id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNameOfDrugInteraction() {
        return nameOfDrugInteraction;
    }

    public void setNameOfDrugInteraction(String nameOfDrugInteraction) {
        this.nameOfDrugInteraction = nameOfDrugInteraction;
    }
}
