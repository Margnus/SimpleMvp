package com.lxf.mvp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.widget.Scroller;

public class ScrollView extends View {

    Paint mPaint;

    Scroller scroller;

    int mSlop = 0;

    public ScrollView(Context context) {
        this(context, null);
    }

    public ScrollView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(20f);
        mSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        AccelerateInterpolator interpolator = new AccelerateInterpolator(1.2f);
        scroller = new Scroller(context, interpolator);

    }

    public void startScroll(int dx, int dy){
        scroller.forceFinished(true);
        int x = getScrollX();
        int y = getScrollY();
        scroller.startScroll(x, y, x+dx, y+dy, 100);
        invalidate();
    }

    VelocityTracker velocityTracker;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(velocityTracker == null){
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                restoreLocation(event);
                break;
            case MotionEvent.ACTION_MOVE:
                int x = (int) (event.getX() - mLastX);
                int y = (int) (event.getY() - mLastY);
                if(Math.abs(x) > mSlop || Math.abs(y) > mSlop){
                    scrollBy(-x, -y);
                    restoreLocation(event);
                }
                break;
            case MotionEvent.ACTION_UP:
                velocityTracker.computeCurrentVelocity(1000,2000.0f);
                int xVelocity = (int) velocityTracker.getXVelocity();
                int yVelocity = (int) velocityTracker.getYVelocity();
                Log.d("OnTouch", "onTouchEvent: xVelocity:"+xVelocity+" yVelocity:"+yVelocity);
                if ( Math.abs(xVelocity) > 10
                        || Math.abs(yVelocity) > 10 ) {
                    scroller.fling(getScrollX(),getScrollY(),
                            -xVelocity,-yVelocity,-1000,1000,-1000,2000);
                    invalidate();
                }
                break;
        }
        return true;
    }

    private float mLastX;
    private float mLastY;

    private void restoreLocation(MotionEvent event) {
        mLastX = event.getX();
        mLastY = event.getY();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {

            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            if (scroller.getCurrX() == getScrollX()
                    && scroller.getCurrY() == getScrollY() ) {
                postInvalidate();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GRAY);

        canvas.drawText("1234564", 0, 3, 10f, 10f, mPaint);

        canvas.drawCircle(100f, 100f, 20f, mPaint);
    }
}
