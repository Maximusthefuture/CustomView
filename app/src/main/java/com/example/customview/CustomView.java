package com.example.customview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomView extends View {

    private static final String TAG = "CustomView";
    private static final Paint BACKGROUND_CIRCLE_PAINT = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final Paint FRONTARC_CIRCLE_PAINT = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final Paint TEXT_PAINT = new Paint(Paint.ANTI_ALIAS_FLAG);
    private  static final float STROKE_WIDTH = 50f;
    private  static final float RADIUS = 400f;
    private static final RectF ARC_RECT = new RectF(STROKE_WIDTH / 2, STROKE_WIDTH / 2, 300 - STROKE_WIDTH / 2, 300 - STROKE_WIDTH / 2);
    private static final float MAX_ANGLE = 360;
    private static final float START_ANGLE = -90;
    private static final float MAX_PROGRESS = 100;
    public static final float FONT_SIZE = 128f;
    public static final Rect mTextBounds = new Rect();

    private int mRadius;
    private RectF mArcRect;


    private int mProgress;



    public CustomView(Context context) {
        super(context, null, 0);

    }

    public CustomView(Context context, AttributeSet attrs) {
       this(context, attrs, 0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    public int getProgress() {
        return mProgress;
    }

    public void setProgress(int mProgress) {
        this.mProgress = mProgress;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

//        canvas.drawCircle(RADIUS + STROKE_WIDTH / 2, RADIUS + STROKE_WIDTH / 2, RADIUS, BACKGROUND_CIRCLE_PAINT);
        canvas.drawArc(mArcRect,  0, MAX_ANGLE, false, BACKGROUND_CIRCLE_PAINT);
        canvas.drawArc(mArcRect,  START_ANGLE, MAX_ANGLE * mProgress / MAX_PROGRESS, false, FRONTARC_CIRCLE_PAINT);
//        canvas.drawLine(0,RADIUS + STROKE_WIDTH * 2, 2 * RADIUS + STROKE_WIDTH  / 2, RADIUS + STROKE_WIDTH / 2 , TEXT_PAINT);
//        canvas.drawLine(0,RADIUS - STROKE_WIDTH * 2, 2 * RADIUS + STROKE_WIDTH  / 2, RADIUS + STROKE_WIDTH / 2 , TEXT_PAINT);

        String text = String.format("%d %%", mProgress);
//        float textWidth =  TEXT_PAINT.getTextBounds(text, 0, text.length(), mTextBounds);
        float x = mArcRect.width() / 2f - mTextBounds.width() / 2f - mTextBounds.left + mArcRect.left;

        canvas.drawText(text, RADIUS / 2, RADIUS + FONT_SIZE / 2, TEXT_PAINT);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure() called with: widthMeasureSpec = [" + MeasureSpec.toString(widthMeasureSpec) + "], heightMeasureSpec = [" + MeasureSpec.toString(heightMeasureSpec) + "]");
//        String maxProgressString = formatString(MAX_PROGRESS);
//        getTextBounds(maxProgressString);
        float desireWidth = Math.max(mTextBounds.width() * 2 * STROKE_WIDTH, getSuggestedMinimumWidth());
        float desireHeight = Math.max(mTextBounds.height() * 2 * STROKE_WIDTH, getSuggestedMinimumHeight());
        int desireSize = (int) Math.max(desireHeight, desireWidth);
        int resolveWidth = resolveSize((int) desireSize, widthMeasureSpec);
        int resolveHeight = resolveSize((int) desireSize, heightMeasureSpec);

        setMeasuredDimension(resolveWidth, resolveHeight);
    }

    private String formatString(float maxProgress) {
        return String.format("%d %%", maxProgress);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged() called with: w = [" + w + "], h = [" + h + "], oldw = [" + oldw + "], oldh = [" + oldh + "]");
        int size = Math.min(w, h);
        mArcRect = new RectF(STROKE_WIDTH / 2, STROKE_WIDTH / 2,  size - STROKE_WIDTH / 2, size - STROKE_WIDTH / 2);
    }

    private void init(Context context, @Nullable AttributeSet attribute) {
        configureBackground();

        configureFront();
        configureText();
        extractAttribute(getContext(), attribute);
    }


    void getTextBounds(String bounds) {

    }

    private void configureText() {
        TEXT_PAINT.setColor(Color.BLACK);
        TEXT_PAINT.setStyle(Paint.Style.FILL);
        TEXT_PAINT.setTextSize(FONT_SIZE);
    }

    private void configureFront() {
        FRONTARC_CIRCLE_PAINT.setColor(Color.GREEN);
        FRONTARC_CIRCLE_PAINT.setStyle(Paint.Style.STROKE);
        FRONTARC_CIRCLE_PAINT.setStrokeWidth(50f);
    }

    private void configureBackground() {
        BACKGROUND_CIRCLE_PAINT.setColor(Color.GRAY);
        BACKGROUND_CIRCLE_PAINT.setStyle(Paint.Style.STROKE);
        BACKGROUND_CIRCLE_PAINT.setStrokeWidth(STROKE_WIDTH);
    }

    public void extractAttribute(Context context, AttributeSet attribute) {
        if (attribute != null) {
            final Resources.Theme theme = context.getTheme();
            final TypedArray typedArray = theme.obtainStyledAttributes(attribute, R.styleable.CustomView, 0, 0);
            try {
//                mProgress = typedArray.getInt(R.styleable.CustomView_progress, 0);
//                mFillColor= typedArray.getColor(R.styleable.CustomViewColor_fill_color, 0);
            }finally {
                typedArray.recycle();
            }
        }
    }
}
