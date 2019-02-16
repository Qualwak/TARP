package com.lab.igor.labtesttask1.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DrugInteraction {

    private String name, description;

    public DrugInteraction(String nameOfDrugInteraction, String description) {
        this.name = nameOfDrugInteraction;
        this.description = description;
    }
}
