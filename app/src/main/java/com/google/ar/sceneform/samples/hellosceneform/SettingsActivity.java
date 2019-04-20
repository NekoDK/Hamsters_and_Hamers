package com.google.ar.sceneform.samples.hellosceneform;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);

        Switch musicSwitch = findViewById(R.id.switch1);
        Switch soundSwitch = findViewById(R.id.switch2);
        Switch vibrateSwitch = findViewById(R.id.switch3);
        boolean musicSwitchIsChecked = sharedPreferences.getBoolean("musicSwitchIsChecked", true);
        musicSwitch.setChecked(musicSwitchIsChecked);
        boolean soundSwitchIsChecked = sharedPreferences.getBoolean("soundSwitchIsChecked", true);
        soundSwitch.setChecked(soundSwitchIsChecked);
        boolean vibrateSwitchIsChecked = sharedPreferences.getBoolean("vibrateSwitchIsChecked", true);
       vibrateSwitch.setChecked(vibrateSwitchIsChecked);

        musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("musicSwitchIsChecked", isChecked);
                editor.apply();
            }
        });

        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("soundSwitchIsChecked", isChecked);
                editor.apply();
            }
        });

        vibrateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("vibrateSwitchIsChecked", isChecked);
                editor.apply();
            }
        });
    }
}