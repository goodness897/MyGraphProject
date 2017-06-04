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
import android.view.View;

/**
 * Created by goodn on 2017-06-04.
 */

public class MyGraphView extends View {

    private Paint paint;

    private Paint textPaint;

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


    public MyGraphView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public MyGraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        paint = new Paint();

        path = new Path();

        textPaint = new Paint();
        textPaint.setTextSize(Util.convertDpToPixel(11f));
        textPaint.setColor(Color.parseColor("#9b9b9b"));

        yRect = new RectF();
        xRect = new RectF();
        textRect = new RectF();

        strYValue = new String[]{"100", "80", "60", "40", "20"};

        defaultIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.bp_grap_dot_red);
        selectIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.bp_graph_double_dot_red);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        graphWidth = MeasureSpec.getSize(widthMeasureSpec);
        graphHeight = MeasureSpec.getSize(heightMeasureSpec);

        graphStartX = getLeft() + graphWidth * 0.02f;
        graphStartY = getTop();


    }

    @Override
    protected void onDraw(Canvas canvas) {


        drawYAxis(canvas);
        drawXAxis(canvas);

        paint.setColor(Color.RED);
        paint.setStrokeWidth(Util.convertDpToPixel(3f));
        canvas.drawPath(path, paint);


    }


    private void drawXAxis(Canvas canvas) {

        float xRectStart = getLeft() + graphWidth * 0.11f;
        float xRectWidth = graphWidth * 0.078f;
        float xRectMargin = graphWidth * 0.16f;

//        path.moveTo(xRect.centerX(), graphStartY + 100);

        for (int i = 0; i < 6; i++) {

            xRect.set(xRectStart, graphStartY, xRectStart + xRectWidth, graphHeight);
            if (i == 0) {
                path.moveTo(xRect.centerX(), graphStartY + 100);
                path.lineTo(xRect.centerX(), graphStartY + 100);

            }
            textRect.set(xRectStart, endYAxis + graphHeight * 0.18f, xRectStart + xRectWidth, graphHeight);
            String text = "2017\n03.24";
            drawMultiLineText(canvas, textRect, text);
            drawPoint(canvas, xRect);
            xRectStart = xRectStart + xRectMargin;
        }


    }

    private void drawPoint(Canvas canvas, RectF xRect) {

        path.lineTo(xRect.centerX(), graphStartY + 100);
        canvas.drawBitmap(defaultIcon, xRect.centerX(), graphStartY + 100, null);

    }

    private void drawMultiLineText(Canvas canvas, RectF textRect, String text) {
        float textSize = textPaint.descent() - textPaint.ascent();
        float lineSpace = textSize * 0.2f;
        float x = textRect.left + 5;
        float y = textRect.top;
        for (String line : text.split("\n")) {
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
            canvas.drawLine(yRect.left + graphWidth * 0.067f, yRect.centerY(), yRect.right, yRect.centerY(), paint);
            if (strYValue[i].length() > 2) {
                canvas.drawText(strYValue[i], yRect.left, yRect.centerY() - textMargin, textPaint);
            } else {
                canvas.drawText(strYValue[i], yRect.left + 15, yRect.centerY() - textMargin, textPaint);
            }
            yRectStart = yRectStart + yRectMargin;
        }

        paint.setColor(getResources().getColor(R.color.color_e4e4e4));
        canvas.drawLine(getLeft(), endYAxis + graphHeight * 0.12f, graphWidth, endYAxis + graphHeight * 0.12f, paint);
    }


}
