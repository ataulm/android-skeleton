package com.example

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
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
        spread()
    }

    private fun LayoutInflater.addColoredRectangleView(@ColorInt color: Int): ColoredRectangleView {
        val view = inflate(R.layout.view_item_choice_of_rectangles, this@ChoiceOfRectanglesView, false) as ColoredRectangleView
        view.setColor(color)
        addView(view)
        return view
    }

    private fun spread() {
        isSpread = true
        AnimatorSet().apply {
            duration = 600
            startDelay = 1000
            interpolator = OvershootInterpolator(3f)
            playTogether(
                    blue.rotateChildAnimator(-17f),
                    blue.translationAnimator(-12.dp.toFloat(), -60.dp.toFloat()),
                    yellow.rotateChildAnimator(0f),
                    yellow.translationAnimator(4.dp.toFloat(), 0f),
                    red.rotateChildAnimator(17f),
                    red.translationAnimator(0f, 60.dp.toFloat())
            )
        }.start()
    }

    fun show(rectangle: Rectangle) {
        if (isSpread) {
            AnimatorSet().apply {
                playSequentially(collateRectanglesAnimator(), rectangle.showAtFrontAnimator())
                duration = 600
                interpolator = FastOutSlowInInterpolator()
            }.start()
            isSpread = false
        } else {
            rectangle.showAtFrontAnimator().start()
        }
    }

    private fun collateRectanglesAnimator(): AnimatorSet {
        return AnimatorSet().apply {
            playTogether(
                    blue.rotateChildAnimator(BACK_CHILD_ROTATION),
                    blue.resetTranslationAnimator(),
                    yellow.rotateChildAnimator(MIDDLE_CHILD_ROTATION),
                    yellow.resetTranslationAnimator(),
                    red.rotateChildAnimator(FRONT_CHILD_ROTATION),
                    red.resetTranslationAnimator()
            )
        }
    }

    private fun Rectangle.showAtFrontAnimator(): Animator {
        when (this) {
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
        front = this
        return AnimatorSet() // TODO: fill it
    }

    private fun ViewPropertyAnimator.resetTranslation() = translationX(0f).translationY(0f)

    private fun View.resetTranslationAnimator(): ObjectAnimator = translationAnimator(0f, 0f)

    private fun View.translationAnimator(x: Float, y: Float): ObjectAnimator {
        return ObjectAnimator.ofPropertyValuesHolder(
                this,
                PropertyValuesHolder.ofFloat(View.TRANSLATION_X, x),
                PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, y)
        )
    }

    private fun ColoredRectangleView.setPivotAtBottomRight() {
        pivotX = width.toFloat()
        pivotY = height.toFloat()
    }

    private fun ColoredRectangleView.shuffleFromBackToMiddle() {
        rotateChildAnimator(MIDDLE_CHILD_ROTATION).apply {
            addListener(object : SimpleAnimationListener() {
                override fun onAnimationEnd(animation: Animator?) {
                    z = Z_MIDDLE
                }
            })
            start()
        }
    }

    private fun ColoredRectangleView.shuffleFromMiddleToBack() {
        rotateChildAnimator(BACK_CHILD_ROTATION).apply {
            addListener(object : SimpleAnimationListener() {
                override fun onAnimationEnd(animation: Animator?) {
                    z = Z_BACK
                }
            })
            start()
        }
    }

    private fun ColoredRectangleView.shuffleFromMiddleToFront() {
        rotateChildAnimator(FRONT_CHILD_ROTATION).apply {
            addListener(object : SimpleAnimationListener() {
                override fun onAnimationEnd(animation: Animator?) {
                    z = Z_FRONT
                }
            })
            start()
        }
    }

    private fun ColoredRectangleView.shuffleFromFrontToMiddle() {
        rotateChildAnimator(MIDDLE_CHILD_ROTATION).apply {
            addListener(object : SimpleAnimationListener() {
                override fun onAnimationEnd(animation: Animator?) {
                    z = Z_MIDDLE
                }
            })
            start()
        }
    }

    private fun ColoredRectangleView.shuffleFromFrontToBack() {
        rotateChildAnimator(BACK_CHILD_ROTATION).apply {
            addListener(object : SimpleAnimationListener() {
                override fun onAnimationEnd(animation: Animator?) {
                    z = Z_BACK
                }
            })
            start()
        }
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
        rotateChildAnimator(FRONT_CHILD_ROTATION).apply {
            addListener(object : SimpleAnimationListener() {
                override fun onAnimationEnd(animation: Animator?) {
                    z = Z_FRONT
                }
            })
            start()
        }
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

private const val BACK_CHILD_ROTATION = 7f
private const val MIDDLE_CHILD_ROTATION = -7f
private const val FRONT_CHILD_ROTATION = 0f

internal class ColoredRectangleView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private val coloredView: View = View(context).apply {
        layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        )
        addView(this)
    }

    fun setColor(@ColorInt color: Int) = coloredView.setBackgroundColor(color)

    fun rotateChildAnimator(rotation: Float): ObjectAnimator {
        return ObjectAnimator.ofFloat(coloredView, View.ROTATION, rotation)
    }
}

private abstract class SimpleAnimationListener : Animator.AnimatorListener {
    override fun onAnimationRepeat(animation: Animator?) {}
    override fun onAnimationEnd(animation: Animator?) {}
    override fun onAnimationCancel(animation: Animator?) {}
    override fun onAnimationStart(animation: Animator?) {}
}
