package com.example

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.OvershootInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import kotlinx.android.synthetic.main.merge_choice_of_rectangles.view.*

internal class ChoiceOfRectanglesView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    private var isSpread = false

    init {
        View.inflate(context, R.layout.merge_choice_of_rectangles, this)
    }

    fun show(rectangle: Rectangle) {
        if (isSpread) {
            unspreadAndThen {
                red.pivotX = red.width.toFloat()
                red.pivotY = red.height.toFloat()
                red.animate()
                        .rotation(45f)
                        .translationXBy(45.dp.toFloat())
                        .translationYBy(-120.dp.toFloat())
                        .setDuration(200)
                        .setInterpolator(FastOutSlowInInterpolator())
                        .withEndAction {
                            red.z = -100f
                            red.animate()
                                    .rotation(0f)
                                    .translationX(0f)
                                    .translationY(0f)
                                    .setDuration(300)
                                    .setInterpolator(FastOutSlowInInterpolator())
                                    .setStartDelay(0)
                                    .start()
                        }
                        .setStartDelay(0)
                        .start()
            }
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

    fun spread() {
        isSpread = true
        blue.animate()
                .rotation(-17f)
                .translationXBy(-12.dp.toFloat())
                .translationYBy(-60.dp.toFloat())
                .startSpread()

        yellow.animate()
                .translationXBy(4.dp.toFloat())
                .startSpread()

        red.animate()
                .rotation(17f)
                .translationYBy(60.dp.toFloat())
                .startSpread()
    }

    private fun ViewPropertyAnimator.startSpread() {
        setDuration(600)
                .setInterpolator(OvershootInterpolator(3f))
                .setStartDelay(1000)
                .start()
    }

    private fun unspreadAndThen(doOnEndIsh: () -> Unit) {
        blue.animate()
                .rotation(7f)
                .translationX(0f)
                .translationY(0f)
                .withEndAction { doOnEndIsh() }
                .startUnspread()

        yellow.animate()
                .rotation(-7f)
                .translationXBy(0f)
                .startUnspread()

        red.animate()
                .rotation(0f)
                .translationY(0f)
                .startUnspread()
    }

    private fun ViewPropertyAnimator.startUnspread() {
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
