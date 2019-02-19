package com.lab.igor.labtesttask1;

import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AppPreLoadNew {

    private static List<String> fooDrugs;
    private static Map<String, String> fooFoodInters;
    private static Map<String, String> fooDrugInters;

    public static Resources cResources;

    public AppPreLoadNew(Resources resources) {
        cResources = resources;
    }

    public static void reinitialize() {
        if (Objects.isNull(fooDrugs)) {
            fooDrugs = foo();
        }

        if (Objects.isNull(fooFoodInters)) {
            fooFoodInters = fooFoodInters();
        }

        if (Objects.isNull(fooDrugInters)) {
            fooDrugInters = fooDrugInters();
        }
    }

    public static List<String> getFooDrugs() {
        if (Objects.isNull(fooDrugs)) {
            fooDrugs = foo();
        }

        return fooDrugs;
    }

    public static Map<String, String> getFooFoodInters() {
        if (Objects.isNull(fooFoodInters)) {
            fooFoodInters = fooFoodInters();
        }

        return fooFoodInters;
    }

    public static Map<String, String> getFooDrugInters() {
        if (Objects.isNull(fooDrugInters)) {
            fooDrugInters = fooDrugInters();
        }

        return fooDrugInters;
    }

    private static List<String> foo() {
        List<String> drugNamesWithSynonyms = new ArrayList<String>();

        InputStream inputStream = cResources.openRawResource(R.raw.drugs_and_synonyms);
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while((line = bufferedReader.readLine()) != null) {
                drugNamesWithSynonyms.add(line.toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return drugNamesWithSynonyms;
    }

    private static Map<String, String> fooFoodInters() {
        Map<String, String> foodInteractions = new HashMap<String, String>();

        InputStream inputStream = cResources.openRawResource(R.raw.food_interactions);

        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] array = line.split("q,q");
                if (array[2].equals("1")) {
                    foodInteractions.put(array[0], " has food interaction: " + array[1] + " We have found " + array[2] + " food interaction with ");
                } else {
                    foodInteractions.put(array[0], " has food interaction: " + array[1] + " We have found " + array[2] + " food interactions with ");
                }
            }
        } catch(IOException exception) {
            exception.printStackTrace();
        }

        return foodInteractions;
    }

    private static Map<String, String> fooDrugInters() {
        Map<String, String> drugInteractions = new HashMap<String, String>();

        InputStream inputStream = cResources.openRawResource(R.raw.drug_interactions);

        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] array = line.split("q,q");
                drugInteractions.put(array[0], " interacts with " + array[1] + ". We have found " + array[2] + " drugs which interacts with ");
            }
        } catch(IOException exception) {
            exception.printStackTrace();
        }

        return drugInteractions;
    }





}
