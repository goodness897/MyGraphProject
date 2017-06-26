package com.example.goodn.mygraphexample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by goodn on 2017-06-04.
 */

public class MyGraphView extends View {

    private Paint paint;

    private Paint textPaint;

    private Paint linePaint;

    private RectF yRect;

    private RectF xRect;

    private RectF textRect;

    private float graphWidth;

    private float graphHeight;

    private float graphStartX;

    private float graphStartY;

    private String[] strYValue;

    private float endYAxis;

    private Bitmap defaultIcon;

    private Bitmap selectIcon;

    private Context context;

    private Path path;

    private int maxValue = 100;

    private List<Integer> listValue;

    private List<Float> pointRectList;

    private int selectLine = 0;
    private int totalWidth;

    public MyGraphView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public MyGraphView(Context context, List<Integer> listValue) {
        super(context);
        this.context = context;
        this.listValue = listValue;
        init();
    }

    public MyGraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        paint = new Paint();

        linePaint = new Paint();
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setColor(getResources().getColor(R.color.color_801b89c0));
        linePaint.setStrokeWidth(Util.convertDpToPixel(4f));
        path = new Path();

        textPaint = new Paint();
        textPaint.setTextSize(Util.convertDpToPixel(11f));
        textPaint.setColor(Color.parseColor("#9b9b9b"));

        yRect = new RectF();
        xRect = new RectF();
        textRect = new RectF();
        strYValue = new String[]{"100", "80", "60", "40", "20"};
        pointRectList = new ArrayList<>();

        defaultIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.graph_dot_blue);
        selectIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.graph_double_dot_blue);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        graphWidth = MeasureSpec.getSize(widthMeasureSpec);
        graphWidth = Util.convertDpToPixel(360);
        graphHeight = MeasureSpec.getSize(heightMeasureSpec);

        graphStartX = getLeft() + graphWidth * 0.02f;
        graphStartY = getTop();

        float xRectStart = getLeft() + graphWidth * 0.11f;
        float xRectWidth = graphWidth * 0.078f;
        float xRectMargin = graphWidth * 0.16f;

        totalWidth = (int) xRectStart + (int) ((xRectWidth + xRectMargin) * (listValue.size() - 4));

        setMeasuredDimension(totalWidth, (int) graphHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        drawYAxis(canvas);
        drawPathLine(canvas);
        drawXAxisAndValue(canvas);


    }

    private void drawPathLine(Canvas canvas) {
        float xRectStart = getLeft() + graphWidth * 0.11f;
        float xRectWidth = graphWidth * 0.078f;
        float xRectMargin = graphWidth * 0.16f;
        for (int i = 0; i < listValue.size(); i++) {

            xRect.set(xRectStart, graphStartY, xRectStart + xRectWidth, graphHeight);
            if (i == 0) {
                path.moveTo(xRect.centerX() + defaultIcon.getWidth() / 2 - 20, graphStartY + defaultIcon.getHeight() / 2 + getRatio(listValue.get(i)));
            } else {
                path.lineTo(xRect.centerX() + defaultIcon.getWidth() / 2 - 20, graphStartY + defaultIcon.getHeight() / 2 + getRatio(listValue.get(i)));
            }
            xRectStart = xRectStart + xRectMargin;
        }
        canvas.drawPath(path, linePaint);

    }

    private void drawXAxisAndValue(Canvas canvas) {

        float xRectStart = getLeft() + graphWidth * 0.11f;
        float xRectWidth = graphWidth * 0.078f;
        float xRectMargin = graphWidth * 0.16f;
        String text = "2017\n03.24";
//        path.moveTo(xRect.centerX(), graphStartY + 100);

        for (int i = 0; i < listValue.size(); i++) {

            xRect.set(xRectStart, graphStartY, xRectStart + xRectWidth, graphHeight);
            textRect.set(xRectStart, endYAxis + graphHeight * 0.18f, xRectStart + xRectWidth, graphHeight);
            drawMultiLineText(canvas, textRect, text, i);
            if (i == selectLine) {
                canvas.drawLine(xRect.centerX(), graphStartY, xRect.centerX(), endYAxis + graphHeight * 0.12f, paint);
                canvas.drawBitmap(selectIcon, xRect.centerX() - selectIcon.getWidth() / 2, graphStartY + getRatio(listValue.get(i)) - selectIcon.getHeight() / 4, null);
            } else {
                canvas.drawBitmap(defaultIcon, xRect.centerX() - defaultIcon.getWidth() / 2, graphStartY + getRatio(listValue.get(i)), null);
            }
//            drawPoint(canvas, xRect, getRatio(listValue.get(i)));
            pointRectList.add(xRect.centerX());
            xRectStart = xRectStart + xRectMargin;
        }

    }

    private float getRatio(int value) {
        float step = 6.0f;
        float maxYPoint = graphHeight * 0.07f;
        if (value == maxValue) {
            return maxYPoint;
        } else {
            return maxYPoint + ((maxValue - value) * step);
        }
    }

    private void drawMultiLineText(Canvas canvas, RectF textRect, String text, int position) {

        float textSize = textPaint.descent() - textPaint.ascent();
        float lineSpace = textSize * 0.2f;
        float x = textRect.left + 5;
        float y = textRect.top;
        for (String line : text.split("\n")) {
            if (position == selectLine) {
                textPaint.setColor(Color.parseColor("#4a4a4a"));
            } else {
                textPaint.setColor(Color.parseColor("#9b9b9b"));
            }
            canvas.drawText(line, x, y, textPaint);
            x = textRect.left;
            y += (textSize + lineSpace);
        }
    }

    private void drawYAxis(Canvas canvas) {

        float yRectStart = graphStartY + graphHeight * 0.06f;
        float yRectHeight = graphHeight * 0.064f;
        float yRectMargin = graphHeight * 0.15f;
        float textMargin = (textPaint.descent() + textPaint.ascent()) / 2;

        endYAxis = 0f;

        paint.setStrokeWidth(Util.convertDpToPixel(1f));
        paint.setColor(getResources().getColor(R.color.color_f3f3f3));

        for (int i = 0; i < 5; i++) { // Y 길이만큼

            yRect.set(graphStartX, yRectStart, graphWidth, yRectStart + yRectHeight);
            endYAxis = yRectStart + yRectHeight;
            canvas.drawLine(yRect.left + graphWidth * 0.067f, yRect.centerY(), totalWidth, yRect.centerY(), paint);
            if (strYValue[i].length() > 2) {
                canvas.drawText(strYValue[i], yRect.left, yRect.centerY() - textMargin, textPaint);
            } else {
                canvas.drawText(strYValue[i], yRect.left + 15, yRect.centerY() - textMargin, textPaint);
            }
            yRectStart = yRectStart + yRectMargin;
        }
        paint.setColor(getResources().getColor(R.color.color_e4e4e4));
        canvas.drawLine(getLeft(), endYAxis + graphHeight * 0.12f, totalWidth, endYAxis + graphHeight * 0.12f, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();

        switch (action) {
            case MotionEvent.ACTION_UP:
                for (int i = 0; i < listValue.size(); i++) {
                    if (pointRectList.get(i) + 100 > x && pointRectList.get(i) - 100 < x) {
                        selectLine = i;
                        invalidate();
                        return true;
                    }
                }
                break;
        }
        return true;
    }

}
