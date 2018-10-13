package com.example.swivel

import android.view.animation.Animation

interface SimpleAnimationListener : Animation.AnimationListener {

    override fun onAnimationRepeat(animation: Animation?) {}
    override fun onAnimationEnd(animation: Animation?) {}
    override fun onAnimationStart(animation: Animation?) {}
}

fun Animation.setOnAnimationEndListener(onAnimationEnd: (Animation?) -> Unit) {

    setAnimationListener(object : SimpleAnimationListener {
        override fun onAnimationEnd(animation: Animation?) = onAnimationEnd(animation)
    })
}
