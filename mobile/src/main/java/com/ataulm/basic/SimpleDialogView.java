package com.ataulm.basic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class SimpleDialogView extends LinearLayout {

    private static final int ERASER_COLOR = 0xFFFFFF;
    private static final int ERASER_ALPHA = 0;

    private final Paint overlayPaint = new Paint();
    private final Paint eraserPaint = new Paint();

    private Bitmap overlayBufferBitmap;

    public SimpleDialogView(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setOrientation(VERTICAL);
        eraserPaint.setColor(ERASER_COLOR);
        eraserPaint.setAlpha(ERASER_ALPHA);
        eraserPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        eraserPaint.setAntiAlias(true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View.inflate(getContext(), R.layout.merge_simple_dialog, this);
        findViewById(R.id.button).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (callback != null) {
                            callback.onClickDismiss();
                        }
                    }
                }
        );
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ensureOverlayBufferHasCorrectSize();
    }

    private void ensureOverlayBufferHasCorrectSize() {
        if (overlayBufferBitmap == null) {
            overlayBufferBitmap = createNewOverlayBuffer();
        } else {
            overlayBufferBitmap = reuseOldOverlayBufferIfPossible(overlayBufferBitmap);
        }
    }

    private Bitmap createNewOverlayBuffer() {
        return Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
    }

    private Bitmap reuseOldOverlayBufferIfPossible(Bitmap oldBufferBitmap) {
        if (canReuse(oldBufferBitmap)) {
            return oldBufferBitmap;
        } else {
            oldBufferBitmap.recycle();
            return createNewOverlayBuffer();
        }
    }

    private boolean canReuse(Bitmap bitmapBuffer) {
        return getMeasuredWidth() == bitmapBuffer.getWidth()
                && getMeasuredHeight() == bitmapBuffer.getHeight();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        overlayBufferBitmap.eraseColor(Color.parseColor("#C7000000"));

        Canvas bufferCanvas = new Canvas(overlayBufferBitmap);
        bufferCanvas.drawRect(
                100,
                100,
                200,
                200,
                eraserPaint
        );

        canvas.drawBitmap(overlayBufferBitmap, 0, 0, overlayPaint);
        super.dispatchDraw(canvas);
    }

    private Callback callback;

    public void bind(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {

        void onClickDismiss();

    }

}
