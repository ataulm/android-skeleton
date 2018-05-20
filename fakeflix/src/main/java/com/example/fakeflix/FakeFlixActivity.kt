package com.example.fakeflix

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_fake_flix.*

class FakeFlixActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fake_flix)

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
