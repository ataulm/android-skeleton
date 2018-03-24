package com.example

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.AlignmentSpan
import android.text.style.StyleSpan
import android.text.style.URLSpan
import android.view.View
import android.widget.TextView

private const val LINE_BREAK = " "

private const val URL_GOOGLE = "http://google.com"

class MyActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)

        val textView: TextView = findViewById(R.id.text_view)
        val paint = Paint()
        val textPaint = TextPaint(paint)
        paint.color = Color.BLACK
        textPaint.color = Color.RED

        textView.doOnLayout {
            textView.text = TextUtils.ellipsize(
                    writeFormattedStory(),
                    textPaint,
                    textView.width.toFloat(),
                    TextUtils.TruncateAt.END
            )
        }
        textView.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun writeFormattedStory(): CharSequence {
        val alignCenterSpan = AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER)
        return SpannableStringBuilder()
                .append(createWithSpans("A Short Story", StyleSpan(Typeface.BOLD), alignCenterSpan))
                .append(LINE_BREAK)
                .append(createWithSpans("This is a short story", URLSpan(URL_GOOGLE), alignCenterSpan))
                .append(LINE_BREAK)
                .append(createWithSpans("fin", StyleSpan(Typeface.ITALIC)))
    }

    private fun createWithSpans(charSequence: CharSequence, vararg spans: Any): SpannableString {
        val spannableString = SpannableString(charSequence)
        for (span in spans) {
            spannableString.setSpan(span, 0, charSequence.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return spannableString
    }
}

inline fun View.doOnLayout(crossinline action: (view: View) -> Unit) {
    if (ViewCompat.isLaidOut(this) && !isLayoutRequested) {
        action(this)
    } else {
        doOnNextLayout {
            action(it)
        }
    }
}

inline fun View.doOnNextLayout(crossinline action: (view: View) -> Unit) {
    addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
        override fun onLayoutChange(
                view: View,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int
        ) {
            view.removeOnLayoutChangeListener(this)
            action(view)
        }
    })
}
