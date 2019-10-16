package com.example

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.InflateException
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
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

    override fun createTextView(context: Context, attrs: AttributeSet?): AppCompatTextView {
        val ctx = ContextThemeWrapper(context, R.style.ThemeOverlay_Demo_Text)
        val ctx2 = ContextThemeWrapper(ctx, R.style.ThemeOverlay_Demo_TextColor)
        return super.createTextView(ctx2, attrs)
    }

    override fun createView(context: Context, name: String, attrs: AttributeSet): View? {
        var name = name
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
