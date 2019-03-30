package com.example

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.OvershootInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.merge_choice_of_rectangles.view.*

internal class ChoiceOfRectanglesView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    init {
        View.inflate(context, R.layout.merge_choice_of_rectangles, this)
    }

    fun show(rectangle: Rectangle) {
        blue.visibility = GONE
        yellow.visibility = GONE
        red.visibility = GONE

        when (rectangle) {
            Rectangle.BLUE -> blue.visibility = View.VISIBLE
            Rectangle.YELLOW -> yellow.visibility = View.VISIBLE
            Rectangle.RED -> red.visibility = View.VISIBLE
        }
    }

    fun spread() {
        blue.animate()
                .rotation(-17f)
                .translationXBy(-12.dp.toFloat())
                .translationYBy(-60.dp.toFloat())
                .startSpreadAnimation()

        yellow.animate()
                .translationXBy(4.dp.toFloat())
                .startSpreadAnimation()

        red.animate()
                .rotation(17f)
                .translationYBy(60.dp.toFloat())
                .startSpreadAnimation()
    }

    private fun ViewPropertyAnimator.startSpreadAnimation() {
        setDuration(600)
                .setInterpolator(OvershootInterpolator(3f))
                .setStartDelay(1000)
                .start()
    }

    enum class Rectangle {
        BLUE,
        YELLOW,
        RED
    }
}

val Int.dp: Int
    get() {
        val density = Resources.getSystem().displayMetrics.density
        return Math.round(this * density)
    }
