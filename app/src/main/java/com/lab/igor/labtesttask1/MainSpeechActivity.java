package com.lab.igor.labtesttask1;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeechService;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.Toast;

import java.security.cert.Extension;
import java.util.List;
import java.util.Locale;

public class MainSpeechActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SPEECH";
    private Button settings;
    private Button instructions;
    private Button profile;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_speech);

        settings = findViewById(R.id.setting_speech);
        settings.setOnClickListener(this);

        profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainSpeechActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        instructions = findViewById(R.id.instructions);
        instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak();
            }
        });
        AccessibilityManager am = (AccessibilityManager) getSystemService(ACCESSIBILITY_SERVICE);
        boolean isAccessibilityEnabled = am.isEnabled();
        boolean isExploreByTouchEnabled = am.isTouchExplorationEnabled();
        if (isAccessibilityEnabled) {
            Log.i(TAG, "TURNED ON");
        } else {
            Log.i(TAG, "TURNED OFF");
        }
        // init text to speech
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                if (i == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(MainSpeechActivity.this, "This language is not supported", Toast.LENGTH_SHORT);
                    } else {

                        textToSpeech.setPitch(0.6f);
                        textToSpeech.setSpeechRate(1.0f);
//                        speak();
                    }
                }
            }


        });
    }

    private void speak() {
        String text = "If you cannot see too well. Our application provides instructions how" +
                "to turn on text-to-speech output for every element. Click on button below and" +
                "turn on, TalkBack.. After this return to our app and find information you" +
                "interested in. Do not forget to turn off TalkBack if you do not need it after" +
                "using";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        } else
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        instructions.setEnabled(false);
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_speech:
                startActivityForResult(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS), 0);
                break;
//            case R.id.instructions:
//                speak();
//                break;
            default:
                break;
        }

    }


}
