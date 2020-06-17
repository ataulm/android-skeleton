package com.example

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_my.*

class MyActivity : AppCompatActivity() {

    private val playButton: Controls.PrimaryButton.Play = Controls.PrimaryButton.Play {
        Toast.makeText(this, "play", Toast.LENGTH_SHORT).show()
        controlsView.set(Controls.UiModel(
                primaryButton = pauseButton,
                rewindClickListener = rewindClickListener
        ))
    }

    private val pauseButton: Controls.PrimaryButton.Pause = Controls.PrimaryButton.Pause {
        Toast.makeText(this, "pause", Toast.LENGTH_SHORT).show()
        controlsView.set(Controls.UiModel(
                primaryButton = playButton,
                rewindClickListener = rewindClickListener
        ))
    }

    private val rewindClickListener = {
        Toast.makeText(this, "rewind", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)

        textView.setText(R.string.lorem_ipsum)
        textView.rotationX = 180f
        scrollView.post {
            scrollView.fullScroll(View.FOCUS_DOWN)
        }

        controlsView.set(Controls.UiModel(playButton, rewindClickListener = rewindClickListener))
    }
}
