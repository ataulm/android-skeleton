package com.example

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.OvershootInterpolator
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

private const val ROTATION_RECT_SHUFFLE_INITIAL = 0f
private const val ROTATION_RECT_SHUFFLE_PEAK = 45f
private val TX_RECT_BEING_SHUFFLED = 45.dp.toFloat()
private val TY_RECT_BEING_SHUFFLED = -120.dp.toFloat()

private const val Z_BACKEST = -3f
private const val Z_BACK = -2f
private const val Z_MIDDLE = -1f
private const val Z_FRONT = 0f
private const val Z_FRONTEST = 1f

internal class ChoiceOfRectanglesView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    private var isSpread = false
    private var front = Rectangle.RED

    private val blue: ColoredRectangleView
    private val yellow: ColoredRectangleView
    private val red: ColoredRectangleView

    init {
        clipChildren = false
        val inflater = LayoutInflater.from(context)
        blue = inflater.addColoredRectangleView(Color.parseColor("#005ab3"))
        yellow = inflater.addColoredRectangleView(Color.parseColor("#ffd500"))
        red = inflater.addColoredRectangleView(ContextCompat.getColor(inflater.context, android.R.color.holo_red_light))
    }

    private fun LayoutInflater.addColoredRectangleView(@ColorInt color: Int): ColoredRectangleView {
        val view = inflate(R.layout.view_item_choice_of_rectangles, this@ChoiceOfRectanglesView, false) as ColoredRectangleView
        view.setColor(color)
        addView(view)
        return view
    }

    fun show(rectangle: Rectangle) {
        if (isSpread) {
            unspreadAndThen {
                animateToFront(rectangle)
            }
            isSpread = false
        } else {
            animateToFront(rectangle)
        }
    }

    private fun animateToFront(rectangle: Rectangle) {
        when (rectangle) {
            Rectangle.BLUE -> {
                when (front) {
                    Rectangle.BLUE -> {
                    }
                    Rectangle.YELLOW -> {
                        red.shuffleFromBackToMiddle()
                        blue.shuffleFromMiddleToFront()
                        yellow.shuffleFromFrontToBack()
                    }
                    Rectangle.RED -> {
                        blue.shuffleFromBackToFront()
                        yellow.shuffleFromMiddleToBack()
                        red.shuffleFromFrontToMiddle()
                    }
                }
            }
            Rectangle.YELLOW -> {
                when (front) {
                    Rectangle.BLUE -> {
                        yellow.shuffleFromBackToFront()
                        red.shuffleFromMiddleToBack()
                        blue.shuffleFromFrontToMiddle()
                    }
                    Rectangle.YELLOW -> {
                    }
                    Rectangle.RED -> {
                        blue.shuffleFromBackToMiddle()
                        yellow.shuffleFromMiddleToFront()
                        red.shuffleFromFrontToBack()
                    }
                }
            }
            Rectangle.RED -> {
                when (front) {
                    Rectangle.BLUE -> {
                        yellow.shuffleFromBackToMiddle()
                        red.shuffleFromMiddleToFront()
                        blue.shuffleFromFrontToBack()
                    }
                    Rectangle.YELLOW -> {
                        red.shuffleFromBackToFront()
                        blue.shuffleFromMiddleToBack()
                        yellow.shuffleFromFrontToMiddle()
                    }
                    Rectangle.RED -> {
                    }
                }

            }
        }
        front = rectangle
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

    private fun unspreadAndThen(doOnEndIsh: () -> Unit) {
        blue.animateRotationToBack { z = Z_BACK }
        yellow.animateRotationToMiddle { z = Z_MIDDLE }
        red.animateRotationToFront { z = Z_FRONT }

        blue.animate()
                .rotation(0f)
                .resetTranslation()
                .withEndAction { doOnEndIsh() }
                .startUnspread()

        yellow.animate()
                .rotation(0f)
                .resetTranslation()
                .startUnspread()

        red.animate()
                .rotation(0f)
                .resetTranslation()
                .startUnspread()
    }

    private fun ViewPropertyAnimator.resetTranslation() = translationX(0f).translationY(0f)

    private fun ViewPropertyAnimator.startSpread() {
        setDuration(600)
                .setInterpolator(OvershootInterpolator(3f))
                .setStartDelay(1000)
                .start()
    }

    private fun ViewPropertyAnimator.startUnspread() {
        setDuration(600)
                .setInterpolator(FastOutSlowInInterpolator())
                .setStartDelay(0)
                .start()
    }

    private fun ColoredRectangleView.setPivotAtBottomRight() {
        pivotX = width.toFloat()
        pivotY = height.toFloat()
    }

    private fun ColoredRectangleView.shuffleFromBackToMiddle() {
        animateRotationToMiddle { z = Z_MIDDLE }
    }

    private fun ColoredRectangleView.shuffleFromMiddleToBack() {
        animateRotationToBack { z = Z_BACK }
    }

    private fun ColoredRectangleView.shuffleFromMiddleToFront() {
        animateRotationToFront { z = Z_FRONT }
    }

    private fun ColoredRectangleView.shuffleFromFrontToMiddle() {
        animateRotationToMiddle { z = Z_MIDDLE }
    }

    private fun ColoredRectangleView.shuffleFromFrontToBack() {
        animateRotationToBack {}
        setPivotAtBottomRight()
        animate()
                .rotation(ROTATION_RECT_SHUFFLE_PEAK)
                .translationXBy(TX_RECT_BEING_SHUFFLED)
                .translationYBy(TY_RECT_BEING_SHUFFLED)
                .setDuration(200)
                .setInterpolator(FastOutSlowInInterpolator())
                .withEndAction {
                    z = Z_BACKEST
                    animate()
                            .rotation(ROTATION_RECT_SHUFFLE_INITIAL)
                            .resetTranslation()
                            .setDuration(300)
                            .setInterpolator(FastOutSlowInInterpolator())
                            .setStartDelay(0)
                            .withEndAction { z = Z_BACK }
                            .start()
                }
                .setStartDelay(0)
                .start()
    }

    private fun ColoredRectangleView.shuffleFromBackToFront() {
        animateRotationToFront {}
        setPivotAtBottomRight()
        animate()
                .rotation(ROTATION_RECT_SHUFFLE_PEAK)
                .translationXBy(TX_RECT_BEING_SHUFFLED)
                .translationYBy(TY_RECT_BEING_SHUFFLED)
                .setDuration(200)
                .setInterpolator(FastOutSlowInInterpolator())
                .withEndAction {
                    z = Z_FRONTEST
                    animate()
                            .rotation(ROTATION_RECT_SHUFFLE_INITIAL)
                            .resetTranslation()
                            .setDuration(300)
                            .setInterpolator(FastOutSlowInInterpolator())
                            .setStartDelay(0)
                            .withEndAction { z = Z_FRONT }
                            .start()
                }
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

internal class ColoredRectangleView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    internal val coloredView: View = View(context).apply {
        layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        )
        addView(this)
    }

    fun setColor(@ColorInt color: Int) = coloredView.setBackgroundColor(color)

    fun animateRotationToBack(doOnEndIsh: () -> Unit) = animateChildRotation(7f, doOnEndIsh)

    fun animateRotationToMiddle(doOnEndIsh: () -> Unit) = animateChildRotation(-7f, doOnEndIsh)

    fun animateRotationToFront(doOnEndIsh: () -> Unit) = animateChildRotation(0f, doOnEndIsh)

    private fun animateChildRotation(rotation: Float, doOnEndIsh: () -> Unit) {
        coloredView.animate()
                .rotation(rotation)
                .setDuration(300)
                .setInterpolator(FastOutSlowInInterpolator())
                .setStartDelay(0)
                .withEndAction { doOnEndIsh() }
                .start()
    }
}
        
