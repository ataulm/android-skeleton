package com.ataulm.basic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

public class RulerView extends View {

    private static final int KEYLINES_MAJOR_DP = 16;
    private static final int KEYLINES_MINOR_DP = 4;

    private final Paint paintMinor;
    private final Paint paintMajor;

    private final int keylinesMinorPx;
    private final int keylinesMajorPx;

    public RulerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paintMinor = new Paint();
        paintMinor.setColor(Color.argb(15, 0, 0, 0));

        paintMajor = new Paint();
        paintMajor.setColor(Color.argb(35, 0, 0, 255));

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        keylinesMinorPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, KEYLINES_MINOR_DP, displayMetrics);
        keylinesMajorPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, KEYLINES_MAJOR_DP, displayMetrics);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        for (int i = 0; i < height; i++) {
            if (i % keylinesMajorPx == 0) {
                canvas.drawLine(0, i, width, i, paintMajor);
            } else if (i % keylinesMinorPx == 0) {
                canvas.drawLine(0, i, width, i, paintMinor);
            }
        }
    }

}
