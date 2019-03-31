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

private const val ROTATION_CARD_BEING_SHUFFLED = 45f
private const val ROTATION_CARD_BACK = 7f
private const val ROTATION_CARD_MIDDLE = -7f
private const val ROTATION_CARD_FRONT = 0f
private const val Z_BACKEST = -3f
private const val Z_BACK = -2f
private const val Z_MIDDLE = -1f
private const val Z_FRONT = 0f
private const val Z_FRONTEST = 1f

private val TX_CARD_BEING_SHUFFLED = 45.dp.toFloat()
private val TY_CARD_BEING_SHUFFLED = -120.dp.toFloat()

internal class ChoiceOfRectanglesView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    private var isSpread = false
    private var front = Rectangle.RED

    private val blue: ColoredRectangleView
    private val yellow: ColoredRectangleView
    private val red: ColoredRectangleView

    init {
        clipChildren = false
        val inflater = LayoutInflater.from(context)

        blue = inflater.inflate(R.layout.view_item_choice_of_rectangles, this, false) as ColoredRectangleView
        blue.setColor(Color.parseColor("#005ab3"))
        addView(blue)

        yellow = inflater.inflate(R.layout.view_item_choice_of_rectangles, this, false) as ColoredRectangleView
        yellow.setColor(Color.parseColor("#ffd500"))
        addView(yellow)

        red = inflater.inflate(R.layout.view_item_choice_of_rectangles, this, false) as ColoredRectangleView
        red.setColor(ContextCompat.getColor(context, android.R.color.holo_red_light))
        addView(red)
    }

    fun show(rectangle: Rectangle) {
        if (isSpread) {
            unspreadAndThen { animateToFront(rectangle) }
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
        blue.animate()
                .rotation(ROTATION_CARD_BACK)
                .resetTranslation()
                .withEndAction { doOnEndIsh() }
                .startUnspread()

        yellow.animate()
                .rotation(ROTATION_CARD_MIDDLE)
                .resetTranslation()
                .startUnspread()

        red.animate()
                .rotation(ROTATION_CARD_FRONT)
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

    private fun ColoredRectangleView.setPivotAtCenter() {
        pivotX = width * 0.5f
        pivotY = height * 0.5f
    }

    private fun ColoredRectangleView.setPivotAtBottomRight() {
        pivotX = width.toFloat()
        pivotY = height.toFloat()
    }

    private fun ColoredRectangleView.shuffleFromBackToMiddle() {
        animate()
                .rotation(ROTATION_CARD_MIDDLE)
                .setDuration(300)
                .setInterpolator(FastOutSlowInInterpolator())
                .setStartDelay(0)
                .withEndAction {
                    z = Z_MIDDLE
                }
                .start()
    }

    private fun ColoredRectangleView.shuffleFromMiddleToBack() {
        animate()
                .rotation(ROTATION_CARD_BACK)
                .setDuration(300)
                .setInterpolator(FastOutSlowInInterpolator())
                .setStartDelay(0)
                .withEndAction {
                    z = Z_BACK
                }
                .start()
    }

    private fun ColoredRectangleView.shuffleFromMiddleToFront() {
        animate()
                .rotation(ROTATION_CARD_FRONT)
                .setDuration(300)
                .setInterpolator(FastOutSlowInInterpolator())
                .setStartDelay(0)
                .withEndAction {
                    z = Z_FRONT
                }
                .start()
    }

    private fun ColoredRectangleView.shuffleFromFrontToMiddle() {
        animate()
                .rotation(ROTATION_CARD_MIDDLE)
                .setDuration(300)
                .setInterpolator(FastOutSlowInInterpolator())
                .setStartDelay(0)
                .withEndAction {
                    z = Z_MIDDLE
                }
                .start()
    }

    private fun ColoredRectangleView.shuffleFromFrontToBack() {
        setPivotAtBottomRight()
        animate()
                .rotation(ROTATION_CARD_BEING_SHUFFLED)
                .translationXBy(TX_CARD_BEING_SHUFFLED)
                .translationYBy(TY_CARD_BEING_SHUFFLED)
                .setDuration(200)
                .setInterpolator(FastOutSlowInInterpolator())
                .withEndAction {
                    z = Z_BACKEST
                    animate()
                            .rotation(ROTATION_CARD_FRONT)
                            .resetTranslation()
                            .setDuration(300)
                            .setInterpolator(FastOutSlowInInterpolator())
                            .setStartDelay(0)
                            .withEndAction {
                                setPivotAtCenter()
                                animate()
                                        .rotation(ROTATION_CARD_BACK)
                                        .resetTranslation()
                                        .setDuration(300)
                                        .setInterpolator(FastOutSlowInInterpolator())
                                        .setStartDelay(0)
                                        .withEndAction {
                                            z = Z_BACK
                                        }
                                        .start()
                            }
                            .start()
                }
                .setStartDelay(0)
                .start()
    }

    private fun ColoredRectangleView.shuffleFromBackToFront() {
        setPivotAtBottomRight()
        animate()
                .rotation(ROTATION_CARD_BEING_SHUFFLED)
                .translationXBy(TX_CARD_BEING_SHUFFLED)
                .translationYBy(TY_CARD_BEING_SHUFFLED)
                .setDuration(200)
                .setInterpolator(FastOutSlowInInterpolator())
                .withEndAction {
                    z = Z_FRONTEST
                    animate()
                            .rotation(ROTATION_CARD_BACK)
                            .resetTranslation()
                            .setDuration(300)
                            .setInterpolator(FastOutSlowInInterpolator())
                            .setStartDelay(0)
                            .withEndAction {
                                setPivotAtCenter()
                                animate()
                                        .rotation(ROTATION_CARD_FRONT)
                                        .resetTranslation()
                                        .setDuration(300)
                                        .setInterpolator(FastOutSlowInInterpolator())
                                        .setStartDelay(0)
                                        .withEndAction {
                                            z = Z_FRONT
                                        }
                                        .start()
                            }
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

    private val coloredView: View

    init {
        coloredView = View(context).apply {
            layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        }
        addView(coloredView)
    }

    fun setColor(@ColorInt color: Int) = coloredView.setBackgroundColor(color)
}
        
