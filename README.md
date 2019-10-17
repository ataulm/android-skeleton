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




`Resources.obtainStyledAttributes`

When we write a custom View, it’s not uncommon for us to define some custom attributes. For example, let’s make a `SpottyFrameLayout` which draws spots on top of its children, and let’s define a couple of custom attributes to allow configuration of these spots.



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