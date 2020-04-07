package com.androidasync

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt

class FramedPictureView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    @ColorInt
    private val mountboardColor: Int
    private val imageDrawable: Drawable?
    private val label: String?

    init {
        super.setOrientation(VERTICAL)
        context.obtainStyledAttributes(attrs, R.styleable.FramedPictureView).apply {
            mountboardColor = getColor(R.styleable.FramedPictureView_mountboardColor, context.defaultMountboardColor())
            imageDrawable = getDrawable(R.styleable.FramedPictureView_imageDrawable)
            label = getString(R.styleable.FramedPictureView_label)

            recycle()
        }
    }

    /**
     * We'll use the theme's colorPrimary as the default mountboard color. This is used when
     * [R.attr.mountboardColor] isn't specified in the XML attrs, nor in the style applied on the
     * view, nor in the default styles for this view, nor in the theme.
     *
     * TODO: go through these one-by-one.
     */
    @ColorInt
    private fun Context.defaultMountboardColor() = TypedValue().let {
        theme.resolveAttribute(R.attr.colorPrimary, it, true)
        it.data
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        View.inflate(context, R.layout.merge_framed_picture, this)
    }
}
