package com.example

import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Layout
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.AlignmentSpan
import android.text.style.StyleSpan
import android.view.View
import android.view.View.AccessibilityDelegate
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.TextView
import java.util.*

private const val LINE_BREAK = "\n"

class MyActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)

        val textView: TextView = findViewById(R.id.text_view)
        textView.text = writeFormattedStory()
        textView.setAccessibilityDelegate(object : View.AccessibilityDelegate() {
            override fun onPopulateAccessibilityEvent(host: View?, event: AccessibilityEvent?) {
                event?.text?.clear()
//                super.onPopulateAccessibilityEvent(host, event)
            }

            override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfo?) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                info?.text = String.format(Locale.US, "%s, whatever", info?.text!!)
            }
        })
    }

    private fun writeFormattedStory(): SpannableStringBuilder {
        val alignCenterSpan = AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER)
        return SpannableStringBuilder()
                .append(createWithSpans("A Short Story", StyleSpan(Typeface.BOLD), alignCenterSpan))
                .append(LINE_BREAK)
                .append(createWithSpans("This is a short story.", alignCenterSpan))
                .append(LINE_BREAK)
                .append(createWithSpans("fin.", StyleSpan(Typeface.ITALIC), alignCenterSpan))
    }

    private fun createWithSpans(charSequence: CharSequence, vararg spans: Any): SpannableString {
        val spannableString = SpannableString(charSequence)
        for (span in spans) {
            spannableString.setSpan(span, 0, charSequence.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return spannableString
    }
}
