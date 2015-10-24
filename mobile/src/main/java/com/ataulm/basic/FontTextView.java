package com.ataulm.basic;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FontTextView extends TextView {

    private final TypefaceFactory typefaceFactory;

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.typefaceFactory = new TypefaceFactory();

        applyCustomAttributes(context, attrs);
    }

    private void applyCustomAttributes(Context context, AttributeSet attrs) {
        if (isInEditMode()) {
            return;
        }

        Typeface typeface = typefaceFactory.createFrom(context, attrs);
        int style = TypefaceFactory.extractStyle(context, attrs);
        setTypeface(typeface, style);
        setAllCaps(extractTextAllCaps(context, attrs));
    }

    private static boolean extractTextAllCaps(Context context, AttributeSet attrs) {
        int[] attrValues = {android.R.attr.textAllCaps};
        TypedArray typedArray = context.obtainStyledAttributes(attrs, attrValues);
        try {
            return typedArray.getBoolean(0, false);
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    public final void setAllCaps(boolean allCaps) {
        if (allCaps) {
            setTransformationMethod(new SpanFriendlyAllCapsTransformationMethod(getContext()));
        } else {
            setTransformationMethod(null);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        getPaint().setSubpixelText(true);
    }

    private static class TypefaceFactory {

        /**
         * order must match with attrs.xml > styleable=WutsonTextView
         */
        public enum FontType {

            ROBOTO_MEDIUM("fonts/Roboto-Medium.ttf"),
            ROBOTO_REGULAR("fonts/Roboto-Regular.ttf");

            private final String assetUrl;

            FontType(String assetUrl) {
                this.assetUrl = assetUrl;
            }

        }

        private static final Map<FontType, SoftReference<Typeface>> FONT_CACHE = new HashMap<>();
        private static final int INVALID_FONT_ID = -1;
        private static final Typeface DEFAULT_TYPEFACE = null;

        public static int extractStyle(Context context, AttributeSet attrs) {
            int[] attrValues = {android.R.attr.textStyle};
            TypedArray typedArray = context.obtainStyledAttributes(attrs, attrValues);
            if (typedArray == null) {
                return Typeface.NORMAL;
            }
            try {
                return typedArray.getInt(0, Typeface.NORMAL);
            } finally {
                typedArray.recycle();
            }
        }

        public Typeface createFrom(Context context, AttributeSet attrs) {
            int fontId = getFontId(context, attrs);
            if (!isValidId(fontId)) {
                return DEFAULT_TYPEFACE;
            }
            FontType fontType = getFontType(fontId);
            return getTypeFace(context, fontType);
        }

        private int getFontId(Context context, AttributeSet attrs) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FontTextView);
            if (typedArray == null) {
                return INVALID_FONT_ID;
            }

            try {
                return typedArray.getInt(R.styleable.FontTextView_textFont, INVALID_FONT_ID);
            } finally {
                typedArray.recycle();
            }
        }

        private boolean isValidId(int fontId) {
            return fontId > INVALID_FONT_ID && fontId < FontType.values().length;
        }

        private FontType getFontType(int fontId) {
            return FontType.values()[fontId];
        }

        private Typeface getTypeFace(Context context, FontType fontType) {
            synchronized (FONT_CACHE) {
                if (fontExistsInCache(fontType)) {
                    return getCachedTypeFace(fontType);
                }

                Typeface typeface = createTypeFace(context, fontType);
                saveFontToCache(fontType, typeface);

                return typeface;
            }
        }

        private boolean fontExistsInCache(FontType fontType) {
            return FONT_CACHE.get(fontType) != null && getCachedTypeFace(fontType) != null;
        }

        private Typeface getCachedTypeFace(FontType fontType) {
            return FONT_CACHE.get(fontType).get();
        }

        private Typeface createTypeFace(Context context, FontType fontType) {
            return Typeface.createFromAsset(context.getAssets(), fontType.assetUrl);
        }

        private void saveFontToCache(FontType fontType, Typeface typeface) {
            FONT_CACHE.put(fontType, new SoftReference<Typeface>(typeface));
        }

    }

    private static class SpanFriendlyAllCapsTransformationMethod implements TransformationMethod {

        private final Locale locale;

        public SpanFriendlyAllCapsTransformationMethod(Context context) {
            locale = context.getResources().getConfiguration().locale;
        }

        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            if (source == null || source.length() == 0) {
                return source;
            }
            return toUpperCase(source);
        }

        private CharSequence toUpperCase(CharSequence source) {
            String upperCase = source.toString().toUpperCase(locale);
            if (isNotASpannedSource(source)) {
                return upperCase;
            }

            List<SpanPosition> spanPositions = extractSpansFrom((Spanned) source);
            return applySpansTo(upperCase, spanPositions);
        }

        @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
        // SpanPositions can't be predicted and not worth the optimisation of an object pool
        private List<SpanPosition> extractSpansFrom(Spanned spannable) {
            Object[] spans = spannable.getSpans(0, spannable.length(), Object.class);
            List<SpanPosition> spanPositions = new ArrayList<>();
            for (Object span : spans) {
                int spanStart = spannable.getSpanStart(span);
                int spanEnd = spannable.getSpanEnd(span);
                int spanFlags = spannable.getSpanFlags(span);
                spanPositions.add(new SpanPosition(span, spanStart, spanEnd, spanFlags));
            }
            return spanPositions;
        }

        private Spannable applySpansTo(String upperCase, List<SpanPosition> spanPositions) {
            Spannable spannedUpperCase = Spannable.Factory.getInstance().newSpannable(upperCase);
            for (SpanPosition spanPosition : spanPositions) {
                spannedUpperCase.setSpan(spanPosition.span, spanPosition.start, spanPosition.end, spanPosition.flags);
            }
            return spannedUpperCase;
        }

        private boolean isNotASpannedSource(CharSequence source) {
            return !(source instanceof Spanned);
        }

        @Override
        public void onFocusChanged(View view, CharSequence sourceText, boolean focused, int direction, Rect previouslyFocusedRect) {
            // No op
        }

        @SuppressWarnings("checkstyle:visibilitymodifier") // We follow closer to Google code style for this one.
        private static final class SpanPosition {

            public final Object span;
            public final int start;
            public final int end;
            public final int flags;

            SpanPosition(Object span, int start, int end, int flags) {
                this.span = span;
                this.start = start;
                this.end = end;
                this.flags = flags;
            }

        }

    }

}
