package com.example

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_my.*

class MyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)

        red.setOnClickListener {
            rectanglesView.show(ChoiceOfRectanglesView.Rectangle.RED)
            select(red)
        }

        yellow.setOnClickListener {
            rectanglesView.show(ChoiceOfRectanglesView.Rectangle.YELLOW)
            select(yellow)
        }

        blue.setOnClickListener {
            rectanglesView.show(ChoiceOfRectanglesView.Rectangle.BLUE)
            select(blue)
        }

        rectanglesView.spread()
    }

    private fun select(button: Button) {
        red.isSelected = red == button
        yellow.isSelected = yellow == button
        blue.isSelected = blue == button
    }
}
