package com.example.sensorassignment;


import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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

public class AccelerometerActivity extends AppCompatActivity implements SensorEventListener {
    CustomView plot;
    Boolean run;
    CountDownTimer c;
    SensorManager sm;
    Sensor acc;
    int currentValue;
    Thread t;
    Button b;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);

        plot = new CustomView(this, SensorType.ACCELEROMETER);

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
        acc = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, acc,100000 );



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

    public void home(View v) {
        run = false;
        Intent toHome = new Intent(this, MainActivity.class);
        startActivity(toHome);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        currentValue = (int) Math.sqrt(sensorEvent.values[0]*sensorEvent.values[0] + sensorEvent.values[1] * sensorEvent.values[1]
                + sensorEvent.values[2] * sensorEvent.values[2]);;
        changeImage(currentValue);


    }


    public void goHome(View v) {
        run = false;
        Intent toHome = new Intent(this, MainActivity.class);
        startActivity(toHome);

    }

    public void changeImage(int val){
        if (val <= 4) {
            img.setBackgroundResource(R.drawable.g1);
        } else if (val <= 8) {
            img.setBackgroundResource(R.drawable.g2);

        } else if (val <=12) {
            img.setBackgroundResource(R.drawable.g3);

        } else if (val <= 18) {
            img.setBackgroundResource(R.drawable.g4);

        } else if (val <= 24) {
            img.setBackgroundResource(R.drawable.g5);

        } else if (val <= 28) {
            img.setBackgroundResource(R.drawable.g6);

        } else if (val <= 40) {
            img.setBackgroundResource(R.drawable.g7);

        } else if (val <= 49) {
            img.setBackgroundResource(R.drawable.g8);

        }else if (val <= 63) {
            img.setBackgroundResource(R.drawable.g9);

        }else if (val <= 80) {
            img.setBackgroundResource(R.drawable.g10);

        }else if (val <= 88) {
            img.setBackgroundResource(R.drawable.g11);

        }else if (val <= 96) {
            img.setBackgroundResource(R.drawable.g12);

        }else if (val <= 104) {
            img.setBackgroundResource(R.drawable.g13);

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}