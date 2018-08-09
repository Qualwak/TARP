package com.lab.igor.labtesttask1.Model;

import java.util.List;

/**
 * Created by Igor on 26-Jul-18.
 */

public class Person {

    public int age;
    public String gender;
    public List<Drug> drugList;

    public Person() {
    }

    public Person(int age) {
        this.age = age;
    }

    public Person(int age, String gender) {
        this.age = age;
        this.gender = gender;
    }

    public Person(int age, String gender, List<Drug> drugList) {
        this.age = age;
        this.gender = gender;
        this.drugList = drugList;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Drug> getDrugList() {
        return drugList;
    }

    public void setDrugList(List<Drug> drugList) {
        this.drugList = drugList;
    }
}
