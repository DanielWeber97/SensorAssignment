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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity  {
    CustomView plot;
    Boolean run;
    CountDownTimer c;
    SensorManager sm;
    Sensor acc, light;
    TextView lightStat,lightInf,accStat,accInf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acc = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        light = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        lightStat = findViewById(R.id.lightStatus);
        lightInf = findViewById(R.id.lightInfo);
        accInf = findViewById(R.id.accInfo);
        accStat = findViewById(R.id.accStatus);


        lightInf.setText("Info: Range-" + light.getMaximumRange() + " Resolution- " + light.getResolution() + " Delay- "+ light.getMaxDelay() );
        accInf.setText("Info: Range-" + acc.getMaximumRange() + " Resolution- " + acc.getResolution() + " Delay- "+ acc.getMaxDelay() );
        if(acc != null){
            accStat.setText("Status: Accelerometer is present");
        } else{
            accStat.setText("Status: Accelerometer is not present");
        }

        if(light != null){
            lightStat.setText("Status: Accelerometer is present");
        } else{
            lightStat.setText("Status: Accelerometer is not present");
        }
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
