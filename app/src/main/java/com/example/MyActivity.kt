package com.example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_my.*

class MyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)

        imageFlippingCheckableView.imageView().setImageResource(R.drawable.ic_launcher)
        imageFlippingCheckableView.setOnClickListener {
            imageFlippingCheckableView.toggle()
        }
    }
}
