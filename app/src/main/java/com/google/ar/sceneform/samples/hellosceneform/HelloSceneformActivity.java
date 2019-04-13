/*
 * Copyright 2018 Google LLC. All Rights Reserved.
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
package com.google.ar.sceneform.samples.hellosceneform;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This is an example activity that uses the Sceneform UX package to make common AR tasks easier.
 */
public class HelloSceneformActivity extends AppCompatActivity {
  private static final String TAG = HelloSceneformActivity.class.getSimpleName();
  private static final double MIN_OPENGL_VERSION = 3.0;

  private ArFragment arFragment;
  private TextView counterEl;
  private ModelRenderable lunkaRenderable;
  private ModelRenderable hamsterRenderable;
  private ModelRenderable hamsterRenderableSchischk;
  private int counter=0;
  private float kofSdviga=0;
  private float kofSdviga2=0;
  private int pointCounter=0;
  private boolean isTapArPlane= false;
  private int maxX=3;
  private int maxY=3;
  private Node[][] arNodes = new  Node[maxX][maxY];
  private Timer timer;
    private int randomX;
    private int randomY;

    @Override
  @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
  // CompletableFuture requires api level 24
  // FutureReturnValueIgnored is not valid
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (!checkIsSupportedDeviceOrFinish(this)) {
      return;
    }

    Intent intent = getIntent();
    maxX=intent.getIntExtra("MaxX",3);
    maxY=intent.getIntExtra("MaxY",3);


    setContentView(R.layout.activity_ux);
    counterEl = findViewById(R.id.Counter);
    arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);

    // When you build a Renderable, Sceneform loads its resources in the background while returning
    // a CompletableFuture. Call thenAccept(), handle(), or check isDone() before calling get().
    ModelRenderable.builder()
        .setSource(this, R.raw.lunka)
        .build()
        .thenAccept(renderable -> lunkaRenderable = renderable)
        .exceptionally(
            throwable -> {
              Toast toast =
                  Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
              toast.setGravity(Gravity.CENTER, 0, 0);
              toast.show();
              return null;
            });

      ModelRenderable.builder()
              .setSource(this, R.raw.hamster_lunka_two)
              .build()
              .thenAccept(renderable -> hamsterRenderable = renderable)
              .exceptionally(
                      throwable -> {
                          Toast toast =
                                  Toast.makeText(HelloSceneformActivity.this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                          toast.setGravity(Gravity.CENTER, 0, 0);
                          toast.show();
                          return null;
                      });

        ModelRenderable.builder()
                .setSource(this, R.raw.hamster_lunka_schischk)
                .build()
                .thenAccept(renderable -> hamsterRenderableSchischk = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(HelloSceneformActivity.this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

    arFragment.setOnTapArPlaneListener(
        (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
          if (lunkaRenderable == null) {
            return;
          }
          if(isTapArPlane){
              return;
          }else{
              isTapArPlane = true;
          }

          // Create the Anchor.
          Anchor anchor = hitResult.createAnchor();
          AnchorNode anchorNode = new AnchorNode(anchor);
          anchorNode.setParent(arFragment.getArSceneView().getScene());




              for (int i = 0; i < maxX; i++) {
                  kofSdviga = 0.12f * i;
                  for (int j = 0; j < maxY; j++) {
                      kofSdviga2 = 0.12f * j;
                      Node node = new Node();
                      node.setWorldPosition(new Vector3(kofSdviga2, 0, kofSdviga));
                      node.setParent(anchorNode);
                      node.setRenderable(lunkaRenderable);
                      arNodes[i][j]=node;
                      node.setName(i+";"+j);
                      node.setOnTapListener(new Node.OnTapListener() {
                          @Override
                          public void onTap(HitTestResult hitTestResult, MotionEvent motionEvent) {
                              Node nodeTapped = hitTestResult.getNode();
                              if(nodeTapped != null) {
                                  String name = nodeTapped.getName();
                                  Log.d("rerereere", name);
                                  String[] split = name.split(";");
                                  int currentX = Integer.parseInt(split[0]);
                                  int currentY = Integer.parseInt(split[1]);
                                  if (currentX == randomX && currentY == randomY) {
                                      Toast.makeText(HelloSceneformActivity.this, "ты попал", Toast.LENGTH_SHORT).show();
                                      counter++;
                                      counterEl.setText("Хомяков поймано" + " " + counter);
                                  }
                              }
                          }
                      });

                      node.setOnTouchListener(new Node.OnTouchListener() {
                          @Override
                          public boolean onTouch(HitTestResult hitTestResult, MotionEvent motionEvent) {
                              return false;
                          }
                      });

                  }
              }
             timer = new Timer();
              timer.schedule(new MyTimerTask(), 1000,1000);
        });
  }

  /**
   * Returns false and displays an error message if Sceneform can not run, true if Sceneform can run
   * on this device.
   *
   * <p>Sceneform requires Android N on the device as well as OpenGL 3.0 capabilities.
   *
   * <p>Finishes the activity if Sceneform can not run
   */
  public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
    if (Build.VERSION.SDK_INT < VERSION_CODES.N) {
      Log.e(TAG, "Sceneform requires Android N or later");
      Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show();
      activity.finish();
      return false;
    }
    String openGlVersionString =
        ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
            .getDeviceConfigurationInfo()
            .getGlEsVersion();
    if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
      Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
      Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
          .show();
      activity.finish();
      return false;
    }
    return true;
  }

  private class MyTimerTask extends TimerTask{


      @Override
      public void run() {

          Node nodePrev = arNodes[randomX][randomY];

          Random random = new Random();
          randomX =random.nextInt(maxX);
          randomY =random.nextInt(maxY);
          Log.d("4343", randomX +" "+ randomY);
          Node nodeNext = arNodes[randomX][randomY];
          runOnUiThread(new Runnable() {
              @Override
              public void run() {
                  nodePrev.setRenderable(lunkaRenderable);
                  nodeNext.setRenderable(hamsterRenderable);
                  nodeNext.setName(randomX+";"+randomY);
              }
          });

      }
  }

    @Override
    protected void onPause() {
        super.onPause();
        if(timer!=null){
            timer.cancel();
        }
        isTapArPlane = false;

    }

    }





