package com.example

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.OvershootInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import kotlinx.android.synthetic.main.merge_choice_of_rectangles.view.*

internal class ChoiceOfRectanglesView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    init {
        View.inflate(context, R.layout.merge_choice_of_rectangles, this)
    }

    fun show(rectangle: Rectangle) {
        if (isSpread) {
            unspread()
            isSpread = false
        }

//        blue.visibility = GONE
//        yellow.visibility = GONE
//        red.visibility = GONE
//
//        when (rectangle) {
//            Rectangle.BLUE -> blue.visibility = View.VISIBLE
//            Rectangle.YELLOW -> yellow.visibility = View.VISIBLE
//            Rectangle.RED -> red.visibility = View.VISIBLE
//        }
    }

    var isSpread = false

    fun spread() {
        isSpread = true
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

    fun unspread() {
        blue.animate()
                .rotation(7f)
                .translationX(0f)
                .translationY(0f)
                .startUnspreadAnimation()

        yellow.animate()
                .rotation(-7f)
                .translationXBy(0f)
                .startUnspreadAnimation()

        red.animate()
                .rotation(0f)
                .translationY(0f)
                .startUnspreadAnimation()
    }

    private fun ViewPropertyAnimator.startSpreadAnimation() {
        setDuration(600)
                .setInterpolator(OvershootInterpolator(3f))
                .setStartDelay(1000)
                .start()
    }

    private fun ViewPropertyAnimator.startUnspreadAnimation() {
        setDuration(600)
                .setInterpolator(FastOutSlowInInterpolator())
                .setStartDelay(0)
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
