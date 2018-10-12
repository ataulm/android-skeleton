package com.example

import android.content.Context
import android.graphics.Camera
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.Interpolator
import android.view.animation.Transformation
import android.widget.Checkable
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.merge_image_flipping_checkable.view.*

class ImageFlippingCheckableView constructor(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs), Checkable {

    private val calculateCenterX = { width * 0.5f }
    private val imageViewOutAnimation = SwivelAnimation.animationOut(calculateCenterX)
    private val checkViewInAnimation = SwivelAnimation.animationIn(calculateCenterX)
    private val checkViewOutAnimation = SwivelAnimation.animationOut(calculateCenterX)
    private val imageViewInAnimation = SwivelAnimation.animationIn(calculateCenterX)

    private var isChecked: Boolean = false

    init {
        View.inflate(context, R.layout.merge_image_flipping_checkable, this)

        imageViewOutAnimation.setOnAnimationEndListener {
            imageView.visibility = View.INVISIBLE
            checkView.startAnimation(checkViewInAnimation)
        }
        checkViewInAnimation.setOnAnimationStartListener { checkView.visibility = View.VISIBLE }

        checkViewOutAnimation.setOnAnimationEndListener {
            checkView.visibility = View.INVISIBLE
            imageView.startAnimation(imageViewInAnimation)
        }
        imageViewInAnimation.setOnAnimationStartListener { imageView.visibility = View.VISIBLE }
    }

    fun imageView() = imageView

    override fun isChecked() = isChecked

    override fun toggle() = setChecked(!isChecked)

    override fun setChecked(checked: Boolean) {
        if (isChecked) {
            checkView.startAnimation(checkViewOutAnimation)
        } else {
            imageView.startAnimation(imageViewOutAnimation)
        }
        isChecked = checked
    }
}

private class SwivelAnimation(
        private val degreesStart: Float,
        private val degreesEnd: Float,
        private val calculateCenterX: () -> Float,
        swivelInterpolator: Interpolator
) : Animation() {

    private val camera = Camera()

    init {
        duration = 500
        interpolator = swivelInterpolator
    }

    override fun applyTransformation(interpolatedTime: Float, transformation: Transformation) {
        val degrees = degreesStart + (degreesEnd - degreesStart) * interpolatedTime
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

        fun animationOut(calculateCenterX: () -> Float): Animation {
            return SwivelAnimation(0f, 90f, calculateCenterX, SwivelInterpolator.FAST_OUT)
        }

        fun animationIn(calculateCenterX: () -> Float): Animation {
            return SwivelAnimation(-90f, 0f, calculateCenterX, SwivelInterpolator.SLOW_IN)
        }
    }
}

private interface SimpleAnimationListener : Animation.AnimationListener {

    override fun onAnimationRepeat(animation: Animation?) {}
    override fun onAnimationEnd(animation: Animation?) {}
    override fun onAnimationStart(animation: Animation?) {}
}

private fun Animation.setOnAnimationEndListener(onAnimationEnd: () -> Unit) {
    setAnimationListener(object : SimpleAnimationListener {
        override fun onAnimationEnd(animation: Animation?) = onAnimationEnd()
    })
}

private fun Animation.setOnAnimationStartListener(onAnimationEnd: () -> Unit) {
    setAnimationListener(object : SimpleAnimationListener {
        override fun onAnimationStart(animation: Animation?) = onAnimationEnd()
    })
}

private class SwivelInterpolator private constructor(private val values: FloatArray) : Interpolator {

    private val stepSize: Float = 1.0f / (this.values.size - 1).toFloat()

    override fun getInterpolation(input: Float): Float {
        return when {
            input >= 1.0f -> 1.0f
            input <= 0.0f -> 0.0f
            else -> {
                val position = Math.min((input * (this.values.size - 1).toFloat()).toInt(), this.values.size - 2)
                val quantized = position.toFloat() * this.stepSize
                val diff = input - quantized
                val weight = diff / this.stepSize
                this.values[position] + weight * (this.values[position + 1] - this.values[position])
            }
        }
    }

    companion object {

        /**
         * Copied from [FastOutSlowInInterpolator]
         */
        private val FAST_OUT_SLOW_IN_VALUES = floatArrayOf(
                0.0f, 1.0E-4f, 2.0E-4f, 5.0E-4f, 9.0E-4f, 0.0014f, 0.002f, 0.0027f, 0.0036f, 0.0046f,
                0.0058f, 0.0071f, 0.0085f, 0.0101f, 0.0118f, 0.0137f, 0.0158f, 0.018f, 0.0205f, 0.0231f,
                0.0259f, 0.0289f, 0.0321f, 0.0355f, 0.0391f, 0.043f, 0.0471f, 0.0514f, 0.056f, 0.0608f,
                0.066f, 0.0714f, 0.0771f, 0.083f, 0.0893f, 0.0959f, 0.1029f, 0.1101f, 0.1177f, 0.1257f,
                0.1339f, 0.1426f, 0.1516f, 0.161f, 0.1707f, 0.1808f, 0.1913f, 0.2021f, 0.2133f, 0.2248f,
                0.2366f, 0.2487f, 0.2611f, 0.2738f, 0.2867f, 0.2998f, 0.3131f, 0.3265f, 0.34f, 0.3536f,
                0.3673f, 0.381f, 0.3946f, 0.4082f, 0.4217f, 0.4352f, 0.4485f, 0.4616f, 0.4746f, 0.4874f,
                0.5f, 0.5124f, 0.5246f, 0.5365f, 0.5482f, 0.5597f, 0.571f, 0.582f, 0.5928f, 0.6033f,
                0.6136f, 0.6237f, 0.6335f, 0.6431f, 0.6525f, 0.6616f, 0.6706f, 0.6793f, 0.6878f,
                0.6961f, 0.7043f, 0.7122f, 0.7199f, 0.7275f, 0.7349f, 0.7421f, 0.7491f, 0.7559f,
                0.7626f, 0.7692f, 0.7756f, 0.7818f, 0.7879f, 0.7938f, 0.7996f, 0.8053f, 0.8108f,
                0.8162f, 0.8215f, 0.8266f, 0.8317f, 0.8366f, 0.8414f, 0.8461f, 0.8507f, 0.8551f,
                0.8595f, 0.8638f, 0.8679f, 0.872f, 0.876f, 0.8798f, 0.8836f, 0.8873f, 0.8909f, 0.8945f,
                0.8979f, 0.9013f, 0.9046f, 0.9078f, 0.9109f, 0.9139f, 0.9169f, 0.9198f, 0.9227f,
                0.9254f, 0.9281f, 0.9307f, 0.9333f, 0.9358f, 0.9382f, 0.9406f, 0.9429f, 0.9452f,
                0.9474f, 0.9495f, 0.9516f, 0.9536f, 0.9556f, 0.9575f, 0.9594f, 0.9612f, 0.9629f,
                0.9646f, 0.9663f, 0.9679f, 0.9695f, 0.971f, 0.9725f, 0.9739f, 0.9753f, 0.9766f, 0.9779f,
                0.9791f, 0.9803f, 0.9815f, 0.9826f, 0.9837f, 0.9848f, 0.9858f, 0.9867f, 0.9877f,
                0.9885f, 0.9894f, 0.9902f, 0.991f, 0.9917f, 0.9924f, 0.9931f, 0.9937f, 0.9944f, 0.9949f,
                0.9955f, 0.996f, 0.9964f, 0.9969f, 0.9973f, 0.9977f, 0.998f, 0.9984f, 0.9986f, 0.9989f,
                0.9991f, 0.9993f, 0.9995f, 0.9997f, 0.9998f, 0.9999f, 0.9999f, 1.0f, 1.0f
        )

        val FAST_OUT = SwivelInterpolator(fastOutValues())
        val SLOW_IN = SwivelInterpolator(slowInValues())

        private fun fastOutValues(): FloatArray {
            val fastOut = FAST_OUT_SLOW_IN_VALUES.copyOfRange(0, 100)
            val max = fastOut.last()
            return fastOut.map { it / max }.toFloatArray()
        }

        private fun slowInValues():FloatArray {
            val slowIn = FAST_OUT_SLOW_IN_VALUES.copyOfRange(101, 200)
            val min = slowIn.first()
            val max = slowIn.last()
            return slowIn.map { (it - min) / (max - min) }.toFloatArray()
        }
    }
}
