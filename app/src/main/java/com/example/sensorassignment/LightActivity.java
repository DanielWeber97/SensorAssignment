package com.example.sensorassignment;


import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Random;

public class LightActivity extends AppCompatActivity implements SensorEventListener {
    CustomView plot;
    Boolean run;
    CountDownTimer c;
    SensorManager sm;
    Sensor light;
    int currentValue;
    Thread t;
    Button b;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);
        //plot = findViewById(R.id.plot);
        plot = new CustomView(this, SensorType.LIGHT);

        LinearLayout.LayoutParams plotParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1000);
        plotParams.setMargins(0,0,0,0);
        plot.setLayoutParams(plotParams);

        b = new Button(this);
        b.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 140));
        b.setText("Go Home");
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goHome(v);
            }
        });

        img = new ImageView(this);
        LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 450);
        img.setLayoutParams(imgParams);
        img.setBackgroundResource(R.drawable.lightbulb);


        LinearLayout l = findViewById(R.id.linearLayout);
        l.addView(plot);
        l.addView(img);
        l.addView(b);
        run = true;


        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        light = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        sm.registerListener(this, light, 100000);


        t = new Thread(new Runnable() {

            @Override
            public void run() {
                while (run) {
                    try {
                        Thread.sleep(150);
                        plot.post(new Runnable() {
                            @Override
                            public void run() {
                                int val = currentValue;
                                plot.addPoint(val);
                                plot.invalidate();
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        t.start();


    }

    public void goHome(View v) {
        run = false;
        Intent toHome = new Intent(this, MainActivity.class);
        startActivity(toHome);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        currentValue = (int) sensorEvent.values[0];
        changeImage(currentValue);
//        int val = (int) sensorEvent.values[0];
//        plot.addPoint(val);
//        plot.invalidate();
//        Log.v("MY_TAG", "Light value is " + val);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void changeImage(int val) {
        if (val <= 400) {
            img.setBackgroundResource(R.drawable.lightbulb);
        } else if (val <= 1000) {
            img.setBackgroundResource(R.drawable.lightbulbone);

        } else if (val <= 2000) {
            img.setBackgroundResource(R.drawable.lightbulbtwo);

        } else if (val <= 3000) {
            img.setBackgroundResource(R.drawable.lightbulbthree);

        } else if (val <= 4000) {
            img.setBackgroundResource(R.drawable.lightbulbfour);

        } else if (val <= 6000) {
            img.setBackgroundResource(R.drawable.lightbulbfive);

        } else if (val <= 8000) {
            img.setBackgroundResource(R.drawable.lightbulbsix);

        } else if (val <= 10000) {
            img.setBackgroundResource(R.drawable.lightbulbseven);

        } else if (val <= 30000) {
            img.setBackgroundResource(R.drawable.lightbulbeight);

        }
    }

}
