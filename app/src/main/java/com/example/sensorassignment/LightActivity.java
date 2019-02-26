package com.example.sensorassignment;



import android.content.Context;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);
        //plot = findViewById(R.id.plot);
        plot = new CustomView(this, SensorType.LIGHT);
        plot.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        LinearLayout l = findViewById(R.id.linearLayout);
        l.addView(plot);
        run = true;


        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        light = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        sm.registerListener(this, light,100000 );


        t = new Thread(new Runnable() {

            @Override
            public void run() {
                while(run){
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
            }}
        });

        t.start();



    }

    public void goHome(View v){
        run = false;

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        currentValue = (int) sensorEvent.values[0];
//        int val = (int) sensorEvent.values[0];
//        plot.addPoint(val);
//        plot.invalidate();
//        Log.v("MY_TAG", "Light value is " + val);
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}
