package com.example

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.OvershootInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import kotlinx.android.synthetic.main.merge_choice_of_rectangles.view.*

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

/**
 * there's a coupling between this class and the ordering the XML
 * - BLUE
 * - YELLOW
 * - RED
 * where RED is on top of the others.
 */
internal class ChoiceOfRectanglesView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    private var isSpread = false
    private var front = Rectangle.RED

    init {
        View.inflate(context, R.layout.merge_choice_of_rectangles, this)
    }

    private fun View.setPivotAtCenter() {
        pivotX = width * 0.5f
        pivotY = height * 0.5f
    }

    private fun View.setPivotAtBottomRight() {
        pivotX = width.toFloat()
        pivotY = height.toFloat()
    }

    private fun View.shuffleFromBackToMiddle() {
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

    private fun View.shuffleFromMiddleToBack() {
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

    private fun View.shuffleFromMiddleToFront() {
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

    private fun View.shuffleFromFrontToMiddle() {
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

    private fun View.shuffleFromFrontToBack() {
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

    private fun View.shuffleFromBackToFront() {
        matrix.isAffine
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

    private fun ViewPropertyAnimator.startSpread() {
        setDuration(600)
                .setInterpolator(OvershootInterpolator(3f))
                .setStartDelay(1000)
                .start()
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
