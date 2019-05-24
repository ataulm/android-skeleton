package com.example

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import kotlinx.android.synthetic.main.card_view.view.*

private const val INDEX_OBSCURED = 0
private const val INDEX_BACK = 1
private const val INDEX_MIDDLE = 2
private const val INDEX_FRONT = 3
private const val VISIBLE_CARDS_COUNT = 3

class ShufflingCardsView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private val cardViews = mutableListOf<CardView>()

    override fun onFinishInflate() {
        super.onFinishInflate()
        clipChildren = false
        val inflater = LayoutInflater.from(context)
        for (i in INDEX_OBSCURED..INDEX_FRONT) {
            val view = inflater.inflate(R.layout.card_view, this, false)
            cardViews.add(view as CardView)
            addView(view)
        }
    }

    fun setCardsWithAnimation(cardDesigns: List<Int>) {
        val visibleRectangles = cardDesigns.take(VISIBLE_CARDS_COUNT)
        visibleRectangles.forEachIndexed { index, rectangle ->
            // want the ones at the top of the list to be at the front of the `cardViews` "stack",
            // where a higher index indicates an elements is in front of another
            cardViews[VISIBLE_CARDS_COUNT - index].setImage(rectangle)
        }
    }
}

internal class CardView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    fun setImage(@DrawableRes image: Int) = imageView.setImageResource(image)
}
