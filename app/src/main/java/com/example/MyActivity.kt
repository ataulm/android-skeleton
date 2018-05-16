package com.example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_my.*

class MyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)

        promptIfStillWatching()
        continueWatchingButton.setOnClickListener {
            outerLayout.visibility = View.INVISIBLE
            Log.d("!!!", "clicked!")
            promptIfStillWatching()
        }
    }

    private fun promptIfStillWatching() {
        outerLayout.postDelayed({ outerLayout.visibility = View.VISIBLE }, 5000)
    }
}
