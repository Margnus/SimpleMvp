package com.test.audio.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.test.audio.R;

public class RecordWaveView extends View {

    Paint mPaint;
    float roundWidth = 5f;
    //圆环的边距
    private int pandding = 10;

    public RecordWaveView(Context context) {
        this(context, null);
    }

    public RecordWaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawDefaultForPlay(canvas);
    }

    private void drawDefaultForPlay(Canvas canvas){
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(dip2px(getContext(), roundWidth));
        mPaint.setColor(getResources().getColor(R.color.RoundColor));
        float roundP = dip2px(getContext(), pandding);
        RectF mRectF = new RectF(roundP, roundP, getWidth() - roundP, getHeight() - roundP);
        canvas.drawArc(mRectF, 0, 360, false, mPaint);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
