package com.tiny.tinyeasyview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created Tiny
 * 实现主要功能：
 * 创建时间： on 2015/12/3.
 * 修改者： 修改日期： 修改内容：
 */
public class CustomView extends View {

    private Paint paint;

    private int mPaintColor;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;
    private int mMaxinumVelocity;
    private float maxVelocityX;
    private float maxVelocityY;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomView);
        mPaintColor = typedArray.getInteger(R.styleable.CustomView_paint_color, Color.RED);
        typedArray.recycle();

        final ViewConfiguration configuration = ViewConfiguration.get(getContext());

        mTouchSlop = configuration.getScaledTouchSlop();
        mMaxinumVelocity = configuration.getScaledMaximumFlingVelocity();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(mPaintColor);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int selfWidth = getWidth();
        final int selfHeight = getHeight();
        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();

        Log.e("Tiny--", "selfWidth -- > " + selfWidth + ";selfHeight -- >" + selfHeight
                + "paddingTop -->" + paddingTop + "paddingBottom -->" + paddingBottom);

        canvas.drawCircle(selfWidth / 2, selfHeight / 2,
                Math.min(selfWidth - paddingLeft - paddingRight, selfHeight - paddingTop - paddingBottom) / 2, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        Log.e("tiny--", "widthMode -->" + widthMode + ";widthSize-->" + widthSize + ";heightMode -- >" + heightMode + ";heightSize -- >" + heightSize);
        int width = 100;
        int height = 100;

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            //Initialize a default minimum value.
            Log.e("tiny--", "ALL AT_MOST");
            width = 200;
            height = 200;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            Log.e("tiny--", "WIDTH AT_MOST");
            width = 200;
            height = heightSize;
        } else if (height == MeasureSpec.AT_MOST) {
            Log.e("tiny--", "HEIGHT AT_MOST");
            width = widthSize;
            height = 200;
        }
        setMeasuredDimension(width, height);
    }

    float mLastX = 0;
    float mLastY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);

        //different device have different value
        final float x = event.getRawX();
        final float y = event.getRawY();
        Log.i("Tiny", "x --" + x + ";y -- " + y);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // If you don`t have to mLastX and mLastY initialization,beating effect will occur.
                mLastX = x;
                mLastY = y;
                if (velocityValueChangeListener != null){
                    velocityValueChangeListener.onVelocityValueChange(0,0,0,0);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float diffX = x - mLastX;
                float diffY = y - mLastY;

                // Filter out less than mTouchSlop movement.
                Log.i("Tiny", "diffX --" + Math.abs(diffX) + ";diffY -- " + Math.abs(diffY) + ";touchSlop--" + mTouchSlop);
                if (Math.abs(diffX) > mTouchSlop || Math.abs(diffY) > mTouchSlop) {
                    Log.d("Tiny", "translation");
                    float translationX = ViewHelper.getTranslationX(this) + diffX;
                    float translationY = ViewHelper.getTranslationY(this) + diffY;
                    ViewHelper.setTranslationX(this, translationX);
                    ViewHelper.setTranslationY(this, translationY);
                    mLastX = x;
                    mLastY = y;
                }

                break;
            case MotionEvent.ACTION_UP:
                final VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, mMaxinumVelocity);
                float mXVelocity = velocityTracker.getXVelocity();
                float mYVelocity = velocityTracker.getYVelocity();

                if (mXVelocity > maxVelocityX){
                    maxVelocityX = mXVelocity;
                }

                if (mYVelocity > maxVelocityY){
                    maxVelocityY = mYVelocity;
                }

                Log.i("Tiny", "mXVelocity -- " + (mXVelocity - mLastX) + ";mYVelocity -- " + (mYVelocity - mLastY));

                Scroller scroller = new Scroller(getContext());
                scroller.startScroll((int)mLastX,(int)mLastY,(int)mXVelocity,(int)mYVelocity);
                if (velocityValueChangeListener != null){
                    velocityValueChangeListener.onVelocityValueChange(mXVelocity,maxVelocityX,mYVelocity,maxVelocityY);
                }
//                float translationX = ViewHelper.getTranslationX(this) + (mXVelocity - mLastX);
//                float translationY = ViewHelper.getTranslationY(this) + (mYVelocity - mLastY);
//                ViewHelper.setTranslationX(this, translationX);
//                ViewHelper.setTranslationY(this, translationY);
                endDrag();
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        Log.i("Tiny", "mLastX --" + mLastX + ";mLastY--" + mLastY);
        return true;
    }

    private void endDrag() {
        if (mVelocityTracker != null) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private  VelocityValueChangeListener velocityValueChangeListener;

    public void setVelocityValueChangeListener(VelocityValueChangeListener velocityValueChangeListener) {
        this.velocityValueChangeListener = velocityValueChangeListener;
    }

    public interface VelocityValueChangeListener{
        void onVelocityValueChange(float velocityX,float maxVelocityX,float velocityY,float maxVelocityY);
    }
}
