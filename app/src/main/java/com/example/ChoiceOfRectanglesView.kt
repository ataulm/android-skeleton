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
            val animatorSet = AnimatorSet()
            animatorSet.playSequentially(
                    collateRectanglesAnimator().apply { duration = 200 },
                    rectangle.showAtFrontAnimator()
            )
            animatorSet.start()
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
        val animatorSet = AnimatorSet().apply {
            duration = 300
            interpolator = FastOutSlowInInterpolator()
        }
        when (front) {
            Rectangle.BLUE -> {
                if (this == Rectangle.YELLOW) {
                    animatorSet.apply {
                        doOnEnd {
                            red.z = Z_BACK
                            blue.z = Z_MIDDLE
                        }
                    }.playTogether(
                            yellow.shuffleFromBackToFront(),
                            red.rotateChildAnimator(BACK_CHILD_ROTATION),
                            blue.rotateChildAnimator(MIDDLE_CHILD_ROTATION)
                    )
                } else if (this == Rectangle.RED) {
                    animatorSet.apply {
                        doOnEnd {
                            yellow.z = Z_MIDDLE
                            red.z = Z_FRONT
                        }
                    }.playTogether(
                            yellow.rotateChildAnimator(MIDDLE_CHILD_ROTATION),
                            red.rotateChildAnimator(FRONT_CHILD_ROTATION),
                            blue.shuffleFromFrontToBack()
                    )
                }
            }
            Rectangle.YELLOW -> {
                if (this == Rectangle.RED) {
                    animatorSet.apply {
                        doOnEnd {
                            blue.z = Z_BACK
                            yellow.z = Z_MIDDLE
                        }
                    }.playTogether(
                            red.shuffleFromBackToFront(),
                            blue.rotateChildAnimator(BACK_CHILD_ROTATION),
                            yellow.rotateChildAnimator(MIDDLE_CHILD_ROTATION)
                    )
                } else if (this == Rectangle.BLUE) {
                    animatorSet.apply {
                        doOnEnd {
                            red.z = Z_MIDDLE
                            blue.z = Z_FRONT
                        }
                    }.playTogether(
                            red.rotateChildAnimator(MIDDLE_CHILD_ROTATION),
                            blue.rotateChildAnimator(FRONT_CHILD_ROTATION),
                            yellow.shuffleFromFrontToBack()
                    )
                }
            }
            Rectangle.RED -> {
                if (this == Rectangle.BLUE) {
                    animatorSet.apply {
                        doOnEnd {
                            yellow.z = Z_BACK
                            red.z = Z_MIDDLE
                        }
                    }.playTogether(
                            blue.shuffleFromBackToFront(),
                            yellow.rotateChildAnimator(BACK_CHILD_ROTATION),
                            red.rotateChildAnimator(MIDDLE_CHILD_ROTATION)
                    )
                } else if (this == Rectangle.YELLOW) {
                    animatorSet.apply {
                        doOnEnd {
                            blue.z = Z_MIDDLE
                            yellow.z = Z_FRONT
                        }
                    }.playTogether(
                            blue.rotateChildAnimator(MIDDLE_CHILD_ROTATION),
                            yellow.rotateChildAnimator(FRONT_CHILD_ROTATION),
                            red.shuffleFromFrontToBack()
                    )
                }
            }
        }
        front = this
        return animatorSet
    }

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

    private fun ColoredRectangleView.shuffleFromFrontToBack(): Animator {
        setPivotAtBottomRight()

        return AnimatorSet().apply {

            val rectangleOut = ObjectAnimator.ofPropertyValuesHolder(
                    this@shuffleFromFrontToBack,
                    PropertyValuesHolder.ofFloat(View.ROTATION, ROTATION_RECT_SHUFFLE_PEAK),
                    PropertyValuesHolder.ofFloat(View.TRANSLATION_X, TX_RECT_BEING_SHUFFLED),
                    PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, TY_RECT_BEING_SHUFFLED)
            ).doOnEnd { z = Z_BACKEST }

            val rectangleIn = AnimatorSet().apply {
                val childRotate = rotateChildAnimator(BACK_CHILD_ROTATION)
                val rectangleToBack = ObjectAnimator.ofPropertyValuesHolder(
                        this@shuffleFromFrontToBack,
                        PropertyValuesHolder.ofFloat(View.ROTATION, ROTATION_RECT_SHUFFLE_INITIAL),
                        PropertyValuesHolder.ofFloat(View.TRANSLATION_X, 0f),
                        PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 0f)
                )
                playTogether(rectangleToBack, childRotate)
            }.doOnEnd { z = Z_BACK }

            playSequentially(rectangleOut, rectangleIn)
        }
    }

    private fun ColoredRectangleView.shuffleFromBackToFront(): Animator {
        setPivotAtBottomRight()

        return AnimatorSet().apply {

            val rectangleOut = ObjectAnimator.ofPropertyValuesHolder(
                    this@shuffleFromBackToFront,
                    PropertyValuesHolder.ofFloat(View.ROTATION, ROTATION_RECT_SHUFFLE_PEAK),
                    PropertyValuesHolder.ofFloat(View.TRANSLATION_X, TX_RECT_BEING_SHUFFLED),
                    PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, TY_RECT_BEING_SHUFFLED)
            ).doOnEnd { z = Z_FRONTEST }

            val rectangleIn = AnimatorSet().apply {
                val childRotate = rotateChildAnimator(FRONT_CHILD_ROTATION)
                val rectangleToFront = ObjectAnimator.ofPropertyValuesHolder(
                        this@shuffleFromBackToFront,
                        PropertyValuesHolder.ofFloat(View.ROTATION, ROTATION_RECT_SHUFFLE_INITIAL),
                        PropertyValuesHolder.ofFloat(View.TRANSLATION_X, 0f),
                        PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 0f)
                )
                playTogether(rectangleToFront, childRotate)
            }.doOnEnd { z = Z_FRONT }

            playSequentially(rectangleOut, rectangleIn)
        }
    }

    enum class Rectangle {
        BLUE,
        YELLOW,
        RED
    }
}

private fun Animator.doOnEnd(action: () -> Unit): Animator {
    addListener(object : SimpleAnimationListener() {
        override fun onAnimationEnd(animation: Animator?) {
            action()
        }
    })
    return this
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

    fun rotateChildAnimator(rotation: Float): Animator {
        return ObjectAnimator.ofFloat(coloredView, View.ROTATION, rotation)
    }
}

private abstract class SimpleAnimationListener : Animator.AnimatorListener {
    override fun onAnimationRepeat(animation: Animator?) {}
    override fun onAnimationEnd(animation: Animator?) {}
    override fun onAnimationCancel(animation: Animator?) {}
    override fun onAnimationStart(animation: Animator?) {}
}
