package com.example

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_my.*

class MyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)

        darkButton.setOnClickListener {
           startActivity(Intent(this, DarkThemeActivity::class.java))
        }
        lightButton.setOnClickListener {
           startActivity(Intent(this, LightThemeActivity::class.java))
        }
    }
}

class DarkThemeActivity : AppCompatActivity(R.layout.activity_material_design_showcase)
class LightThemeActivity : AppCompatActivity(R.layout.activity_material_design_showcase)
