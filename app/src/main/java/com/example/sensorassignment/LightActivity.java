package com.example.sensorassignment;



import android.content.Context;
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

public class LightActivity extends AppCompatActivity implements SensorEventListener {
    CustomView plot;
    Boolean run;
    CountDownTimer c;
    SensorManager sm;
    Sensor light;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        plot = findViewById(R.id.plot);
        run = false;

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        light = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        sm.registerListener(this, light,100000 );
        Log.v("MY_TAG", "max acc value" +light.getMaximumRange());

        Thread thread = new Thread(){

        };

//        c = new CountDownTimer(Integer.MAX_VALUE,100) {
//            @Override
//            public void onTick(long l) {
//                Random r = new Random();
//                int randomNum = r.nextInt(100);
//                plot.addPoint(randomNum);
//                plot.invalidate();
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//        };


    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int val = (int) sensorEvent.values[0];
        plot.addPoint(val);
        plot.invalidate();
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
