package com.androidasync

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.content.res.AppCompatResources
import kotlinx.android.synthetic.main.merge_burger.view.*

class BurgerView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private val includeLettuce: Boolean
    private val sauceColor: ColorStateList

    init {
        context.obtainStyledAttributes(attrs, R.styleable.BurgerView).apply {
            includeLettuce = getBoolean(R.styleable.BurgerView_includeLettuce, false)

            val sauceColorRes = getResourceId(R.styleable.BurgerView_sauce, -1)
            sauceColor = if (sauceColorRes != -1) {
                AppCompatResources.getColorStateList(context, sauceColorRes)
            } else {
                ColorStateList.valueOf(Color.YELLOW)
            }

            recycle()
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        View.inflate(context, R.layout.merge_burger, this)
        burgerLettuce.visibility = if (includeLettuce) View.VISIBLE else View.GONE
        burgerSauce.imageTintList = sauceColor
    }
}
