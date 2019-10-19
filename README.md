# Mixin' Android Themes – how to combine styles from multiple sources

The Android theming system doesn’t support multiple inheritance. But uh, we can do some weird shit.

This post shows how we can use a custom layout inflater to specify multiple theme overlays for a single View, at the layout level or in a style resource, effectively including multiple styles, similar to Sass's mixin system.

## Naming

- Attribute set: the attributes we'll set on a View in an XML layout. We won't include the `style` attribute that we can set on a View in this definition (TK but maybe it's included)
- Style resource: this is a definition of a style in `res/values/somefile.xml`
- `style` attribute: this is the attribute we can set on a View in an XML layout. The value must be a style resource.
- `android:theme`/`app:theme` are attributes we can set on a View in a layout. We **cannot** set them in style resources (they'll be ignored TK check if View does know about `theme`) because they're only read by the `LayoutInflater` and the specified theme is overlaid using a `ContextThemeWrapper`.

TODO:
- check if View knows about `theme` attribute or if only LayoutInflater/AppCompatLayoutInflater knows about it
- check if `style`/`android:theme` attributes are considered part of AttributeSet.
- why isn't `createThemedContext` done in `MaterialComponentsViewInflater`? Instead it's done in each material component in the constructor. They specify `materialThemeOverlay` in the default style, so maybe the layoutinflater can't get it at that point? 

`style` is available from the AttributeSet that's passed to the inflater/constructor. We can use `attrs.styleAttribute` which is equivalent to `attrs.getAttributeResourceValue(null, "style")`. Not sure if it's possible to get from a typed array because I don't think it's defined as an attribute anyway (TK check this). We can put rando attributes and it'll be included in the AttributeSet but obviously it'll have no effect because nothing is looking for it. The purpose of the styleables is to organise these and declare them for autocompletion and so that specific views can associate them with specific indices from the typed array.



---

`Resources.obtainStyledAttributes`

When we write a custom View, it’s not uncommon for us to define some custom attributes. Let’s make a `SpottyFrameLayout` which draws spots on top of its children. We’ll define a couple of custom attributes so we can customise the spots too.

So here's our `SpottyFrameLayout`:

```kotlin
class SpottyFrameLayout(context: Context, attrs: AttributeSet)
    : FrameLayout(context, attrs)
```

and here's our custom attributes, defined in `res/values/attrs.xml`:

```xml
<resources>

    <declare-styleable name="SpottyFrameLayout">
        <attr name="spotColor" format="color" />
        <attr name="spotSize" format="dimension" />
    </declare-styleable>

</resources>
```

There's two reasons we define these attributes. The first reason is so that we can get contextual autocomplete in the IDE.

TK attr_autocomplete.png

Android Studio knows (from the styleable's name) that `SpottyFrameLayout` has two custom attributes so it can suggest them to us for autocomplete. Since we've also specified the _format_ for the attributes (color and dimension, respectively), it'll know to restrict the allowed values to that particular type.

The second reason is that a styleable definition is required to get a `TypedArray`, which is a handy way of reading values from an `AttributeSet` that are typed correctly. Let's try to read the values in our custom View:

```kotlin
init {
    val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SpottyFrameLayout)
    // TODO: read values from typedArray
}
```

We get an instance of a `TypedArray` back which should have two elements, one for each of the attributes that are in the `SpottyFrameLayout` styleable.

We could have read the attributes without a `TypedArray`, using functions directly on `AttributeSet` instead, but using `obtainStyledAttributes(...)` gives us:

- resolution of resource references e.g. resolving `@string/app_name` to "My app name"
- application of themes and styles when retrieving attribute values (hence the name, "obtain _styled_ attributes")

`TypedArray` is wrapper around an array, so we can access values with integer indices. In our case, we can use `0` or `1`  because we've only got two attributes. It would be horrible to use indices directly though—they correspond to the _alphabetized_ position of our custom attributes.

Instead, we use the styleables that are generated with the underscore convention and descriptive names:

```kotlin
init {
    val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SpottyFrameLayout)
    val spotColor = typedArray.getColor(R.styleable.SpottyFrameLayout_spotColor, 0)
    val spotSize = typedArray.getDimensionPixelSize(R.styleable.SpottyFrameLayout_spotSize, 0)
    typedArray.recycle()
}
```

It's important to remember to recycle the `TypedArray` because this is obtained from a pool of objects and when we're done with it, we should mark it as such.

When we have our attributes, we can use them to customise our View:

```kotlin
class SpottyFrameLayout(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    @Px
    private val spotSize: Float
    private val spotPaint = Paint().apply { isAntiAlias = true }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SpottyFrameLayout)
        spotSize = typedArray.getDimension(R.styleable.SpottyFrameLayout_spotSize, 0f)
        spotPaint.color = typedArray.getColor(R.styleable.SpottyFrameLayout_spotColor, 0)
        typedArray.recycle()

        setWillNotDraw(false)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        val spotRadius = (spotSize / 2)
        val maxSpotsHorizontal = (width / spotSize).toInt()
        val maxSpotsVertical = (height / spotSize).toInt()
        for (i in 0 until maxSpotsHorizontal) {
            for (j in 0 until maxSpotsVertical) {
                if (Math.random() > 0.95) {
                    val adjustedRadiusElseItIsBoring = spotRadius - (Math.random() * spotRadius).toFloat()
                    canvas.drawCircle(spotRadius + i * spotSize, spotRadius + j * spotSize, adjustedRadiusElseItIsBoring, spotPaint)
                }
            }
        }
    }
}
```

TK spotty_framelayout.png

---

When we're reading attributes in custom Views, we use a function to obtain a `TypedArray` containing the specific attributes were after.



View.java
>By default, Views are created using the theme of the Context object supplied to their constructor; however, a different theme may be specified by using the {@link android.R.styleable#View_theme android:theme} attribute in layout XML or by passing a {@link ContextThemeWrapper} to the constructor from code.
>
>When the {@link android.R.styleable#View_theme android:theme} attribute is used in XML, the specified theme is applied on top of the inflation context's theme (see {@link LayoutInflater}) and used for the view itself as well as any child elements.

Resources.java (`obtainStyledAttributes(...)`)
>When determining the final value of a particular attribute, there are four inputs that come into play:
>
>1. Any attribute values in the given `AttributeSet`.
>2. The style resource specified in the `AttributeSet` (named "style").
>3. The default style specified by `defStyleAttr` and     `defStyleRes`
>4. The base values in this theme.

View docs talk about `android:theme` but it never tries to read the value. Seems like it's a layout inflater responsibility.
If we keep the same convention (since `android:theme` is meant to specify a theme that's going to wrap the theme that's passed in to the constructor) we should drop the `overlay` from the attr too. So maybe `app:theme2`, `app:theme3`, ... `app:themeN`?