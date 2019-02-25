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

public class MainActivity extends AppCompatActivity  {
    CustomView plot;
    Boolean run;
    CountDownTimer c;
    SensorManager sm;
    Sensor acc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void toLight(View v) {
        Intent goToLight = new Intent(this,LightActivity.class);
        startActivity(goToLight);
    }

    public void toAccelerometer(View v) {
         Intent goToAcc = new Intent(this, AccelerometerActivity.class);
         startActivity(goToAcc);
    }




}
