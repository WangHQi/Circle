package wanghuiqi.bawie.com.whq_week_lx;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

/**
 * date:2018-11-05
 * author:王慧琦(琦小妹i)
 * function:
 */
public class LuckyDics extends View implements View.OnClickListener {

    private int mXCenter;
    private int mYCenter;
    private Paint mPaint;
    private int[] mColor = new int[]{Color.GREEN, Color.YELLOW, Color.GREEN, Color.YELLOW, Color.GREEN, Color.YELLOW};
    private RotateAnimation mRotate;
    private String mStr[]=new String[]{"蛋挞","火锅","麻辣烫","榴莲蛋糕","烤肉","恭喜发财"};

    public LuckyDics(Context context) {
        this(context, null);
    }

    public LuckyDics(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LuckyDics(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取屏幕的宽和高
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int heigth = metrics.heightPixels;
        //获取屏幕的中心轴
        mXCenter = width / 2;
        mYCenter = heigth / 2;
        //初始化画笔
        initPaint();
        //初始化动画
        initDraw();
        setOnClickListener(this);
    }

    //设置动画
    private void initDraw() {
        mRotate = new RotateAnimation(0, 360, mXCenter, mYCenter);
        //设置时间
        mRotate.setDuration(1000);
        //重复次数
        mRotate.setRepeatCount(-1);
        //控件动画结束时,是否保持动画最后的状态
        mRotate.setFillAfter(true);
        mRotate.setInterpolator(new LinearInterpolator());
        //重复类型有两个,reverse表示倒序回放,restart表示从头播放
        mRotate.setRepeatMode(Animation.RESTART);
    }

    //初始化画笔
    private void initPaint() {
        mPaint = new Paint();
        //mPaint.setColor(Color.GRAY);
        //设置样式
        mPaint.setStyle(Paint.Style.FILL);
        //设置画笔宽
        mPaint.setStrokeWidth(20);
        //抗锯齿
        mPaint.setAntiAlias(true);


    }


    //绘制
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(mXCenter, mYCenter);
        //画圆()参数:X,Y轴,半径,画笔
        // canvas.drawCircle(0,0,50,mPaint);
        RectF rectF = new RectF(-300, -300, 300, 300);
        for (int i = 0; i < 6; i++) {
            //设置每个盘块画笔的颜色
            mPaint.setColor(mColor[i]);
            canvas.drawArc(rectF, 60 * i, 60, true, mPaint);
        }
        mPaint.setColor(Color.RED);
        //点击抽奖的圆
        canvas.drawCircle(0, 0, 50, mPaint);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(25);
        Rect rect = new Rect();
        mPaint.getTextBounds("start",0,5,rect);
        int rectWidth = rect.width();
        int rectHeigth = rect.height();
        //绘制text文本
        canvas.drawText("start",-rectWidth/2,rectHeigth/2,mPaint);

        RectF rectFText = new RectF(-200, -200, 200, 200);
        for (int i=0;i<6;i++){
            mPaint.setColor(Color.BLUE);
            Path path = new Path();
            path.addArc(rectFText,60*i+20,60);
            canvas.drawTextOnPath(mStr[i],path,0,0,mPaint);
        }
    }

    private boolean flag;

    @Override
    public void onClick(View v) {
        if (flag) {
            luckyEnd();
            random();
        } else {
            luckyStart();
        }
    }

    public void luckyEnd() {
        flag = false;
        clearAnimation();
    }

    public void luckyStart() {
        flag = true;
        startAnimation(mRotate);
    }

    public void random() {
        double random = Math.random();
        RotateAnimation rotateAnimation = new RotateAnimation(0,
                (float) (360 * random), mXCenter, mYCenter);
        rotateAnimation.setDuration(1000);
        startAnimation(rotateAnimation);
        //不掉帧
        rotateAnimation.setFillAfter(true);
    }

}
