package com.example

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.Px
import androidx.appcompat.app.AppCompatActivity

class MyActivity : AppCompatActivity(R.layout.activity_my)

class SpottyFrameLayout(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    @Px
    private val spotSize: Float
    private val spotPaint = Paint().apply { isAntiAlias = true }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SpottyFrameLayout)
        spotSize = typedArray.getDimension(R.styleable.SpottyFrameLayout_spotSize, 0f)
        spotPaint.color = typedArray.getColor(R.styleable.SpottyFrameLayout_spotColor, 0)
        typedArray.recycle()

        setWillNotDraw(false)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        val spotRadius = (spotSize / 2)
        val maxSpotsHorizontal = (width / spotSize).toInt()
        val maxSpotsVertical = (height / spotSize).toInt()
        for (i in 0 until maxSpotsHorizontal) {
            for (j in 0 until maxSpotsVertical) {
                if (Math.random() > 0.95) {
                    val adjustedRadiusElseItIsBoring = spotRadius - (Math.random() * spotRadius).toFloat()
                    canvas.drawCircle(spotRadius + i * spotSize, spotRadius + j * spotSize, adjustedRadiusElseItIsBoring, spotPaint)
                }
            }
        }
    }
}
