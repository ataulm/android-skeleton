package com.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ataulm.materialdesignshowcase.MaterialDesignShowcaseActivity
import kotlinx.android.synthetic.main.activity_my.*

class MyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)

        darkButton.setOnClickListener {
            MaterialDesignShowcaseActivity.startActivity(this, R.style.Theme_Demo_Red)
        }
        lightButton.setOnClickListener {
            MaterialDesignShowcaseActivity.startActivity(this, R.style.Theme_Demo_Light)
        }
    }
}
