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
package com.lab.igor.labtesttask1.ocr;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;

import com.lab.igor.labtesttask1.activity.OcrCaptureActivity;
import com.lab.igor.labtesttask1.adapter.DetectedDrugsAdapter;
import com.lab.igor.labtesttask1.camera.GraphicOverlay;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A very simple Processor which gets detected TextBlocks and adds them to the overlay
 * as OcrGraphics.
 */
public class OcrDetectorProcessor implements Detector.Processor<TextBlock> {

    private RecyclerView recyclerView;
    private GraphicOverlay<OcrGraphic> graphicOverlay;
    private OcrCaptureActivity ocrCaptureActivity;
    private String[] drugStrings;
    private ArrayList<String> usersDrugs;
    private Map<String, String> mapDrugs;
    private DetectedDrugsAdapter adapter;
    private String whereToGo;

    public OcrDetectorProcessor(GraphicOverlay<OcrGraphic> ocrGraphicOverlay, String[] strings,/* DatabaseHelper databaseHelper,*/ Map<String, String> mapDrugs, OcrCaptureActivity ocrCaptureActivity, RecyclerView recyclerView, ArrayList<String> usersDrugs, String whereToGo) {
        this.graphicOverlay = ocrGraphicOverlay;
        this.drugStrings = strings;
        this.mapDrugs = mapDrugs;
        this.ocrCaptureActivity = ocrCaptureActivity;
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

        for (int i = 0; i < items.size(); ++i) {
            TextBlock item = items.valueAt(i);
            boolean flag = linearSearch(drugStrings, item.getValue().toLowerCase());

            if (item != null && flag) {// && item.getValue().split(" ")[0].split("\n")[0].length() > 3) {
                final List<String> drugsCamera = new ArrayList<>();
                for (int q = 0; q < items.size(); q++) {
                    Log.d("OcrDetectorProcessor", items.valueAt(q).getValue());
                    Log.d("OcrDetectorProcessor", items.valueAt(q).getValue() + " is added");
                    String nextArray = items.valueAt(q).getValue().split(" ")[0].split("\n")[0];
                    if (mapDrugs.get(nextArray.toLowerCase()) != null) {
                        Log.d("OcrDetectorProcessor", nextArray);
                        if (usersDrugs != null && usersDrugs.contains(nextArray.substring(0, 1).toUpperCase() + nextArray.substring(1).toLowerCase())) {
                            drugsCamera.add(nextArray + mapDrugs.get(nextArray.toLowerCase()) + nextArray);
                        } else {
                            drugsCamera.add(nextArray + mapDrugs.get(nextArray.toLowerCase()) + nextArray);
                        }
                    }

                }
                Handler handler = new Handler(ocrCaptureActivity.getMainLooper());
                handler.post(() -> {
                    recyclerView.setLayoutManager(new LinearLayoutManager(ocrCaptureActivity));
                    adapter = new DetectedDrugsAdapter(ocrCaptureActivity, drugsCamera, usersDrugs, whereToGo);
                    recyclerView.setAdapter(adapter);
                });
                Log.d("OcrDetectorProcessor", "Text detected! " + item.getValue());
                final OcrGraphic graphic = new OcrGraphic(graphicOverlay, item);
                graphicOverlay.add(graphic);
            }
        }
    }

    /**
     * Frees the resources associated with this detection processor.
     */
    @Override
    public void release() {
        graphicOverlay.clear();
    }

    private static boolean linearSearch(String[] strings, String value) {
        String values = value.split("\n")[0].split(" ")[0];
        for (String string : strings) {
            if (values.equals(string)) {
                Log.d("OcrDetetectorProcessor", "DRUG FOUND" + string);
                return true;
            }
        }
        return false;
    }

}
