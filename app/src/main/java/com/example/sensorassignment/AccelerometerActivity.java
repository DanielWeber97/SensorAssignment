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

import java.util.ArrayList;
import java.util.Random;

public class AccelerometerActivity extends AppCompatActivity implements SensorEventListener {
    CustomView plot;
    Boolean run;
    CountDownTimer c;
    SensorManager sm;
    Sensor acc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);
        plot = findViewById(R.id.plot);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acc = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, acc,100000 );

    }

    public void home(View v) {
        // Intent goHome = new Intent(this,)
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int val = (int) Math.sqrt(sensorEvent.values[0]*sensorEvent.values[0] + sensorEvent.values[1] * sensorEvent.values[1]
                + sensorEvent.values[2] * sensorEvent.values[2]);
        plot.addPoint(val);
        plot.invalidate();
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}