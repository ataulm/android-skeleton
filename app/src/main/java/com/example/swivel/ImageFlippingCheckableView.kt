package com.example.swivel

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Checkable
import android.widget.ImageView
import android.widget.ViewFlipper
import com.example.R
import kotlinx.android.synthetic.main.merge_image_flipping_checkable.view.*

private const val IMAGE = 0
private const val CHECKMARK = 1

class ImageFlippingCheckableView constructor(context: Context, attrs: AttributeSet)
    : ViewFlipper(context, attrs), Checkable {

    private val calculateCenterX = { width * 0.5f }
    private val isShowingBack: (Boolean) -> Unit = { isShowingBack ->
        if (isShowingBack && displayedChild != CHECKMARK) {
            displayedChild = CHECKMARK
        } else if (!isShowingBack && displayedChild != IMAGE) {
            displayedChild = IMAGE
        }
    }

    private val swivelAnimation = SwivelAnimation.animationOut(calculateCenterX, isShowingBack)
    private var isChecked: Boolean = false

    init {
        View.inflate(context, R.layout.merge_image_flipping_checkable, this)
    }

    /**
     * Will not cancel running animations.
     */
    fun setChecked(targetState: Boolean, shouldAnimate: Boolean = true) {
        if (shouldAnimate) {
            setChecked(targetState)
        } else {
            displayedChild = if (targetState) CHECKMARK else IMAGE
            isChecked = targetState
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
        startAnimation(swivelAnimation)
        isChecked = targetState
    }

    override fun isChecked() = isChecked
    override fun toggle() = setChecked(!isChecked)

    fun imageView(): ImageView = imageView
}
