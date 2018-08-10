/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lab.igor.labtesttask1;

import android.content.Intent;
import android.os.Handler;
import android.support.design.internal.SnackbarContentLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.lab.igor.labtesttask1.Adapter.MyRecyclerViewAdapter;
import com.lab.igor.labtesttask1.Database.DatabaseHelper;
import com.lab.igor.labtesttask1.camera.GraphicOverlay;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A very simple Processor which gets detected TextBlocks and adds them to the overlay
 * as OcrGraphics.
 */
public class OcrDetectorProcessor implements Detector.Processor<TextBlock> {

    TextView textView;
    RecyclerView recyclerView;
    private GraphicOverlay<OcrGraphic> graphicOverlay;
    OcrCaptureActivity ocrCaptureActivity;
    String[] drugStrings;
    ArrayList<String> usersDrugs;
    //DatabaseHelper databaseHelper;
    Map<String, String> mapDrugs;
    String textToSearch;
    MyRecyclerViewAdapter adapter;
    String whereToGo;

    OcrDetectorProcessor(GraphicOverlay<OcrGraphic> ocrGraphicOverlay, String[] strings,/* DatabaseHelper databaseHelper,*/ Map<String, String> mapDrugs, OcrCaptureActivity ocrCaptureActivity, RecyclerView recyclerView, ArrayList<String> usersDrugs, String whereToGo) {
        graphicOverlay = ocrGraphicOverlay;
        drugStrings = strings;
        // this.databaseHelper = databaseHelper;
        this.mapDrugs = mapDrugs;
        this.ocrCaptureActivity = ocrCaptureActivity;
//        this.textView = textView;
        this.recyclerView = recyclerView;
        this.usersDrugs = usersDrugs;
        this.whereToGo = whereToGo;
    }

    /**
     * Called by the detector to deliver detection results.
     * If your application called for it, this could be a place to check for
     * equivalent detections by tracking TextBlocks that are similar in location and content from
     * previous frames, or reduce noise by eliminating TextBlocks that have not persisted through
     * multiple detections.
     */
    @Override
    public void receiveDetections(Detector.Detections<TextBlock> detections) {
        graphicOverlay.clear();
        SparseArray<TextBlock> items = detections.getDetectedItems();

//        String[] strings = new String[100000];
//        strings[0] = "apples";
//        for (int i = 1; i < strings.length - 1; i++)
//            strings[i] = "aaaaaaaaaaaaaaaaaaaaaaaaaaa";
//        strings[strings.length - 1] = "Hydrocodone";
//        boolean flag = false;

        //FasterReader fasterReader = new FasterReader();

        for (int i = 0; i < items.size(); ++i) {
            TextBlock item = items.valueAt(i);
            // boolean flag = binarySearch(drugStrings, item.getValue().toLowerCase());
            boolean flag = linearSearch(drugStrings, item.getValue().toLowerCase());
//            boolean flag = false;
//            TextBlock item = items.valueAt(i);
//            for (int j = 0; j < drugStrings.length; j++) {
//                if (item.getValue().toLowerCase().equals(drugStrings[j])) {
//                    flag = true;
//                    break;
//                }
//            }
//            Log.d("OcrDetectorProcessor", "CHECKING");
            if (item != null && flag) {// && item.getValue().split(" ")[0].split("\n")[0].length() > 3) {
                final List<String> drugsCamera = new ArrayList<>();
                for (int q = 0; q < items.size(); q++) {
                    Log.d("OcrDetectorProcessor", items.valueAt(q).getValue());
                    Log.d("OcrDetectorProcessor", items.valueAt(q).getValue() + " is added");
                    //if (!mapDrugs.get(items.valueAt(q).getValue().toLowerCase()).equals(null)) {
//                    String string1 = items.valueAt(q).getValue().toLowerCase().substring(0, items.valueAt(q).getValue().indexOf(" "));
//                    String string2 = items.valueAt(q).getValue().toLowerCase().substring(0, items.valueAt(q).getValue().indexOf("\n"));
                    String nextArray = items.valueAt(q).getValue().split(" ")[0].split("\n")[0];
                    if (mapDrugs.get(nextArray.toLowerCase()) != null) {
                        Log.d("OcrDetectorProcessor", nextArray);
//                        Log.d("OcrDetectorProcessor", string1);
//                        Log.d("OcrDetectorProcessor", string2);
                        Log.d("OcrDetectorProcessor", "HERE 1");

                        if (usersDrugs != null && usersDrugs.contains(nextArray.substring(0, 1).toUpperCase() + nextArray.substring(1).toLowerCase())) {
                            drugsCamera.add(nextArray + mapDrugs.get(nextArray.toLowerCase()) + nextArray);
                            Log.d("OcrDetectorProcessor", "HERE 2");
                        } else {
                            Log.d("OcrDetectorProcessor", "HERE 3");
                            drugsCamera.add(nextArray + mapDrugs.get(nextArray.toLowerCase()) + nextArray);
                        }
                    }
//                    else if (mapDrugs.get(items.valueAt(q).getValue().toLowerCase().substring(0, items.valueAt(q).getValue().toLowerCase().indexOf(' '))) != null) {
//                        Log.d("OCRDETECT", "HERE IS IT" + items.valueAt(q).getValue().toLowerCase().substring(0, items.valueAt(q).getValue().toLowerCase().indexOf(' ')));
//                        if (usersDrugs != null && usersDrugs.contains(items.valueAt(q).getValue().substring(0, 1).toUpperCase() + items.valueAt(q).getValue().toLowerCase().substring(1, items.valueAt(q).getValue().toLowerCase().indexOf(" ")))) {
//                            drugsCamera.add(items.valueAt(q).getValue().substring(0, items.valueAt(q).getValue().indexOf(' ')) + mapDrugs.get(items.valueAt(q).getValue().toLowerCase().substring(0, items.valueAt(q).getValue().indexOf(" "))) + items.valueAt(q).getValue().substring(0, items.valueAt(q).getValue().indexOf(" ")));
//                        } else {
//                            drugsCamera.add(items.valueAt(q).getValue().substring(0, items.valueAt(q).getValue().indexOf(' ')) + mapDrugs.get(items.valueAt(q).getValue().toLowerCase().substring(0, items.valueAt(q).getValue().indexOf(" "))) + items.valueAt(q).getValue().substring(0, items.valueAt(q).getValue().indexOf(" ")));
//                        }
//                    }
//                    } else if (mapDrugs.get(items.valueAt(q).getValue().substring(0, items.valueAt(q).getValue().indexOf(' ')).toLowerCase()) != null) {
//                        if (usersDrugs != null && usersDrugs.contains(items.valueAt(q).getValue().substring(0,1).toUpperCase() + items.valueAt(q).getValue().substring(items.valueAt(q).getValue().indexOf(' ')).toLowerCase())) {
//                            drugsCamera.add(items.valueAt(q).getValue() + mapDrugs.get(items.valueAt(q).getValue().substring(0, items.valueAt(q).getValue().indexOf(' ')).toLowerCase()) + items.valueAt(q).getValue());
//                        } else
//                            drugsCamera.add(items.valueAt(q).getValue() + mapDrugs.get(items.valueAt(q).getValue().substring(0, items.valueAt(q).getValue().indexOf(' ')).toLowerCase()) + items.valueAt(q).getValue());
//                    }
                }
                Handler handler = new Handler(ocrCaptureActivity.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
//                        String test = "";
//                        for (int i = 0; i < drugsCamera.length; i++) {
//                            Log.d("OcrDetectorProcessor", drugsCamera[i] + "YYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
//                            test += "                  " + drugsCamera[i] + "\n";
//                        }
//                        textView.setText(test);
//                        recyclerView.setLayoutManager(new LinearLayoutManager(ocrCaptureActivity));
//                        recyclerView.setAdapter(new AdapterCamera(ocrCaptureActivity, drugsCamera));

                        recyclerView.setLayoutManager(new LinearLayoutManager(ocrCaptureActivity));
                        adapter = new MyRecyclerViewAdapter(ocrCaptureActivity, drugsCamera, usersDrugs, whereToGo);
                        recyclerView.setAdapter(adapter);
                    }
                });
                Log.d("OcrDetectorProcessor", "Text detected! " + item.getValue());
                final OcrGraphic graphic = new OcrGraphic(graphicOverlay, item);
                graphicOverlay.add(graphic);
//                String drugInteraction = databaseHelper.getDrugInteractionByDrugName(item.getValue());
//                Log.d("OcrDetetectorProcessor", "INTERACTION FOUND" + drugInteraction);
//                String[] drugsCamera = new String[items.size()];
//                for (int q = 0; q < items.size(); q++) {
//                    Log.d("OcrDetectorProcessor", items.valueAt(q).getValue());
//                    drugsCamera[q] = "HEEEEEELO";
//                }
//                textToSearch = item.getValue();
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        recyclerView.setLayoutManager(new LinearLayoutManager(ocrCaptureActivity));
//                        recyclerView.setAdapter(new AdapterCamera(ocrCaptureActivity, drrrr));
//                    }
//                }).start();

//                Snackbar.make(graphicOverlay, item.getValue() + " interacts with " + mapDrugs.get(item.getValue().toLowerCase()),// + "\n See more interactions",// + drugInteraction,
//                        Snackbar.LENGTH_INDEFINITE)
//                        .setAction("View more", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                Intent intent3 = new Intent(ocrCaptureActivity, Main7Activity.class);
//                                intent3.putExtra("text_view", textToSearch);
//                                ocrCaptureActivity.startActivity(intent3);
//                            }
//                        })
//
//                        .show();


//                Snackbar.make(graphicOverlay, item.getValue() + " - FOUND \\N See more interactions...",
//                        Snackbar.LENGTH_INDEFINITE)
//                        .show();
//                flag = false;
            }
//            if ("Hydrocodone".contains(item.getValue()))
//                flag = true;
//            try {
//                boolean flag = searchingInFile(item.getValue());
//                if (item != null && flag) {
//                    Log.d("OcrDetectorProcessor", "Text detected! " + item.getValue());
//                    OcrGraphic graphic = new OcrGraphic(graphicOverlay, item);
//                    graphicOverlay.add(graphic);
////                flag = false;
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

//            if (item != null && item.getValue() != null) {
//                Log.d("OcrDetectorProcessor", "Text detected! " + item.getValue());
//                OcrGraphic graphic = new OcrGraphic(graphicOverlay, item);
//                graphicOverlay.add(graphic);
//            }
        }
    }

    /**
     * Frees the resources associated with this detection processor.
     */
    @Override
    public void release() {
        graphicOverlay.clear();
    }

    public static boolean linearSearch(String[] strings, String value) {
        String values = value.split("\n")[0].split(" ")[0];
        for (int i = 0; i < strings.length; i++) {
            if (values.equals(strings[i])) {
                Log.d("OcrDetetectorProcessor", "DRUG FOUND" + strings[i]);
                return true;
            }
        }
        return false;
    }

}
