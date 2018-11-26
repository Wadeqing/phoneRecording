package com.sinocall.phonerecordera.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * 手势签名自定义控件
 */
public class SignatureView extends View {
    private static final float STROKE_WIDTH = 10f;

    /**
     * Need to track this so the dirty region can accommodate the stroke. *
     */
    private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;

    private Paint paint = new Paint();
    private Path path = new Path();
    private ArrayList<Paint> paints = new ArrayList<Paint>();
    private long thisTime, lastTime;
    private double velocity_1;
    private double velocity_2;
    private double acceleration = 0;
    private ArrayList<Double> vs = new ArrayList<Double>();
    public Boolean isEmpty = true;
    /**
     * Optimizes painting by invalidating the smallest possible area.
     */
    private float lastTouchX;
    private float lastTouchY;
    private final RectF dirtyRect = new RectF();

    public SignatureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(STROKE_WIDTH);

    }

    /**
     * Erases the signature.
     */
    public void clear() {
        isEmpty = true;
        path.reset();
        // Repaints the entire view.
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
//        double Width = paint.getStrokeWidth()+acceleration;
        canvas.drawColor(Color.TRANSPARENT);
        double Width;
        if (velocity_2 != 0) {
            Width = paint.getStrokeWidth() - Math.abs(velocity_2) * 0.9;
        } else {
            Width = paint.getStrokeWidth();
        }
//
//
        if (Width <= 5) {
            Width = 5;
        } else if (Width > 20) {
            Width = 20;
        }
        paint.setStrokeWidth((int) (Width));
        canvas.drawPath(path, paint);

//        for (int i=0;i<vs.size();i++){
//            Double v = vs.get(i)*0.9;
//            if(v!=0){
//                Width = paint.getStrokeWidth()-Math.abs(v);
//            }else{
//                Width = paint.getStrokeWidth();
//            }
//
//            if(Width<=5){
//                Width=5;
//            }else if(Width>20){
//                Width=20;
//            }
//            paint.setStrokeWidth((int)(Width));
//            canvas.drawPath(path, paint);
//        }
        vs.clear();


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        if (thisTime == 0) {
            lastTime = System.currentTimeMillis();
        } else {
            lastTime = thisTime;
        }

        thisTime = System.currentTimeMillis();
        float distanceX = eventX - lastTouchX;
        float distanceY = eventY - lastTouchY;
        if (thisTime != lastTime) {
            float x = distanceX * distanceX;
            float y = distanceY * distanceY;
            double sss = Math.sqrt(x + y);

            velocity_2 = Math.sqrt(distanceX * distanceX + distanceY * distanceY) / (thisTime - lastTime);
            if (velocity_2 == 0) {
                velocity_2 = velocity_1;
            }
            acceleration = (velocity_2 - velocity_1) / (thisTime - lastTime);
            velocity_1 = velocity_2;
            vs.add(velocity_2);
        }
        paints.add(paint);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isEmpty = false;
                path.moveTo(eventX, eventY);
                lastTouchX = eventX;
                lastTouchY = eventY;
                // There is no end point yet, so don't waste cycles invalidating.
                return true;

            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                // Start tracking the dirty region.
                paint.setStrokeWidth(STROKE_WIDTH);//重新初始化比划粗细
                resetDirtyRect(eventX, eventY);

                // When the hardware tracks events faster than they are delivered,
                // the
                // event will contain a history of those skipped points.
                int historySize = event.getHistorySize();
                for (int i = 0; i < historySize; i++) {
                    float historicalX = event.getHistoricalX(i);
                    float historicalY = event.getHistoricalY(i);
                    expandDirtyRect(historicalX, historicalY);
                    path.lineTo(historicalX, historicalY);
                }

                // After replaying history, connect the line to the touch point.
                path.lineTo(eventX, eventY);
                break;

            default:
                // debug("Ignored touch event: " + event.toString());
                return false;
        }

        // Include half the stroke width to avoid clipping.
        invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

        lastTouchX = eventX;
        lastTouchY = eventY;

        return true;
    }

    /**
     * Called when replaying history to ensure the dirty region includes all
     * points.
     */
    private void expandDirtyRect(float historicalX, float historicalY) {
        if (historicalX < dirtyRect.left) {
            dirtyRect.left = historicalX;
        } else if (historicalX > dirtyRect.right) {
            dirtyRect.right = historicalX;
        }
        if (historicalY < dirtyRect.top) {
            dirtyRect.top = historicalY;
        } else if (historicalY > dirtyRect.bottom) {
            dirtyRect.bottom = historicalY;
        }
    }

    /**
     * Resets the dirty region when the motion event occurs.
     */
    private void resetDirtyRect(float eventX, float eventY) {

        // The lastTouchX and lastTouchY were set when the ACTION_DOWN
        // motion event occurred.
        dirtyRect.left = Math.min(lastTouchX, eventX);
        dirtyRect.right = Math.max(lastTouchX, eventX);
        dirtyRect.top = Math.min(lastTouchY, eventY);
        dirtyRect.bottom = Math.max(lastTouchY, eventY);
    }


}