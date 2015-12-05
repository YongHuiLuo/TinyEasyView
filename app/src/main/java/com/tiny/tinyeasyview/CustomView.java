package com.tiny.tinyeasyview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

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
        final float x = event.getRawX();
        final float y = event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                float diffX = x - mLastX;
                float diffY = y - mLastY;

                float translationX = ViewHelper.getTranslationX(this) + diffX;
                float translationY = ViewHelper.getTranslationY(this) + diffY;

                ViewHelper.setTranslationX(this, translationX);
                ViewHelper.setTranslationY(this, translationY);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }

        mLastX = x;
        mLastY = y;
        return true;
    }
}
