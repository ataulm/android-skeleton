package com.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.ui.core.Text
import androidx.ui.core.setContent

class MyComposeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Text("hello compose")
        }
    }
}
