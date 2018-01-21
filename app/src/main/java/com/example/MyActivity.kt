package com.example

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import android.view.View

class MyActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)
    }

    fun sendKeyEvent(view: View) {
        Log.d("!!!", "gosfkljas;lf")

        val handler = Handler(Looper.myLooper())
        val message = Message.obtain(handler)
        message.obj = KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_0)
        message.what = 6 // AccessibilityService.IAccessibilityServiceClientWrapper.DO_ON_KEY_EVENT
        handler.dispatchMessage(message)

        val message2 = Message.obtain(handler)
        message2.obj = KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_0)
        message2.what = 6 // AccessibilityService.IAccessibilityServiceClientWrapper.DO_ON_KEY_EVENT
        handler.dispatchMessage(message2)
    }
}
