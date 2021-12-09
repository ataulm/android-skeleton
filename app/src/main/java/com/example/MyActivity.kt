package com.example

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.view.ViewCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)

        val seekBarStateDesc = findViewById<SeekBar>(R.id.seekBarWithCustomStateDescription)
        seekBarStateDesc.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                setStateDescCompat(seekBarStateDesc, progress)
                setStateDescApi30(seekBarStateDesc, progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        var toast: Toast? = null
        findViewById<Button>(R.id.button).setOnClickListener {
            toast?.cancel()
            toast = Toast.makeText(
                applicationContext,
//                getStateDescCompat(seekBarStateDesc),
                getStateDescApi30(seekBarStateDesc),
                Toast.LENGTH_SHORT
            ).also { it.show() }
        }
    }

    private fun setStateDescCompat(seekBarStateDesc: SeekBar, progress: Int) {
        ViewCompat.setStateDescription(seekBarStateDesc, "£$progress")
        Log.d("!!!", "seekBarStateDesc: ${ViewCompat.getStateDescription(seekBarStateDesc)}")
    }

    private fun setStateDescApi30(seekBarStateDesc: SeekBar, progress: Int) {
        seekBarStateDesc.stateDescription = "£$progress"
        Log.d("!!!", "seekBarStateDesc: ${seekBarStateDesc.stateDescription}")
    }

    private fun getStateDescCompat(seekBarStateDesc: SeekBar): String {
        return "${ViewCompat.getStateDescription(seekBarStateDesc)}"
    }

    private fun getStateDescApi30(seekBarStateDesc: SeekBar): String {
        return "${seekBarStateDesc.stateDescription}"
    }
}