package com.lxf.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.Picture;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.lxf.mvp.R;
import com.lxf.mvp.utils.Utils;

public class CanvasView extends View {

    private Paint mGridPaint, mEffectPaint;//网格画笔
    private Point mWinSize;//屏幕尺寸
    private Point mCoo;//坐标系原点

    public CanvasView(Context context) {
        this(context, null, 0);
        initView();
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制背景
        Utils.drawGrid(canvas, mWinSize, mGridPaint);
        //绘制坐标系
        Utils.drawCoo(canvas, mCoo, mWinSize, mGridPaint);

//        drawColor(canvas);
//        drawPoint(canvas);
//        drawLine(canvas);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            drawRect(canvas);
//            drawLikeCircle(canvas);
//        }
//        drawBitmap(canvas);
        stateTest(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void initView() {
        mWinSize = new Point();
        mCoo = new Point(500, 500);
        Utils.loadWinSize(getContext(), mWinSize);
        mGridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mEffectPaint = new Paint();
    }

    private void drawColor(Canvas canvas){
        canvas.drawColor(Color.parseColor("#00ff00"));
        canvas.drawARGB(255, 224, 247, 245);
        canvas.drawRGB(224, 247, 245);
    }

    private void drawPoint(Canvas canvas){
        mGridPaint.setStrokeWidth(4);
        mGridPaint.setColor(Color.RED);
        mGridPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPoint(200,200, mGridPaint);
        canvas.drawPoints(new float[]{
                400, 400, 500, 500,
                600, 400, 700, 350,
                800, 300, 900, 300
        }, mGridPaint);
    }

    private void drawLine(Canvas canvas){
        mGridPaint.setStrokeWidth(4);
        mGridPaint.setColor(Color.BLUE);
        mGridPaint.setStyle(Paint.Style.STROKE);
        canvas.drawLine(200,200, 400,400, mGridPaint);
        canvas.drawLines(new float[]{
                400, 400, 500, 500,
                600, 400, 700, 350,
                800, 300, 900, 300
        }, mGridPaint);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void drawRect(Canvas canvas){
        mGridPaint.setStrokeWidth(4);
        mGridPaint.setColor(Color.BLUE);
        mGridPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(250,250, 500,450, mGridPaint);
        canvas.drawRoundRect(550, 250, 750, 450, 20, 20, mGridPaint);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void drawLikeCircle(Canvas canvas){
        mGridPaint.setStrokeWidth(4);
        mGridPaint.setColor(Color.YELLOW);
        mGridPaint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(200, 200, 100, mGridPaint);
        canvas.drawOval(250,250, 500,450, mGridPaint);

        RectF rect = new RectF(100, 100, 500, 300);
        canvas.drawOval(rect, mGridPaint);

        RectF rectArc = new RectF(100 + 500, 100, 500 + 500, 300);
        //绘制圆弧(矩形边界,开始角度,扫过角度,使用中心?边缘两点与中心连线区域：边缘两点连线区域)
        canvas.drawArc(rectArc, 0, 90, true, mGridPaint);

        RectF rectArc2 = new RectF(100 + 500 + 300, 100, 500 + 500 + 300, 300);
        //绘制圆弧(矩形边界,开始角度,扫过角度,使用中心?边缘两点与中心连线区域：边缘两点连线区域)
        canvas.drawArc(rectArc2, 0, 90, false, mGridPaint);

    }

    /**
     * 绘制图片
     * @param canvas
     */
    private void drawBitmap(Canvas canvas) {
        //1.定点绘制图片
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
        canvas.drawBitmap(bitmap, 100, 100, mGridPaint);
        //2.适用变换矩阵绘制图片
        Matrix matrix = new Matrix();
        //设置变换矩阵:缩小3倍，斜切0.5,右移150，下移100
        matrix.setValues(new float[]{
                1, 0.5f, 1500 * 3,
                0, 1, 100 * 3,
                0, 0, 3
        });
        canvas.drawBitmap(bitmap, matrix, mGridPaint);

        //3.图片适用矩形区域不剪裁
        RectF rectf1 = new RectF(100 + 900, 100, 600 + 900, 400);
        canvas.drawBitmap(bitmap, null, rectf1, mGridPaint);

        //4.图片裁剪出的矩形区域
        Rect rect = new Rect(300, 300, 400, 400);
        //图片适用矩形区域
        RectF rectf2 = new RectF(100 + 900, 100 + 400, 600 + 900, 400 + 400);
        canvas.drawBitmap(bitmap, rect, rectf2, mGridPaint);
    }

    /**
     * Picture用法
     * @param canvas
     */
    private void drawPicture(Canvas canvas) {
        //创建Picture对象
        Picture picture = new Picture();
        //确定picture产生的Canvas元件的大小，并生成Canvas元件
        Canvas recodingCanvas = picture.beginRecording(canvas.getWidth(), canvas.getHeight());
        //Canvas元件的操作
        recodingCanvas.drawRect(100, 0, 200, 100, mGridPaint);
        recodingCanvas.drawRect(0, 100, 100, 200, mGridPaint);
        recodingCanvas.drawRect(200, 100, 300, 200, mGridPaint);
        //Canvas元件绘制结束
        picture.endRecording();

        canvas.save();
        canvas.drawPicture(picture);//使用picture的Canvas元件
        canvas.translate(0, 300);
        picture.draw(canvas);//同上：使用picture的Canvas元件
        canvas.drawPicture(picture);
        canvas.translate(350, 0);
        canvas.drawPicture(picture);
        canvas.restore();
    }

    private void stateTest(Canvas canvas) {
//        canvas.save();
//        canvas.translate(mCoo.x, mCoo.y);//将原点平移到坐标系原点
//        canvas.drawLine(500, 200, 900, 400, mGridPaint);
//        canvas.drawRect(100, 100, 300, 200, mGridPaint);
//
//        canvas.save();//保存canvas状态
//        //(角度,中心点x,中心点y)
////        canvas.rotate(45, 100, 100);
//        canvas.scale(2f,2f,100,100);
////        canvas.skew(1f, 0f);
//        mGridPaint.setColor(Color.parseColor("#880FB5FD"));
//        canvas.drawRect(100, 100, 300, 200, mGridPaint);
//        canvas.restore();//图层向下合并
//        canvas.restore();

        canvas.save();
        RectF rectf1 = new RectF(100, 100, 200, 200);
        canvas.clipRect(rectf1);
        mGridPaint.setStrokeWidth(4);
        mGridPaint.setColor(Color.BLUE);
        mGridPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(200, 200, 400, 400, mGridPaint);
        canvas.restore();
    }

    /**
     * 虚线测试
     *
     * @param canvas
     */
    private void dashEffect(Canvas canvas) {
        mGridPaint.setStrokeCap(Paint.Cap.BUTT);
        //显示100，隐藏50,显示50，隐藏50,的循环
        mGridPaint.setPathEffect(new DashPathEffect(new float[]{100, 50, 50, 50}, 0));
        Path path = new Path();
        path.moveTo(100, 650);
        path.lineTo(1000, 650);
        canvas.drawPath(path, mGridPaint);
        //显示100，隐藏50,显示60，隐藏50,的循环,偏移：mDashOffSet
        mGridPaint.setPathEffect(new DashPathEffect(new float[]{100, 50, 50, 50}, 20));
        Path pathOffset50 = new Path();
        pathOffset50.moveTo(100, 750);
        pathOffset50.lineTo(1000, 750);
        canvas.drawPath(pathOffset50, mGridPaint);
    }

    /**
     * 路径点样路径样式
     *
     * @param canvas
     */
    private void PathDashEffect(Canvas canvas) {
        canvas.save();
        canvas.translate(0, 1100);
        Path path = new Path();
        // 定义路径的起点
        path.moveTo(100, 80);
        path.lineTo(600, -100);
        path.lineTo(1000, 80);
        //变形过渡
//        mEffectPaint.setPathEffect(new PathDashPathEffect(
//                CommonPath.nStarPath(5, 16, 8), 40, mDashOffSet, PathDashPathEffect.Style.ROTATE));
//        canvas.drawPath(path, mEffectPaint);
//        canvas.restore();
//        //旋转过渡
//        canvas.save();
//        canvas.translate(0, 1200);
//        mEffectPaint.setPathEffect(new PathDashPathEffect(
//                CommonPath.nStarPath(5, 16, 8), 40, mDashOffSet, PathDashPathEffect.Style.MORPH));
//        canvas.drawPath(path, mEffectPaint);
//        canvas.restore();
//        //移动过渡
//        canvas.save();
//        canvas.translate(0, 1300);
//        mEffectPaint.setPathEffect(new PathDashPathEffect(
//                CommonPath.nStarPath(5, 16, 8), 40, mDashOffSet, PathDashPathEffect.Style.TRANSLATE));
//        canvas.drawPath(path, mEffectPaint);
//        canvas.restore();
    }

}
