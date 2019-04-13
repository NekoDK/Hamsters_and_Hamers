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
        Intent intent = new Intent(this, HelloSceneformActivity.class);
        startActivity(intent);
    }
    public void openHardGame (View view){
        Intent intent = new Intent(this, HelloSceneformActivity.class);
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

    }
    public void Finish (View view){
        finish();

    }

}
