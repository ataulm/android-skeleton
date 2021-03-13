package com.example

import android.content.res.Resources
import android.graphics.Point
import android.os.Bundle
import android.util.Size
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat

class MyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)

        setupRed()
        setupBlue()
        setupOrange()
        setupGreen()
    }

    private fun setupRed() {
        val redInfoButton = findViewById<View>(R.id.redInfo)
        setupPopupButton(
            redInfoButton,
            R.layout.red_popup
        ) { buttonPosition: IntArray, popupSize: Size ->
            Point(
                if (redInfoButton.isInRtl()) {
                    buttonPosition.x
                } else {
                    buttonPosition.x + redInfoButton.width - popupSize.width
                },
                buttonPosition.y + redInfoButton.height
            )
        }
    }

    private fun View.isInRtl(): Boolean {
        return ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL
    }

    private fun setupBlue() {
        val blueInfoButton = findViewById<View>(R.id.blueInfo)
        setupPopupButton(
            blueInfoButton,
            R.layout.blue_popup
        ) { buttonPosition: IntArray, popupSize: Size ->
            Point(
                if (blueInfoButton.isInRtl()) {
                    buttonPosition.x + blueInfoButton.width - popupSize.width
                } else {
                    buttonPosition.x
                },
                buttonPosition.y + blueInfoButton.height
            )
        }
    }

    private fun setupGreen() {
        val greenInfoButton = findViewById<View>(R.id.greenInfo)
        setupPopupButton(
            greenInfoButton,
            R.layout.green_popup
        ) { buttonPosition: IntArray, popupSize: Size ->
            Point(
                if (greenInfoButton.isInRtl()) {
                    buttonPosition.x + greenInfoButton.width - popupSize.width
                } else {
                    buttonPosition.x
                },
                buttonPosition.y - popupSize.height
            )
        }
    }

    private fun setupOrange() {
        val orangeInfoButton = findViewById<View>(R.id.orangeInfo)
        setupPopupButton(
            orangeInfoButton,
            R.layout.orange_popup
        ) { buttonPosition: IntArray, popupSize: Size ->
            Point(
                if (orangeInfoButton.isInRtl()) {
                    buttonPosition.x
                } else {
                    buttonPosition.x + orangeInfoButton.width - popupSize.width
                },
                buttonPosition.y - popupSize.height
            )
        }
    }

    private fun setupPopupButton(
        popupButton: View,
        popupLayoutRes: Int,
        determinePopupLocation: (buttonPosition: IntArray, popupSize: Size) -> Point
    ) {
        val popupView = View.inflate(this, popupLayoutRes, null)
        // we need to measure it so we can use the dimens in our calc to position on screen
        popupView.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        val popupSize = Size(popupView.measuredWidth, popupView.measuredHeight)

        val popupWindow = PopupWindow(
            popupView,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            true
        ).also {
            it.elevation = popupView.elevation
        }

        popupButton.setOnClickListener {
            val buttonPosition = IntArray(2)
            popupButton.getLocationOnScreen(buttonPosition)
            val location = determinePopupLocation(buttonPosition, popupSize)
            popupWindow.showAtLocation(
                popupButton,
                Gravity.NO_GRAVITY,
                location.x,
                location.y
            )
        }
    }

    private val IntArray.x
        get() = this[0]

    private val IntArray.y
        get() = this[1]

    private val Int.dp
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()
}

