package com.example

import android.content.Context
import android.graphics.Camera
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.Checkable
import android.widget.ImageView
import android.widget.ViewFlipper
import com.example.SwivelAnimation.Companion.createAnimation
import kotlinx.android.synthetic.main.merge_swivel_check.view.*

private val ROTATION_TO_CHECKED = 0f..180f
private val ROTATION_TO_NOT_CHECKED = 180f..0f

class SwivelCheckView constructor(context: Context, attrs: AttributeSet)
    : ViewFlipper(context, attrs), Checkable {

    private val calculateCenterX = { width * 0.5f }
    private val updateDisplayedSide: (DisplaySide) -> Unit = { setDisplaySide(it) }
    private val toCheckedAnimation = createAnimation(ROTATION_TO_CHECKED, calculateCenterX, updateDisplayedSide)
    private val toNotCheckedAnimation = createAnimation(ROTATION_TO_NOT_CHECKED, calculateCenterX, updateDisplayedSide)

    private var isChecked: Boolean = false

    init {
        View.inflate(context, R.layout.merge_swivel_check, this)
    }

    /**
     * Will not cancel running animations.
     */
    fun setChecked(targetState: Boolean, shouldAnimate: Boolean = true) {
        if (shouldAnimate) {
            setChecked(targetState)
        } else {
            setDisplaySide(if (targetState) DisplaySide.BACK else DisplaySide.FRONT)
            isChecked = targetState
        }
    }

    private fun setDisplaySide(displaySide: DisplaySide) {
        if (displayedChild != displaySide.viewFlipperChildIndex) {
            displayedChild = displaySide.viewFlipperChildIndex
        }
    }

    /**
     * Will animate to the targetState.
     *
     * If already mid-way through an animation, it will stop the animation at its position
     * and reverse it from that point.
     */
    override fun setChecked(targetState: Boolean) {
        if (targetState == isChecked) {
            return
        }
        if (targetState) {
            isChecked = true
            startAnimation(toCheckedAnimation)
        } else {
            isChecked = false
            startAnimation(toNotCheckedAnimation)
        }
    }

    override fun isChecked() = isChecked
    override fun toggle() = setChecked(!isChecked)

    fun imageView(): ImageView = imageView
}

private class SwivelAnimation(
        private val degreesStart: Float,
        private val degreesEnd: Float,
        private val calculateCenterX: () -> Float,
        private val updateDisplayedSide: (DisplaySide) -> Unit
) : Animation() {

    private val camera = Camera()

    override fun applyTransformation(interpolatedTime: Float, transformation: Transformation) {
        val degrees = degreesStart + (degreesEnd - degreesStart) * interpolatedTime

        val matrix = transformation.matrix
        val centerX = calculateCenterX()
        camera.save()
        matrix.setScale(-1f, 0f)
        camera.rotateY(degrees)
        camera.getMatrix(matrix)
        camera.restore()

        matrix.preTranslate(-centerX, 0f)
        matrix.postTranslate(centerX, 0f)

        val displaySide = if (degrees >= 90) DisplaySide.BACK else DisplaySide.FRONT
        updateDisplayedSide(displaySide)
        if (displaySide == DisplaySide.BACK) {
            matrix.preScale(-1f, 1f, centerX, 0f)
        }
    }

    companion object {

        private const val DURATION_MS = 320L

        fun createAnimation(rotation: ClosedFloatingPointRange<Float>, calculateCenterX: () -> Float, updateDisplayedSide: (DisplaySide) -> Unit): Animation {
            return SwivelAnimation(rotation.start, rotation.endInclusive, calculateCenterX, updateDisplayedSide).apply {
                duration = DURATION_MS
                interpolator = FastOutSlowInInterpolator()
            }
        }
    }
}

private enum class DisplaySide(val viewFlipperChildIndex: Int) {

    FRONT(0),
    BACK(1)
}
