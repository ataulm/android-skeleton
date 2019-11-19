package com.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
                .add(android.R.id.content, ButtonedFragment(R.layout.fragment_buttoned_one), "a")
                .commit()

        supportFragmentManager.beginTransaction()
                .add(android.R.id.content, ButtonedFragment(R.layout.fragment_buttoned_two), "b")
                .addToBackStack(null)
                .commit()
    }
}

internal class ButtonedFragment(private val layoutId: Int) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }
}
