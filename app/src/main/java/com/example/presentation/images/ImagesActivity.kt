package com.example.presentation.images

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.R
import com.example.domain.Breed
import com.example.domain.Subbreed

internal class ImagesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images)
    }

    companion object {

        private const val KEY_BREED = "key_breed"
        private const val KEY_SUBBREED = "key_subbreed"

        fun buildIntent(activity: Activity, breed: Breed, subbreed: Subbreed? = null): Intent {
            return Intent(activity, ImagesActivity::class.java)
                    .putExtra(KEY_BREED, breed)
                    .putExtra(KEY_SUBBREED, subbreed)
        }
    }
}
