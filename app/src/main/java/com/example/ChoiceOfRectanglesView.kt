package com.example

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.merge_choice_of_rectangles.view.*

internal class ChoiceOfRectanglesView(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    init {
        View.inflate(context, R.layout.merge_choice_of_rectangles, this)
    }

    fun show(rectangle: Rectangle) {
        blue.visibility = GONE
        yellow.visibility = GONE
        red.visibility = GONE

        when (rectangle) {
            Rectangle.BLUE -> blue.visibility = View.VISIBLE
            Rectangle.YELLOW -> yellow.visibility = View.VISIBLE
            Rectangle.RED -> red.visibility = View.VISIBLE
        }
    }

    fun spread() {
        // TODO: spread ’em!
    }

    enum class Rectangle {
        BLUE,
        YELLOW,
        RED
    }
}
