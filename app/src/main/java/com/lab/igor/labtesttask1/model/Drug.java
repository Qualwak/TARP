package com.lab.igor.labtesttask1.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Drug {

    private int id;
    private String name;
    private String description;
    private String type;
    private String synonyms;

    public Drug() {}

    public Drug(int id, String name, String description, String type, String synonyms) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.synonyms = synonyms;
    }

}
