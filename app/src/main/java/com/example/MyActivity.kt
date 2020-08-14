package com.example

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.customview.widget.ExploreByTouchHelper
import kotlinx.android.synthetic.main.activity_my.*
import kotlin.math.roundToInt


class MyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)
        histogramView.show(RatingsHistogramView.RatingsHistogram(
                listOf(
                        RatingsHistogramView.RatingsHistogram.Interval(0.5f, 0.2f, 0),
                        RatingsHistogramView.RatingsHistogram.Interval(1.0f, 0.2f, 0),
                        RatingsHistogramView.RatingsHistogram.Interval(1.5f, 0.5f, 0),
                        RatingsHistogramView.RatingsHistogram.Interval(2.0f, 0.5f, 0),
                        RatingsHistogramView.RatingsHistogram.Interval(2.5f, 0.5f, 0),
                        RatingsHistogramView.RatingsHistogram.Interval(3.0f, 0.1f, 0),
                        RatingsHistogramView.RatingsHistogram.Interval(3.5f, 0.5f, 0),
                        RatingsHistogramView.RatingsHistogram.Interval(4.0f, 0.1f, 0),
                        RatingsHistogramView.RatingsHistogram.Interval(4.5f, 0.1f, 0),
                        RatingsHistogramView.RatingsHistogram.Interval(5.0f, 0.1f, 0)
                )
        ))
    }
}

class RatingsHistogramView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var ratingsHistogram: RatingsHistogram? = null
    private val paint = Paint().apply {
        color = Color.parseColor("#e5e7ef")
    }
    private val accessHelper = AccessHelper()

    init {
        ViewCompat.setAccessibilityDelegate(this, accessHelper)
    }

    override fun dispatchHoverEvent(event: MotionEvent): Boolean {
        return if (accessHelper.dispatchHoverEvent(event)) {
            true
        } else {
            super.dispatchHoverEvent(event)
        }
    }

    private inner class AccessHelper : ExploreByTouchHelper(this) {

        private val intervalBounds = Rect()

        @Suppress("DEPRECATION") // setBoundsInParent is required by [ExploreByTouchHelper]
        override fun onPopulateNodeForVirtualView(virtualViewId: Int, node: AccessibilityNodeInfoCompat) {
            node.className = RatingsHistogramView::class.simpleName
            node.contentDescription = "content desc $virtualViewId"
            updateBoundsForInterval(virtualViewId)
            node.setBoundsInParent(intervalBounds)
        }

        private fun updateBoundsForInterval(index: Int) {
            val histogram = this@RatingsHistogramView.ratingsHistogram ?: return
            val intervalWidth = width.toFloat() / histogram.intervals.size
            val left = index * intervalWidth
            intervalBounds.left = (left + 2).roundToInt()
            intervalBounds.top = (height - (histogram.intervals[index].weight * height)).roundToInt()
            intervalBounds.right = (left + intervalWidth - 2).roundToInt()
            intervalBounds.bottom = height
        }

        override fun getVirtualViewAt(x: Float, y: Float): Int {
            ratingsHistogram?.let {
                val intervalWidth = width.toFloat() / it.intervals.size
                return (x / intervalWidth).toInt()
            }
            return HOST_ID
        }

        override fun getVisibleVirtualViews(virtualViewIds: MutableList<Int>) {
            val numberOfIntervals = ratingsHistogram?.intervals?.size ?: 0
            for (i in 0 until numberOfIntervals) {
                virtualViewIds.add(i)
            }
        }

        override fun onPerformActionForVirtualView(virtualViewId: Int, action: Int, arguments: Bundle?): Boolean {
            return false
        }
    }

    fun show(ratingsHistogram: RatingsHistogram) {
        this.ratingsHistogram = ratingsHistogram
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        ratingsHistogram?.let {
            val intervalWidth = width.toFloat() / it.intervals.size
            it.intervals.forEachIndexed { index, interval ->
                val left = index * intervalWidth
                canvas.drawRect(
                        left + 2,
                        height - (interval.weight * height),
                        left + intervalWidth - 2,
                        height.toFloat(),
                        paint
                )
            }
        }
    }

    /**
     * Ratings go from 0.5 to 5 inc. in 0.5 increments. This means there are 10 intervals.
     */
    data class RatingsHistogram(val intervals: List<Interval>) {
        init {
            // let's enforce that everything is read for us
            check(intervals[0].rating == 0.5f)
            check(intervals[1].rating == 1.0f)
            check(intervals[2].rating == 1.5f)
            check(intervals[3].rating == 2.0f)
            check(intervals[4].rating == 2.5f)
            check(intervals[5].rating == 3.0f)
            check(intervals[6].rating == 3.5f)
            check(intervals[7].rating == 4.0f)
            check(intervals[8].rating == 4.5f)
            check(intervals[9].rating == 5.0f)
        }

        data class Interval(val rating: Float, val weight: Float, val count: Int)
    }

}
