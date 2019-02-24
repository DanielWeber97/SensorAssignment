package com.example.sensorassignment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.NoSuchElementException;

public class CustomView extends View {

    private ArrayList<Float> vals;
    private int xIncr;
    private int yVal;

    public ArrayList<Float> getVals(){
        return vals;
    }

    public int getxIncr(){
        return xIncr;
    }

    public int getyVal(){
        return yVal;
    }
    public CustomView(Context context) {
        super(context);
        vals = new ArrayList<Float>(10);

    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        vals = new ArrayList<Float>(10);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        vals = new ArrayList<Float>(10);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        vals = new ArrayList<Float>(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        p.setStyle(Paint.Style.FILL);
        p.setColor(Color.BLUE);
        xIncr = this.getWidth()/10;

        int max = (int)findMax(vals);
        if(max != 0) {
            yVal = this.getHeight() / max;
        } else{
            yVal = 0;
        }

        for(int i = 0; i< vals.size(); i++){

            canvas.drawCircle(i*xIncr+20,vals.get(i)*yVal,11,p);
        }

        drawLines(canvas, p);
    }

    public void clear(){
        vals.clear();
    }

    public void addPoint(float f){
        vals.add(f);
        if(vals.size()>= 11){
            vals.remove(0);
        }
    }

    public void drawLines(Canvas canvas, Paint p){
        if(vals.size()>1) {
            for (int i = 0; i < vals.size() - 1; i++) {
                canvas.drawLine(i * xIncr + 20, vals.get(i) * yVal, (i + 1) * xIncr + 20, vals.get(i + 1) * yVal, p);
            }
        }
    }



    public float findMax(ArrayList<Float> arrayList){
        float currentMax  = 0;
        for(int i = 0; i< arrayList.size(); i++){
            if (arrayList.get(i) > currentMax){
                currentMax = arrayList.get(i);
            }
        }
        return currentMax;
    }

}
