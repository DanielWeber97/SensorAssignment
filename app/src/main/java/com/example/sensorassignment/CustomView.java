package com.example.sensorassignment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.NoSuchElementException;

public class CustomView extends View {

    private ArrayList<Float> vals, means, stdevs;
    private int xIncr;
    private int yVal;
    private SensorType type;
    private double time =0;

    public ArrayList<Float> getVals(){
        return vals;
    }

    public int getxIncr(){
        return xIncr;
    }

    public int getyVal(){
        return yVal;
    }

    public CustomView(Context context, SensorType type){
        super(context);
        vals = new ArrayList<Float>(10);
        if(type == SensorType.LIGHT){
            this.type = SensorType.LIGHT;
        } else{
            this.type = SensorType.ACCELEROMETER;
        }


        vals = new ArrayList<Float>(10);
        means = new ArrayList<Float>(10);
        stdevs = new ArrayList<Float>(10);
    }

    public CustomView(Context context) {
        super(context);
        vals = new ArrayList<Float>(10);
        means = new ArrayList<Float>(10);
        stdevs = new ArrayList<Float>(10);

    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        vals = new ArrayList<Float>(10);
        means = new ArrayList<Float>(10);
        stdevs = new ArrayList<Float>(10);

    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        vals = new ArrayList<Float>(10);
        means = new ArrayList<Float>(10);
        stdevs = new ArrayList<Float>(10);

    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        vals = new ArrayList<Float>(10);
        means = new ArrayList<Float>(10);
        stdevs = new ArrayList<Float>(10);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint value = new Paint();
        value.setStyle(Paint.Style.FILL);
        value.setColor(Color.BLUE);
        value.setStrokeWidth(10);

        Paint mean = new Paint();
        mean.setStyle(Paint.Style.FILL);
        mean.setColor(Color.GREEN);
        mean.setStrokeWidth(10);

        Paint stdev = new Paint();
        stdev.setStyle(Paint.Style.FILL);
        stdev.setColor(Color.RED);
        stdev.setStrokeWidth(10);

        drawGridlines(canvas);
        drawXAxis(canvas);
        drawYAxis(canvas);

        drawKey(canvas);




        xIncr = this.getWidth()/10;

        int max = (int)findMax(vals,means);
        if(max != 0) {
            yVal = this.getHeight() / max;
        } else{
            yVal = 0;
        }

        for(int i = 0; i< vals.size(); i++){

            canvas.drawCircle((i+1)*xIncr+20,ycoord(vals.get(i)),11,value);
            canvas.drawCircle((i+1)*xIncr+20,ycoord(means.get(i)),11,mean);
            canvas.drawCircle((i+1)*xIncr+20,ycoord(stdevs.get(i)),11,stdev);

        }

        drawLines(canvas, value,mean,stdev);






    }

    public void clear(){
        vals.clear();
    }

    public void addPoint(float f){
        Log.v("MY_TAG", f + "");
        vals.add(f);
        means.add(mean());
        stdevs.add(std());
        if(vals.size()>= 11){
            vals.remove(0);
            means.remove(0);
            stdevs.remove(0);
        }
    }

    public void drawLines(Canvas canvas, Paint value, Paint mean, Paint stdev){
        if(vals.size()>1) {
            for (int i = 0; i < vals.size() - 1; i++) {
                canvas.drawLine((i+1) * xIncr + 20, ycoord(vals.get(i)), (i + 2) * xIncr + 20, ycoord(vals.get(i+1)), value);
                canvas.drawLine((i+1) * xIncr + 20, ycoord(means.get(i)), (i + 2) * xIncr + 20, ycoord(means.get(i+1)), mean);
                canvas.drawLine((i+1) * xIncr + 20, ycoord(stdevs.get(i)), (i + 2) * xIncr + 20, ycoord(stdevs.get(i+1)), stdev);
            }
        }
    }

    public Float mean(){
        Float mean = 0f;

        for(int i = vals.size()-1; i > vals.size()-4; i--){
            if(i < vals.size()&& i >= 0){
                mean += vals.get(i);
            }
        }
        if(vals.size()!= 0) {
            if (vals.size() >= 3) {
                return (mean / 3);
            } else {
                return mean / vals.size();
            }
        }
        return -1f;
    }

    public Float std(){
        double sum = 0;
        //Log.v("MY_TAG", "Calculating Standard Deviation");
        for(int i = vals.size()-1; i > vals.size()-4; i--){
            if(i < vals.size()&& i >= 0){
                sum += Math.pow(vals.get(i)- mean(),2);
            }
        }

        if(vals.size()!= 0) {
            if (vals.size() >= 3) {
                 sum =(sum / 3);
            } else {
                sum = sum / vals.size();
            }
        }
      //  Log.v("MY_TAG", "mean: " +mean());
        String s = Math.sqrt(sum) + "";
        Float ret = Float.parseFloat(s);

     //   Log.v("MY_TAG", "std dev: " +ret);
        return ret;
    }



    public float findMax(ArrayList<Float> al1,ArrayList<Float> al2){
        float currentMax  = 0;
        for(int i = 0; i< al1.size(); i++){
            if (al1.get(i) > currentMax){
                currentMax = al1.get(i);
            }
        }

        for(int i = 0; i< al2.size(); i++){
            if (al2.get(i) > currentMax){
                currentMax = al2.get(i);
            }
        }

        return currentMax;
    }

    public Float ycoord(Float f){
        //Log.v("MY_TAG", "sensor value: "+f + " view height: " + this.getHeight());
      //  return this.getHeight()- (f* (this.getHeight()/findMax(vals,means)));
        int maxVal;
        if(type == SensorType.LIGHT){
            double val =(this.getHeight() - (91* Math.log(f)));
            String s = val + "";
            Float ret = Float.parseFloat(s);
            if(ret >= this.getHeight()-35){
                return this.getHeight()-35f;
            }
            return Math.min(ret+90,this.getHeight()-35);
        } else{
            maxVal = 78;
        }
        return this.getHeight()-((f/maxVal) * this.getHeight());
    }

    public void drawYAxis(Canvas canvas){
        int max = (int) findMax(vals,means);
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        p.setTextSize(30);
        double dy;
       if(type == SensorType.LIGHT){


           for(int i = 0; i < 9; i++){
               dy = Math.pow(3,i);
               String text = i * dy +"";
               if(i ==8){
                   text = "30000.0";
               }
               canvas.drawText(text,5,(float)(10-i)*(this.getHeight()/10)-35,p);
               //   Log.v("MY_TAG", "View height is " + this.getHeight());
           }



       } else {
           dy = 78/10;

           for(int i = 1; i <= 10; i++){
               String text = i * dy +"";
               canvas.drawText(text,0,(float)(10-i)*(this.getHeight()/10),p);
               //   Log.v("MY_TAG", "View height is " + this.getHeight());
           }
       }


    }

    public void drawXAxis(Canvas canvas){
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        p.setTextSize(30);
            for(int i = 0; i< vals.size(); i++){
                canvas.drawText(time+"",(i+1)*xIncr+20,this.getHeight(),p);
                time+=.0150;
                time = (double)Math.round(time * 100d) / 100d;

                Log.v("MY_TAG",time+"");
            }
    }

    public void drawGridlines(Canvas canvas){
        Paint p = new Paint();
        p.setColor(Color.LTGRAY);
        p.setStrokeWidth(7);
        for(int i = 0; i<= vals.size(); i++){
            canvas.drawLine(xIncr+20,(float)(10-i)*(this.getHeight()/10)-35,this.getWidth(),(float)(10-i)*(this.getHeight()/10)-35 ,p);
            canvas.drawLine((i+1)*xIncr+20,65,(i+1)*xIncr+20,this.getHeight()-35,p);
        }
    }

    public void drawKey(Canvas canvas){

        Paint r = new Paint();
        r.setTextSize(30);
        r.setColor(Color.RED);
        canvas.drawText("Standard Dev", 315,30,r);
        canvas.drawCircle(300,20,5,r);

        Paint b = new Paint();
        b.setTextSize(30);
        b.setColor(Color.BLUE);
        canvas.drawText("Value", 915,30,b);
        canvas.drawCircle(900,20,5,b);

        Paint g = new Paint();
        g.setTextSize(30);
        g.setColor(Color.GREEN);
        canvas.drawText("Mean", 665,30,g);
        canvas.drawCircle(650,20,5,g);
    }

}
