package com.example.swivel

import android.graphics.Camera
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.view.animation.Animation
import android.view.animation.Transformation

internal class SwivelAnimation(
        private val degreesStart: Float,
        private val degreesEnd: Float,
        private val calculateCenterX: () -> Float,
        private val isShowingBack: (Boolean) -> Unit
) : Animation() {

    private val camera = Camera()

    override fun applyTransformation(interpolatedTime: Float, transformation: Transformation) {
        val degrees = degreesStart + (degreesEnd - degreesStart) * interpolatedTime
        isShowingBack(degrees >= 90)

        val matrix = transformation.matrix
        camera.save()
        camera.rotateY(degrees)
        camera.getMatrix(matrix)
        camera.restore()

        val centerX = calculateCenterX()
        matrix.preTranslate(-centerX, 0f)
        matrix.postTranslate(centerX, 0f)
    }

    companion object {

        private const val DURATION = 5000L

        fun animationOut(calculateCenterX: () -> Float, isShowingBack: (Boolean) -> Unit): Animation {
            return SwivelAnimation(0f, 180f, calculateCenterX, isShowingBack).apply {
                duration = DURATION
                interpolator = FastOutSlowInInterpolator()
            }
        }
    }
}
