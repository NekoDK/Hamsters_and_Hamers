package com.google.ar.sceneform.samples.hellosceneform;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


    }

    public void openGrandMasterGame(View view){
        Intent intent = new Intent(this, GrandMasterActivity.class);
        intent.putExtra("MaxZ",2);
        intent.putExtra("Period",2000);
        intent.putExtra("Delay",2000);
        startActivity(intent);
    }
    public void openHardGame (View view){
        Intent intent = new Intent(this, HardActivity.class);
        startActivity(intent);
    }
    public void openMediumGame (View view){
        Intent intent = new Intent(this, HelloSceneformActivity.class);
        startActivity(intent);
    }
    public void openEasyGame (View view){
        Intent intent = new Intent(this, HelloSceneformActivity.class);
        intent.putExtra("MaxX",2);
        intent.putExtra("MaxY",2);
        startActivity(intent);
    }
    public void openSetting (View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
    public void Finish (View view){
        finish();

    }

}
