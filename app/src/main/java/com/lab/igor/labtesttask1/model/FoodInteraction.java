package com.lab.igor.labtesttask1.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodInteraction {

    private int id;
    private String drugName;
    private String interaction;

    public FoodInteraction() {}

    public FoodInteraction(String interaction) {
        this.interaction = interaction;
    }

    public FoodInteraction(int id, String drugName, String interaction) {
        this.id = id;
        this.drugName = drugName;
        this.interaction = interaction;
    }

}
