package com.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_my.*

class MyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)

        rectanglesView.spread()
        red.setOnClickListener { rectanglesView.show(ChoiceOfRectanglesView.Rectangle.RED) }
        yellow.setOnClickListener { rectanglesView.show(ChoiceOfRectanglesView.Rectangle.YELLOW) }
        blue.setOnClickListener { rectanglesView.show(ChoiceOfRectanglesView.Rectangle.BLUE) }
    }
}
