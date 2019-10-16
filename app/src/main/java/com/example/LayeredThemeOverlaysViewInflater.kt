package com.example

import android.content.Context
import android.util.AttributeSet
import android.view.InflateException
import android.view.View
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatCheckedTextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.AppCompatToggleButton
import androidx.collection.ArrayMap
import com.google.android.material.theme.MaterialComponentsViewInflater
import java.lang.reflect.Constructor

/**
 * Copied bits and pieces from [AppCompatViewInflater] since some of the functions are final/private.
 * This was to allow us to hook into the `createView` for _any_ view, not just the AppCompat ones or custom ones.
 *
 * This will let us read our custom theme overlay attr(s) and add as many ContextThemeWrappers as there are theme overlays.
 */
class LayeredThemeOverlaysViewInflater : MaterialComponentsViewInflater() {

    private val sConstructorSignature = arrayOf(Context::class.java, AttributeSet::class.java)
    private val sClassPrefixList = arrayOf("android.widget.", "android.view.", "android.webkit.")
    private val sConstructorMap = ArrayMap<String, Constructor<out View>>()
    private val mConstructorArgs = arrayOfNulls<Any>(2)

    override fun createTextView(context: Context, attrs: AttributeSet): AppCompatTextView {
        return super.createTextView(moreThemifyContext(context, attrs), attrs)
    }

    override fun createImageView(context: Context, attrs: AttributeSet): AppCompatImageView {
        return super.createImageView(moreThemifyContext(context, attrs), attrs)
    }

    override fun createButton(context: Context, attrs: AttributeSet): AppCompatButton {
        return super.createButton(moreThemifyContext(context, attrs), attrs)
    }

    override fun createEditText(context: Context, attrs: AttributeSet): AppCompatEditText {
        return super.createEditText(moreThemifyContext(context, attrs), attrs)
    }

    override fun createSpinner(context: Context, attrs: AttributeSet): AppCompatSpinner {
        return super.createSpinner(moreThemifyContext(context, attrs), attrs)
    }

    override fun createImageButton(context: Context, attrs: AttributeSet): AppCompatImageButton {
        return super.createImageButton(moreThemifyContext(context, attrs), attrs)
    }

    override fun createCheckBox(context: Context, attrs: AttributeSet): AppCompatCheckBox {
        return super.createCheckBox(moreThemifyContext(context, attrs), attrs)
    }

    override fun createRadioButton(context: Context, attrs: AttributeSet): AppCompatRadioButton {
        return super.createRadioButton(moreThemifyContext(context, attrs), attrs)
    }

    override fun createCheckedTextView(context: Context, attrs: AttributeSet): AppCompatCheckedTextView {
        return super.createCheckedTextView(moreThemifyContext(context, attrs), attrs)
    }

    override fun createAutoCompleteTextView(context: Context,
                                             attrs: AttributeSet): AppCompatAutoCompleteTextView {
        return super.createAutoCompleteTextView(moreThemifyContext(context, attrs), attrs)
    }

    override fun createMultiAutoCompleteTextView(context: Context,
                                                  attrs: AttributeSet): AppCompatMultiAutoCompleteTextView {
        return super.createMultiAutoCompleteTextView(moreThemifyContext(context, attrs), attrs)
    }

    override fun createRatingBar(context: Context, attrs: AttributeSet): AppCompatRatingBar {
        return super.createRatingBar(moreThemifyContext(context, attrs), attrs)
    }

    override fun createSeekBar(context: Context, attrs: AttributeSet): AppCompatSeekBar {
        return super.createSeekBar(moreThemifyContext(context, attrs), attrs)
    }

    override fun createToggleButton(context: Context, attrs: AttributeSet): AppCompatToggleButton {
        return super.createToggleButton(moreThemifyContext(context, attrs), attrs)
    }

    override fun createView(passedContext: Context, passedName: String, attrs: AttributeSet): View? {
        val context = moreThemifyContext(passedContext, attrs)
        var name = passedName
        if (name == "view") {
            name = attrs.getAttributeValue(null, "class")
        }

        try {
            mConstructorArgs[0] = context
            mConstructorArgs[1] = attrs

            if (-1 == name.indexOf('.')) {
                for (i in sClassPrefixList.indices) {
                    val view = createViewByPrefix(context, name, sClassPrefixList[i])
                    if (view != null) {
                        return view
                    }
                }
                return null
            } else {
                return createViewByPrefix(context, name, null)
            }
        } catch (e: Exception) {
            // We do not want to catch these, lets return null and let the actual LayoutInflater
            // try
            return null
        } finally {
            // Don't retain references on context.
            mConstructorArgs[0] = null
            mConstructorArgs[1] = null
        }
    }

    private fun moreThemifyContext(context: Context, attrs: AttributeSet): Context {
        val a = context.obtainStyledAttributes(attrs, R.styleable.View, 0, 0)
        val extraThemeOverlayId = a.getResourceId(R.styleable.View_extraThemeOverlay, 0)
        val extraThemeOverlayId2 = a.getResourceId(R.styleable.View_extraThemeOverlay2, 0)
        a.recycle()

        var moreThemedContext = context
        moreThemedContext = if (extraThemeOverlayId != 0 && (moreThemedContext !is androidx.appcompat.view.ContextThemeWrapper || moreThemedContext.themeResId != extraThemeOverlayId)) {
            // If the context isn't a ContextThemeWrapper, or it is but does not have
            // the same theme as we need, wrap it in a new wrapper
            androidx.appcompat.view.ContextThemeWrapper(moreThemedContext, extraThemeOverlayId)
        } else moreThemedContext

        moreThemedContext = if (extraThemeOverlayId2 != 0 && (moreThemedContext !is androidx.appcompat.view.ContextThemeWrapper || moreThemedContext.themeResId != extraThemeOverlayId2)) {
            // If the context isn't a ContextThemeWrapper, or it is but does not have
            // the same theme as we need, wrap it in a new wrapper
            androidx.appcompat.view.ContextThemeWrapper(moreThemedContext, extraThemeOverlayId2)
        } else moreThemedContext

        return moreThemedContext
    }

    @Throws(ClassNotFoundException::class, InflateException::class)
    private fun createViewByPrefix(context: Context, name: String, prefix: String?): View? {
        var constructor = sConstructorMap[name]

        try {
            if (constructor == null) {
                // Class not found in the cache, see if it's real, and try to add it
                val clazz = Class.forName(
                        if (prefix != null) prefix + name else name,
                        false,
                        context.classLoader).asSubclass(View::class.java)

                constructor = clazz.getConstructor(*sConstructorSignature)
                sConstructorMap[name] = constructor!!
            }
            constructor.isAccessible = true
            return constructor.newInstance(*mConstructorArgs)
        } catch (e: Exception) {
            // We do not want to catch these, lets return null and let the actual LayoutInflater
            // try
            return null
        }
    }
}
